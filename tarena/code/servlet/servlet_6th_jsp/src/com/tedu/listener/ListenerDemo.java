package com.tedu.listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;

public class ListenerDemo {
    /*
     * ���󣺴���һ�����ڣ��ڴ��������һ����ť��Ϊ��ť�󶨵���¼��������ť�ڿ���̨��ӡ"hello Listener"
     */
    public static void main(String[] args) {
        // 1. ����һ�����ڶ���
        JFrame frame = new JFrame();
        
        // 2. ���ô��ڵĴ�С��λ��
        frame.setSize(350, 250);
        frame.setLocation(400, 300);
        
        // 3. ����һ����ť
        JButton btn = new JButton("��ť");
        
        // 4. ����ť��ӵ�������
        frame.add(btn);
        
        /*
         * Ϊ��ť��ӹ��ܣ������ť�ڿ���̨��ӡ"hello Listener"
         * �¼�Դ: ��ť
         * �¼�: ��ť�����
         * ������: listener
         * ��������ע�ᵽ��ť��֮�󣬼�������һֱ�����Ű�ť��ֻҪ��ť������¼��������ͻ�֪ͨ������������ִ�м������еķ����������¼���
         */
        // ����һ��������ActionListener(��Ϊ������)
        ActionListener listener = new ActionListener() {
            
            @Override
            public void actionPerformed(ActionEvent e) {
                // ����ť��������÷�����ִ��
                System.out.println("hello Listener");
            }
        };
        
        // ��������ע�ᵽ��ť��(ע��󣬼���������һֱ�����������ť)
        btn.addActionListener(listener);
        
        // 5. ���ô��ڿɼ�
        frame.setVisible(true);
    }
}
