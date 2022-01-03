package com.table.model;

import java.io.Serializable;

public class TableVO implements Serializable {
	
	private Integer tableno; // auto increment
	private Integer table_nop;
	private Integer table_status;
	private Integer table_left;
	private Integer table_top;
	
	
	public TableVO() {}
	
	public int getTableno() {
		return tableno;
	}
	public void setTableno(Integer tableno) {
		this.tableno = tableno;
	}
	
	public int getTable_nop() {
		return table_nop;
	}
	public void setTable_nop(Integer table_nop) {
		this.table_nop = table_nop;
	}
	
	public int getTable_status() {
		return table_status;
	}
	public void setTable_status(Integer table_status) {
		this.table_status = table_status;
	}

	public Integer getTable_left() {
		return table_left;
	}

	public void setTable_left(Integer table_left) {
		this.table_left = table_left;
	}

	public Integer getTable_top() {
		return table_top;
	}

	public void setTable_top(Integer table_top) {
		this.table_top = table_top;
	}
	
	

}
