package com.emp.model;

import java.sql.Date;

public class EmpVO implements java.io.Serializable{

	private Integer empno;
	private String job;
	private Integer sal;
	private String ename;
	private Date hiredate;
	private String eaccount;
	private String epassword;
	private Integer job_status;
	
	public EmpVO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Integer getEmpno() {
		return empno;
	}

	public void setEmpno(Integer empno) {
		this.empno = empno;
	}

	public String getJob() {
		return job;
	}

	public void setJob(String job) {
		this.job = job;
	}

	public Integer getSal() {
		return sal;
	}

	public void setSal(Integer sal) {
		this.sal = sal;
	}

	public String getEname() {
		return ename;
	}

	public void setEname(String ename) {
		this.ename = ename;
	}

	public Date getHiredate() {
		return hiredate;
	}

	public void setHiredate(Date hiredate) {
		this.hiredate = hiredate;
	}

	public String getEaccount() {
		return eaccount;
	}

	public void setEaccount(String eaccount) {
		this.eaccount = eaccount;
	}

	public String getEpassword() {
		return epassword;
	}

	public void setEpassword(String epassword) {
		this.epassword = epassword;
	}

	public Integer getJob_status() {
		return job_status;
	}

	public void setJob_status(Integer job_status) {
		this.job_status = job_status;
	}


	
	
}
