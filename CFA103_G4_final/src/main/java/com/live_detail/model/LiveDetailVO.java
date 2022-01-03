package com.live_detail.model;


public class LiveDetailVO implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	private Integer liveno;
	private Integer mealno;
	private Integer meal_amount;
	private Integer meal_price;
	private Integer meal_status;
	private String meal_note;
	private Integer meal_set;

	public Integer getLiveno() {
		return liveno;
	}

	public void setLiveno(Integer liveno) {
		this.liveno = liveno;
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

	public String getMeal_note() {
		return meal_note;
	}

	public void setMeal_note(String meal_note) {
		this.meal_note = meal_note;
	}

	public Integer getMeal_status() {
		return meal_status;
	}

	public void setMeal_status(Integer meal_status) {
		this.meal_status = meal_status;
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
		LiveDetailVO other = (LiveDetailVO) obj;
		if (mealno == null || meal_set == null) {
			if (other.mealno != null || other.meal_set != null)
				return false;
		} else if (!(mealno.equals(other.mealno) && meal_set.equals(other.meal_set)))
			return false;
		return true;
	}
}
