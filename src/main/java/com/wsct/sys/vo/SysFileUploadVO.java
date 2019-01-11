package com.wsct.sys.vo;

public class SysFileUploadVO {
	private String wjm;
	private String scr;

	public String getWjm() {
		return wjm;
	}

	public void setWjm(String wjm) {
		this.wjm = wjm;
	}

	public String getScr() {
		return scr;
	}

	public void setScr(String scr) {
		this.scr = scr;
	}

	@Override
	public String toString() {
		return "SysFileUploadVO [wjm=" + wjm + ", scr=" + scr + "]";
	}

}
