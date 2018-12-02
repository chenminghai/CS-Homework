// ProcessTabDlg.cpp : 实现文件
//

#include "stdafx.h"
#include "WinResourcesManager.h"
#include "ProcessTabDlg.h"
#include "afxdialogex.h"
#include <tlhelp32.h>
#include <Windows.h>
#include <Psapi.h>

// CProcessTabDlg 对话框

IMPLEMENT_DYNAMIC(CProcessTabDlg, CDialogEx)

CProcessTabDlg::CProcessTabDlg(CWnd* pParent /*=NULL*/)
	: CDialogEx(CProcessTabDlg::IDD, pParent)
{

}

CProcessTabDlg::~CProcessTabDlg()
{
}

void CProcessTabDlg::DoDataExchange(CDataExchange* pDX)
{
	CDialogEx::DoDataExchange(pDX);
	DDX_Control(pDX, IDC_ProcessList, m_procList);
}


BEGIN_MESSAGE_MAP(CProcessTabDlg, CDialogEx)
END_MESSAGE_MAP()


// CProcessTabDlg 消息处理程序


BOOL CProcessTabDlg::OnInitDialog()
{
	CDialogEx::OnInitDialog();

	// TODO:  在此添加额外的初始化
	m_procList.SetExtendedStyle(LVS_EX_GRIDLINES);
	m_procList.InsertColumn(0, _T("进程名称"), LVCFMT_LEFT, 210, 0);
	m_procList.InsertColumn(1, _T("进程ID"), LVCFMT_LEFT, 60, 0);
	m_procList.InsertColumn(2, _T("进程状态"), LVCFMT_LEFT, 80, 0);
	m_procList.InsertColumn(3, _T("描述"), LVCFMT_LEFT, 180, 0);

	getProcessList();

	return TRUE;  // return TRUE unless you set the focus to a control
	// 异常:  OCX 属性页应返回 FALSE
}

BOOL CProcessTabDlg::getProcessList()
{
	//获取系统进程快照
	PROCESSENTRY32 pe;
	pe.dwSize = sizeof(PROCESSENTRY32);
	HANDLE hProcessSnap = ::CreateToolhelp32Snapshot(TH32CS_SNAPPROCESS, 0);
	if (hProcessSnap == INVALID_HANDLE_VALUE)
	{
		MessageBox(L"CreateToolhelp32Snapshot调用失败!\n");
		return -1;
	}
	//遍历进程快照。轮流显示每个进程的信息  
	CString strID;
	TCHAR  szFilePath[MAX_PATH];
	BOOL bMore = ::Process32First(hProcessSnap, &pe);
	while (bMore)
	{
		HANDLE hProcess = OpenProcess(PROCESS_QUERY_INFORMATION | PROCESS_VM_READ, FALSE, pe.th32ProcessID);
		::GetModuleFileNameEx(hProcess, NULL, szFilePath, MAX_PATH);
		m_procList.InsertItem(0, _T(""));
		m_procList.SetItemText(0, 0, pe.szExeFile);
		strID.Format(L"%d", pe.th32ProcessID);
		m_procList.SetItemText(0, 1, strID);
		m_procList.SetItemText(0, 2, L"正在运行");
		m_procList.SetItemText(0, 3, szFilePath);
		bMore = ::Process32Next(hProcessSnap, &pe);
		::CloseHandle(hProcess);
	}
	// 删除快照
	::CloseHandle(hProcessSnap);
	return TRUE;
}