package com.rez_detail.model;

import java.util.List;

public class RezDetailService {

	private RezDetailDAO_interface dao;
	
	public RezDetailService() {
		dao = new RezDetailDAO();
	}
	
	public RezDetailVO addRezDetail(Integer rezno, Integer tableno) {
		
		RezDetailVO rezdetailVO = new RezDetailVO();
		
		rezdetailVO.setRezno(rezno);
		rezdetailVO.setTableno(tableno);
		dao.insert(rezdetailVO);
		
		return rezdetailVO;
	}
	
	public RezDetailVO updateRezDetail(Integer rezno, Integer tableno) {
		
		RezDetailVO rezdetailVO = new RezDetailVO();
		
		rezdetailVO.setRezno(rezno);
		rezdetailVO.setTableno(tableno);
		dao.update(rezdetailVO);
		
		return rezdetailVO;
	}
	
	public RezDetailVO getOneRezno(Integer rezno) {
		return dao.findByRezNo(rezno);
	}
	
	public List<RezDetailVO> getAll() {
		return dao.getAll();
	}
	
	public RezDetailVO getOneTableno(Integer tableno) {
		return dao.findByTableno(tableno);
	}
}
