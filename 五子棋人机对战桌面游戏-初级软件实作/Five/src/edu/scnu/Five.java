package edu.scnu;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JToolBar;

public class Five extends JFrame{	
	private static final long serialVersionUID = 1L;

	private JToolBar toolbar;
	private JButton startButton,backButton,exitButton;
	private ChessBoard boardPanel;
	JLabel msg;
	JCheckBox computerFirst;
	public Five() {
		super("五子棋人机对战");
		toolbar=new JToolBar();
		startButton=new JButton("开始游戏");
		backButton=new JButton("悔棋");
		exitButton=new JButton("退出");
		boardPanel=new ChessBoard(this);
		msg=new JLabel("新的游戏未开始!");
		computerFirst=new JCheckBox("计算机先下子");
		toolbar.add(startButton);
		toolbar.add(backButton);
		toolbar.add(exitButton);
		toolbar.add(computerFirst);
		ActionMonitor monitor=new ActionMonitor();
		startButton.addActionListener(monitor);
		backButton.addActionListener(monitor);
		exitButton.addActionListener(monitor);
		this.add(toolbar, BorderLayout.NORTH);
		this.add(boardPanel,BorderLayout.CENTER);
		this.add(msg, BorderLayout.SOUTH);
		this.setLocation(350, 20);
		this.pack();
		this.setResizable(true);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setVisible(true);
		
	}
	class ActionMonitor implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			if(e.getSource()==startButton) {
				boardPanel.restartGame();
			}
			else if(e.getSource()==backButton) {
				boardPanel.goback();
			}
			else if(e.getSource()==exitButton) {
				System.exit(0);
			}
		}
			
	}
	public static void main(String[]args) {
		new Five();
		
	}
}
