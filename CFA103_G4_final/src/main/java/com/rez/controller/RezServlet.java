package com.rez.controller;

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
import com.rez_detail.model.RezDetailDAO;
import com.rez_detail.model.RezDetailVO;
import com.rez.model.RezService;
import com.rez.model.*;
import com.rez_time.model.RezTimeService;
import com.rez_time.model.RezTimeVO;
import com.rez_time.model.RezTimeDAO;

public class RezServlet extends HttpServlet {

	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");

		if ("getOne_For_Display".equals(action)) { // 來自select_page.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 **********************/
				String str = req.getParameter("rezno");
				if (str == null || (str.trim()).length() == 0) {
					errorMsgs.add("請輸入訂位編號");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/back-end/rez/select_page.jsp");
					failureView.forward(req, res);
					return; // 程式中斷
				}

				Integer rezno = null;
				try {
					rezno = new Integer(str);
				} catch (Exception e) {
					errorMsgs.add("訂位編號格式不正確");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/back-end/rez/select_page.jsp");
					failureView.forward(req, res);
					return;// 程式中斷
				}

				/*************************** 2.開始查詢資料 *****************************************/
				RezService rezSvc = new RezService();
				RezVO rezVO = rezSvc.getOneRez(rezno);
				if (rezVO == null) {
					errorMsgs.add("查無資料");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/back-end/rez/select_page.jsp");
					failureView.forward(req, res);
					return; // 程式中斷
				}

				/*************************** 3.查詢完成,準備轉交(Send the Success view) *************/
				req.setAttribute("rezVO", rezVO); // 資料庫取出的rezVO物件,存入req
				String url = "/back-end/rez/listOneRez.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 成功轉交
				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 *************************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得資料:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/rez/select_page.jsp");
				failureView.forward(req, res);
			}
		}

		if ("getOne_For_Update".equals(action)) { // 來自listAllRez.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/*************************** 1.接收請求參數 ****************************************/
				Integer rezno = new Integer(req.getParameter("rezno"));

				/*************************** 2.開始查詢資料 ****************************************/
				RezService rezSvc = new RezService();
				RezVO rezVO = rezSvc.getOneRez(rezno);

				/*************************** 3.查詢完成,準備轉交(Send the Success view) ************/
				req.setAttribute("rezVO", rezVO); // 資料庫取出的empVO物件,存入req
				String url = "/back-end/rez/update_rez_input.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);// 成功轉交 update_emp_input.jsp
				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 **********************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得要修改的資料:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/rez/listAllRez.jsp");
				failureView.forward(req, res);
			}
		}

		if ("update".equals(action)) { // 來自update_rez_input.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 **********************/

				Integer rezno = new Integer(req.getParameter("rezno").trim());

				Integer memno = null;
				String memno1 = req.getParameter("memno").trim();
				if (memno1.length() != 0) {
					try {
						memno = new Integer(memno1);
					} catch (Exception e) {
						errorMsgs.add("請輸入正確數字格式");
					}
				}

				String phone = req.getParameter("phone").trim();
				String phoneReg = "^[(0-9)(0-9)]{10,10}$";
				if (!phone.trim().matches(phoneReg)) {
					errorMsgs.add("連絡電話: 只能為數字10碼");
				}

				Integer num_of_ppl = new Integer(req.getParameter("num_of_ppl").trim());
				if (num_of_ppl == null || num_of_ppl == 0) {
					errorMsgs.add("訂位人數請勿空白");
				} else if (num_of_ppl == 0) {
					errorMsgs.add("訂位人數請勿為0");
				}

				java.sql.Date reservationdate = null;
				try {
					reservationdate = java.sql.Date.valueOf(req.getParameter("reservationdate").trim());
				} catch (IllegalArgumentException e) {
					reservationdate = new java.sql.Date(System.currentTimeMillis());
					errorMsgs.add("請選擇訂位日期!");
				}

				java.sql.Time reservationtime = null;
				try {
					reservationtime = java.sql.Time.valueOf(req.getParameter("reservationtime").trim());
				} catch (IllegalArgumentException e) {
					reservationtime = new java.sql.Time(System.currentTimeMillis());
					errorMsgs.add("請選擇訂位時間!");
				}

				String email1 = req.getParameter("email").trim();
				String email = null;
				if (email1.length() != 0) {
					try {
						email = new String(email1);
					} catch (Exception e) {
						errorMsgs.add("請填寫正確的email格式");
					}
				}

				String lastname = req.getParameter("lastname").trim();
				String lastnameReg = "^[(\\u4e00-\\u9fa5)(a-zA-Z)]{1,10}$";
				if (!lastname.trim().matches(lastnameReg)) {
					errorMsgs.add("不能為數字");
				}

				String sex = req.getParameter("sex").trim();
				if (sex == null || sex.trim().length() == 0) {
					errorMsgs.add("請勾選性別");
				}

				RezVO rezVO = new RezVO();
				rezVO.setRezno(rezno);
				rezVO.setMemno(memno);
				rezVO.setPhone(phone);
				rezVO.setNum_of_ppl(num_of_ppl);
				rezVO.setReservationdate(reservationdate);
				rezVO.setReservationtime(reservationtime);
				rezVO.setEmail(email);
				rezVO.setLastname(lastname);
				rezVO.setSex(sex);

				if (!errorMsgs.isEmpty()) {
					req.setAttribute("rezVO", rezVO); // 含有輸入格式錯誤的rezVO物件,也存入req
					RequestDispatcher failureView = req.getRequestDispatcher("/back-end/rez/update_rez_input.jsp");
					failureView.forward(req, res);
					return; // 程式中斷
				}

				/*************************** 2.開始修改資料 *****************************************/
				RezService rezSvc = new RezService();
				rezVO = rezSvc.updateRez(rezno, memno, phone, num_of_ppl, reservationdate, reservationtime, email,
						lastname, sex);
				
				RezTimeDAO reztime = new RezTimeDAO();
				List<RezTimeVO> rs = reztime.getAll();
				Integer count = 0;
				Integer count1 = 0;
				for (RezTimeVO r : rs) {
					System.out.println(r.getRez_time_date()+"==========none");
						System.out.println(reservationdate);

					if (r.getRez_time_date().equals(reservationdate)) {
						r.setRez_time_mid(r.getRez_time_mid() + 1);
						System.out.println(r.getRez_time_mid()+"==========done");
						reztime.update2(r);
					} else if (r.getRez_time_date().equals(reservationdate)) {
						r.setRez_time_eve(r.getRez_time_eve() + 1);
						System.out.println(r.getRez_time_eve()+"==========done");
						reztime.update3(r);
					}
				}
				req.setAttribute("rezVO", rezVO);

				/*************************** 3.修改完成,準備轉交(Send the Success view) *************/
				req.setAttribute("rezVO", rezVO); // 資料庫update成功後,正確的的rezVO物件,存入req
				String url = "/back-end/rez/listOneRez.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 修改成功後,轉交listOneRez.jsp
				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 *************************************/
			} catch (Exception e) {
				errorMsgs.add("修改資料失敗:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/rez/update_rez_input.jsp");
				failureView.forward(req, res);
			}
		}

		// 後台新增
		if ("insert".equals(action)) { // 來自addRez.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 **********************/

				String rezno1 = req.getParameter("rezno").trim();
				Integer rezno = 0;
				if (rezno1.length() != 0) {
					try {
						rezno = new Integer(rezno1);
					} catch (Exception e) {
						errorMsgs.add("訂位編號: 請填數字格式");
					}
				}

				String memno1 = req.getParameter("memno").trim();
				Integer memno = null;
				if (memno1.length() != 0) {
					try {
						memno = new Integer(memno1);
					} catch (Exception e) {
						errorMsgs.add("請輸入正確的會員編號(數字格式)");
					}
				}

				String phone = req.getParameter("phone").trim();
				String phoneReg = "^[(0-9)(0-9)]{10,10}$";
				if (!phone.trim().matches(phoneReg)) {
					errorMsgs.add("連絡電話: 只能為數字10碼");
				}

				Integer num_of_ppl = new Integer(req.getParameter("num_of_ppl").trim());
				if (num_of_ppl == 0) {
					errorMsgs.add("訂位人數請勿為0");
				}

				java.sql.Date reservationdate = null;
				try {
					reservationdate = java.sql.Date.valueOf(req.getParameter("reservationdate").trim());
				} catch (IllegalArgumentException e) {
					reservationdate = new java.sql.Date(System.currentTimeMillis());
					errorMsgs.add("請選擇訂位日期!");
				}

				java.sql.Time reservationtime = null;
				try {
					reservationtime = java.sql.Time.valueOf(req.getParameter("reservationtime").trim());
				} catch (IllegalArgumentException e) {
					reservationtime = new java.sql.Time(System.currentTimeMillis());
					errorMsgs.add("請選擇訂位時間!");
				}

				String email1 = req.getParameter("email").trim();
				String email = null;
				if (email1.length() != 0) {
					try {
						email = new String(email1);
					} catch (Exception e) {
						errorMsgs.add("請填寫正確的email格式");
					}
				}

				String lastname = req.getParameter("lastname").trim();
				String lastnameReg = "^[(\\u4e00-\\u9fa5)(a-zA-Z)]{1,10}$";
				if (!lastname.trim().matches(lastnameReg)) {
					errorMsgs.add("不能為數字");
				}

				String sex = req.getParameter("sex").trim();
				if (sex == null || sex.trim().length() == 0) {
					errorMsgs.add("請勾選性別");
				}

				RezVO rezVO = new RezVO();
				rezVO.setRezno(rezno);
				rezVO.setMemno(memno);
				rezVO.setPhone(phone);
				rezVO.setNum_of_ppl(num_of_ppl);
				rezVO.setReservationdate(reservationdate);
				rezVO.setReservationtime(reservationtime);
				rezVO.setEmail(email);
				rezVO.setLastname(lastname);
				rezVO.setSex(sex);

				if (!errorMsgs.isEmpty()) {
					req.setAttribute("rezVO", rezVO);
					RequestDispatcher failureView = req.getRequestDispatcher("/back-end/rez/addRez.jsp");
					failureView.forward(req, res);
					return;
				}
				/*************************** 2.開始新增資料 *****************************************/
				RezService rezSvc = new RezService();
				rezVO = rezSvc.addRez(rezno, memno, phone, num_of_ppl, reservationdate, reservationtime, email,
						lastname, sex);

				RezTimeDAO reztime = new RezTimeDAO();
				List<RezTimeVO> rs = reztime.getAll();
				Integer count = 0;
				Integer count1 = 0;
				for (RezTimeVO r : rs) {
					System.out.println(r.getRez_time_date()+"==========none");
						System.out.println(reservationdate);

					if (r.getRez_time_date().equals(reservationdate)) {
						r.setRez_time_mid(r.getRez_time_mid() + 1);
						System.out.println(r.getRez_time_mid()+"==========done");
						reztime.update2(r);
					} else if (r.getRez_time_date().equals(reservationdate)) {
						r.setRez_time_eve(r.getRez_time_eve() + 1);
						System.out.println(r.getRez_time_eve()+"==========done");
						reztime.update3(r);
					}
				}
				req.setAttribute("rezVO", rezVO);
				
				RezDetailDAO rezdetail = new RezDetailDAO();
//				rezdetail.insert();
				


				
				/*************************** 3.新增完成,準備轉交(Send the Success view) *************/
				String url = "/back-end/rez/listAllRez.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 新增成功後,轉交listAllRez.jsp
				successView.forward(req, res);
				
				/*************************** 其他可能的錯誤處理 *************************************/
			} catch (Exception e) {
				errorMsgs.add("新增資料失敗:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/rez/addRez.jsp");
				failureView.forward(req, res);
			}
		}
		
//		前台新增
		if ("book".equals(action)) { // 來自addRez.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 **********************/

				String rezno1 = req.getParameter("rezno").trim();
				Integer rezno = 0;
				if (rezno1.length() != 0) {
					try {
						rezno = new Integer(rezno1);
					} catch (Exception e) {
						errorMsgs.add("訂位編號: 請填數字格式");
					}
				}

				String memno1 = req.getParameter("memno").trim();
				Integer memno = null;
				if (memno1.length() != 0) {
					try {
						memno = new Integer(memno1);
					} catch (Exception e) {
						errorMsgs.add("請輸入正確的會員編號(數字格式)");
					}
				}

				String phone = req.getParameter("phone").trim();
				String phoneReg = "^[(0-9)(0-9)]{10,10}$";
				if (!phone.trim().matches(phoneReg)) {
					errorMsgs.add("連絡電話: 只能為數字10碼");
				}

				Integer num_of_ppl = new Integer(req.getParameter("num_of_ppl").trim());
				if (num_of_ppl == 0) {
					errorMsgs.add("訂位人數請勿為0");
				}

				java.sql.Date reservationdate = null;
				try {
					reservationdate = java.sql.Date.valueOf(req.getParameter("reservationdate").trim());
				} catch (IllegalArgumentException e) {
					reservationdate = new java.sql.Date(System.currentTimeMillis());
					errorMsgs.add("請選擇訂位日期!");
				}

				java.sql.Time reservationtime = null;
				try {
					reservationtime = java.sql.Time.valueOf(req.getParameter("reservationtime").trim());
				} catch (IllegalArgumentException e) {
					reservationtime = new java.sql.Time(System.currentTimeMillis());
					errorMsgs.add("請選擇訂位時間!");
				}

				String email1 = req.getParameter("email").trim();
				String email = null;
				if (email1.length() != 0) {
					try {
						email = new String(email1);
					} catch (Exception e) {
						errorMsgs.add("請填寫正確的email格式");
					}
				}

				String lastname = req.getParameter("lastname").trim();
				String lastnameReg = "^[(\\u4e00-\\u9fa5)(a-zA-Z)]{1,10}$";
				if (!lastname.trim().matches(lastnameReg)) {
					errorMsgs.add("不能為數字");
				}

				String sex = req.getParameter("sex").trim();
				if (sex == null || sex.trim().length() == 0) {
					errorMsgs.add("請勾選性別");
				}

				RezVO rezVO = new RezVO();
				rezVO.setRezno(rezno);
				rezVO.setMemno(memno);
				rezVO.setPhone(phone);
				rezVO.setNum_of_ppl(num_of_ppl);
				rezVO.setReservationdate(reservationdate);
				rezVO.setReservationtime(reservationtime);
				rezVO.setEmail(email);
				rezVO.setLastname(lastname);
				rezVO.setSex(sex);

				if (!errorMsgs.isEmpty()) {
					req.setAttribute("rezVO", rezVO);
					RequestDispatcher failureView = req.getRequestDispatcher("/front-end/rez/addRez.jsp");
					failureView.forward(req, res);
					return;
				}
				/*************************** 2.開始新增資料 *****************************************/
				RezService rezSvc = new RezService();
				rezVO = rezSvc.addRez(rezno, memno, phone, num_of_ppl, reservationdate, reservationtime, email,
						lastname, sex);
//				中午
				RezTimeDAO reztime = new RezTimeDAO();
				List<RezTimeVO> rs = reztime.getAll();
				Integer count = 0;
				Integer count1 = 0;
				for (RezTimeVO r : rs) {
					System.out.println(r.getRez_time_date()+"==========none");
						System.out.println(reservationdate);

					if (r.getRez_time_date().equals(reservationdate)) {
						r.setRez_time_mid(r.getRez_time_mid() + 1);
						System.out.println(r.getRez_time_mid()+"==========done");
						reztime.update2(r);
					} else if (r.getRez_time_date().equals(reservationdate)) {
						r.setRez_time_eve(r.getRez_time_eve() + 1);
						System.out.println(r.getRez_time_eve()+"==========done");
						reztime.update3(r);
					}
				}
				
				RezDetailDAO rezdetail = new RezDetailDAO();
				System.out.println(rezno+"===========good");

				req.setAttribute("rezVO", rezVO);

				
				/*************************** 3.新增完成,準備轉交(Send the Success view) *************/
				String url = "/front-end/rez/addsuccess.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 新增成功後,轉交listAllRez.jsp
				successView.forward(req, res);
				
				/*************************** 其他可能的錯誤處理 *************************************/
			} catch (Exception e) {
				errorMsgs.add("新增資料失敗:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/front-end/rez/addRez.jsp");
				failureView.forward(req, res);
			}
		}
		
		
		

		if ("delete".equals(action)) { // 來自listAllRez.jsp

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/*************************** 1.接收請求參數 ***************************************/
				Integer rezno = new Integer(req.getParameter("rezno"));

				/*************************** 2.開始刪除資料 ***************************************/
				RezService rezSvc = new RezService();
				rezSvc.deleteRez(rezno);
				
//				RezTimeDAO reztime = new RezTimeDAO();
//				List<RezTimeVO> rs = reztime.getAll();
//				Integer count = 0;
//				for (RezTimeVO r : rs) {
//					System.out.println(r.getRez_time_date()+"==========none");
//						System.out.println(rezno);
//
//					if (r.getRez_time_date().equals(rezno)) {
//						r.setRez_time_mid(r.getRez_time_mid() - 1);
//						System.out.println(r.getRez_time_mid()+"==========done");
//						reztime.update2(r);
//					} 
//				}

				/*************************** 3.刪除完成,準備轉交(Send the Success view) ***********/
				String url = "/back-end/rez/listAllRez.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);// 刪除成功後,轉交回送出刪除的來源網頁
				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 **********************************/
			} catch (Exception e) {
				errorMsgs.add("刪除資料失敗:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/rez/listAllRez.jsp");
				failureView.forward(req, res);
			}
		}
	}
}
