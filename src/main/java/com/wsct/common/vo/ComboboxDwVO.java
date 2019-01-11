package com.wsct.common.vo;

/**
 * 功能描述：通过单位id取单位信息
 * 
 * @param dwid
 * @return 
 * @version 1.0.0
 * @author 乐振雷
 * @date 2016-12-7
 */
public class ComboboxDwVO {
	private int dwid;//单位id
	private int sjdwid;//上级单位id
	private String dwmc;//单位名称
	private String dwjb;//单位级别
	private String dwfzr;//单位负责人
	private String dwsx;//单位属性
	public int getDwid() {
		return dwid;
	}
	public void setDwid(int dwid) {
		this.dwid = dwid;
	}
	public int getSjdwid() {
		return sjdwid;
	}
	public void setSjdwid(int sjdwid) {
		this.sjdwid = sjdwid;
	}
	public String getDwmc() {
		return dwmc;
	}
	public void setDwmc(String dwmc) {
		this.dwmc = dwmc;
	}
	public String getDwjb() {
		return dwjb;
	}
	public void setDwjb(String dwjb) {
		this.dwjb = dwjb;
	}
	public String getDwfzr() {
		return dwfzr;
	}
	public void setDwfzr(String dwfzr) {
		this.dwfzr = dwfzr;
	}
	public String getDwsx() {
		return dwsx;
	}
	public void setDwsx(String dwsx) {
		this.dwsx = dwsx;
	}
	
}
