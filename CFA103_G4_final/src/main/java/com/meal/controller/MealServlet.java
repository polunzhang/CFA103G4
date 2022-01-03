package com.meal.controller;

import java.io.*;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import com.meal.model.*;
import com.meal_pic.model.MealPicDAOJDBC;
import com.meal_pic.model.MealPicService;
import com.meal_pic.model.MealPicVO;
@MultipartConfig
public class MealServlet extends HttpServlet {
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
				req.setAttribute("meal_name", meal_name); // ��Ʈw���X��mealVO����,�s�Jreq
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
				Integer mealno = new Integer(req.getParameter("mealno"));

				/*************************** 2.�}�l�d�߸�� ****************************************/
				MealService mealSvc = new MealService();
				MealVO mealVO = mealSvc.getOneMeal(mealno);

				/*************************** 3.�d�ߧ���,�ǳ����(Send the Success view) ************/
				req.setAttribute("mealVO", mealVO); // ��Ʈw���X��empVO����,�s�Jreq
				String url = "/back-end/meal/update_meal_input.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);// ���\��� update_meal_input.jsp
				successView.forward(req, res);

				/*************************** ��L�i�઺���~�B�z **********************************/
			} catch (Exception e) {
				errorMsgs.add("�L�k���o�n�ק諸���:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/meal/listAllMeal.jsp");
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

				Integer mealno = new Integer(req.getParameter("mealno").trim());

				String meal_name = req.getParameter("mealname");
				String meal_nameReg = "^[(\u4e00-\u9fa5)(_)]{1,15}$";
				if (meal_name == null || meal_name.trim().length() == 0) {
					errorMsgs.add("�\�I�W��: �ФŪť�");
				} else if (!meal_name.trim().matches(meal_nameReg)) { // �H�U�m�ߥ��h(�W)��ܦ�(regular-expression)
					errorMsgs.add("�\�I�W��: �u��O����r���M_ , �B���ץ��ݦb1��15����");
				}

				Integer meal_type_no = new Integer(req.getParameter("mealtypeno").trim());

				Integer meal_price = null;
				try {
					meal_price = new Integer(req.getParameter("mealprice").trim());
					if (meal_price % 1 != 0 || meal_price <= 0 || meal_price == null) {
						errorMsgs.add("�\�I����: �п�J�����");
					}
				} catch (NumberFormatException e) {
					meal_price = 0;
					errorMsgs.add("�\�I����ж�Ʀr.");
				}

				String meal_intro = req.getParameter("mealintro").trim();
				if (meal_intro == null || meal_intro.trim().length() == 0) {
					errorMsgs.add("�\�I���нФŪť�");
				}

				MealVO mealVO = new MealVO();
				mealVO.setMeal_type_no(meal_type_no);
				mealVO.setMeal_price(meal_price);
				mealVO.setMeal_name(meal_name);
				mealVO.setMeal_intro(meal_intro);
				mealVO.setMealno(mealno);

				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("mealVO", mealVO); // �t����J�榡���~��mealVO����,�]�s�Jreq
					RequestDispatcher failureView = req.getRequestDispatcher("/back-end/meal/update_meal_input.jsp");
					failureView.forward(req, res);
					return; // �{�����_
				}
				/*���o�q�e�x�e�Ӫ�part"updatemealfile"�A*/
				Part part = req.getPart("updatemealfile");
				
				if(part.getSize() > 0) {
				InputStream in = part.getInputStream();
				byte[] meal_pic = new byte[in.available()];
				in.read(meal_pic);
				in.close();
				MealPicDAOJDBC dao = new MealPicDAOJDBC();
				MealPicService mealPicSvc = new MealPicService();
				mealPicSvc.updateMealPicByMealNo(mealno,meal_pic);
			}
				
				/*************************** 2.�}�l�ק��� *****************************************/
				MealService mealSvc = new MealService();
				mealVO = mealSvc.updateMeal(meal_type_no, meal_price, meal_name, meal_intro, mealno);

				/*************************** 3.�ק粒��,�ǳ����(Send the Success view) *************/
				req.setAttribute("mealVO", mealVO); // ��Ʈwupdate���\��,���T����mealVO����,�s�Jreq
				String url = "/back-end/meal/listOneMeal.jsp";
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

				Integer meal_type_no = new Integer(req.getParameter("meal_type_no").trim());

				Integer meal_price = null;
				try {
					meal_price = new Integer(req.getParameter("meal_price").trim());
					if (meal_price % 1 != 0 || meal_price <= 0 || meal_price == null) {
						errorMsgs.add("�\�I����: �п�J�����");
					}
				} catch (NumberFormatException e) {
					meal_price = 0;
					errorMsgs.add("�\�I����ж�Ʀr.");
				}

				String meal_name = req.getParameter("meal_name");
				String meal_nameReg = "^[(\u4e00-\u9fa5)(a-zA-Z0-9_)]{2,10}$";
				if (meal_name == null || meal_name.trim().length() == 0) {
					errorMsgs.add("�\�I�W��: �ФŪť�");
				} else if (!meal_name.trim().matches(meal_nameReg)) { // �H�U�m�ߥ��h(�W)��ܦ�(regular-expression)
					errorMsgs.add("�\�I�W��: �u��O���B�^��r���B�Ʀr�M_ , �B���ץ��ݦb2��10����");
				}

				String meal_intro = req.getParameter("meal_intro").trim();
				if (meal_intro == null || meal_intro.trim().length() == 0) {
					errorMsgs.add("�\�I���нФŪť�");
				}
				
				/*���o�q�e�x�e�Ӫ�part"upmealfile"�A*/
				Part part = req.getPart("upmealfile");
//				System.out.println(part.getSize());
				InputStream in = part.getInputStream();
				byte[] meal_pic = new byte[in.available()];
				in.read(meal_pic);
				in.close();
//				System.out.println(part);
				/*���~�d�I*/
				if(part.getSize() <= 0) {
					errorMsgs.add("�|���W�ǹϤ�!");		
				}
				MealService mealSvc = new MealService();
				
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("MealService", mealSvc); // �t����J�榡���~��mealVO����,�]�s�Jreq
					RequestDispatcher failureView = req.getRequestDispatcher("/back-end/meal/addMeal.jsp");
					failureView.forward(req, res);
					return;
				}
				
				/*************************** 2.�}�l�s�W��� ***************************************/
				mealSvc.addMeal1(meal_type_no,meal_price,meal_name,meal_intro,meal_pic);
						
				/*************************** 3.�s�W����,�ǳ����(Send the Success view) ***********/
				String url = "/back-end/meal/listAllMeal.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // �s�W���\�����listAllMeal.jsp
				successView.forward(req, res);
				
				/*************************** ��L�i�઺���~�B�z **********************************/
			} catch (Exception e) {
				errorMsgs.add(e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/meal/addMeal.jsp");
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
				Integer mealno = new Integer(req.getParameter("mealno"));

				/*************************** 2.�}�l�R����� ***************************************/
				MealService mealSvc = new MealService();
				mealSvc.deleteMealandpic(mealno);

				/*************************** 3.�R������,�ǳ����(Send the Success view) ***********/
				String url = "/back-end/meal/listAllMeal.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);// �R�����\��,���^�e�X�R�����ӷ�����
				successView.forward(req, res);

				/*************************** ��L�i�઺���~�B�z **********************************/
			} catch (Exception e) {
				errorMsgs.add("�R����ƥ���:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/meal/listAllMeal.jsp");
				failureView.forward(req, res);
			}
		}
	}

}
