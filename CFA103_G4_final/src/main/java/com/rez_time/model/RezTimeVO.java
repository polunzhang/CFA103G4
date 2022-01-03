package com.rez_time.model;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

import com.rez.model.*;

public class RezTimeVO implements java.io.Serializable {
	private Integer rez_time_no;
	private Date rez_time_date;
	private Integer rez_time_mid_limit;
	private Integer rez_time_mid;
	private Integer rez_time_eve_limit;
	private Integer rez_time_eve;
	
	
	public Integer getRez_time_no() {
		return rez_time_no;
	}
	public void setRez_time_no(Integer rez_time_no) {
		this.rez_time_no = rez_time_no;
	}
	public Date getRez_time_date() {
		return rez_time_date;
	}
	public void setRez_time_date(Date rez_time_date) {
		this.rez_time_date = rez_time_date;
	}
	public Integer getRez_time_mid_limit() {
		return rez_time_mid_limit;
	}
	public void setRez_time_mid_limit(Integer rez_time_mid_limit) {
		this.rez_time_mid_limit = rez_time_mid_limit;
	}
	public Integer getRez_time_mid() {
		return rez_time_mid;
	}
	public void setRez_time_mid(Integer rez_time_mid) {
		this.rez_time_mid = rez_time_mid;
	}
	public Integer getRez_time_eve_limit() {
		return rez_time_eve_limit;
	}
	public void setRez_time_eve_limit(Integer rez_time_eve_limit) {
		this.rez_time_eve_limit = rez_time_eve_limit;
	}
	public Integer getRez_time_eve() {
		return rez_time_eve;
	}
	public void setRez_time_eve(Integer rez_time_eve) {
		this.rez_time_eve = rez_time_eve;
	}
	
}
