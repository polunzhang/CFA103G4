package com.rez.model;

import java.util.List;
import com.rez_detail.model.*;
import java.sql.Timestamp;
import java.sql.Date;
import java.sql.Time;
import com.rez_time.model.*;

public class RezService {
	
	private RezDAO_interface dao;
	
	public RezService() {
		dao = new RezDAO();
	}
	
	public RezVO addRez(Integer rezno, Integer memno, String phone, Integer num_of_ppl, java.sql.Date reservationdate, java.sql.Time reservationtime, String email, String lastname, String sex) {
		
		RezVO rezVO = new RezVO();
		
		rezVO.setRezno(rezno);
		rezVO.setMemno(memno);
		rezVO.setPhone(phone);
		rezVO.setNum_of_ppl(num_of_ppl);
		rezVO.setReservationdate(reservationdate);
		rezVO.setReservationtime(reservationtime);
		rezVO.setEmail(email);
		rezVO.setLastname(lastname);
		rezVO.setSex(sex);
		dao.insert(rezVO);
		
		return rezVO;
	}
	
	public RezVO updateRez(Integer rezno, Integer memno, String phone, Integer num_of_ppl, java.sql.Date reservationdate, java.sql.Time reservationtime, String email, String lastname, String sex) {
		
		RezVO rezVO = new RezVO();
		
		rezVO.setRezno(rezno);
		rezVO.setMemno(memno);
		rezVO.setPhone(phone);
		rezVO.setNum_of_ppl(num_of_ppl);
		rezVO.setReservationdate(reservationdate);
		rezVO.setReservationtime(reservationtime);
		rezVO.setEmail(email);
		rezVO.setLastname(lastname);
		rezVO.setSex(sex);
		dao.update(rezVO);
		
		return rezVO;
	}
	
	public void deleteRez(Integer rezno) {
		dao.delete(rezno);
	}
	
	public RezVO getOneRez(Integer rezno) {
		return dao.findByRezNO(rezno);
	}
	
	public List<RezVO> getAll() {
		return dao.getAll();
	}
}
