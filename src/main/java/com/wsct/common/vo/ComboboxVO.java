package com.wsct.common.vo;

public class ComboboxVO {
	private String value;//字段值
	private String content;//字段内容

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getContent() {
		return content;
	}

	public ComboboxVO() {
		super();
	}

	public void setContent(String content) {
		this.content = content;
	}

	public ComboboxVO(String value, String content) {
		super();
		this.value = value;
		this.content = content;
	}
}
