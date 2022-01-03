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
		
		
		if ("getOne_For_Display".equals(action)) { // 來自select_page_online_order.jsp的請求

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
							.getRequestDispatcher("/back-end/online_order/select_page_online_order.jsp");
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
							.getRequestDispatcher("/back-end/online_order/select_page_online_order.jsp");
					failureView.forward(req, res);
					return;//程式中斷
				}
				
				/***************************2.開始查詢資料*****************************************/
				OnlineOrderService onlineOrderSvc = new OnlineOrderService();
				OnlineOrderVO onlineOrderVO = onlineOrderSvc.getOneOnlineOrder(olno);
				if (onlineOrderVO == null) {
					errorMsgs.add("查無資料");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/back-end/online_order/select_page_online_order.jsp");
					failureView.forward(req, res);
					return;//程式中斷
				}
				
				/***************************3.查詢完成,準備轉交(Send the Success view)*************/
				req.setAttribute("onlineOrderVO", onlineOrderVO); // 資料庫取出的empVO物件,存入req
				String url = "/back-end/online_order/listOneOnlineOrder.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 成功轉交 listOneEmp.jsp
				successView.forward(req, res);
				
				/***************************其他可能的錯誤處理*************************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得資料:" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/back-end/online_order/select_page_online_order.jsp");
				failureView.forward(req, res);
			}
		}
		
		
		if("getOne_For_Update".equals(action)) { // 來自listAllOnlineOrder.jsp的請求
			
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
			
			try {
				/***************************1.接收請求參數****************************************/
				Integer olno = new Integer(req.getParameter("olno"));
				
				/***************************2.開始查詢資料****************************************/
				OnlineOrderService onlineOrderSvc = new OnlineOrderService();
				OnlineOrderVO onlineOrderVO = onlineOrderSvc.getOneOnlineOrder(olno);
				
				/***************************3.查詢完成,準備轉交(Send the Success view)************/
				req.setAttribute("onlineOrderVO", onlineOrderVO);  // 資料庫取出的onlineOrderVO物件,存入req
				String url = "/back-end/online_order/update_online_order_input.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 成功轉交 update_online_order_input.jsp
				successView.forward(req, res);
				
				/***************************其他可能的錯誤處理**********************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得要修改的資料:" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/back-end/online_order/listAllOnlineOreder.jsp");//1212
				failureView.forward(req, res);
			}
		}
		
		if("delivered".equals(action)) { // 來自listAllOnlineOrder.jsp的請求
			
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
			
			try {
				/***************************1.接收請求參數****************************************/
				Integer olno = new Integer(req.getParameter("olno"));
				
				/***************************2.開始查詢資料****************************************/
				OnlineOrderService onlineOrderSvc = new OnlineOrderService();
				OnlineOrderVO onlineOrderVO = onlineOrderSvc.getOneOnlineOrder(olno);
				onlineOrderVO.setPay_status(2);
				
				onlineOrderVO = onlineOrderSvc.updateOnlineOrder(onlineOrderVO);//把修改後的onlineOrderVO再存進去結果
				
				/***************************3.查詢完成,準備轉交(Send the Success view)************/
				req.setAttribute("onlineOrderVO", onlineOrderVO);  // 資料庫取出的onlineOrderVO物件,存入req
				String url = "/back-end/online_order/delivery.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 成功轉交 update_online_order_input.jsp
				successView.forward(req, res);
				
				/***************************其他可能的錯誤處理**********************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得要修改的資料:" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/back-end/online_order/listAllOnlineOreder.jsp");//1212
				failureView.forward(req, res);
			}
		}
		
		
		if("update".equals(action)) { // 來自update_online_order_input.jsp的請求
			
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
			
			try {
				/***************************1.接收請求參數 - 輸入格式的錯誤處理**********************/
				Integer olno = new Integer(req.getParameter("olno").trim());
				
				Integer empno = null;
				try {
					empno = new Integer(req.getParameter("empno").trim());
				} catch(NumberFormatException e) {
					empno = 0;
					errorMsgs.add("請填員工編號.");
				}
				
				Integer memno = null;
				try {
					memno = new Integer(req.getParameter("memno").trim());
				} catch(NumberFormatException e) {
					memno = 0;
					errorMsgs.add("請填會員編號.");
				}
				
				Integer pay_status = null;
				try {
					pay_status = new Integer(req.getParameter("pay_status").trim());
				} catch (NumberFormatException e) {
					pay_status = 0;
					errorMsgs.add("結帳狀態請填代號.");
				}
				
				String address = req.getParameter("address");
				String addressReg = "^[(\\u4e00-\\u9fa5)(a-zA-Z0-9_)]{0,50}$";
				if(!address.trim().matches(addressReg)) { //允許空值，空值表示外帶訂單
					errorMsgs.add("地址只能是中、英文字母、數字和_，且長度必須在0到50之間");
				}
				
				java.sql.Timestamp set_time = null;
				try {
					set_time = java.sql.Timestamp.valueOf(req.getParameter("set_time").trim());
				} catch (IllegalArgumentException e) {
					set_time = new java.sql.Timestamp(System.currentTimeMillis());
					errorMsgs.add("請輸入取餐時間");
				}
				
				java.sql.Timestamp create_time = null;
					create_time = java.sql.Timestamp.valueOf(java.time.LocalDateTime.now());//修改時當下時間
				
				
				Integer total = null;
				try {
					total = new Integer(req.getParameter("total").trim());
				} catch(NumberFormatException e) {
					total = 0;
					errorMsgs.add("訂單請填金額.");
				}
				
				Integer pay_way = null;
				try {
					pay_way = new Integer(req.getParameter("pay_way").trim());
				} catch(NumberFormatException e) {
					pay_way = 0;
					errorMsgs.add("請填付款方式.");
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
					req.setAttribute("onlineOrderVO", onlineOrderVO); // 含有輸入格式錯誤的onlineOrderVO物件,也存入req
					RequestDispatcher failureView = req
							.getRequestDispatcher("/back-end/online_order/update_online_order_input.jsp");
					failureView.forward(req, res);
					return; //程式中斷
				}
				
				/***************************2.開始修改資料*****************************************/
				OnlineOrderService onlineOrderSvc = new OnlineOrderService();
				onlineOrderVO = onlineOrderSvc.updateOnlineOrder(olno, empno, memno, pay_status, address, set_time, create_time, total, pay_way);
				
				/***************************3.修改完成,準備轉交(Send the Success view)*************/
				req.setAttribute("onlineOrderVO", onlineOrderVO);// 資料庫update成功後,正確的的empVO物件,存入req
				String url = "/back-end/online_order/listOneOnlineOrder.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 修改成功後,轉交listOneEmp.jsp
				successView.forward(req, res);
				
				/***************************其他可能的錯誤處理*************************************/
			} catch(Exception e) {
				errorMsgs.add("修改資料失敗:"+e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/back-end/online_order/update_online_order_input.jsp");
				failureView.forward(req, res);
			}
		}
		
//		if("insert".equals(action)) {// 來自addMealType.jsp的請求
////			System.out.print(0);看有沒有抓到值
//			List<String> errorMsgs = new LinkedList<String>();
//			// Store this set in the request scope, in case we need to
//			// send the ErrorPage view.
//			req.setAttribute("errorMsgs", errorMsgs);
//			
//			try {
//				/***********************1.接收請求參數 - 輸入格式的錯誤處理*************************/
////				System.out.print(1);
//				
////				Integer olno = new Integer(req.getParameter("olno").trim());//訂單編號會自動產生，因此不用寫
//				
//				Integer empno = null;
//				try {
//					empno = new Integer(req.getParameter("empno").trim());
//				} catch(NumberFormatException e) {
//					empno = 0;
//					errorMsgs.add("請填員工編號.");
//				}
//				
//				Integer memno = null;
//				try {
//					memno = new Integer(req.getParameter("memno").trim());
//				} catch(NumberFormatException e) {
//					memno = 0;
//					errorMsgs.add("請填會員編號.");
//				}
//				
//				Integer pay_status = null;
//				try {
//					pay_status = new Integer(req.getParameter("pay_status").trim());
//				} catch (NumberFormatException e) {
//					pay_status = 0;
//					errorMsgs.add("結帳狀態請填代號.");
//				}
//				
//				String address = req.getParameter("address");
//				String addressReg = "^[(\\u4e00-\\u9fa5)(a-zA-Z0-9_)]{0,50}$";
//				if(!address.trim().matches(addressReg)) { //允許空值，空值表示外帶訂單
////					System.out.print(3);
//					errorMsgs.add("地址只能是中、英文字母、數字和_，且長度必須在5到50之間");
//				}
//				
//				java.sql.Timestamp set_time = null;
//				try {
//					set_time = java.sql.Timestamp.valueOf(req.getParameter("set_time").trim());
//				} catch (IllegalArgumentException e) {
//					set_time = new java.sql.Timestamp(System.currentTimeMillis());
//					errorMsgs.add("請輸入取餐時間");
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
//					errorMsgs.add("訂單請填金額.");
//				}
//				
//				Integer pay_way = null;
//				try {
//					pay_way = new Integer(req.getParameter("pay_way").trim());
//				} catch(NumberFormatException e) {
//					pay_way = 0;
//					errorMsgs.add("請填付款方式.");
//				}
//				
//				
//				
//				OnlineOrderVO onlineOrderVO = new OnlineOrderVO();
////				onlineOrderVO.setOlno(olno); //olno是自動產生的，因此不需要去新增它。
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
//					req.setAttribute("onlineOrderVO", onlineOrderVO); // 含有輸入格式錯誤的onlineOrderVO物件,也存入req
//					RequestDispatcher failureView = req
//							.getRequestDispatcher("/back-end/online_order/addOnlineOrder.jsp");
//					failureView.forward(req, res);
//					return; //程式中斷
//				}
//				
//				/***************************2.開始新增資料*****************************************/
//				OnlineOrderService onlineOrderSvc = new OnlineOrderService();
//				onlineOrderVO = onlineOrderSvc.addOrderWithDetail(empno, memno, pay_status, address, set_time, create_time, total, pay_way, buylist);
//				/***************************3.新增完成,準備轉交(Send the Success view)***********/
//				String url = "/back-end/online_order/listAllOnlineOrder.jsp";
//				RequestDispatcher successView = req.getRequestDispatcher(url); // 新增成功後轉交listAllOnlineOrder.jsp
//				successView.forward(req, res);
//				
//			} catch(Exception e) {
//				errorMsgs.add(e.getMessage());
//				RequestDispatcher failureView = req
//						.getRequestDispatcher("/back-end/online_order/addOnlineOrder.jsp");
//				failureView.forward(req, res);
//			}
//		}
		
		if("delete".equals(action)) {// 來自listAllOnlineOrder.jsp
			
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
			
			try {
				/***************************1.接收請求參數***************************************/
				Integer olno = new Integer(req.getParameter("olno").trim());
				
				/***************************2.開始刪除資料***************************************/
				OnlineOrderService onlineOrderSvc = new OnlineOrderService();
				onlineOrderSvc.deleteOnlineOrder(olno);
				
				/***************************3.刪除完成,準備轉交(Send the Success view)***********/
				String url = "/back-end/online_order/listAllOnlineOrder.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);// 刪除成功後,轉交回送出刪除的來源網頁
				successView.forward(req, res);
				
				/***************************其他可能的錯誤處理**********************************/
			} catch(Exception e) {
				errorMsgs.add("刪除資料失敗:"+e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/back-end/online_order/listAllOnlineOrder.jsp");
				failureView.forward(req, res);
			}
		}
		
	}

}
