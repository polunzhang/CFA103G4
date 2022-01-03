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
					errorMsgs.add("�п�J�ู");
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
					errorMsgs.add("�ู�榡�����T");
				}

				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/back-end/table/select_page_table.jsp");
					failureView.forward(req, res);
					return;
				}

				TableService tableSvc = new TableService();
				TableVO tableVO = tableSvc.getOneTable(tableno);

				if (tableVO == null) {
					errorMsgs.add("�d�L���");
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
				errorMsgs.add("�L�k���o���:" + e.getMessage());
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
				errorMsgs.add("�п�J���H��");
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
				errorMsgs.add("���H�Ʈ榡�����T");
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
				errorMsgs.add("�d�L���");
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
//		errorMsgs.add("�L�k���o���:" + e.getMessage());
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
				errorMsgs.add("�п�J��쪬�A");
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
				errorMsgs.add("��쪬�A�榡�����T");
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
				errorMsgs.add("�d�L���");
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
//		errorMsgs.add("�L�k���o���:" + e.getMessage());
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
				errorMsgs.add("�L�k���o�n�ק諸���:" + e.getMessage());
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
				errorMsgs.add("�L�k���o�n�ק諸���:" + e.getMessage());
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
					errorMsgs.add("���H�ƽж�Ʀr");
				}

				Integer table_status = null;
				try {
					table_status = new Integer(req.getParameter("table_status").trim());

				} catch (NumberFormatException e) {
					table_nop = 0;
					errorMsgs.add("���H�ƽж�Ʀr");
				}

				TableVO tableVO = new TableVO();
				tableVO.setTableno(tableno);
				tableVO.setTable_nop(table_nop);
				tableVO.setTable_status(table_status);
				tableVO.setTable_left(0);
				tableVO.setTable_top(0);

				if (!errorMsgs.isEmpty()) {
					req.setAttribute("tableVO", tableVO); // �t����J�榡���~��empVO����,�]�s�Jreq
					RequestDispatcher failureView = req.getRequestDispatcher("/back-end/table/update_table_input.jsp");
					failureView.forward(req, res);
					return; // �{�����_
				}
				// �}�l�ק���

				TableService tableSvc = new TableService();
				tableVO = tableSvc.updateTable(tableVO);
				// �ק粒���A�ǳ����(Send the success view)

				req.setAttribute("tableVO", tableVO); // ��Ʈwupdate���\��,���T����tableVO����,�s�Jreq
				String url = "/back-end/table/listOneTable.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // �ק令�\��,���listOneTable.jsp
				successView.forward(req, res);
			} catch (Exception e) {
				errorMsgs.add("�ק��ƥ���:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/table/update_table_input.jsp");
				failureView.forward(req, res);
			}

		}

		if ("insert".equals(action)) { // �Ӧ�addTable.jsp���ШD
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				Integer table_nop = null;
				try {
					table_nop = new Integer(req.getParameter("table_nop").trim());
				} catch (NumberFormatException e) {
					table_nop = 0;
					errorMsgs.add("���H�ƽж�Ʀr");
				}
				Integer table_status = null;
				try {
					table_status = new Integer(req.getParameter("table_status").trim());
				} catch (NumberFormatException e) {
					table_nop = 0;
					errorMsgs.add("��쪬�A�ж�Ʀr");
				}

				TableVO tableVO = new TableVO();
				tableVO.setTable_nop(table_nop);
				tableVO.setTable_status(table_status);

				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("tableVO", tableVO); // �t����J�榡���~��empVO����,�]�s�Jreq
					RequestDispatcher failureView = req.getRequestDispatcher("/back-end/table/addTable.jsp");
					failureView.forward(req, res);
					return;
				}
				/*************************** 2.�}�l�s�W��� ***************************************/
				TableService tableSvc = new TableService();
				tableVO = tableSvc.addTable(table_nop, table_status);

				/*************************** 3.�s�W����,�ǳ����(Send the Success view) ***********/
				String url = "/back-end/table/listAllTable.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // �s�W���\�����listAllTable.jsp
				successView.forward(req, res);
			} catch (Exception e) {
				errorMsgs.add(e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/table/addTable.jsp");
				failureView.forward(req, res);
			}
		}

		if ("delete".equals(action)) { // �Ӧ�listAllTable.jsp

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/*************************** 1.�����ШD�Ѽ� ***************************************/
				Integer tableno = new Integer(req.getParameter("tableno"));

				/*************************** 2.�}�l�R����� ***************************************/
				TableService tableSvc = new TableService();
				tableSvc.deleteTable(tableno);

				/*************************** 3.�R������,�ǳ����(Send the Success view) ***********/
				String url = "/back-end/table/listAllTable.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);// �R�����\��,���^�e�X�R�����ӷ�����
				successView.forward(req, res);

				/*************************** ��L�i�઺���~�B�z **********************************/
			} catch (Exception e) {
				errorMsgs.add("�R����ƥ���:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/table/listAllTable.jsp");
				failureView.forward(req, res);
			}
		}
		
		if ("save".equals(action)) {
			try {
				/*************************** 1.�����ШD�Ѽ� ***************************************/
				String data = (String) req.getParameter("data");
				System.out.println(data);
				JSONArray arr = new JSONArray(data);
				
				/*************************** 2.�}�l�R����� ***************************************/
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

				/*************************** 3.�R������,�ǳ����(Send the Success view) ***********/
				String url = "/back-end/table/tables.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);// �R�����\��,���^�e�X�R�����ӷ�����
				successView.forward(req, res);

				/*************************** ��L�i�઺���~�B�z **********************************/
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
