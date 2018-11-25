package cn.tedu.lambda;

public class Demo01 {
    public static void main(String[] args) {
        /**
         * Lambda ʵ�ֹ����Խӿ�
         */
        // ʵ�ֹ����Խӿ�
        Foo foo = new Foo() {
            public int test(int a, int b) {
                return a + b;
            }
        };
        
        Foo f2 = (int a, int b) -> { return a + b; };
        
        System.out.println(foo.test(4, 5));
        System.out.println(f2.test(10, 100));
    }
}

// �����Խӿ�
interface Foo {
    int test(int a, int b);
}

