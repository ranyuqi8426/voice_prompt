package com.wsct.test.vo;

import javax.validation.constraints.AssertFalse;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;

public class TestVO {
	private String pay_type;//支付类型
	private String update_cd;//修改人
	private String update_time;//修改时间
	private String contract_id;
	private String topic_name;private Integer topic_cost_id;//外键
	@NotBlank(message="用户名不能为空")
    private String userName;
	public Integer getTopic_cost_id() {
		return topic_cost_id;
	}
	public void setTopic_cost_id(Integer topic_cost_id) {
		this.topic_cost_id = topic_cost_id;
	}
	public String getContract_id() {
		return contract_id;
	}
	public void setContract_id(String contract_id) {
		this.contract_id = contract_id;
	}
	public String getTopic_name() {
		return topic_name;
	}
	public void setTopic_name(String topic_name) {
		this.topic_name = topic_name;
	}
	public String getPay_type() {
		return pay_type;
	}
	public void setPay_type(String pay_type) {
		this.pay_type = pay_type;
	}
	public String getUpdate_cd() {
		return update_cd;
	}
	public void setUpdate_cd(String update_cd) {
		this.update_cd = update_cd;
	}
	public String getUpdate_time() {
		return update_time;
	}
	public void setUpdate_time(String update_time) {
		this.update_time = update_time;
	}
	
}
