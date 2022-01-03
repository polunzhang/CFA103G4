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
		
if ("login".equals(action)) { // �Ӧ�empLogin.jsp���ШD

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/*********************** 1.�����ШD�Ѽ� - ��J�榡�����~�B�z *************************/
				
				String eaccount = req.getParameter("eaccount");
				String epassword = req.getParameter("epassword");
				
				if (eaccount == null || eaccount.trim().length() == 0) {
					errorMsgs.add("�п�J�b��");
				}
				EmpService empSvc = new EmpService();
				EmpVO empVO = empSvc.getOneEaccount(eaccount);
				if (eaccount.trim().length() != 0 && empVO == null) {
					req.setAttribute("eaccountError", "�d�L�b��");
					errorMsgs.add("�d�L�b��");
					}
//					req.setAttribute("eaccountError", "�d�L�b��");
				if (epassword == null || epassword.trim().length() == 0) {
						errorMsgs.add("�п�J�K�X");
				}else if (!epassword.equals(empVO.getEpassword())) {
					errorMsgs.add("�K�X���~");
//					req.setAttribute("epasswordError", "�K�X���~");
				}else if (empVO.getJob_status() == 0) {
					errorMsgs.add("��¾���u����n�J�v��");
				}else{
					
					EmperDetailService eds = new EmperDetailService();
					List<EmperDetailVO> list = eds.getByEmpno(empVO.getEmpno());
					
					List<Integer> list2 = new ArrayList<Integer>();
					for(EmperDetailVO aEmperDetail : list) {
						list2.add(aEmperDetail.getEmperno());
					}
					
					HttpSession session = req.getSession();
					session.setAttribute("list", list);   //*�u�@1: �~�bsession�����w�g�n�J�L������
					session.setAttribute("list2", list2);   //�u�t�v���s��
					session.setAttribute("empVO", empVO);   
				       try {                                                        
				         String location = (String) session.getAttribute("location");
				         if (location != null) {
				           session.removeAttribute("location");   //*�u�@2: �ݬݦ��L�ӷ����� (-->�p���ӷ�����:�h���ɦܨӷ�����)
				           res.sendRedirect(location);            
				           return;
				         }
				       }catch (Exception ignored) { }

				      res.sendRedirect(req.getContextPath()+"/back-end/back-end-home.jsp");  //*�u�@3: (-->�p�L�ӷ�����:�h���ɦ�login_success.jsp)
				   
					
					/*************************** 2.�ǳ����(Send the Success view) ***********/
//					String url = "/back-end/empLogin/loginSuccess.html";
//					RequestDispatcher successView = req.getRequestDispatcher(url); // �s�W���\�����loginSuccess.html
//					successView.forward(req, res);
				}


				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("empVO", empVO); // �t����J�榡���~��empVO����,�]�s�Jreq
					RequestDispatcher failureView = req.getRequestDispatcher("/back-end/empLogin/empLogin.jsp");
					failureView.forward(req, res);
					return; // �{�����_
				}
				
				/*************************** ��L�i�઺���~�B�z **********************************/
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
