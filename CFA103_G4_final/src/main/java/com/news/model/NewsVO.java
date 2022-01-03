package com.news.model;

import java.io.Serializable;
import java.sql.Date;
import java.util.Arrays;

public class NewsVO implements Serializable{
private Integer newsno,empno;
private String news_title,news_content;
private Date news_time;
public Integer getNewsno() {
	return newsno;
}
public void setNewsno(Integer newsno) {
	this.newsno = newsno;
}
public Integer getEmpno() {
	return empno;
}
public void setEmpno(Integer empno) {
	this.empno = empno;
}
public String getNews_title() {
	return news_title;
}
public void setNews_title(String news_title) {
	this.news_title = news_title;
}
public String getNews_content() {
	return news_content;
}
public void setNews_content(String news_content) {
	this.news_content = news_content;
}

public Date getNews_time() {
	return news_time;
}
public void setNews_time(Date news_time) {
	this.news_time = news_time;
}


}
