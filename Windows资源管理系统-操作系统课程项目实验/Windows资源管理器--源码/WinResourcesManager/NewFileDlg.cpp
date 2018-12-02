// NewFileDlg.cpp : 实现文件
//

#include "stdafx.h"
#include "WinResourcesManager.h"
#include "NewFileDlg.h"
#include "afxdialogex.h"
#include "WinResourcesManagerDlg.h"
// CNewFileDlg 对话框

IMPLEMENT_DYNAMIC(CNewFileDlg, CDialogEx)

CNewFileDlg::CNewFileDlg(CWnd* pParent /*=NULL*/)
	: CDialogEx(CNewFileDlg::IDD, pParent)
{

}

CNewFileDlg::~CNewFileDlg()
{
}

void CNewFileDlg::DoDataExchange(CDataExchange* pDX)
{
	CDialogEx::DoDataExchange(pDX);
	DDX_Control(pDX, IDC_MFCEDITBROWSE1, m_newfilePath);
	DDX_Control(pDX, IDC_IDC_NewFileName, m_newfileName);
}


BEGIN_MESSAGE_MAP(CNewFileDlg, CDialogEx)
	ON_BN_CLICKED(IDOK, &CNewFileDlg::OnBnClickedOk)
END_MESSAGE_MAP()


// CNewFileDlg 消息处理程序


BOOL CNewFileDlg::OnInitDialog()
{
	CDialogEx::OnInitDialog();

	// TODO:  在此添加额外的初始化
	m_newfilePath.SetWindowTextW(getPath());
	m_newfilePath.SetSel(-1);
	return TRUE;  // return TRUE unless you set the focus to a control
	// 异常:  OCX 属性页应返回 FALSE
}
void CNewFileDlg::setPath(CString path1, CMyMFCShellListCtrl* m_wndShellList1)
{
	path=path1;
	m_wndShellList = m_wndShellList1;
}

CString CNewFileDlg::getPath()
{
	return path;
}

void CNewFileDlg::OnBnClickedOk()
{
	// TODO:  在此添加控件通知处理程序代码
	CString newFilePath;
	CString newFileName;
	m_newfilePath.GetWindowTextW(newFilePath);
	m_newfileName.GetWindowTextW(newFileName);
	//MessageBox(newFilePath);
	//MessageBox(newFileName);
	if (newFilePath == "" || newFileName == "")
	{
		MessageBox(L"文件名或文件路径不能为空");
		return;
	}
	CString path = newFilePath + "\\" + newFileName;

	if (newFileName.FindOneOf(_T("/:*?<>|"))!=-1)
	{
		MessageBox(L"该文件名不能含“ / : * ? < > |”");
		MessageBox(newFileName);
	}
	else if (!PathFileExists(path))
	{
		if (newFileName.ReverseFind('.')!=-1)  // + 4 >= newFileName.GetLength()
		{
			int index = newFileName.ReverseFind('\\');
			if (index != -1)
			{
				CString t=newFileName.Left(index);
				::SHCreateDirectoryExW(NULL, newFilePath + t, NULL);
			}
			CFile m_sFile;
			m_sFile.Open(path, CFile::modeCreate | CFile::modeNoTruncate | CFile::modeReadWrite | CFile::shareDenyWrite);
			m_sFile.SeekToEnd();
			m_sFile.Close();
		}
		else
		{ 
			::SHCreateDirectoryExW(NULL, path, NULL);   		//CreateDirectory(path, 0);//不存在则创建
		}
		m_wndShellList->Refresh();
		CDialogEx::OnOK();
	}
	else
	{
		MessageBox(L"该文件或文件夹已存在");
	}
	
}
