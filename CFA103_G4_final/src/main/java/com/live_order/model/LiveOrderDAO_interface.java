package com.live_order.model;

import java.util.List;
import java.util.Map;

import com.live_detail.model.LiveDetailVO;

public interface LiveOrderDAO_interface {
    public void insert(LiveOrderVO LiveOrderVO);
    public void update(LiveOrderVO LiveOrderVO);
    //內用訂單，一桌只會查到一筆未結帳訂單
    public LiveOrderVO findByTableNO(Integer tableno , Integer pay_status);
    public LiveOrderVO findByTableNO(Integer tableno);
    public List<LiveOrderVO> findByPayStatus(Integer pay_status);
    public List<LiveOrderVO> getAll();
    public Integer insertWithDetails(LiveOrderVO liveOrderVO , List<LiveDetailVO> list);
    public LiveOrderVO findByPrimaryKey(Integer liveno);

    //萬用複合查詢(傳入參數型態Map)(回傳 List)
    public List<LiveOrderVO> getAll(Map<String, String[]> map); 

}
