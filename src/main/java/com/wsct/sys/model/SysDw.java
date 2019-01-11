package com.wsct.sys.model;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 单位字典
 * @author chykong
 * @date 2016年12月6日
 */
public class SysDw {
	private int dwid;// 单位id
	private String dwmc;// 单位名称
	private String dwjc;// 单位简称
	private String dwdm;// 单位代码
	private int sjdw;// 上级单位id
	private int shdw;// 报表收审单位id
	private String dwsx;// 单位属性
	private String dwsxmc;// 单位属性名称
	private String dwlx;// 单位类型
	private String dwlxmc;// 单位类型名称
	private String sfdm;// 省份代码
	private String sfmc;// 省份名
	private String dwjb;// 单位级别
	private String dwjbmc;// 单位级别名称
	private String dwfzr;// 单位负责人
	private Date cjsj;// 创建时间
	private Date xgsj;// 修改时间
	private int px;// 排序
	private int cnt;// 下级单位数量

	public int getDwid() {
		return dwid;
	}

	public void setDwid(int dwid) {
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

	public String getSfdm() {
		return sfdm;
	}

	public void setSfdm(String sfdm) {
		this.sfdm = sfdm;
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

	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "GMT+8")
	public Date getCjsj() {
		return cjsj;
	}

	public void setCjsj(Date cjsj) {
		this.cjsj = cjsj;
	}

	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	public Date getXgsj() {
		return xgsj;
	}

	public void setXgsj(Date xgsj) {
		this.xgsj = xgsj;
	}

	public int getPx() {
		return px;
	}

	public void setPx(int px) {
		this.px = px;
	}

	@Override
	public String toString() {
		return "SysDw [dwid=" + dwid + ", dwmc=" + dwmc + ", dwjc=" + dwjc + ", dwdm=" + dwdm + ", sjdw=" + sjdw
				+ ", shdw=" + shdw + ", dwsx=" + dwsx + ", dwsxmc=" + dwsxmc + ", dwlx=" + dwlx + ", dwlxmc=" + dwlxmc
				+ ", sfdm=" + sfdm + ", sfmc=" + sfmc + ", dwjb=" + dwjb + ", dwjbmc=" + dwjbmc + ", dwfzr=" + dwfzr
				+ ", cjsj=" + cjsj + ", xgsj=" + xgsj + ", px=" + px + ", cnt=" + cnt + "]";
	}

	public int getSjdw() {
		return sjdw;
	}

	public void setSjdw(int sjdw) {
		this.sjdw = sjdw;
	}

	public String getDwsxmc() {
		return dwsxmc;
	}

	public void setDwsxmc(String dwsxmc) {
		this.dwsxmc = dwsxmc;
	}

	public String getDwlxmc() {
		return dwlxmc;
	}

	public void setDwlxmc(String dwlxmc) {
		this.dwlxmc = dwlxmc;
	}

	public String getSfmc() {
		return sfmc;
	}

	public void setSfmc(String sfmc) {
		this.sfmc = sfmc;
	}

	public String getDwjbmc() {
		return dwjbmc;
	}

	public void setDwjbmc(String dwjbmc) {
		this.dwjbmc = dwjbmc;
	}

	public int getCnt() {
		return cnt;
	}

	public void setCnt(int cnt) {
		this.cnt = cnt;
	}

	public int getShdw() {
		return shdw;
	}

	public void setShdw(int shdw) {
		this.shdw = shdw;
	}

}
