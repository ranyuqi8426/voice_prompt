package com.wsct.common.vo;
/** 
* 车组编号comboxbox
*
* @author 余建东
* @date：2016年12月22日  
*/
public class ComboboxCzbhVO {
	private String czbhdm;//车组编号代码
	private String czbhmc;//车组编号名称
	private String czxh;//车组型号
	
	public String getCzbhdm() {
		return czbhdm;
	}
	public void setCzbhdm(String czbhdm) {
		this.czbhdm = czbhdm;
	}
	public String getCzbhmc() {
		return czbhmc;
	}
	public void setCzbhmc(String czbhmc) {
		this.czbhmc = czbhmc;
	}
	public String getCzxh() {
		return czxh;
	}
	public void setCzxh(String czxh) {
		this.czxh = czxh;
	}
	@Override
	public String toString() {
		return "ComboboxCzbhVO [czbhdm=" + czbhdm + ", czbhmc=" + czbhmc + ", czxh=" + czxh + "]";
	}
	
}
