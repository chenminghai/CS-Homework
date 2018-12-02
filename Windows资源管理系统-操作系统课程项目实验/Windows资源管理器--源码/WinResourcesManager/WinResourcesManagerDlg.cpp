
// WinResourcesManagerDlg.cpp : 实现文件
//

#include "stdafx.h"
#include "WinResourcesManager.h"
#include "WinResourcesManagerDlg.h"
#include "NewFileDlg.h"
#include "afxdialogex.h"
#include "ProcessManagerDlg.h"
#include<tlhelp32.h>
#include <afxwin.h>
#include <process.h>
#include "QuickSerachDlg.h"

#ifdef _DEBUG
#define new DEBUG_NEW
#endif


// 用于应用程序“关于”菜单项的 CAboutDlg 对话框

class CAboutDlg : public CDialogEx
{
public:
	CAboutDlg();

// 对话框数据
	enum { IDD = IDD_ABOUTBOX };

	protected:
	virtual void DoDataExchange(CDataExchange* pDX);    // DDX/DDV 支持

// 实现
protected:
	DECLARE_MESSAGE_MAP()
public:
//	afx_msg void OnSize(UINT nType, int cx, int cy);
};

CAboutDlg::CAboutDlg() : CDialogEx(CAboutDlg::IDD)
{
}

void CAboutDlg::DoDataExchange(CDataExchange* pDX)
{
	CDialogEx::DoDataExchange(pDX);
}

BEGIN_MESSAGE_MAP(CAboutDlg, CDialogEx)
	ON_WM_SIZE()
END_MESSAGE_MAP()


// CWinResourcesManagerDlg 对话框



CWinResourcesManagerDlg::CWinResourcesManagerDlg(CWnd* pParent /*=NULL*/)
	: CDialogEx(CWinResourcesManagerDlg::IDD, pParent)
{
	m_hIcon = AfxGetApp()->LoadIcon(IDR_MAINFRAME);
}

void CWinResourcesManagerDlg::DoDataExchange(CDataExchange* pDX)
{
	CDialogEx::DoDataExchange(pDX);
	DDX_Control(pDX, IDC_Folder, m_wbdShellTree);
	DDX_Control(pDX, IDC_File, m_wndShellList);
	DDX_Control(pDX, IDC_Edit, m_edit);
	DDX_Control(pDX, IDC_Path, m_absolutePath);
	DDX_Control(pDX, IDC_Search, m_QuickSearch);
}

BEGIN_MESSAGE_MAP(CWinResourcesManagerDlg, CDialogEx)
	ON_WM_SYSCOMMAND()
	ON_WM_PAINT()
	ON_WM_QUERYDRAGICON()
	ON_WM_SIZE()
	ON_BN_CLICKED(IDC_Process, &CWinResourcesManagerDlg::OnBnClickedProcess)
	ON_EN_KILLFOCUS(IDC_Edit, &CWinResourcesManagerDlg::OnEnKillfocusEdit)
	ON_BN_CLICKED(IDC_Rename, &CWinResourcesManagerDlg::OnBnClickedRename)
	ON_NOTIFY(NM_CLICK, IDC_File, &CWinResourcesManagerDlg::OnNMClickFile)
	ON_BN_CLICKED(IDC_Delete, &CWinResourcesManagerDlg::OnBnClickedDelete)
	ON_BN_CLICKED(IDC_Copy, &CWinResourcesManagerDlg::OnBnClickedCopy)
	ON_BN_CLICKED(IDC_Paste, &CWinResourcesManagerDlg::OnBnClickedPaste)
	ON_BN_CLICKED(IDC_Move, &CWinResourcesManagerDlg::OnBnClickedMove)
	ON_BN_CLICKED(IDC_New, &CWinResourcesManagerDlg::OnBnClickedNew)
	ON_NOTIFY(LVN_INSERTITEM, IDC_File, &CWinResourcesManagerDlg::OnLvnInsertitemFile)
	ON_BN_CLICKED(IDC_Quick, &CWinResourcesManagerDlg::OnBnClickedQuick)
	ON_BN_CLICKED(IDC_Jump, &CWinResourcesManagerDlg::OnBnClickedJump)
	ON_BN_CLICKED(IDC_BUTTON3, &CWinResourcesManagerDlg::OnBnClickedButton3)
END_MESSAGE_MAP()


// CWinResourcesManagerDlg 消息处理程序

BOOL CWinResourcesManagerDlg::OnInitDialog()
{
	CDialogEx::OnInitDialog();

	// 将“关于...”菜单项添加到系统菜单中。

	// IDM_ABOUTBOX 必须在系统命令范围内。
	ASSERT((IDM_ABOUTBOX & 0xFFF0) == IDM_ABOUTBOX);
	ASSERT(IDM_ABOUTBOX < 0xF000);

	CMenu* pSysMenu = GetSystemMenu(FALSE);
	if (pSysMenu != NULL)
	{
		BOOL bNameValid;
		CString strAboutMenu;
		bNameValid = strAboutMenu.LoadString(IDS_ABOUTBOX);
		ASSERT(bNameValid);
		if (!strAboutMenu.IsEmpty())
		{
			pSysMenu->AppendMenu(MF_SEPARATOR);
			pSysMenu->AppendMenu(MF_STRING, IDM_ABOUTBOX, strAboutMenu);
		}
	}

	// 设置此对话框的图标。  当应用程序主窗口不是对话框时，框架将自动
	//  执行此操作
	SetIcon(m_hIcon, TRUE);			// 设置大图标
	SetIcon(m_hIcon, FALSE);		// 设置小图标

	// TODO:  在此添加额外的初始化代码

	m_wbdShellTree.Expand(m_wbdShellTree.GetRootItem(), TVE_EXPAND);
	m_wbdShellTree.SetRelatedList(&m_wndShellList);

	LVCOLUMN lvColumn;
	lvColumn.mask = LVCF_TEXT | LVCF_FMT | LVCF_WIDTH;

	LPWSTR sName[4] = { _T("名称"), _T("大小"), _T("类型"), _T("修改日期") };

	int nWidth[4] = { 200, 100, 100, 200 };

	for (int i = 0; i<4; i++)

	{

		lvColumn.pszText = sName[i];

		lvColumn.cx = nWidth[i];

		lvColumn.fmt = LVCFMT_LEFT;

		m_wndShellList.SetColumn(i, &lvColumn);
		//m_wndShellList.EnableShellContextMenu(false);

	}
	m_edit.ShowWindow(SW_HIDE);

	GetClientRect(&m_rect);     //取客户区大小 
	Old.x = m_rect.right - m_rect.left;
	Old.y = m_rect.bottom - m_rect.top;

	m_wndShellList.GetCurrentFolder(path);
	m_absolutePath.SetWindowTextW(path);

	return TRUE;  // 除非将焦点设置到控件，否则返回 TRUE
}

void CWinResourcesManagerDlg::OnSysCommand(UINT nID, LPARAM lParam)
{
	if ((nID & 0xFFF0) == IDM_ABOUTBOX)
	{
		CAboutDlg dlgAbout;
		dlgAbout.DoModal();
	}
	else
	{
		CDialogEx::OnSysCommand(nID, lParam);
	}
}

// 如果向对话框添加最小化按钮，则需要下面的代码
//  来绘制该图标。  对于使用文档/视图模型的 MFC 应用程序，
//  这将由框架自动完成。

void CWinResourcesManagerDlg::OnPaint()
{
	if (IsIconic())
	{
		CPaintDC dc(this); // 用于绘制的设备上下文

		SendMessage(WM_ICONERASEBKGND, reinterpret_cast<WPARAM>(dc.GetSafeHdc()), 0);

		// 使图标在工作区矩形中居中
		int cxIcon = GetSystemMetrics(SM_CXICON);
		int cyIcon = GetSystemMetrics(SM_CYICON);
		CRect rect;
		GetClientRect(&rect);
		int x = (rect.Width() - cxIcon + 1) / 2;
		int y = (rect.Height() - cyIcon + 1) / 2;

		// 绘制图标
		dc.DrawIcon(x, y, m_hIcon);
	}
	else
	{
		//设置背景色为白色,自己改的
		CRect rect;
		CPaintDC dc(this);
		GetClientRect(rect);
		dc.FillSolidRect(rect,RGB(250,250,250));

		CDialogEx::OnPaint();
	}
}

//当用户拖动最小化窗口时系统调用此函数取得光标
//显示。
HCURSOR CWinResourcesManagerDlg::OnQueryDragIcon()
{
	return static_cast<HCURSOR>(m_hIcon);
}


// 屏蔽客户输入esc键或enter键退出程序
BOOL CWinResourcesManagerDlg::PreTranslateMessage(MSG* pMsg)
{
	// TODO: 在此添加专用代码和/或调用基类
	if (pMsg->message == WM_KEYDOWN && pMsg->wParam == VK_ESCAPE)// 屏蔽esc键
	{
		return TRUE;// 不作任何操作
	}
	if (pMsg->message == WM_KEYDOWN && pMsg->wParam == VK_RETURN)// 屏蔽enter键
	{
		return TRUE;// 不作任何处理
	}
	return CDialog::PreTranslateMessage(pMsg);
}

void CWinResourcesManagerDlg::OnSize(UINT nType, int cx, int cy)
{
	CDialogEx::OnSize(nType, cx, cy);

	// TODO:  在此处添加消息处理程序代码
	if (nType == SIZE_RESTORED || nType == SIZE_MAXIMIZED){
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

void CWinResourcesManagerDlg::OnBnClickedProcess()
{
	// TODO:  在此添加控件通知处理程序代码
	CString t;
	m_absolutePath.GetWindowTextW(t);

	CProcessManagerDlg *dlg = new CProcessManagerDlg;//非模态
	dlg->Create(IDD_TaskManagerDlg, GetDesktopWindow());    //第一个参数是对话框ID号
	dlg->ShowWindow(SW_SHOW);
	dlg->setPath(t);
	CDiskMonitorTabDlg *d = new CDiskMonitorTabDlg;
	d->setPath(t);
}

void CWinResourcesManagerDlg::OnNMClickFile(NMHDR *pNMHDR, LRESULT *pResult)
{
	LPNMITEMACTIVATE pNMItemActivate = reinterpret_cast<LPNMITEMACTIVATE>(pNMHDR);
	// TODO:  在此添加控件通知处理程序代码
	NM_LISTVIEW* pNMListView = (NM_LISTVIEW*)pNMHDR;
	m_Row = pNMListView->iItem;//获得选中的行
	m_Col = pNMListView->iSubItem;//获得选中列
	if (pNMListView->iItem != -1)
	{
		oldName = m_wndShellList.GetItemText(pNMListView->iItem, pNMListView->iSubItem);
		m_wndShellList.GetSubItemRect(m_Row, m_Col, LVIR_LABEL, rc);//获得子项的RECT； 
	}
	m_wndShellList.GetCurrentFolder(path);
	//m_absolutePath.SetWindowTextW(path);
	//MessageBox(oldName);
	*pResult = 0;
}

void CWinResourcesManagerDlg::OnBnClickedRename()
{
	// TODO:  在此添加控件通知处理程序代码
	m_edit.SetParent(&m_wndShellList);//转换坐标为列表框中的坐标
	m_edit.MoveWindow(rc);//移动Edit到RECT坐在的位置;
	m_edit.SetWindowText(m_wndShellList.GetItemText(m_Row, m_Col));//将该子项中的值放在Edit控件中；
	m_edit.ShowWindow(SW_SHOW);//显示Edit控件；
	m_edit.SetFocus();//设置Edit焦点
	m_edit.ShowCaret();//显示光标
	m_edit.SetSel(-1);//将光标移动到最后
}

void CWinResourcesManagerDlg::OnEnKillfocusEdit()
{
	// TODO:  在此添加控件通知处理程序代码
	m_edit.GetWindowText(newName);    //得到用户输入的新的内容
	if (PathFileExists(path + newName) && oldName != newName)
	{
		AfxMessageBox(_T("文件或目录重名"));
		m_wndShellList.SetItemText(m_Row, m_Col, oldName);   //设置编辑框的新内容
		m_edit.ShowWindow(SW_HIDE);
		//MessageBox(L"1");
	}
	else if (oldName == newName)
	{
		m_edit.ShowWindow(SW_HIDE);
		//MessageBox(L"2");
	}
	else
	{
		TCHAR szSrc[MAX_PATH] = { 0 };
		TCHAR szDesc[MAX_PATH] = { 0 };
		if (!PathIsRoot(path))
		{
			path=path + L"\\";
		}
		lstrcpy(szSrc, path+oldName);
		lstrcpy(szDesc, path + newName);
		//MessageBox(szSrc);
		//MessageBox(szDesc);
		SHFILEOPSTRUCT FileOp = { 0 };
		FileOp.fFlags = 0;
		FileOp.pFrom = szSrc;
		FileOp.pTo = szDesc;
		FileOp.wFunc = FO_RENAME;
		SHFileOperation(&FileOp);
		m_wndShellList.SetItemText(m_Row, m_Col, newName);   //设置编辑框的新内容
		//MessageBox(L"3");
		m_edit.ShowWindow(SW_HIDE);
	}
}


void CWinResourcesManagerDlg::OnBnClickedDelete()
{
	// TODO:  在此添加控件通知处理程序代码
	TCHAR szSrc[MAX_PATH] = { 0 };
	lstrcpy(szSrc, path +"\\"+ oldName);
	SHFILEOPSTRUCT FileOp;
	ZeroMemory((void*)&FileOp, sizeof(SHFILEOPSTRUCT));
	FileOp.fFlags = FOF_NOCONFIRMATION;
	FileOp.hNameMappings = NULL;
	FileOp.hwnd = NULL;
	FileOp.lpszProgressTitle = NULL;
	FileOp.pFrom = szSrc;
	FileOp.pTo = NULL;
	FileOp.wFunc = FO_DELETE;
	int i=SHFileOperation(&FileOp);
	if (i != 0)
	{
		CFile::Remove(szSrc);
		//MessageBox(L"成功");
	}
	m_wndShellList.Refresh();
}


void CWinResourcesManagerDlg::OnBnClickedCopy()
{
	// TODO:  在此添加控件通知处理程序代码
	m_absolutePath.GetWindowTextW(copyPath);
	copyName = oldName;
	if (PathIsRoot(copyPath))
	{
		copyPath = copyPath + copyName;
	}
	else
	{
		copyPath = copyPath + "\\" + copyName;
	}
	//MessageBox(copyPath);
}


void CWinResourcesManagerDlg::OnBnClickedPaste()
{
	// TODO:  在此添加控件通知处理程序代码
	
	CString realPath;
	m_absolutePath.GetWindowTextW(realPath);
	if (PathIsRoot(realPath))
	{
		realPath = realPath + copyName;
	}
	else
	{
		realPath = realPath + "\\" + copyName;
	}
	while (PathFileExists(realPath) && !PathIsDirectory(realPath))
	{
		int idx = realPath.ReverseFind('.'); //idx 的值为4;
		
		CString filename = realPath.Left(idx);
		CString suff = realPath.Mid(idx);
		realPath = filename + L"-副本" + suff;
	}
	if (PathIsDirectory(copyPath) && !PathIsDirectory(path + oldName))
	{
		realPath = path + copyName + L"-副本";
	}
	//MessageBox(copyPath);
	//MessageBox(realPath);
	
	TCHAR szSrc[MAX_PATH] = { 0 };
	TCHAR szDesc[MAX_PATH] = { 0 };
	lstrcpy(szSrc, copyPath);
	lstrcpy(szDesc, realPath);
	SHFILEOPSTRUCT FileOp = { 0 };
	ZeroMemory((void*)&FileOp, sizeof(SHFILEOPSTRUCT));
	FileOp.fFlags = FOF_NOCONFIRMATION;
	FileOp.hNameMappings = NULL;
	FileOp.hwnd = NULL;
	FileOp.lpszProgressTitle = NULL;
	FileOp.pFrom = szSrc;
	FileOp.pTo = szDesc;
	FileOp.wFunc = FO_COPY;
	SHFileOperation(&FileOp);
	m_wndShellList.Refresh();
}


void CWinResourcesManagerDlg::OnBnClickedMove()
{
	// TODO:  在此添加控件通知处理程序代码
	BROWSEINFO   bInfo;
	ZeroMemory(&bInfo, sizeof(bInfo));
	bInfo.hwndOwner = m_hWnd;
	TCHAR tchPath[255];
	bInfo.lpszTitle = _T("所选择路径:   ");
	bInfo.ulFlags = BIF_RETURNONLYFSDIRS;
	LPITEMIDLIST lpDlist; 	//用来保存返回信息的IDList，使用SHGetPathFromIDList函数转换为字符串 
	lpDlist = SHBrowseForFolder(&bInfo);   //显示选择对话框 
	if (lpDlist != NULL)
	{
		SHGetPathFromIDList(lpDlist, tchPath);//把项目标识列表转化成目录 
	}
	CString realPath1;
	CString realPath2;
	TCHAR szSrc[MAX_PATH] = { 0 };
	TCHAR szDesc[MAX_PATH] = { 0 };
	if (PathIsRoot(path))
	{
		realPath1 = path + oldName;
	}
	else
	{
		realPath1 = path + "\\" + oldName;
	}
	if (PathIsRoot(tchPath))
	{
		realPath2 = tchPath + oldName;
	}
	else
	{
		CString temp = L"\\" + oldName;
		realPath2 = tchPath + temp;
	}
	lstrcpy(szSrc, realPath1);
	lstrcpy(szDesc, realPath2);
	//MessageBox(szSrc);
	//MessageBox(szDesc);
	SHFILEOPSTRUCT FileOp;
	ZeroMemory((void*)&FileOp, sizeof(SHFILEOPSTRUCT));
	FileOp.fFlags = FOF_NOCONFIRMATION|FOF_NOCONFIRMMKDIR;;
	FileOp.hNameMappings = NULL;
	FileOp.hwnd = NULL;
	FileOp.lpszProgressTitle = NULL;
	FileOp.pFrom = szSrc;
	FileOp.pTo = szDesc;
	FileOp.wFunc = FO_MOVE;
	int i=SHFileOperation(&FileOp);
	if (i != 0)
	{
		MessageBox(L"移动失败！");
	}
	m_wndShellList.Refresh();
}

void CWinResourcesManagerDlg::OnBnClickedNew()
{
	// TODO:  在此添加控件通知处理程序代码
	CNewFileDlg dlg(this);
	dlg.setPath(path, &m_wndShellList);
	// 显示模式对话框
	dlg.DoModal();
}

void CWinResourcesManagerDlg::OnLvnInsertitemFile(NMHDR *pNMHDR, LRESULT *pResult)
{
	LPNMLISTVIEW pNMLV = reinterpret_cast<LPNMLISTVIEW>(pNMHDR);
	// TODO:  在此添加控件通知处理程序代码
	m_wndShellList.GetCurrentFolder(path);
	m_absolutePath.SetWindowTextW(path);
	*pResult = 0;
}


UINT FindFileThreadFunc(LPVOID pParam)     //工作函数
{
	CWinResourcesManagerDlg* p = (CWinResourcesManagerDlg*)pParam;
	p->FindFile();
	return 0;
}

CQuickSerachDlg *dlg1;

void CWinResourcesManagerDlg::OnBnClickedQuick()   //启动线程事件
{
	// TODO:  在此添加控件通知处理程序代码
	CQuickSerachDlg *dlg = new CQuickSerachDlg;
	dlg->Create(IDD_QuickSearchDlg, GetDesktopWindow());    //第一个参数是对话框ID号
	dlg->ShowWindow(SW_SHOW);
	dlg1 = dlg;
	m_absolutePath.GetWindowTextW(m_strPath);
	m_QuickSearch.GetWindowTextW(m_strFileName);
	//m_wndShellList.DeleteAllItems();
	CWinThread *pThread = AfxBeginThread(FindFileThreadFunc, this, THREAD_PRIORITY_NORMAL,0,0);
	ASSERT_VALID(pThread);
	pThread->ResumeThread();
}

void CWinResourcesManagerDlg::FindFile()   //搜索文件函数
{
	stop = FALSE;
	if (m_strPath.IsEmpty() || m_strFileName.IsEmpty())
	{
		return;
	}
	FindFiles(m_strPath, m_strFileName);
	stop = FALSE;
}

void CWinResourcesManagerDlg::FindFiles(CString m_strPath, CString m_strFileName)  //递归函数
{
	if (m_strPath.Right(1) != _T("\\"))
	{
		m_strPath += "\\";
	}
	m_strPath += _T("*.*");
	CFileFind file;
	BOOL bContinue = file.FindFile(m_strPath);
	while (bContinue)
	{
		bContinue = file.FindNextFile();  //查找下一个文件
		if (file.IsDirectory() && !file.IsDots())  //目录
		{
			FindFiles(file.GetFilePath(), m_strFileName);
		}
		else if (!file.IsDots() && !file.IsDirectory())  //文件
		{
			if (file.GetFileName().Find(m_strFileName)!=-1)
			{	//插入数据
				int index = dlg1->m_searchResult.GetItemCount();
				dlg1->m_searchResult.InsertItem(index, file.GetFileName());
				dlg1->m_searchResult.SetItemText(index, 1, file.GetFilePath());
			}
		}
	}
}

void CWinResourcesManagerDlg::OnBnClickedJump()
{
	// TODO:  在此添加控件通知处理程序代码
	CString path;
	m_absolutePath.GetWindowTextW(path);
	if (!PathFileExists(path))
	{
		AfxMessageBox(_T("该路径不存在"));
		return;
	}
	m_wndShellList.DisplayFolder(path);
}


void CWinResourcesManagerDlg::OnBnClickedButton3()
{
	// TODO:  在此添加控件通知处理程序代码
	m_wndShellList.Refresh();
}
