// ProcessManagerDlg.cpp : 实现文件
//

#include "stdafx.h"
#include "WinResourcesManager.h"
#include "ProcessManagerDlg.h"
#include "afxdialogex.h"
#include "ProcessTabDlg.h"
// CProcessManagerDlg 对话框

IMPLEMENT_DYNAMIC(CProcessManagerDlg, CDialogEx)

CProcessManagerDlg::CProcessManagerDlg(CWnd* pParent /*=NULL*/)
	: CDialogEx(CProcessManagerDlg::IDD, pParent)
{

}

CProcessManagerDlg::~CProcessManagerDlg()
{
}

void CProcessManagerDlg::DoDataExchange(CDataExchange* pDX)
{
	CDialogEx::DoDataExchange(pDX);
	DDX_Control(pDX, IDC_TAB2, m_tab);
}


BEGIN_MESSAGE_MAP(CProcessManagerDlg, CDialogEx)
	ON_NOTIFY(TCN_SELCHANGE, IDC_TAB2, &CProcessManagerDlg::OnTcnSelchangeTab2)
	ON_BN_CLICKED(IDC_End, &CProcessManagerDlg::OnBnClickedEnd)
//	ON_WM_SIZE()
END_MESSAGE_MAP()


// CProcessManagerDlg 消息处理程序


BOOL CProcessManagerDlg::OnInitDialog()
{
	CDialogEx::OnInitDialog();

	// TODO:  在此添加额外的初始化
	m_tab.InsertItem(0, _T("进程列表"));
	m_tab.InsertItem(1, _T("线程列表"));
	m_tab.InsertItem(3, _T("文件监控"));
	m_ThreadTab.Create(IDD_Thread, &m_tab);
	m_ProcessTab.Create(IDD_Process, &m_tab);//IDC_MonitorList,
	m_DiskMonitorTab.Create(IDD_DiskMonitor, &m_tab);
	//获取m_tab控件的大小。
	CRect rec;
	m_tab.GetClientRect(&rec);
	//将m_tab控件的大小适当改小。逻辑坐标默认向下为正，向右为正
	rec.top += 22;  //去掉选项卡标签页那一块空间
	rec.bottom -= 4;
	rec.left += 4;
	rec.right -= 4;
	//改变选项卡窗口的位置和大小
	m_ProcessTab.MoveWindow(&rec);
	m_ThreadTab.MoveWindow(&rec);
	m_DiskMonitorTab.MoveWindow(&rec);
	m_ProcessTab.ShowWindow(TRUE);
	m_ThreadTab.ShowWindow(FALSE);
	m_DiskMonitorTab.ShowWindow(FALSE);
	m_tab.SetCurSel(0); //默认显示第一个选项卡

	isShow = true;
	return TRUE;  // return TRUE unless you set the focus to a control
	// 异常:  OCX 属性页应返回 FALSE
}


void CProcessManagerDlg::OnTcnSelchangeTab2(NMHDR *pNMHDR, LRESULT *pResult)
{
	// TODO:  在此添加控件通知处理程序代码
	*pResult = 0;
	int CurSel;
	CurSel = m_tab.GetCurSel(); //获取点击操作的第几张表
	CString temp = getPath();
	switch (CurSel)
	{
	case 0: //点击第一张表
		m_ProcessTab.ShowWindow(TRUE);
		m_ThreadTab.ShowWindow(FALSE);
		m_DiskMonitorTab.ShowWindow(FALSE);
		//m_ProcessTab.getProcessList();
		break;
	case 1://点击第二张表
		m_ProcessTab.ShowWindow(FALSE);
		m_ThreadTab.ShowWindow(TRUE);
		m_DiskMonitorTab.ShowWindow(FALSE);
		if (isShow)
		{
			m_ThreadTab.getThreadList();
			isShow = false;
		}
		break;
	case 2://点击第三张表
		m_ProcessTab.ShowWindow(FALSE);
		m_ThreadTab.ShowWindow(FALSE);
		m_DiskMonitorTab.ShowWindow(TRUE);
		m_DiskMonitorTab.m_monitorPath.SetWindowTextW(temp);
		break;
	default:;
	}
}


void CProcessManagerDlg::OnBnClickedEnd()
{
	// TODO: Add your control notification handler code here
	CString strPID;
	for (int i = 0; i<m_ProcessTab.m_procList.GetItemCount(); i++)//遍历整个列表视图
	{
		if (m_ProcessTab.m_procList.GetItemState(i, LVIS_SELECTED) == LVIS_SELECTED)          //获取选中行
		{
			strPID = m_ProcessTab.m_procList.GetItemText(i, 1);//获得被选中的那个一行的第二个数据PID
			UpdateData(false);
		}
	}
	DWORD a = _ttoi(strPID);
	HANDLE hProcess;
	// 打开进程
	hProcess = OpenProcess(PROCESS_TERMINATE, FALSE, a);
	if (hProcess)
	{
		if (!TerminateProcess(hProcess, 0))
		{
			CString strError;
			strError.Format(L"错误号:%d", GetLastError());
			AfxMessageBox(strError, MB_OK | MB_ICONINFORMATION, NULL);
		}
	}
	else
	{
		CString strError;
		strError.Format(L"错误号:%d", GetLastError());
		if (GetLastError() == ERROR_ACCESS_DENIED)
			strError = _T("拒绝访问!") + strError;
		AfxMessageBox(strError, MB_OK | MB_ICONINFORMATION, NULL);
	}
	Sleep(300);
}
