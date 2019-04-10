package cn.spring.mvc.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.spring.mvc.entity.Message;

@RequestMapping("/req/")
@Controller
public class MvcRequestController {

	@RequestMapping("doShowUI")
	public String doShowUI() {
		
		return "request";
	}

	// ���� servlet ԭ��API �����������
	// HttpServletRequest ��Ӧ��jar �����ģ�  tomcat/jar
	// ʲô������ֱ��ʹ��HttpServletRequest ����
	// ��ȡ�������ݣ�
	// ����Ҫ��ȡ����ͷ����������ڲ�����ʱ����Ҫ���������ݽ��б�������ݴ�����ʱ�ɿ���ʹ��HttpServletRequest
	@RequestMapping("doSaveObject")
	public String doSaveObject(HttpServletRequest request) {
		String id = request.getParameter("id");
		String content = request.getParameter("content");
		System.out.println("id = " + id);
		System.out.println("content = " + content);
		
		return "request";
	}
	
	// ֱ�ӽ������������(id=100)��ͬ�ı���(Integer id)������������[id].
	// �ײ������Щ����?
	// 1) ͨ�������ȡ��������
	// 2) ͨ�������ȡ������Ϣ(���֣�����)
	// 3) ��ȡ������������ֶ�Ӧ�ķ���������Ϣ
	// 4) ���ݶ�Ӧ�Ĳ�����Ϣ��������ת�����Լ���ֵ����
	// ��Ҫע��: 
	// 1) ������������������ҳ������ʱ�������������Ϊ�������͡�
	// 2) �������������������������һ��ʱ��ʹ�� @RequestParam(name="tid") ��ָ�������ĸ�������ֵ
	// 3) ��������ĳ����������Ҫ����ҳ�����ж�Ӧ����ʱ��ʹ�� @RequestParam(required=true) ��ָ��
	@RequestMapping("doSaveObject02")
	public String doSaveObject02(@RequestParam(name="tid", required=true) Integer id, String content) {
		System.out.println("ttid = " + id);
		System.out.println("ttcontent = " + content);
		return "request";
	}
	
	// ����������Ƚ϶��������ɿ���ʹ��JaveBean ����������
	// ����1: �����еĲ�����Ϣ����η�װ��Message �����еģ�
	//	a). �����޲ι��캯������msg ����(���û�н��ᱨ��)
	// 	b). ����msg �����set �����������bean �����������ע��(id <--> setId)
	@RequestMapping("doSaveObject03")
	public String doSaveObject03(Message msg) {
		System.out.println(msg);
		return "request";
	}
	
	@RequestMapping("doUpdateObject")
	public String doUpdateObject(Integer id) {
		System.out.println("MvcRequestController.doUpdateObject(), id=" + id);
		return "request";
	}
	
	@RequestMapping("doDeleteObject/{idParam}")
	public String doDeleteObject(@PathVariable("idParam") Integer id) {
		System.out.println("MvcRequestController.doDeleteObject(), id=" + id);
		return "request";
	}
	
	// @ResponseBody����������һ���������ݡ�������һ��ҳ��
	// @ResponseBodyע�����ã���ע�������ڽ�Controller�ķ������صĶ���
	// ͨ���ʵ���HttpMessageConverterת��Ϊָ����ʽ��д�뵽Response�����body��������
	// ʹ�ó��������ص����ݲ���Html��ǩ��ҳ�棬�����������ݸ�ʽ������ʱ������Json��xml�ȣ�ʹ�ã�
	@RequestMapping("doFindObjects")
	@ResponseBody
	public String doFindObjects(Integer pageCurrent) {
		return "pageCurrent: " + pageCurrent;
	}
	
	
	// @RequestHeader ��ʶ����ͷ��ĳһ������
	@RequestMapping("doWithHeader")
	@ResponseBody
	public String doWithHeader(@RequestHeader String Accept) {
		return "obtain header accept = " + Accept;
	}
	
	// ��ȡ������Header �е�����
	@RequestMapping("doWithEntry")
	@ResponseBody
	public String doWithEntry(HttpEntity<String> entity) {
		return "headers = " + entity.getHeaders();
	}
	
	// ��ȡCookie �е�ֵ����Ҫ�õ�@CookieValue ע��
	@RequestMapping("doWithCookie")
	@ResponseBody
	public String doWithCookie(@CookieValue(name="JSESSIONID") String jsid) {
		return "cookie.JSESSIONID: " + jsid;
	}
}





