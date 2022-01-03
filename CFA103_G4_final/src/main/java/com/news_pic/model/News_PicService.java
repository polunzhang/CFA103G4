package com.news_pic.model;

import java.sql.Date;
import java.util.List;

import com.news.model.NewsVO;

public class News_PicService {
	private News_PicDAO_interface dao;

	public News_PicService() {
		dao = new News_PicDAO();
	}
	
	public News_PicVO addNewsPic(Integer newsno,byte[] news_image) {
		News_PicVO news_picVO=new News_PicVO();
		news_picVO.setNewsno(newsno);
		news_picVO.setNews_image(news_image);
		
		
		dao.insert(news_picVO);
		return news_picVO;
	}
	
	public News_PicVO updateNewsPic(Integer newsno,byte[] news_image) {
		News_PicVO news_picVO=new News_PicVO();
		 
		 news_picVO.setNewsno(newsno);
		 news_picVO.setNews_image(news_image);
		
		dao.update2(news_picVO);
		return news_picVO;
	}
	
	public void deleteNewsPic(Integer news_pic_no) {
		dao.delete(news_pic_no);
	}
	
	public News_PicVO getOneNewsPic(Integer news_pic_no,Integer newsno) {
		return dao.findByPrimaryKey(news_pic_no,newsno);
	}
	
	public List<News_PicVO> getAll() {
		return dao.getAll();
	}
	public List<News_PicVO> getNewsnoPic(Integer newsno){
		return dao.getNewsnoPic(newsno);
	}
	
	
}
