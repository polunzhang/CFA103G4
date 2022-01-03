package com.meal.model;

import java.util.List;


import com.meal.model.MealVO;

public interface MealDAO_interface {
    public void insert(MealVO MealVO);
    public void insertPicWithMealNo(MealVO mealVO , byte[] mealPic );
    public void update(MealVO MealVO);
    public void delete(Integer mealno);
    public void deleteDataAndPic(Integer mealno);
    public MealVO findByPrimaryKey(Integer mealno);
    public List<MealVO> findByMealName(String mealname);
    public List<MealVO> findByMealType(Integer meal_type_no);
    public List<MealVO> getAll();

}
