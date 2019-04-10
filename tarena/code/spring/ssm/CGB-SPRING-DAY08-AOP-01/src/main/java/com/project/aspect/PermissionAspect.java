package com.project.aspect;

import java.lang.reflect.Method;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import com.project.service.Permission;


// ִ��Ȩ�޼�������
@Aspect
@Component
public class PermissionAspect {
	/**
	 * 
	 * @param joinpoint ��ʾһ�����ӵ�,������ӵ��Ӧ����Ҫִ�е��Ǹ�����ҵ�񷽷�����
	 */
	@Before("execution(* com.project.service..*.*(..))")
	public void checkPermission(JoinPoint joinpoint) {
		System.out.println("Ȩ�޼��");
		
		// ��ȡ���ӵ��Ӧ�ķ���ǩ����Ϣ(��Ӧ���ķ�����Ϣ)
		Signature s = joinpoint.getSignature();
		String methodName = s.getName();
		System.out.println("methodName = " + methodName);
		Object[] args = joinpoint.getArgs();
		Class<?>[] params = new Class<?>[args.length];
		for (int i = 0; i < args.length; i++) {
			Object a = args[i];
			System.out.println("a = " + a);
			System.out.println("a.class: " + a.getClass());
			params[i] = a.getClass();
		}
		
		// �����ȡ�������Ǹ�Ŀ����󣬶��Ǵ������
		Class<?> targetCls = joinpoint.getTarget().getClass();
		System.out.println("target class: " + targetCls);
		Method m = null;
		try {
			if (params == null || params.length == 0) {
				m = targetCls.getDeclaredMethod(methodName);
			} else {
				m = targetCls.getDeclaredMethod(methodName, params);	
			}
			System.out.println("method: " + m);
		} catch (Exception e) {
			e.printStackTrace();
			return ;
		}
		if (m.isAnnotationPresent(Permission.class)) {
			System.out.println("ִ�д˷�����ҪȨ�޼��: " + m.getName());
		} else {
			System.out.println("ִ�д˷�������ҪȨ�޼��: " + m.getName());
		}
	}
}
