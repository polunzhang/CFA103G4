package com.emperDetail.model;

import java.util.List;
import java.util.Set;


public interface EmperDetailDAO_interface {

	public void insert(EmperDetailVO emperDetailVO);
	public void update(EmperDetailVO emperDetailVO);
    public void delete(Integer empno, Integer emperno);
    public List<EmperDetailVO> findByEmp(Integer empno);
    public List<EmperDetailVO> findByEmper(Integer emperno);
    public List<EmperDetailVO> getAll();
}
