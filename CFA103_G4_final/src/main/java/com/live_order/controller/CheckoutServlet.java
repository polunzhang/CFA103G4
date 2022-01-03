package com.live_order.controller;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.live_order.model.LiveOrderService;
import com.live_order.model.LiveOrderVO;
import com.online_order.model.OnlineOrderService;
import com.online_order.model.OnlineOrderVO;
import com.table.model.TableService;
import com.table.model.TableVO;

@WebServlet("/Checkout")
public class CheckoutServlet extends HttpServlet {

	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		
		req.setCharacterEncoding("UTF-8");
		HttpSession session = req.getSession();
		String action = req.getParameter("action");
		
		if ("liveOrder_Checkout".equals(action)) { // �Ӧ�checkout.jsp���ШD

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
			
			try {
			/***************************1.�����ШD�Ѽ� - ��J�榡�����~�B�z**********************/
			
			LiveOrderService liveorderSvc = new LiveOrderService();
			Integer liveno = new Integer(req.getParameter("liveno").trim());
			LiveOrderVO liveorderVO = liveorderSvc.getOneByLiveNO(liveno);
			Integer pay_way = null;
			try {
				pay_way = new Integer(req.getParameter("pay_way").trim());
			} catch (NumberFormatException e) {
				pay_way = 0;
				errorMsgs.add("�нT�{�I�ڤ覡.");
			}
			liveorderVO.setPay_way(pay_way);
			
			// Send the use back to the form, if there were errors
			if (!errorMsgs.isEmpty()) {
				req.setAttribute("liveorderVO", liveorderVO); // �t����J�榡���~��livedetailVO����,�]�s�Jreq
				RequestDispatcher failureView = req
						.getRequestDispatcher("/back-end/checkout/checkout.jsp");
				failureView.forward(req, res);
				return; //�{�����_
			}
			
			/***************************2.�}�l�ק���*****************************************/
			
			liveorderVO = liveorderSvc.updateOrder(liveorderVO);
			
			/***************************3.�ק粒��,�ǳ����(Send the Success view)*************/
			
			req.setAttribute("liveorderVO", liveorderVO); // ��Ʈwupdate���\��,���T����livedetailVO����,�s�Jreq
			String url1 = "/back-end/checkout/payByCash.jsp";
			String url2 = "/back-end/checkout/payByCard.jsp";
			if (liveorderVO.getPay_way() == 0) {
				RequestDispatcher successView = req.getRequestDispatcher(url1); // �ק令�\��,���listOneEmp.jsp	
				successView.forward(req, res);
			} else {
				RequestDispatcher successView = req.getRequestDispatcher(url2); // �ק令�\��,���listOneEmp.jsp	
				successView.forward(req, res);}
			
			/***************************��L�i�઺���~�B�z*************************************/
			} catch (Exception e) {
				errorMsgs.add("�ק��ƥ���:"+e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/back-end/checkout/checkout.jsp");
				failureView.forward(req, res);
			}
		}
		
		if ("onlineOrder_Checkout".equals(action)) { // �Ӧ�checkout.jsp���ШD

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
			
			try {
				/***************************1.�����ШD�Ѽ� - ��J�榡�����~�B�z**********************/
				OnlineOrderService onlineorderSvc = new OnlineOrderService();
				Integer olno = new Integer(req.getParameter("olno").trim());
				
				OnlineOrderVO onlineorderVO = onlineorderSvc.getOneOnlineOrder(olno);
				/***************************2.�}�l�d�߸��*****************************************/
				if (onlineorderVO == null) {
					errorMsgs.add("�d�L���");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/back-end/online_order/select_page_online_order.jsp");
					failureView.forward(req, res);
					return;//�{�����_
				}
				
				/***************************3.�d�ߧ���,�ǳ����(Send the Success view)*************/
				req.setAttribute("onlineorderVO", onlineorderVO); // ��Ʈw���X��empVO����,�s�Jreq
				String url = "/back-end/checkout/payByCash.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // ���\��� listOneEmp.jsp
				successView.forward(req, res);
				
				/***************************��L�i�઺���~�B�z*************************************/
			} catch (Exception e) {
				errorMsgs.add("�L�k���o���:" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/back-end/checkout/checkout.jsp");
				failureView.forward(req, res);
			}
		}
		
		if ("payByCash".equals(action)) { // �Ӧ�checkout.jsp���ШD
			
			List<String> errorMsgs = new LinkedList<String>();
			String order=req.getParameter("order");
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
			
			try {
		if (order.equals("live")) {
			/***************************1.�����ШD�Ѽ� - ��J�榡�����~�B�z**********************/
			LiveOrderService liveorderSvc = new LiveOrderService();
			Integer liveno = new Integer(req.getParameter("liveno").trim());
			LiveOrderVO liveorderVO = liveorderSvc.getOneByLiveNO(liveno);
			liveorderVO.setPay_status(1);
			
			TableService tableSvc = new TableService();
			Integer tableno = new Integer(req.getParameter("tableno").trim());
			TableVO tableVO = tableSvc.getOneTable(tableno);
			tableVO.setTable_status(0);
			
			// Send the use back to the form, if there were errors
			if (!errorMsgs.isEmpty()) {
				req.setAttribute("liveorderVO", liveorderVO); // �t����J�榡���~��livedetailVO����,�]�s�Jreq
				RequestDispatcher failureView = req
						.getRequestDispatcher("/back-end/checkout/checkout.jsp");
				failureView.forward(req, res);
				return; //�{�����_
			}
			
			/***************************2.�}�l�ק���*****************************************/
			
			liveorderVO = liveorderSvc.updateOrder(liveorderVO);
			tableVO = tableSvc.updateTable(tableVO);
			
			/***************************3.�ק粒��,�ǳ����(Send the Success view)*************/
			
			req.setAttribute("liveorderVO", liveorderVO); // ��Ʈwupdate���\��,���T����livedetailVO����,�s�Jreq
			String url = "/back-end/checkout/checkout.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(url); // �ק令�\��,���listOneEmp.jsp	
			successView.forward(req, res);
		}
		if (order.equals("online")) {
			/***************************1.�����ШD�Ѽ� - ��J�榡�����~�B�z**********************/
			OnlineOrderService onlineorderSvc = new OnlineOrderService();
			Integer olno = new Integer(req.getParameter("olno").trim());
			OnlineOrderVO onlineorderVO = onlineorderSvc.getOneOnlineOrder(olno);
			onlineorderVO.setPay_status(1);
			
			// Send the use back to the form, if there were errors
			if (!errorMsgs.isEmpty()) {
				req.setAttribute("onlineorderVO", onlineorderVO); // �t����J�榡���~��livedetailVO����,�]�s�Jreq
				RequestDispatcher failureView = req
						.getRequestDispatcher("/back-end/checkout/checkout.jsp");
				failureView.forward(req, res);
				return; //�{�����_
			}
			
			/***************************2.�}�l�ק���*****************************************/
			
			onlineorderVO = onlineorderSvc.updateOnlineOrder(onlineorderVO);
			
			/***************************3.�ק粒��,�ǳ����(Send the Success view)*************/
			
			req.setAttribute("onlineorderVO", onlineorderVO); // ��Ʈwupdate���\��,���T����livedetailVO����,�s�Jreq
			String url = "/back-end/checkout/checkout.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(url); // �ק令�\��,���listOneEmp.jsp	
			successView.forward(req, res);
		}
			/***************************��L�i�઺���~�B�z*************************************/
			} catch (Exception e) {
				errorMsgs.add("�ק��ƥ���:"+e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/back-end/checkout/checkout.jsp");
				failureView.forward(req, res);
			}
			
		}

		if ("payByCard".equals(action)) { // �Ӧ�checkout.jsp���ШD
			
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
			
			try {
				/***************************1.�����ШD�Ѽ� - ��J�榡�����~�B�z**********************/
				
				LiveOrderService liveorderSvc = new LiveOrderService();
				Integer liveno = new Integer(req.getParameter("liveno").trim());
				LiveOrderVO liveorderVO = liveorderSvc.getOneByLiveNO(liveno);
				liveorderVO.setPay_status(1);
				
				TableService tableSvc = new TableService();
				Integer tableno = new Integer(req.getParameter("tableno").trim());
				TableVO tableVO = tableSvc.getOneTable(tableno);
				tableVO.setTable_status(0);
				
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("liveorderVO", liveorderVO); // �t����J�榡���~��livedetailVO����,�]�s�Jreq
					RequestDispatcher failureView = req
							.getRequestDispatcher("/back-end/checkout/checkout.jsp");
					failureView.forward(req, res);
					return; //�{�����_
				}
				
				/***************************2.�}�l�ק���*****************************************/
				
				liveorderVO = liveorderSvc.updateOrder(liveorderVO);
				tableVO = tableSvc.updateTable(tableVO);
				
				/***************************3.�ק粒��,�ǳ����(Send the Success view)*************/
				
				req.setAttribute("liveorderVO", liveorderVO); // ��Ʈwupdate���\��,���T����livedetailVO����,�s�Jreq
				String url = "/back-end/checkout/checkout.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // �ק令�\��,���listOneEmp.jsp	
				successView.forward(req, res);
				
				/***************************��L�i�઺���~�B�z*************************************/
			} catch (Exception e) {
				errorMsgs.add("�ק��ƥ���:"+e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/back-end/checkout/checkout.jsp");
				failureView.forward(req, res);
			}
		}
	}
}
