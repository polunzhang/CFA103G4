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
				Integer promno = new Integer(req.getParameter("promno"));

				/*************************** 2.開始查詢資料 ****************************************/
				PromService PmSvc = new PromService();
				PromDetailService PDSvc = new PromDetailService();
				
				PromVO promVO = PmSvc.getOneprom(promno);
				List<PromDetailVO> promdetailVO = PDSvc.getOnepromDetail(promno);
				
				PromDetailJDBCDAO PDdao = new PromDetailJDBCDAO();
				PromDetailVO pdVOLO = PDdao.findByPrimaryKeyLimitOne(promno);
//				System.out.println(pdVOLO.getProm_state().getClass().getSimpleName());
				
				

				/*************************** 3.查詢完成,準備轉交(Send the Success view) ************/
				req.setAttribute("promVO", promVO); // 資料庫取出的empVO物件,存入req
				req.setAttribute("promdetailVO", promdetailVO); // 資料庫取出的empVO物件,存入req
				req.setAttribute("pdVOLO", pdVOLO); // 資料庫取出的empVO物件,存入req
				
				String url = "/back-end/prom/update_prom_input.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);// 成功轉交 update_meal_input.jsp
				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 **********************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得要修改的資料:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/prom/listAllProm.jsp");
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

				Integer promno = new Integer(req.getParameter("promno"));
				Integer empno = new Integer(req.getParameter("empno"));
				
//				System.out.println(777777);				
				String prom_title = req.getParameter("prom_title").trim();
				if (prom_title == null || prom_title.trim().length() == 0) {
					errorMsgs.add("活動名稱請勿空白");
				}
//				System.out.println(66666);
				String prom_content = req.getParameter("prom_content").trim();
				if (prom_content == null || prom_content.trim().length() == 0) {
					errorMsgs.add("活動介紹請勿空白");
				}
//				System.out.println(5555555);
				
//				取得從前台送來的part"upmealfile"
				Part part = req.getPart("uppromfile");
				System.out.println(part.getSize());
				byte[] prom_pic=null;
//				判斷是否有更新圖片
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
//				取得折扣狀態
				Integer promState = new Integer(req.getParameter("promState").trim());
//				測試取得的promState
//				System.out.println(promState);
//				取得折扣百分比/金額
				Double promValues = new Double(req.getParameter("promValues").trim());
//				處理取得的數字
				if(promState == 1) {
					promValues = (100-promValues)/100;
				}if(promState == 0) {
					promValues = Math.abs(promValues);
				}
//				System.out.println(789);
//				測試處理後的promValues
//				System.out.println(promValues);

//				取得日期
				java.sql.Date promTimeStart = null;
				try {
					promTimeStart = java.sql.Date.valueOf(req.getParameter("promTimeStart").trim());
				} catch (IllegalArgumentException e) {
					promTimeStart=new java.sql.Date(System.currentTimeMillis());
					errorMsgs.add("請輸入起始日期!");
				}
//				System.out.println(456);
//				取得日期
				java.sql.Date promTimeEnd = null;
				try {
					promTimeEnd = java.sql.Date.valueOf(req.getParameter("promTimeEnd").trim());
				} catch (IllegalArgumentException e) {
					promTimeEnd=new java.sql.Date(System.currentTimeMillis());
					errorMsgs.add("請輸入結束日期!");
				}
				
				
//				System.out.println(123);
				/*************************** 2.開始修改資料 *****************************************/
//				取得該promo中所有mealno(使用getOnepromDetail(即findbypk)並用foreach存入mealno)
				PromDetailService PDSvc = new PromDetailService();
				List<PromDetailVO> PromDetailVO = PDSvc.getOnepromDetail(promno); 
				
//				以foreach取出prom內之mealno並存入另一arraylist
				for(PromDetailVO promdetailvo : PromDetailVO) {
					PDSvc.updatePromDetail(promdetailvo.getMealno(),promValues,promTimeStart,promTimeEnd,promState,promno,promdetailvo.getMealno());
				}
				PromService PSvc = new PromService();
				PromVO PromVO = PSvc.updateProm(promno,empno,prom_title,prom_content,prom_pic);

				/*************************** 3.修改完成,準備轉交(Send the Success view) *************/
				req.setAttribute("PromVO",PromVO ); // 資料庫update成功後,正確的的mealVO物件,存入req
				String url = "/back-end/prom/listAllProm.jsp";
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
				Integer empno = new Integer(req.getParameter("empno").trim());
//錯誤處理尚待整合時釐清
//				if (empno == null ) {
//					errorMsgs.add("請輸入員工編號");
//				}
			
				String prom_title = req.getParameter("prom_title").trim();
				if (prom_title == null || prom_title.trim().length() == 0) {
					errorMsgs.add("活動名稱請勿空白");
				}
				
				String prom_content = req.getParameter("prom_content").trim();
				if (prom_content == null || prom_content.trim().length() == 0) {
					errorMsgs.add("活動介紹請勿空白");
				}
			
				
				/*取得從前台送來的part"upmealfile"，*/
				Part part = req.getPart("uppromfile");
//				System.out.println(part.getSize());
				InputStream in = part.getInputStream();
				byte[] prom_pic = new byte[in.available()];
				in.read(prom_pic);
				in.close();
//				System.out.println(part);
				/*錯誤攔截*/
				if(part.getSize() <= 0) {
					errorMsgs.add("尚未上傳圖片!");		
				}
				PromService promSvc = new PromService();
				
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/back-end/prom/addProm.jsp");
					failureView.forward(req, res);
					return;
				}
				
				/*************************** 2.開始新增資料 ***************************************/
				PromVO promvoo = promSvc.addProm(empno,prom_title,prom_content,prom_pic);
				System.out.println("servlet取得"+promvoo.getPromno());
				HttpSession session = req.getSession();
				session.setAttribute("PROMVO", promvoo);
				/*************************** 3.新增完成,準備轉交(Send the Success view) ***********/
				String url = "/back-end/prom/addPromDetail.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 新增成功後轉交listAllMeal.jsp
				successView.forward(req, res);
				
				/*************************** 其他可能的錯誤處理 **********************************/
			} catch (Exception e) {
				errorMsgs.add(e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/prom/addProm.jsp");
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
				Integer promno = new Integer(req.getParameter("promno"));

				/*************************** 2.開始刪除資料 ***************************************/
				PromJDBCDAO pddao = new PromJDBCDAO();
				pddao.delete(promno);

				/*************************** 3.刪除完成,準備轉交(Send the Success view) ***********/
				String url = "/back-end/prom/listAllProm.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);// 刪除成功後,轉交回送出刪除的來源網頁
				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 **********************************/
			} catch (Exception e) {
				errorMsgs.add("刪除資料失敗:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/prom/listAllProm.jsp");
				failureView.forward(req, res);
			}
		}
	}

}
