package com.live_detail.model;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.live_order.model.LiveOrderService;
import com.live_order.model.LiveOrderVO;

public class LiveDetailService {

	private LiveDetailDAO_interface dao;

	public LiveDetailService() {
		dao = new LiveDetailDAO();
	}

	public LiveDetailVO addOrderDetail(Integer liveno, Integer mealno, Integer meal_amount,Integer meal_price, Integer mean_set) {
		LiveDetailVO liveDetailVO = new LiveDetailVO();
		liveDetailVO.setLiveno(liveno);
		liveDetailVO.setMealno(mealno);
		liveDetailVO.setMeal_amount(meal_amount);
		liveDetailVO.setMeal_price(meal_price);
		liveDetailVO.setMeal_status(0);
		liveDetailVO.setMeal_set(mean_set);
		dao.insert(liveDetailVO);
		return liveDetailVO;
	}

	public LiveDetailVO updateOrderDetail(Integer liveno, Integer mealno, Integer meal_amount, Integer meal_price,
			Integer meal_status, String meal_note, Integer meal_set) {
		LiveDetailVO liveDetailVO = new LiveDetailVO();
		liveDetailVO.setMealno(mealno);
		liveDetailVO.setMeal_amount(meal_amount);
		liveDetailVO.setMeal_price(meal_price);
		liveDetailVO.setMeal_status(meal_status);
		liveDetailVO.setMeal_note(meal_note);
		liveDetailVO.setMeal_set(meal_set);
		dao.update(liveDetailVO);
		return liveDetailVO;
	}

	public void updateOrderDetail(LiveDetailVO liveDetailVO) {
		dao.update(liveDetailVO);
	}

	public List<LiveDetailVO> getListByLiveNO(Integer liveno) {
		return dao.findByPrimaryKey(liveno);
	}

	public List<LiveDetailVO> getAll() {
		return dao.getAll();
	}

	
	public List<LiveDetailVO> getAllUncooked() {
		Map<String, String[]> map = new TreeMap<String, String[]>();
		map.put("meal_status", new String[] { "0" });
		return dao.getAll(map);
	}
	
	public List<LiveDetailVO> getAllUncooked(String liveno) {
		Map<String, String[]> map = new TreeMap<String, String[]>();
		map.put("meal_status", new String[] { "0" });
		map.put("liveno", new String[] { liveno });
		return dao.getAll(map);
	}
	
	public List<LiveDetailVO> getAllUnserved() {
		Map<String, String[]> map = new TreeMap<String, String[]>();
		map.put("meal_status", new String[] { "1" });
		return dao.getAll(map);
	}
	public List<LiveDetailVO> getAllUnserved(String liveno) {
		Map<String, String[]> map = new TreeMap<String, String[]>();
		map.put("meal_status", new String[] { "1" });
		map.put("liveno", new String[] { liveno });
		return dao.getAll(map);
	}

	public List<LiveDetailVO> getAll(Map<String, String[]> map) {
		return dao.getAll(map);
	}

	public void deleteOneDetail(Integer liveno, Integer mealno, Integer meal_set) {
		dao.delete(liveno, mealno, meal_set);
	}

	public String getTime(Integer liveno) {
		LiveOrderService LOSvc = new LiveOrderService();
		LiveOrderVO liveOrderVO = LOSvc.getOneByLiveNO(liveno);
		Timestamp timestamp = liveOrderVO.getCreate_time();
		
		SimpleDateFormat hhmmss = new SimpleDateFormat("HH:mm:ss");
		String timeString = hhmmss.format(timestamp);
		
		return timeString;
		
	}
	
	public long getCMS(Integer liveno) {
		LiveOrderService LOSvc = new LiveOrderService();
		LiveOrderVO liveOrderVO = LOSvc.getOneByLiveNO(liveno);
		Timestamp timestamp = liveOrderVO.getCreate_time();
		long CMS = timestamp.getTime();
		
		return CMS;
		
	}
}
