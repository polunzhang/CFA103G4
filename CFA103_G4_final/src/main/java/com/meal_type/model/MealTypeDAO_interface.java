package com.meal_type.model;

import java.util.List;

import com.meal_type.model.MealTypeVO;

public interface MealTypeDAO_interface {
	public void insert(MealTypeVO mealTypeVO);
	public void update(MealTypeVO mealTypeVO);
	public void delete(Integer mealTypeVO);
	public MealTypeVO findByPrimaryKey(Integer meal_type_no);
	public List<MealTypeVO> getAll();
}
