#pragma once



// CDiskMonitor

class CDiskMonitor : public CWinApp
{
	DECLARE_DYNCREATE(CDiskMonitor)

protected:
	CDiskMonitor();           // 动态创建所使用的受保护的构造函数
	virtual ~CDiskMonitor();

public:
	virtual BOOL InitInstance();
	virtual int ExitInstance();

protected:
	DECLARE_MESSAGE_MAP()
};


class DiskMonitor
{
private:

public:
	DiskMonitor();
	virtual ~DiskMonitor();
	BOOL Run(HWND hWnd, char path[], BOOL WatchSubDir, DWORD dwNotifyFilter);
	void Stop();
	void SetEventInfo();
	void OpenLog();
public:
	DWORD m_dwNotifyFilter;
	char m_Path[MAX_PATH];
	BOOL m_bWatchSubtree;
	BOOL m_bisRunning;
	BOOL m_bWriteLog;
	HANDLE m_hThread;
	HANDLE m_hDir;
	HANDLE m_hEvent;
	HWND m_hWnd;
	CTime m_Time;
	CString m_Event;
	CString m_LogFileName;
	CStdioFile *fOutput;

	DiskMonitor *m;
	void set(DiskMonitor *m1)
	{
		m=m1;
	}

};



