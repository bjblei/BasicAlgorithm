package com.example.mavenGit;

/**
 * 整体思想：巧妙复用了二进制数据数位（倒序）+数组下表+指定的int有序数（1～9）
 * 使得每一行的多个备选整数（1～9）转换成一个9位二进制数==》整行的多个空格可以共享这个数,
 * 并且可以修改或重置（擦初）指定数（特定位置0或1）
 * @version 1.0
 * @auth leibao
 * @date 2019/12/21
 */
public class SoudoKu {
	final int N = 9;
	//每一行/每一列 使用1～9的情况用1或者0标记===》形成一个0~2^9 - 1的十进制整数
	int[] row = new int[N], col = new int[N];
	int[][] cell = new int[3][3];
	/**
	 * bitNum数组表示0~2^9 - 1的整数中二进制表示中1的个数:如 bitNum[7] = 3 bitNum[8] = 1
	 * mapPow2Index数组表示2的整数次幂中二进制1所在位置（从序号0开始,低位到高位）
	 * 如 mapPow2Index[1] = 0=2^0,mapPow2Index[2] = 1=2^1, mapPow2Index[4] = 2=2^2
	 */
	int[] bitNum = new int[1 << N];
	int[] mapPow2Index = new int[1 << N];

	public SoudoKu() {
	}

	public void solveSudoku(char[][] board) {
		init();
		int cnt = fill_state(board);
		dfs(cnt, board);
		visitBoard(board);
	}

	void init() {
		//以下2个循环把数组的数初始化为二进制表示9个1，即一开始所以格子都可填
		for (int i = 0; i < N; i++) row[i] = col[i] = (1 << N) - 1;
		for (int i = 0; i < 3; i++)
			for (int j = 0; j < 3; j++)
				cell[i][j] = (1 << N) - 1;
		//2的整数次幂中二进制1的位置
		for (int i = 0; i < N; i++)
			mapPow2Index[1 << i] = i;

		//统计0~2^9 - 1的整数中二进制表示中1的个数
		int nbitNum = 1 << N;
		//保存每一行/列中用到的整数所有可能取值对应的1的位数
		for (int i = 0; i < nbitNum; i++) {
			int n = 0;
			for (int j = i; j != 0; j ^= lowestBit2Decimal(j))
				n++;
			bitNum[i] = n;
		}
	}

	int fill_state(char[][] board) {
		int cnt = 0;    //统计board数组空格('.')的个数
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				if (board[i][j] == '.') {
					cnt++;
					continue;
				}
				//已经填入的整数,对应状态
				int t = board[i][j] - '1';//0~8:二进制位下标
				/**
				 * 假设最初:board[i][j]==9
				 * 原始 9 8 7 6 5 4 3 2 1 标记成二进制是
				 * 111111111（最开始9个1,2^9-1）==》修改为011111111（index为8的那个1改为0）
				 */
				//数独中 i,j位置为数组下标，修改row col cell数组中状态
				change_state(i, j, t);
			}
		}
		return cnt;
	}

	//深度遍历:递归调用
	boolean dfs(int cnt, char[][] board) {
		if (cnt == 0) return true;
		int candidateMinCnt = 10, x = 0, y = 0;
		//剪枝，即找出当前所有空格候选数字个数最少的位置 记为(x,y)
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				if (board[i][j] != '.') {
					continue;
				}
				//当前位置'.'
				int candidateCnt = bitNum[getCandidateNum(i, j)];//可用数字个数
				if (candidateCnt < candidateMinCnt) {
					candidateMinCnt = candidateCnt;
					x = i;
					y = j;
				}

			}
		}
		//遍历当前 x y所有可选数字
		for (int i = getCandidateNum(x, y); i != 0; i ^= lowestBit2Decimal(i)) {
			int minCandidateIndex = mapPow2Index[lowestBit2Decimal(i)];

			change_state(x, y, minCandidateIndex);
			//todo
			board[x][y] = (char) ('1' + minCandidateIndex);//real number:1~9

			//递归调用
			if (dfs(cnt - 1, board)) return true;

			//恢复现场
			change_state(x, y, minCandidateIndex);
			board[x][y] = '.';
		}
		return false;
	}

	void change_state(int x, int y, int t) {
		//按位异或操作:将制定位置t上的值取反，其他位置不变
		row[x] ^= 1 << t;
		col[y] ^= 1 << t;
		cell[x / 3][y / 3] ^= 1 << t;
	}

	//对维护数组x行,y列的3个集合(行、列、九宫格)进行&运算
	//就可以获得可选数字的集合(因为我们用1标识可填数字)
	int getCandidateNum(int x, int y) {
		return row[x] & col[y] & cell[x / 3][y / 3];
	}

	//6 &（-6）===》110 &（010）=010 =》2^1=2
	/**
	 * 截取一个整数对应的二进制最低位1代表的十进制数
	 */
	int lowestBit2Decimal(int x) {
		return -x & x;
	}

	public void visitBoard(char[][] board) {
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[0].length; j++) {
				if (j == 0) {
					System.out.print("[");
				}
				System.out.print(board[i][j]);
				if (j < board[0].length - 1) {
					System.out.print(",");
				}
				if (j == board[0].length - 1) {
					System.out.println("]");
				}
			}
		}
	}

	public static void main(String[] args) {
		char[][] board = new char[9][9];//
		board[0] = new char[]{'5', '3', '.', '.', '7', '.', '.', '.', '.'};
		board[1] = new char[]{'6', '.', '.', '1', '9', '5', '.', '.', '.'};
		board[2] = new char[]{'.', '9', '8', '.', '.', '.', '.', '6', '.'};

		board[3] = new char[]{'8', '.', '.', '.', '6', '.', '.', '.', '3'};
		board[4] = new char[]{'4', '.', '.', '8', '.', '3', '.', '.', '1'};

		board[5] = new char[]{'7', '.', '.', '.', '2', '.', '.', '.', '6'};
		board[6] = new char[]{'.', '6', '.', '.', '.', '.', '2', '8', '.'};

		board[7] = new char[]{'.', '.', '.', '4', '1', '9', '.', '.', '5'};

		board[8] = new char[]{'.', '.', '.', '.', '8', '.', '.', '7', '9'};
		//

		SoudoKu soudoKu = new SoudoKu();
		soudoKu.solveSudoku(board);
	}
}