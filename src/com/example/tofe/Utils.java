package com.example.tofe;

import java.util.Arrays;

public class Utils {

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
}
