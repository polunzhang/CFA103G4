package com.table.model;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.live_order.model.LiveOrderVO;
import com.online_order.model.OnlineOrderVO;

public class TableService {

	private TableDAO_interface dao;

	public TableService() {
		dao = new TableDAO();
	}

	public TableVO addTable(Integer table_nop, Integer table_status) {

		TableVO tableVO = new TableVO();

		tableVO.setTable_nop(table_nop);
		tableVO.setTable_status(table_status);
		dao.insert(tableVO);

		return tableVO;
	}

//	public TableVO updateTable(Integer tableno, Integer table_nop, Integer table_status, Integer left, Integer top) {
//
//		TableVO tableVO = new TableVO();
//
//		tableVO.setTableno(tableno);
//		tableVO.setTable_nop(table_nop);
//		tableVO.setTable_status(table_status);
//		tableVO.setLeft(left);
//		tableVO.setTop(top);
//		dao.update(tableVO);
//
//		return tableVO;
//	}
	
	public TableVO updateTable(TableVO tableVO) {
		dao.update(tableVO);
		return tableVO;
	}

	public void deleteTable(Integer tableno) {
		dao.delete(tableno);
	}

	public TableVO getOneTable(Integer tableno) {
		return dao.findByPrimaryKey(tableno);
	}

	public List<TableVO> findByTableNop(Integer table_nop) {
		return dao.findByTableNop(table_nop);
	}
	public List<TableVO> findByTableStatus(Integer table_status) {
		return dao.findByTableStatus(table_status);
	}
	
	public List<TableVO> getAll() {
		return dao.getAll();
	}
	
	public List<TableVO> getAll(Map<String, String[]> map) {
		return dao.getAll(map);
	}
	
	public List<TableVO> getAllAvailable() {
		Map<String, String[]> map = new TreeMap<String, String[]>();
		map.put("table_status", new String[] { "0" });
		return dao.getAll(map);
	}
}
