package com.wsct.sys.model;

public class SysFileUpload {
	private int id;
	private String wjm;
	private String wjlj;
	private String scr;
	private String scsj;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getWjm() {
		return wjm;
	}

	public void setWjm(String wjm) {
		this.wjm = wjm;
	}

	public String getWjlj() {
		return wjlj;
	}

	public void setWjlj(String wjlj) {
		this.wjlj = wjlj;
	}

	public String getScr() {
		return scr;
	}

	public void setScr(String scr) {
		this.scr = scr;
	}

	public String getScsj() {
		return scsj;
	}

	public void setScsj(String scsj) {
		this.scsj = scsj;
	}

	@Override
	public String toString() {
		return "SysFileUpload [id=" + id + ", wjm=" + wjm + ", wjlj=" + wjlj + ", scr=" + scr + ", scsj=" + scsj + "]";
	}

}
