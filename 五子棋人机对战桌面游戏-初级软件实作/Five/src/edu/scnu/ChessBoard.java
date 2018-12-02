package edu.scnu;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class ChessBoard extends JPanel {

	private static final long serialVersionUID = 1L;
	public static final int Margin=30;
	public static final int Span=30;
	public static final int Rows=18;
	public static final int Cols=18;
	private Five five;
	Image img;
	List<Chess> chessList;
	int chessCount;
	int computerColor;
	int [][] boardStatus;
	//保护矩形区域变量
	int left;
	int top;
	int right;
	int bottom;
	
	boolean isComputerGo; 
	boolean isGamming=false;
	boolean isBlack=true;

	public ChessBoard(Five five) {
		img=Toolkit.getDefaultToolkit().getImage("image/background.jpg");
		this.addMouseListener(new MouseMonitor());
		this.addMouseMotionListener(new MouseMotionMonitor());
		this.chessCount=0;
		this.chessList=new ArrayList<Chess>();
		this.five=five;
		this.boardStatus=new int[Cols+1][Rows+1];
		for(int i=0;i<Cols;i++) {
			for(int j=0;j<Rows;j++)
				boardStatus[i][j]=0;
		}
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(img, 0, 0, this);
		for(int i=0;i<=Rows;i++) {
			g.drawLine(Margin, Margin+i*Span, Margin+Cols*Span, Margin+i*Span);
		}
		for(int i=0;i<=Cols;i++) {
			g.drawLine(Margin+i*Span, Margin, Margin+i*Span, Margin+Rows*Span);
		}
		g.fillRect(Margin+3*Span-2, Margin+3*Span-2, 5, 5);
		g.fillRect(Margin+(Cols/2)*Span-2, Margin+3*Span-2, 5, 5);
		g.fillRect(Margin+(Cols-3)*Span-2, Margin+3*Span-2, 5, 5);
		g.fillRect(Margin+3*Span-2, Margin+(Rows/2)*Span-2, 5, 5);
		g.fillRect(Margin+(Cols/2)*Span-2, Margin+(Rows/2)*Span-2, 5, 5);
		g.fillRect(Margin+(Cols-3)*Span-2, Margin+(Rows/2)*Span-2, 5, 5);
		g.fillRect(Margin+3*Span-2, Margin+(Rows-3)*Span-2, 5, 5);
		g.fillRect(Margin+(Cols/2)*Span-2, Margin+(Rows-3)*Span-2, 5, 5);
		g.fillRect(Margin+(Cols-3)*Span-2, Margin+(Rows-3)*Span-2, 5, 5);	
		
		for(int i=0;i<chessCount;i++) {
			chessList.get(i).draw(g);
			if(i==chessCount-1) {
				int xPos=chessList.get(i).getCol()*Span+Margin;
				int yPos=chessList.get(i).getRow()*Span+Margin;
				g.setColor(Color.RED);
				g.drawRect(xPos-Chess.Diameter/2, yPos-Chess.Diameter/2, Chess.Diameter, Chess.Diameter);
			}
		}
		int count[]=new int[Rows];
		for(int j=0;j<Rows;j++) count[j]=0;
		for(int i=0;i<chessCount;i++) {
			int xx=chessList.get(i).getRow();
			count[xx]++;
		}
		int max=0;
		for(int k=0;k<Rows;k++) {
			if(max<count[k]) max=count[k];
		}
		System.out.println("棋盘中行向棋子最多个数为："+max);
	}
	
	public Dimension getPreferredSize() {
		return new Dimension(Margin*2+Span*Cols,Margin*2+Span*Rows);
	}
	
	private boolean hasChess(int col,int row) {
		for(int i=0;i<chessCount;i++) {
			Chess ch=chessList.get(i);
			if(ch!=null && ch.getCol()==col && ch.getRow()==row)
				return true;		
		}
		return false;
	}
	private boolean hasChess(int col,int row,Color color) {
		for(int i=0;i<chessCount;i++) {
			Chess ch=chessList.get(i);
			if(ch!=null && ch.getCol()==col && ch.getRow()==row && ch.getColor()==color)
				return true;		
		}
		return false;
	}
	
	private int searchX(int col,int row , Color c,int direct,int i,int continueCount) {
		
		for(int x=col+direct;x*direct<=i;x+=direct) {
			if(hasChess(x,row,c))
				continueCount++;
		else 
			break;
		}
		return continueCount;
	}
	private int searchY(int col,int row , Color c,int direct,int i,int continueCount) {

		for(int y=row+direct;y*direct<=i;y+=direct) {
			if(hasChess(col,y,c))
				continueCount++;
		else 
			break;
		}
		return continueCount;
	}
	private boolean isWin(int col,int row) {
		int continueCount=1;
		Color c=isBlack ? Color.BLACK:Color.WHITE;
		//横向搜索
		continueCount=searchX(col,row,c,1,Cols,continueCount)+searchX(col,row,c,-1,0,0);
		if(continueCount>=5)  return true;
		else  continueCount=1;
		//纵向搜索
		continueCount=searchY(col,row,c,1,Rows,continueCount)+searchY(col,row,c,-1,0,0);
		if(continueCount>=5)  return true;
		else  continueCount=1;
		//右上到左下
		for(int x=col+1,y=row-1;y>=0 && x<=Cols;x++,y--){
			if(hasChess(x,y,c)) continueCount++;
			else break;
		}
		for(int x=col-1,y=row+1;x>=0 && y<=Rows;x--,y++){
			if(hasChess(x,y,c)) continueCount++;
			else break;
		}
		if(continueCount>=5)  return true;
		else  continueCount=1;
		//左上到右下
		for(int x=col-1,y=row-1;x>=0 && y>=0;x--,y--){
			if(hasChess(x,y,c)) continueCount++;
			else break;
		}
		for(int x=col+1,y=row+1;x<=Cols && y<=Rows;x++,y++){
			if(hasChess(x,y,c)) continueCount++;
			else break;
		}
		if(continueCount>=5)  return true;
		else  return false;
	}
	
	public void restartGame() {
		chessList.clear();
		for(int i=0;i<Cols;i++) {
			for(int j=0;j<Rows;j++)
				boardStatus[i][j]=0;
		}
		
		left=7;
		top=7;
		right=7;
		bottom=7;
		
		isBlack=true;
		isGamming=true;
		isComputerGo=five.computerFirst.isSelected();
		computerColor=isComputerGo ? 1:2;
		chessCount=0;
		if(isComputerGo) {
			computerGo();
		}
		paintComponent(this.getGraphics());
		if(isBlack) {
			five.msg.setText("请黑棋下子!");
		}
		else {
			five.msg.setText("请白棋下子!");
		}
	}
	
	private void computerGo() {

		Evaluate2 e=new Evaluate2(this);
		int pos[]=e.getTheBestPosition();
		putChess(pos[0],pos[1],isBlack ? Color.BLACK:Color.WHITE);
	}
	private void manGo(int col,int row) {
		putChess(col,row,isBlack ? Color.BLACK:Color.WHITE);
	}
	private void putChess(int col, int row, Color color) {
		
		Chess ch=new Chess(ChessBoard.this,col,row,color);
		chessList.add(ch);
		chessCount++;
		boardStatus[col][row]=(color==Color.BLACK) ? 1:2 ;
		paintComponent(this.getGraphics());
		//更新矩形保护区
		if(left>col) left=col;
		if(top>row) top=row;
		if(right<col) right=col;
		if(bottom<row) bottom=row;
		
		if(isWin(col,row)) {
			String colorName=isBlack ? "黑棋":"白棋";
			String message=String.format("恭喜,%s赢了!", colorName);
			JOptionPane.showMessageDialog(ChessBoard.this, message);
			isGamming=false;
		}
		else {
			isBlack=!isBlack;
			isComputerGo=!isComputerGo;
		}
	}

	public void goback() {
		if(isComputerGo || chessCount<2) return;
		int i=chessList.get(chessCount-1).getCol();
		int j=chessList.get(chessCount-1).getRow();
		boardStatus[i][j]=0;
		i=chessList.get(chessCount-2).getCol();
		j=chessList.get(chessCount-2).getRow();
		boardStatus[i][j]=0;
		chessList.remove(chessCount-1);
		chessList.remove(chessCount-2);
		chessCount-=2;
		isBlack=!isBlack;
		isGamming=true;
		paintComponent(this.getGraphics());
		if(isBlack) {
			five.msg.setText("请黑棋下子!");
		}
		else {
			five.msg.setText("请白棋下子!");
		}
	}
	
	class MouseMonitor extends MouseAdapter{
		public void mousePressed(MouseEvent e) {
			if(! isGamming) return ;
			if(isComputerGo) return;
			int col=(e.getX()-Margin+Span/2)/Span;
			int row=(e.getY()-Margin+Span/2)/Span;
			if(col<0 || row <0 || col>Cols || row>Rows)
				return;
			if(hasChess(col,row))
				return;
			manGo(col,row);
			if(!isGamming) 
				return;
			computerGo();
		}
	}
	
	class MouseMotionMonitor extends MouseMotionAdapter{
		public void mouseMoved(MouseEvent e) {
			int col=(e.getX()-Margin+Span/2)/Span;
			int row=(e.getY()-Margin+Span/2)/Span;
			if(col<0 || row <0 || col>Cols || row>Rows ||!isGamming || hasChess(col,row)) {
				ChessBoard.this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}
			else {
				ChessBoard.this.setCursor(new Cursor(Cursor.HAND_CURSOR));
			}
		}
	}
}
