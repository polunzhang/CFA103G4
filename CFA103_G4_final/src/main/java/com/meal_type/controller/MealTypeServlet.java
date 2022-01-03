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

		
		if("getOne_For_Update".equals(action)) { // 來自listAllMealType.jsp的請求
			
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
			
			try {
				/***************************1.接收請求參數****************************************/
				Integer meal_type_no = new Integer(req.getParameter("meal_type_no"));
				
				/***************************2.開始查詢資料****************************************/
				MealTypeService mealTypeSvc = new MealTypeService();
				MealTypeVO mealTypeVO = mealTypeSvc.findByPrimaryKey(meal_type_no);
				
				/***************************3.查詢完成,準備轉交(Send the Success view)************/
				req.setAttribute("mealTypeVO", mealTypeVO);// 資料庫取出的mealTypeVO物件,存入req
				String url = "/back-end/meal_type/update_meal_type_input.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 成功轉交 listOneMealType.jsp
				successView.forward(req, res);
				
				/***************************其他可能的錯誤處理**********************************/
			} catch(Exception e) {
				errorMsgs.add("無法取得要修改的資料:" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/back-end/meal_type/select_page_meal_type.jsp");
				failureView.forward(req, res);
			}
		}
		
		if("update".equals(action)) {// 來自update_meal_type_input.jsp的請求
			
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
			
			try {
				/***************************1.接收請求參數 - 輸入格式的錯誤處理**********************/
				Integer meal_type_no = new Integer(req.getParameter("meal_type_no").trim());
				
				String meal_type_name = req.getParameter("meal_type_name").trim();
				String meal_type_nameReg = "^[(\\u4e00-\\u9fa5)(a-zA-Z_)]{1,5}$";
				if(meal_type_name == null || meal_type_name.trim().length() == 0) {
					errorMsgs.add("餐點分類名稱請勿空白");
				} else if(!meal_type_name.trim().matches(meal_type_nameReg)) {
					errorMsgs.add("餐點分類名稱只能是中、英文字母和_，且長度必須在1到5之間");
				}
				
				MealTypeVO mealTypeVO = new MealTypeVO();
				mealTypeVO.setMeal_type_no(meal_type_no);
				mealTypeVO.setMeal_type_name(meal_type_name);
				
				// Send the use back to the form, if there were errors
				if(!errorMsgs.isEmpty()) {
					req.setAttribute("mealTypeVO", mealTypeVO);// 含有輸入格式錯誤的mealTypeVO物件,也存入req
					RequestDispatcher failureView = req
							.getRequestDispatcher("/back-end/meal_type/update_meal_type_input.jsp");
					failureView.forward(req, res);
					return; //程式中斷
				}
				
				/***************************2.開始修改資料*****************************************/
				MealTypeService mealTypeSvc = new MealTypeService();
				mealTypeVO = mealTypeSvc.updateMealType(meal_type_no, meal_type_name);
				
				/***************************3.修改完成,準備轉交(Send the Success view)*************/
				req.setAttribute("mealTypeVO", mealTypeVO);// 資料庫update成功後,正確的的mealTypeVO物件,存入req
				String url = "/back-end/meal_type/select_page_meal_type.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 修改成功後,轉交select_page_meal_type.jsp
				successView.forward(req, res);
				
				/***************************其他可能的錯誤處理*************************************/
			} catch(Exception e) {
				errorMsgs.add("修改資料失敗:"+e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/back-end/meal_type/update_meal_type_input.jsp");
				failureView.forward(req, res);
			}
		}
		
		if("insert".equals(action)) {// 來自meal_select_page.jsp的請求
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
			
			try {
				/***********************1.接收請求參數 - 輸入格式的錯誤處理*************************/
				
				String meal_type_name = req.getParameter("mealtype");
				String meal_type_nameReg = "^[(\u4e00-\u9fa5)(a-zA-Z_)]{1,5}$";
				if (meal_type_name == null || meal_type_name.trim().length() == 0) {
					errorMsgs.add("餐點種類名稱請勿空白");
				} else if(!meal_type_name.trim().matches(meal_type_nameReg)) {
					errorMsgs.add("餐點分類名稱: 只能是中、英文字母和_ , 且長度必需在1到5之間");
				}
				
				MealTypeVO mealTypeVO = new MealTypeVO();
//				mealTypeVO.setMeal_type_no(meal_type_no);  //meal_type_no是自動產生的，因此不需要去新增它。
				mealTypeVO.setMeal_type_name(meal_type_name);
				
				// Send the use back to the form, if there were errors
				if(!errorMsgs.isEmpty()) {
					req.setAttribute("mealTypeVO", mealTypeVO);// 含有輸入格式錯誤的mealTypeVO物件,也存入req
					RequestDispatcher failureView = req
							.getRequestDispatcher("/back-end/meal/select_page.jsp");
					failureView.forward(req, res);
					return; //程式中斷
				}
				
				/***************************2.開始新增資料*****************************************/
				MealTypeService mealTypeSvc = new MealTypeService();
				mealTypeVO = mealTypeSvc.addMealType(meal_type_name);
				
				/***************************3.新增完成,準備轉交(Send the Success view)***********/
				String url = "/back-end/meal/select_page.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 新增成功後轉交listAllOnlineOrder.jsp
				successView.forward(req, res);
				
			} catch(Exception e) {
				errorMsgs.add(e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/back-end/meal/select_page.jsp");
				failureView.forward(req, res);
			}
		}
		
		if("delete".equals(action)) {// 來自select_page_meal_type.jsp
			
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
			
			try {
				/***************************1.接收請求參數***************************************/
				Integer meal_type_no = new Integer(req.getParameter("meal_type_no").trim());
				
				/***************************2.開始刪除資料***************************************/
				MealTypeService mealTypeSvc = new MealTypeService();
				mealTypeSvc.deleteMealType(meal_type_no);
				
				/***************************3.刪除完成,準備轉交(Send the Success view)***********/
				String url = "/back-end/meal_type/select_page_meal_type.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);// 刪除成功後,轉交回送出刪除的來源網頁
				successView.forward(req, res);
				
				/***************************其他可能的錯誤處理**********************************/
			} catch(Exception e) {
				errorMsgs.add("刪除資料失敗:"+e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/back-end/meal_type/select_page_meal_type.jsp");
				failureView.forward(req, res);
			}
			
		}
		
	}

}
