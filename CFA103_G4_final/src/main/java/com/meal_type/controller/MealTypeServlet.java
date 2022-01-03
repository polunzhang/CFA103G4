package com.meal_type.controller;

import java.util.*;
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import com.meal_type.model.*;


public class MealTypeServlet extends HttpServlet {
       
	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		doPost(req, res);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {

		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");

		
		if("getOne_For_Update".equals(action)) { // �Ӧ�listAllMealType.jsp���ШD
			
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
			
			try {
				/***************************1.�����ШD�Ѽ�****************************************/
				Integer meal_type_no = new Integer(req.getParameter("meal_type_no"));
				
				/***************************2.�}�l�d�߸��****************************************/
				MealTypeService mealTypeSvc = new MealTypeService();
				MealTypeVO mealTypeVO = mealTypeSvc.findByPrimaryKey(meal_type_no);
				
				/***************************3.�d�ߧ���,�ǳ����(Send the Success view)************/
				req.setAttribute("mealTypeVO", mealTypeVO);// ��Ʈw���X��mealTypeVO����,�s�Jreq
				String url = "/back-end/meal_type/update_meal_type_input.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // ���\��� listOneMealType.jsp
				successView.forward(req, res);
				
				/***************************��L�i�઺���~�B�z**********************************/
			} catch(Exception e) {
				errorMsgs.add("�L�k���o�n�ק諸���:" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/back-end/meal_type/select_page_meal_type.jsp");
				failureView.forward(req, res);
			}
		}
		
		if("update".equals(action)) {// �Ӧ�update_meal_type_input.jsp���ШD
			
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
			
			try {
				/***************************1.�����ШD�Ѽ� - ��J�榡�����~�B�z**********************/
				Integer meal_type_no = new Integer(req.getParameter("meal_type_no").trim());
				
				String meal_type_name = req.getParameter("meal_type_name").trim();
				String meal_type_nameReg = "^[(\\u4e00-\\u9fa5)(a-zA-Z_)]{1,5}$";
				if(meal_type_name == null || meal_type_name.trim().length() == 0) {
					errorMsgs.add("�\�I�����W�ٽФŪť�");
				} else if(!meal_type_name.trim().matches(meal_type_nameReg)) {
					errorMsgs.add("�\�I�����W�٥u��O���B�^��r���M_�A�B���ץ����b1��5����");
				}
				
				MealTypeVO mealTypeVO = new MealTypeVO();
				mealTypeVO.setMeal_type_no(meal_type_no);
				mealTypeVO.setMeal_type_name(meal_type_name);
				
				// Send the use back to the form, if there were errors
				if(!errorMsgs.isEmpty()) {
					req.setAttribute("mealTypeVO", mealTypeVO);// �t����J�榡���~��mealTypeVO����,�]�s�Jreq
					RequestDispatcher failureView = req
							.getRequestDispatcher("/back-end/meal_type/update_meal_type_input.jsp");
					failureView.forward(req, res);
					return; //�{�����_
				}
				
				/***************************2.�}�l�ק���*****************************************/
				MealTypeService mealTypeSvc = new MealTypeService();
				mealTypeVO = mealTypeSvc.updateMealType(meal_type_no, meal_type_name);
				
				/***************************3.�ק粒��,�ǳ����(Send the Success view)*************/
				req.setAttribute("mealTypeVO", mealTypeVO);// ��Ʈwupdate���\��,���T����mealTypeVO����,�s�Jreq
				String url = "/back-end/meal_type/select_page_meal_type.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // �ק令�\��,���select_page_meal_type.jsp
				successView.forward(req, res);
				
				/***************************��L�i�઺���~�B�z*************************************/
			} catch(Exception e) {
				errorMsgs.add("�ק��ƥ���:"+e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/back-end/meal_type/update_meal_type_input.jsp");
				failureView.forward(req, res);
			}
		}
		
		if("insert".equals(action)) {// �Ӧ�meal_select_page.jsp���ШD
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
			
			try {
				/***********************1.�����ШD�Ѽ� - ��J�榡�����~�B�z*************************/
				
				String meal_type_name = req.getParameter("mealtype");
				String meal_type_nameReg = "^[(\u4e00-\u9fa5)(a-zA-Z_)]{1,5}$";
				if (meal_type_name == null || meal_type_name.trim().length() == 0) {
					errorMsgs.add("�\�I�����W�ٽФŪť�");
				} else if(!meal_type_name.trim().matches(meal_type_nameReg)) {
					errorMsgs.add("�\�I�����W��: �u��O���B�^��r���M_ , �B���ץ��ݦb1��5����");
				}
				
				MealTypeVO mealTypeVO = new MealTypeVO();
//				mealTypeVO.setMeal_type_no(meal_type_no);  //meal_type_no�O�۰ʲ��ͪ��A�]�����ݭn�h�s�W���C
				mealTypeVO.setMeal_type_name(meal_type_name);
				
				// Send the use back to the form, if there were errors
				if(!errorMsgs.isEmpty()) {
					req.setAttribute("mealTypeVO", mealTypeVO);// �t����J�榡���~��mealTypeVO����,�]�s�Jreq
					RequestDispatcher failureView = req
							.getRequestDispatcher("/back-end/meal/select_page.jsp");
					failureView.forward(req, res);
					return; //�{�����_
				}
				
				/***************************2.�}�l�s�W���*****************************************/
				MealTypeService mealTypeSvc = new MealTypeService();
				mealTypeVO = mealTypeSvc.addMealType(meal_type_name);
				
				/***************************3.�s�W����,�ǳ����(Send the Success view)***********/
				String url = "/back-end/meal/select_page.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // �s�W���\�����listAllOnlineOrder.jsp
				successView.forward(req, res);
				
			} catch(Exception e) {
				errorMsgs.add(e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/back-end/meal/select_page.jsp");
				failureView.forward(req, res);
			}
		}
		
		if("delete".equals(action)) {// �Ӧ�select_page_meal_type.jsp
			
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
			
			try {
				/***************************1.�����ШD�Ѽ�***************************************/
				Integer meal_type_no = new Integer(req.getParameter("meal_type_no").trim());
				
				/***************************2.�}�l�R�����***************************************/
				MealTypeService mealTypeSvc = new MealTypeService();
				mealTypeSvc.deleteMealType(meal_type_no);
				
				/***************************3.�R������,�ǳ����(Send the Success view)***********/
				String url = "/back-end/meal_type/select_page_meal_type.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);// �R�����\��,���^�e�X�R�����ӷ�����
				successView.forward(req, res);
				
				/***************************��L�i�઺���~�B�z**********************************/
			} catch(Exception e) {
				errorMsgs.add("�R����ƥ���:"+e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/back-end/meal_type/select_page_meal_type.jsp");
				failureView.forward(req, res);
			}
			
		}
		
	}

}
