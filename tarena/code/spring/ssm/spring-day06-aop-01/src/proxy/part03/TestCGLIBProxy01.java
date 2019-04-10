package proxy.part03;

import java.lang.reflect.Method;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.InvocationHandler;

class MsgService {
	public void sendMsg(String msg) {
		System.out.println(msg);
	}
}

public class TestCGLIBProxy01 {
	public static void main(String[] args) {
		// 1. ����Ŀ�����
		MsgService msgService = new MsgService();
		
		// 2. �����������
		// 2.1 ����һ��������ǿ��(���𴴽�����)
		Enhancer e = new Enhancer();
		e.setClassLoader(msgService.getClass().getClassLoader());
//		e.setInterfaces(msgService.getClass().getInterfaces());
		e.setSuperclass(msgService.getClass());
		e.setCallback(new InvocationHandler() {
			@Override
			public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
				System.out.println("begin");
				Object result = method.invoke(msgService, args);
				System.out.println("end");
				return result;
			}
		});
		
		MsgService proxy = (MsgService)e.create();
		
		// 3. ִ�д������ҵ��
		proxy.sendMsg("hello cglib proxy");
	}
}
