package com.wsct.sys.vo;

/**
 * 单位查询VO
 * @author chykong
 * @date 2016年12月6日
 */
public class SysDwSearchVO {
	private Integer dwid;// 单位id
	private String dwmc;// 单位名称
	private String dwjc;// 单位简称
	private String dwdm;// 单位代码
	private Integer sjdw;// 上级单位id
	private Integer bbssdw;// 报表收审单位id

	private String dwsx;// 单位属性
	private String dwlx;// 单位类型
	private String dwjb;// 单位级别

	private String showZgs;//显示总公司，用于单位管理里面，列表查询如果上级节点是1，就显示总公司，不然没有地方显示总公司这个单位
	public Integer getDwid() {
		return dwid;
	}

	public void setDwid(Integer dwid) {
		this.dwid = dwid;
	}

	public String getDwmc() {
		return dwmc;
	}

	public void setDwmc(String dwmc) {
		this.dwmc = dwmc;
	}

	public String getDwjc() {
		return dwjc;
	}

	public void setDwjc(String dwjc) {
		this.dwjc = dwjc;
	}

	public String getDwdm() {
		return dwdm;
	}

	public void setDwdm(String dwdm) {
		this.dwdm = dwdm;
	}

	@Override
	public String toString() {
		return "SysDwSearchVO [dwid=" + dwid + ", dwmc=" + dwmc + ", dwjc=" + dwjc + ", dwdm=" + dwdm + ", sjdw=" + sjdw
				+ ", bbssdw=" + bbssdw + "]";
	}

	public Integer getSjdw() {
		return sjdw;
	}

	public void setSjdw(Integer sjdw) {
		this.sjdw = sjdw;
	}

	public Integer getBbssdw() {
		return bbssdw;
	}

	public void setBbssdw(Integer bbssdw) {
		this.bbssdw = bbssdw;
	}

	public String getDwsx() {
		return dwsx;
	}

	public void setDwsx(String dwsx) {
		this.dwsx = dwsx;
	}

	public String getDwlx() {
		return dwlx;
	}

	public void setDwlx(String dwlx) {
		this.dwlx = dwlx;
	}

	public String getDwjb() {
		return dwjb;
	}

	public void setDwjb(String dwjb) {
		this.dwjb = dwjb;
	}

	public String getShowZgs() {
		return showZgs;
	}

	public void setShowZgs(String showZgs) {
		this.showZgs = showZgs;
	}
}
