package com.rez_detail.model;

import java.util.List;

public interface RezDetailDAO_interface {
	public void insert(RezDetailVO rezdetailVO);
	public void update(RezDetailVO rezdetailVO);
	public RezDetailVO findByRezNo(Integer rezno);
	public List<RezDetailVO> getAll();
	public RezDetailVO findByTableno(Integer tableno);
}
