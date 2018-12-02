#pragma once
#include "afxeditbrowsectrl.h"
#include "afxwin.h"
#include "MyMFCShellListCtrl.h"

// CNewFileDlg 对话框

class CNewFileDlg : public CDialogEx
{
	DECLARE_DYNAMIC(CNewFileDlg)

public:
	CNewFileDlg(CWnd* pParent = NULL);   // 标准构造函数
	virtual ~CNewFileDlg();

// 对话框数据
	enum { IDD = IDD_DIALOG2 };

protected:
	virtual void DoDataExchange(CDataExchange* pDX);    // DDX/DDV 支持

	DECLARE_MESSAGE_MAP()
public:
	// 新建文件时得路径
	CMFCEditBrowseCtrl m_newfilePath;
	virtual BOOL OnInitDialog();
	CString path;
	CMyMFCShellListCtrl* m_wndShellList;
	void setPath(CString path, CMyMFCShellListCtrl* m_wndShellList1);
	CString getPath();
	// 创建文件得名字
	CEdit m_newfileName;
	afx_msg void OnBnClickedOk();
};
