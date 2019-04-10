package aop;


import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.project.aspect.LoggingAspect;
import com.project.service.HelloService;
import com.project.service.impl.HelloServiceImpl;

public class TestHelloService {
	// ��ʼ��spring ����
	private ClassPathXmlApplicationContext ctx;
	
	@Before
	public void init() {
		ctx = new ClassPathXmlApplicationContext("beans.xml");
	}
	
	@Test
	public void testSayHello() {
		// helloService ָ���ĸ�����	B)
		// A) HelloServiceImpl
		// B) HelloService
		// C) LogginAspect
		HelloService helloService = ctx.getBean("helloServiceImpl", HelloService.class);
		helloService.sayHello("suyh");
		
		System.out.println(helloService instanceof HelloServiceImpl);
		System.out.println(helloService instanceof LoggingAspect);
		
	}
	
	@After
	public void destroy() {
		ctx.close();
	}
}
