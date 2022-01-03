package com.emperDetail.model;

import java.util.List;


public class EmperDetailService {

	private EmperDetailDAO_interface dao;

	public EmperDetailService() {
		dao = new EmperDetailJNDIDAO();
	}

	public EmperDetailVO addEmperDetail(Integer emperno, Integer empno) {

		EmperDetailVO emperDetailVO = new EmperDetailVO();
		emperDetailVO.setEmperno(emperno);
		emperDetailVO.setEmpno(empno);
		dao.insert(emperDetailVO);
		return emperDetailVO;
	}

	public EmperDetailVO updateEmperDetail(Integer emperno, Integer empno) {

		EmperDetailVO emperDetailVO = new EmperDetailVO();
		emperDetailVO.setEmperno(emperno);
		emperDetailVO.setEmpno(empno);
		dao.update(emperDetailVO);
		return emperDetailVO;
	}

	public void deleteEmperDetail(Integer empno, Integer emperno) {
		dao.delete(empno, emperno);
	}
	
	public List<EmperDetailVO> getByEmpno(Integer empno) {
		return dao.findByEmp(empno);
	}
	
	public List<EmperDetailVO> getByEmperno(Integer emperno) {
		return dao.findByEmper(emperno);
	}
	
	public List<EmperDetailVO> getAll() {
		return dao.getAll();
	}
	
}
