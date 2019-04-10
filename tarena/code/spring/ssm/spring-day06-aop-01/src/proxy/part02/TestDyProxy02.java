package proxy.part02;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class TestDyProxy02 {
	static class ServiceHandler implements InvocationHandler {
		private Object target;	// Ŀ�����
		
		public ServiceHandler(Object target) {
			this.target = target;
		}
		
		@Override
		/**
		 * @param proxy ָ��������
		 * @param method ָ��ӿ��еķ�������
		 * @param args ָ��method ����ִ��ʱ��Ҫ��ʵ�ʲ���
		 */
		public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
			System.out.println("begin transaction");
			Object result = method.invoke(target, args);	// ����ҵ��
			System.out.println("end transaction");
			return result;
		}
	}
	
	
	public static void main(String[] args) {
		// Ŀ�����
		UserService userService = new UserServiceImpl();
		
//		System.out.println(UserServiceImpl.class == userService.getClass());
		
		// Ϊ���Ŀ����󴴽�һ���������
//		ClassLoader loader = UserServiceImpl.class.getClassLoader();
		ClassLoader loader = userService.getClass().getClassLoader();
		Class<?>[] interfaces = userService.getClass().getInterfaces();
		InvocationHandler handler = new ServiceHandler(userService);
		UserService proxy = (UserService) Proxy.newProxyInstance(
				loader,	// Ŀ������������ (Ŀ����������ʵ��ĳЩ�ӿ�)
				interfaces, // Ŀ�����ʵ������Щ�ӿ�
				handler);	// ������: ҵ������
		
		proxy.saveUser("����");
	}
}
