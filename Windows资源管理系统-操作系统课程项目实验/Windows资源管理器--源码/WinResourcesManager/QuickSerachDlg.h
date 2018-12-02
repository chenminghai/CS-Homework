#pragma once
#include "afxcmn.h"


// CQuickSerachDlg 对话框

class CQuickSerachDlg : public CDialogEx
{
	DECLARE_DYNAMIC(CQuickSerachDlg)

public:
	CQuickSerachDlg(CWnd* pParent = NULL);   // 标准构造函数
	virtual ~CQuickSerachDlg();

// 对话框数据
	enum { IDD = IDD_QuickSearchDlg };

protected:
	virtual void DoDataExchange(CDataExchange* pDX);    // DDX/DDV 支持

	DECLARE_MESSAGE_MAP()
public:
	CListCtrl m_searchResult;
	virtual BOOL OnInitDialog();
	afx_msg void OnNMDblclkList1(NMHDR *pNMHDR, LRESULT *pResult);
	afx_msg void OnSize(UINT nType, int cx, int cy);
	CRect m_rect;//获取最开始的窗口大小
	POINT Old;
};
