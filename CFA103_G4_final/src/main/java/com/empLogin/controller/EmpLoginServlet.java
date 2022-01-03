package com.empLogin.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.emp.model.EmpService;
import com.emp.model.EmpVO;
import com.emperDetail.model.EmperDetailService;
import com.emperDetail.model.EmperDetailVO;

public class EmpLoginServlet extends HttpServlet{

	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}
	
	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");
		
if ("login".equals(action)) { // 來自empLogin.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/*********************** 1.接收請求參數 - 輸入格式的錯誤處理 *************************/
				
				String eaccount = req.getParameter("eaccount");
				String epassword = req.getParameter("epassword");
				
				if (eaccount == null || eaccount.trim().length() == 0) {
					errorMsgs.add("請輸入帳號");
				}
				EmpService empSvc = new EmpService();
				EmpVO empVO = empSvc.getOneEaccount(eaccount);
				if (eaccount.trim().length() != 0 && empVO == null) {
					req.setAttribute("eaccountError", "查無帳號");
					errorMsgs.add("查無帳號");
					}
//					req.setAttribute("eaccountError", "查無帳號");
				if (epassword == null || epassword.trim().length() == 0) {
						errorMsgs.add("請輸入密碼");
				}else if (!epassword.equals(empVO.getEpassword())) {
					errorMsgs.add("密碼錯誤");
//					req.setAttribute("epasswordError", "密碼錯誤");
				}else if (empVO.getJob_status() == 0) {
					errorMsgs.add("離職員工不具登入權限");
				}else{
					
					EmperDetailService eds = new EmperDetailService();
					List<EmperDetailVO> list = eds.getByEmpno(empVO.getEmpno());
					
					List<Integer> list2 = new ArrayList<Integer>();
					for(EmperDetailVO aEmperDetail : list) {
						list2.add(aEmperDetail.getEmperno());
					}
					
					HttpSession session = req.getSession();
					session.setAttribute("list", list);   //*工作1: 才在session內做已經登入過的標識
					session.setAttribute("list2", list2);   //只含權限編號
					session.setAttribute("empVO", empVO);   
				       try {                                                        
				         String location = (String) session.getAttribute("location");
				         if (location != null) {
				           session.removeAttribute("location");   //*工作2: 看看有無來源網頁 (-->如有來源網頁:則重導至來源網頁)
				           res.sendRedirect(location);            
				           return;
				         }
				       }catch (Exception ignored) { }

				      res.sendRedirect(req.getContextPath()+"/back-end/back-end-home.jsp");  //*工作3: (-->如無來源網頁:則重導至login_success.jsp)
				   
					
					/*************************** 2.準備轉交(Send the Success view) ***********/
//					String url = "/back-end/empLogin/loginSuccess.html";
//					RequestDispatcher successView = req.getRequestDispatcher(url); // 新增成功後轉交loginSuccess.html
//					successView.forward(req, res);
				}


				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("empVO", empVO); // 含有輸入格式錯誤的empVO物件,也存入req
					RequestDispatcher failureView = req.getRequestDispatcher("/back-end/empLogin/empLogin.jsp");
					failureView.forward(req, res);
					return; // 程式中斷
				}
				
				/*************************** 其他可能的錯誤處理 **********************************/
			} catch (Exception e) {
				errorMsgs.add(e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/empLogin/empLogin.jsp");
				failureView.forward(req, res);
			}
		}

if ("logOut".equals(action)) {
		HttpSession session = req.getSession();
		session.invalidate();
		String url = "/back-end/empLogin/empLogin.jsp";
		RequestDispatcher successView = req.getRequestDispatcher(url); 
		successView.forward(req, res);
		}
		
	}	
	
}
