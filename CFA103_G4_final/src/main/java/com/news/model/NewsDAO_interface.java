package com.news.model;

import java.sql.Date;
import java.util.List;



public interface NewsDAO_interface {
	public void insert( NewsVO newsVO);
	public void insertPicWithNewsNo(NewsVO newsVO , byte[] newsPic );
    public void update(NewsVO newsVO);
    public void updatePicWithNewsNo(NewsVO newsVO , byte[] newsPic );
    public void delete(Integer newsno);
    public NewsVO findByPrimaryKey(Integer newsno);
    public List<NewsVO> getAll();
    public List<NewsVO> getSameDay(Date news_time);
    
}
