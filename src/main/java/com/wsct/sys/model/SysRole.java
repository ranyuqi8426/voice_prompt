package com.wsct.sys.model;

public class SysRole {

	private int id;// 角色代码
	private String name;// 角色名称
	private String description;//描述
	private int display_order;//排序
	private String code;//角色代码
	private String default_module;//默认模块，登录显示
	private int is_admin;//是否管理角色，0否1是，0的开放给路局使用
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getDisplay_order() {
		return display_order;
	}

	public void setDisplay_order(int display_order) {
		this.display_order = display_order;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@Override
	public String toString() {
		return "SysRole [id=" + id + ", name=" + name + ", description=" + description + ", display_order=" + display_order + ", code=" + code + "]";
	}

	public String getDefault_module() {
		return default_module;
	}

	public void setDefault_module(String default_module) {
		this.default_module = default_module;
	}

	public int getIs_admin() {
		return is_admin;
	}

	public void setIs_admin(int is_admin) {
		this.is_admin = is_admin;
	}

}
