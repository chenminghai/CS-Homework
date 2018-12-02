// ThreadTabDlg.cpp : 实现文件
//

#include "stdafx.h"
#include "WinResourcesManager.h"
#include "ThreadTabDlg.h"
#include "afxdialogex.h"
#include <tlhelp32.h>
#include <Windows.h>
#include <Psapi.h>

// CThreadTabDlg 对话框

IMPLEMENT_DYNAMIC(CThreadTabDlg, CDialogEx)

CThreadTabDlg::CThreadTabDlg(CWnd* pParent /*=NULL*/)
	: CDialogEx(CThreadTabDlg::IDD, pParent)
{

}

CThreadTabDlg::~CThreadTabDlg()
{
}

void CThreadTabDlg::DoDataExchange(CDataExchange* pDX)
{
	CDialogEx::DoDataExchange(pDX);
	DDX_Control(pDX, IDC_ThreadList, m_threadList);
}


BEGIN_MESSAGE_MAP(CThreadTabDlg, CDialogEx)
	ON_WM_SIZE()
END_MESSAGE_MAP()


// CThreadTabDlg 消息处理程序


BOOL CThreadTabDlg::OnInitDialog()
{
	CDialogEx::OnInitDialog();

	// TODO:  在此添加额外的初始化
	m_threadList.SetExtendedStyle(LVS_EX_GRIDLINES);
	m_threadList.InsertColumn(0, _T("线程ID"), LVCFMT_LEFT, 60, 0);
	m_threadList.InsertColumn(1, _T("线程类别"), LVCFMT_LEFT, 80, 0);
	m_threadList.InsertColumn(2, _T("所属进程"), LVCFMT_LEFT, 180, 0);
	m_threadList.InsertColumn(3, _T("进程路径"), LVCFMT_LEFT, 180, 0);

	//getThreadList();

	return TRUE;  // return TRUE unless you set the focus to a control
	// 异常:  OCX 属性页应返回 FALSE
}

BOOL CThreadTabDlg::getThreadList()
{
	//获取系统进程快照
	HANDLE hProcessSnap = ::CreateToolhelp32Snapshot(TH32CS_SNAPPROCESS, 0);  // 获取系统进程列表
	HANDLE hSnapshot = CreateToolhelp32Snapshot(TH32CS_SNAPPROCESS, 0);    // 获取为了获取线程列表
	PROCESSENTRY32 pe;      //进程信息
	pe.dwSize = sizeof(PROCESSENTRY32);
	THREADENTRY32 te;       // 线程信息
	te.dwSize = sizeof(THREADENTRY32);
	if (hProcessSnap == INVALID_HANDLE_VALUE || hSnapshot == INVALID_HANDLE_VALUE)
	{
		MessageBox(L"CreateToolhelp32Snapshot调用失败!\n");
		return FALSE;
	}
	DWORD idThread = 0;
	DWORD idProcess = 0;
	TCHAR  szFilePath[MAX_PATH];
	CString strID;
	BOOL bMore = ::Process32First(hProcessSnap, &pe);
	//遍历进程快照
	while (bMore)
	{
		HANDLE hProcess = OpenProcess(PROCESS_QUERY_INFORMATION | PROCESS_VM_READ, FALSE, pe.th32ProcessID);
		::K32GetModuleFileNameExW(hProcess, NULL, szFilePath, MAX_PATH);
		// 获取进程的主线程ID
		idProcess = pe.th32ProcessID;
		hSnapshot = CreateToolhelp32Snapshot(TH32CS_SNAPTHREAD, 0);  // 系统所有线程快照
		if (Thread32First(hSnapshot, &te))       // 第一个线程
		{
			do
			{
				if (idProcess == te.th32OwnerProcessID)  // 认为找到的第一个该进程的线程为主线程
				{
					idThread = te.th32ThreadID;
					m_threadList.InsertItem(0, _T(""));
					strID.Format(L"%d", idThread);
					m_threadList.SetItemText(0, 0, strID);
					m_threadList.SetItemText(0, 1, L"主进程");
					m_threadList.SetItemText(0, 2, pe.szExeFile);
					m_threadList.SetItemText(0, 3, szFilePath);
					break;
				}
			} while (Thread32Next(hSnapshot, &te));  // 下一个线程
		}
		bMore = ::Process32Next(hProcessSnap, &pe);  // 下一个进程
		::CloseHandle(hProcess);
	}
	// 删除快照  
	::CloseHandle(hProcessSnap);
	::CloseHandle(hSnapshot);
	return TRUE;
}