package com.project.service.impl;

import org.springframework.stereotype.Service;

import com.project.service.HelloService;

@Service
public class HelloServiceImpl implements HelloService {

	// ����ҵ��
	public void sayHello(String msg) {
		System.out.println("hello: " + msg);
	}

}
