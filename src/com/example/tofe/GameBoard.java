package com.example.tofe;

import java.util.Arrays;

import android.util.Log;

public class GameBoard {

	public int[][] dimensionArray = new int[4][4];// 一级数列为纵，二级数列为横

	public GameBoard() {
		for (int i = 0; i < dimensionArray.length; i++) {
			for (int j = 0; j < dimensionArray[i].length; j++) {
				// Log.w("liuy", i + "," + j);
				dimensionArray[i][j] = 2;// 为了便于测试，我把棋盘写满了2，应该是-1.TODO
			}
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

	public void plusLeft() {// 向左移动数字，从左到右逐行取值
		int n = 4;// 4行
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
			for (int j = 0, k = 0; j < array.length; j++) {
				if (array[j] != -1) {
					arrayWithoutNull1[k] = array[j];
					k++;
				}
			}
			Log.w("liuy", "检测" + Arrays.toString(arrayWithoutNull1));
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

	}
}
