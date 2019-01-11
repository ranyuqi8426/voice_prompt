package com.wsct.common.vo;

public class ComboboxJxVO {
	private String jxdm;//机型代码
	private String jxmc;//机型名称
	private String jczl;//机车种类
	private float jcgl;//机车功率
	public String getJxdm() {
		return jxdm;
	}
	public void setJxdm(String jxdm) {
		this.jxdm = jxdm;
	}
	public String getJxmc() {
		return jxmc;
	}
	public void setJxmc(String jxmc) {
		this.jxmc = jxmc;
	}
	public String getJczl() {
		return jczl;
	}
	public void setJczl(String jczl) {
		this.jczl = jczl;
	}
	public float getJcgl() {
		return jcgl;
	}
	public void setJcgl(float jcgl) {
		this.jcgl = jcgl;
	}
	
	@Override
	public String toString() {
		return "ComboboxJxVO [jxdm=" + jxdm + ", jxmc=" + jxmc + ", jczl=" + jczl + ", jcgl=" + jcgl + "]";
	}
}
