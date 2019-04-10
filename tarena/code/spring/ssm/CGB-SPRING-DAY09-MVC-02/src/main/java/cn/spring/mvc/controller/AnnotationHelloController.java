package cn.spring.mvc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

//http://localhost:80/[��Ŀ����]/hello/doSayHello.do
// hello --> annotationHelloController
@RequestMapping("/hello/")
@Controller
public class AnnotationHelloController {

	// @param Model ���ڷ�װ����
	// http://localhost:80/[��Ŀ����]/[��ӳ��@RequestMapping]/doSayHello.do
	// SimpleUrlHandlerMapping
	// ͨ��@RequestMapping ע�ⶨ��ӳ���ϵ
	@RequestMapping(value="doSayHello")
	public String doSayHello(Model model) {
		model.addAttribute("msg", "Hello AnnotationHelloController");
		System.out.println("model: " + model);
		return "hello"; // ���ַ����ύ����ͼ������
	}

	// http://localhost:80/[��Ŀ����]/[��ӳ��@RequestMapping]/doSaveMsg.do?msg=message
	@RequestMapping("doSaveMsg")
	public ModelAndView doSaveMsg(String msg) {
		System.out.println("msg = " + msg);
		ModelAndView mv = new ModelAndView();
		mv.setViewName("success");
		mv.addObject("msg", msg);
		return mv; // /WEB-INF/pages/success.jsp
	}

	// http://localhost:80/[��Ŀ����]/[��ӳ��@RequestMapping]/doSayMsg/message.do
	// http://localhost:80/[��Ŀ����]/[��ӳ��@RequestMapping]/doSayMsg/aaaaaaa.do
	// @RequestMapping("doSayMsg/{?}")
	// public ModelAndView doSayMsg(@PathVariable("?") String msg) {
	// {abc}: abc ��һ��ռλ����������������һ���ַ�������ͨ��@PathVariable("abc") ���õ����λ�õ�ֵ
	@RequestMapping("doSayMsg/{abc}")	// rest ��� 
	public ModelAndView doSayMsg(@PathVariable("abc") String msg) {
		System.out.println("msg = " + msg);
		ModelAndView mv = new ModelAndView();
		mv.setViewName("success");
		mv.addObject("msg", msg);
		return mv; // /WEB-INF/pages/success.jsp
	}
}
