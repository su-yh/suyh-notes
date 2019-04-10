package com.project.sys.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.project.sys.entity.SysUser;
import com.project.sys.vo.Account;

public interface SysUserDao {

	List<Map<String, Object>> findUsers();
	
	Map<String, Object> findUserById(Integer id);
	
	int insertObject(SysUser entity);
	
	List<Account> findNameAndPwd();
	
	// ����ʹ��'$'ȡ������ֵʱ��Ҫʹ��ע��'@Param' ע��Բ�����������
	// ��ʹ��'#' ��ȡ����ֵʱ������ӿڷ������ж������ʱ����Ҫʹ��'@Param' ע���������
	List<SysUser> findUsers(@Param("columnName") String colName, @Param("phone") String ph);
	
//	Account findUser(String name);
}
