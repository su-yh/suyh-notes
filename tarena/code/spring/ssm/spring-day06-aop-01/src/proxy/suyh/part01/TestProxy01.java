package proxy.suyh.part01;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class TestProxy01 {
	public static void main(String[] args) {
		// 1. ����һ��Ŀ�������
		IMsgService msgService = new MsgServiceImpl();
		// 2. ʵ�ִ���
		ClassLoader loader = msgService.getClass().getClassLoader();
		Class<?>[] interfaces = msgService.getClass().getInterfaces();
		InvocationHandler handler = new InvocationHandler() {
			
			@Override
			public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
				System.out.println("start trancation");
				Object result = method.invoke(msgService, args);
				System.out.println("end trancation");
				return result;
			}
		};
		IMsgService proxy = (IMsgService) Proxy.newProxyInstance(loader, interfaces, handler);
		// 3. ͨ�����������ʵ��Ŀ�������չ����
		proxy.sendMsg("hello proxy");
	}
}
