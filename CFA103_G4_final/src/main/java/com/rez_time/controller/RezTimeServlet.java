package com.rez_time.controller;

import java.io.*;
import java.util.*;

import java.sql.Timestamp;
import java.sql.Date;

import javax.servlet.*;
import javax.servlet.http.*;

import com.rez.model.RezService;
import com.rez.model.RezVO;
import com.rez_time.model.RezTimeService;
import com.rez_time.model.RezTimeVO;
import com.rez.model.RezService;
import com.rez.model.*;
import com.rez_time.model.*;

public class RezTimeServlet extends HttpServlet {

	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		doPost(req, res);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {

		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");
		
		
		if("getOne_For_Display".equals(action)) { // select reztime_page.jsp請求
			
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			
			try {
				/***************************1.接收請求參數 - 輸入格式的錯誤處理**********************/
				String str = req.getParameter("rez_time_no");
			if (str == null || (str.trim()).length() == 0) {
				errorMsgs.add("請輸入時間編號");
			}	
				
			if (!errorMsgs.isEmpty()) {
				RequestDispatcher failureView = req
						.getRequestDispatcher("/back-end/reztime/selectRezTime_page.jsp");
				failureView.forward(req, res);
				return; //程式中斷
			}
			
			Integer rez_time_no = null;
		try {
			rez_time_no = new Integer(str);
		} catch (Exception e) {
			errorMsgs.add("時間編號格式不正確");
		}
		
		
		
		if (!errorMsgs.isEmpty()) {
			RequestDispatcher failureView = req
					.getRequestDispatcher("/back-end/reztime/selectRezTime_page.jsp");
			failureView.forward(req, res);
			return;//程式中斷
		}
		/***************************2.開始查詢資料*****************************************/
		RezTimeService rezSvc = new RezTimeService();
		RezTimeVO reztimeVO = rezSvc.getOneRez_Time_NO(rez_time_no);
		if (reztimeVO == null) {
			errorMsgs.add("查無資料");
		}
		if (!errorMsgs.isEmpty()) {
			RequestDispatcher failureView = req
					.getRequestDispatcher("/back-end/reztime/selectRezTime_page.jsp");
			failureView.forward(req, res);
		return; //程式中斷
		}
		/***************************3.查詢完成,準備轉交(Send the Success view)*************/
		req.setAttribute("reztimeVO", reztimeVO); // 資料庫取出的rezVO物件,存入req
		String url = "/back-end/reztime/listOneRezTime.jsp";
		RequestDispatcher successView = req.getRequestDispatcher(url); // 成功轉交
		successView.forward(req, res);
		/***************************其他可能的錯誤處理*************************************/
		} catch (Exception e) {
			errorMsgs.add("無法取得資料:" + e.getMessage());
			RequestDispatcher failureView = req
						.getRequestDispatcher("/back-end/reztime/selectRezTime_page.jsp");
			failureView.forward(req, res);
		}
	}	
			
		if ("getOne_For_Update".equals(action))	 { //來自listAllreztime.jsp請求
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			
		try {
			/***************************1.接收請求參數****************************************/
			Integer rez_time_no = new Integer(req.getParameter("rez_time_no"));
			
			/***************************2.開始查詢資料****************************************/
			RezTimeService reztimeSvc = new RezTimeService();
			RezTimeVO reztimeVO = reztimeSvc.getOneRez_Time_NO(rez_time_no);
			
			/***************************3.查詢完成,準備轉交(Send the Success view)************/
			req.setAttribute("reztimeVO", reztimeVO);         // 資料庫取出的reztimeVO物件,存入req
			String url = "/back-end/reztime/update_RezTime_input.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(url);// 成功轉交 update_reztime_input.jsp
			successView.forward(req, res);
			
			/***************************其他可能的錯誤處理**********************************/
		} catch (Exception e) {
			errorMsgs.add("無法取得要修改的資料:" + e.getMessage());
			RequestDispatcher failureView = req
					.getRequestDispatcher("/back-end/reztime/listAllRezTime.jsp");
			failureView.forward(req, res);
		}
	}
		
		//修改
		if ("update".equals(action)) { 
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			
			try {
				/***************************1.接收請求參數 - 輸入格式的錯誤處理**********************/

Integer rez_time_no = new Integer(req.getParameter("rez_time_no").trim());

				java.sql.Date rez_time_date = null;
				try {
rez_time_date = java.sql.Date.valueOf(req.getParameter("rez_time_date").trim());
				} catch (IllegalArgumentException e) {
					rez_time_date = new java.sql.Date(System.currentTimeMillis());
					errorMsgs.add("Hi!!!");
				}
				
Integer rez_time_mid_limit = new Integer(req.getParameter("rez_time_mid_limit").trim());

Integer rez_time_mid = new Integer(req.getParameter("rez_time_mid").trim());

Integer rez_time_eve_limit = new Integer(req.getParameter("rez_time_eve_limit").trim());

Integer rez_time_eve = new Integer(req.getParameter("rez_time_eve").trim());

			RezTimeVO reztimeVO = new RezTimeVO();
			reztimeVO.setRez_time_no(rez_time_no);
			reztimeVO.setRez_time_date(rez_time_date);
			reztimeVO.setRez_time_mid_limit(rez_time_mid_limit);
			reztimeVO.setRez_time_mid(rez_time_mid);
			reztimeVO.setRez_time_eve_limit(rez_time_eve_limit);
			reztimeVO.setRez_time_eve(rez_time_eve);
			
			if (!errorMsgs.isEmpty()) {
				req.setAttribute("reztimeVO", reztimeVO); // 含有輸入格式錯誤的reztimeVO物件,也存入req
				RequestDispatcher failureView = req
						.getRequestDispatcher("/back-end/reztime/update_RezTime_input.jsp");
				failureView.forward(req, res);
				return; //程式中斷
			}
			
			/***************************2.開始修改資料*****************************************/
RezTimeService reztimeSvc = new RezTimeService();
reztimeVO = reztimeSvc.updateRezTime(rez_time_no, rez_time_date, rez_time_mid_limit, rez_time_mid, rez_time_eve_limit, rez_time_eve);

			/***************************3.修改完成,準備轉交(Send the Success view)*************/
			req.setAttribute("reztimeVO", reztimeVO); // 資料庫update成功後,正確的的rezVO物件,存入req
			String url = "/back-end/reztime/listOneRezTime.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(url); // 修改成功後,轉交listOneRez.jsp
			successView.forward(req, res);
			
			/***************************其他可能的錯誤處理*************************************/
			} catch (Exception e) {
				errorMsgs.add("修改資料失敗:"+e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/back-end/reztime/update_RezTime_input.jsp");
				failureView.forward(req, res);
			}
		}
		
		
		// 新增
		if ("insert".equals(action)) { // 來自addReztime.jsp的請求
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			
			try {
				/***************************1.接收請求參數 - 輸入格式的錯誤處理**********************/
				java.sql.Date rez_time_date = null;
				try {
rez_time_date = java.sql.Date.valueOf(req.getParameter("rez_time_date").trim());
				} catch (IllegalArgumentException e) {
					rez_time_date = new java.sql.Date(System.currentTimeMillis());
					errorMsgs.add("Hi!!!");
				}
				
Integer rez_time_mid_limit = new Integer(req.getParameter("rez_time_mid_limit").trim());

Integer rez_time_mid = new Integer(req.getParameter("rez_time_mid").trim());

Integer rez_time_eve_limit = new Integer(req.getParameter("rez_time_eve_limit").trim());

Integer rez_time_eve = new Integer(req.getParameter("rez_time_eve").trim());	

			RezTimeVO reztimeVO = new RezTimeVO();
			reztimeVO.setRez_time_date(rez_time_date);
			reztimeVO.setRez_time_mid_limit(rez_time_mid_limit);
			reztimeVO.setRez_time_mid(rez_time_mid);
			reztimeVO.setRez_time_eve_limit(rez_time_eve_limit);
			reztimeVO.setRez_time_eve(rez_time_eve);
			
			if (!errorMsgs.isEmpty()) {
req.setAttribute("reztimeVO", reztimeVO);
			RequestDispatcher failureView = req
					.getRequestDispatcher("/back-end/reztime/addRezTime.jsp");
			failureView.forward(req, res);
			return;
			}
			
			/***************************2.開始新增資料*****************************************/
RezTimeService reztimeSvc = new RezTimeService();
reztimeVO = reztimeSvc.addRezTime(rez_time_date, rez_time_mid_limit, rez_time_mid, rez_time_eve_limit, rez_time_eve);

			/***************************3.新增完成,準備轉交(Send the Success view)*************/
			String url = "/back-end/reztime/listAllRezTime.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(url); // 新增成功後,轉交listAllRez.jsp
			successView.forward(req, res);
			
			/***************************其他可能的錯誤處理*************************************/
			} catch (Exception e) {
				errorMsgs.add("新增資料失敗:" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/back-end/reztime/addRezTime.jsp");
				failureView.forward(req, res);
			}
		}
		
	}
}

