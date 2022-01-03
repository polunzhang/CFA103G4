package com.online_detail.model;

import java.util.List;

public interface OnlineDetailDAO_interface {
	public void insert(OnlineDetailVO onlineDetailVO);
	public void update(OnlineDetailVO onlineDetailVO);
	public void delete(Integer mealno);
	public List<OnlineDetailVO> findByOlno(Integer olno);
	public List<OnlineDetailVO> findByMealStatus(Integer meal_status);
	public List<OnlineDetailVO> getAll();
	
	//同時新增訂單主檔和訂單明細(自增主鍵對應)
	public void insert2 (OnlineDetailVO onlineDetailVO, java.sql.Connection con);
}
