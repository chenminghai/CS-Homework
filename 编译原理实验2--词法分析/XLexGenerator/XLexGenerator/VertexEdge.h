#pragma once
#define NULL 0

// 定义邻接表的边
class Edge
{
public:
	int number;
	int position;
	char element;
	Edge *next;
	Edge()
	{
		number = -1;
		position = -1;
		next = NULL;
	}
	Edge(int num, int posi, char ele)
	{
		number = num;
		position = posi;
		element = ele;
		next = NULL;
	}
};

// 定义邻接表的顶点
class Vertex
{
public:
	int number;
	Vertex *next;
	Edge *link;
	Vertex()
	{
		number = -1;
		next = NULL;
		link = NULL;
	}
	Vertex(int num)
	{
		number = num;
		next = NULL;
		link = NULL;
	}
};