package com.project.sys.test;

import java.util.LinkedHashMap;

import org.junit.Test;

public class TestLinkedHashMap {

	@Test
	public void test01() {
		// 1. �ܰ�put ˳��洢����
		// 2. �ܰ�LRU �㷨��¼������ٷ��ʵĶ���
		LinkedHashMap<String, Object> map = new LinkedHashMap<String, Object>();

		map.put("A", 100);
		map.put("B", 100);
		map.put("C", 100);
		map.put("D", 100);

		System.out.println(map);

		map.get("B");
		map.get("D");

		System.out.println(map);
	}

	@Test
	public void test02() {
		LinkedHashMap<String, Object> map = new LinkedHashMap<String, Object>(10, 0.75f, true);

		map.put("A", 100);
		map.put("B", 100);
		map.put("C", 100);
		map.put("D", 100);

		System.out.println(map);

		map.get("B");
		map.get("D");

		System.out.println(map);
	}
}
