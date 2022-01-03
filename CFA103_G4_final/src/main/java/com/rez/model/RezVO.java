package com.rez.model;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;
import com.rez_time.model.*;

public class RezVO implements java.io.Serializable {
	private Integer rezno;
	private Integer memno;
	private String phone;
	private Integer num_of_ppl;
	private Date reservationdate;
	
	private Time reservationtime;
	private String email;
	private String lastname;
	private String sex;
	
	public Integer getRezno() {
		return rezno;
	}
	public void setRezno(Integer rezno) {
		this.rezno = rezno;
	}
	public Integer getMemno() {
		return memno;
	}
	public void setMemno(Integer memno) {
		this.memno = memno;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public Integer getNum_of_ppl() {
		return num_of_ppl;
	}
	public void setNum_of_ppl(Integer num_of_ppl) {
		this.num_of_ppl = num_of_ppl;
	}
	public Date getReservationdate() {
		return reservationdate;
	}
	public void setReservationdate(Date reservationdate) {
		this.reservationdate = reservationdate;
	}
	
	//-----------------------1211新增-----------------------
	
	public Time getReservationtime() {
		return reservationtime;
	}
	public void setReservationtime(Time reservationtime) {
		this.reservationtime = reservationtime;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getLastname() {
		return lastname;
	}
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
}
