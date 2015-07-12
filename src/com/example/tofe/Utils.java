package com.example.tofe;

import java.util.Arrays;

import android.content.Context;

public class Utils {
	/**
	 * ��ȡһ�������
	 *
	 */
	public static int getNumber1(final int Max, final int Min) {

		return (int) Math.round(Math.random() * (Max - Min) + Min);
	}

	/**
	 * ��ȡ��������飨���ظ���
	 * 
	 * @param count
	 *            ��Ҫ��ȡ�����������
	 */
	public static int[] getNumber2(final int count, final int Max, final int Min) {// Math.round(Math.random()*(Max-Min)+Min)
		int[] randomArray = new int[count];
		// for (int i = 0; i < randomArray.length; i++) {
		// long num=Math.round(Math.random()*(Max-Min)+Min);
		// int x=Arrays.binarySearch(randomArray, (int)num);
		// if(x==-1){//�ж��ǲ����ظ�����
		// randomArray[]
		// }
		// }
		int i = 0;
		do {
			long num = Math.round(Math.random() * (Max - Min) + Min);
			int x = Arrays.binarySearch(randomArray, (int) num);
			if (x < 0) {// �ж��ǲ����ظ�����
				randomArray[i] = (int) num;
				i += 1;
				// Log.w("liuy", "�����"+i+"="+(int)num);
			} else {
				// Log.w("liuy",
				// "������ظ�!��ֵΪ="+(int)num+",λ����:"+x);//������ʱ�᷵����ʾ�ظ�������������־������û���ظ��ģ��Ҳ�̫��
			}
		} while (i < randomArray.length);

		return randomArray;

	}

	/** ��ȡitem��ɫ */
	public static int getColor(final int number) {
		int color;
		switch (number) {
		case 2:
			color = R.drawable.shape_item_2;
			break;
		case 4:
			color = R.drawable.shape_item_4;
			break;
		case 8:
			color = R.drawable.shape_item_8;
			break;
		case 16:
			color = R.drawable.shape_item_16;
			break;
		case 32:
			color = R.drawable.shape_item_32;
			break;
		case 64:
			color = R.drawable.shape_item_64;
			break;
		case 128:
			color = R.drawable.shape_item_128;
			break;
		case 256:
			color = R.drawable.shape_item_256;
			break;
		case 512:
			color = R.drawable.shape_item_512;
			break;
		case 1024:
			color = R.drawable.shape_item_1024;
			break;
		case 2048:
			color = R.drawable.shape_item_2048;
			break;
		case 4096:
			color = R.drawable.shape_item_4096;
			break;
		default:
			color = R.drawable.shape_item_8192;
			break;
		}
		return color;
	}
}
