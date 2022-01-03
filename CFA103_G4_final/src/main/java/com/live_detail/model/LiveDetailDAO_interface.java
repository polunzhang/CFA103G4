package com.live_detail.model;

import java.util.List;
import java.util.Map;
import java.util.Set;


public interface LiveDetailDAO_interface {
    public void insert(LiveDetailVO LiveDetailVO);
    public void update(LiveDetailVO LiveDetailVO);
    public void delete(Integer liveno , Integer mealno, Integer meal_set);
    public List<LiveDetailVO> findByPrimaryKey(Integer liveno);
    public List<LiveDetailVO> getAll();
    public void insert2 (LiveDetailVO liveDetailVO , java.sql.Connection con);
    
    //萬用複合查詢(傳入參數型態Map)(回傳 List)
    public List<LiveDetailVO> getAll(Map<String, String[]> map); 

}
