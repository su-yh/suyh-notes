package beans;

import java.util.Date;
import java.util.Map;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestBeans {
	public static void main(String[] args) {
		// 1. ��ʼ��Spring ��������
		// ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
		
		System.out.println(ctx);
		// 2. ��ȡ������Ҫ�Ķ���
		Date date1 = (Date)ctx.getBean("date1");
		System.out.println(date1);
		
		Date date2 = ctx.getBean("date1", Date.class);
		Map map = ctx.getBean("map1", Map.class);
		System.out.println(map);
		
		// HelloService û��ʵ���޲ι���ʱ��������������
		 HelloService hs = ctx.getBean("helloService", HelloService.class);
		 System.out.println(hs);
		
		// 3. �ͷ���Դ
		ctx.close();
	}
}
