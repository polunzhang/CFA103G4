package com.table.model;

import java.util.*;

import com.online_order.model.OnlineOrderVO;

public interface TableDAO_interface {
	
	public void insert(TableVO tableVO);
	public void update(TableVO tableVO);
	public void delete(Integer tableno);
	public TableVO findByPrimaryKey(Integer tableno);
	public List<TableVO> findByTableStatus(Integer table_status);
	public List<TableVO> findByTableNop(Integer table_nop);
	public List<TableVO> getAll();
	//查詢某桌的訂位明細(一對多)(回傳 Set)
//	public Set<TableVO> getReznoByTableno(Integer tableno);
	
	//萬用複合查詢(傳入參數型態Map)(回傳 List)
    public List<TableVO> getAll(Map<String, String[]> map); 

}
