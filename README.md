# CS-Homework
这是本人在本科阶段所做的作业。目的是为了保留纪念，等以后再看自己的作业会不会害羞和骂自己当年多菜:-)
## 编译原理
* 实验一：C++源代码单词扫描程序（词法分析）<br/>
一、实验内容及要求：<br/>
(1). C++源代码扫描程序识别C++记号。C++语言包含了几种类型的记号：标识符，关键字，数（包括整数、浮点数），字符串、注释、特殊符号（分界符）和运算符号等。<br/>
(2). 打开一个C++源文件，打印出所有以上的记号。<br/>
(3). 要求应用程序应为Windows界面。<br/>
(4). 选作部分：为了提高C++源程序的可读性，C++程序在书写过程中加入了空行、空格、缩进、注释等。假设你想牺牲可读性，以节省磁盘空间，那么你可以存贮一个删除了所有不必要空格和注释的C++源程序的压缩文本。因此，程序中还应该有这样的压缩功能。<br/>
(5). 选作部分：进一步思考或实现——如何进一步实现减小源文件大小的压缩功能。<br/>
(6). 应该书写完善的软件文档。<br/>
* 实验一运行截图如下(单词分割)：<br/>
![](https://github.com/chenminghai/CS-Homework/blob/master/%E7%BC%96%E8%AF%91%E5%8E%9F%E7%90%86%E5%AE%9E%E9%AA%8C1--%E5%8D%95%E8%AF%8D%E5%88%87%E5%89%B2/%E5%9B%BE%E7%89%871.png)
* 实验二：XLEX生成器<br/>
一、实验内容：<br/>
设计一个应用软件，以实现将正则表达式-->NFA--->DFA-->DFA最小化-->词法分析程序<br/>
二、实验要求：<br/>
(1). 要提供一个源程序编辑界面，让用户输入正则表达式（可保存、打开源程序）<br/>
(2). 需要提供窗口以便用户可以查看转换得到的NFA（用状态转换表呈现即可）<br/>
(3). 需要提供窗口以便用户可以查看转换得到的DFA（用状态转换表呈现即可）<br/>
(4). 需要提供窗口以便用户可以查看转换得到的最小化DFA（用状态转换表呈现即可）<br/>
(5). 需要提供窗口以便用户可以查看转换得到的词法分析程序（该分析程序需要用C语言描述）<br/>
(6). 应该书写完善的软件文档<br/>
* 实验二运行截图如下(词法分析)：<br/>
![](https://github.com/chenminghai/CS-Homework/blob/master/%E7%BC%96%E8%AF%91%E5%8E%9F%E7%90%86%E5%AE%9E%E9%AA%8C2--%E8%AF%8D%E6%B3%95%E5%88%86%E6%9E%90/%E5%9B%BE%E7%89%872.png)
![](https://github.com/chenminghai/CS-Homework/blob/master/%E7%BC%96%E8%AF%91%E5%8E%9F%E7%90%86%E5%AE%9E%E9%AA%8C2--%E8%AF%8D%E6%B3%95%E5%88%86%E6%9E%90/%E5%9B%BE%E7%89%873.png)
![](https://github.com/chenminghai/CS-Homework/blob/master/%E7%BC%96%E8%AF%91%E5%8E%9F%E7%90%86%E5%AE%9E%E9%AA%8C2--%E8%AF%8D%E6%B3%95%E5%88%86%E6%9E%90/%E5%9B%BE%E7%89%874.png)
![](https://github.com/chenminghai/CS-Homework/blob/master/%E7%BC%96%E8%AF%91%E5%8E%9F%E7%90%86%E5%AE%9E%E9%AA%8C2--%E8%AF%8D%E6%B3%95%E5%88%86%E6%9E%90/%E5%9B%BE%E7%89%875.png)
* 实验三：TINY扩充语言的语法分析<br/>
一、实验内容：<br/>
扩充的语法规则有：实现 while、do while、for语句、大于>比较运算符号以及求余计算式子，具体文法规则自行构造。
可参考：P97及P136的文法规则。<br/>
(1). While-stmt --> while  exp  do  stmt-sequence  endwhile<br/>
(2). Dowhile-stmt-->do  stmt-sequence  while(exp); <br/>
(3). for-stmt-->for identifier:=simple-exp  to  simple-exp  do  stmt-sequence enddo    步长递增1<br/>
(4). for-stmt-->for identifier:=simple-exp  downto  simple-exp  do  stmt-sequence enddo    步长递减1<br/>
(5). 大于>比较运算符号以及求余计算式子的文法规则请自行组织。<br/>
(6). 把TINY语言原有的if语句书写格式<br/>
if_stmt-->if exp then stmt-sequence end | if exp then stmt-sequence else stmt-sequence end <br/>
改写为：if_stmt-->if(exp) stmt-sequence | if(exp) stmt-sequence else stmt-sequence <br/>
* 实验三运行截图如下(语法分析)：<br/>
![](https://github.com/chenminghai/CS-Homework/blob/master/%E7%BC%96%E8%AF%91%E5%8E%9F%E7%90%86%E5%AE%9E%E9%AA%8C3--%E8%AF%AD%E6%B3%95%E5%88%86%E6%9E%90/%E5%9B%BE%E7%89%876.png)

# 五子棋人机对战
* This small project is a Gomoku game with graphical human-machine interaction written in Java language.
  这是一个使用Java语言编写的五子棋-人机对战桌面应用。（一个作业）
* 运行截图如下：
* ![](https://github.com/chenminghai/CS-Homework/blob/master/%E4%BA%94%E5%AD%90%E6%A3%8B%E4%BA%BA%E6%9C%BA%E5%AF%B9%E6%88%98%E6%A1%8C%E9%9D%A2%E6%B8%B8%E6%88%8F-%E5%88%9D%E7%BA%A7%E8%BD%AF%E4%BB%B6%E5%AE%9E%E4%BD%9C/1.PNG)

