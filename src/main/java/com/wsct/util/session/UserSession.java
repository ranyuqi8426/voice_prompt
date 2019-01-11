package com.wsct.util.session;

/**
 * 系统用户Session信息
 * @author kcy
 */
public class UserSession {

	private int user_id;//
	private int dwid;// 单位id
	private String dwmc;// 单位名称
	private String dwjb;// 单位级别

	private String user_ip;// 用户IP
	private String user_name;// 用户名
	private String realname;// 真实性别
	private String phone;//座机
	private int role_id;// 角色代码
	private String default_module;// 默认模块

	private String dwlx;// 单位类型
	private String dwsx;// 单位属性
	private String dwfzr;//单位负责人

	public int getUser_id() {
		return user_id;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}

	public int getDwid() {
		return dwid;
	}

	public void setDwid(int dwid) {
		this.dwid = dwid;
	}

	public String getUser_ip() {
		return user_ip;
	}

	public void setUser_ip(String user_ip) {
		this.user_ip = user_ip;
	}

	public String getUser_name() {
		return user_name;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}

	public String getRealname() {
		return realname;
	}

	public void setRealname(String realname) {
		this.realname = realname;
	}

	public int getRole_id() {
		return role_id;
	}

	public void setRole_id(int role_id) {
		this.role_id = role_id;
	}

	public String getDefault_module() {
		return default_module;
	}

	public void setDefault_module(String default_module) {
		this.default_module = default_module;
	}

	public String getDwjb() {
		return dwjb;
	}

	public void setDwjb(String dwjb) {
		this.dwjb = dwjb;
	}

	public String getDwmc() {
		return dwmc;
	}

	public void setDwmc(String dwmc) {
		this.dwmc = dwmc;
	}

	public String getDwlx() {
		return dwlx;
	}

	public void setDwlx(String dwlx) {
		this.dwlx = dwlx;
	}

	public String getDwsx() {
		return dwsx;
	}

	public void setDwsx(String dwsx) {
		this.dwsx = dwsx;
	}

	public String getDwfzr() {
		return dwfzr;
	}

	public void setDwfzr(String dwfzr) {
		this.dwfzr = dwfzr;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	@Override
	public String toString() {
		return "UserSession [user_id=" + user_id + ", dwid=" + dwid + ", dwmc=" + dwmc + ", dwjb=" + dwjb + ", user_ip="
				+ user_ip + ", user_name=" + user_name + ", realname=" + realname + ", phone=" + phone + ", role_id="
				+ role_id + ", default_module=" + default_module + ", dwlx=" + dwlx + ", dwsx=" + dwsx + ", dwfzr="
				+ dwfzr + "]";
	}

}
