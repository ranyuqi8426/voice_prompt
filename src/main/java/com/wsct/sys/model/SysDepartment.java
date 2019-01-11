package com.wsct.sys.model;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

public class SysDepartment {

	private String code;//编号
	private String name;//名称
	private Date create_date;//创建时间
	private String parent_code;//上级节点
	private int curst;//状态
	private int cnt;//下级节点数
	private String create_person;//创建人
	private String create_personname;//

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "GMT+8")
	public Date getCreate_date() {
		return create_date;
	}

	public void setCreate_date(Date create_date) {
		this.create_date = create_date;
	}

	public String getParent_code() {
		return parent_code;
	}

	public void setParent_code(String parent_code) {
		this.parent_code = parent_code;
	}

	public int getCurst() {
		return curst;
	}

	public void setCurst(int curst) {
		this.curst = curst;
	}

	public int getCnt() {
		return cnt;
	}

	public void setCnt(int cnt) {
		this.cnt = cnt;
	}

	public String getCreate_person() {
		return create_person;
	}

	public void setCreate_person(String create_person) {
		this.create_person = create_person;
	}

	public String getCreate_personname() {
		return create_personname;
	}

	public void setCreate_personname(String create_personname) {
		this.create_personname = create_personname;
	}

}
