package com.news_pic.model;

public class News_PicVO {
private Integer news_pic_no,newsno;
private byte[] news_image;
public Integer getNews_pic_no() {
	return news_pic_no;
}
public void setNews_pic_no(Integer news_pic_no) {
	this.news_pic_no = news_pic_no;
}
public Integer getNewsno() {
	return newsno;
}
public void setNewsno(Integer newsno) {
	this.newsno = newsno;
}
public byte[] getNews_image() {
	return news_image;
}
public void setNews_image(byte[] news_image) {
	this.news_image = news_image;
}

}
