package com.prom.controller;

import java.io.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import com.meal.model.*;
import com.meal_pic.model.MealPicDAOJDBC;
import com.meal_pic.model.MealPicService;
import com.meal_pic.model.MealPicVO;
import com.prom.model.PromJDBCDAO;
import com.prom.model.PromService;
import com.prom.model.PromVO;
import com.prom_detail.model.PromDetailJDBCDAO;
import com.prom_detail.model.PromDetailService;
import com.prom_detail.model.PromDetailVO;
@MultipartConfig
public class PromServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");

		if ("getOne_For_Display".equals(action)) { // �Ӧ�select_page.jsp���ШD

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/*************************** 1.�����ШD�Ѽ� - ��J�榡�����~�B�z **********************/
				String str = req.getParameter("mealno");
				if (str == null || (str.trim()).length() == 0 ) {
					errorMsgs.add("����J�s���A�п�J�\�I�s��");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/back-end/meal/select_page.jsp");
					failureView.forward(req, res);
					return;// �{�����_
				}

				Integer mealno = null;
				try {
					mealno = new Integer(str);
					if (mealno <= 0 ) {
						errorMsgs.add("�\�I�s���d����~�A�п�J����ơC");}
				} catch (Exception e) {
					errorMsgs.add("�\�I�s���榡�����T");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/back-end/meal/select_page.jsp");
					failureView.forward(req, res);
					return;// �{�����_
				}
				/*************************** 2.�}�l�d�߸�� *****************************************/
				MealService mealSvc = new MealService();
				MealVO mealVO = mealSvc.getOneMeal(mealno);
				if (mealVO == null) {
					errorMsgs.add("�d�L���");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/back-end/meal/select_page.jsp");
					failureView.forward(req, res);
					return;// �{�����_
				}

				/*************************** 3.�d�ߧ���,�ǳ����(Send the Success view) *************/
				req.setAttribute("mealVO", mealVO); // ��Ʈw���X��mealVO����,�s�Jreq
				String url = "/back-end/meal/listOneMeal.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // ���\��� listOneMeal.jsp
				successView.forward(req, res);

				/*************************** ��L�i�઺���~�B�z *************************************/
			} catch (Exception e) {
				errorMsgs.add("�L�k���o���:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/meal/select_page.jsp");
				failureView.forward(req, res);
			}
		}

		if ("getOne_For_Display_Byname".equals(action)) { // �Ӧ�select_page.jsp���ШD

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/*************************** 1.�����ШD�Ѽ� - ��J�榡�����~�B�z **********************/
				String str = new String(req.getParameter("mealname"));
				String meal_nameReg = "^[(\u4e00-\u9fa5)(_)]{1,15}$";
				
				if (str == null || (str.trim()).length() == 0) {
					errorMsgs.add("����J�\�I�W�A�п�J�\�I�W");
					
				} else if (str == null || str.trim().length() == 0) {
					errorMsgs.add("�\�I�W��: �ФŪť�");
					
				} else if (!str.trim().matches(meal_nameReg)) { // �H�U�m�ߥ��h(�W)��ܦ�(regular-expression)
					errorMsgs.add("�\�I�W��: �u��O����r���M_ , �B���ץ��ݦb1��15����");
				}
				
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/back-end/meal/select_page.jsp");
					failureView.forward(req, res);
					return;// �{�����_
				}
				
				String meal_name = null;	
				try {
					meal_name = new String(str);
				} catch (Exception e) {
					errorMsgs.add("�\�I�W�ٮ榡�����T");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/back-end/meal/select_page.jsp");
					failureView.forward(req, res);
					return;// �{�����_
				}
				/*************************** 2.�}�l�d�߸�� *****************************************/
				MealService mealSvc = new MealService();
//				System.out.println("mealname ="+ meal_name);
				List<MealVO> mealVO = mealSvc.getMealByName(meal_name);
//				System.out.println("mealVO ="+ mealVO.size());
				int mealVOANS = mealVO.size();
				if (mealVOANS <= 0 ) {
					errorMsgs.add("�d�L���");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/back-end/meal/select_page.jsp");
					failureView.forward(req, res);
					return;// �{�����_
				}

				/*************************** 3.�d�ߧ���,�ǳ����(Send the Success view) *************/
				req.setAttribute("mealVO", mealVO); // ��Ʈw���X��mealVO����,�s�Jreq
				String url = "/back-end/meal/listMealByName.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // ���\��� listMealByName.jsp
				successView.forward(req, res);

				/*************************** ��L�i�઺���~�B�z *************************************/
			} catch (Exception e) {
				errorMsgs.add("�L�k���o���:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/meal/select_page.jsp");
				failureView.forward(req, res);
			}

		}

		if ("getOne_Type_For_Display".equals(action)) { // �Ӧ�select_page.jsp���ШD

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/*************************** 1.�����ШD�Ѽ� - ��J�榡�����~�B�z **********************/
				Integer mtn = new Integer(req.getParameter("meal_type_no"));
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/back-end/meal/select_page.jsp");
					failureView.forward(req, res);
					return;// �{�����_
				}

				Integer meal_type_no = null;
				try {
					meal_type_no = new Integer(mtn);
				} catch (Exception e) {
					errorMsgs.add("���O�W�ٮ榡�����T");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/back-end/meal/select_page.jsp");
					failureView.forward(req, res);
					return;// �{�����_
				}
				/*************************** 2.�}�l�d�߸�� *****************************************/
				MealService mealSvc = new MealService();
				List<MealVO> mealVO = mealSvc.getOneMealType(meal_type_no);
				if (mealVO == null) {
					errorMsgs.add("�d�L���");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/back-end/meal/select_page.jsp");
					failureView.forward(req, res);
					return;// �{�����_
				}

				/*************************** 3.�d�ߧ���,�ǳ����(Send the Success view) *************/
				req.setAttribute("mealVO", mealVO); // ��Ʈw���X��mealVO����,�s�Jreq
				String url = "/back-end/meal/listMealByMealType.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // ���\��� listMealByName.jsp
				successView.forward(req, res);

				/*************************** ��L�i�઺���~�B�z *************************************/
			} catch (Exception e) {
				errorMsgs.add("�L�k���o���:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/meal/select_page.jsp");
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
				Integer promno = new Integer(req.getParameter("promno"));

				/*************************** 2.�}�l�d�߸�� ****************************************/
				PromService PmSvc = new PromService();
				PromDetailService PDSvc = new PromDetailService();
				
				PromVO promVO = PmSvc.getOneprom(promno);
				List<PromDetailVO> promdetailVO = PDSvc.getOnepromDetail(promno);
				
				PromDetailJDBCDAO PDdao = new PromDetailJDBCDAO();
				PromDetailVO pdVOLO = PDdao.findByPrimaryKeyLimitOne(promno);
//				System.out.println(pdVOLO.getProm_state().getClass().getSimpleName());
				
				

				/*************************** 3.�d�ߧ���,�ǳ����(Send the Success view) ************/
				req.setAttribute("promVO", promVO); // ��Ʈw���X��empVO����,�s�Jreq
				req.setAttribute("promdetailVO", promdetailVO); // ��Ʈw���X��empVO����,�s�Jreq
				req.setAttribute("pdVOLO", pdVOLO); // ��Ʈw���X��empVO����,�s�Jreq
				
				String url = "/back-end/prom/update_prom_input.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);// ���\��� update_meal_input.jsp
				successView.forward(req, res);

				/*************************** ��L�i�઺���~�B�z **********************************/
			} catch (Exception e) {
				errorMsgs.add("�L�k���o�n�ק諸���:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/prom/listAllProm.jsp");
				failureView.forward(req, res);
			}
		}

		if ("update".equals(action)) { // �Ӧ�update_meal_input.jsp���ШD

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/*************************** 1.�����ШD�Ѽ� - ��J�榡�����~�B�z **********************/

				Integer promno = new Integer(req.getParameter("promno"));
				Integer empno = new Integer(req.getParameter("empno"));
				
//				System.out.println(777777);				
				String prom_title = req.getParameter("prom_title").trim();
				if (prom_title == null || prom_title.trim().length() == 0) {
					errorMsgs.add("���ʦW�ٽФŪť�");
				}
//				System.out.println(66666);
				String prom_content = req.getParameter("prom_content").trim();
				if (prom_content == null || prom_content.trim().length() == 0) {
					errorMsgs.add("���ʤ��нФŪť�");
				}
//				System.out.println(5555555);
				
//				���o�q�e�x�e�Ӫ�part"upmealfile"
				Part part = req.getPart("uppromfile");
				System.out.println(part.getSize());
				byte[] prom_pic=null;
//				�P�_�O�_����s�Ϥ�
				if(part.getSize() > 0) {
					InputStream in = part.getInputStream();
					prom_pic = new byte[in.available()];
					in.read(prom_pic);
					in.close();
				}else if(part.getSize() <= 0) {
					PromJDBCDAO Pdao = new PromJDBCDAO();
					prom_pic=Pdao.findonepic(promno);
					
				}
//				System.out.println(000);
//				���o�馩���A
				Integer promState = new Integer(req.getParameter("promState").trim());
//				���ը��o��promState
//				System.out.println(promState);
//				���o�馩�ʤ���/���B
				Double promValues = new Double(req.getParameter("promValues").trim());
//				�B�z���o���Ʀr
				if(promState == 1) {
					promValues = (100-promValues)/100;
				}if(promState == 0) {
					promValues = Math.abs(promValues);
				}
//				System.out.println(789);
//				���ճB�z�᪺promValues
//				System.out.println(promValues);

//				���o���
				java.sql.Date promTimeStart = null;
				try {
					promTimeStart = java.sql.Date.valueOf(req.getParameter("promTimeStart").trim());
				} catch (IllegalArgumentException e) {
					promTimeStart=new java.sql.Date(System.currentTimeMillis());
					errorMsgs.add("�п�J�_�l���!");
				}
//				System.out.println(456);
//				���o���
				java.sql.Date promTimeEnd = null;
				try {
					promTimeEnd = java.sql.Date.valueOf(req.getParameter("promTimeEnd").trim());
				} catch (IllegalArgumentException e) {
					promTimeEnd=new java.sql.Date(System.currentTimeMillis());
					errorMsgs.add("�п�J�������!");
				}
				
				
//				System.out.println(123);
				/*************************** 2.�}�l�ק��� *****************************************/
//				���o��promo���Ҧ�mealno(�ϥ�getOnepromDetail(�Yfindbypk)�å�foreach�s�Jmealno)
				PromDetailService PDSvc = new PromDetailService();
				List<PromDetailVO> PromDetailVO = PDSvc.getOnepromDetail(promno); 
				
//				�Hforeach���Xprom����mealno�æs�J�t�@arraylist
				for(PromDetailVO promdetailvo : PromDetailVO) {
					PDSvc.updatePromDetail(promdetailvo.getMealno(),promValues,promTimeStart,promTimeEnd,promState,promno,promdetailvo.getMealno());
				}
				PromService PSvc = new PromService();
				PromVO PromVO = PSvc.updateProm(promno,empno,prom_title,prom_content,prom_pic);

				/*************************** 3.�ק粒��,�ǳ����(Send the Success view) *************/
				req.setAttribute("PromVO",PromVO ); // ��Ʈwupdate���\��,���T����mealVO����,�s�Jreq
				String url = "/back-end/prom/listAllProm.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // �ק令�\��,���listOneMeal.jsp
				successView.forward(req, res);

				/*************************** ��L�i�઺���~�B�z *************************************/
			} 
			catch (Exception e) {
				errorMsgs.add("�ק��ƥ���:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/meal/update_meal_input.jsp");
				failureView.forward(req, res);
			}
		}

		if ("insert".equals(action)) { // �Ӧ�addMeal.jsp���ШD

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/*********************** 1.�����ШD�Ѽ� - ��J�榡�����~�B�z *************************/
				Integer empno = new Integer(req.getParameter("empno").trim());
//���~�B�z�|�ݾ�X����M
//				if (empno == null ) {
//					errorMsgs.add("�п�J���u�s��");
//				}
			
				String prom_title = req.getParameter("prom_title").trim();
				if (prom_title == null || prom_title.trim().length() == 0) {
					errorMsgs.add("���ʦW�ٽФŪť�");
				}
				
				String prom_content = req.getParameter("prom_content").trim();
				if (prom_content == null || prom_content.trim().length() == 0) {
					errorMsgs.add("���ʤ��нФŪť�");
				}
			
				
				/*���o�q�e�x�e�Ӫ�part"upmealfile"�A*/
				Part part = req.getPart("uppromfile");
//				System.out.println(part.getSize());
				InputStream in = part.getInputStream();
				byte[] prom_pic = new byte[in.available()];
				in.read(prom_pic);
				in.close();
//				System.out.println(part);
				/*���~�d�I*/
				if(part.getSize() <= 0) {
					errorMsgs.add("�|���W�ǹϤ�!");		
				}
				PromService promSvc = new PromService();
				
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/back-end/prom/addProm.jsp");
					failureView.forward(req, res);
					return;
				}
				
				/*************************** 2.�}�l�s�W��� ***************************************/
				PromVO promvoo = promSvc.addProm(empno,prom_title,prom_content,prom_pic);
				System.out.println("servlet���o"+promvoo.getPromno());
				HttpSession session = req.getSession();
				session.setAttribute("PROMVO", promvoo);
				/*************************** 3.�s�W����,�ǳ����(Send the Success view) ***********/
				String url = "/back-end/prom/addPromDetail.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // �s�W���\�����listAllMeal.jsp
				successView.forward(req, res);
				
				/*************************** ��L�i�઺���~�B�z **********************************/
			} catch (Exception e) {
				errorMsgs.add(e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/prom/addProm.jsp");
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
				Integer promno = new Integer(req.getParameter("promno"));

				/*************************** 2.�}�l�R����� ***************************************/
				PromJDBCDAO pddao = new PromJDBCDAO();
				pddao.delete(promno);

				/*************************** 3.�R������,�ǳ����(Send the Success view) ***********/
				String url = "/back-end/prom/listAllProm.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);// �R�����\��,���^�e�X�R�����ӷ�����
				successView.forward(req, res);

				/*************************** ��L�i�઺���~�B�z **********************************/
			} catch (Exception e) {
				errorMsgs.add("�R����ƥ���:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/prom/listAllProm.jsp");
				failureView.forward(req, res);
			}
		}
	}

}
