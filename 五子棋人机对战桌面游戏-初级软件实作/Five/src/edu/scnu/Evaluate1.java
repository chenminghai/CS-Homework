package edu.scnu;

//普通估值，只考虑当前状态
@SuppressWarnings("static-access")
public class Evaluate1 {
	private static final int FIVE = 50000;
	private static final int HUO_FOUR = 5000;
	private static final int CHONG_FOUR = 1000;
	private static final int HUO_THREE = 500;
	private static final int MIAN_THREE = 100;
	private static final int HUO_TWO = 50;
	private ChessBoard cb;
	private int[][] blackValue;
	private int[][] whiteValue;
	private int[][] staticValue;

	public Evaluate1(ChessBoard cb) {
		this.cb = cb;
		blackValue = new int[ChessBoard.Cols + 1][ChessBoard.Rows + 1];
		whiteValue = new int[ChessBoard.Cols + 1][ChessBoard.Rows + 1];
		staticValue = new int[ChessBoard.Cols + 1][ChessBoard.Rows + 1];
		for (int i = 0; i <= ChessBoard.Cols; i++) {
			for (int j = 0; j <= ChessBoard.Rows; j++) {
				blackValue[i][j] = 0;
				whiteValue[i][j] = 0;
			}
		}
		for (int i = 0; i <= ChessBoard.Cols / 2; i++) {
			for (int j = 0; j <= ChessBoard.Rows / 2; j++) {
				staticValue[i][j] = i < j ? i : j;
				staticValue[ChessBoard.Cols - i][j] = staticValue[i][j];
				staticValue[i][ChessBoard.Rows - j] = staticValue[i][j];
				staticValue[ChessBoard.Cols - i][ChessBoard.Rows - j] = staticValue[i][j];
			}
		}
	}

	private int evaluateValue(int color, int col, int row, int dir) {
		int k, m;
		int value = 0;
		int chessCount1 = 1;
		int chessCount2 = 0;
		int chessCount3 = 0;
		int spaceCount1 = 0;
		int spaceCount2 = 0;
		int spaceCount3 = 0;
		int spaceCount4 = 0;
		switch (dir) {
		case 1:
			for (k = col + 1; k <= cb.Cols; k++) {
				if (cb.boardStatus[k][row] == color) {
					chessCount1++;
				} else {
					break;
				}
			}
			while (k <= cb.Cols && cb.boardStatus[k][row] == 0) {
				spaceCount1++;
				k++;
			}
			if (spaceCount1 == 1) {
				while (k <= cb.Cols && cb.boardStatus[k][row] == color) {
					chessCount2++;
					k++;
				}
				while (k <= cb.Cols && cb.boardStatus[k][row] == 0) {
					spaceCount2++;
					k++;
				}
			}
			//
			for (k = col - 1; k >= 0; k--) {
				if (cb.boardStatus[k][row] == color) {
					chessCount1++;
				} else {
					break;
				}
			}
			while (k >= 0 && cb.boardStatus[k][row] == 0) {
				spaceCount3++;
				k--;
			}
			if (spaceCount3 == 1) {
				while (k >= 0 && cb.boardStatus[k][row] == color) {
					chessCount3++;
					k--;
				}
				while (k >= 0 && cb.boardStatus[k][row] == 0) {
					spaceCount4++;
					k--;
				}
			}
			break;

		case 2:
			for (k = row + 1; k <= cb.Rows; k++) {
				if (cb.boardStatus[col][k] == color) {
					chessCount1++;
				} else {
					break;
				}
			}
			while (k <= cb.Rows && cb.boardStatus[col][k] == 0) {
				spaceCount1++;
				k++;
			}
			if (spaceCount1 == 1) {
				while (k <= cb.Rows && cb.boardStatus[col][k] == color) {
					chessCount2++;
					k++;
				}
				while (k <= cb.Rows && cb.boardStatus[col][k] == 0) {
					spaceCount2++;
					k++;
				}
			}
			//
			for (k = row - 1; k >= 0; k--) {
				if (cb.boardStatus[col][k] == color) {
					chessCount1++;
				} else {
					break;
				}
			}
			while (k >= 0 && cb.boardStatus[col][k] == 0) {
				spaceCount3++;
				k--;
			}
			if (spaceCount3 == 1) {
				while (k >= 0 && cb.boardStatus[col][k] == color) {
					chessCount3++;
					k--;
				}
				while (k >= 0 && cb.boardStatus[col][k] == 0) {
					spaceCount4++;
					k--;
				}
			}
			break;

		case 3:
			for (k = col + 1, m = row + 1; k <= cb.Cols && m <= cb.Rows; k++, m++) {
				if (cb.boardStatus[k][m] == color) {
					chessCount1++;
				} else {
					break;
				}
			}
			while (k <= cb.Cols && m <= cb.Rows && cb.boardStatus[k][m] == 0) {
				spaceCount1++;
				k++;
				m++;
			}
			if (spaceCount1 == 1) {
				while (k <= cb.Cols && m <= cb.Rows && cb.boardStatus[k][m] == color) {
					chessCount2++;
					k++;
					m++;
				}
				while (k <= cb.Cols && m <= cb.Rows && cb.boardStatus[k][m] == 0) {
					spaceCount2++;
					k++;
					m++;
				}
			}
			//
			for (k = col - 1, m = row - 1; k >= 0 && m >= 0; k--, m--) {
				if (cb.boardStatus[k][m] == color) {
					chessCount1++;
				} else {
					break;
				}
			}
			while (k >= 0 && m >= 0 && cb.boardStatus[k][m] == 0) {
				spaceCount3++;
				k--;
				m--;
			}
			if (spaceCount3 == 1) {
				while (k >= 0 && m >= 0 && cb.boardStatus[k][m] == color) {
					chessCount3++;
					k--;
					m--;
				}
				while (k >= 0 && m >= 0 && cb.boardStatus[k][m] == 0) {
					spaceCount4++;
					k--;
					m--;
				}
			}
			break;

		case 4:
			for (k = col + 1, m = row - 1; k <= cb.Cols && m >= 0; k++, m--) {
				if (cb.boardStatus[k][m] == color) {
					chessCount1++;
				} else {
					break;
				}
			}
			while (k <= cb.Cols && m >= 0 && cb.boardStatus[k][m] == 0) {
				spaceCount1++;
				k++;
				m--;
			}
			if (spaceCount1 == 1) {
				while (k <= cb.Cols && m >= 0 && cb.boardStatus[k][m] == color) {
					chessCount2++;
					k++;
					m--;
				}
				while (k <= cb.Cols && m >= 0 && cb.boardStatus[k][m] == 0) {
					spaceCount2++;
					k++;
					m--;
				}
			}
			//
			for (k = col - 1, m = row + 1; k >= 0 && m <= cb.Rows; k--, m++) {
				if (cb.boardStatus[k][m] == color) {
					chessCount1++;
				} else {
					break;
				}
			}
			while (k >= 0 && m <= cb.Rows && cb.boardStatus[k][m] == 0) {
				spaceCount3++;
				k--;
				m++;
			}
			if (spaceCount3 == 1) {
				while (k >= 0 && m <= cb.Rows && cb.boardStatus[k][m] == color) {
					chessCount3++;
					k--;
					m++;
				}
				while (k >= 0 && m <= cb.Rows && cb.boardStatus[k][m] == 0) {
					spaceCount4++;
					k--;
					m++;
				}
			}
			break;
		}
		if (chessCount1 + chessCount2 + chessCount3 + spaceCount1 + spaceCount2 + spaceCount3 + spaceCount4 >= 5) {
			value = getValue(chessCount1, chessCount2, chessCount3, spaceCount1, spaceCount2, spaceCount3, spaceCount4);
		}
		return value;
	}

	private int getValue(int chessCount1, int chessCount2, int chessCount3, int spaceCount1, int spaceCount2,
			int spaceCount3, int spaceCount4) {

		int value = 0;
		switch (chessCount1) {
		case 5:
			value = FIVE;
			break;
		case 4:
			if (spaceCount1 > 0 && spaceCount3 > 0) {
				value = HUO_FOUR;
			} else {
				value = CHONG_FOUR;
			}
			break;
		case 3:
			if ((spaceCount1 == 1 && chessCount2 >= 1) && (spaceCount3 == 1 && chessCount3 >= 1)) {
				value = HUO_FOUR;
			} else if ((spaceCount1 == 1 && chessCount2 >= 1) || (spaceCount3 == 1 && chessCount3 >= 1)) {
				value = CHONG_FOUR;
			} else if ((spaceCount1 > 1 && spaceCount3 > 0) || (spaceCount1 > 0 && spaceCount3 > 1)) {
				value = HUO_THREE;
			} else {
				value = MIAN_THREE;
			}
			break;
		case 2:
			if ((spaceCount1 == 1 && chessCount2 >= 2) && (spaceCount3 == 1 && chessCount3 >= 2)) {
				value = HUO_FOUR;
			} else if ((spaceCount1 == 1 && chessCount2 >= 2) || (spaceCount3 == 1 && chessCount3 >= 2)) {
				value = CHONG_FOUR;
			} else if ((spaceCount1 == 1 &&chessCount2 ==1 && spaceCount2>0 && spaceCount3>0) || 
					(spaceCount3 ==1 && chessCount3== 1 && spaceCount1>0 && spaceCount4>0)) {
				value = HUO_THREE;
			} else {
				value =HUO_TWO;
			}
			break;
		case 1:
			if( (spaceCount1==1&&chessCount2>=3) || spaceCount3==1 && chessCount3>=3) {
				value=CHONG_FOUR;
			}else if((spaceCount1==1 && chessCount2==2 && spaceCount2>=1 && spaceCount3>=1)||
					(spaceCount3==1 && chessCount3==2 && spaceCount1>=1 && spaceCount4>=1)) {
				value=HUO_THREE;
				
			}else if((spaceCount1==1 && chessCount2==2 && (spaceCount2>=1 || spaceCount3>=1))||
					(spaceCount3==1 &&chessCount3==2 && (spaceCount1>=1 || spaceCount4>=1))) {
				value=MIAN_THREE;
				
			}else if((spaceCount1==1 && chessCount2==1 && spaceCount2>1 && spaceCount3>0)||
					(spaceCount3==1 && chessCount3==1 && spaceCount1>0 && spaceCount4>1)) {
				value=HUO_TWO;
			}
			break;
		default:
			value=0;
			break;
		}
		return value;
	}
	int [] getTheBestPosition() {
		for(int i=0;i<=cb.Cols;i++) {
			for(int j=0;j<=cb.Rows;j++) {
				blackValue[i][j]=0;
				whiteValue[i][j]=0;
				if(cb.boardStatus[i][j]==0) {
					for(int m=1;m<=4;m++) {
						blackValue[i][j]+=evaluateValue(1,i,j,m);
						whiteValue[i][j]+=evaluateValue(2,i,j,m);
					}
				}
			}
		}
		int k=0;
		//
		int [][]totalValue=new int[(cb.Cols+1)*(cb.Rows+1)][3];
		for(int i=0;i<=cb.Cols;i++) {
			for(int j=0;j<=cb.Rows;j++) {
				if(cb.boardStatus[i][j]==0) {
					totalValue[k][0]=i;
					totalValue[k][1]=j;
					totalValue[k][2]=blackValue[i][j]+whiteValue[i][j]+staticValue[i][j];
					k++;
				}
			}
		}
		sort(totalValue);
		k=1;
		//
		int maxValue=totalValue[0][2];
		while(totalValue[k][2]==maxValue) {
			k++;
		}
		int r=(int) (Math.random()*k);
		int []position=new int[2];
		position[0]=totalValue[r][0];
		position[1]=totalValue[r][1];
		return position;
	}

	private void sort(int[][] allValue) {
		
		for(int i=0;i<allValue.length-1;i++) {
			for(int j=0;j<allValue.length-1;j++) {
				int ti,tj,tvalue;
				if(allValue[j][2]<allValue[j+1][2]) {
					tvalue=allValue[j][2];
					allValue[j][2]=allValue[j+1][2];
					allValue[j+1][2]=tvalue;
					ti=allValue[j][0];
					allValue[j][0]=allValue[j+1][0];
					allValue[j+1][0]=ti;
					tj=allValue[j][1];
					allValue[j][1]=allValue[j+1][1];
					allValue[j+1][1]=tj;					
				}
			}
		}
	}
}
