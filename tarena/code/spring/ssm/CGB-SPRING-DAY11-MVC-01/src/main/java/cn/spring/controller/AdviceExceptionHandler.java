package cn.spring.controller;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

// ˵����ʹ��@ControllerAdvice ע���������࣬һ����Ϊȫ�ֵ��쳣������
@ControllerAdvice
public class AdviceExceptionHandler {
	@ExceptionHandler(Throwable.class)
	@ResponseBody
	public String handlerException(Throwable e) {
		return e.getMessage();
	}
}
