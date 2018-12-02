#pragma once
#include "afxcmn.h"
#include "ThreadTabDlg.h"
#include "ProcessTabDlg.h"
#include "DiskMonitorTabDlg.h"
// CProcessManagerDlg 对话框

class CProcessManagerDlg : public CDialogEx
{
	DECLARE_DYNAMIC(CProcessManagerDlg)

public:
	CProcessManagerDlg(CWnd* pParent = NULL);   // 标准构造函数
	virtual ~CProcessManagerDlg();

// 对话框数据
	enum { IDD = IDD_DIALOG1 };

protected:
	virtual void DoDataExchange(CDataExchange* pDX);    // DDX/DDV 支持

	DECLARE_MESSAGE_MAP()
public:
	CTabCtrl m_tab;  //CListCtrl m_procList;
	CThreadTabDlg m_ThreadTab;
	CProcessTabDlg m_ProcessTab;
	CDiskMonitorTabDlg m_DiskMonitorTab;
	virtual BOOL OnInitDialog();
	afx_msg void OnTcnSelchangeTab2(NMHDR *pNMHDR, LRESULT *pResult);
	afx_msg void OnBnClickedEnd();
	CString path;
	void setPath(CString p) {  path = p; }
	CString getPath() { return path; }
	bool isShow;
};
