package com.prom_detail.model;

import java.util.List;


public interface PromDetailDAO_interface {
    public void insert(PromDetailVO promdetailvo);
    public void update(PromDetailVO promdetailvo);
    public void delete(PromDetailVO promdetailvo);
    public List<PromDetailVO> findByPrimaryKey(Integer promno);
    public PromDetailVO findByPrimaryKeyLimitOne(Integer promno);
    public List<PromDetailVO> findByMealNo(Integer mealno);
    public List<PromDetailVO> getAll();
}
