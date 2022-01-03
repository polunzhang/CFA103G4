package com.feedback.model;

import java.sql.Date;
import java.util.List;

public class FeedbackService {
	private FeedbackDAO_interface dao;

	public FeedbackService() {
		dao = new FeedbackJDBCDAO();
	}

	public FeedbackVO addFeedback(String feedback_phone,String feedback_content,Date feedback_date) {
		FeedbackVO Feedbackvo = new FeedbackVO();
		Feedbackvo.setFeedback_phone(feedback_phone);
		Feedbackvo.setFeedback_content(feedback_content);
		Feedbackvo.setFeedback_date(feedback_date);
		return Feedbackvo;
	}

	
	public FeedbackVO getOneMealPic(Integer feedback_no) {
		return dao.findByPrimaryKey(feedback_no);
	}
	public List<FeedbackVO> getAll() {
		return dao.getAll();
	}
	
}
