package com.online_order.model;

import java.util.List;
import java.util.Map;

import com.online_detail.model.OnlineDetailVO;

public interface OnlineOrderDAO_interface {
	public void insert(OnlineOrderVO onlineOrderVO);
	public void update(OnlineOrderVO onlineOrderVO);
	public void delete(Integer olno);
	public OnlineOrderVO findByOlno(Integer olno);//依照訂單編號查詢
	public List<OnlineOrderVO> findByPayStatus(Integer pay_status);//客人取餐時查詢結帳狀態。
	public List<OnlineOrderVO> getAll();
	public List<OnlineOrderVO> findByAddress();
	public List<OnlineOrderVO> findByAddress2();
	public List<OnlineOrderVO> findByMemno(Integer memno);
	
	
	
	//同時新增訂單主檔和訂單明細(自增主鍵對應)
	public Integer insertWithDetails(OnlineOrderVO onlineOrderVO, List<OnlineDetailVO> list);
	
	//萬用複合查詢(傳入參數型態Map)(回傳 List)
    public List<OnlineOrderVO> getAll(Map<String, String[]> map); 
}
