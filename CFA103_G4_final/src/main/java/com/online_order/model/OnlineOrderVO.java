package com.online_order.model;

import java.sql.Timestamp;

public class OnlineOrderVO implements java.io.Serializable{
	private Integer olno;
	private Integer empno;
	private Integer memno;
	private Integer pay_status;
	private String address;
	private Timestamp set_time;
	private Timestamp create_time;
	private Integer total;
	private Integer pay_way;
	
	public Integer getOlno() {
		return olno;
	}
	public void setOlno(Integer olno) {
		this.olno = olno;
	}
	public Integer getEmpno() {
		return empno;
	}
	public void setEmpno(Integer empno) {
		this.empno = empno;
	}
	public Integer getMemno() {
		return memno;
	}
	public void setMemno(Integer memno) {
		this.memno = memno;
	}
	public Integer getPay_status() {
		return pay_status;
	}
	public void setPay_status(Integer pay_status) {
		this.pay_status = pay_status;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public Timestamp getSet_time() {
		return set_time;
	}
	public void setSet_time(Timestamp set_time) {
		this.set_time = set_time;
	}
	public Timestamp getCreate_time() {
		return create_time;
	}
	public void setCreate_time(Timestamp create_time) {
		this.create_time = create_time;
	}
	public Integer getTotal() {
		return total;
	}
	public void setTotal(Integer total) {
		this.total = total;
	}
	public Integer getPay_way() {
		return pay_way;
	}
	public void setPay_way(Integer pay_way) {
		this.pay_way = pay_way;
	}
	
}
