package edu.scnu;

//alpha-beta剪枝搜索方法
@SuppressWarnings("static-access")
public class Evaluate {
	private static final int FIVE = 50000;
	private static final int HUO_FOUR = 5000;
	private static final int CHONG_FOUR = 1000;
	private static final int HUO_THREE = 500;
	private static final int MIAN_THREE = 100;
	private static final int HUO_TWO = 50;
	private static final int LARGE_NUMBER = 10000000;
	private static final int SEARCH_DEPTH = 4;
	private static final int SAMPLE_NUMBER = 10;
	private ChessBoard cb;
	private int[][] blackValue;
	private int[][] whiteValue;
	private int[][] staticValue;

	public Evaluate(ChessBoard cb) {
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

	private int evaluateGame() {
		int value = 0;
		int i, j, k;
		int[] line = new int[cb.Cols + 1];
		// 对每一行估值
		for (j = cb.top; j <= cb.bottom; j++) {
			for (i = 0; i <= cb.Cols; i++) {
				line[i] = cb.boardStatus[i][j];
			}
			value += evaluateLine(line, cb.Cols + 1, 1);		
			value -= evaluateLine(line, cb.Cols + 1, 2);	
		}

		// 对每一列估值
		for (i = cb.left; i <= cb.right; i++) {
			for (j = 0; j < cb.Rows; j++) {
				line[j] = cb.boardStatus[i][j];
			}
			value += evaluateLine(line, cb.Rows + 1, 1);
			value -= evaluateLine(line, cb.Rows + 1, 2);
		}
		// 左下到右上斜线估值
		for (j = 4; j <= cb.Rows; j++) {
			for (k = 0; k <= j; k++) {
				line[k] = cb.boardStatus[k][j - k];
			}
			value += evaluateLine(line, j + 1, 1);
			value -= evaluateLine(line, j + 1, 2);
		}
		for (j = 1; j <= cb.Rows - 4; j++) {
			for (k = 0; k <= cb.Cols - j; k++) {
				line[k] = cb.boardStatus[k + j][cb.Rows - k];
			}
			value += evaluateLine(line, cb.Rows + 1 - j, 1);
			value -= evaluateLine(line, cb.Rows + 1 - j, 2);
		}

		// 左上到右下斜线估值
		for (j = 0; j <= cb.Rows - 4; j++) {
			for (k = 0; k <= cb.Rows - j; k++) {
				line[k] = cb.boardStatus[k][k + j];
			}
			value += evaluateLine(line, cb.Rows + 1 - j, 1);
			value -= evaluateLine(line, cb.Rows + 1 - j, 2);
		}
		for (i=1;i<=cb.Cols-4;i++) {
			for (k = 0; k <= cb.Rows - i; k++) {
				line[k] = cb.boardStatus[k+i][k];
			}
			value += evaluateLine(line, cb.Rows + 1 - i, 1);
			value -= evaluateLine(line, cb.Rows + 1 - i, 2);
		}
		if (cb.computerColor == 1) {
			return value;
		} else {
			return -value;
		}
	}
	
	private int evaluateLine(int[] lineState, int num, int color) {	
		int chess, space1, space2;
		int i, j;
		int value = 0;
		int begin, end;
		for (i = 0; i < num; i++)
			if (lineState[i] == color) {
				chess = 1;
				begin = i;
				for (j = begin + 1; j < num && lineState[j] == color; j++) {
					chess++;
				}
				if (chess < 2) {
					continue;
				}
				end = j - 1;
				space1 = 0;
				space2 = 0;
				// 计算棋子前面的空位数
				for (j = begin - 1; j >= 0 && (lineState[j] == 0 || lineState[j] == color); j--) {
					space1++;
				}
				// 计算棋子后面的空位数
				for (j = end + 1; j < num && (lineState[j] == 0 || lineState[j] == color); j++) {
					space2++;
				}
				if (chess + space1 + space2 >= 5) {
					value += getValue(chess, space1, space2);
				}
				i = end + 1;
			}
			return value;
	}

	private int getValue(int chessCount, int spaceCount1, int spaceCount2) {
		int value = 0;
		switch (chessCount) {
		case 5:
			value = FIVE;
			break;
		case 4:
			if (spaceCount1 > 0 && spaceCount2 > 0) {
				value = HUO_FOUR;
			} else {
				value = CHONG_FOUR;
			}
			break;
		case 3:
			if (spaceCount1 > 0 && spaceCount2 > 0) {
				value = HUO_THREE;
			} else {
				value = MIAN_THREE;
			}
			break;
		case 2:
			if (spaceCount1 > 0 && spaceCount2 > 0) {
				value = HUO_TWO;
			}
			break;
		default:
			value = 0;
			break;
		}
		return value;
	}

	private int[][] getTheMostValuablePositions() {
		
		int i, j, k = 0;
		int l,t,r,b;
		l=(cb.left>2) ? cb.left-2:0;
		t=(cb.top>2) ? cb.top-2:0;
		r=(cb.right<cb.Cols-1) ? cb.right+2:cb.Cols;
		b=(cb.bottom<cb.Rows-1) ? cb.bottom+2:cb.Rows;
		
		int[][] allValue = new int[(cb.Cols + 1) * (cb.Rows + 1)][3];
		for (i = l; i < r; i++) {
			for (j = t; j < b; j++) {
				if (cb.boardStatus[i][j] == 0) {
					allValue[k][0] = i;
					allValue[k][1] = j;
					allValue[k][2] = blackValue[i][j] + whiteValue[i][j] + staticValue[i][j];
					k++;
				}
			}
		}
		sort(allValue);
		int size = k < SAMPLE_NUMBER ? k : SAMPLE_NUMBER;
		int valuablePositions[][] = new int[size][3];
		//
		for (i = 0; i < size; i++) {
			valuablePositions[i][0] = allValue[i][0];
			valuablePositions[i][1] = allValue[i][1];
			valuablePositions[i][2] = allValue[i][2];
		}
		return valuablePositions;
	}

	private void sort(int[][] allValue) {

		for (int i = 0; i < allValue.length - 1; i++) {
			for (int j = 0; j < allValue.length - 1; j++) {
				int ti, tj, tvalue;
				if (allValue[j][2] < allValue[j + 1][2]) {
					tvalue = allValue[j][2];
					allValue[j][2] = allValue[j + 1][2];
					allValue[j + 1][2] = tvalue;
					ti = allValue[j][0];
					allValue[j][0] = allValue[j + 1][0];
					allValue[j + 1][0] = ti;
					tj = allValue[j][1];
					allValue[j][1] = allValue[j + 1][1];
					allValue[j + 1][1] = tj;
				}
			}
		}
	}

	private void getTheSpaceValues() {
		int l,t,r,b;
		l=(cb.left>2) ? cb.left-2:0;
		t=(cb.top>2) ? cb.top-2:0;
		r=(cb.right<cb.Cols-1) ? cb.right+2:cb.Cols;
		b=(cb.bottom<cb.Rows-1) ? cb.bottom+2:cb.Rows;
		for(int i=l;i<=r;i++) {
			for(int j=t;j<=b;j++) {
				blackValue[i][j]=0;
				whiteValue[i][j]=0;
				if(cb.boardStatus[i][j]==0) {
					for (int m = 1; m <= 4; m++) {
						blackValue[i][j] += evaluateValue(1, i, j, m);
						whiteValue[i][j] += evaluateValue(2, i, j, m);
					}
				}
			}
		}
	}
	int[] getTheBestPosition() {
		getTheSpaceValues();
		int maxValue = -LARGE_NUMBER;
		int value;
		int[] position = new int[2];
		int valuablePositions[][] = getTheMostValuablePositions();
		for (int i = 0; i < valuablePositions.length; i++) {

			if (valuablePositions[i][2] >= FIVE) {
				position[0] = valuablePositions[i][0];
				position[1] = valuablePositions[i][1];
				break;
			}
			cb.boardStatus[valuablePositions[i][0]][valuablePositions[i][1]] = cb.computerColor;
			
			int oldLeft=cb.left;
			int oldTop=cb.top;
			int oldRight=cb.right;
			int oldBottom=cb.bottom;
			if(cb.left>valuablePositions[i][0]) cb.left=valuablePositions[i][0];
			if(cb.top>valuablePositions[i][1]) cb.top=valuablePositions[i][1];
			if(cb.right>valuablePositions[i][0]) cb.right=valuablePositions[i][0];
			if(cb.bottom>valuablePositions[i][1]) cb.bottom=valuablePositions[i][0];
			
			value = min(SEARCH_DEPTH,-LARGE_NUMBER,LARGE_NUMBER);
			cb.boardStatus[valuablePositions[i][0]][valuablePositions[i][1]] = 0;
			cb.left=oldLeft;
			cb.top=oldTop;
			cb.right=oldRight;
			cb.bottom=oldBottom;
			
			if (value > maxValue) {
				maxValue = value;
				position[0] = valuablePositions[i][0];
				position[1] = valuablePositions[i][1];
			}
		}
		return position;
	}

	private int min(int depth, int alpha, int beta) {
		if(depth==0)
			return evaluateGame();
		getTheSpaceValues();
		int value;
		int valuablePositions[][] = getTheMostValuablePositions();
		for (int i = 0; i <valuablePositions.length; i++) {
			if(cb.computerColor==1) {
				if(whiteValue[valuablePositions[i][0]][valuablePositions[i][1]]>=FIVE) {
					return -10*FIVE;
				}
			}else {
				if(blackValue[valuablePositions[i][0]][valuablePositions[i][1]]>=FIVE) {
					return -10*FIVE;
				}
			}
			cb.boardStatus[valuablePositions[i][0]][valuablePositions[i][1]]=cb.computerColor==1?2:1;	
			int oldLeft=cb.left;
			int oldTop=cb.top;
			int oldRight=cb.right;
			int oldBottom=cb.bottom;
			if(cb.left>valuablePositions[i][0]) cb.left=valuablePositions[i][0];
			if(cb.top>valuablePositions[i][1]) cb.top=valuablePositions[i][1];
			if(cb.right>valuablePositions[i][0]) cb.right=valuablePositions[i][0];
			if(cb.bottom>valuablePositions[i][1]) cb.bottom=valuablePositions[i][0];
			
			value=max(depth-1,alpha,beta);		
			cb.boardStatus[valuablePositions[i][0]][valuablePositions[i][1]]=0;
			cb.left=oldLeft;
			cb.top=oldTop;
			cb.right=oldRight;
			cb.bottom=oldBottom;
			
			if(value<beta) {
				beta=value;
				if(alpha>=beta) {
					return alpha;
				}
			}
		}
		return beta;
	}
	private int max(int depth, int alpha, int beta) {
		if(depth==0)
			return evaluateGame();
		getTheSpaceValues();
		int value;
		int valuablePositions[][] = getTheMostValuablePositions();
		for (int i = 0; i <valuablePositions.length; i++) {
			if(cb.computerColor==1) {
				if(whiteValue[valuablePositions[i][0]][valuablePositions[i][1]]>=FIVE) {
					return 10*FIVE;
				}
			}else {
				if(blackValue[valuablePositions[i][0]][valuablePositions[i][1]]>=FIVE) {
					return 10*FIVE;
				}
			}
			cb.boardStatus[valuablePositions[i][0]][valuablePositions[i][1]]=cb.computerColor;	
			
			int oldLeft=cb.left;
			int oldTop=cb.top;
			int oldRight=cb.right;
			int oldBottom=cb.bottom;
			if(cb.left>valuablePositions[i][0]) cb.left=valuablePositions[i][0];
			if(cb.top>valuablePositions[i][1]) cb.top=valuablePositions[i][1];
			if(cb.right>valuablePositions[i][0]) cb.right=valuablePositions[i][0];
			if(cb.bottom>valuablePositions[i][1]) cb.bottom=valuablePositions[i][0];
			
			value=min(depth-1,alpha,beta);		
			cb.boardStatus[valuablePositions[i][0]][valuablePositions[i][1]]=0;
			cb.left=oldLeft;
			cb.top=oldTop;
			cb.right=oldRight;
			cb.bottom=oldBottom;
			
			if(value>alpha) {
				alpha=value;
				if(alpha>=beta) {
					return beta;
				}
			}
		}
		return alpha;
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
			} else if ((spaceCount1 == 1 && chessCount2 == 1 && spaceCount2 > 0 && spaceCount3 > 0)
					|| (spaceCount3 == 1 && chessCount3 == 1 && spaceCount1 > 0 && spaceCount4 > 0)) {
				value = HUO_THREE;
			} else {
				value = HUO_TWO;
			}
			break;
		case 1:
			if ((spaceCount1 == 1 && chessCount2 >= 3) || spaceCount3 == 1 && chessCount3 >= 3) {
				value = CHONG_FOUR;
			} else if ((spaceCount1 == 1 && chessCount2 == 2 && spaceCount2 >= 1 && spaceCount3 >= 1)
					|| (spaceCount3 == 1 && chessCount3 == 2 && spaceCount1 >= 1 && spaceCount4 >= 1)) {
				value = HUO_THREE;

			} else if ((spaceCount1 == 1 && chessCount2 == 2 && (spaceCount2 >= 1 || spaceCount3 >= 1))
					|| (spaceCount3 == 1 && chessCount3 == 2 && (spaceCount1 >= 1 || spaceCount4 >= 1))) {
				value = MIAN_THREE;

			} else if ((spaceCount1 == 1 && chessCount2 == 1 && spaceCount2 > 1 && spaceCount3 > 0)
					|| (spaceCount3 == 1 && chessCount3 == 1 && spaceCount1 > 0 && spaceCount4 > 1)) {
				value = HUO_TWO;
			}
			break;
		default:
			value = 0;
			break;
		}
		return value;
	}
}
