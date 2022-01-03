package com.online_order.controller;

import java.io.*;
import java.util.*;

import javax.servlet.*;
import javax.servlet.http.*;
import com.online_detail.model.*;
import com.online_order.model.*;

public class OnlineOrderServlet extends HttpServlet {
       
	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		doPost(req, res);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		
		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");
		
		
		if ("getOne_For_Display".equals(action)) { // �Ӧ�select_page_online_order.jsp���ШD

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
							.getRequestDispatcher("/back-end/online_order/select_page_online_order.jsp");
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
							.getRequestDispatcher("/back-end/online_order/select_page_online_order.jsp");
					failureView.forward(req, res);
					return;//�{�����_
				}
				
				/***************************2.�}�l�d�߸��*****************************************/
				OnlineOrderService onlineOrderSvc = new OnlineOrderService();
				OnlineOrderVO onlineOrderVO = onlineOrderSvc.getOneOnlineOrder(olno);
				if (onlineOrderVO == null) {
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
				req.setAttribute("onlineOrderVO", onlineOrderVO); // ��Ʈw���X��empVO����,�s�Jreq
				String url = "/back-end/online_order/listOneOnlineOrder.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // ���\��� listOneEmp.jsp
				successView.forward(req, res);
				
				/***************************��L�i�઺���~�B�z*************************************/
			} catch (Exception e) {
				errorMsgs.add("�L�k���o���:" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/back-end/online_order/select_page_online_order.jsp");
				failureView.forward(req, res);
			}
		}
		
		
		if("getOne_For_Update".equals(action)) { // �Ӧ�listAllOnlineOrder.jsp���ШD
			
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
			
			try {
				/***************************1.�����ШD�Ѽ�****************************************/
				Integer olno = new Integer(req.getParameter("olno"));
				
				/***************************2.�}�l�d�߸��****************************************/
				OnlineOrderService onlineOrderSvc = new OnlineOrderService();
				OnlineOrderVO onlineOrderVO = onlineOrderSvc.getOneOnlineOrder(olno);
				
				/***************************3.�d�ߧ���,�ǳ����(Send the Success view)************/
				req.setAttribute("onlineOrderVO", onlineOrderVO);  // ��Ʈw���X��onlineOrderVO����,�s�Jreq
				String url = "/back-end/online_order/update_online_order_input.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // ���\��� update_online_order_input.jsp
				successView.forward(req, res);
				
				/***************************��L�i�઺���~�B�z**********************************/
			} catch (Exception e) {
				errorMsgs.add("�L�k���o�n�ק諸���:" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/back-end/online_order/listAllOnlineOreder.jsp");//1212
				failureView.forward(req, res);
			}
		}
		
		if("delivered".equals(action)) { // �Ӧ�listAllOnlineOrder.jsp���ШD
			
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
			
			try {
				/***************************1.�����ШD�Ѽ�****************************************/
				Integer olno = new Integer(req.getParameter("olno"));
				
				/***************************2.�}�l�d�߸��****************************************/
				OnlineOrderService onlineOrderSvc = new OnlineOrderService();
				OnlineOrderVO onlineOrderVO = onlineOrderSvc.getOneOnlineOrder(olno);
				onlineOrderVO.setPay_status(2);
				
				onlineOrderVO = onlineOrderSvc.updateOnlineOrder(onlineOrderVO);//��ק�᪺onlineOrderVO�A�s�i�h���G
				
				/***************************3.�d�ߧ���,�ǳ����(Send the Success view)************/
				req.setAttribute("onlineOrderVO", onlineOrderVO);  // ��Ʈw���X��onlineOrderVO����,�s�Jreq
				String url = "/back-end/online_order/delivery.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // ���\��� update_online_order_input.jsp
				successView.forward(req, res);
				
				/***************************��L�i�઺���~�B�z**********************************/
			} catch (Exception e) {
				errorMsgs.add("�L�k���o�n�ק諸���:" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/back-end/online_order/listAllOnlineOreder.jsp");//1212
				failureView.forward(req, res);
			}
		}
		
		
		if("update".equals(action)) { // �Ӧ�update_online_order_input.jsp���ШD
			
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
			
			try {
				/***************************1.�����ШD�Ѽ� - ��J�榡�����~�B�z**********************/
				Integer olno = new Integer(req.getParameter("olno").trim());
				
				Integer empno = null;
				try {
					empno = new Integer(req.getParameter("empno").trim());
				} catch(NumberFormatException e) {
					empno = 0;
					errorMsgs.add("�ж���u�s��.");
				}
				
				Integer memno = null;
				try {
					memno = new Integer(req.getParameter("memno").trim());
				} catch(NumberFormatException e) {
					memno = 0;
					errorMsgs.add("�ж�|���s��.");
				}
				
				Integer pay_status = null;
				try {
					pay_status = new Integer(req.getParameter("pay_status").trim());
				} catch (NumberFormatException e) {
					pay_status = 0;
					errorMsgs.add("���b���A�ж�N��.");
				}
				
				String address = req.getParameter("address");
				String addressReg = "^[(\\u4e00-\\u9fa5)(a-zA-Z0-9_)]{0,50}$";
				if(!address.trim().matches(addressReg)) { //���\�ŭȡA�ŭȪ�ܥ~�a�q��
					errorMsgs.add("�a�}�u��O���B�^��r���B�Ʀr�M_�A�B���ץ����b0��50����");
				}
				
				java.sql.Timestamp set_time = null;
				try {
					set_time = java.sql.Timestamp.valueOf(req.getParameter("set_time").trim());
				} catch (IllegalArgumentException e) {
					set_time = new java.sql.Timestamp(System.currentTimeMillis());
					errorMsgs.add("�п�J���\�ɶ�");
				}
				
				java.sql.Timestamp create_time = null;
					create_time = java.sql.Timestamp.valueOf(java.time.LocalDateTime.now());//�ק�ɷ�U�ɶ�
				
				
				Integer total = null;
				try {
					total = new Integer(req.getParameter("total").trim());
				} catch(NumberFormatException e) {
					total = 0;
					errorMsgs.add("�q��ж���B.");
				}
				
				Integer pay_way = null;
				try {
					pay_way = new Integer(req.getParameter("pay_way").trim());
				} catch(NumberFormatException e) {
					pay_way = 0;
					errorMsgs.add("�ж�I�ڤ覡.");
				}
				
				OnlineOrderVO onlineOrderVO = new OnlineOrderVO();
				onlineOrderVO.setOlno(olno);
				onlineOrderVO.setEmpno(empno);
				onlineOrderVO.setMemno(memno);
				onlineOrderVO.setPay_status(pay_status);
				onlineOrderVO.setAddress(address);
				onlineOrderVO.setSet_time(set_time);
				onlineOrderVO.setCreate_time(create_time);
				onlineOrderVO.setTotal(total);
				onlineOrderVO.setPay_way(pay_way);
				
				
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("onlineOrderVO", onlineOrderVO); // �t����J�榡���~��onlineOrderVO����,�]�s�Jreq
					RequestDispatcher failureView = req
							.getRequestDispatcher("/back-end/online_order/update_online_order_input.jsp");
					failureView.forward(req, res);
					return; //�{�����_
				}
				
				/***************************2.�}�l�ק���*****************************************/
				OnlineOrderService onlineOrderSvc = new OnlineOrderService();
				onlineOrderVO = onlineOrderSvc.updateOnlineOrder(olno, empno, memno, pay_status, address, set_time, create_time, total, pay_way);
				
				/***************************3.�ק粒��,�ǳ����(Send the Success view)*************/
				req.setAttribute("onlineOrderVO", onlineOrderVO);// ��Ʈwupdate���\��,���T����empVO����,�s�Jreq
				String url = "/back-end/online_order/listOneOnlineOrder.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // �ק令�\��,���listOneEmp.jsp
				successView.forward(req, res);
				
				/***************************��L�i�઺���~�B�z*************************************/
			} catch(Exception e) {
				errorMsgs.add("�ק��ƥ���:"+e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/back-end/online_order/update_online_order_input.jsp");
				failureView.forward(req, res);
			}
		}
		
//		if("insert".equals(action)) {// �Ӧ�addMealType.jsp���ШD
////			System.out.print(0);�ݦ��S������
//			List<String> errorMsgs = new LinkedList<String>();
//			// Store this set in the request scope, in case we need to
//			// send the ErrorPage view.
//			req.setAttribute("errorMsgs", errorMsgs);
//			
//			try {
//				/***********************1.�����ШD�Ѽ� - ��J�榡�����~�B�z*************************/
////				System.out.print(1);
//				
////				Integer olno = new Integer(req.getParameter("olno").trim());//�q��s���|�۰ʲ��͡A�]�����μg
//				
//				Integer empno = null;
//				try {
//					empno = new Integer(req.getParameter("empno").trim());
//				} catch(NumberFormatException e) {
//					empno = 0;
//					errorMsgs.add("�ж���u�s��.");
//				}
//				
//				Integer memno = null;
//				try {
//					memno = new Integer(req.getParameter("memno").trim());
//				} catch(NumberFormatException e) {
//					memno = 0;
//					errorMsgs.add("�ж�|���s��.");
//				}
//				
//				Integer pay_status = null;
//				try {
//					pay_status = new Integer(req.getParameter("pay_status").trim());
//				} catch (NumberFormatException e) {
//					pay_status = 0;
//					errorMsgs.add("���b���A�ж�N��.");
//				}
//				
//				String address = req.getParameter("address");
//				String addressReg = "^[(\\u4e00-\\u9fa5)(a-zA-Z0-9_)]{0,50}$";
//				if(!address.trim().matches(addressReg)) { //���\�ŭȡA�ŭȪ�ܥ~�a�q��
////					System.out.print(3);
//					errorMsgs.add("�a�}�u��O���B�^��r���B�Ʀr�M_�A�B���ץ����b5��50����");
//				}
//				
//				java.sql.Timestamp set_time = null;
//				try {
//					set_time = java.sql.Timestamp.valueOf(req.getParameter("set_time").trim());
//				} catch (IllegalArgumentException e) {
//					set_time = new java.sql.Timestamp(System.currentTimeMillis());
//					errorMsgs.add("�п�J���\�ɶ�");
//				}
//				
//				java.sql.Timestamp create_time = null;
//				create_time = java.sql.Timestamp.valueOf(java.time.LocalDateTime.now());
//				
//				Integer total = null;
//				try {
//					total = new Integer(req.getParameter("total").trim());
//				} catch(NumberFormatException e) {
//					total = 0;
//					errorMsgs.add("�q��ж���B.");
//				}
//				
//				Integer pay_way = null;
//				try {
//					pay_way = new Integer(req.getParameter("pay_way").trim());
//				} catch(NumberFormatException e) {
//					pay_way = 0;
//					errorMsgs.add("�ж�I�ڤ覡.");
//				}
//				
//				
//				
//				OnlineOrderVO onlineOrderVO = new OnlineOrderVO();
////				onlineOrderVO.setOlno(olno); //olno�O�۰ʲ��ͪ��A�]�����ݭn�h�s�W���C
//				onlineOrderVO.setEmpno(empno);
//				onlineOrderVO.setMemno(memno);
//				onlineOrderVO.setPay_status(pay_status);
//				onlineOrderVO.setAddress(address);
//				onlineOrderVO.setSet_time(set_time);
//				onlineOrderVO.setCreate_time(create_time);
//				onlineOrderVO.setTotal(total);
//				onlineOrderVO.setPay_way(pay_way);
//				
//				// Send the use back to the form, if there were errors
//				if (!errorMsgs.isEmpty()) {
//					req.setAttribute("onlineOrderVO", onlineOrderVO); // �t����J�榡���~��onlineOrderVO����,�]�s�Jreq
//					RequestDispatcher failureView = req
//							.getRequestDispatcher("/back-end/online_order/addOnlineOrder.jsp");
//					failureView.forward(req, res);
//					return; //�{�����_
//				}
//				
//				/***************************2.�}�l�s�W���*****************************************/
//				OnlineOrderService onlineOrderSvc = new OnlineOrderService();
//				onlineOrderVO = onlineOrderSvc.addOrderWithDetail(empno, memno, pay_status, address, set_time, create_time, total, pay_way, buylist);
//				/***************************3.�s�W����,�ǳ����(Send the Success view)***********/
//				String url = "/back-end/online_order/listAllOnlineOrder.jsp";
//				RequestDispatcher successView = req.getRequestDispatcher(url); // �s�W���\�����listAllOnlineOrder.jsp
//				successView.forward(req, res);
//				
//			} catch(Exception e) {
//				errorMsgs.add(e.getMessage());
//				RequestDispatcher failureView = req
//						.getRequestDispatcher("/back-end/online_order/addOnlineOrder.jsp");
//				failureView.forward(req, res);
//			}
//		}
		
		if("delete".equals(action)) {// �Ӧ�listAllOnlineOrder.jsp
			
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
			
			try {
				/***************************1.�����ШD�Ѽ�***************************************/
				Integer olno = new Integer(req.getParameter("olno").trim());
				
				/***************************2.�}�l�R�����***************************************/
				OnlineOrderService onlineOrderSvc = new OnlineOrderService();
				onlineOrderSvc.deleteOnlineOrder(olno);
				
				/***************************3.�R������,�ǳ����(Send the Success view)***********/
				String url = "/back-end/online_order/listAllOnlineOrder.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);// �R�����\��,���^�e�X�R�����ӷ�����
				successView.forward(req, res);
				
				/***************************��L�i�઺���~�B�z**********************************/
			} catch(Exception e) {
				errorMsgs.add("�R����ƥ���:"+e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/back-end/online_order/listAllOnlineOrder.jsp");
				failureView.forward(req, res);
			}
		}
		
	}

}
