package com.table.controller;

import java.io.*;
import java.util.*;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import org.json.JSONArray;
import org.json.JSONObject;

import com.table.model.*;

@WebServlet("/Table")
public class TableServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");

		if ("getOne_For_Display".equals(action)) {

			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			try {

				String str = req.getParameter("tableno");
				if (str == null || (str.trim()).length() == 0) {
					errorMsgs.add("請輸入桌號");
				}

				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/back-end/table/select_page_table.jsp");
					failureView.forward(req, res);
					return;
				}

				Integer tableno = null;
				try {
					tableno = new Integer(str);
				} catch (Exception e) {
					errorMsgs.add("桌號格式不正確");
				}

				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/back-end/table/select_page_table.jsp");
					failureView.forward(req, res);
					return;
				}

				TableService tableSvc = new TableService();
				TableVO tableVO = tableSvc.getOneTable(tableno);

				if (tableVO == null) {
					errorMsgs.add("查無資料");
				}

				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/back-end/table/select_page_table.jsp");
					failureView.forward(req, res);
					return;
				}

				req.setAttribute("tableVO", tableVO);
				String url = "/back-end/table/listOneTable.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);

			} catch (Exception e) {
				errorMsgs.add("無法取得資料:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/table/select_page_table.jsp");
				failureView.forward(req, res);
			}

		}
		if ("findByTableNop".equals(action)) {

			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

//	try {

			String str = req.getParameter("table_nop");
			if (str == null || (str.trim()).length() == 0) {
				errorMsgs.add("請輸入桌位人數");
			}

			if (!errorMsgs.isEmpty()) {
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/table/select_page_table.jsp");
				failureView.forward(req, res);
				return;
			}

			Integer table_nop = null;
			try {
				table_nop = new Integer(str);
			} catch (Exception e) {
				errorMsgs.add("桌位人數格式不正確");
			}

			if (!errorMsgs.isEmpty()) {
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/table/select_page_table.jsp");
				failureView.forward(req, res);
				return;
			}

			TableService tableSvc = new TableService();
			List<TableVO> tableVO = tableSvc.findByTableStatus(table_nop);
			ServletContext context = getServletContext();
			context.setAttribute("table_nop", table_nop);
			if (tableVO == null) {
				errorMsgs.add("查無資料");
			}

			if (!errorMsgs.isEmpty()) {
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/table/select_page_table.jsp");
				failureView.forward(req, res);
				return;
			}

			req.setAttribute("tableVO", tableVO);
			String url = "/back-end/table/listTableNop.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(url);
			successView.forward(req, res);

//	} catch (Exception e) {
//		errorMsgs.add("無法取得資料:" + e.getMessage());
//		RequestDispatcher failureView = req.getRequestDispatcher("/back-end/table/select_page_table.jsp");
//		failureView.forward(req, res);
//	}

		}
		if ("findByTableStatus".equals(action)) {

			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

//	try {

			String str = req.getParameter("table_status");
			if (str == null || (str.trim()).length() == 0) {
				errorMsgs.add("請輸入桌位狀態");
			}

			if (!errorMsgs.isEmpty()) {
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/table/select_page_table.jsp");
				failureView.forward(req, res);
				return;
			}

			Integer table_status = null;
			try {
				table_status = new Integer(str);
			} catch (Exception e) {
				errorMsgs.add("桌位狀態格式不正確");
			}

			if (!errorMsgs.isEmpty()) {
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/table/select_page_table.jsp");
				failureView.forward(req, res);
				return;
			}

			TableService tableSvc = new TableService();
			List<TableVO> tableVO = tableSvc.findByTableStatus(table_status);
			ServletContext context = getServletContext();
			context.setAttribute("table_status", table_status);
			if (tableVO == null) {
				errorMsgs.add("查無資料");
			}

			if (!errorMsgs.isEmpty()) {
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/table/select_page_table.jsp");
				failureView.forward(req, res);
				return;
			}

			req.setAttribute("tableVO", tableVO);
			String url = "/back-end/table/listTableStatus.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(url);
			successView.forward(req, res);

//	} catch (Exception e) {
//		errorMsgs.add("無法取得資料:" + e.getMessage());
//		RequestDispatcher failureView = req.getRequestDispatcher("/back-end/table/select_page_table.jsp");
//		failureView.forward(req, res);
//	}

		}

		if ("getOne_For_Update".equals(action)) {

			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			try {
				Integer tableno = new Integer(req.getParameter("tableno"));

				TableService tableSvc = new TableService();
				TableVO tableVO = tableSvc.getOneTable(tableno);

				req.setAttribute("tableVO", tableVO);
				String url = "/back-end/table/update_table_input.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);
			} catch (Exception e) {
				errorMsgs.add("無法取得要修改的資料:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/table/listAllEmp.jsp");
				failureView.forward(req, res);
			}
		}
		
		if ("take_A_Seat".equals(action)) {
			
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			try {
				Integer tableno = new Integer(req.getParameter("tableno"));
				
				TableService tableSvc = new TableService();
				TableVO tableVO = tableSvc.getOneTable(tableno);
				tableVO.setTable_status(2);
				
				tableVO = tableSvc.updateTable(tableVO);
				
				req.setAttribute("tableVO", tableVO);
				String url = "/back-end/table/tables.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);
			} catch (Exception e) {
				errorMsgs.add("無法取得要修改的資料:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/table/tables.jsp");
				failureView.forward(req, res);
			}
		}

		if ("update".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				Integer tableno = new Integer(req.getParameter("tableno").trim());

				Integer table_nop = null;
				try {
					table_nop = new Integer(req.getParameter("table_nop").trim());

				} catch (NumberFormatException e) {
					table_nop = 0;
					errorMsgs.add("桌位人數請填數字");
				}

				Integer table_status = null;
				try {
					table_status = new Integer(req.getParameter("table_status").trim());

				} catch (NumberFormatException e) {
					table_nop = 0;
					errorMsgs.add("桌位人數請填數字");
				}

				TableVO tableVO = new TableVO();
				tableVO.setTableno(tableno);
				tableVO.setTable_nop(table_nop);
				tableVO.setTable_status(table_status);
				tableVO.setTable_left(0);
				tableVO.setTable_top(0);

				if (!errorMsgs.isEmpty()) {
					req.setAttribute("tableVO", tableVO); // 含有輸入格式錯誤的empVO物件,也存入req
					RequestDispatcher failureView = req.getRequestDispatcher("/back-end/table/update_table_input.jsp");
					failureView.forward(req, res);
					return; // 程式中斷
				}
				// 開始修改資料

				TableService tableSvc = new TableService();
				tableVO = tableSvc.updateTable(tableVO);
				// 修改完成，準備轉交(Send the success view)

				req.setAttribute("tableVO", tableVO); // 資料庫update成功後,正確的的tableVO物件,存入req
				String url = "/back-end/table/listOneTable.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 修改成功後,轉交listOneTable.jsp
				successView.forward(req, res);
			} catch (Exception e) {
				errorMsgs.add("修改資料失敗:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/table/update_table_input.jsp");
				failureView.forward(req, res);
			}

		}

		if ("insert".equals(action)) { // 來自addTable.jsp的請求
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				Integer table_nop = null;
				try {
					table_nop = new Integer(req.getParameter("table_nop").trim());
				} catch (NumberFormatException e) {
					table_nop = 0;
					errorMsgs.add("桌位人數請填數字");
				}
				Integer table_status = null;
				try {
					table_status = new Integer(req.getParameter("table_status").trim());
				} catch (NumberFormatException e) {
					table_nop = 0;
					errorMsgs.add("桌位狀態請填數字");
				}

				TableVO tableVO = new TableVO();
				tableVO.setTable_nop(table_nop);
				tableVO.setTable_status(table_status);

				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("tableVO", tableVO); // 含有輸入格式錯誤的empVO物件,也存入req
					RequestDispatcher failureView = req.getRequestDispatcher("/back-end/table/addTable.jsp");
					failureView.forward(req, res);
					return;
				}
				/*************************** 2.開始新增資料 ***************************************/
				TableService tableSvc = new TableService();
				tableVO = tableSvc.addTable(table_nop, table_status);

				/*************************** 3.新增完成,準備轉交(Send the Success view) ***********/
				String url = "/back-end/table/listAllTable.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 新增成功後轉交listAllTable.jsp
				successView.forward(req, res);
			} catch (Exception e) {
				errorMsgs.add(e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/table/addTable.jsp");
				failureView.forward(req, res);
			}
		}

		if ("delete".equals(action)) { // 來自listAllTable.jsp

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/*************************** 1.接收請求參數 ***************************************/
				Integer tableno = new Integer(req.getParameter("tableno"));

				/*************************** 2.開始刪除資料 ***************************************/
				TableService tableSvc = new TableService();
				tableSvc.deleteTable(tableno);

				/*************************** 3.刪除完成,準備轉交(Send the Success view) ***********/
				String url = "/back-end/table/listAllTable.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);// 刪除成功後,轉交回送出刪除的來源網頁
				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 **********************************/
			} catch (Exception e) {
				errorMsgs.add("刪除資料失敗:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/table/listAllTable.jsp");
				failureView.forward(req, res);
			}
		}
		
		if ("save".equals(action)) {
			try {
				/*************************** 1.接收請求參數 ***************************************/
				String data = (String) req.getParameter("data");
				System.out.println(data);
				JSONArray arr = new JSONArray(data);
				
				/*************************** 2.開始刪除資料 ***************************************/
				TableService tableSvc = new TableService();
				for(int i = 0; i < arr.length(); i++) {
					JSONObject obj = arr.getJSONObject(i);
					Integer tableno = Integer.parseInt(obj.getString("tableno"));
					Integer left = obj.getInt("left");
					Integer top = obj.getInt("top");
					
					TableVO tableVO = tableSvc.getOneTable(tableno);
					tableVO.setTable_left(left);
					tableVO.setTable_top(top);
					tableSvc.updateTable(tableVO);
				}

				/*************************** 3.刪除完成,準備轉交(Send the Success view) ***********/
				String url = "/back-end/table/tables.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);// 刪除成功後,轉交回送出刪除的來源網頁
				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 **********************************/
			} catch (Exception e) {
				e.printStackTrace();
//				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/table/listAllTable.jsp");
//				failureView.forward(req, res);
			}
		}
		
		
		if ("go_to_order".equals(action)) {
			   System.out.println("11111111");
			   try {

				   System.out.println("2222222222");
			    Integer tableno = new Integer(req.getParameter("tableno"));
			    
			    req.setAttribute("tableno", tableno);
			    String url = "/back-end/live_order/OrderSystem2.jsp";
			    RequestDispatcher successView = req.getRequestDispatcher(url);
			    successView.forward(req, res);
			    
			   } catch (Exception e) {

				   System.out.println("3333333333");
			    RequestDispatcher failureView = req.getRequestDispatcher("/back-end/table/tables.jsp");
			    failureView.forward(req, res);
			   }
			   
			  }

	}

}
