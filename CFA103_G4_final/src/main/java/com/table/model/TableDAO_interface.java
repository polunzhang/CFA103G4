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
	//�d�߬Y�઺�q�����(�@��h)(�^�� Set)
//	public Set<TableVO> getReznoByTableno(Integer tableno);
	
	//�U�νƦX�d��(�ǤJ�Ѽƫ��AMap)(�^�� List)
    public List<TableVO> getAll(Map<String, String[]> map); 

}
