package com.emper.model;

import java.util.List;

public interface EmperDAO_interface {
	
	public void insert(EmperVO emperVO);
	public void update(EmperVO emperVO);
    public void delete(Integer emperno);
    public EmperVO findByPrimaryKey(Integer emperno);
    public List<EmperVO> getAll();

}
