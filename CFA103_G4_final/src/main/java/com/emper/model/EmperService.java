package com.emper.model;

import java.util.List;



public class EmperService {

	private EmperDAO_interface dao;

	public EmperService() {
		dao = new EmperJNDIDAO();
	}
	
	public EmperVO addEmper(String emper_name) {
	
		EmperVO emperVO = new EmperVO();
		emperVO.setEmper_name(emper_name);
		dao.insert(emperVO);
		
		return emperVO;
	}
	
	public EmperVO updateEmper(Integer emperno, String emper_name) {
		
		EmperVO emperVO = new EmperVO();
		emperVO.setEmperno(emperno);
		emperVO.setEmper_name(emper_name);
		dao.update(emperVO);
		
		return emperVO;
	}
	
	public void deleteEmper(Integer emperno) {
		dao.delete(emperno);
	}
	
	public EmperVO getOneEmper(Integer emperno) {
		return dao.findByPrimaryKey(emperno);
	}
	
	public List<EmperVO> getAll() {
		return dao.getAll();
	}
	
}
