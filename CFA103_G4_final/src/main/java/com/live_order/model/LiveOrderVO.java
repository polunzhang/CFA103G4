package com.live_order.model;
import java.sql.Date;
import java.sql.Timestamp;

public class LiveOrderVO implements java.io.Serializable{
	private Integer liveno;
	private Integer empno;
	private Integer pay_status;
	private Timestamp create_time;
	private Integer total;
	private Integer tableno;
	private Integer pay_way;
	
	public Integer getLiveno() {
		return liveno;
	}
	public void setLiveno(Integer liveno) {
		this.liveno = liveno;
	}
	public Integer getEmpno() {
		return empno;
	}
	public void setEmpno(Integer empno) {
		this.empno = empno;
	}
	public Integer getPay_status() {
		return pay_status;
	}
	public void setPay_status(Integer pay_status) {
		this.pay_status = pay_status;
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
	public Integer getTableno() {
		return tableno;
	}
	public void setTableno(Integer tableno) {
		this.tableno = tableno;
	}
	public Integer getPay_way() {
		return pay_way;
	}
	public void setPay_way(Integer pay_way) {
		this.pay_way = pay_way;
	}
	
}
