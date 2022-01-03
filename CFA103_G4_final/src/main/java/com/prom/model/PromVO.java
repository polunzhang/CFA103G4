package com.prom.model;

public class PromVO implements java.io.Serializable {
	private Integer promno;
	private Integer empno;
	private String prom_title;	
	private String prom_content;
	private byte[] prom_image;

	public Integer getPromno() {
		return promno;
	}
	public void setPromno(Integer promno) {
		this.promno = promno;
	}
	public Integer getEmpno() {
		return empno;
	}
	public void setEmpno(Integer empno) {
		this.empno = empno;
	}
	public String getProm_title() {
		return prom_title;
	}
	public void setProm_title(String prom_title) {
		this.prom_title = prom_title;
	}
	public String getProm_content() {
		return prom_content;
	}
	public void setProm_content(String prom_content) {
		this.prom_content = prom_content;
	}
	public byte[] getProm_image() {
		return prom_image;
	}
	public void setProm_image(byte[] prom_image) {
		this.prom_image = prom_image;
	}
}
