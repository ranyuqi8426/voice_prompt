package com.wsct.sys.model;

public class SysRoleModule {

	private int id;
	private int role_id;//角色id
	private int module_id;//模块id

	public SysRoleModule() {
		super();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public SysRoleModule(int role_id, int module_id) {
		super();
		this.role_id = role_id;
		this.module_id = module_id;
	}

	public int getRole_id() {
		return role_id;
	}

	public void setRole_id(int role_id) {
		this.role_id = role_id;
	}

	public int getModule_id() {
		return module_id;
	}

	public void setModule_id(int module_id) {
		this.module_id = module_id;
	}

	@Override
	public String toString() {
		return "SysRoleModule [id=" + id + ", role_id=" + role_id + ", module_id=" + module_id + "]";
	}

}
