package com.example.tofe;

import java.util.Arrays;

public class Utils {

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
}
