// QuickSerachDlg.cpp : 实现文件
//

#include "stdafx.h"
#include "WinResourcesManager.h"
#include "QuickSerachDlg.h"
#include "afxdialogex.h"


// CQuickSerachDlg 对话框

IMPLEMENT_DYNAMIC(CQuickSerachDlg, CDialogEx)

CQuickSerachDlg::CQuickSerachDlg(CWnd* pParent /*=NULL*/)
	: CDialogEx(CQuickSerachDlg::IDD, pParent)
{

}

CQuickSerachDlg::~CQuickSerachDlg()
{
}

void CQuickSerachDlg::DoDataExchange(CDataExchange* pDX)
{
	CDialogEx::DoDataExchange(pDX);
	DDX_Control(pDX, IDC_LIST1, m_searchResult);
}


BEGIN_MESSAGE_MAP(CQuickSerachDlg, CDialogEx)
	ON_NOTIFY(NM_DBLCLK, IDC_LIST1, &CQuickSerachDlg::OnNMDblclkList1)
	ON_WM_SIZE()
END_MESSAGE_MAP()


// CQuickSerachDlg 消息处理程序

BOOL CQuickSerachDlg::OnInitDialog()
{
	CDialogEx::OnInitDialog();

	// TODO:  在此添加额外的初始化
	m_searchResult.SetExtendedStyle(m_searchResult.GetExtendedStyle() | LVS_EX_FLATSB | LVS_EX_HEADERDRAGDROP | LVS_EX_FULLROWSELECT | LVS_EX_GRIDLINES);
	m_searchResult.InsertColumn(0, _T("名称"), LVCFMT_LEFT, 150);
	m_searchResult.InsertColumn(1, _T("文件路径"), LVCFMT_LEFT, 400);
	m_searchResult.InsertColumn(2, _T("类型"), LVCFMT_LEFT, 80);
	m_searchResult.InsertColumn(3, _T("大小"), LVCFMT_LEFT, 50);

	GetClientRect(&m_rect);     //取客户区大小 
	Old.x = m_rect.right - m_rect.left;
	Old.y = m_rect.bottom - m_rect.top;

	return TRUE;  // return TRUE unless you set the focus to a control
	// 异常:  OCX 属性页应返回 FALSE
}

void CQuickSerachDlg::OnNMDblclkList1(NMHDR *pNMHDR, LRESULT *pResult)
{
	LPNMITEMACTIVATE pNMItemActivate = reinterpret_cast<LPNMITEMACTIVATE>(pNMHDR);
	// TODO:  在此添加控件通知处理程序代码
	CString strPath;
	NMLISTVIEW *pNMListView = (NMLISTVIEW*)pNMHDR;
	if (-1 != pNMListView->iItem)        // 如果iItem不是-1，就说明有列表项被选择   
	{
		// 获取被选择列表项第2个子项的文本   
		strPath = m_searchResult.GetItemText(pNMListView->iItem, 1);
	}
	ShellExecute(NULL, NULL, _T("explorer"), strPath, NULL, SW_SHOW);
	*pResult = 0;
}


void CQuickSerachDlg::OnSize(UINT nType, int cx, int cy)
{
	CDialogEx::OnSize(nType, cx, cy);

	// TODO:  在此处添加消息处理程序代码
	if (nType == SIZE_RESTORED || nType == SIZE_MAXIMIZED)
	{
		float fsp[2];
		POINT Newp; //获取现在对话框的大小
		CRect recta;
		GetClientRect(&recta);     //取客户区大小  
		Newp.x = recta.right - recta.left;
		Newp.y = recta.bottom - recta.top;
		fsp[0] = (float)Newp.x / Old.x;
		fsp[1] = (float)Newp.y / Old.y;
		CRect Rect;
		int woc;
		CPoint OldTLPoint, TLPoint; //左上角
		CPoint OldBRPoint, BRPoint; //右下角
		HWND  hwndChild = ::GetWindow(m_hWnd, GW_CHILD);  //列出所有控件  
		while (hwndChild)
		{
			woc = ::GetDlgCtrlID(hwndChild);//取得ID
			GetDlgItem(woc)->GetWindowRect(Rect);
			ScreenToClient(Rect);
			OldTLPoint = Rect.TopLeft();
			TLPoint.x = long(OldTLPoint.x*fsp[0]);
			TLPoint.y = long(OldTLPoint.y*fsp[1]);
			OldBRPoint = Rect.BottomRight();
			BRPoint.x = long(OldBRPoint.x *fsp[0]);
			BRPoint.y = long(OldBRPoint.y *fsp[1]);
			Rect.SetRect(TLPoint, BRPoint);
			GetDlgItem(woc)->MoveWindow(Rect, TRUE);
			hwndChild = ::GetWindow(hwndChild, GW_HWNDNEXT);
		}
		Old = Newp;
	}
}
