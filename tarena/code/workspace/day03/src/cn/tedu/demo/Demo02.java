package cn.tedu.demo;

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
		
		Scanner input = new Scanner(System.in);
		System.out.print("��������Ʒ����: ");
		int singlePrice = input.nextInt();
		System.out.print("�����빺���������");
		int count = input.nextInt();
		// ����Ӧ�����
		int total = singlePrice * count;
		System.out.print("Ӧ�ս��Ϊ��" + total + "��������ʵ����");
		int pay = input.nextInt();
		
		// ���pay < total���������pay >= total 
		if (pay < total) {
			System.out.println("����ʧ�ܣ�");
		} else {
			int minus = pay - total;
			System.out.println("����ɹ������㣺��" + minus );
		}

		input.close();
	}
}
