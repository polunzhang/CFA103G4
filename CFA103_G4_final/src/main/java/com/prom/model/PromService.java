package com.prom.model;

import java.util.List;

public class PromService {

	private PromDAO_interface dao;

	public PromService() {
		dao = new PromJDBCDAO();
	}

	public PromVO addProm(Integer empno, String prom_title, String prom_content,
			byte[] prom_image) {

		PromVO Promvo = new PromVO();

		Promvo.setEmpno(empno);
		Promvo.setProm_title(prom_title);
		Promvo.setProm_content(prom_content);
		Promvo.setProm_image(prom_image);
		PromVO promnoo = dao.insert(Promvo);
		
		System.out.println("Service¨ú±o"+promnoo.getPromno());
		return promnoo;
	}

	public PromVO updateProm(Integer promno, Integer empno, String prom_title,
			String prom_content,byte[] prom_image) {

		PromVO Promvo = new PromVO();

		Promvo.setEmpno(empno);
		Promvo.setProm_title(prom_title);
		Promvo.setProm_content(prom_content);
		Promvo.setProm_image(prom_image);
		Promvo.setPromno(promno);	
		dao.update(Promvo);

		return Promvo;
	}

	public void deletePromvo(Integer promno) {
		dao.delete(promno);
	}

	public PromVO getOneprom(Integer promno) {
		return dao.findByPrimaryKey(promno);
	}
	
	public List<PromVO> getOneMealType(Integer empno) {
		
		PromVO Promvo = new PromVO();
		Promvo.setEmpno(empno);
		dao.findByEmpno(empno);
		
		return dao.findByEmpno(empno);
	}
	
	

	public List<PromVO> getAll() {
		return dao.getAll();
	}
}