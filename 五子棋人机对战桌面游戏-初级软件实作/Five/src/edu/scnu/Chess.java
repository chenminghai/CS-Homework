package edu.scnu;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RadialGradientPaint;
import java.awt.RenderingHints;
import java.awt.geom.Ellipse2D;

public class Chess {

	public static final int Diameter=ChessBoard.Span-2;
	private int col;
	private int row;
	private Color color;
	ChessBoard cb;
	public Chess(ChessBoard cb, int col, int row, Color color) {
		this.col = col;
		this.row = row;
		this.color=color;
		this.cb = cb;
	}
	public int getCol() {
		return col;
	}	
	public int getRow() {
		return row;
	}	
	public Color getColor() {
		return color;
	}
	@SuppressWarnings("static-access")
	public void draw(Graphics g) {
		int xPos=col*cb.Span+cb.Margin;	
		int yPos=row*cb.Span+cb.Margin;
		Graphics2D g2d=(Graphics2D) g;
		RadialGradientPaint paint=null;
		int x=xPos+Diameter/4;
		int y=yPos-Diameter/4;
		float[] f= {0f,1f};
		Color[] c={Color.WHITE,Color.BLACK};
		if(color==Color.BLACK) {
			paint=new RadialGradientPaint(x,y,Diameter,f,c);
		}
		else if(color==Color.WHITE) {
			paint=new RadialGradientPaint(x,y,Diameter * 2,f,c);
		}
		g2d.setPaint(paint);
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_DEFAULT);
		Ellipse2D e=new Ellipse2D.Float(xPos-Diameter/2, yPos-Diameter/2, Diameter, Diameter);
		g2d.fill(e);	
	}
}
