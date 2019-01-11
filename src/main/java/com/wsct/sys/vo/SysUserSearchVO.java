package com.wsct.sys.vo;

/**
 * 用户管理查询条件
 * @author chykong
 * @date 2016-12-06
 */
public class SysUserSearchVO {
	private String username;// email
	private String realname;// 真实姓名
	private Integer userlevel;// 级别
	private Integer status;// 状态
	private Integer create_id;//按创建人的id进行管理

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getRealname() {
		return realname;
	}

	public void setRealname(String realname) {
		this.realname = realname;
	}

	public Integer getUserlevel() {
		return userlevel;
	}

	public void setUserlevel(Integer userlevel) {
		this.userlevel = userlevel;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "SysUserSearchVO [username=" + username + ", realname=" + realname + ", userlevel=" + userlevel
				+ ", status=" + status + ", create_id=" + create_id + "]";
	}

	public Integer getCreate_id() {
		return create_id;
	}

	public void setCreate_id(Integer create_id) {
		this.create_id = create_id;
	}
}
