package com.cs_rec.model;

import java.util.List;

public interface Cs_RecDAO_interface {
	public void insert(Cs_RecVO cs_recVO);
    public void update(Cs_RecVO cs_recVO);
    public void delete(Integer csno);
    public Cs_RecVO findByPrimaryKey(Integer csno);
    public List<Cs_RecVO> getAll();
    
}
