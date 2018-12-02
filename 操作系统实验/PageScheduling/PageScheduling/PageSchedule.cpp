#include<iostream>
using namespace std;

class Page          //定义类Page
{
public:
	Page();
	int creatAddress();
	int getMaxMin();
	int getMaxMemory();
	int getChange();
	int FIFO(int a[], int m, int p[], int count);
	int LRU(int b[], int m, int p[], int count);
	int OPT(int c[], int m, int p[], int count);
	int doPage();
private:
	int d[256];     //用于存放指令地址的数组
	int MaxAddress;  //初始化最大地址
	int MinAddress;  //初始化最小地址
	int PageSize;      //初始化页面的大小
	int change[256];//用于存放作业访问的页号顺序的数组
	int MaxMemory;       //初始化分配给作业的内存块数
};

Page::Page()          //构造函数
{
	MaxAddress = 0;    //初始化最大地址
	MinAddress = 32768;//初始化最小地址
	PageSize = 1024;     //初始化页面的大小
	MaxMemory = 1;           //初始化分配给作业的内存块数
}

int Page::creatAddress()
{
	d[0] = 10000;       //第一条指令的地址
	int a, i;
	for (i = 1; i<256; i++)
	{
		a = rand() % 1024 + 1;
		if (a>0 && a<513)
			d[i] = d[i - 1] + 1;
		else
		if (a>512 && a<769)
			d[i] = rand() % (d[i - 1]) + 1;
		else
		if (a>768 && a<1025)
			d[i] = d[i - 1] + 1 + rand() % (32767 - d[i - 1]);
	}

	return 0;
}

int Page::getMaxMin()        //获得该作业的最大和最小地址
{
	for (int i = 0; i<256; i++)
	{
		if (MaxAddress<d[i])
			MaxAddress = d[i];
		if (MinAddress>d[i])
			MinAddress = d[i];
	}

	return 1;
}

int Page::getMaxMemory()          //确定该作业所需要的最大内存块数
{
	int H = MaxAddress / PageSize;
	if (MaxAddress%PageSize != 0)
		H++;

	int L = MinAddress / PageSize;
	if (MinAddress%PageSize != 0)
		L++;
	MaxMemory = H - L + 1;
	return MaxMemory;
}

int Page::getChange()       //确定该作业访问的页号的顺序，对于相邻相同的页号，合并为一个
{
	int count = 1;
	change[0] = d[0] / PageSize;
	if (d[0] % PageSize != 0)
		change[0]++;
	int j = 1;
	int i;
	for (i = 1; i<256; i++)
	{
		int temp = d[i] / PageSize;
		if (d[i] % PageSize != 0)
			temp++;
		if (temp != change[j - 1])
		{
			change[j] = temp;
			count++;
			j++;
		}
	}

	return count;
}

int Page::FIFO(int a[], int m, int p[], int count)   //采用FIFO算法
{
	int k = 0;
	int countF = 0;
	int i, j;

	for (j = m; j<count; j++)
	{
		for (i = 0; i<m; i++)
		if (a[i] == p[j])
			break;
		if (i == m)
		{
			a[k] = p[j];
			k = (k + 1) % m;
			countF++;
		}
	}

	return countF;
}

int Page::LRU(int b[], int m, int p[], int count)   //采用LRU算法
{
	int countL = 0;
	int i, j, ii;
	for (j = m; j<count; j++)
	{
		for (i = 0; i<m; i++)
		if (b[i] == p[j])
			break;
		if (i == m)
		{
			for (ii = 0; ii<m - 1; ii++)
				b[ii] = b[ii + 1];
			b[ii] = p[j];
			countL++;
		}
		else
		{
			int temp = b[i];
			for (ii = i; ii<m - 1; ii++)
				b[ii] = b[ii + 1];
			b[ii] = temp;
		}
	}

	return countL;
}

int Page::OPT(int c[], int m, int p[], int count)  //采用OPT算法
{
	int countO = 0;
	int i, j, ii, jj;
	for (j = m; j<count; j++)
	{
		for (i = 0; i<m; i++)
		if (c[i] == p[j])
			break;
		if (i == m)
		{
			int mincount = count;
			int outpage;
			int tempcount = 0;

			for (ii = 0; ii<m; ii++)
			{
				for (jj = j; jj<count; jj++)
				if (c[ii] == p[jj])
					tempcount++;
				if (mincount>tempcount)
				{
					mincount = tempcount;
					outpage = c[ii];
					tempcount = 0;
				}
				tempcount = 0;
			}

			for (ii = 0; ii<m; ii++)
			if (c[ii] == outpage)
				break;
			c[ii] = p[j];
			countO++;
		}
	}

	return countO;
}

int Page::doPage()
{
	creatAddress();

	cout << "1. 运行的作业共有256条指令，地址范围为[0，32767]" << endl;
	cout << "2. 可供选择的页面大小为：1k，2k，4k，8k，16k" << endl;
	cout << "3. 可输入任意数，如：2" << endl << endl;

	cout << "请输入所选择的页面大小：";
	cin >> PageSize;
	PageSize *= 1024;

	int count = getChange();
	cout << endl << "该作业依次要访问的页号的总数是：" << count << endl << endl;

	int *pFIFO = new int[count];
	int *pLRU = new int[count];
	int *pOPT = new int[count];
	int i;
	for (i = 0; i<count; i++)
		pFIFO[i] = pLRU[i] = pOPT[i] = change[i];
	getMaxMin();
	int M = getMaxMemory();
	int m;

	cout << "该作业所需最大内存块数为：" << M << endl << endl;
	cout << "下面给出的是[1," << M << "]的各个数作为分配给该作业的内存块数的情况:" << endl;

	for (m = 1; m <= M; m++)
	{
		int *a = new int[m];
		int *b = new int[m];
		int *c = new int[m];
		a[0] = b[0] = c[0] = change[0];
		int j, ii;
		for (i = 1; i<m; i++)
		{
			for (j = i; j<count; j++)
			{
				for (ii = 0; ii<i; ii++)
				if (a[ii] == change[j])
					break;
				if (ii == i)
				{
					a[i] = b[i] = c[i] = change[j];
					break;
				}
			}
			if (j == count)
				break;
		}

		int CountF = FIFO(a, m, pFIFO, count);
		int CountL = LRU(b, m, pLRU, count);
		int CountO = OPT(c, m, pOPT, count);
		delete a;
		delete b;
		delete c;

		cout << "1.页面大小：" << PageSize / 1024 << "k" << endl;
		cout << "2.分配给程序的内存块数：" << m << endl;
		cout << "3.缺页率：" << endl;
		cout << "  （1）OPT算法的缺页率： " << CountO / (float)count << endl;
		cout << "  （2）FIFO算法的缺页率：" << CountF / (float)count << endl;
		cout << "  （3）LRU算法的缺页率为：" << CountL / (float)count << endl;
		cout << endl;
	}

	return 0;
}
int main()
{
	cout << "--------------------------------------------------------------------------" << endl;
	cout << "            模拟操作系统的页面置换【OPT、FIFO、LRU算法】                  " << endl;
	cout << "--------------------------------------------------------------------------" << endl;

	Page p;
	p.doPage();
	system("pause");
	return 0;
}
