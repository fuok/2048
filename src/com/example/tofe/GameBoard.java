package com.example.tofe;

import java.util.Arrays;

import com.example.tofe.MainActivity.PlaceholderFragment;

import android.util.Log;

public class GameBoard {

	private int[][] dimensionArray = new int[4][4];// һ������Ϊ�ݣ���������Ϊ��

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
	 * ���������֣�һ����2����������������ӵ����������ͬһ���õķ����ֿ�������Ŀ���ǣ��÷���ֵ��ǳ�����������ӵ�item
	 * 
	 */
	public void randomAdd() {
		// �����ҳ��ɹ�������ӵ�λ�ã�Ҳ���ǿո�
		int n = 0;
		for (int i = 0; i < dimensionArray.length; i++) {
			for (int j = 0; j < dimensionArray[i].length; j++) {
				if (dimensionArray[i][j] == -1) {
					n++;
				}
			}
		}
		Log.w("liuy", "�ո�����" + n);
		if (n > 0) {

			// ���ݿո���������������ҳ�����ȥ�ĸ���
			int randomNum = Utils.getNumber1(n, 1);
			// Log.w("liuy", "�����λ���ǣ�" + Arrays.toString(randomNum));
			for (int x = 0, m = 0; x < dimensionArray.length; x++) {
				for (int y = 0; y < dimensionArray[x].length; y++) {
					if (dimensionArray[x][y] == -1) {
						m++;
						// for (int i = 0; i < randomNum.length; i++) {
						if (randomNum == m) {
							Log.w("liuy", "���");
							dimensionArray[x][y] = 2;// �������ֵ��2
						}
						// }
					}
				}
			}
		} else {
			Log.w("liuy", "������");// ����Ӧ���ǵ������ˣ���Ϊ�ƶ�ʱ�Ѿ��ж���doPlusOrMove�������ƶ���������false�Ͳ����ٵ�randomAdd��
			return;
		}

	}

	/**
	 * ���������֣�һ����2��������2048���򣬿������������������������Ӷ�������
	 * 
	 * @param count
	 *            ��ӵĸ���
	 */
	public void randomAdd(final int count) {
		// �����ҳ��ɹ�������ӵ�λ�ã�Ҳ���ǿո�
		int n = 0;
		for (int i = 0; i < dimensionArray.length; i++) {
			for (int j = 0; j < dimensionArray[i].length; j++) {
				if (dimensionArray[i][j] == -1) {
					n++;
				}
			}
		}
		Log.w("liuy", "�ո�����" + n);
		if (n > 0) {

			// ���ݿո���������������ҳ�����ȥ�ĸ���
			int[] randomNum = Utils.getNumber2(count, n, 1);
			Log.w("liuy", "�����λ���ǣ�" + Arrays.toString(randomNum));
			for (int x = 0, m = 0; x < dimensionArray.length; x++) {
				for (int y = 0; y < dimensionArray[x].length; y++) {
					if (dimensionArray[x][y] == -1) {
						m++;
						for (int i = 0; i < randomNum.length; i++) {
							if (randomNum[i] == m) {
								Log.w("liuy", "���");
								dimensionArray[x][y] = 2;// �������ֵ��2
							}
						}
					}
				}
			}
		} else {
			Log.w("liuy", "������");// ����Ӧ���ǵ������ˣ���Ϊ�ƶ�ʱ�Ѿ��ж���doPlusOrMove�������ƶ���������false�Ͳ����ٵ�randomAdd��
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

	public boolean pullLeft() {// �����ƶ����֣�����������ȡֵ
		int n = 4;// 4��
		boolean doPlusOrMove = false;// ���ڱ�Ǳ����ƶ����Ƿ�����ӳ������ƶ�����
		for (int i = 0; i < n; i++) {
			// ����һ�������鱣��ÿһ�е���λ����
			int[] array = new int[4];
			// ��Ϊ��������Ԫ������ȫ���Ķ�ά���飬���Ը����ƶ�����Ĳ�ͬ��ȡֵ��ԭ������ȫ��һ���ġ�
			array[0] = dimensionArray[i][0];
			array[1] = dimensionArray[i][1];
			array[2] = dimensionArray[i][2];
			array[3] = dimensionArray[i][3];

			// ȥ�����е�-1,�õ�һ����ֵ������ǰ�벿�ֵ�����
			int[] arrayWithoutNull1 = new int[] { -1, -1, -1, -1 };
			// boolean doMove=false;
			for (int j = 0, k = 0; j < array.length; j++) {
				if (array[j] != -1) {
					arrayWithoutNull1[k] = array[j];
					k++;
				}
				if (doPlusOrMove == false && j < array.length - 1) {// �ж��ƶ�����
					if (array[j] == -1 && array[j + 1] != -1) {
						doPlusOrMove = true;
					}
				}
			}
			// Log.w("liuy", "���" + Arrays.toString(arrayWithoutNull1));
			// ����������飬��ͣ���Ϊһ���ĸ���ֵ������һ�ԣ�ʵ�����ǶԱȴ��������鳤����1
			int m = arrayWithoutNull1.length - 1;

			for (int j = 0; j < m; j++) {
				int a = arrayWithoutNull1[j];
				int b = arrayWithoutNull1[j + 1];
				if (a == b && a != -1) {
					a += a;
					b = -1;
					arrayWithoutNull1[j] = a;
					arrayWithoutNull1[j + 1] = b;
					if (doPlusOrMove == false) {// �ж���ӳ���
						doPlusOrMove = true;
					}
				}
			}

			// ȥ�����е�-1,�õ�һ����ֵ������ǰ�벿�ֵ�����,�ó�������ĳ�Աֵ�����Ա����ȫ�ֶ�ά����
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
			Log.w("liuy", "��������");
		} else {
			Log.w("liuy", "���޷��ƶ�");
			PlaceholderFragment.mHandler.sendEmptyMessage(MainActivity.GAME_OVER);
		}
		return doPlusOrMove;

	}

	public boolean pullRight() {// �����ƶ����֣����ҵ�������ȡֵ
		int n = 4;// 4��
		boolean doPlusOrMove = false;// ���ڱ�Ǳ����ƶ����Ƿ�����ӳ������ƶ�����
		for (int i = 0; i < n; i++) {
			// ����һ�������鱣��ÿһ�е���λ����
			int[] array = new int[4];
			// ��Ϊ��������Ԫ������ȫ���Ķ�ά���飬���Ը����ƶ�����Ĳ�ͬ��ȡֵ��ԭ������ȫ��һ���ġ�
			array[0] = dimensionArray[i][3];
			array[1] = dimensionArray[i][2];
			array[2] = dimensionArray[i][1];
			array[3] = dimensionArray[i][0];

			// ȥ�����е�-1,�õ�һ����ֵ������ǰ�벿�ֵ�����
			int[] arrayWithoutNull1 = new int[] { -1, -1, -1, -1 };
			for (int j = 0, k = 0; j < array.length; j++) {
				if (array[j] != -1) {
					arrayWithoutNull1[k] = array[j];
					k++;
				}
				if (doPlusOrMove == false && j < array.length - 1) {// �ж��ƶ�����
					if (array[j] == -1 && array[j + 1] != -1) {
						doPlusOrMove = true;
					}
				}
			}
			// Log.w("liuy", "���" + Arrays.toString(arrayWithoutNull1));
			// ����������飬��ͣ���Ϊһ���ĸ���ֵ������һ�ԣ�ʵ�����ǶԱȴ��������鳤����1
			int m = arrayWithoutNull1.length - 1;
			for (int j = 0; j < m; j++) {
				int a = arrayWithoutNull1[j];
				int b = arrayWithoutNull1[j + 1];
				if (a == b && a != -1) {
					a += a;
					b = -1;
					arrayWithoutNull1[j] = a;
					arrayWithoutNull1[j + 1] = b;
					if (doPlusOrMove == false) {// �ж���ӳ���
						doPlusOrMove = true;
					}
				}
			}

			// ȥ�����е�-1,�õ�һ����ֵ������ǰ�벿�ֵ�����,�ó�������ĳ�Աֵ�����Ա����ȫ�ֶ�ά����
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
			Log.w("liuy", "��������");
		} else {
			Log.w("liuy", "���޷��ƶ�");
			PlaceholderFragment.mHandler.sendEmptyMessage(MainActivity.GAME_OVER);
		}
		return doPlusOrMove;
	}

	public boolean pullUp() {// �����ƶ����֣����ϵ�������ȡֵ
		int n = 4;// 4��
		boolean doPlusOrMove = false;// ���ڱ�Ǳ����ƶ����Ƿ�����ӳ������ƶ�����
		for (int i = 0; i < n; i++) {
			// ����һ�������鱣��ÿһ�е���λ����
			int[] array = new int[4];
			// ��Ϊ��������Ԫ������ȫ���Ķ�ά���飬���Ը����ƶ�����Ĳ�ͬ��ȡֵ��ԭ������ȫ��һ���ġ�
			array[0] = dimensionArray[0][i];
			array[1] = dimensionArray[1][i];
			array[2] = dimensionArray[2][i];
			array[3] = dimensionArray[3][i];

			// ȥ�����е�-1,�õ�һ����ֵ������ǰ�벿�ֵ�����
			int[] arrayWithoutNull1 = new int[] { -1, -1, -1, -1 };
			for (int j = 0, k = 0; j < array.length; j++) {
				if (array[j] != -1) {
					arrayWithoutNull1[k] = array[j];
					k++;
				}
				if (doPlusOrMove == false && j < array.length - 1) {// �ж��ƶ�����
					if (array[j] == -1 && array[j + 1] != -1) {
						doPlusOrMove = true;
					}
				}
			}
			// Log.w("liuy", "���" + Arrays.toString(arrayWithoutNull1));
			// ����������飬��ͣ���Ϊһ���ĸ���ֵ������һ�ԣ�ʵ�����ǶԱȴ��������鳤����1
			int m = arrayWithoutNull1.length - 1;
			for (int j = 0; j < m; j++) {
				int a = arrayWithoutNull1[j];
				int b = arrayWithoutNull1[j + 1];
				if (a == b && a != -1) {
					a += a;
					b = -1;
					arrayWithoutNull1[j] = a;
					arrayWithoutNull1[j + 1] = b;
					if (doPlusOrMove == false) {// �ж���ӳ���
						doPlusOrMove = true;
					}
				}
			}

			// ȥ�����е�-1,�õ�һ����ֵ������ǰ�벿�ֵ�����,�ó�������ĳ�Աֵ�����Ա����ȫ�ֶ�ά����
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
			Log.w("liuy", "��������");
		} else {
			Log.w("liuy", "���޷��ƶ�");
			PlaceholderFragment.mHandler.sendEmptyMessage(MainActivity.GAME_OVER);
		}
		return doPlusOrMove;
	}

	public boolean pullDown() {// �����ƶ����֣����µ�������ȡֵ
		int n = 4;// 4��
		boolean doPlusOrMove = false;// ���ڱ�Ǳ����ƶ����Ƿ�����ӳ������ƶ�����
		for (int i = 0; i < n; i++) {
			// ����һ�������鱣��ÿһ�е���λ����
			int[] array = new int[4];
			// ��Ϊ��������Ԫ������ȫ���Ķ�ά���飬���Ը����ƶ�����Ĳ�ͬ��ȡֵ��ԭ������ȫ��һ���ġ�
			array[0] = dimensionArray[3][i];
			array[1] = dimensionArray[2][i];
			array[2] = dimensionArray[1][i];
			array[3] = dimensionArray[0][i];

			// ȥ�����е�-1,�õ�һ����ֵ������ǰ�벿�ֵ�����
			int[] arrayWithoutNull1 = new int[] { -1, -1, -1, -1 };
			for (int j = 0, k = 0; j < array.length; j++) {
				if (array[j] != -1) {
					arrayWithoutNull1[k] = array[j];
					k++;
				}
				if (doPlusOrMove == false && j < array.length - 1) {// �ж��ƶ�����
					if (array[j] == -1 && array[j + 1] != -1) {
						doPlusOrMove = true;
					}
				}
			}
			// Log.w("liuy", "���" + Arrays.toString(arrayWithoutNull1));
			// ����������飬��ͣ���Ϊһ���ĸ���ֵ������һ�ԣ�ʵ�����ǶԱȴ��������鳤����1
			int m = arrayWithoutNull1.length - 1;
			for (int j = 0; j < m; j++) {
				int a = arrayWithoutNull1[j];
				int b = arrayWithoutNull1[j + 1];
				if (a == b && a != -1) {
					a += a;
					b = -1;
					arrayWithoutNull1[j] = a;
					arrayWithoutNull1[j + 1] = b;
					if (doPlusOrMove == false) {// �ж���ӳ���
						doPlusOrMove = true;
					}
				}
			}

			// ȥ�����е�-1,�õ�һ����ֵ������ǰ�벿�ֵ�����,�ó�������ĳ�Աֵ�����Ա����ȫ�ֶ�ά����
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
			Log.w("liuy", "��������");
		} else {
			Log.w("liuy", "���޷��ƶ�");
			PlaceholderFragment.mHandler.sendEmptyMessage(MainActivity.GAME_OVER);
		}
		return doPlusOrMove;

	}
}
