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
		
		
		if("getOne_For_Display".equals(action)) { // �Ӧ�select_page_online_detail.jsp���ШD
			
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
			
			try {
				/***************************1.�����ШD�Ѽ� - ��J�榡�����~�B�z**********************/
				String str = req.getParameter("olno");
				if (str == null || (str.trim()).length() == 0) {
					errorMsgs.add("�п�J�q��s��");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/back-end/online_detail/select_page_online_detail.jsp");
					failureView.forward(req, res);
					return;//�{�����_
				}
				
				Integer olno = null;
				try {
					olno = new Integer(str);
				} catch (Exception e) {
					errorMsgs.add("�q��s���榡�����T");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/back-end/online_detail/select_page_online_detail.jsp");
					failureView.forward(req, res);
					return;//�{�����_
				}
				
//				/***************************2.�}�l�d�߸��*****************************************/
				OnlineDetailService onlineDetailSvc = new OnlineDetailService();
				List<OnlineDetailVO> onlineDetailVO = onlineDetailSvc.findByOlno(olno);
				ServletContext context = getServletContext();
				context.setAttribute("olno", olno);
				if (onlineDetailVO == null) {
					errorMsgs.add("�d�L���");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/back-end/online_detail/select_page_online_detail.jsp");
					failureView.forward(req, res);
					return;//�{�����_
				}
				
				/***************************3.�d�ߧ���,�ǳ����(Send the Success view)*************/
				req.setAttribute("onlineDetailVO", onlineDetailVO); // ��Ʈw���X��empVO����,�s�Jreq
				String url = "/back-end/online_detail/listOneOnlineDetail.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // ���\��� listOneEmp.jsp
				successView.forward(req, res);
				
				/***************************��L�i�઺���~�B�z*************************************/
			} catch (Exception e) {
				errorMsgs.add("�L�k���o���:" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/back-end/online_detail/select_page_online_detail.jsp");
				failureView.forward(req, res);
			}
		}
		if("getOne_For_Display2".equals(action)) { // �Ӧ�select_page_online_detail.jsp���ШD
			
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
			
			try {
				/***************************1.�����ШD�Ѽ� - ��J�榡�����~�B�z**********************/
				String str = req.getParameter("meal_status");
				if (str == null || (str.trim()).length() == 0) {
					errorMsgs.add("�п���\�I���A");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/back-end/online_detail/select_page_online_detail.jsp");
					failureView.forward(req, res);
					return;//�{�����_
				}
				
				Integer meal_status = null;
				try {
					meal_status = new Integer(str);
				} catch (Exception e) {
					errorMsgs.add("�q��s���榡�����T");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/back-end/online_detail/select_page_online_detail.jsp");
					failureView.forward(req, res);
					return;//�{�����_
				}
				
//				/***************************2.�}�l�d�߸��*****************************************/
				OnlineDetailService onlineDetailSvc = new OnlineDetailService();
				List<OnlineDetailVO> onlineDetailVO = onlineDetailSvc.findByOlno(meal_status);
				ServletContext context = getServletContext();
				context.setAttribute("meal_status", meal_status);
				if (onlineDetailVO == null) {
					errorMsgs.add("�d�L���");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/back-end/online_detail/select_page_online_detail.jsp");
					failureView.forward(req, res);
					return;//�{�����_
				}
				
				/***************************3.�d�ߧ���,�ǳ����(Send the Success view)*************/
				req.setAttribute("onlineDetailVO", onlineDetailVO); // ��Ʈw���X��empVO����,�s�Jreq
				String url = "/back-end/online_detail/listOneOnlineDetail2.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // ���\��� listOneEmp.jsp
				successView.forward(req, res);
				
				/***************************��L�i�઺���~�B�z*************************************/
			} catch (Exception e) {
				errorMsgs.add("�L�k���o���:" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/back-end/online_detail/select_page_online_detail.jsp");
				failureView.forward(req, res);
			}
		}
	
	}
}
