#pragma once
#include "afxcmn.h"


// CThreadTabDlg 对话框

class CThreadTabDlg : public CDialogEx
{
	DECLARE_DYNAMIC(CThreadTabDlg)

public:
	CThreadTabDlg(CWnd* pParent = NULL);   // 标准构造函数
	virtual ~CThreadTabDlg();

// 对话框数据
	enum { IDD = IDD_Thread };

protected:
	virtual void DoDataExchange(CDataExchange* pDX);    // DDX/DDV 支持

	DECLARE_MESSAGE_MAP()
public:
	virtual BOOL OnInitDialog();
	CListCtrl m_threadList;
	BOOL getThreadList();
};
