#pragma once


// CMyMFCShellListCtrl

class CMyMFCShellListCtrl : public CMFCShellListCtrl
{
	DECLARE_DYNAMIC(CMyMFCShellListCtrl)

public:
	CMyMFCShellListCtrl();
	virtual ~CMyMFCShellListCtrl();
protected:
	DECLARE_MESSAGE_MAP()
public:
/*	afx_msg void OnLvnDeleteitem(NMHDR *pNMHDR, LRESULT *pResult);
	afx_msg void OnDropFiles(HDROP hDropInfo);
	afx_msg void OnDeleteitem(NMHDR* pNMHDR, LRESULT* pResult)
	{
		CMFCShellListCtrl::OnDeleteitem(pNMHDR, pResult);
		CMFCShellListCtrl::Refresh();
		MessageBox(L"OnDeleteitem");
	}
	afx_msg void OnContextMenu(CWnd* pWnd, CPoint point)
	{
		CMFCShellListCtrl::OnContextMenu(pWnd, point);
		MessageBox(L"OnContextMenu");
	}
*/
};


