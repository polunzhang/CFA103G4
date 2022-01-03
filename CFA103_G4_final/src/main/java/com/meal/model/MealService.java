package com.meal.model;

import java.util.List;

import com.meal_pic.model.*;

public class MealService {

	private MealDAO_interface dao;
	private MealPicDAO_interface picdao;
	

	public MealService() {
		dao = new MealJDBCDAO();
		picdao = new MealPicDAOJDBC();
	}

	public MealVO addMeal(Integer meal_type_no, Integer meal_price, String meal_name,
			String meal_intro) {

		MealVO mealvo = new MealVO();

		mealvo.setMeal_type_no(meal_type_no);
		mealvo.setMeal_price(meal_price);
		mealvo.setMeal_name(meal_name);
		mealvo.setMeal_intro(meal_intro);
		dao.insert(mealvo);
		return mealvo;
	}
	
	public MealVO addMeal1(Integer meal_type_no, Integer meal_price, String meal_name,
			String meal_intro,byte[] mealPic) {

		MealVO mealvo = new MealVO();

		mealvo.setMeal_type_no(meal_type_no);
		mealvo.setMeal_price(meal_price);
		mealvo.setMeal_name(meal_name);
		mealvo.setMeal_intro(meal_intro);
		dao.insertPicWithMealNo(mealvo, mealPic);

		return mealvo;
	}

	public MealVO updateMeal(Integer meal_type_no, Integer meal_price, String meal_name,
			String meal_intro,Integer mealno) {

		MealVO mealvo = new MealVO();

		mealvo.setMeal_type_no(meal_type_no);
		mealvo.setMeal_price(meal_price);
		mealvo.setMeal_name(meal_name);
		mealvo.setMeal_intro(meal_intro);
		mealvo.setMealno(mealno);	
		dao.update(mealvo);

		return mealvo;
	}

	public void deleteMeal(Integer mealno) {
		dao.delete(mealno);
	
	}
	public void deleteMealandpic(Integer mealno) {
		dao.deleteDataAndPic(mealno);
	
	}

	public MealVO getOneMeal(Integer mealno) {
		return dao.findByPrimaryKey(mealno);
	}
	
	public List<MealVO> getMealByName(String meal_name) {
	
		MealVO mealvo = new MealVO();
		mealvo.setMeal_name(meal_name);
		dao.findByMealName(meal_name);
		return dao.findByMealName(meal_name);
	}
	
	public List<MealVO> getOneMealType(Integer meal_type_no) {
		
		MealVO mealvo = new MealVO();
		mealvo.setMeal_type_no(meal_type_no);
		dao.findByMealType(meal_type_no);
		return dao.findByMealType(meal_type_no);
	}
	
	

	public List<MealVO> getAll() {
		return dao.getAll();
	}
	


}