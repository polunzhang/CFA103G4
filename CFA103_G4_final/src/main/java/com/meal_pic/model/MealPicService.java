package com.meal_pic.model;

import java.util.List;

public class MealPicService {

	private MealPicDAO_interface dao;

	public MealPicService() {
		dao = new MealPicDAOJDBC();
	}

	public MealPicVO addMealPic(Integer mealno, byte[] meal_pic) {
		MealPicVO mealPicVO = new MealPicVO();
		mealPicVO.setMealno(mealno);
		mealPicVO.setMeal_pic(meal_pic);
		dao.insert(mealPicVO);
		return mealPicVO;
	}

	public MealPicVO updateMealPic(Integer meal_pic_no, Integer mealno, byte[] meal_pic) {
		MealPicVO mealPicVO = new MealPicVO();
		mealPicVO.setMeal_pic_no(meal_pic_no);
		mealPicVO.setMealno(mealno);
		mealPicVO.setMeal_pic(meal_pic);
		dao.update(mealPicVO);
		return mealPicVO;
	}
	
	public MealPicVO updateMealPicByMealNo(Integer mealno, byte[] meal_pic) {
		MealPicVO mealPicVO = new MealPicVO();
		mealPicVO.setMealno(mealno);
		mealPicVO.setMeal_pic(meal_pic);
		dao.update2(mealPicVO);
		return mealPicVO;
	}

	public void deleteMealPic(Integer mealno) {
		dao.delete(mealno);
	}
	
	public MealPicVO getOneMealPic(Integer meal_pic) {
		return dao.findByPrimaryKey(meal_pic);
	}
	public List<MealPicVO> getAllMealPic() {
		return dao.getAll();
	}
	
}
