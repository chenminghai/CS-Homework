#pragma once
#include "afxcmn.h"

// CProcessTabDlg 对话框

class CProcessTabDlg : public CDialogEx
{
	DECLARE_DYNAMIC(CProcessTabDlg)

public:
	CProcessTabDlg(CWnd* pParent = NULL);   // 标准构造函数
	virtual ~CProcessTabDlg();

// 对话框数据
	enum { IDD = IDD_Process };

protected:
	virtual void DoDataExchange(CDataExchange* pDX);    // DDX/DDV 支持

	DECLARE_MESSAGE_MAP()
public:
	virtual BOOL OnInitDialog();
	CListCtrl m_procList;
	BOOL getProcessList();
};
