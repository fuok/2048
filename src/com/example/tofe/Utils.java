package com.example.tofe;

import java.util.Arrays;

import android.content.Context;

public class Utils {
	/**
	 * 获取一个随机数
	 *
	 */
	public static int getNumber1(final int Max, final int Min) {

		return (int) Math.round(Math.random() * (Max - Min) + Min);
	}

	/**
	 * 获取随机数数组（不重复）
	 * 
	 * @param count
	 *            需要获取的随机数数量
	 */
	public static int[] getNumber2(final int count, final int Max, final int Min) {// Math.round(Math.random()*(Max-Min)+Min)
		int[] randomArray = new int[count];
		// for (int i = 0; i < randomArray.length; i++) {
		// long num=Math.round(Math.random()*(Max-Min)+Min);
		// int x=Arrays.binarySearch(randomArray, (int)num);
		// if(x==-1){//判断是不是重复的数
		// randomArray[]
		// }
		// }
		int i = 0;
		do {
			long num = Math.round(Math.random() * (Max - Min) + Min);
			int x = Arrays.binarySearch(randomArray, (int) num);
			if (x < 0) {// 判断是不是重复的数
				randomArray[i] = (int) num;
				i += 1;
				// Log.w("liuy", "随机数"+i+"="+(int)num);
			} else {
				// Log.w("liuy",
				// "随机数重复!数值为="+(int)num+",位置是:"+x);//这里有时会返回提示重复的数，但从日志来看是没有重复的，我不太懂
			}
		} while (i < randomArray.length);

		return randomArray;

	}

	/** 获取item颜色 */
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
