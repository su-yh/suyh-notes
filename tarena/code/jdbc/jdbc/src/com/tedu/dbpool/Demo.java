package com.tedu.dbpool;

// ����һ��Phone �ӿ�
interface Phone {
    public void call();
    public void message();
}

class IPhone implements Phone {

    @Override
    public void call() {
        System.out.println("�ֻ����Դ�绰��");
    }

    @Override
    public void message() {
        System.out.println("�ֻ����Է�������");
    }
    
}

// дһ���� RingIPhone �̳� IPhone ��
// ��дcall ����Ϊ������
class RingIPhone extends IPhone {
    @Override
    public void call() {
        System.out.println("�ֻ�������������...");
    }
}


public class Demo {
    public static void main(String[] args) {
        Phone iphone = new IPhone();
        
        iphone.call();
        iphone.message();
        // �������iphone �����ϵ�call ��������Ϊ�����壬�����Ǵ�绰
        
        Phone ringIPone = new RingIPhone();
        
        ringIPone.call();
    }
}
