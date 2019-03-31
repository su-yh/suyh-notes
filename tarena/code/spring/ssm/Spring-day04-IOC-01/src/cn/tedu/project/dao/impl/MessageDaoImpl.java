package cn.tedu.project.dao.impl;

import org.springframework.stereotype.Repository;

import cn.tedu.project.dao.MessageDao;

/**
 * ���ݳ־ò����(��װJDBC�����Ķ���)һ���ʹ�� @Repository ע������
 * @author suyh
 *
 */
@Repository
public class MessageDaoImpl implements MessageDao {
	public MessageDaoImpl() {
		System.out.println("MessageDaoImpl.MessageDaoImpl()");
	}

	@Override
	public void insertMsg(String msg) {
		System.out.println("MessageDaoImpl.insertMsg()" + msg);
	}

}
