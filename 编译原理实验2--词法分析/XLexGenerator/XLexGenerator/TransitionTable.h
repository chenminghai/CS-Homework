#pragma once
// 定义转移矩阵类
class TransitionTable
{
public:
	TransitionTable(int rowNum, int colNum);
	~TransitionTable();
	void SetValue(int i, int j, int value);
	int GetValue(int i, int j);
	int Transit(int current, char input, char *edge);
	void Clear(void);
private:
	int **matrix;
	int rowNumber;
	int colNumber;
};
// 构造函数
TransitionTable::TransitionTable(int rowNum, int colNum)
{
	rowNumber = rowNum;
	colNumber = colNum;
	matrix = (int**)(new int*[rowNumber]);
	for (int i = 0; i < rowNumber; i++)
	{
		matrix[i] = new int[colNumber];
	}
}
// 析构函数
TransitionTable::~TransitionTable()
{
	Clear();
}
// 设置元素的值
void TransitionTable::SetValue(int i, int j, int value)
{
	matrix[i][j] = value;
}
// 获取元素的值
int TransitionTable::GetValue(int i, int j)
{
	return matrix[i][j];
}
// 状态转移函数
int TransitionTable::Transit(int current, char input, char *edge)
{
	for (int i = 0; edge[i] != '\0'; i++)
	{
		if (input == edge[i])
		{
			return matrix[current][i];
		}
	}
	return -1;
}
// 清空转移矩阵
void TransitionTable::Clear(void)
{
	for (int i = 0; i < rowNumber; i++)
	{
		delete[] matrix[i];
	}
	delete matrix;
}
