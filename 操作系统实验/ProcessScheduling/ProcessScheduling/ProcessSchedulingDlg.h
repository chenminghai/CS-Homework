
// ProcessSchedulingDlg.h : 头文件
//

#pragma once
#include "afxcmn.h"
#include <iostream>
#include <time.h>
#include <string>
using namespace std;

struct PCB             //PCB进程控制块
{
	int pid;        //进程标识符
	int priority;      //进程优先级
	string status;     //进程的状态标识
	int runtime;       //进程已运行CPU时间
	int life;          //进程的生命周期
	PCB *next;         //进程的队列指针
};

// CProcessSchedulingDlg 对话框
class CProcessSchedulingDlg : public CDialogEx
{
	// 构造
public:
	CProcessSchedulingDlg(CWnd* pParent = NULL);	// 标准构造函数

	// 对话框数据
	enum { IDD = IDD_PROCESSSCHEDULING_DIALOG };

protected:
	virtual void DoDataExchange(CDataExchange* pDX);	// DDX/DDV 支持

	// 实现
protected:
	HICON m_hIcon;

	// 生成的消息映射函数
	virtual BOOL OnInitDialog();
	afx_msg void OnSysCommand(UINT nID, LPARAM lParam);
	afx_msg void OnPaint();
	afx_msg HCURSOR OnQueryDragIcon();
	afx_msg long OnHotKey(WPARAM wParam, LPARAM lParam);
	DECLARE_MESSAGE_MAP()
public:
	afx_msg void OnTimer(UINT_PTR nIDEvent);
	// 当前正在运行的进程
	CListCtrl m_run;
	// 当前的就绪队列
	CListCtrl m_ready;
	// 当前已经完成的进程
	CListCtrl m_finish;
	PCB *run = NULL;       //运行队列头指针
	PCB *ready = NULL;     //就绪队列头指针
	PCB *finish = NULL;    //完成队列头指针
	void insert(PCB *p);    //按priority大小插入就绪队列
	void Prinft(); //打印
	int flag = 0;
	void Priority();             //处理优先级的函数
	void CreateProssess();       //创建进程
};
