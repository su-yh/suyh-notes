package com.project.sys.vo;

/**
 * vo(Value Object)��ֵ�������ڷ�װ�ڴ������ݵ�һ������
 * һ�������ݿ��û�ж�Ӧ��ϵ
 * @author suyh
 *
 */
public class Account {
	private String uname;
	private String pwd;
	public String getUname() {
		return uname;
	}
	public void setUname(String uname) {
		this.uname = uname;
	}
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	@Override
	public String toString() {
		return "Account [uname=" + uname + ", pwd=" + pwd + "]";
	}
	
}
