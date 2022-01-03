package com.prom_detail.model;

import java.sql.Date;

public class PromDetailVO {
	private Integer promno;
	private Integer mealno;
	private Double prom_value;
	private Date prom_time_start;
	private Date prom_time_end;
	private Integer prom_state;
	
	public Integer getPromno() {
		return promno;
	}
	public void setPromno(Integer promno) {
		this.promno = promno;
	}
	public Integer getMealno() {
		return mealno;
	}
	public void setMealno(Integer mealno) {
		this.mealno = mealno;
	}
	public Double getProm_value() {
		return prom_value;
	}
	public void setProm_value(Double prom_value) {
		this.prom_value = prom_value;
	}
	public Date getProm_time_start() {
		return prom_time_start;
	}
	public void setProm_time_start(Date prom_time_start) {
		this.prom_time_start = prom_time_start;
	}
	public Date getProm_time_end() {
		return prom_time_end;
	}
	public void setProm_time_end(Date prom_time_end) {
		this.prom_time_end = prom_time_end;
	}
	public Integer getProm_state() {
		return prom_state;
	}
	public void setProm_state(Integer prom_state) {
		this.prom_state = prom_state;
	}
	
	
}
