package com.news_pic.model;

import java.util.List;
import com.news.model.NewsVO;

public interface News_PicDAO_interface {
	public void insert( News_PicVO news_picVO);
	public void insert2(News_PicVO news_picVO,java.sql.Connection con);
    public void update(News_PicVO  news_picVO);
    public void update2(News_PicVO news_picVO);
    public void delete(Integer news_pic_no);
    public News_PicVO findByPrimaryKey(Integer news_pic_no,Integer newsno);
    public List<News_PicVO> getNewsnoPic(Integer newsno);
    public List<News_PicVO> getAll();
    
}
