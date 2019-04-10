package com.project.aspect;

import org.springframework.stereotype.Component;

// �����͵Ķ���Ҫ��װ��չҵ��
@Component
public class LoggingAspect {
	// ϣ���˷�����ҵ�񷽷�ִ��֮ǰִ��
	public void beforeMethod() {
		System.out.println("LoggingAspect.beforeMethod()");
	}
	// ϣ���˷�����ҵ�񷽷�ִ��֮��ִ��
	public void afterMethod() {
		System.out.println("LoggingAspect.afterMethod()");
	}
}
