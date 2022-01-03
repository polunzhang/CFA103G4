package com.emperDetail.controller;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.emp.model.EmpService;
import com.emp.model.EmpVO;
import com.emperDetail.model.EmperDetailService;
import com.emperDetail.model.EmperDetailVO;

public class EmperDetailServlet extends HttpServlet {

	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");

if ("getEmpno_For_Display".equals(action)) { // �Ӧ�select_page_emper_detail.jsp���ШD

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
					RequestDispatcher failureView = req
							.getRequestDispatcher("/back-end/emperDetail/select_page_emper_detail.jsp");
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
					RequestDispatcher failureView = req
							.getRequestDispatcher("/back-end/emperDetail/select_page_emper_detail.jsp");
					failureView.forward(req, res);
					return;// �{�����_
				}

				/*************************** 2.�}�l�d�߸�� *****************************************/
				EmperDetailService emperDetailSvc = new EmperDetailService();
				List<EmperDetailVO> list = emperDetailSvc.getByEmpno(empno);
				if (list == null) {
					errorMsgs.add("�d�L���");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/back-end/emperDetail/select_page_emper_detail.jsp");
					failureView.forward(req, res);
					return;// �{�����_
				}

				/*************************** 3.�d�ߧ���,�ǳ����(Send the Success view) *************/
				req.setAttribute("list", list); // ��Ʈw���X��list����,�s�Jreq
				String url = "/back-end/emperDetail/listOneEmperDetail.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // ���\��� listOneEmperDetail.jsp
				successView.forward(req, res);

				/*************************** ��L�i�઺���~�B�z *************************************/
			} catch (Exception e) {
				errorMsgs.add("�L�k���o���:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/emperDetail/select_page_emper_detail.jsp");
				failureView.forward(req, res);
			}
		}

if ("getEmperno_For_Display".equals(action)) { // �Ӧ�select_page_emper_detail.jsp���ШD

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/*************************** 1.�����ШD�Ѽ� - ��J�榡�����~�B�z **********************/
				String str = req.getParameter("emperno");
				if (str == null || (str.trim()).length() == 0) {
					errorMsgs.add("�п�J�v���s��");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/back-end/emperDetail" + "/select_page_emper_detail.jsp");
					failureView.forward(req, res);
					return;// �{�����_
				}

				Integer emperno = null;
				try {
					emperno = new Integer(str);
				} catch (Exception e) {
					errorMsgs.add("�v���s���榡�����T");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/back-end/emperDetail/select_page_emper_detail.jsp");
					failureView.forward(req, res);
					return;// �{�����_
				}

				/*************************** 2.�}�l�d�߸�� *****************************************/
				EmperDetailService emperDetailSvc = new EmperDetailService();
				List<EmperDetailVO> list = emperDetailSvc.getByEmperno(emperno);
				if (list == null) {
					errorMsgs.add("�d�L���");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/back-end/emperDetail/select_page_emper_detail.jsp");
					failureView.forward(req, res);
					return;// �{�����_
				}

				/*************************** 3.�d�ߧ���,�ǳ����(Send the Success view) *************/
				req.setAttribute("list", list); // ��Ʈw���X��list����,�s�Jreq
				String url = "/back-end/emperDetail/listOneEmperDetail.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // ���\��� listOneEmperDetail.jsp
				successView.forward(req, res);

				/*************************** ��L�i�઺���~�B�z *************************************/
			} catch (Exception e) {
				errorMsgs.add("�L�k���o���:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/emperDetail/select_page_emper_detail.jsp");
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

				Integer empno = new Integer(req.getParameter("empno"));
				Integer emperno = new Integer(req.getParameter("emperno"));
				
				System.out.println(empno + "" + emperno);

				EmperDetailVO emperDetailVO = new EmperDetailVO();
				emperDetailVO.setEmpno(empno);
				emperDetailVO.setEmperno(emperno);


				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("emperDetailVO", emperDetailVO); // �t����J�榡���~��empVO����,�]�s�Jreq
					RequestDispatcher failureView = req.getRequestDispatcher("/back-end/emperDetail/addEmperDetail.jsp");
					failureView.forward(req, res);
					return; // �{�����_
				}

				/*************************** 2.�}�l�s�W��� ***************************************/
				EmperDetailService emperDetailSvc = new EmperDetailService();
				emperDetailVO = emperDetailSvc.addEmperDetail(emperno, empno);

				/*************************** 3.�s�W����,�ǳ����(Send the Success view) ***********/
				String url = "/back-end/emperDetail/listAllEmperDetail.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // �s�W���\�����listAllEmp.jsp
				successView.forward(req, res);

				/*************************** ��L�i�઺���~�B�z **********************************/
			} catch (Exception e) {
				errorMsgs.add(e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/emperDetail/addEmperDetail.jsp");
				failureView.forward(req, res);
			}
		}

if ("delete".equals(action)) { // �Ӧ�listAllEmperDetail.jsp

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/*************************** 1.�����ШD�Ѽ� ***************************************/
				Integer empno = new Integer(req.getParameter("empno"));
				Integer emperno = new Integer(req.getParameter("emperno"));

				/*************************** 2.�}�l�R����� ***************************************/
				EmperDetailService emperDetailSvc = new EmperDetailService();
				emperDetailSvc.deleteEmperDetail(empno,emperno);

				/*************************** 3.�R������,�ǳ����(Send the Success view) ***********/
				String url = "/back-end/emperDetail/listAllEmperDetail.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);// �R�����\��,���^�e�X�R�����ӷ�����
				successView.forward(req, res);

				/*************************** ��L�i�઺���~�B�z **********************************/
			} catch (Exception e) {
				errorMsgs.add("�R����ƥ���:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/emperDetail/listAllEmperDetail.jsp");
				failureView.forward(req, res);
			}
		}

	}

}
