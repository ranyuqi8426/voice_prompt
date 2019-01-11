package com.wsct.test.model;

import java.util.Date;

public class Test { 
	private Integer topic_costrecord_id;//主键
	private Integer topic_cost_id;//外键
	private String pay_type;//支付类型
	private String pay_money;//支付金额
	private String pay_reason;//金付原因
	private String create_cd;//创建人
	private Date create_time;//创建时间
	private String update_cd;//修改人
	private Date update_time;//修改时间
	public Integer getTopic_costrecord_id() {
		return topic_costrecord_id;
	}
	public void setTopic_costrecord_id(Integer topic_costrecord_id) {
		this.topic_costrecord_id = topic_costrecord_id;
	}
	public Integer getTopic_cost_id() {
		return topic_cost_id;
	}
	public void setTopic_cost_id(Integer topic_cost_id) {
		this.topic_cost_id = topic_cost_id;
	}
	public String getPay_type() {
		return pay_type;
	}
	public void setPay_type(String pay_type) {
		this.pay_type = pay_type;
	}
	public String getPay_money() {
		return pay_money;
	}
	public void setPay_money(String pay_money) {
		this.pay_money = pay_money;
	}
	public String getPay_reason() {
		return pay_reason;
	}
	public void setPay_reason(String pay_reason) {
		this.pay_reason = pay_reason;
	}
	public String getCreate_cd() {
		return create_cd;
	}
	public void setCreate_cd(String create_cd) {
		this.create_cd = create_cd;
	}
	public Date getCreate_time() {
		return create_time;
	}
	public void setCreate_time(Date create_time) {
		this.create_time = create_time;
	}
	public String getUpdate_cd() {
		return update_cd;
	}
	public void setUpdate_cd(String update_cd) {
		this.update_cd = update_cd;
	}
	public Date getUpdate_time() {
		return update_time;
	}
	public void setUpdate_time(Date update_time) {
		this.update_time = update_time;
	}
	
}
