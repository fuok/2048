package com.example.tofe;

import java.util.Arrays;

import com.example.tofe.MainActivity.PlaceholderFragment;

import android.util.Log;

public class GameBoard {

	private int[][] dimensionArray = new int[4][4];// 一级数列为纵，二级数列为横

	public int[][] getDArray() {
		return dimensionArray;
	}

	public GameBoard() {
		for (int i = 0; i < dimensionArray.length; i++) {
			for (int j = 0; j < dimensionArray[i].length; j++) {
				// Log.w("liuy", i + "," + j);
				dimensionArray[i][j] = -1;
			}
		}
	}

	/**
	 * 随机添加数字（一般是2），这个方法针对添加单个的情况，同一作用的方法分开两个的目的是，用返回值标记出滑动后新添加的item
	 * 
	 */
	public void randomAdd() {
		// 首先找出可供添加新子的位置，也就是空格
		int n = 0;
		for (int i = 0; i < dimensionArray.length; i++) {
			for (int j = 0; j < dimensionArray[i].length; j++) {
				if (dimensionArray[i][j] == -1) {
					n++;
				}
			}
		}
		Log.w("liuy", "空格数：" + n);
		if (n > 0) {

			// 根据空格数量，从中随机找出填充进去的格子
			int randomNum = Utils.getNumber1(n, 1);
			// Log.w("liuy", "填入的位置是：" + Arrays.toString(randomNum));
			for (int x = 0, m = 0; x < dimensionArray.length; x++) {
				for (int y = 0; y < dimensionArray[x].length; y++) {
					if (dimensionArray[x][y] == -1) {
						m++;
						// for (int i = 0; i < randomNum.length; i++) {
						if (randomNum == m) {
							Log.w("liuy", "填充");
							dimensionArray[x][y] = 2;// 填入的数值是2
						}
						// }
					}
				}
			}
		} else {
			Log.w("liuy", "填满了");// 这里应该是到不了了，因为移动时已经判断了doPlusOrMove，以下移动方法返回false就不会再调randomAdd了
			return;
		}

	}

	/**
	 * 随机添加数字（一般是2），根据2048规则，开局添加两个，这个方法针对添加多个的情况
	 * 
	 * @param count
	 *            添加的个数
	 */
	public void randomAdd(final int count) {
		// 首先找出可供添加新子的位置，也就是空格
		int n = 0;
		for (int i = 0; i < dimensionArray.length; i++) {
			for (int j = 0; j < dimensionArray[i].length; j++) {
				if (dimensionArray[i][j] == -1) {
					n++;
				}
			}
		}
		Log.w("liuy", "空格数：" + n);
		if (n > 0) {

			// 根据空格数量，从中随机找出填充进去的格子
			int[] randomNum = Utils.getNumber2(count, n, 1);
			Log.w("liuy", "填入的位置是：" + Arrays.toString(randomNum));
			for (int x = 0, m = 0; x < dimensionArray.length; x++) {
				for (int y = 0; y < dimensionArray[x].length; y++) {
					if (dimensionArray[x][y] == -1) {
						m++;
						for (int i = 0; i < randomNum.length; i++) {
							if (randomNum[i] == m) {
								Log.w("liuy", "填充");
								dimensionArray[x][y] = 2;// 填入的数值是2
							}
						}
					}
				}
			}
		} else {
			Log.w("liuy", "填满了");// 这里应该是到不了了，因为移动时已经判断了doPlusOrMove，以下移动方法返回false就不会再调randomAdd了
			return;
		}

	}

	public String toString() {
		for (int i = 0; i < dimensionArray.length; i++) {
			StringBuffer buffer = new StringBuffer();
			for (int j = 0; j < dimensionArray[i].length; j++) {
				String str = dimensionArray[i][j] + ",";
				buffer.append(str);
			}
			Log.w("liuy", buffer.toString());
		}
		return "";

	}

	public boolean pullLeft() {// 向左移动数字，从左到右逐行取值
		int n = 4;// 4行
		boolean doPlusOrMove = false;// 用于标记本次移动中是否有相加成立或移动成立
		for (int i = 0; i < n; i++) {
			// 声明一个新数组保存每一行的四位数字
			int[] array = new int[4];
			// 因为这组数组元素来自全量的二维数组，所以根据移动方向的不同，取值的原则是完全不一样的。
			array[0] = dimensionArray[i][0];
			array[1] = dimensionArray[i][1];
			array[2] = dimensionArray[i][2];
			array[3] = dimensionArray[i][3];

			// 去掉其中的-1,得到一个正值集中在前半部分的数组
			int[] arrayWithoutNull1 = new int[] { -1, -1, -1, -1 };
			// boolean doMove=false;
			for (int j = 0, k = 0; j < array.length; j++) {
				if (array[j] != -1) {
					arrayWithoutNull1[k] = array[j];
					k++;
				}
				if (doPlusOrMove == false && j < array.length - 1) {// 判断移动成立
					if (array[j] == -1 && array[j + 1] != -1) {
						doPlusOrMove = true;
					}
				}
			}
			// Log.w("liuy", "检测" + Arrays.toString(arrayWithoutNull1));
			// 遍历这个数组，求和，因为一共四个数值，两两一对，实际上是对比次数比数组长度少1
			int m = arrayWithoutNull1.length - 1;

			for (int j = 0; j < m; j++) {
				int a = arrayWithoutNull1[j];
				int b = arrayWithoutNull1[j + 1];
				if (a == b && a != -1) {
					a += a;
					b = -1;
					arrayWithoutNull1[j] = a;
					arrayWithoutNull1[j + 1] = b;
					if (doPlusOrMove == false) {// 判断相加成立
						doPlusOrMove = true;
					}
				}
			}

			// 去掉其中的-1,得到一个正值集中在前半部分的数组,得出的数组的成员值，可以保存回全局二维数组
			int[] arrayWithoutNull2 = new int[] { -1, -1, -1, -1 };
			for (int j = 0, k = 0; j < arrayWithoutNull1.length; j++) {
				if (arrayWithoutNull1[j] != -1) {
					arrayWithoutNull2[k] = arrayWithoutNull1[j];
					k++;
				}
			}

			// Log.w("liuy", array[i] + "");
			dimensionArray[i][0] = arrayWithoutNull2[0];
			dimensionArray[i][1] = arrayWithoutNull2[1];
			dimensionArray[i][2] = arrayWithoutNull2[2];
			dimensionArray[i][3] = arrayWithoutNull2[3];
		}
		if (doPlusOrMove) {
			Log.w("liuy", "产生新数");
		} else {
			Log.w("liuy", "已无法移动");
			PlaceholderFragment.mHandler.sendEmptyMessage(MainActivity.GAME_OVER);
		}
		return doPlusOrMove;

	}

	public boolean pullRight() {// 向右移动数字，从右到左逐行取值
		int n = 4;// 4行
		boolean doPlusOrMove = false;// 用于标记本次移动中是否有相加成立或移动成立
		for (int i = 0; i < n; i++) {
			// 声明一个新数组保存每一行的四位数字
			int[] array = new int[4];
			// 因为这组数组元素来自全量的二维数组，所以根据移动方向的不同，取值的原则是完全不一样的。
			array[0] = dimensionArray[i][3];
			array[1] = dimensionArray[i][2];
			array[2] = dimensionArray[i][1];
			array[3] = dimensionArray[i][0];

			// 去掉其中的-1,得到一个正值集中在前半部分的数组
			int[] arrayWithoutNull1 = new int[] { -1, -1, -1, -1 };
			for (int j = 0, k = 0; j < array.length; j++) {
				if (array[j] != -1) {
					arrayWithoutNull1[k] = array[j];
					k++;
				}
				if (doPlusOrMove == false && j < array.length - 1) {// 判断移动成立
					if (array[j] == -1 && array[j + 1] != -1) {
						doPlusOrMove = true;
					}
				}
			}
			// Log.w("liuy", "检测" + Arrays.toString(arrayWithoutNull1));
			// 遍历这个数组，求和，因为一共四个数值，两两一对，实际上是对比次数比数组长度少1
			int m = arrayWithoutNull1.length - 1;
			for (int j = 0; j < m; j++) {
				int a = arrayWithoutNull1[j];
				int b = arrayWithoutNull1[j + 1];
				if (a == b && a != -1) {
					a += a;
					b = -1;
					arrayWithoutNull1[j] = a;
					arrayWithoutNull1[j + 1] = b;
					if (doPlusOrMove == false) {// 判断相加成立
						doPlusOrMove = true;
					}
				}
			}

			// 去掉其中的-1,得到一个正值集中在前半部分的数组,得出的数组的成员值，可以保存回全局二维数组
			int[] arrayWithoutNull2 = new int[] { -1, -1, -1, -1 };
			for (int j = 0, k = 0; j < arrayWithoutNull1.length; j++) {
				if (arrayWithoutNull1[j] != -1) {
					arrayWithoutNull2[k] = arrayWithoutNull1[j];
					k++;
				}
			}

			// Log.w("liuy", array[i] + "");
			dimensionArray[i][3] = arrayWithoutNull2[0];
			dimensionArray[i][2] = arrayWithoutNull2[1];
			dimensionArray[i][1] = arrayWithoutNull2[2];
			dimensionArray[i][0] = arrayWithoutNull2[3];
		}
		if (doPlusOrMove) {
			Log.w("liuy", "产生新数");
		} else {
			Log.w("liuy", "已无法移动");
			PlaceholderFragment.mHandler.sendEmptyMessage(MainActivity.GAME_OVER);
		}
		return doPlusOrMove;
	}

	public boolean pullUp() {// 向上移动数字，从上到下逐列取值
		int n = 4;// 4列
		boolean doPlusOrMove = false;// 用于标记本次移动中是否有相加成立或移动成立
		for (int i = 0; i < n; i++) {
			// 声明一个新数组保存每一行的四位数字
			int[] array = new int[4];
			// 因为这组数组元素来自全量的二维数组，所以根据移动方向的不同，取值的原则是完全不一样的。
			array[0] = dimensionArray[0][i];
			array[1] = dimensionArray[1][i];
			array[2] = dimensionArray[2][i];
			array[3] = dimensionArray[3][i];

			// 去掉其中的-1,得到一个正值集中在前半部分的数组
			int[] arrayWithoutNull1 = new int[] { -1, -1, -1, -1 };
			for (int j = 0, k = 0; j < array.length; j++) {
				if (array[j] != -1) {
					arrayWithoutNull1[k] = array[j];
					k++;
				}
				if (doPlusOrMove == false && j < array.length - 1) {// 判断移动成立
					if (array[j] == -1 && array[j + 1] != -1) {
						doPlusOrMove = true;
					}
				}
			}
			// Log.w("liuy", "检测" + Arrays.toString(arrayWithoutNull1));
			// 遍历这个数组，求和，因为一共四个数值，两两一对，实际上是对比次数比数组长度少1
			int m = arrayWithoutNull1.length - 1;
			for (int j = 0; j < m; j++) {
				int a = arrayWithoutNull1[j];
				int b = arrayWithoutNull1[j + 1];
				if (a == b && a != -1) {
					a += a;
					b = -1;
					arrayWithoutNull1[j] = a;
					arrayWithoutNull1[j + 1] = b;
					if (doPlusOrMove == false) {// 判断相加成立
						doPlusOrMove = true;
					}
				}
			}

			// 去掉其中的-1,得到一个正值集中在前半部分的数组,得出的数组的成员值，可以保存回全局二维数组
			int[] arrayWithoutNull2 = new int[] { -1, -1, -1, -1 };
			for (int j = 0, k = 0; j < arrayWithoutNull1.length; j++) {
				if (arrayWithoutNull1[j] != -1) {
					arrayWithoutNull2[k] = arrayWithoutNull1[j];
					k++;
				}
			}

			// Log.w("liuy", array[i] + "");
			dimensionArray[0][i] = arrayWithoutNull2[0];
			dimensionArray[1][i] = arrayWithoutNull2[1];
			dimensionArray[2][i] = arrayWithoutNull2[2];
			dimensionArray[3][i] = arrayWithoutNull2[3];
		}
		if (doPlusOrMove) {
			Log.w("liuy", "产生新数");
		} else {
			Log.w("liuy", "已无法移动");
			PlaceholderFragment.mHandler.sendEmptyMessage(MainActivity.GAME_OVER);
		}
		return doPlusOrMove;
	}

	public boolean pullDown() {// 向下移动数字，从下到上逐列取值
		int n = 4;// 4列
		boolean doPlusOrMove = false;// 用于标记本次移动中是否有相加成立或移动成立
		for (int i = 0; i < n; i++) {
			// 声明一个新数组保存每一行的四位数字
			int[] array = new int[4];
			// 因为这组数组元素来自全量的二维数组，所以根据移动方向的不同，取值的原则是完全不一样的。
			array[0] = dimensionArray[3][i];
			array[1] = dimensionArray[2][i];
			array[2] = dimensionArray[1][i];
			array[3] = dimensionArray[0][i];

			// 去掉其中的-1,得到一个正值集中在前半部分的数组
			int[] arrayWithoutNull1 = new int[] { -1, -1, -1, -1 };
			for (int j = 0, k = 0; j < array.length; j++) {
				if (array[j] != -1) {
					arrayWithoutNull1[k] = array[j];
					k++;
				}
				if (doPlusOrMove == false && j < array.length - 1) {// 判断移动成立
					if (array[j] == -1 && array[j + 1] != -1) {
						doPlusOrMove = true;
					}
				}
			}
			// Log.w("liuy", "检测" + Arrays.toString(arrayWithoutNull1));
			// 遍历这个数组，求和，因为一共四个数值，两两一对，实际上是对比次数比数组长度少1
			int m = arrayWithoutNull1.length - 1;
			for (int j = 0; j < m; j++) {
				int a = arrayWithoutNull1[j];
				int b = arrayWithoutNull1[j + 1];
				if (a == b && a != -1) {
					a += a;
					b = -1;
					arrayWithoutNull1[j] = a;
					arrayWithoutNull1[j + 1] = b;
					if (doPlusOrMove == false) {// 判断相加成立
						doPlusOrMove = true;
					}
				}
			}

			// 去掉其中的-1,得到一个正值集中在前半部分的数组,得出的数组的成员值，可以保存回全局二维数组
			int[] arrayWithoutNull2 = new int[] { -1, -1, -1, -1 };
			for (int j = 0, k = 0; j < arrayWithoutNull1.length; j++) {
				if (arrayWithoutNull1[j] != -1) {
					arrayWithoutNull2[k] = arrayWithoutNull1[j];
					k++;
				}
			}

			// Log.w("liuy", array[i] + "");
			dimensionArray[3][i] = arrayWithoutNull2[0];
			dimensionArray[2][i] = arrayWithoutNull2[1];
			dimensionArray[1][i] = arrayWithoutNull2[2];
			dimensionArray[0][i] = arrayWithoutNull2[3];
		}
		if (doPlusOrMove) {
			Log.w("liuy", "产生新数");
		} else {
			Log.w("liuy", "已无法移动");
			PlaceholderFragment.mHandler.sendEmptyMessage(MainActivity.GAME_OVER);
		}
		return doPlusOrMove;

	}
}
