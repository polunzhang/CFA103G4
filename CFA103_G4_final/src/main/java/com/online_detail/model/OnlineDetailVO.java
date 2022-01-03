package com.online_detail.model;

import com.online_detail.model.OnlineDetailVO;

public class OnlineDetailVO {
	private Integer olno;
	private Integer mealno;
	private Integer meal_amount;
	private Integer meal_price;
	private Integer meal_status;
	private String meal_note;
	private Integer meal_set;
	
	public Integer getOlno() {
		return olno;
	}
	public void setOlno(Integer olno) {
		this.olno = olno;
	}
	public Integer getMealno() {
		return mealno;
	}
	public void setMealno(Integer mealno) {
		this.mealno = mealno;
	}
	public Integer getMeal_amount() {
		return meal_amount;
	}
	public void setMeal_amount(Integer meal_amount) {
		this.meal_amount = meal_amount;
	}
	public Integer getMeal_price() {
		return meal_price;
	}
	public void setMeal_price(Integer meal_price) {
		this.meal_price = meal_price;
	}
	public Integer getMeal_status() {
		return meal_status;
	}
	public void setMeal_status(Integer meal_status) {
		this.meal_status = meal_status;
	}
	public String getMeal_note() {
		return meal_note;
	}
	public void setMeal_note(String meal_note) {
		this.meal_note = meal_note;
	}
	public Integer getMeal_set() {
		return meal_set;
	}
	public void setMeal_set(Integer meal_set) {
		this.meal_set = meal_set;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((mealno == null) ? 0 : mealno.hashCode());
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		OnlineDetailVO other = (OnlineDetailVO) obj;
		if (mealno == null || meal_set == null) {
			if (other.mealno != null || other.meal_set != null)
				return false;
		} else if (!(mealno.equals(other.mealno) && meal_set.equals(other.meal_set)))
			return false;
		return true;
	}
}
