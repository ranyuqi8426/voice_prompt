package com.wsct.common.vo;

public class CommonDwVO {
	private Integer commonDwid;//单位id
	private String commonDwmc;//单位名称
	
	public Integer getCommonDwid() {
		return commonDwid;
	}
	public void setCommonDwid(Integer commonDwid) {
		this.commonDwid = commonDwid;
	}
	public String getCommonDwmc() {
		return commonDwmc;
	}
	public void setCommonDwmc(String commonDwmc) {
		this.commonDwmc = commonDwmc;
	}
	
	@Override
	public String toString() {
		return "CommonDwVO [commonDwid=" + commonDwid + ", commonDwmc=" + commonDwmc + "]";
	}
}
