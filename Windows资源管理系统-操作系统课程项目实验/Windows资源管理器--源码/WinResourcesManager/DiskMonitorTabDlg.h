#pragma once
#include "afxeditbrowsectrl.h"
#include "DiskMonitor.h"
#include "afxcmn.h"

// CDiskMonitorTabDlg 对话框

class CDiskMonitorTabDlg : public CDialogEx
{
	DECLARE_DYNAMIC(CDiskMonitorTabDlg)

public:
	CDiskMonitorTabDlg(CWnd* pParent = NULL);   // 标准构造函数
	virtual ~CDiskMonitorTabDlg();

// 对话框数据
	enum { IDD = IDD_DiskMonitor };

protected:
	virtual void DoDataExchange(CDataExchange* pDX);    // DDX/DDV 支持
	void GetNotifyFilter();
	void EnableWindowItems(BOOL able);
	DWORD m_dwNotifyFilter;
	DECLARE_MESSAGE_MAP()
public:
	// 监控路径
	CMFCEditBrowseCtrl m_monitorPath;
	virtual BOOL OnInitDialog();
	afx_msg void OnBnClickedOpenlog();
	afx_msg void OnBnClickedStart();
	afx_msg void OnBnClickedStop();
	//DiskMonitor m_dskm;
	DiskMonitor *m_dskm;
	char m_cPath[MAX_PATH];
	BOOL m_CreateDataChg;
	BOOL m_DirCDR;
	CListCtrl m_List;
	BOOL m_FileCDR;
	BOOL m_FileDirAttrChg;
	BOOL m_FileSizeChg;
	BOOL m_SaftyAttrChg;
	BOOL m_VisitDataChg;
	BOOL m_bWatchSubtree;
	BOOL m_WriteDataChg;
	BOOL m_bWriteToLog;
	CString absolutePath;
	void setPath(CString path1)
	{
		absolutePath = path1;
	}
	CString getPath()
	{
		return absolutePath;
	}
};
