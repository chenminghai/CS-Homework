// DiskMonitor.cpp : 实现文件
//

#include "stdafx.h"
#include "WinResourcesManager.h"
#include "DiskMonitor.h"
#include "locale.h"
// CDiskMonitor

IMPLEMENT_DYNCREATE(CDiskMonitor, CWinApp)

CDiskMonitor::CDiskMonitor()
{
}

CDiskMonitor::~CDiskMonitor()
{
}

BOOL CDiskMonitor::InitInstance()
{
	// TODO:    在此执行任意逐线程初始化
	return TRUE;
}

int CDiskMonitor::ExitInstance()
{
	// TODO:    在此执行任意逐线程清理
	return CWinApp::ExitInstance();
}

BEGIN_MESSAGE_MAP(CDiskMonitor, CWinApp)
END_MESSAGE_MAP()


// CDiskMonitor 消息处理程序
DiskMonitor::DiskMonitor()
{
	m_dwNotifyFilter = 0;
	m_bWriteLog = 0;
	m_bWatchSubtree = 0;
	m_bisRunning = 0;
	m_hWnd = NULL;
	m_hThread = NULL;
	m_hDir = NULL;
	m_Event = "";
	m_hEvent = NULL;
	fOutput = new CStdioFile;
	memset(m_Path, 0, sizeof(m_Path));
}

DiskMonitor::~DiskMonitor()
{
	CloseHandle(m_hDir);
	CloseHandle(m_hEvent);
	delete m;
}

void DiskMonitor::SetEventInfo()
{
	m_Time = CTime::GetCurrentTime();
	CString time;
	time.Format(L"%d/%d/%d ", m_Time.GetYear(), m_Time.GetMonth(), m_Time.GetDay());
	time += m_Time.Format("%H:%M:%S");

	if (m_bWriteLog)
	{
		fOutput->SeekToEnd();
		fOutput->WriteString(time + " " + m_Event + "\n");
	}
		

	((CListCtrl*)CWnd::FromHandle(GetDlgItem(m_hWnd, IDC_EVENTS)))->InsertItem(0, time);
	((CListCtrl*)CWnd::FromHandle(GetDlgItem(m_hWnd, IDC_EVENTS)))->SetItemText(0, 1, m_Event);
	m_Event = "";
}

DWORD WINAPI DirProc(LPVOID lpParameter)
{
	DiskMonitor *dm = (DiskMonitor*)lpParameter;
	char cBuf[2048];
	DWORD BytesReruned;

	while (TRUE)
	{
		WaitForSingleObject(dm->m_hEvent, INFINITE);
		ZeroMemory(cBuf, 2048);

		BOOL bSuccess = ReadDirectoryChangesW(dm->m_hDir, cBuf, 2048, dm->m_bWatchSubtree,
			dm->m_dwNotifyFilter,
			&BytesReruned, NULL, NULL);
		if (bSuccess)
		{
			FILE_NOTIFY_INFORMATION *pNotifyInfo = (FILE_NOTIFY_INFORMATION *)cBuf;
			do{
				DWORD NextEntryOffset = pNotifyInfo->NextEntryOffset;
				DWORD Action = pNotifyInfo->Action;
				DWORD FileNameLength = pNotifyInfo->FileNameLength;
				WCHAR wcFileName[1024] = { 0 };
				char cFileName[1024] = { 0 };
				memcpy(wcFileName, pNotifyInfo->FileName, FileNameLength);
				WideCharToMultiByte(CP_ACP, 0, wcFileName, -1, cFileName, 1024, NULL, NULL);
				dm->m_Event += cFileName;
				switch (Action)
				{
				case FILE_ACTION_ADDED:
					dm->m_Event += " 被创建  "; break;
				case FILE_ACTION_REMOVED:
					dm->m_Event += " 被删除  "; break;
				case FILE_ACTION_MODIFIED:
					dm->m_Event += " 被修改  "; break;
				case FILE_ACTION_RENAMED_OLD_NAME:
					dm->m_Event += " 被重命名为："; break;
				case FILE_ACTION_RENAMED_NEW_NAME:
					dm->m_Event += "  ";
					break;
				default:
					dm->m_Event += "未知事件!  ";
					break;
				}
				if (NextEntryOffset)
				{
					pNotifyInfo = (PFILE_NOTIFY_INFORMATION)((LPBYTE)pNotifyInfo + NextEntryOffset);
				}
				else
				{
					if (dm->m_bisRunning)
						dm->SetEventInfo();
					else dm->m_Event.Empty();
					break;
				}
			} while (TRUE);
		}
		else break;
	}

	return 0;
}

BOOL DiskMonitor::Run(HWND hWnd, char path[], BOOL WatchSubDir, DWORD dwNotifyFilter)
{
	if (!m_hWnd)
		m_hWnd = hWnd;
	if (fOutput == NULL)
		fOutput = new CStdioFile;
	m_bWatchSubtree = WatchSubDir;
	m_dwNotifyFilter = dwNotifyFilter;
	m_bisRunning = TRUE;
	strcpy_s(m_Path, path);
	CString temp(m_Path);
	if (m_hDir == NULL)
		m_hDir = CreateFile(temp, FILE_LIST_DIRECTORY,
		FILE_SHARE_READ | FILE_SHARE_WRITE | FILE_SHARE_DELETE,
		NULL, OPEN_EXISTING, FILE_FLAG_BACKUP_SEMANTICS | FILE_FLAG_OVERLAPPED, NULL);
	if (INVALID_HANDLE_VALUE == m_hDir)
	{
		return FALSE;
	}
	if (m_bWriteLog)
	{
		CTime tm = CTime::GetCurrentTime();
		char filename[50] = { 0 };
		sprintf_s(filename, "LOG_%d_%d_%d.txt", tm.GetYear(), tm.GetMonth(), tm.GetDay());
		m_LogFileName = filename;
		CString str;
		str.Format(L"%d/%d/%d ", tm.GetYear(), tm.GetMonth(), tm.GetDay());
		str += tm.Format("%H:%M:%S ");
		str += "开始监视...";

		char* pOldLocale = setlocale(LC_CTYPE, "chs");
		fOutput->Open(m_LogFileName, CFile::modeNoTruncate | CFile::modeCreate | CFile::modeWrite | CFile::typeText);
		if (fOutput)
		{
			fOutput->SeekToEnd();
			fOutput->WriteString(str);
		}
		setlocale(LC_CTYPE, pOldLocale);//如上所说
	}

	//if(!m_hEvent)
	m_hEvent = CreateEvent(NULL, TRUE, FALSE, NULL);

	SetEvent(m_hEvent);
	m_hThread = CreateThread(NULL, 0, DirProc, this, 0, NULL);
	if (m_hThread == NULL)
		return FALSE;
	CloseHandle(m_hThread);

	m_Time = CTime::GetCurrentTime();
	CString time;
	time.Format(L"%d/%d/%d ", m_Time.GetYear(), m_Time.GetMonth(), m_Time.GetDay());
	time += m_Time.Format("%H:%M:%S");
	((CListCtrl*)CWnd::FromHandle(GetDlgItem(m_hWnd, IDC_EVENTS)))->InsertItem(0, time);
	((CListCtrl*)CWnd::FromHandle(GetDlgItem(m_hWnd, IDC_EVENTS)))->SetItemText(0, 1,_T("开始监控..."));

	return TRUE;
}

void DiskMonitor::Stop()
{
	if (m_hEvent)
		ResetEvent(m_hEvent);
	m_hDir = NULL;
	m_dwNotifyFilter = 0;

	if (m_bWriteLog && fOutput != NULL)
	{
		CTime tm = CTime::GetCurrentTime();
		CString str;
		str.Format(L"%d/%d/%d ", tm.GetYear(), tm.GetMonth(), tm.GetDay());
		str += tm.Format("%H:%M:%S ");
		str += "停止监视...";
		fOutput->SeekToEnd();
		fOutput->WriteString(str + "\n");
		fOutput->Close();
	}
	if (m_hWnd && m_bisRunning)
	{
		m_bisRunning = FALSE;
		m_Time = CTime::GetCurrentTime();
		CString time;
		time.Format(L"%d/%d/%d ", m_Time.GetYear(), m_Time.GetMonth(), m_Time.GetDay());
		time += m_Time.Format("%H:%M:%S");
		((CListCtrl*)CWnd::FromHandle(GetDlgItem(m_hWnd, IDC_EVENTS)))->InsertItem(0, time);
		((CListCtrl*)CWnd::FromHandle(GetDlgItem(m_hWnd, IDC_EVENTS)))->SetItemText(0, 1, _T("停止监视..."));
	}
}

void DiskMonitor::OpenLog()
{
	if (m_bWriteLog)
	{
		::ShellExecute(m_hWnd, _T("open"), m_LogFileName, NULL, NULL, SW_SHOW);
	}
}