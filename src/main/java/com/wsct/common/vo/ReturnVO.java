package com.wsct.common.vo;

public class ReturnVO {
	private int flag;//返回标记
	private String strMsg;//返回消息
	
	public int getFlag() {
		return flag;
	}
	public void setFlag(int flag) {
		this.flag = flag;
	}
	public String getStrMsg() {
		return strMsg;
	}
	public void setStrMsg(String strMsg) {
		this.strMsg = strMsg;
	}
	
	@Override
	public String toString() {
		return "returnVO [flag=" + flag + ", strMsg=" + strMsg + "]";
	}
}
