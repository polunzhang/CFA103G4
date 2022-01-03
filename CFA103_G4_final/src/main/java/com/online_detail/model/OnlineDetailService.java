package com.online_detail.model;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;

import com.live_order.model.LiveOrderService;
import com.live_order.model.LiveOrderVO;
import com.online_order.model.OnlineOrderService;
import com.online_order.model.OnlineOrderVO;

public class OnlineDetailService {

	private OnlineDetailDAO_interface dao;
	
	public OnlineDetailService() {
		dao = new OnlineDetailJDBCDAO();
	}

	public OnlineDetailVO addOnlineDetail(Integer olno, Integer mealno,Integer meal_amount, Integer meal_price,
			Integer meal_status, String meal_note, Integer meal_set) {
		
		OnlineDetailVO onlineDetailVO = new OnlineDetailVO();
		onlineDetailVO.setOlno(olno);
		onlineDetailVO.setMealno(mealno);
		onlineDetailVO.setMeal_amount(meal_amount);
		onlineDetailVO.setMeal_price(meal_price);
		onlineDetailVO.setMeal_status(meal_status);
		onlineDetailVO.setMeal_note(meal_note);
		onlineDetailVO.setMeal_set(meal_set);
		dao.insert(onlineDetailVO);
		return onlineDetailVO;
	}
	
	public OnlineDetailVO updateOnlineDetail(Integer meal_amount, Integer meal_price, Integer meal_status,
			String meal_note, Integer meal_set, Integer olno,Integer mealno) {
		
		OnlineDetailVO onlineDetailVO = new OnlineDetailVO();
		onlineDetailVO.setMeal_amount(meal_amount);
		onlineDetailVO.setMeal_price(meal_price);
		onlineDetailVO.setMeal_status(meal_status);
		onlineDetailVO.setMeal_note(meal_note);
		onlineDetailVO.setMeal_set(meal_set);
		onlineDetailVO.setOlno(olno);
		onlineDetailVO.setMealno(mealno);
		dao.update(onlineDetailVO);
		
		return onlineDetailVO;
	}
	
	public void deleteOnlineDetail(Integer mealno) {
		dao.delete(mealno);
	}
	
	public List<OnlineDetailVO> findByOlno(Integer olno){
		return dao.findByOlno(olno);
	}
	public List<OnlineDetailVO> findByMealStatus(Integer meal_status){
		return dao.findByMealStatus(meal_status);
	}
	
	
	
	
	public List<OnlineDetailVO> getAll(){
		return dao.getAll();
	}
	
	public String getTime(Integer olno) {
		OnlineOrderService LOSvc = new OnlineOrderService();
		OnlineOrderVO onlineOrderVO = LOSvc.getOneOnlineOrder(olno);
		Timestamp timestamp = onlineOrderVO.getCreate_time();
		
		SimpleDateFormat hhmmss = new SimpleDateFormat("HH:mm:ss");
		String timeString = hhmmss.format(timestamp);
		
		return timeString;
		
	}
	
	
	public long getCMS(Integer olno) {
		OnlineOrderService LOSvc = new OnlineOrderService();
		OnlineOrderVO onlineOrderVO = LOSvc.getOneOnlineOrder(olno);
		Timestamp timestamp = onlineOrderVO.getCreate_time();
		long CMS = timestamp.getTime();
		
		return CMS;
		
	}
	
}
