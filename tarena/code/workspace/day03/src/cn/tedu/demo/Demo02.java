package cn.tedu.demo;

import java.text.DecimalFormat;
import java.util.Scanner;

public class Demo02 {
	public static void main(String[] args) {
		int score = 99;
		if (score >= 99) {
			System.out.println("A");
		} else if (score >= 80) {
			System.out.println("B");
		} else if (score >= 60) {
			System.out.println("C");
		} else {
			System.out.println("D");
		}
		
//		Scanner input = new Scanner(System.in);
//		System.out.print("��������Ʒ����: ");
//		double singlePrice = input.nextDouble();
//		System.out.print("�����빺���������");
//		int count = input.nextInt();
//		// ����Ӧ�����
//		double total = singlePrice * count;
//		// ��total ���б���С��
//		DecimalFormat df = new DecimalFormat("#0.00");
//		String totalStr = df.format(total);
//		System.out.print("Ӧ�ս��Ϊ��" + totalStr + "��������ʵ����");
//		double pay = input.nextDouble();
//		
//		// ���pay < total���������pay >= total 
//		if (pay < total) {
//			System.out.println("����ʧ�ܣ�");
//		} else {
//			double minus = pay - total;
//			System.out.println(minus);
//			String minusStr = df.format(minus);
//			System.out.println("����ɹ������㣺��" + minusStr);
//		}
		
		DecimalFormat df2 = new DecimalFormat("00.00");
		double d1 = 12.3456;
		double d2 = 2.34567;
		String d1Str = df2.format(d1);
		String d2Str = df2.format(d2);
		System.out.println(d1Str);
		System.out.println(d2Str);
		
		DecimalFormat df3 = new DecimalFormat("##.00");
		DecimalFormat df4 = new DecimalFormat("#.0");
		double d3 = 1.235678;	// 1.24
		double d4 = 1.2325677;	// 1.23
		double d5 = 1.01599;	// 1.02
		double d6 = 1.0599;		// 1.1
		String d3Str = df3.format(d3);
		String d4Str = df3.format(d4);
		String d5Str = df3.format(d5);
		String d6Str = df4.format(d6);
		System.out.println(d3Str + " " + d4Str + " " + d5Str + " " + d6Str);

		double dd1 = 1.41111;
		double dd2 = 1.92;
		double dd3 = dd1 - dd2;
		double dd4 = dd2 - dd1;
		System.out.println(dd3);
		System.out.println(dd4);
//		input.close();
	}
}



