package cn.tedu.project.service.impl;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import cn.tedu.project.dao.UserDao;
import cn.tedu.project.service.UserService;

/**
 * дһ��������: class TestUserService
 * 1). �ڲ������д�spring �����л�ȡUserServiceImpl ���͵Ķ���
 * 2). ��ô˶���󣬵��ô˶���ķ���saveUser() 
 * �����������󣬱�дTestUserService
 * @author suyh
 *
 */

@Service
public class UserServiceImpl implements UserService {
//	@Qualifier("userDaoImpl")
//	@Autowired
	@Resource
	private UserDao userDao;
	
//	@Resource
	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
		System.out.println("UserServiceImpl.setUserDao()");
	}

//	@Autowired
	public UserServiceImpl(UserDao ud) {
		this.userDao = ud;
	}
	@Override
	public void saveUser(String user) {
		userDao.insertUser(user);
	}

}
