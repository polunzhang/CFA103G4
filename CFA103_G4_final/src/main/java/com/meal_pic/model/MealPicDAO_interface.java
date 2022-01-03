package com.meal_pic.model;
import java.sql.Connection;
import java.util.List;

public interface MealPicDAO_interface {

	public void insert(MealPicVO MealPicVO);
    public void insert2(MealPicVO MealPicVO,java.sql.Connection con);
    public void update(MealPicVO MealPicVO);
    public void update2(MealPicVO MealPicVO);
    public void delete(Integer mealno);
    public void delete2(Integer mealno,java.sql.Connection con);
    public MealPicVO findByPrimaryKey(Integer meal_pic_no);
    public List<MealPicVO> getAll();
}
