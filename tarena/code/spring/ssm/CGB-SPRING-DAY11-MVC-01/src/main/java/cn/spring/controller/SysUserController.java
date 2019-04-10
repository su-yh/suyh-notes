package cn.spring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@RequestMapping("/sys/")
@Controller
public class SysUserController {

	@RequestMapping("doSaveObject")
	@ResponseBody
	public String doSaveObject() {
		System.out.println("SysUserController.doSaveObject()");
		return "Save OK";
	}
	
	// ����Controller �е��쳣����������Щ������Ҫ����ExceptionHandler ע�����������
	// ע�� �е����ݱ�ʾ����������ܹ�������쳣(��������쳣����������)
	@ExceptionHandler(value=Exception.class)
	@ResponseBody
	public String handleException(Exception e) {
		return e.getMessage();
	}
}
