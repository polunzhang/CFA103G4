package com.feedback.model;

import java.sql.Date;

public class FeedbackVO implements java.io.Serializable {
	private Integer feedbackno;
	private String feedback_phone;
	private String feedback_content;
	private Date feedback_date;
	
	public Integer getFeedback_no() {
		return feedbackno;
	}
	public void setFeedback_no(Integer feedback_no) {
		this.feedbackno = feedback_no;
	}
	public String getFeedback_phone() {
		return feedback_phone;
	}
	public void setFeedback_phone(String feedback_phone) {
		this.feedback_phone = feedback_phone;
	}
	public String getFeedback_content() {
		return feedback_content;
	}
	public void setFeedback_content(String feedback_content) {
		this.feedback_content = feedback_content;
	}
	public Date getFeedback_date() {
		return feedback_date;
	}
	public void setFeedback_date(Date feedback_date) {
		this.feedback_date = feedback_date;
	}


}
