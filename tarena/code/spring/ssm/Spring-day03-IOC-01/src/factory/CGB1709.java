package factory;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

// ����ʱ��Ч��һ�㶼���������һ�����Ǳ���ʱ��Ч�����ǻ�����������
/**
 * ע��: 
 * 1. ��Java �е�һ��Ԫ���ݣ���������Java ����
 * 2. ������Ҳ��һ���࣬
 * @Retention ��ʾ�Լ������ע���ʱ��Ч
 * @Target ��ʾ�Լ������ע��Ӧ��������
 * @author suyh
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(value=ElementType.TYPE)
public @interface CGB1709 {	// CGB1709.class

}
