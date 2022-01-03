package com.news.model;

import java.sql.Date;
import java.util.List;



public class NewsService {
private NewsDAO_interface dao;

public NewsService() {
	dao = new NewsDAO();
}

public NewsVO addNews(Integer empno,String news_title,
		String news_content,Date news_time,byte[] news_pic) {
	NewsVO newsVO=new NewsVO();
	newsVO.setEmpno(empno);
	newsVO.setNews_title(news_title);
	newsVO.setNews_content(news_content);
	newsVO.setNews_time(news_time);
	
	dao.insertPicWithNewsNo(newsVO, news_pic);
	return newsVO;
}
public NewsVO updateNews(Integer newsno,Integer empno,String news_title,
		String news_content,Date news_time) {
	NewsVO newsVO=new NewsVO();
	newsVO.setNewsno(newsno);
	newsVO.setEmpno(empno);
	newsVO.setNews_title(news_title);
	newsVO.setNews_content(news_content);
	newsVO.setNews_time(news_time);
	
	dao.update(newsVO);
	
	return newsVO;
}

public void deleteNews(Integer newsno) {
	dao.delete(newsno);
}

public NewsVO getOneNews(Integer newsno) {
	return dao.findByPrimaryKey(newsno);
}
public List<NewsVO> getSameDate(Date date){
	return dao.getSameDay(date);
}

public List<NewsVO> getAll() {
	return dao.getAll();
}
}
