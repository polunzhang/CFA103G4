package com.online_detail.controller;

import java.io.*;
import java.util.*;

import javax.servlet.*;
import javax.servlet.http.*;
import com.online_detail.model.*;
import com.online_order.model.*;

public class OnlineDetailServlet extends HttpServlet{

	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		doPost(req, res);
	}
	
	public void doPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		
		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");
		
		
		if("getOne_For_Display".equals(action)) { // 來自select_page_online_detail.jsp的請求
			
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
			
			try {
				/***************************1.接收請求參數 - 輸入格式的錯誤處理**********************/
				String str = req.getParameter("olno");
				if (str == null || (str.trim()).length() == 0) {
					errorMsgs.add("請輸入訂單編號");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/back-end/online_detail/select_page_online_detail.jsp");
					failureView.forward(req, res);
					return;//程式中斷
				}
				
				Integer olno = null;
				try {
					olno = new Integer(str);
				} catch (Exception e) {
					errorMsgs.add("訂單編號格式不正確");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/back-end/online_detail/select_page_online_detail.jsp");
					failureView.forward(req, res);
					return;//程式中斷
				}
				
//				/***************************2.開始查詢資料*****************************************/
				OnlineDetailService onlineDetailSvc = new OnlineDetailService();
				List<OnlineDetailVO> onlineDetailVO = onlineDetailSvc.findByOlno(olno);
				ServletContext context = getServletContext();
				context.setAttribute("olno", olno);
				if (onlineDetailVO == null) {
					errorMsgs.add("查無資料");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/back-end/online_detail/select_page_online_detail.jsp");
					failureView.forward(req, res);
					return;//程式中斷
				}
				
				/***************************3.查詢完成,準備轉交(Send the Success view)*************/
				req.setAttribute("onlineDetailVO", onlineDetailVO); // 資料庫取出的empVO物件,存入req
				String url = "/back-end/online_detail/listOneOnlineDetail.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 成功轉交 listOneEmp.jsp
				successView.forward(req, res);
				
				/***************************其他可能的錯誤處理*************************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得資料:" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/back-end/online_detail/select_page_online_detail.jsp");
				failureView.forward(req, res);
			}
		}
		if("getOne_For_Display2".equals(action)) { // 來自select_page_online_detail.jsp的請求
			
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
			
			try {
				/***************************1.接收請求參數 - 輸入格式的錯誤處理**********************/
				String str = req.getParameter("meal_status");
				if (str == null || (str.trim()).length() == 0) {
					errorMsgs.add("請選擇餐點狀態");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/back-end/online_detail/select_page_online_detail.jsp");
					failureView.forward(req, res);
					return;//程式中斷
				}
				
				Integer meal_status = null;
				try {
					meal_status = new Integer(str);
				} catch (Exception e) {
					errorMsgs.add("訂單編號格式不正確");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/back-end/online_detail/select_page_online_detail.jsp");
					failureView.forward(req, res);
					return;//程式中斷
				}
				
//				/***************************2.開始查詢資料*****************************************/
				OnlineDetailService onlineDetailSvc = new OnlineDetailService();
				List<OnlineDetailVO> onlineDetailVO = onlineDetailSvc.findByOlno(meal_status);
				ServletContext context = getServletContext();
				context.setAttribute("meal_status", meal_status);
				if (onlineDetailVO == null) {
					errorMsgs.add("查無資料");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/back-end/online_detail/select_page_online_detail.jsp");
					failureView.forward(req, res);
					return;//程式中斷
				}
				
				/***************************3.查詢完成,準備轉交(Send the Success view)*************/
				req.setAttribute("onlineDetailVO", onlineDetailVO); // 資料庫取出的empVO物件,存入req
				String url = "/back-end/online_detail/listOneOnlineDetail2.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 成功轉交 listOneEmp.jsp
				successView.forward(req, res);
				
				/***************************其他可能的錯誤處理*************************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得資料:" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/back-end/online_detail/select_page_online_detail.jsp");
				failureView.forward(req, res);
			}
		}
	
	}
}
