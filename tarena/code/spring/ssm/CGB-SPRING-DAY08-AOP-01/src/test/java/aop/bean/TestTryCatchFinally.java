package aop.bean;

public class TestTryCatchFinally {
	static int doMethod01() {
		int a = 10;
		try {
			a++;
		} finally {
			return a;
		}
	}
	static int doMethod02() {
		int a = 10;
		try {
			return a;
		} finally {
			a++;
		}
	}
	
	static  int method03() {
        int a = 10;
        try {
            // ҵ�����
        	int num = a / 0;
            return a;    // �������ҵ������⣬����е㽫����ִ��
        } catch(Exception e) {
            System.out.println("catch");
            throw new RuntimeException(e);
        } finally {
            ++a;
            System.out.println("finally");
            return a;
        }
    }
	
	public static void main(String[] args) {
		int result = method03();
		System.out.println(result);
	}
}
