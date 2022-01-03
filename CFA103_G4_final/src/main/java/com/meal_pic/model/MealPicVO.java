package com.meal_pic.model;

public class MealPicVO implements java.io.Serializable {
	private Integer meal_pic_no;
	private Integer mealno;
	private byte[] meal_pic;
	
	public Integer getMeal_pic_no() {
		return meal_pic_no;
	}
	public void setMeal_pic_no(Integer meal_pic_no) {
		this.meal_pic_no = meal_pic_no;
	}
	public Integer getMealno() {
		return mealno;
	}
	public void setMealno(Integer mealno) {
		this.mealno = mealno;
	}
	public byte[] getMeal_pic() {
		return meal_pic;
	}
	public void setMeal_pic(byte[] meal_pic) {
		this.meal_pic = meal_pic;
	}
}
