package com.cs_rec.model;

import java.sql.Timestamp;

public class Cs_RecVO {
private Integer csno,memno,empno,msg_direct;
private String msg_context;
private Timestamp msg_time;
private byte[] msg_image;
public Integer getCsno() {
	return csno;
}
public void setCsno(Integer csno) {
	this.csno = csno;
}
public Integer getMemno() {
	return memno;
}
public void setMemno(Integer memno) {
	this.memno = memno;
}
public Integer getEmpno() {
	return empno;
}
public void setEmpno(Integer empno) {
	this.empno = empno;
}
public Integer getMsg_direct() {
	return msg_direct;
}
public void setMsg_direct(Integer msg_direct) {
	this.msg_direct = msg_direct;
}
public String getMsg_context() {
	return msg_context;
}
public void setMsg_context(String msg_context) {
	this.msg_context = msg_context;
}
public Timestamp getMsg_time() {
	return msg_time;
}
public void setMsg_time(Timestamp msg_time) {
	this.msg_time = msg_time;
}
public byte[] getMsg_image() {
	return msg_image;
}
public void setMsg_image(byte[] msg_image) {
	this.msg_image = msg_image;
}


}
