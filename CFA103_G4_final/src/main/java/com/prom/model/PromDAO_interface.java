package com.prom.model;

import java.util.List;

import com.prom.model.PromVO;

public interface PromDAO_interface {
	public PromVO insert(PromVO PromVO);
    public void update(PromVO PromVO);
    public void delete(Integer Promno);
    public PromVO findByPrimaryKey(Integer Promno);
    public List<PromVO> findByEmpno(Integer Empno);
    public List<PromVO> getAll();
    public byte[] findonepic(Integer Promno);
}
