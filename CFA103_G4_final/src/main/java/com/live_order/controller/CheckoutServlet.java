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
		
		if ("liveOrder_Checkout".equals(action)) { // 來自checkout.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
			
			try {
			/***************************1.接收請求參數 - 輸入格式的錯誤處理**********************/
			
			LiveOrderService liveorderSvc = new LiveOrderService();
			Integer liveno = new Integer(req.getParameter("liveno").trim());
			LiveOrderVO liveorderVO = liveorderSvc.getOneByLiveNO(liveno);
			Integer pay_way = null;
			try {
				pay_way = new Integer(req.getParameter("pay_way").trim());
			} catch (NumberFormatException e) {
				pay_way = 0;
				errorMsgs.add("請確認付款方式.");
			}
			liveorderVO.setPay_way(pay_way);
			
			// Send the use back to the form, if there were errors
			if (!errorMsgs.isEmpty()) {
				req.setAttribute("liveorderVO", liveorderVO); // 含有輸入格式錯誤的livedetailVO物件,也存入req
				RequestDispatcher failureView = req
						.getRequestDispatcher("/back-end/checkout/checkout.jsp");
				failureView.forward(req, res);
				return; //程式中斷
			}
			
			/***************************2.開始修改資料*****************************************/
			
			liveorderVO = liveorderSvc.updateOrder(liveorderVO);
			
			/***************************3.修改完成,準備轉交(Send the Success view)*************/
			
			req.setAttribute("liveorderVO", liveorderVO); // 資料庫update成功後,正確的的livedetailVO物件,存入req
			String url1 = "/back-end/checkout/payByCash.jsp";
			String url2 = "/back-end/checkout/payByCard.jsp";
			if (liveorderVO.getPay_way() == 0) {
				RequestDispatcher successView = req.getRequestDispatcher(url1); // 修改成功後,轉交listOneEmp.jsp	
				successView.forward(req, res);
			} else {
				RequestDispatcher successView = req.getRequestDispatcher(url2); // 修改成功後,轉交listOneEmp.jsp	
				successView.forward(req, res);}
			
			/***************************其他可能的錯誤處理*************************************/
			} catch (Exception e) {
				errorMsgs.add("修改資料失敗:"+e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/back-end/checkout/checkout.jsp");
				failureView.forward(req, res);
			}
		}
		
		if ("onlineOrder_Checkout".equals(action)) { // 來自checkout.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
			
			try {
				/***************************1.接收請求參數 - 輸入格式的錯誤處理**********************/
				OnlineOrderService onlineorderSvc = new OnlineOrderService();
				Integer olno = new Integer(req.getParameter("olno").trim());
				
				OnlineOrderVO onlineorderVO = onlineorderSvc.getOneOnlineOrder(olno);
				/***************************2.開始查詢資料*****************************************/
				if (onlineorderVO == null) {
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
				req.setAttribute("onlineorderVO", onlineorderVO); // 資料庫取出的empVO物件,存入req
				String url = "/back-end/checkout/payByCash.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 成功轉交 listOneEmp.jsp
				successView.forward(req, res);
				
				/***************************其他可能的錯誤處理*************************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得資料:" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/back-end/checkout/checkout.jsp");
				failureView.forward(req, res);
			}
		}
		
		if ("payByCash".equals(action)) { // 來自checkout.jsp的請求
			
			List<String> errorMsgs = new LinkedList<String>();
			String order=req.getParameter("order");
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
			
			try {
		if (order.equals("live")) {
			/***************************1.接收請求參數 - 輸入格式的錯誤處理**********************/
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
				req.setAttribute("liveorderVO", liveorderVO); // 含有輸入格式錯誤的livedetailVO物件,也存入req
				RequestDispatcher failureView = req
						.getRequestDispatcher("/back-end/checkout/checkout.jsp");
				failureView.forward(req, res);
				return; //程式中斷
			}
			
			/***************************2.開始修改資料*****************************************/
			
			liveorderVO = liveorderSvc.updateOrder(liveorderVO);
			tableVO = tableSvc.updateTable(tableVO);
			
			/***************************3.修改完成,準備轉交(Send the Success view)*************/
			
			req.setAttribute("liveorderVO", liveorderVO); // 資料庫update成功後,正確的的livedetailVO物件,存入req
			String url = "/back-end/checkout/checkout.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(url); // 修改成功後,轉交listOneEmp.jsp	
			successView.forward(req, res);
		}
		if (order.equals("online")) {
			/***************************1.接收請求參數 - 輸入格式的錯誤處理**********************/
			OnlineOrderService onlineorderSvc = new OnlineOrderService();
			Integer olno = new Integer(req.getParameter("olno").trim());
			OnlineOrderVO onlineorderVO = onlineorderSvc.getOneOnlineOrder(olno);
			onlineorderVO.setPay_status(1);
			
			// Send the use back to the form, if there were errors
			if (!errorMsgs.isEmpty()) {
				req.setAttribute("onlineorderVO", onlineorderVO); // 含有輸入格式錯誤的livedetailVO物件,也存入req
				RequestDispatcher failureView = req
						.getRequestDispatcher("/back-end/checkout/checkout.jsp");
				failureView.forward(req, res);
				return; //程式中斷
			}
			
			/***************************2.開始修改資料*****************************************/
			
			onlineorderVO = onlineorderSvc.updateOnlineOrder(onlineorderVO);
			
			/***************************3.修改完成,準備轉交(Send the Success view)*************/
			
			req.setAttribute("onlineorderVO", onlineorderVO); // 資料庫update成功後,正確的的livedetailVO物件,存入req
			String url = "/back-end/checkout/checkout.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(url); // 修改成功後,轉交listOneEmp.jsp	
			successView.forward(req, res);
		}
			/***************************其他可能的錯誤處理*************************************/
			} catch (Exception e) {
				errorMsgs.add("修改資料失敗:"+e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/back-end/checkout/checkout.jsp");
				failureView.forward(req, res);
			}
			
		}

		if ("payByCard".equals(action)) { // 來自checkout.jsp的請求
			
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
			
			try {
				/***************************1.接收請求參數 - 輸入格式的錯誤處理**********************/
				
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
					req.setAttribute("liveorderVO", liveorderVO); // 含有輸入格式錯誤的livedetailVO物件,也存入req
					RequestDispatcher failureView = req
							.getRequestDispatcher("/back-end/checkout/checkout.jsp");
					failureView.forward(req, res);
					return; //程式中斷
				}
				
				/***************************2.開始修改資料*****************************************/
				
				liveorderVO = liveorderSvc.updateOrder(liveorderVO);
				tableVO = tableSvc.updateTable(tableVO);
				
				/***************************3.修改完成,準備轉交(Send the Success view)*************/
				
				req.setAttribute("liveorderVO", liveorderVO); // 資料庫update成功後,正確的的livedetailVO物件,存入req
				String url = "/back-end/checkout/checkout.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 修改成功後,轉交listOneEmp.jsp	
				successView.forward(req, res);
				
				/***************************其他可能的錯誤處理*************************************/
			} catch (Exception e) {
				errorMsgs.add("修改資料失敗:"+e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/back-end/checkout/checkout.jsp");
				failureView.forward(req, res);
			}
		}
	}
}
