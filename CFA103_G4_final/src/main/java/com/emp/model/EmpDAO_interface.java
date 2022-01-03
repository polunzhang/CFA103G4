package com.emp.model;

import java.util.List;

public interface EmpDAO_interface {
	
	public void insert(EmpVO empVO);
	public void update(EmpVO empVO);
    public void delete(Integer empno);
    public EmpVO findByPrimaryKey(Integer empno);
    public EmpVO findByEaccount(String eaccount);
    public List<EmpVO> getAll();

}
