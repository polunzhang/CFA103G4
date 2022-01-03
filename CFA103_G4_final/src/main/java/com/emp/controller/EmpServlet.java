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

if ("getOne_For_Display".equals(action)) { // �Ӧ�select_page.jsp���ШD

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/*************************** 1.�����ШD�Ѽ� - ��J�榡�����~�B�z **********************/
				String str = req.getParameter("empno");
				if (str == null || (str.trim()).length() == 0) {
					errorMsgs.add("�п�J���u�s��");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/back-end/emp/select_page.jsp");
					failureView.forward(req, res);
					return;// �{�����_
				}

				Integer empno = null;
				try {
					empno = new Integer(str);
				} catch (Exception e) {
					errorMsgs.add("���u�s���榡�����T");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/back-end/emp/select_page.jsp");
					failureView.forward(req, res);
					return;// �{�����_
				}

				/*************************** 2.�}�l�d�߸�� *****************************************/
				EmpService empSvc = new EmpService();
				EmpVO empVO = empSvc.getOneEmp(empno);
				if (empVO == null) {
					errorMsgs.add("�d�L���");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/back-end/emp/select_page.jsp");
					failureView.forward(req, res);
					return;// �{�����_
				}

				/*************************** 3.�d�ߧ���,�ǳ����(Send the Success view) *************/
				req.setAttribute("empVO", empVO); // ��Ʈw���X��empVO����,�s�Jreq
				String url = "/back-end/emp/listOneEmp.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // ���\��� listOneEmp.jsp
				successView.forward(req, res);

				/*************************** ��L�i�઺���~�B�z *************************************/
			} catch (Exception e) {
				errorMsgs.add("�L�k���o���:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/emp/select_page.jsp");
				failureView.forward(req, res);
			}
		}

if ("getOne_For_Update".equals(action)) { // �Ӧ�listAllEmp.jsp���ШD

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/*************************** 1.�����ШD�Ѽ� ****************************************/
				Integer empno = new Integer(req.getParameter("empno"));

				/*************************** 2.�}�l�d�߸�� ****************************************/
				EmpService empSvc = new EmpService();
				EmpVO empVO = empSvc.getOneEmp(empno);

				/*************************** 3.�d�ߧ���,�ǳ����(Send the Success view) ************/
				req.setAttribute("empVO", empVO); // ��Ʈw���X��empVO����,�s�Jreq
				String url = "/back-end/emp/updateEmp_input.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);// ���\��� update_emp_input.jsp
				successView.forward(req, res);

				/*************************** ��L�i�઺���~�B�z **********************************/
			} catch (Exception e) {
				errorMsgs.add("�L�k���o�n�ק諸���:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/emp/listAllEmp.jsp");
				failureView.forward(req, res);
			}
		}

if ("update".equals(action)) { // �Ӧ�update_emp_input.jsp���ШD

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/*************************** 1.�����ШD�Ѽ� - ��J�榡�����~�B�z **********************/
				Integer empno = new Integer(req.getParameter("empno").trim());

				String job = req.getParameter("job").trim();
				if (job == null || job.trim().length() == 0) {
					errorMsgs.add("¾��ФŪť�");
				}

				Integer sal = null;
				try {
					sal = new Integer(req.getParameter("sal").trim());
				} catch (NumberFormatException e) {
					sal = 0;
					errorMsgs.add("�~���ж�Ʀr.");
				}

				String ename = req.getParameter("ename");
				String enameReg = "^[(\u4e00-\u9fa5)(a-zA-Z)]{2,10}$";
				if (ename == null || ename.trim().length() == 0) {
					errorMsgs.add("���u�m�W: �ФŪť�");
				} else if (!ename.trim().matches(enameReg)) { // �H�U�m�ߥ��h(�W)��ܦ�(regular-expression)
					errorMsgs.add("���u�m�W: �u��O���B�^��r��, �B���ץ��ݦb2��10����");
				}

				java.sql.Date hiredate = null;
				try {
					hiredate = java.sql.Date.valueOf(req.getParameter("hiredate").trim());
				} catch (IllegalArgumentException e) {
					hiredate = new java.sql.Date(System.currentTimeMillis());
					errorMsgs.add("�п�J���!");
				}


				String eaccount = req.getParameter("eaccount");
				String epassword = req.getParameter("epassword");
				String epasswordReg = "^[A-Z]{1}[a-z]{1}[0-9]{6}$";
				if (epassword == null || epassword.trim().length() == 0) {
					errorMsgs.add("�K�X�ФŪť�");
				} else if (!epassword.trim().matches(epasswordReg)) { // �H�U�m�ߥ��h(�W)��ܦ�(regular-expression)
					errorMsgs.add("�K�X: ���]�t���r1�ӭ^��j�g�r���B1�Ӥp�g�r���B6�ӼƦr");
				}

//				Integer job_status = null;
//				try {
//					job_status = new Integer(req.getParameter("job_status").trim());
//				} catch (NumberFormatException e) {
//					sal = 0;
//					errorMsgs.add("�b¾���A�ж�Ʀr.");
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
					req.setAttribute("empVO", empVO); // �t����J�榡���~��empVO����,�]�s�Jreq
					RequestDispatcher failureView = req.getRequestDispatcher("/back-end/emp/updateEmp_input.jsp");
					failureView.forward(req, res);
					return; // �{�����_
				}

				/*************************** 2.�}�l�ק��� *****************************************/
				EmpService empSvc = new EmpService();
				empVO = empSvc.updateEmp(empno, job, sal, ename, hiredate, epassword, job_status);

				/*************************** 3.�ק粒��,�ǳ����(Send the Success view) *************/
				req.setAttribute("empVO", empVO); // ��Ʈwupdate���\��,���T����empVO����,�s�Jreq
				String url = "/back-end/emp/listOneEmp.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // �ק令�\��,���listOneEmp.jsp
				successView.forward(req, res);

				/*************************** ��L�i�઺���~�B�z *************************************/
			} catch (Exception e) {
				errorMsgs.add("�ק��ƥ���:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/emp/updateEmp_input.jsp");
				failureView.forward(req, res);
			}
		}

if ("insert".equals(action)) { // �Ӧ�addEmp.jsp���ШD

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/*********************** 1.�����ШD�Ѽ� - ��J�榡�����~�B�z *************************/
				String job = req.getParameter("job").trim();
				if (job == null || job.trim().length() == 0) {
					errorMsgs.add("¾��ФŪť�");
				}

				Integer sal = null;
				try {
					sal = new Integer(req.getParameter("sal").trim());
				} catch (NumberFormatException e) {
					sal = 0;
					errorMsgs.add("�~���ж�Ʀr.");
				}

				String ename = req.getParameter("ename");
				String enameReg = "^[(\u4e00-\u9fa5)(a-zA-Z)]{2,10}$";
				if (ename == null || ename.trim().length() == 0) {
					errorMsgs.add("���u�m�W: �ФŪť�");
				} else if (!ename.trim().matches(enameReg)) { // �H�U�m�ߥ��h(�W)��ܦ�(regular-expression)
					errorMsgs.add("���u�m�W: �u��O���B�^��r��, �B���ץ��ݦb2��10����");
				}

				java.sql.Date hiredate = null;
				try {
					hiredate = java.sql.Date.valueOf(req.getParameter("hiredate").trim());
				} catch (IllegalArgumentException e) {
					hiredate = new java.sql.Date(System.currentTimeMillis());
					errorMsgs.add("�п�J���!");
				}

				String eaccount = req.getParameter("eaccount");
				String eaccountReg = "^[A-Z]{1}[0-9]{5}$";
				if (eaccount == null || eaccount.trim().length() == 0) {
					errorMsgs.add("�b���ФŪť�");
				} else if (!eaccount.trim().matches(eaccountReg)) { // �H�U�m�ߥ��h(�W)��ܦ�(regular-expression)
					errorMsgs.add("�b��: ���]�t���r1�ӭ^��j�g�r���B5�ӼƦr");
				} else {
					EmpService empSvc = new EmpService();
					EmpVO empVO = empSvc.getOneEaccount(eaccount);
					if (empVO != null) {
						req.setAttribute("eaccountError", "*�b������*");
						errorMsgs.add("�b������");
					}
				}

				String epassword = req.getParameter("epassword");
				String epasswordReg = "^[A-Z]{1}[a-z]{1}[0-9]{6}$";
				if (epassword == null || epassword.trim().length() == 0) {
					errorMsgs.add("�K�X�ФŪť�");
				} else if (!epassword.trim().matches(epasswordReg)) { // �H�U�m�ߥ��h(�W)��ܦ�(regular-expression)
					errorMsgs.add("�K�X: ���]�t���r1�ӭ^��j�g�r���B1�Ӥp�g�r���B6�ӼƦr");
				}

//				Integer job_status = null;
//				try {
//					job_status = new Integer(req.getParameter("job_status").trim());
//				} catch (NumberFormatException e) {
//					
//					errorMsgs.add("�b¾���A�ж�Ʀr.");
//				}
//				Integer job_status = new Integer(req.getParameter("job_status").trim());
//				if(job_status == null) {					
//					errorMsgs.add("�п�ܦb¾���A");
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
					req.setAttribute("empVO", empVO); // �t����J�榡���~��empVO����,�]�s�Jreq
					RequestDispatcher failureView = req.getRequestDispatcher("/back-end/emp/addEmp.jsp");
					failureView.forward(req, res);
					return; // �{�����_
				}

				/*************************** 2.�}�l�s�W��� ***************************************/
				EmpService empSvc = new EmpService();
				empVO = empSvc.addEmp(job, sal, ename, hiredate, eaccount, epassword, job_status);

				/*************************** 3.�s�W����,�ǳ����(Send the Success view) ***********/
				String url = "/back-end/emp/listAllEmp.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // �s�W���\�����listAllEmp.jsp
				successView.forward(req, res);

				/*************************** ��L�i�઺���~�B�z **********************************/
			} catch (Exception e) {
				errorMsgs.add(e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/emp/addEmp.jsp");
				failureView.forward(req, res);
			}
		}

if ("delete".equals(action)) { // �Ӧ�listAllEmp.jsp

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/*************************** 1.�����ШD�Ѽ� ***************************************/
				Integer empno = new Integer(req.getParameter("empno"));

				/*************************** 2.�}�l�R����� ***************************************/
				EmpService empSvc = new EmpService();
				empSvc.deleteEmp(empno);

				/*************************** 3.�R������,�ǳ����(Send the Success view) ***********/
				String url = "/back-end/emp/listAllEmp.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);// �R�����\��,���^�e�X�R�����ӷ�����
				successView.forward(req, res);

				/*************************** ��L�i�઺���~�B�z **********************************/
			} catch (Exception e) {
				errorMsgs.add("�R����ƥ���:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/emp/listAllEmp.jsp");
				failureView.forward(req, res);
			}
		}

	}
}
