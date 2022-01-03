package com.member.model;

import java.sql.Date;

public class MemberVO {

	private Integer memno, mem_state, mem_verify;
	private String mname, gender, address, phone, maccount, mpassword, mem_email, mem_id;
	private Date bday;
	
	public Integer getMemno() {
		return memno;
	}
	public void setMemno(Integer memno) {
		this.memno = memno;
	}
	public String getMname() {
		return mname;
	}
	public void setMname(String mname) {
		this.mname = mname;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public Date getBday() {
		return bday;
	}
	public void setBday(Date bday) {
		this.bday = bday;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getMaccount() {
		return maccount;
	}
	public void setMaccount(String maccound) {
		this.maccount = maccound;
	}
	public String getMpassword() {
		return mpassword;
	}
	public void setMpassword(String mpassword) {
		this.mpassword = mpassword;
	}
	public String getMem_email() {
		return mem_email;
	}
	public void setMem_email(String mem_email) {
		this.mem_email = mem_email;
	}
	public Integer getMem_state() {
		return mem_state;
	}
	public void setMem_state(Integer mem_state) {
		this.mem_state = mem_state;
	}
	public Integer getMem_verify() {
		return mem_verify;
	}
	public void setMem_verify(Integer mem_verify) {
		this.mem_verify = mem_verify;
	}
	public String getMem_id() {
		return mem_id;
	}
	public void setMem_id(String mem_id) {
		this.mem_id = mem_id;
	}
	
}
