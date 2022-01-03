package com.emp.controller;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.*;
import javax.servlet.http.*;

import com.emp.model.EmpService;
import com.emp.model.EmpVO;

public class EmpServlet extends HttpServlet {

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
				String str = req.getParameter("empno");
				if (str == null || (str.trim()).length() == 0) {
					errorMsgs.add("請輸入員工編號");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/back-end/emp/select_page.jsp");
					failureView.forward(req, res);
					return;// 程式中斷
				}

				Integer empno = null;
				try {
					empno = new Integer(str);
				} catch (Exception e) {
					errorMsgs.add("員工編號格式不正確");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/back-end/emp/select_page.jsp");
					failureView.forward(req, res);
					return;// 程式中斷
				}

				/*************************** 2.開始查詢資料 *****************************************/
				EmpService empSvc = new EmpService();
				EmpVO empVO = empSvc.getOneEmp(empno);
				if (empVO == null) {
					errorMsgs.add("查無資料");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/back-end/emp/select_page.jsp");
					failureView.forward(req, res);
					return;// 程式中斷
				}

				/*************************** 3.查詢完成,準備轉交(Send the Success view) *************/
				req.setAttribute("empVO", empVO); // 資料庫取出的empVO物件,存入req
				String url = "/back-end/emp/listOneEmp.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 成功轉交 listOneEmp.jsp
				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 *************************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得資料:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/emp/select_page.jsp");
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
				Integer empno = new Integer(req.getParameter("empno"));

				/*************************** 2.開始查詢資料 ****************************************/
				EmpService empSvc = new EmpService();
				EmpVO empVO = empSvc.getOneEmp(empno);

				/*************************** 3.查詢完成,準備轉交(Send the Success view) ************/
				req.setAttribute("empVO", empVO); // 資料庫取出的empVO物件,存入req
				String url = "/back-end/emp/updateEmp_input.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);// 成功轉交 update_emp_input.jsp
				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 **********************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得要修改的資料:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/emp/listAllEmp.jsp");
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
				Integer empno = new Integer(req.getParameter("empno").trim());

				String job = req.getParameter("job").trim();
				if (job == null || job.trim().length() == 0) {
					errorMsgs.add("職位請勿空白");
				}

				Integer sal = null;
				try {
					sal = new Integer(req.getParameter("sal").trim());
				} catch (NumberFormatException e) {
					sal = 0;
					errorMsgs.add("薪水請填數字.");
				}

				String ename = req.getParameter("ename");
				String enameReg = "^[(\u4e00-\u9fa5)(a-zA-Z)]{2,10}$";
				if (ename == null || ename.trim().length() == 0) {
					errorMsgs.add("員工姓名: 請勿空白");
				} else if (!ename.trim().matches(enameReg)) { // 以下練習正則(規)表示式(regular-expression)
					errorMsgs.add("員工姓名: 只能是中、英文字母, 且長度必需在2到10之間");
				}

				java.sql.Date hiredate = null;
				try {
					hiredate = java.sql.Date.valueOf(req.getParameter("hiredate").trim());
				} catch (IllegalArgumentException e) {
					hiredate = new java.sql.Date(System.currentTimeMillis());
					errorMsgs.add("請輸入日期!");
				}


				String eaccount = req.getParameter("eaccount");
				String epassword = req.getParameter("epassword");
				String epasswordReg = "^[A-Z]{1}[a-z]{1}[0-9]{6}$";
				if (epassword == null || epassword.trim().length() == 0) {
					errorMsgs.add("密碼請勿空白");
				} else if (!epassword.trim().matches(epasswordReg)) { // 以下練習正則(規)表示式(regular-expression)
					errorMsgs.add("密碼: 須包含首字1個英文大寫字母、1個小寫字母、6個數字");
				}

//				Integer job_status = null;
//				try {
//					job_status = new Integer(req.getParameter("job_status").trim());
//				} catch (NumberFormatException e) {
//					sal = 0;
//					errorMsgs.add("在職狀態請填數字.");
//				}
				Integer job_status = new Integer(req.getParameter("job_status").trim());

				
				EmpVO empVO = new EmpVO();
				empVO.setEmpno(empno);
				empVO.setJob(job);
				empVO.setSal(sal);
				empVO.setEname(ename);
				empVO.setHiredate(hiredate);
				empVO.setEaccount(eaccount);
				empVO.setEpassword(epassword);
				empVO.setJob_status(job_status);

				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("empVO", empVO); // 含有輸入格式錯誤的empVO物件,也存入req
					RequestDispatcher failureView = req.getRequestDispatcher("/back-end/emp/updateEmp_input.jsp");
					failureView.forward(req, res);
					return; // 程式中斷
				}

				/*************************** 2.開始修改資料 *****************************************/
				EmpService empSvc = new EmpService();
				empVO = empSvc.updateEmp(empno, job, sal, ename, hiredate, epassword, job_status);

				/*************************** 3.修改完成,準備轉交(Send the Success view) *************/
				req.setAttribute("empVO", empVO); // 資料庫update成功後,正確的的empVO物件,存入req
				String url = "/back-end/emp/listOneEmp.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 修改成功後,轉交listOneEmp.jsp
				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 *************************************/
			} catch (Exception e) {
				errorMsgs.add("修改資料失敗:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/emp/updateEmp_input.jsp");
				failureView.forward(req, res);
			}
		}

if ("insert".equals(action)) { // 來自addEmp.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/*********************** 1.接收請求參數 - 輸入格式的錯誤處理 *************************/
				String job = req.getParameter("job").trim();
				if (job == null || job.trim().length() == 0) {
					errorMsgs.add("職位請勿空白");
				}

				Integer sal = null;
				try {
					sal = new Integer(req.getParameter("sal").trim());
				} catch (NumberFormatException e) {
					sal = 0;
					errorMsgs.add("薪水請填數字.");
				}

				String ename = req.getParameter("ename");
				String enameReg = "^[(\u4e00-\u9fa5)(a-zA-Z)]{2,10}$";
				if (ename == null || ename.trim().length() == 0) {
					errorMsgs.add("員工姓名: 請勿空白");
				} else if (!ename.trim().matches(enameReg)) { // 以下練習正則(規)表示式(regular-expression)
					errorMsgs.add("員工姓名: 只能是中、英文字母, 且長度必需在2到10之間");
				}

				java.sql.Date hiredate = null;
				try {
					hiredate = java.sql.Date.valueOf(req.getParameter("hiredate").trim());
				} catch (IllegalArgumentException e) {
					hiredate = new java.sql.Date(System.currentTimeMillis());
					errorMsgs.add("請輸入日期!");
				}

				String eaccount = req.getParameter("eaccount");
				String eaccountReg = "^[A-Z]{1}[0-9]{5}$";
				if (eaccount == null || eaccount.trim().length() == 0) {
					errorMsgs.add("帳號請勿空白");
				} else if (!eaccount.trim().matches(eaccountReg)) { // 以下練習正則(規)表示式(regular-expression)
					errorMsgs.add("帳號: 須包含首字1個英文大寫字母、5個數字");
				} else {
					EmpService empSvc = new EmpService();
					EmpVO empVO = empSvc.getOneEaccount(eaccount);
					if (empVO != null) {
						req.setAttribute("eaccountError", "*帳號重複*");
						errorMsgs.add("帳號重複");
					}
				}

				String epassword = req.getParameter("epassword");
				String epasswordReg = "^[A-Z]{1}[a-z]{1}[0-9]{6}$";
				if (epassword == null || epassword.trim().length() == 0) {
					errorMsgs.add("密碼請勿空白");
				} else if (!epassword.trim().matches(epasswordReg)) { // 以下練習正則(規)表示式(regular-expression)
					errorMsgs.add("密碼: 須包含首字1個英文大寫字母、1個小寫字母、6個數字");
				}

//				Integer job_status = null;
//				try {
//					job_status = new Integer(req.getParameter("job_status").trim());
//				} catch (NumberFormatException e) {
//					
//					errorMsgs.add("在職狀態請填數字.");
//				}
//				Integer job_status = new Integer(req.getParameter("job_status").trim());
//				if(job_status == null) {					
//					errorMsgs.add("請選擇在職狀態");
//				}
				Integer job_status = new Integer(req.getParameter("job_status").trim());

				EmpVO empVO = new EmpVO();
				empVO.setJob(job);
				empVO.setSal(sal);
				empVO.setEname(ename);
				empVO.setHiredate(hiredate);
				empVO.setEaccount(eaccount);
				empVO.setEpassword(epassword);
				empVO.setJob_status(job_status);

				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("empVO", empVO); // 含有輸入格式錯誤的empVO物件,也存入req
					RequestDispatcher failureView = req.getRequestDispatcher("/back-end/emp/addEmp.jsp");
					failureView.forward(req, res);
					return; // 程式中斷
				}

				/*************************** 2.開始新增資料 ***************************************/
				EmpService empSvc = new EmpService();
				empVO = empSvc.addEmp(job, sal, ename, hiredate, eaccount, epassword, job_status);

				/*************************** 3.新增完成,準備轉交(Send the Success view) ***********/
				String url = "/back-end/emp/listAllEmp.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 新增成功後轉交listAllEmp.jsp
				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 **********************************/
			} catch (Exception e) {
				errorMsgs.add(e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/emp/addEmp.jsp");
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
				Integer empno = new Integer(req.getParameter("empno"));

				/*************************** 2.開始刪除資料 ***************************************/
				EmpService empSvc = new EmpService();
				empSvc.deleteEmp(empno);

				/*************************** 3.刪除完成,準備轉交(Send the Success view) ***********/
				String url = "/back-end/emp/listAllEmp.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);// 刪除成功後,轉交回送出刪除的來源網頁
				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 **********************************/
			} catch (Exception e) {
				errorMsgs.add("刪除資料失敗:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/emp/listAllEmp.jsp");
				failureView.forward(req, res);
			}
		}

	}
}
