package com.meal_type.model;

import java.util.List;

public class MealTypeService {

	private MealTypeDAO_interface dao;
	
	public MealTypeService() {
		dao = new MealTypeJDBCDAO();
	}
	
	public MealTypeVO addMealType(String meal_type_name) {
		MealTypeVO mealTypeVO = new MealTypeVO();
		
		mealTypeVO.setMeal_type_name(meal_type_name);
		dao.insert(mealTypeVO);
		
		return mealTypeVO;
	}
	
	public MealTypeVO updateMealType(Integer meal_type_no, String meal_type_name) {
		
		MealTypeVO mealTypeVO = new MealTypeVO();
		
		mealTypeVO.setMeal_type_no(meal_type_no);
		mealTypeVO.setMeal_type_name(meal_type_name);
		
		dao.update(mealTypeVO);
		
		return mealTypeVO;
	}
	
	public void deleteMealType(Integer meal_type_no) {
		dao.delete(meal_type_no);
	}
	
	public MealTypeVO findByPrimaryKey(Integer meal_type_no) {
		return dao.findByPrimaryKey(meal_type_no);
	}
	
	public List<MealTypeVO> getAll(){
		return dao.getAll();
	}
}
