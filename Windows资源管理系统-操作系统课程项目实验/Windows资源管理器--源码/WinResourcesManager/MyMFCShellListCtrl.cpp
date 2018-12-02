// MyMFCShellListCtrl.cpp : 实现文件
//

#include "stdafx.h"
#include "WinResourcesManager.h"
#include "MyMFCShellListCtrl.h"


// CMyMFCShellListCtrl

IMPLEMENT_DYNAMIC(CMyMFCShellListCtrl, CMFCShellListCtrl)

CMyMFCShellListCtrl::CMyMFCShellListCtrl()
{

}

CMyMFCShellListCtrl::~CMyMFCShellListCtrl()
{
}


BEGIN_MESSAGE_MAP(CMyMFCShellListCtrl, CMFCShellListCtrl)
//	ON_NOTIFY_REFLECT(LVN_DELETEITEM, &CMyMFCShellListCtrl::OnLvnDeleteitem)
//	ON_WM_DROPFILES()
END_MESSAGE_MAP()



// CMyMFCShellListCtrl 消息处理程序