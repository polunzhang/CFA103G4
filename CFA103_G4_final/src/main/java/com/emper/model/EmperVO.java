package com.emper.model;

public class EmperVO implements java.io.Serializable{
	
	private Integer emperno;
	private String emper_name;
	
	public EmperVO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Integer getEmperno() {
		return emperno;
	}

	public void setEmperno(Integer emperno) {
		this.emperno = emperno;
	}

	public String getEmper_name() {
		return emper_name;
	}

	public void setEmper_name(String emper_name) {
		this.emper_name = emper_name;
	}	
	

}

