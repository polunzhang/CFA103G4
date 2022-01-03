package com.feedback.model;

import java.util.List;

import com.feedback.model.*;

public interface FeedbackDAO_interface {
	public void insert(FeedbackVO feedbackvo);
    public FeedbackVO findByPrimaryKey(Integer feedbackno); 
    public List<FeedbackVO> getAll();
}
