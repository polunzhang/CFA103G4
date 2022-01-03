package com.rez_time.model;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import com.rez.model.*;

public interface RezTimeDAO_interface {
	public void insert(RezTimeVO reztimeVO);
	public void update(RezTimeVO reztimeVO);
	public void update2(RezTimeVO reztimeVO);
	public void update3(RezTimeVO reztimeVO);
	public List<RezTimeVO> getAll();
	public RezTimeVO findByRez_Time_NO(Integer rez_time_no);
	public RezTimeVO findByRez_Time_Mid(Integer rez_time_mid);
	public RezTimeVO findByRez_Time_Eve(Integer rez_time_eve);
	public RezTimeVO findByRez_Time_Date(Date rez_time_date);
	
//	public void delete(RezTimeVO reztimeVO);
}
