package com.cs_rec.model;

import java.sql.Timestamp;
import java.util.List;


public class Cs_RecService {
	
	private Cs_RecDAO_interface dao;

	public Cs_RecService() {
		dao = new Cs_RecDAO();
	}

	public Cs_RecVO addCS_REC(Integer csno, Integer memno, Integer empno, String msg_context, Integer msg_direct, 
			Timestamp msg_time, byte[] msg_image) {

		Cs_RecVO cs_recVO = new Cs_RecVO();

		cs_recVO.setCsno(csno);
		cs_recVO.setMemno(memno);
		cs_recVO.setEmpno(empno);
		cs_recVO.setMsg_context(msg_context);
		cs_recVO.setMsg_direct(msg_direct);
		cs_recVO.setMsg_time(msg_time);
		cs_recVO.setMsg_image(msg_image);
		dao.insert(cs_recVO);

		return cs_recVO;
	}

	public Cs_RecVO updateCS_REC(Integer csno, Integer memno, Integer empno, String msg_context, Integer msg_direct, 
			Timestamp msg_time, byte[] msg_image) {

		Cs_RecVO cs_recVO = new Cs_RecVO();

		cs_recVO.setCsno(csno);
		cs_recVO.setMemno(memno);
		cs_recVO.setEmpno(empno);
		cs_recVO.setMsg_context(msg_context);
		cs_recVO.setMsg_direct(msg_direct);
		cs_recVO.setMsg_time(msg_time);
		cs_recVO.setMsg_image(msg_image);
		dao.insert(cs_recVO);

		return cs_recVO;
	}

	public void deleteEmp(Integer csno) {
		dao.delete(csno);
	}

	public Cs_RecVO getOneEmp(Integer csno) {
		return dao.findByPrimaryKey(csno);
	}

	public List<Cs_RecVO> getAll() {
		return dao.getAll();
	}
}
