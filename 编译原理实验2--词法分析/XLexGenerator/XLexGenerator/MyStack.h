#pragma once

// 链式栈类的前视定义
template <class T>
class LinkedStack;

// 定义链式栈结点类
template <class T>
class StackNode
{
	friend class LinkedStack<T>;
private:
	T data;
	StackNode<T> *next;
	StackNode(T item = 0, StackNode<T> *p = NULL)
	{
		data = item;
		next = p;
	}
};

// 定义链式栈类
template <class T>
class LinkedStack
{
private:
	StackNode<T> *top;
public:
	LinkedStack();
	~LinkedStack();
	bool IsEmpty(void) const;
	int Length(void) const;
	void Push(const T &item);
	T Pop(void);
	T GetTop(void);
	void Clear(void);
};
// 构造函数
template <class T>
LinkedStack<T>::LinkedStack()
{
	top = NULL;
}
// 析构函数
template <class T>
LinkedStack<T>::~LinkedStack()
{
	Clear();
}
// 判断栈是否为空
template <class T>
bool LinkedStack<T>::IsEmpty(void) const
{
	return (!top);
}
// 获取队列的长度
template <class T>
int LinkedStack<T>::Length(void) const
{
	StackNode<T> *temp = new StackNode<T>();
	temp = top;
	int length = 0;
	while (temp)
	{
		temp = temp->next;
		length++;
	}
	return length;
}
// 压入数据(入栈)
template <class T>
void LinkedStack<T>::Push(const T &item)
{
	top = new StackNode<T>(item, top);
}
// 抽出数据(出栈)
template <class T>
T LinkedStack<T>::Pop(void)
{
	if (!IsEmpty())
	{
		StackNode<T> *temp = top;
		top = top->next;
		T value = temp->data;
		delete temp;
		return value;
	}
}
// 获取栈头数据
template <class T>
T LinkedStack<T>::GetTop(void)
{
	if (!IsEmpty())
	{
		return top->data;
	}
}
// 设置栈为空栈
template <class T>
void LinkedStack<T>::Clear(void)
{
	StackNode<T> *temp = new StackNode<T>();
	while (top)
	{
		temp = top;
		top = top->next;
		delete temp;
	}
}