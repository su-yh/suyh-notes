package beans;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

// @Retention ��ʾ�Լ������ע���ʱ��Ч
// @Target ��ʾ�Լ������ע��Ӧ��������
@Retention(RetentionPolicy.RUNTIME)
@Target(value=ElementType.TYPE)
public @interface Suyh {

}
