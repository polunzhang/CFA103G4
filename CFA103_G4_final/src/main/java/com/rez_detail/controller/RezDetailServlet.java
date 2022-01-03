package com.rez_detail.controller;

import java.io.*;
import java.util.*;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Date;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import java.sql.Timestamp;
import java.sql.Date;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import com.rez.model.RezVO;
import com.rez_detail.model.RezDetailService;
import com.rez_detail.model.RezDetailVO;
import com.rez.model.RezService;
import com.rez.model.*;
import com.rez_time.model.RezTimeService;
import com.table.model.TableService;
import com.table.model.TableVO;
import com.rez_time.model.RezTimeDAO;

public class RezDetailServlet extends HttpServlet {

	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		doPost(req, res);
	}
	public void doPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");
		
		if("getOne_For_Display".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
			
			try {
				/***************************1.��?��??�隢??���?��?�� - ??�詨?���??���?��?�隤?�����**********************/
				String str = req.getParameter("rezno");
				if (str == null || (str.trim()).length() == 0) {
						errorMsgs.add("?��??��?��?��?���??��?����");
					}
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/back-end/rezdetail/allrezdetail.jsp");
					failureView.forward(req, res);
					return; //??��?��?��??���?
					}
					
					Integer rezno = null;
				try {
						rezno = new Integer(str);
					} catch (Exception e) {
						errorMsgs.add("?���??��?����?�??���?�迤?���?");
					}
					// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/back-end/rezdetail/allrezdetail.jsp");
					failureView.forward(req, res);
					return;//??��?��?��??���?
				}
				/***************************2.��??��??��?�閰Ｚ�?����*****************************************/
				RezDetailService rezdetailSvc = new RezDetailService();
				RezDetailVO rezdetailVO = rezdetailSvc.getOneRezno(rezno);
				if (rezdetailVO == null) {
						errorMsgs.add("��?��??��?���");
					}
					// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/back-end/rezdetail/allrezdetail.jsp");
					failureView.forward(req, res);
				return; //??��?��?��??���?
				}
					
				/***************************3.��?�閰Ｗ�?����,??���?��?���?(Send the Success view)*************/
				req.setAttribute("rezdetailVO", rezdetailVO); // ??���?��?���?����??�ezVO�??��,?��req
				String url = "/back-end/rezdetail/update_rezdetail.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // ��??���?���?
				successView.forward(req, res);
					
				/***************************��???�?����?����?��?�隤?�����*************************************/
				} catch (Exception e) {
				errorMsgs.add("��?��?��?����?����:" + e.getMessage());
				RequestDispatcher failureView = req
							.getRequestDispatcher("/back-end/rezdetail/selectrezdetail.jsp");
				failureView.forward(req, res);
			}
		}
		
		if ("getOne_For_Update".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
//			try {
				/*************************** 1.��?��??�隢??���?��?�� ****************************************/
			TableService tableSvc = new TableService();	
			Integer tableno = new Integer(req.getParameter("tableno"));
				
				/*************************** 2.��??��??��?�閰Ｚ�?���� ****************************************/
				
				TableVO tableVO = tableSvc.getOneTable(tableno);
				tableVO.setTable_status(1);
				tableSvc.updateTable(tableVO);
				
				/***************************3.��?�閰Ｗ�?����,??���?��?���?(Send the Success view)*************/
				req.setAttribute("tableVO", tableVO); // ??���?��?���?����??�ezVO�??��,?��req
				String url = "/back-end/rezdetail/listOne.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // ��??���?���?
				successView.forward(req, res);
					
				/***************************��???�?����?����?��?�隤?�����*************************************/
//			} catch (Exception e) {
//				errorMsgs.add("��?��?��?����?����:" + e.getMessage());
//				RequestDispatcher failureView = req
//							.getRequestDispatcher("/back-end/rezdetail/allrezdetail.jsp");
//				failureView.forward(req, res);
//			}
		}
		
		if("insert".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			try {
				/***************************1.��?��??�隢??���?��?�� - ??�詨?���??���?��?�隤?�����**********************/
				
Integer rezno = new Integer(req.getParameter("rezno").trim());

Integer tableno = new Integer(req.getParameter("tableno").trim());

				RezDetailVO rezdetailVO = new RezDetailVO();
				rezdetailVO.setRezno(rezno);
				rezdetailVO.setTableno(tableno);
				
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("rezdetailVO", rezdetailVO);
					RequestDispatcher failureView = req.getRequestDispatcher("/back-end/rezdetail/allrezdetail.jsp");
					failureView.forward(req, res);
					return;
				}
				/*************************** 2.��??��??��?��?���� *****************************************/
				RezDetailService rezdetailSvc = new RezDetailService();
				rezdetailVO = rezdetailSvc.addRezDetail(rezno, tableno);
				
				/*************************** 3.��?��?����,??���?��?���?(Send the Success view) *************/
				String url = "/back-end/rezdetail/allrezdetail.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // ��?��?���?����?,?��?��漱listAllRez.jsp
				successView.forward(req, res);
				
				/*************************** ��???�?����?����?��?�隤?����� *************************************/
			} catch (Exception e) {
				errorMsgs.add("��?��?����?��?����:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/rezdetail/allrezdetail.jsp");
				failureView.forward(req, res);
			}
		}
		
if ("update".equals(action)) { // ??��?�update_rez_input.jsp��??��??���?
			
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			try {
				/***************************1.��?��??�隢??���?��?�� - ??�詨?���??���?��?�隤?�����**********************/
				
Integer rezno = new Integer(req.getParameter("rezno").trim());			

Integer tableno = new Integer(req.getParameter("tableno").trim());

				RezDetailVO rezdetailVO = new RezDetailVO();
				rezdetailVO.setRezno(rezno);
				rezdetailVO.setTableno(tableno);
				
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("rezdetailVO", rezdetailVO); // ��?���??��?���??�??�隤?����?�ezVO�??��,??��?��req
					RequestDispatcher failureView = req
							.getRequestDispatcher("/back-end/rezdetail/update_rezdetail.jsp");
					failureView.forward(req, res);
					return; //??��?��?��??���?
				}
				
				/***************************2.��??��??��?��??��?���*****************************************/
				RezDetailService rezdetailSvc = new RezDetailService();
				rezdetailVO = rezdetailSvc.updateRezDetail(rezno, tableno);
				
				/***************************3.?��?��??�摰?����,??���?��?���?(Send the Success view)*************/
				req.setAttribute("rezdetailVO", rezdetailVO); // ??���?��?�update��??����?,??��?��箇�??��??�ezVO�??��,?��req
				String url = "/back-end/rezdetail/listOne.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // ?��?��??����?����?,?��?��漱listOneRez.jsp
				successView.forward(req, res);
				
				/***************************��???�?����?����?��?�隤?�����*************************************/
			} catch (Exception e) {
				errorMsgs.add("?��?��??��?���?��?����:"+e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/back-end/rezdetail/update_rezdetail.jsp");
				failureView.forward(req, res);
			}
		}
	}
}
