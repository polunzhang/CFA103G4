package com.meal.model;

public class MealVO implements java.io.Serializable {
	 private static final long serialVersionUID = 1L;
	 public MealVO() {
	  mealno = 0;
	  meal_type_no = 0;
	  meal_price = 0;
	  meal_name = "";
	  meal_intro = "";
	  quantity = 0;
	 }
	private Integer mealno;
	private Integer meal_type_no;
	private Integer meal_price;
	private String meal_name;
	private String meal_intro;
	private Integer quantity;
	

	public Integer getMealno() {
		return mealno;
	}
	public Integer getQuantity() {
		return quantity;
	}
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	public void setMealno(Integer mealno) {
		this.mealno = mealno;
	}
	public Integer getMeal_type_no() {
		return meal_type_no;
	}
	public void setMeal_type_no(Integer meal_type_no) {
		this.meal_type_no = meal_type_no;
	}
	public Integer getMeal_price() {
		return meal_price;
	}
	public void setMeal_price(Integer meal_price) {
		this.meal_price = meal_price;
	}
	public String getMeal_name() {
		return meal_name;
	}
	public void setMeal_name(String meal_name) {
		this.meal_name = meal_name;
	}
	public String getMeal_intro() {
		return meal_intro;
	}
	public void setMeal_intro(String meal_intro) {
		this.meal_intro = meal_intro;
	}
	@Override
	public String toString() {
		return "MealVO [mealno=" + mealno + ", meal_type_no=" + meal_type_no + ", meal_price=" + meal_price
				+ ", meal_name=" + meal_name + ", meal_intro=" + meal_intro + ",quantity=" + quantity +"]";
	}
	 @Override
	 public int hashCode() {
	  final int prime = 31;
	  int result = 1;
	  result = prime * result + ((meal_name == null) ? 0 : meal_name.hashCode());
	  return result;
	 }
	 
	 
	 public boolean equals(Object obj) {
	  if (this == obj)
	   return true;
	  if (obj == null)
	   return false;
	  if (getClass() != obj.getClass())
	   return false;
	  MealVO other = (MealVO) obj;
	  if (meal_name == null) {
	   if (other.meal_name != null)
	    return false;
	  } else if (!meal_name.equals(other.meal_name))
	   return false;
	  return true;
	 }
	
}
