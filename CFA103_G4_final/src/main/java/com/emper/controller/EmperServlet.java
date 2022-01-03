package com.emper.controller;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.*;
import javax.servlet.http.*;

import com.emper.model.EmperService;
import com.emper.model.EmperVO;



public class EmperServlet extends HttpServlet{

	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");
		
if ("getOne_For_Update".equals(action)) { // �Ӧ�select_page_emper.jsp���ШD

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/*************************** 1.�����ШD�Ѽ� ****************************************/
				Integer emperno = new Integer(req.getParameter("emperno"));

				/*************************** 2.�}�l�d�߸�� ****************************************/
				EmperService emperSvc = new EmperService();
				EmperVO emperVO = emperSvc.getOneEmper(emperno);

				/*************************** 3.�d�ߧ���,�ǳ����(Send the Success view) ************/
				req.setAttribute("emperVO", emperVO); // ��Ʈw���X��emperVO����,�s�Jreq
				String url = "/back-end/emper/updateEmper_input.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);// ���\��� updateEmper_input.jsp
				successView.forward(req, res);

				/*************************** ��L�i�઺���~�B�z **********************************/
			} catch (Exception e) {
				errorMsgs.add("�L�k���o�n�ק諸���:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/emper/select_page_emper.jsp");
				failureView.forward(req, res);
			}
		}

if ("update".equals(action)) { // �Ӧ�updateEmper_input.jsp���ШD

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/*************************** 1.�����ШD�Ѽ� - ��J�榡�����~�B�z **********************/
				Integer emperno = new Integer(req.getParameter("emperno").trim());

				String emper_name = req.getParameter("emper_name");
				String emper_nameReg = "^[\u4e00-\u9fa5]{2,10}$";
				if (emper_name == null || emper_name.trim().length() == 0) {
					errorMsgs.add("�v���W�ٽФŪť�");
				} else if (!emper_name.trim().matches(emper_nameReg)) { // �H�U�m�ߥ��h(�W)��ܦ�(regular-expression)
					errorMsgs.add("�v���W��: �u��O����, �B���ץ��ݦb2��10����");
				}

				
				EmperVO emperVO = new EmperVO();
				emperVO.setEmperno(emperno);
				emperVO.setEmper_name(emper_name);
				

				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("emperVO", emperVO); // �t����J�榡���~��empVO����,�]�s�Jreq
					RequestDispatcher failureView = req.getRequestDispatcher("/back-end/emper/updateEmper_input.jsp");
					failureView.forward(req, res);
					return; // �{�����_
				}

				/*************************** 2.�}�l�ק��� *****************************************/
				EmperService emperSvc = new EmperService();
				emperVO = emperSvc.updateEmper(emperno, emper_name);

				/*************************** 3.�ק粒��,�ǳ����(Send the Success view) *************/
				req.setAttribute("emperVO", emperVO); // ��Ʈwupdate���\��,���T����emperVO����,�s�Jreq
				String url = "/back-end/emper/select_page_emper.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // �ק令�\��,���select_page_emper.jsp
				successView.forward(req, res);

				/*************************** ��L�i�઺���~�B�z *************************************/
			} catch (Exception e) {
				errorMsgs.add("�ק��ƥ���:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/emper/updateEmper_input.jsp");
				failureView.forward(req, res);
			}
		}

if ("insert".equals(action)) { // �Ӧ�addEmper.jsp���ШD

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/*********************** 1.�����ШD�Ѽ� - ��J�榡�����~�B�z *************************/
				
				String emper_name = req.getParameter("emper_name");
				String emper_nameReg = "^[\u4e00-\u9fa5]{2,10}$";
				if (emper_name == null || emper_name.trim().length() == 0) {
					errorMsgs.add("�v���W�ٽФŪť�");
				} else if (!emper_name.trim().matches(emper_nameReg)) { // �H�U�m�ߥ��h(�W)��ܦ�(regular-expression)
					errorMsgs.add("�v���W��: �u��O����, �B���ץ��ݦb2��10����");
				}

				EmperVO emperVO = new EmperVO();
				emperVO.setEmper_name(emper_name);

				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("emperVO", emperVO); // �t����J�榡���~��empVO����,�]�s�Jreq
					RequestDispatcher failureView = req.getRequestDispatcher("/back-end/emper/addEmper.jsp");
					failureView.forward(req, res);
					return; // �{�����_
				}

				/*************************** 2.�}�l�s�W��� ***************************************/
				EmperService emperSvc = new EmperService();
				emperVO = emperSvc.addEmper(emper_name);

				/*************************** 3.�s�W����,�ǳ����(Send the Success view) ***********/
				String url = "/back-end/emper/select_page_emper.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // �s�W���\�����select_page_emper.jsp
				successView.forward(req, res);

				/*************************** ��L�i�઺���~�B�z **********************************/
			} catch (Exception e) {
				errorMsgs.add(e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/emper/addEmper.jsp");
				failureView.forward(req, res);
			}
		}

if ("delete".equals(action)) { // �Ӧ�select_page_emper.jsp

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/*************************** 1.�����ШD�Ѽ� ***************************************/
				Integer emperno = new Integer(req.getParameter("emperno"));

				/*************************** 2.�}�l�R����� ***************************************/
				EmperService emperSvc = new EmperService();
				emperSvc.deleteEmper(emperno);

				/*************************** 3.�R������,�ǳ����(Send the Success view) ***********/
				String url = "/back-end/emper/select_page_emper.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);// �R�����\��,���^�e�X�R�����ӷ�����
				successView.forward(req, res);

				/*************************** ��L�i�઺���~�B�z **********************************/
			} catch (Exception e) {
				errorMsgs.add("�R����ƥ���:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/emper/select_page_emper.jsp");
				failureView.forward(req, res);
			}
		}

	}
	
}
