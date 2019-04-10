package com.jt.common;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

public class DateJsonSerializer extends JsonSerializer<Date> {

	/***
	 * 1. ���������ʱ���ã�
	 * ������ת��ΪJSON ��ʱ
	 * �����ڶ���Ķ�Ӧ��GET ������ʹ���� 
	 * @JsonSerializer(using=DataJsonSerializer.class)
	 * 2. ��������еĲ�������ʲô���壿
	 * 2.1 value: Ҫת���Ķ���
	 * 2.2 gen: json ����ת����
	 * 2.3 serializers ���л��ṩ��
	 */
	@Override
	public void serialize(Date value, JsonGenerator gen, SerializerProvider serializers)
			throws IOException, JsonProcessingException {
		// ���ڸ�ʽת����
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:SS");
		// ת�����ڶ���
		String dateStr = sdf.format(value);
		// �����ַ���д��json ����
		gen.writeString(dateStr);
	}
}
