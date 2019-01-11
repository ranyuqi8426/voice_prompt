package com.wsct.sys.model;
/**
 * 通知公告
 * @author 崔明超
 * @date 2017年1月16
 *
 */
public class SysTzgg {

	private int id;
	private String bt;//标题
	private String nr;//内容
	private String fbr;//发布人
	private String fbsj;//发布时间
	private int px;//排序
	private String wjlj;//文件路径
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getBt() {
		return bt;
	}
	public void setBt(String bt) {
		this.bt = bt;
	}
	public String getNr() {
		return nr;
	}
	public void setNr(String nr) {
		this.nr = nr;
	}
	public String getFbr() {
		return fbr;
	}
	public void setFbr(String fbr) {
		this.fbr = fbr;
	}
	public String getFbsj() {
		return fbsj;
	}
	public void setFbsj(String fbsj) {
		this.fbsj = fbsj;
	}
	public int getPx() {
		return px;
	}
	public void setPx(int px) {
		this.px = px;
	}
	public String getWjlj() {
		return wjlj;
	}
	public void setWjlj(String wjlj) {
		this.wjlj = wjlj;
	}
	@Override
	public String toString() {
		return "SysTzgg [id=" + id + ", bt=" + bt + ", nr=" + nr + ", fbr=" + fbr + ", fbsj=" + fbsj + ", px=" + px
				+ ", wjlj=" + wjlj + "]";
	}
	
}
