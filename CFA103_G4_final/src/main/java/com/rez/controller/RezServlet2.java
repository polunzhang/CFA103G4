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
import com.rez.model.RezService;
import com.rez.model.*;
import com.rez_time.model.RezTimeService;
import com.rez_time.model.RezTimeVO;
import com.rez_time.model.RezTimeDAO;

public class RezServlet2 extends HttpServlet {

	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");
		
		if ("insert".equals(action)) { // 來自addRez.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
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

//				req.setAttribute("rezVO", rezVO);


				/*************************** 3.新增完成,準備轉交(Send the Success view) *************/
				String url = "/front-end/rez/addsuccess.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 新增成功後,轉交addsuccess.jsp
				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 *************************************/
			} catch (Exception e) {
				errorMsgs.add("新增資料失敗:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/front-end/rez/addRez.jsp");
				failureView.forward(req, res);
			}
		}
	}
}
