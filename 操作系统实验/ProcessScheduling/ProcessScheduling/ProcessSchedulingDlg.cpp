
// ProcessSchedulingDlg.cpp : 实现文件
//

#include "stdafx.h"
#include "ProcessScheduling.h"
#include "ProcessSchedulingDlg.h"
#include "afxdialogex.h"
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
//	virtual BOOL PreTranslateMessage(MSG* pMsg);
//	virtual BOOL OnCommand(WPARAM wParam, LPARAM lParam);
};

CAboutDlg::CAboutDlg() : CDialogEx(CAboutDlg::IDD)
{
}

void CAboutDlg::DoDataExchange(CDataExchange* pDX)
{
	CDialogEx::DoDataExchange(pDX);
}

BEGIN_MESSAGE_MAP(CAboutDlg, CDialogEx)
END_MESSAGE_MAP()


// CProcessSchedulingDlg 对话框



CProcessSchedulingDlg::CProcessSchedulingDlg(CWnd* pParent /*=NULL*/)
	: CDialogEx(CProcessSchedulingDlg::IDD, pParent)
{
	m_hIcon = AfxGetApp()->LoadIcon(IDR_MAINFRAME);
}

void CProcessSchedulingDlg::DoDataExchange(CDataExchange* pDX)
{
	CDialogEx::DoDataExchange(pDX);
	DDX_Control(pDX, IDC_RUN, m_run);
	DDX_Control(pDX, IDC_READY, m_ready);
	DDX_Control(pDX, IDC_FINISH, m_finish);
}

BEGIN_MESSAGE_MAP(CProcessSchedulingDlg, CDialogEx)
	ON_WM_SYSCOMMAND()
	ON_WM_PAINT()
	ON_WM_QUERYDRAGICON()
	ON_MESSAGE(WM_HOTKEY,OnHotKey)
	ON_WM_TIMER()
END_MESSAGE_MAP()


// CProcessSchedulingDlg 消息处理程序

BOOL CProcessSchedulingDlg::OnInitDialog()
{
	CDialogEx::OnInitDialog();

	m_run.SetExtendedStyle(m_run.GetExtendedStyle() | LVS_EX_FLATSB | LVS_EX_HEADERDRAGDROP | LVS_EX_FULLROWSELECT | LVS_EX_GRIDLINES);

	m_run.InsertColumn(0, _T("进程名称"), LVCFMT_LEFT, 130);
	m_run.InsertColumn(1, _T("进程优先级"), LVCFMT_LEFT, 130);
	m_run.InsertColumn(2, _T("进程生命周期"), LVCFMT_LEFT, 130);
	m_run.InsertColumn(3, _T("进程状态"), LVCFMT_LEFT, 129);
	
	m_ready.SetExtendedStyle(m_ready.GetExtendedStyle() | LVS_EX_FLATSB | LVS_EX_HEADERDRAGDROP | LVS_EX_FULLROWSELECT | LVS_EX_GRIDLINES);

	m_ready.InsertColumn(0, _T("进程名称"), LVCFMT_LEFT, 130);
	m_ready.InsertColumn(1, _T("进程优先级"), LVCFMT_LEFT, 130);
	m_ready.InsertColumn(2, _T("进程生命周期"), LVCFMT_LEFT, 130);
	m_ready.InsertColumn(3, _T("进程状态"), LVCFMT_LEFT, 129);

	m_finish.SetExtendedStyle(m_ready.GetExtendedStyle() | LVS_EX_FLATSB | LVS_EX_HEADERDRAGDROP | LVS_EX_FULLROWSELECT | LVS_EX_GRIDLINES);

	m_finish.InsertColumn(0, _T("进程名称"), LVCFMT_LEFT, 130);
	m_finish.InsertColumn(1, _T("进程优先级"), LVCFMT_LEFT, 130);
	m_finish.InsertColumn(2, _T("进程生命周期"), LVCFMT_LEFT, 130);
	m_finish.InsertColumn(3, _T("进程状态"), LVCFMT_LEFT, 129);

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
	::RegisterHotKey(m_hWnd,ID_Create,MOD_CONTROL,'F');
	::RegisterHotKey(m_hWnd, ID_Quit, MOD_CONTROL, 'Q');
	return TRUE;  // 除非将焦点设置到控件，否则返回 TRUE
}

void CProcessSchedulingDlg::OnSysCommand(UINT nID, LPARAM lParam)
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

void CProcessSchedulingDlg::OnPaint()
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
		CDialogEx::OnPaint();
	}
}

//当用户拖动最小化窗口时系统调用此函数取得光标
//显示。
HCURSOR CProcessSchedulingDlg::OnQueryDragIcon()
{
	return static_cast<HCURSOR>(m_hIcon);
}

HRESULT CProcessSchedulingDlg::OnHotKey(WPARAM wParam , LPARAM lParam)
{
	if(wParam == ID_Create)
	{
		CreateProssess();
		Prinft();
		SetTimer(1, 2000, NULL);
	}
	if (wParam == ID_Quit)
	{

		if (AfxMessageBox(_T("是否确定退出"), MB_YESNO) == IDYES)	
		{
			KillTimer(1);
			exit(0);
		}
	}
	return TRUE;
}

void CProcessSchedulingDlg::OnTimer(UINT_PTR nIDEvent)
{
	// TODO:  在此添加消息处理程序代码和/或调用默认值
	flag++;
	Priority();
	CDialogEx::OnTimer(nIDEvent);
}

void CProcessSchedulingDlg::CreateProssess()          //创建进程
{
	PCB *node;                 //进程指针
	srand((int)time(0));
	node = new PCB;
	if (node == NULL)
	{
		return;
	}
	else
	{
		node->pid = (1 + rand() % (100 + 1));
		node->life = rand() % 5 + 1; //为进程随机分配占用CPU时间
		node->runtime = 0;
		node->status = "就绪";
		node->priority = rand() % 49; //为进程随机分配优先数
	}
	insert(node);          //按照优先数插入到就绪队列中
}

void CProcessSchedulingDlg::insert(PCB *p)    //按priority大小插入就绪队列
{
	PCB *S1, *S2;
	if (ready == NULL)
	{
		p->next = NULL;
		ready = p;
	}
	else
	{
		S1 = ready;
		S2 = S1;
		while (S1 != NULL)
		{
			if (S1->priority >= p->priority)
			{
				S2 = S1;
				S1 = S1->next;
			}
			else
				break;
		}

		if (S2->priority >= p->priority)
		{
			S2->next = p;
			p->next = S1;
		}
		else
		{
			p->next = ready;
			ready = p;
		}
	}
}

void CProcessSchedulingDlg::Prinft()
{
	CString str1,str2,str3;
	int pid1,priority1,life1,i = 0;
	PCB *p;            //进程控制块指针
	p = run;           //运行队列
	if (p != NULL)
		p->next = NULL;

	m_run.DeleteAllItems(); // 全部清空
	while (p != NULL)
	{
		pid1 = p->pid;
		priority1 = p->priority;
		life1 = p->life;
		str1.Format(_T("%d"), pid1);
		str2.Format(_T("%d"), priority1);
		str3.Format(_T("%d"), life1);

		m_run.InsertItem(i, str1);
		m_run.SetItemText(i, 1, str2);
		m_run.SetItemText(i, 2, str3);
		m_run.SetItemText(i, 3, _T("运行"));
		p = p->next;
	}

	m_ready.DeleteAllItems(); // 全部清空
	p = ready;
	while (p != NULL)
	{
		pid1 = p->pid;
		priority1 = p->priority;
		life1 = p->life;
		str1.Format(_T("%d"), pid1);
		str2.Format(_T("%d"), priority1);
		str3.Format(_T("%d"), life1);

		m_ready.InsertItem(i, str1);
		m_ready.SetItemText(i, 1, str2);
		m_ready.SetItemText(i, 2, str3);
		m_ready.SetItemText(i, 3,_T("就绪"));
		p = p->next;
	}

	m_finish.DeleteAllItems(); // 全部清空
	p = finish;
	while (p != NULL)
	{
		pid1 = p->pid;
		priority1 = p->priority;
		life1 = p->life;
		str1.Format(_T("%d"), pid1);
		str2.Format(_T("%d"), priority1);
		str3.Format(_T("%d"), life1);

		m_finish.InsertItem(i, str1);
		m_finish.SetItemText(i, 1, str2);
		m_finish.SetItemText(i, 2, str3);
		m_finish.SetItemText(i, 3, _T("完成"));
		p = p->next;
	}
}

void CProcessSchedulingDlg::Priority()                 //处理优先级的函数
{
	if (flag == 1)
	{
		run = ready;
		if (run == NULL) return;
		ready = ready->next;
		run->status = "运行";
	}
	if (run == NULL){
		flag = 0;
		return;
	}

	//运行队列不空时，有进程正在运行
	run->runtime = run->runtime + 1;
	run->life = run->life - 1;
	run->priority = run->priority / 2; //每运行一次优先数变为原来的二分之一

	if (run->life == 0)        //如所需时间为0将其插入完成队列
	{
		run->status = "完成";
		run->next = finish;
		finish = run;
		run = NULL;           //运行队列头指针制空

		if (ready != NULL)     //就绪队列不空
		{
			run = ready;
			run->status = "运行";
			ready = ready->next;
		}
	}
	else if ((ready != NULL) && (run->priority < ready->priority))
	{
		run->status = "就绪";
		insert(run);
		run = ready;
		run->status = "运行";
		ready = ready->next;
	}
	Prinft();              //输出进程控制块PCB信息
}