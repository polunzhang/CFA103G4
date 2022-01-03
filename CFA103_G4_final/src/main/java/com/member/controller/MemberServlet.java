package com.member.controller;

import java.io.*;
import java.util.*;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.*;
import javax.servlet.http.*;

import com.emp.model.*;
import com.member.model.*;

public class MemberServlet extends HttpServlet {

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
				String str = req.getParameter("memno");
				if (str == null || (str.trim()).length() == 0) {
					errorMsgs.add("請輸入會員編號");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/back-end/member/select_page.jsp");
					failureView.forward(req, res);
					return;// 程式中斷
				}

				Integer memno = null;
				try {
					memno = new Integer(str);
				} catch (Exception e) {
					errorMsgs.add("會員編號格式不正確");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/back-end/member/select_page.jsp");
					failureView.forward(req, res);
					return;// 程式中斷
				}

				/*************************** 2.開始查詢資料 *****************************************/
				MemberService memberSvc = new MemberService();
				MemberVO memberVO = memberSvc.getOneMember(memno);
				if (memberVO == null) {
					errorMsgs.add("查無資料");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/back-end/member/select_page.jsp");
					failureView.forward(req, res);
					return;// 程式中斷
				}

				/*************************** 3.查詢完成,準備轉交(Send the Success view) *************/
				req.setAttribute("memberVO", memberVO); // 資料庫取出的empVO物件,存入req
				String url = "/back-end/member/listOneMember.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 成功轉交 listOneEmp.jsp
				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 *************************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得資料:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/member/select_page.jsp");
				failureView.forward(req, res);
			}
		}

		if ("getOne_For_Display_By_Name".equals(action)) { // 來自select_page.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 **********************/
				String mname = req.getParameter("mname");
				if (mname == null || (mname.trim()).length() == 0) {
					errorMsgs.add("請輸入會員姓名");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/back-end/member/select_page.jsp");
					failureView.forward(req, res);
					return;// 程式中斷
				}

				/*************************** 2.開始查詢資料 *****************************************/
				MemberService memberSvc = new MemberService();
				MemberVO memberVO = memberSvc.getOneMemberByName(mname);
				if (memberVO == null) {
					errorMsgs.add("查無資料");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/back-end/member/select_page.jsp");
					failureView.forward(req, res);
					return;// 程式中斷
				}

				/*************************** 3.查詢完成,準備轉交(Send the Success view) *************/
				req.setAttribute("memberVO", memberVO); // 資料庫取出的empVO物件,存入req
				String url = "/back-end/member/listOneMember.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 成功轉交 listOneEmp.jsp
				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 *************************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得資料:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/member/select_page.jsp");
				failureView.forward(req, res);
			}
		}

		if ("getOne_For_Update".equals(action)) { // 來自listAllEmp.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/*************************** 1.接收請求參數 ****************************************/
				Integer memno = new Integer(req.getParameter("memno"));

				/*************************** 2.開始查詢資料 ****************************************/
				MemberService memberSvc = new MemberService();
				MemberVO memberVO = memberSvc.getOneMember(memno);

				/*************************** 3.查詢完成,準備轉交(Send the Success view) ************/
				req.setAttribute("memberVO", memberVO); // 資料庫取出的empVO物件,存入req
				String url = "/back-end/member/update_member_input.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);// 成功轉交 update_emp_input.jsp
				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 **********************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得要修改的資料:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/member/update_member_input.jsp");
				failureView.forward(req, res);
			}
		}

		if ("update".equals(action)) { // 來自update_emp_input.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 **********************/
				String mname = req.getParameter("mname");
				String mnameReg = "^[(\u4e00-\u9fa5)(a-zA-Z0-9_)]{2,10}$";
				if (mname == null || mname.trim().length() == 0) {
					errorMsgs.add("會員姓名: 請勿空白");
				} else if (!mname.trim().matches(mnameReg)) { // 以下練習正則(規)表示式(regular-expression)
					errorMsgs.add("會員姓名: 只能是中、英文字母、數字和_ , 且長度必需在2到10之間");
				}

				String mem_id = req.getParameter("mem_id");
				String mem_idReg = "^\\p{Alpha}[12]\\d{8}$";
				if (mem_id == null || mem_id.trim().length() == 0) {
					errorMsgs.add("請輸入身分證字號");
				} else if (!mem_id.trim().matches(mem_idReg)) {
					errorMsgs.add("請輸入正確的身分證字號格式");
				}

				String gender = req.getParameter("gender").trim();
				if (gender == null || gender.trim().length() == 0) {
					errorMsgs.add("性別請勿空白");
				}

				java.sql.Date bday = null;
				try {
					bday = java.sql.Date.valueOf(req.getParameter("bday").trim());
				} catch (IllegalArgumentException e) {
					bday = new java.sql.Date(System.currentTimeMillis());
					errorMsgs.add("請輸入生日");
				}

				String address = req.getParameter("address").trim();
				if (address == null || address.trim().length() == 0)
					errorMsgs.add("請輸入地址");

				String phone = req.getParameter("phone");
				String phoneReg = "^09[0-9]{8}$";
				if (phone == null || phone.trim().length() == 0) {
					errorMsgs.add("請輸入手機號碼");
				} else if (!phone.trim().matches(phoneReg)) {
					errorMsgs.add("請輸入正確的手機號碼格式");
				}

				String mem_email = req.getParameter("mem_email");
				String mem_emailReg = "^([A-Za-z0-9_\\-\\.])+\\@([A-Za-z0-9_\\-\\.])+\\.([A-Za-z]{2,4})$";
				if (mem_email == null || mem_email.trim().length() == 0) {
					errorMsgs.add("請輸入郵箱");
				} else if (!mem_email.trim().matches(mem_emailReg)) {
					errorMsgs.add("請輸入正確的郵箱格式");
				}

				String maccount = req.getParameter("maccount");
				String maccountReg = "^[A-Za-z0-9]{6,24}$";
				if (maccount == null || maccount.trim().length() == 0) {
					errorMsgs.add("請輸入帳號");
				} else if (!maccount.trim().matches(maccountReg)) {
					errorMsgs.add("請輸入正確的帳號格式");
				}

				String mpassword = req.getParameter("mpassword");
				String mpasswordReg = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{6,}$";
				if (mpassword == null || mpassword.trim().length() == 0) {
					errorMsgs.add("請輸入密碼");
				} else if (!mpassword.trim().matches(mpasswordReg)) {
					errorMsgs.add("第一個須為大寫英文字母");
				}

				Integer mem_state = new Integer(req.getParameter("mem_state").trim());

				Integer mem_verify = new Integer(req.getParameter("mem_state").trim());

				Integer memno = Integer.valueOf(req.getParameter("memno"));
				MemberVO memberVO = new MemberVO();
				memberVO.setMemno(memno);
				memberVO.setMname(mname);
				memberVO.setGender(gender);
				memberVO.setBday(bday);
				memberVO.setAddress(address);
				memberVO.setPhone(phone);
				memberVO.setMaccount(maccount);
				memberVO.setMpassword(mpassword);
				memberVO.setMem_email(mem_email);
				memberVO.setMem_state(mem_state);
				memberVO.setMem_verify(mem_verify);
				memberVO.setMem_id(mem_id);

				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("memberVO", memberVO); // 含有輸入格式錯誤的empVO物件,也存入req
					RequestDispatcher failureView = req
							.getRequestDispatcher("/back-end/member/update_member_input.jsp");
					failureView.forward(req, res);
					return; // 程式中斷
				}

				/*************************** 2.開始修改資料 *****************************************/
				MemberService memberSvc = new MemberService();
				memberVO = memberSvc.updateMember(memberVO);

				/*************************** 3.修改完成,準備轉交(Send the Success view) *************/
				req.setAttribute("memberVO", memberVO); // 資料庫update成功後,正確的的empVO物件,存入req
				String url = "/back-end/member/listAllMember.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 修改成功後,轉交listOneEmp.jsp
				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 *************************************/
			} catch (Exception e) {
				errorMsgs.add("修改資料失敗:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/member/update_member_input.jsp");
				failureView.forward(req, res);
			}
		}

		if ("member_update".equals(action)) { // 來自update_emp_input.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 **********************/
				String mname = req.getParameter("mname");
				String mnameReg = "^[(\u4e00-\u9fa5)(a-zA-Z0-9_)]{2,10}$";
				if (mname == null || mname.trim().length() == 0) {
					errorMsgs.add("會員姓名: 請勿空白");
				} else if (!mname.trim().matches(mnameReg)) { // 以下練習正則(規)表示式(regular-expression)
					errorMsgs.add("會員姓名: 只能是中、英文字母、數字和_ , 且長度必需在2到10之間");
				}

				String mem_id = req.getParameter("mem_id");
				String mem_idReg = "^\\p{Alpha}[12]\\d{8}$";
				if (mem_id == null || mem_id.trim().length() == 0) {
					errorMsgs.add("請輸入身分證字號");
				} else if (!mem_id.trim().matches(mem_idReg)) {
					errorMsgs.add("請輸入正確的身分證字號格式");
				}

				String gender = req.getParameter("gender").trim();
				if (gender == null || gender.trim().length() == 0) {
					errorMsgs.add("性別請勿空白");
				}

				java.sql.Date bday = null;
				try {
					bday = java.sql.Date.valueOf(req.getParameter("bday").trim());
				} catch (IllegalArgumentException e) {
					bday = new java.sql.Date(System.currentTimeMillis());
					errorMsgs.add("請輸入生日");
				}

				String address = req.getParameter("address").trim();
				if (address == null || address.trim().length() == 0)
					errorMsgs.add("請輸入地址");

				String phone = req.getParameter("phone");
				String phoneReg = "^09[0-9]{8}$";
				if (phone == null || phone.trim().length() == 0) {
					errorMsgs.add("請輸入手機號碼");
				} else if (!phone.trim().matches(phoneReg)) {
					errorMsgs.add("請輸入正確的手機號碼格式");
				}

				String maccount = req.getParameter("maccount");
				String maccountReg = "^[A-Za-z0-9]{6,24}$";
				if (maccount == null || maccount.trim().length() == 0) {
					errorMsgs.add("請輸入帳號");
				} else if (!maccount.trim().matches(maccountReg)) {
					errorMsgs.add("請輸入正確的帳號格式");
				}

				String mpassword = req.getParameter("mpassword");
				String mpasswordReg = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{6,}$";
				if (mpassword == null || mpassword.trim().length() == 0) {
					errorMsgs.add("請輸入密碼");
				} else if (!mpassword.trim().matches(mpasswordReg)) {
					errorMsgs.add("第一個須為大寫英文字母");
				}

				String mem_email = req.getParameter("mem_email");
				String mem_emailReg = "^([A-Za-z0-9_\\-\\.])+\\@([A-Za-z0-9_\\-\\.])+\\.([A-Za-z]{2,4})$";
				if (mem_email == null || mem_email.trim().length() == 0) {
					errorMsgs.add("請輸入郵箱");
				} else if (!mem_email.trim().matches(mem_emailReg)) {
					errorMsgs.add("請輸入正確的郵箱格式");
				}

				Integer mem_state = new Integer(req.getParameter("mem_state").trim());

				Integer mem_verify = new Integer(req.getParameter("mem_state").trim());

				Integer memno = Integer.valueOf(req.getParameter("memno"));
				MemberVO memberVO = new MemberVO();
				memberVO.setMemno(memno);
				memberVO.setMname(mname);
				memberVO.setGender(gender);
				memberVO.setBday(bday);
				memberVO.setAddress(address);
				memberVO.setPhone(phone);
				memberVO.setMaccount(maccount);
				memberVO.setMpassword(mpassword);
				memberVO.setMem_email(mem_email);
				memberVO.setMem_state(mem_state);
				memberVO.setMem_verify(mem_verify);
				memberVO.setMem_id(mem_id);

				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("memberVO", memberVO); // 含有輸入格式錯誤的empVO物件,也存入req
					RequestDispatcher failureView = req.getRequestDispatcher("/front-end/member/front-end_update.jsp");
					failureView.forward(req, res);
					return; // 程式中斷
				}

				/*************************** 2.開始修改資料 *****************************************/
				MemberService memberSvc = new MemberService();
				memberVO = memberSvc.updateMember(memberVO);

				/*************************** 3.修改完成,準備轉交(Send the Success view) *************/
				req.setAttribute("memberVO", memberVO); // 資料庫update成功後,正確的的empVO物件,存入req
				String url = "/front-end/member/front-end_listOneMember.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 修改成功後,轉交listOneEmp.jsp
				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 *************************************/
			} catch (Exception e) {
				errorMsgs.add("修改資料失敗:" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/front-end/member/front-end_listOneMember.jsp");
				failureView.forward(req, res);
			}
		}

		if ("insert".equals(action)) { // 來自addEmp.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
			MemberVO memberVO = null;
			try {
				/*********************** 1.接收請求參數 - 輸入格式的錯誤處理 *************************/
				String mname = req.getParameter("mname");
				String mnameReg = "^[(\u4e00-\u9fa5)(a-zA-Z0-9_)]{2,10}$";
				if (mname == null || mname.trim().length() == 0) {
					errorMsgs.add("會員姓名: 請勿空白");
				} else if (!mname.trim().matches(mnameReg)) { // 以下練習正則(規)表示式(regular-expression)
					errorMsgs.add("會員姓名: 只能是中、英文字母、數字和_ , 且長度必需在2到10之間");
				}

				String mem_id = req.getParameter("mem_id");
				String mem_idReg = "^\\p{Alpha}[12]\\d{8}$";
				if (mem_id == null || mem_id.trim().length() == 0) {
					errorMsgs.add("請輸入身分證字號");
				} else if (!mem_id.trim().matches(mem_idReg)) {
					errorMsgs.add("請輸入正確的身分證字號格式");
				}

				String gender = req.getParameter("gender").trim();
				if (gender == null || gender.trim().length() == 0) {
					errorMsgs.add("性別請勿空白");
				}

				java.sql.Date bday = null;
				try {
					bday = java.sql.Date.valueOf(req.getParameter("bday").trim());
				} catch (IllegalArgumentException e) {
					bday = new java.sql.Date(System.currentTimeMillis());
					errorMsgs.add("請輸入生日");
				}

				String address = req.getParameter("address").trim();
				if (address == null || address.trim().length() == 0)
					errorMsgs.add("請輸入地址");

				String phone = req.getParameter("phone");
				String phoneReg = "^09[0-9]{8}$";
				if (phone == null || phone.trim().length() == 0) {
					errorMsgs.add("請輸入手機號碼");
				} else if (!phone.trim().matches(phoneReg)) {
					errorMsgs.add("請輸入正確的手機號碼格式");
				}

				String maccount = req.getParameter("maccount");
				String maccountReg = "^[A-Za-z0-9]{6,24}$";
				if (maccount == null || maccount.trim().length() == 0) {
					errorMsgs.add("請輸入帳號");
				} else if (!maccount.trim().matches(maccountReg)) {
					errorMsgs.add("請輸入正確的帳號格式");
				}

				String mpassword = req.getParameter("mpassword");
				String mpasswordReg = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{6,}$";
				if (mpassword == null || mpassword.trim().length() == 0) {
					errorMsgs.add("請輸入密碼");
				} else if (!mpassword.trim().matches(mpasswordReg)) {
					errorMsgs.add("第一個須為大寫英文字母");
				}

				String mem_email = req.getParameter("mem_email");
				String mem_emailReg = "^([A-Za-z0-9_\\-\\.])+\\@([A-Za-z0-9_\\-\\.])+\\.([A-Za-z]{2,4})$";
				if (mem_email == null || mem_email.trim().length() == 0) {
					errorMsgs.add("請輸入郵箱");
				} else if (!mem_email.trim().matches(mem_emailReg)) {
					errorMsgs.add("請輸入正確的郵箱格式");
				}

				Integer mem_state = new Integer(req.getParameter("mem_state").trim());

				Integer mem_verify = new Integer(req.getParameter("mem_state").trim());

				memberVO = new MemberVO();
				memberVO.setMname(mname);
				memberVO.setGender(gender);
				memberVO.setBday(bday);
				memberVO.setAddress(address);
				memberVO.setPhone(phone);
				memberVO.setMaccount(maccount);
				memberVO.setMpassword(mpassword);
				memberVO.setMem_email(mem_email);
				memberVO.setMem_state(mem_state);
				memberVO.setMem_verify(mem_verify);
				memberVO.setMem_id(mem_id);

				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {

					req.setAttribute("memberVO", memberVO); // 含有輸入格式錯誤的empVO物件,也存入req
					RequestDispatcher failureView = req.getRequestDispatcher("/back-end/member/addMember.jsp");
					failureView.forward(req, res);
					return; // 程式中斷
				}

				/*************************** 2.開始新增資料 ***************************************/
				MemberService memberSvc = new MemberService();
				memberVO = memberSvc.addMember(mname, gender, bday, address, phone, maccount, mpassword, mem_email,
						mem_state, mem_verify, mem_id);

				/*************************** 3.新增完成,準備轉交(Send the Success view) ***********/
				String url = "/back-end/member/listAllMember.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 新增成功後轉交listAllMember.jsp
				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 **********************************/
			} catch (Exception e) {
				errorMsgs.add(e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/member/addMember.jsp");
				failureView.forward(req, res);
			}
		}

		if ("front-end_member_update".equals(action)) { // 來自listAllEmp.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/*************************** 1.接收請求參數 ****************************************/
				Integer memno = new Integer(req.getParameter("memno"));

				/*************************** 2.開始查詢資料 ****************************************/
				MemberService memberSvc = new MemberService();
				MemberVO memberVO = memberSvc.getOneMember(memno);

				/*************************** 3.查詢完成,準備轉交(Send the Success view) ************/
				req.setAttribute("memberVO", memberVO); // 資料庫取出的empVO物件,存入req
				String url = "/front-end/member/front-end_update.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);// 成功轉交 update_emp_input.jsp
				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 **********************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得要修改的資料:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/front-end/member/front-end_update.jsp");
				failureView.forward(req, res);
			}
		}

		if ("login".equals(action)) { // 來自addEmp.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/*********************** 1.接收請求參數 - 輸入格式的錯誤處理 *************************/
				String maccount = req.getParameter("maccount");
				String mpassword = req.getParameter("mpassword");
				MemberService memSVC = new MemberService();
				MemberVO memberVO = memSVC.login(maccount);
				if (maccount == null || maccount.trim().length() == 0) {
					errorMsgs.add("請輸入帳號");
				} else if (memberVO == null) {
					errorMsgs.add("此帳號不存在");
				} else if ((memberVO.getMem_state() == 0) && (memberVO.getMem_verify() == 0)) {
					errorMsgs.add("尚未驗證信箱");
				}

				if (mpassword == null || mpassword.trim().length() == 0) {
					errorMsgs.add("請輸入密碼");
				} else if (!mpassword.equals(memberVO.getMpassword())) {
					errorMsgs.add("密碼錯誤");
				}

				HttpSession session = req.getSession();
				session.setAttribute("memberVO", memberVO);

//				try {
//					String location = (String) session.getAttribute("location");
//					if (location != null) {
//						session.removeAttribute("location"); // *工作2: 看看有無來源網頁 (-->如有來源網頁:則重導至來源網頁)
//						res.sendRedirect(location);
//						return;
//					}
//				} catch (Exception ignored) {
//					res.sendRedirect(req.getContextPath() + "front-end/member/Login.jsp"); // *工作3:
//																							// (-->如無來源網頁:則重導至login_success.jsp)
//				}

				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {

					req.setAttribute("memberVO", memberVO); // 含有輸入格式錯誤的empVO物件,也存入req
					RequestDispatcher failureView = req.getRequestDispatcher("/front-end/member/Login.jsp");
					failureView.forward(req, res);
					return; // 程式中斷
				}

				/*************************** 3.新增完成,準備轉交(Send the Success view) ***********/
				String url = "/front-end/front-end_homepage.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 **********************************/
			} catch (Exception e) {
				errorMsgs.add(e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/front-end/member/Login.jsp");
				failureView.forward(req, res);
			}
		}


		if ("logout".equals(action)) {
			HttpSession session = req.getSession();
			session.invalidate();
			String url = "/front-end/front-end_homepage.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(url);
			successView.forward(req, res);
		}

		if ("signup".equals(action)) { // 來自addEmp.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
			MemberVO memberVO = null;
			try {
				/*********************** 1.接收請求參數 - 輸入格式的錯誤處理 *************************/
				MemberDAO memDAO = new MemberDAO();
				String mname = req.getParameter("mname");
				String mnameReg = "^[(\u4e00-\u9fa5)(a-zA-Z0-9_)]{2,10}$";
				if (mname == null || mname.trim().length() == 0) {
					errorMsgs.add("會員姓名: 請勿空白");
				} else if (!mname.trim().matches(mnameReg)) { // 以下練習正則(規)表示式(regular-expression)
					errorMsgs.add("會員姓名: 只能是中、英文字母、數字和_ , 且長度必需在2到10之間");
				}

				String gender = null;
				String mem_id = req.getParameter("mem_id");
				String mem_idReg = "^\\p{Alpha}[12]\\d{8}$";
				if (mem_id == null || mem_id.trim().length() == 0) {
					errorMsgs.add("請輸入身分證字號");
				} else if (!mem_id.trim().matches(mem_idReg)) {
					errorMsgs.add("請輸入正確的身分證字號格式");
				} else if (mem_id.charAt(1) == '1') {
					gender = new String("男");
				} else {
					gender = new String("女");
				}

				java.sql.Date bday = null;
				try {
					bday = java.sql.Date.valueOf(req.getParameter("bday").trim());
				} catch (IllegalArgumentException e) {
					bday = new java.sql.Date(System.currentTimeMillis());
					errorMsgs.add("請輸入生日");
				}

				String address = req.getParameter("address").trim();
				if (address == null || address.trim().length() == 0)
					errorMsgs.add("請輸入地址");

				String phone = req.getParameter("phone");
				String phoneReg = "^09[0-9]{8}$";
				if (phone == null || phone.trim().length() == 0) {
					errorMsgs.add("請輸入手機號碼");
				} else if (!phone.trim().matches(phoneReg)) {
					errorMsgs.add("請輸入正確的手機號碼格式");
				}

				String maccount = req.getParameter("maccount");
				String maccountReg = "^[A-Za-z0-9]{6,24}$";
				if (maccount == null || maccount.trim().length() == 0) {
					errorMsgs.add("請輸入帳號");
				} else if (!maccount.trim().matches(maccountReg)) {
					errorMsgs.add("請輸入正確的帳號格式");
				} else if (memDAO.login(maccount) != null) {
					errorMsgs.add("此帳號已存在");
				}

				String mpassword = req.getParameter("mpassword");
				String mpasswordReg = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{6,}$";
				if (mpassword == null || mpassword.trim().length() == 0) {
					errorMsgs.add("請輸入密碼");
				} else if (!mpassword.trim().matches(mpasswordReg)) {
					errorMsgs.add("第一個須為大寫英文字母");
				}

				String mem_email = req.getParameter("mem_email");
				String mem_emailReg = "^([A-Za-z0-9_\\-\\.])+\\@([A-Za-z0-9_\\-\\.])+\\.([A-Za-z]{2,4})$";
				if (mem_email == null || mem_email.trim().length() == 0) {
					errorMsgs.add("請輸入郵箱");
				} else if (!mem_email.trim().matches(mem_emailReg)) {
					errorMsgs.add("請輸入正確的郵箱格式");
				} else if (memDAO.forgetpassword(mem_email) != null) {
					errorMsgs.add("此信箱已使用");
				}

				Integer mem_state = new Integer(0);

				Integer mem_verify = new Integer(0);

				memberVO = new MemberVO();
				memberVO.setMname(mname);
				memberVO.setGender(gender);
				memberVO.setBday(bday);
				memberVO.setAddress(address);
				memberVO.setPhone(phone);
				memberVO.setMaccount(maccount);
				memberVO.setMpassword(mpassword);
				memberVO.setMem_email(mem_email);
				memberVO.setMem_state(mem_state);
				memberVO.setMem_verify(mem_verify);
				memberVO.setMem_id(mem_id);

				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {

					req.setAttribute("memberVO", memberVO); // 含有輸入格式錯誤的empVO物件,也存入req
					RequestDispatcher failureView = req.getRequestDispatcher("/front-end/member/Signup.jsp");
					failureView.forward(req, res);
					return; // 程式中斷
				}

				/*************************** 2.開始新增資料 ***************************************/
				MemberService memberSvc = new MemberService();
				memberVO = memberSvc.addMember(mname, gender, bday, address, phone, maccount, mpassword, mem_email,
						mem_state, mem_verify, mem_id);
				// 取的 memno
				memberVO = memberSvc.login(maccount);
				String str = generateForm(req, memberVO.getMemno());
				System.out.println(str);
				/* 寄送註冊成功信件 */
				new MailService().sendMail(mem_email, "恭喜您成為Business is Booming的會員", "請點連結驗證信箱 " + str);
				/*************************** 3.新增完成,準備轉交(Send the Success view) ***********/
				String url = "/front-end/member/Signupthanks.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 **********************************/
			} catch (Exception e) {
				errorMsgs.add(e.getMessage());
				e.printStackTrace();
				RequestDispatcher failureView = req.getRequestDispatcher("/front-end/member/Signup.jsp");
				failureView.forward(req, res);
			}
		}

		if ("delete".equals(action)) { // 來自listAllEmp.jsp

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/*************************** 1.接收請求參數 ***************************************/
				Integer memno = new Integer(req.getParameter("memno"));

				/*************************** 2.開始刪除資料 ***************************************/
				MemberService memberSvc = new MemberService();
				memberSvc.deleteMember(memno);

				/*************************** 3.刪除完成,準備轉交(Send the Success view) ***********/
				String url = "/back-end/member/listAllMember.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);// 刪除成功後,轉交回送出刪除的來源網頁
				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 **********************************/
			} catch (Exception e) {
				errorMsgs.add("刪除資料失敗:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/member/listAllMember.jsp");
				failureView.forward(req, res);
			}
		}

		if ("validate".equals(action)) {

			try {
				System.out.println("validate action!!!");
				String str = req.getParameter("memno");
				Integer memno = new Integer(str);
				/*************************** 2.開始查詢資料 *****************************************/
				MemberService memberSvc = new MemberService();
				MemberVO memberVO = memberSvc.getOneMember(memno);
				if (memberVO != null) {
					memberVO.setMem_state(1);
					memberVO.setMem_verify(1);
					MemberVO resultVO = memberSvc.updateMember(memberVO);
					if (resultVO != null) {
						String url = "/front-end/member/Login.jsp";
						RequestDispatcher successView = req.getRequestDispatcher(url);
						successView.forward(req, res);
					} else {
						System.out.println("errro 1");
					}
				} else {
					System.out.println("errro 2");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		if ("forgetpassword".equals(action)) {

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				System.out.println("forgetpassword action!!!");
				MemberDAO memberDAO = new MemberDAO();
				String mem_email = req.getParameter("mem_email");
				/*************************** 2.開始查詢資料 *****************************************/
				MemberService memberSvc = new MemberService();
				String random = new RandomPassword().getRandomPassword();
				MemberVO memberVO = memberSvc.forgetpassword(mem_email);
				if (memberVO == null) {
					errorMsgs.add("請輸入EMAIL");
				} else if (memberVO != null) {
					new MailService().sendMail(mem_email, "密碼變更通知", " 以下為新密碼: " + random);
				}

				memberVO.setMpassword(random);
				memberSvc.updateMember(memberVO);
				/*************************** 3.查詢完成,準備轉交(Send the Success view) *************/
				req.setAttribute("memberVO", memberVO); // 資料庫取出的empVO物件,存入req
				String url = "/front-end/member/Login.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 成功轉交 listOneEmp.jsp
				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 *************************************/
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private String generateForm(HttpServletRequest req, Integer memno) {
		StringBuffer form = new StringBuffer();
		form.append("<form action=\"" + req.getScheme() + "://" + req.getServerName() + ":" + req.getServerPort()
				+ req.getContextPath() + "/front-end/member/MemberServlet.do\" method=\"POST\">");
		form.append("	<input type=\"hidden\" name=\"action\" value=\"validate\">");
		form.append("	<input type=\"hidden\" name=\"memno\" value=\"" + memno + "\">");
		form.append("	<input type=\"submit\" value=\"點此鏈結\">");
		form.append("</form>");
		return form.toString();
	}
}
