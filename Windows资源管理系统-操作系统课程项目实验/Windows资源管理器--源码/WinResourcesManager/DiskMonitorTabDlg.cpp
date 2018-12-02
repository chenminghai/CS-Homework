// DiskMonitorTabDlg.cpp : 实现文件
//

#include "stdafx.h"
#include "WinResourcesManager.h"
#include "DiskMonitorTabDlg.h"
#include "afxdialogex.h"

// CDiskMonitorTabDlg 对话框

IMPLEMENT_DYNAMIC(CDiskMonitorTabDlg, CDialogEx)

CDiskMonitorTabDlg::CDiskMonitorTabDlg(CWnd* pParent /*=NULL*/)
	: CDialogEx(CDiskMonitorTabDlg::IDD, pParent)
	, m_CreateDataChg(FALSE)
	, m_DirCDR(FALSE)
	, m_FileCDR(FALSE)
	, m_FileDirAttrChg(FALSE)
	, m_FileSizeChg(FALSE)
	, m_SaftyAttrChg(FALSE)
	, m_VisitDataChg(FALSE)
	, m_bWatchSubtree(FALSE)
	, m_WriteDataChg(FALSE)
	, m_bWriteToLog(FALSE)
{
	m_dwNotifyFilter = 0;
	//{{AFX_DATA_INIT(CDiskMonitorDlg)
	m_FileCDR = TRUE;
	m_DirCDR = TRUE;
	m_CreateDataChg = TRUE;
	m_FileSizeChg = TRUE;
	m_FileDirAttrChg = TRUE;
	m_SaftyAttrChg = TRUE;
	m_VisitDataChg = TRUE;
	m_WriteDataChg = TRUE;
	m_bWriteToLog = TRUE;
	m_bWatchSubtree = TRUE;
}

CDiskMonitorTabDlg::~CDiskMonitorTabDlg()
{
}

void CDiskMonitorTabDlg::DoDataExchange(CDataExchange* pDX)
{
	CDialogEx::DoDataExchange(pDX);
	DDX_Control(pDX, IDC_MFCEDITBROWSE1, m_monitorPath);
	DDX_Check(pDX, IDC_CREATA_DATA_CHG, m_CreateDataChg);
	DDX_Check(pDX, IDC_DIR_C_D_R, m_DirCDR);
	DDX_Control(pDX, IDC_EVENTS, m_List);
	DDX_Check(pDX, IDC_FILE_C_D_R, m_FileCDR);
	DDX_Check(pDX, IDC_FILE_DIR_ATTR_CHG, m_FileDirAttrChg);
	DDX_Check(pDX, IDC_FILE_SIZE_CHG, m_FileSizeChg);
	DDX_Check(pDX, IDC_SAFTY_ATTR_CHG, m_SaftyAttrChg);
	DDX_Check(pDX, IDC_VISIT_DATA_CHG, m_VisitDataChg);
	DDX_Check(pDX, IDC_WATCH_SUBTREE, m_bWatchSubtree);
	DDX_Check(pDX, IDC_WRITE_DATA_CHG, m_WriteDataChg);
	DDX_Check(pDX, IDC_WRITE_TO_LOG, m_bWriteToLog);
}


BEGIN_MESSAGE_MAP(CDiskMonitorTabDlg, CDialogEx)
	ON_BN_CLICKED(IDC_VIEW_LOG, &CDiskMonitorTabDlg::OnBnClickedOpenlog)
	ON_BN_CLICKED(IDC_Start, &CDiskMonitorTabDlg::OnBnClickedStart)
	ON_BN_CLICKED(IDC_Stop, &CDiskMonitorTabDlg::OnBnClickedStop)
END_MESSAGE_MAP()


// CDiskMonitorTabDlg 消息处理程序


BOOL CDiskMonitorTabDlg::OnInitDialog()
{
	CDialogEx::OnInitDialog();

	// TODO:  在此添加额外的初始化
	CString temp = getPath();
	m_monitorPath.SetWindowTextW(temp);
	m_monitorPath.SetSel(-1);
	m_List.SetTextColor(RGB(0, 0, 255));
	m_List.InsertColumn(0, L"时间", LVCFMT_CENTER, 120);
	m_List.InsertColumn(1, L"事件", LVCFMT_CENTER, 250);
	::SetWindowPos(m_hWnd, wndTopMost, 0, 0, 0, 0, SWP_NOMOVE | SWP_NOSIZE);
	return TRUE;  // return TRUE unless you set the focus to a control
	// 异常:  OCX 属性页应返回 FALSE
}


void CDiskMonitorTabDlg::OnBnClickedOpenlog()
{
	// TODO:  在此添加控件通知处理程序代码
	m_dskm->OpenLog();
	if (m_dskm != NULL)
	{
		m_dskm->set(m_dskm);
	}
}


void CDiskMonitorTabDlg::OnBnClickedStart()
{
	// TODO:  在此添加控件通知处理程序代码
	m_dskm = new DiskMonitor;
	UpdateData(TRUE);
	CString temp;
	m_monitorPath.GetWindowTextW(temp);
	int nLength = temp.GetLength();
	int nBytes = WideCharToMultiByte(CP_ACP, 0, temp, nLength, NULL, 0, NULL, NULL);
	memset(m_cPath, 0, nLength + 1);
	WideCharToMultiByte(CP_OEMCP, 0, temp, nLength, m_cPath, nBytes, NULL, NULL);
	m_cPath[nBytes] = 0;

	//MessageBox(CString(m_cPath));
	char path[MAX_PATH] = { 0 };
	int i, k;
	for (i = 0, k = 0; m_cPath[i] != 0; i++)
	{
		if (m_cPath[i] == '\\')
		{
			path[k++] = '\\';
			path[k++] = '\\';
		}
		else path[k++] = m_cPath[i];
	}
	path[k] = '\0';
	GetNotifyFilter();
	EnableWindowItems(FALSE);

	m_dskm->m_bWriteLog = m_bWriteToLog;
	m_dskm->Run(m_hWnd, path, m_bWatchSubtree, m_dwNotifyFilter);
}
void CDiskMonitorTabDlg::GetNotifyFilter()
{
	m_dwNotifyFilter = 0;
	if (m_FileCDR == TRUE)
		m_dwNotifyFilter |= FILE_NOTIFY_CHANGE_FILE_NAME;
	if (m_DirCDR == TRUE)
		m_dwNotifyFilter |= FILE_NOTIFY_CHANGE_DIR_NAME;
	if (m_FileDirAttrChg == TRUE)
		m_dwNotifyFilter |= FILE_NOTIFY_CHANGE_ATTRIBUTES;
	if (m_FileSizeChg == TRUE)
		m_dwNotifyFilter |= FILE_NOTIFY_CHANGE_SIZE;
	if (m_SaftyAttrChg == TRUE)
		m_dwNotifyFilter |= FILE_NOTIFY_CHANGE_SECURITY;
	if (m_VisitDataChg == TRUE)
		m_dwNotifyFilter |= FILE_NOTIFY_CHANGE_LAST_ACCESS;
	if (m_WriteDataChg == TRUE)
		m_dwNotifyFilter |= FILE_NOTIFY_CHANGE_LAST_WRITE;
	if (m_CreateDataChg == TRUE)
		m_dwNotifyFilter |= FILE_NOTIFY_CHANGE_CREATION;
}

void CDiskMonitorTabDlg::EnableWindowItems(BOOL able)
{
	GetDlgItem(IDC_FILE_C_D_R)->EnableWindow(able);
	GetDlgItem(IDC_DIR_C_D_R)->EnableWindow(able);
	GetDlgItem(IDC_FILE_DIR_ATTR_CHG)->EnableWindow(able);
	GetDlgItem(IDC_FILE_SIZE_CHG)->EnableWindow(able);
	GetDlgItem(IDC_VISIT_DATA_CHG)->EnableWindow(able);
	GetDlgItem(IDC_WRITE_DATA_CHG)->EnableWindow(able);
	GetDlgItem(IDC_SAFTY_ATTR_CHG)->EnableWindow(able);
	GetDlgItem(IDC_CREATA_DATA_CHG)->EnableWindow(able);
	GetDlgItem(IDC_Start)->EnableWindow(able);
	GetDlgItem(IDC_DIR_PATH)->EnableWindow(able);
	GetDlgItem(IDC_WATCH_SUBTREE)->EnableWindow(able);
	GetDlgItem(IDC_WRITE_TO_LOG)->EnableWindow(able);
	GetDlgItem(IDC_VIEW_LOG)->EnableWindow(able);
}

void CDiskMonitorTabDlg::OnBnClickedStop()
{
	// TODO:  在此添加控件通知处理程序代码
	m_dskm->Stop();
	EnableWindowItems(TRUE);
}
