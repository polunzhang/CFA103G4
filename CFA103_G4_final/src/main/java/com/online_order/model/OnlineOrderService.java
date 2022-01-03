package com.online_order.model;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.online_detail.model.OnlineDetailVO;


public class OnlineOrderService {
	
	private OnlineOrderDAO_interface dao;
	
	public OnlineOrderService() {
		dao = new OnlineOrderJDBCDAO();
	}
	
	public Integer addOrderWithDetail(Integer empno, Integer memno, Integer pay_status,
			String address, java.sql.Timestamp set_time, Integer total, Integer pay_way, List<OnlineDetailVO> list) {
		
		OnlineOrderVO onlineOrderVO = new OnlineOrderVO();
		
		onlineOrderVO.setEmpno(empno);
		onlineOrderVO.setMemno(memno);
		onlineOrderVO.setPay_status(pay_status);
		onlineOrderVO.setAddress(address);
		onlineOrderVO.setSet_time(set_time);
		onlineOrderVO.setCreate_time(new Timestamp(System.currentTimeMillis()));
		onlineOrderVO.setTotal(total);
		onlineOrderVO.setPay_way(pay_way);
		Integer olno = dao.insertWithDetails(onlineOrderVO, list);
		return olno;
	}
	
	public OnlineOrderVO updateOnlineOrder(Integer olno, Integer empno, Integer memno,
			Integer pay_status, String address, java.sql.Timestamp set_time, java.sql.Timestamp create_time,
			Integer total, Integer pay_way) {
		
		OnlineOrderVO onlineOrderVO = new OnlineOrderVO();
		
		onlineOrderVO.setOlno(olno);
		onlineOrderVO.setEmpno(empno);
		onlineOrderVO.setMemno(memno);
		onlineOrderVO.setPay_status(pay_status);
		onlineOrderVO.setAddress(address);
		onlineOrderVO.setSet_time(set_time);
		onlineOrderVO.setCreate_time(create_time);
		onlineOrderVO.setTotal(total);
		onlineOrderVO.setPay_way(pay_way);
		dao.update(onlineOrderVO);
		
		return onlineOrderVO;
	}
	
	public OnlineOrderVO updateOnlineOrder(OnlineOrderVO onlineorderVO) {
		dao.update(onlineorderVO);
		return onlineorderVO;
	}
	
	
	public void deleteOnlineOrder(Integer olno) {
		dao.delete(olno);
	}
	
	public OnlineOrderVO getOneOnlineOrder(Integer olno) {
		return dao.findByOlno(olno);
	}
	
	public List<OnlineOrderVO> findByPayStatus(Integer pay_status){
		return dao.findByPayStatus(pay_status);
	}
	
	public List<OnlineOrderVO> getAll(){
		return dao.getAll();
	}
	public List<OnlineOrderVO> findByAddress(){
		return dao.findByAddress();
	}
	public List<OnlineOrderVO> findByAddress2(){
		return dao.findByAddress2();
	}
	
	public List<OnlineOrderVO> findByMemno(Integer memno){
		return dao.findByMemno(memno);
	}
	
	public List<OnlineOrderVO> getAll(Map<String, String[]> map) {
		return dao.getAll(map);
	}
	
	public List<OnlineOrderVO> getAllUncheck() {
		Map<String, String[]> map = new TreeMap<String, String[]>();
		map.put("pay_status", new String[] { "0" });
		map.put("pay_way", new String[] { "0" });
		return dao.getAll(map);
	}

}
