package com.emp.model;


import java.util.List;

public class EmpService {

	private EmpDAO_interface dao;

	public EmpService() {
		dao = new EmpJNDIDAO();
	}
	
	public EmpVO addEmp(String job, Integer sal, String ename, java.sql.Date hiredate,
			String eaccount, String epassword, Integer job_status) {
		
		EmpVO empVO = new EmpVO();
		empVO.setJob(job);
		empVO.setSal(sal);
		empVO.setEname(ename);
		empVO.setHiredate(hiredate);
		empVO.setEaccount(eaccount);
		empVO.setEpassword(epassword);
		empVO.setJob_status(job_status);
		dao.insert(empVO);
		
		return empVO;
	}
	
	public EmpVO updateEmp(Integer empno, String job, Integer sal, String ename,
			java.sql.Date hiredate, String epassword, Integer job_status) {
		
		EmpVO empVO = new EmpVO();
		empVO.setEmpno(empno);
		empVO.setJob(job);
		empVO.setSal(sal);
		empVO.setEname(ename);
		empVO.setHiredate(hiredate);
		
		empVO.setEpassword(epassword);
		empVO.setJob_status(job_status);
		dao.update(empVO);
		
		return getOneEmp(empno);
	}
	
	public void deleteEmp(Integer empno) {
		dao.delete(empno);
	}
	
	public EmpVO getOneEmp(Integer empno) {
		return dao.findByPrimaryKey(empno);
	}
	
	public EmpVO getOneEaccount(String  eaccount) {
		return dao.findByEaccount(eaccount);
	}
	
	public List<EmpVO> getAll() {
		return dao.getAll();
	}
}
