package cn.tedu.demo;

import java.util.Scanner;

public class demo01 {
	public static void main(String[] args) {
		demo01 d = new demo01();
		int dp = d.product(10);
		System.out.println(10 + "�Ľ׳��ǣ�" + dp);
		
		System.out.print("����������������");
		Scanner input = new Scanner(System.in);
		int m = input.nextInt();
		int n = input.nextInt();
		d.size(m, n);
		input.close();
	}
	
	public int product(int num) {
//		System.out.println(num + " �Ľ׳���: ": );
		int nRes = 1;
		for (int n = 1; n <= num; ++n) {
			nRes *= n;
		}
		
		return nRes;
	}
	
	public void size(int m, int n) {
//		int sum = m + n;
	}
}
