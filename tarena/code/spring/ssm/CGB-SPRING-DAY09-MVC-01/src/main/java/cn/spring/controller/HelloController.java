package cn.spring.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

// Spring MVC �еĺ�˿�����
// ��������: 
// 1) client request --> tomcat
// 2) tomcat --> DispatcherServlet (web.xml)
// 3) DispatcherServlet --> (����ӳ���ϵ: spring_mvc.xml) --> HelloController
// 4) HelloController  -->  handleRequest(..)
// 5) ...
public class HelloController implements Controller {

	// ���ڴ�������
	@Override
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// ����ģ�ͺ���ͼ����(����: ��װ����)
		ModelAndView mv = new ModelAndView();
//		request.setAttribute(arg0, arg1);	// ���ƴ˹���
		mv.addObject("msg", "Hello SpingMVC");
		// ���ó������ݵ�ҳ��(���� hello.jsp)
		mv.setViewName("hello");
		return mv; // (���ݣ���ͼ)
	}

}
