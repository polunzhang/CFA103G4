package com.rez_time.model;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import com.rez.model.*;

public class RezTimeService {
	
	private RezTimeDAO_interface dao;
	
	public RezTimeService() {
		dao = new RezTimeDAO();
	}
	
	public RezTimeVO addRezTime(Date rez_time_date, Integer rez_time_mid_limit, Integer rez_time_mid, Integer rez_time_eve_limit, Integer rez_time_eve) {
		
		RezTimeVO reztimeVO = new RezTimeVO();
		
//		reztimeVO.setRez_time_no(rez_time_no);
		reztimeVO.setRez_time_date(rez_time_date);
		reztimeVO.setRez_time_mid_limit(rez_time_mid_limit);
		reztimeVO.setRez_time_mid(rez_time_mid);
		reztimeVO.setRez_time_eve_limit(rez_time_eve_limit);
		reztimeVO.setRez_time_eve(rez_time_eve);
		
		dao.insert(reztimeVO);
		return reztimeVO;
	}
	
	public RezTimeVO updateRezTime(Integer rez_time_no, Date rez_time_date, Integer rez_time_mid_limit, Integer rez_time_mid, Integer rez_time_eve_limit, Integer rez_time_eve) {
		
		RezTimeVO reztimeVO = new RezTimeVO();
		
		reztimeVO.setRez_time_no(rez_time_no);
		reztimeVO.setRez_time_date(rez_time_date);
		reztimeVO.setRez_time_mid_limit(rez_time_mid_limit);
		reztimeVO.setRez_time_mid(rez_time_mid);
		reztimeVO.setRez_time_eve_limit(rez_time_eve_limit);
		reztimeVO.setRez_time_eve(rez_time_eve);
		dao.update(reztimeVO);
		
		return reztimeVO;
	}
	
	public RezTimeVO updateRezTime2(Integer rez_time_no, Integer rez_time_mid) {
		
		RezTimeVO reztimeVO = new RezTimeVO();
		
		reztimeVO.setRez_time_no(rez_time_no);
		reztimeVO.setRez_time_mid(rez_time_mid);
		
		dao.update2(reztimeVO);
		
		return reztimeVO;
	}
	
public RezTimeVO updateRezTime3(Integer rez_time_no, Integer rez_time_eve) {
		
		RezTimeVO reztimeVO = new RezTimeVO();
		
		reztimeVO.setRez_time_no(rez_time_no);
		reztimeVO.setRez_time_mid(rez_time_eve);
		
		dao.update3(reztimeVO);
		
		return reztimeVO;
	}
	
	public RezTimeVO getOneRez_Time_NO(Integer rez_time_no) {
		return dao.findByRez_Time_NO(rez_time_no);
	}
	
	public RezTimeVO getOneRez_Time_Date(Date rez_time_date) {
		return dao.findByRez_Time_Date(rez_time_date);
	}
	
	public RezTimeVO getOneRez_Time_Mid(Integer rez_time_mid) {
		return dao.findByRez_Time_Mid(rez_time_mid);
	}
	
	public RezTimeVO getOneRez_Time_Eve(Integer rez_time_eve) {
		return dao.findByRez_Time_Eve(rez_time_eve);
	}
	
	public List<RezTimeVO> getAll() {
		return dao.getAll();
	}
	
}
