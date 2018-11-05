package cn.tedu.junit;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Scanner;

/**
 * �û��������������������һ�������������test ��ͷ�ķ�����������ִ��
 * 
 * @author suyh
 *
 */
public class Junit3Demo {
    public static void main(String[] args) throws ClassNotFoundException, InstantiationException,
            IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        System.out.println("����������: ");

        Scanner sc = new Scanner(System.in);

        String className = sc.nextLine();
        Class cls = Class.forName(className);
        Object obj = cls.newInstance();
        Method[] methods = cls.getDeclaredMethods();

        // �������з������жϷ����Ƿ���test ��ͷ���������ִ�д˷���
        for (Method m : methods) {
            if (m.getName().startsWith("test")) {
                // ִ�д˷���
                Object val = m.invoke(obj); // ����һ���÷������ڵĶ���, �������������б�
                System.out.println(val);
            }
        }

        sc.close();
    }
}

class Coo {
    public void testA() {
        System.out.println("ִ��A����");
    }

    public void methodB() {
        System.out.println("ִ��B����");
    }

    public int testC() {
        return 1;
    }
}
