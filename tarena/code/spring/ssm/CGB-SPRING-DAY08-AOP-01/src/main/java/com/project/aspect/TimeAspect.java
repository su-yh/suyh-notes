package com.project.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class TimeAspect {

	@Around("bean(userServiceImpl)")
	public Object aroundAdvice(ProceedingJoinPoint joinPoint) throws Throwable {
		System.out.println("TimeAspect.aroundAdvice()");
		try {
			System.out.println("@Before");
			// ִ��Ŀ�귽��
			Object result = joinPoint.proceed();
			System.out.println("@After");
			// ������ȷ��ִ�н��.
			return result;
		} catch (Throwable e) {
			e.printStackTrace();
			System.out.println("@AfterThrowing");
			// ���쳣�����׳�
			throw e;
		}
	}
}
