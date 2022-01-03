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

		if ("getOne_For_Display".equals(action)) { // 來自select_page.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 **********************/
				String str = req.getParameter("mealno");
				if (str == null || (str.trim()).length() == 0 ) {
					errorMsgs.add("未輸入編號，請輸入餐點編號");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/back-end/meal/select_page.jsp");
					failureView.forward(req, res);
					return;// 程式中斷
				}

				Integer mealno = null;
				try {
					mealno = new Integer(str);
					if (mealno <= 0 ) {
						errorMsgs.add("餐點編號範圍錯誤，請輸入正整數。");}
				} catch (Exception e) {
					errorMsgs.add("餐點編號格式不正確");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/back-end/meal/select_page.jsp");
					failureView.forward(req, res);
					return;// 程式中斷
				}
				/*************************** 2.開始查詢資料 *****************************************/
				MealService mealSvc = new MealService();
				MealVO mealVO = mealSvc.getOneMeal(mealno);
				if (mealVO == null) {
					errorMsgs.add("查無資料");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/back-end/meal/select_page.jsp");
					failureView.forward(req, res);
					return;// 程式中斷
				}

				/*************************** 3.查詢完成,準備轉交(Send the Success view) *************/
				req.setAttribute("mealVO", mealVO); // 資料庫取出的mealVO物件,存入req
				String url = "/back-end/meal/listOneMeal.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 成功轉交 listOneMeal.jsp
				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 *************************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得資料:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/meal/select_page.jsp");
				failureView.forward(req, res);
			}
		}

		if ("getOne_For_Display_Byname".equals(action)) { // 來自select_page.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 **********************/
				String str = new String(req.getParameter("mealname"));
				String meal_nameReg = "^[(\u4e00-\u9fa5)(_)]{1,15}$";
				
				if (str == null || (str.trim()).length() == 0) {
					errorMsgs.add("未輸入餐點名，請輸入餐點名");
					
				} else if (str == null || str.trim().length() == 0) {
					errorMsgs.add("餐點名稱: 請勿空白");
					
				} else if (!str.trim().matches(meal_nameReg)) { // 以下練習正則(規)表示式(regular-expression)
					errorMsgs.add("餐點名稱: 只能是中文字母和_ , 且長度必需在1到15之間");
				}
				
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/back-end/meal/select_page.jsp");
					failureView.forward(req, res);
					return;// 程式中斷
				}
				
				String meal_name = null;	
				try {
					meal_name = new String(str);
				} catch (Exception e) {
					errorMsgs.add("餐點名稱格式不正確");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/back-end/meal/select_page.jsp");
					failureView.forward(req, res);
					return;// 程式中斷
				}
				/*************************** 2.開始查詢資料 *****************************************/
				MealService mealSvc = new MealService();
//				System.out.println("mealname ="+ meal_name);
				List<MealVO> mealVO = mealSvc.getMealByName(meal_name);
//				System.out.println("mealVO ="+ mealVO.size());
				int mealVOANS = mealVO.size();
				if (mealVOANS <= 0 ) {
					errorMsgs.add("查無資料");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/back-end/meal/select_page.jsp");
					failureView.forward(req, res);
					return;// 程式中斷
				}

				/*************************** 3.查詢完成,準備轉交(Send the Success view) *************/
				req.setAttribute("mealVO", mealVO); // 資料庫取出的mealVO物件,存入req
				req.setAttribute("meal_name", meal_name); // 資料庫取出的mealVO物件,存入req
				String url = "/back-end/meal/listMealByName.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 成功轉交 listMealByName.jsp
				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 *************************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得資料:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/meal/select_page.jsp");
				failureView.forward(req, res);
			}

		}

		if ("getOne_Type_For_Display".equals(action)) { // 來自select_page.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 **********************/
				Integer mtn = new Integer(req.getParameter("meal_type_no"));
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/back-end/meal/select_page.jsp");
					failureView.forward(req, res);
					return;// 程式中斷
				}

				Integer meal_type_no = null;
				try {
					meal_type_no = new Integer(mtn);
				} catch (Exception e) {
					errorMsgs.add("類別名稱格式不正確");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/back-end/meal/select_page.jsp");
					failureView.forward(req, res);
					return;// 程式中斷
				}
				/*************************** 2.開始查詢資料 *****************************************/
				MealService mealSvc = new MealService();
				List<MealVO> mealVO = mealSvc.getOneMealType(meal_type_no);
				if (mealVO == null) {
					errorMsgs.add("查無資料");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/back-end/meal/select_page.jsp");
					failureView.forward(req, res);
					return;// 程式中斷
				}

				/*************************** 3.查詢完成,準備轉交(Send the Success view) *************/
				req.setAttribute("mealVO", mealVO); // 資料庫取出的mealVO物件,存入req
				String url = "/back-end/meal/listMealByMealType.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 成功轉交 listMealByName.jsp
				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 *************************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得資料:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/meal/select_page.jsp");
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
				Integer mealno = new Integer(req.getParameter("mealno"));

				/*************************** 2.開始查詢資料 ****************************************/
				MealService mealSvc = new MealService();
				MealVO mealVO = mealSvc.getOneMeal(mealno);

				/*************************** 3.查詢完成,準備轉交(Send the Success view) ************/
				req.setAttribute("mealVO", mealVO); // 資料庫取出的empVO物件,存入req
				String url = "/back-end/meal/update_meal_input.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);// 成功轉交 update_meal_input.jsp
				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 **********************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得要修改的資料:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/meal/listAllMeal.jsp");
				failureView.forward(req, res);
			}
		}

		if ("update".equals(action)) { // 來自update_meal_input.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 **********************/

				Integer mealno = new Integer(req.getParameter("mealno").trim());

				String meal_name = req.getParameter("mealname");
				String meal_nameReg = "^[(\u4e00-\u9fa5)(_)]{1,15}$";
				if (meal_name == null || meal_name.trim().length() == 0) {
					errorMsgs.add("餐點名稱: 請勿空白");
				} else if (!meal_name.trim().matches(meal_nameReg)) { // 以下練習正則(規)表示式(regular-expression)
					errorMsgs.add("餐點名稱: 只能是中文字母和_ , 且長度必需在1到15之間");
				}

				Integer meal_type_no = new Integer(req.getParameter("mealtypeno").trim());

				Integer meal_price = null;
				try {
					meal_price = new Integer(req.getParameter("mealprice").trim());
					if (meal_price % 1 != 0 || meal_price <= 0 || meal_price == null) {
						errorMsgs.add("餐點價格: 請輸入正整數");
					}
				} catch (NumberFormatException e) {
					meal_price = 0;
					errorMsgs.add("餐點價格請填數字.");
				}

				String meal_intro = req.getParameter("mealintro").trim();
				if (meal_intro == null || meal_intro.trim().length() == 0) {
					errorMsgs.add("餐點介紹請勿空白");
				}

				MealVO mealVO = new MealVO();
				mealVO.setMeal_type_no(meal_type_no);
				mealVO.setMeal_price(meal_price);
				mealVO.setMeal_name(meal_name);
				mealVO.setMeal_intro(meal_intro);
				mealVO.setMealno(mealno);

				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("mealVO", mealVO); // 含有輸入格式錯誤的mealVO物件,也存入req
					RequestDispatcher failureView = req.getRequestDispatcher("/back-end/meal/update_meal_input.jsp");
					failureView.forward(req, res);
					return; // 程式中斷
				}
				/*取得從前台送來的part"updatemealfile"，*/
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
				
				/*************************** 2.開始修改資料 *****************************************/
				MealService mealSvc = new MealService();
				mealVO = mealSvc.updateMeal(meal_type_no, meal_price, meal_name, meal_intro, mealno);

				/*************************** 3.修改完成,準備轉交(Send the Success view) *************/
				req.setAttribute("mealVO", mealVO); // 資料庫update成功後,正確的的mealVO物件,存入req
				String url = "/back-end/meal/listOneMeal.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 修改成功後,轉交listOneMeal.jsp
				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 *************************************/
			} 
			catch (Exception e) {
				errorMsgs.add("修改資料失敗:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/meal/update_meal_input.jsp");
				failureView.forward(req, res);
			}
		}

		if ("insert".equals(action)) { // 來自addMeal.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/*********************** 1.接收請求參數 - 輸入格式的錯誤處理 *************************/

				Integer meal_type_no = new Integer(req.getParameter("meal_type_no").trim());

				Integer meal_price = null;
				try {
					meal_price = new Integer(req.getParameter("meal_price").trim());
					if (meal_price % 1 != 0 || meal_price <= 0 || meal_price == null) {
						errorMsgs.add("餐點價格: 請輸入正整數");
					}
				} catch (NumberFormatException e) {
					meal_price = 0;
					errorMsgs.add("餐點價格請填數字.");
				}

				String meal_name = req.getParameter("meal_name");
				String meal_nameReg = "^[(\u4e00-\u9fa5)(a-zA-Z0-9_)]{2,10}$";
				if (meal_name == null || meal_name.trim().length() == 0) {
					errorMsgs.add("餐點名稱: 請勿空白");
				} else if (!meal_name.trim().matches(meal_nameReg)) { // 以下練習正則(規)表示式(regular-expression)
					errorMsgs.add("餐點名稱: 只能是中、英文字母、數字和_ , 且長度必需在2到10之間");
				}

				String meal_intro = req.getParameter("meal_intro").trim();
				if (meal_intro == null || meal_intro.trim().length() == 0) {
					errorMsgs.add("餐點介紹請勿空白");
				}
				
				/*取得從前台送來的part"upmealfile"，*/
				Part part = req.getPart("upmealfile");
//				System.out.println(part.getSize());
				InputStream in = part.getInputStream();
				byte[] meal_pic = new byte[in.available()];
				in.read(meal_pic);
				in.close();
//				System.out.println(part);
				/*錯誤攔截*/
				if(part.getSize() <= 0) {
					errorMsgs.add("尚未上傳圖片!");		
				}
				MealService mealSvc = new MealService();
				
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("MealService", mealSvc); // 含有輸入格式錯誤的mealVO物件,也存入req
					RequestDispatcher failureView = req.getRequestDispatcher("/back-end/meal/addMeal.jsp");
					failureView.forward(req, res);
					return;
				}
				
				/*************************** 2.開始新增資料 ***************************************/
				mealSvc.addMeal1(meal_type_no,meal_price,meal_name,meal_intro,meal_pic);
						
				/*************************** 3.新增完成,準備轉交(Send the Success view) ***********/
				String url = "/back-end/meal/listAllMeal.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 新增成功後轉交listAllMeal.jsp
				successView.forward(req, res);
				
				/*************************** 其他可能的錯誤處理 **********************************/
			} catch (Exception e) {
				errorMsgs.add(e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/meal/addMeal.jsp");
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
				Integer mealno = new Integer(req.getParameter("mealno"));

				/*************************** 2.開始刪除資料 ***************************************/
				MealService mealSvc = new MealService();
				mealSvc.deleteMealandpic(mealno);

				/*************************** 3.刪除完成,準備轉交(Send the Success view) ***********/
				String url = "/back-end/meal/listAllMeal.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);// 刪除成功後,轉交回送出刪除的來源網頁
				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 **********************************/
			} catch (Exception e) {
				errorMsgs.add("刪除資料失敗:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/meal/listAllMeal.jsp");
				failureView.forward(req, res);
			}
		}
	}

}
