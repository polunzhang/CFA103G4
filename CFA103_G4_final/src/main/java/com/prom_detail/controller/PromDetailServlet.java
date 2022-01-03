package com.prom_detail.controller;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.LinkedList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javax.servlet.RequestDispatcher;
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
import com.member.model.MailService;
import com.member.model.MemberDAO;
import com.member.model.MemberJDBCDAO;
import com.member.model.MemberVO;
import com.prom.model.PromJDBCDAO;
import com.prom.model.PromService;
import com.prom.model.PromVO;
import com.prom.model.promMailService;
import com.prom_detail.model.PromDetailJDBCDAO;
import com.prom_detail.model.PromDetailService;
import com.prom_detail.model.PromDetailVO;

import java.lang.*;
import java.text.SimpleDateFormat;
@MultipartConfig
public class PromDetailServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");
		
		if ("search".equals(action)) { // 來自listAllEmp.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/*************************** 1.接收請求參數 ****************************************/
				Integer mealno = new Integer(req.getParameter("mealnoseahch"));
//				System.out.println("Search取得" + mealno);
				/*************************** 2.開始查詢資料 ****************************************/
				PromDetailJDBCDAO pddao = new PromDetailJDBCDAO();
				List<PromDetailVO> pdVO = pddao.findByMealNo(mealno);
				if(pdVO.size()<=0) {
					errorMsgs.add("查無相關促銷活動");
				}
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/back-end/prom/listAllProm.jsp");
					failureView.forward(req, res);
					return;// 程式中斷
				}
//				System.out.println(pdVO.size());
//				取出各promno裝入list內
				ArrayList<PromVO> PromVOLIST = new ArrayList<PromVO>(); 
				PromService PSVC = new PromService();
				for(PromDetailVO promdetailvo: pdVO) {
//					System.out.println(55555);
					PromVO PromVO = PSVC.getOneprom(promdetailvo.getPromno());
					PromVOLIST.add(PromVO);
//					System.out.println("promo查詢結果存入PromVOLIST，size=" + PromVOLIST.size());

				}
				/*************************** 3.查詢完成,準備轉交(Send the Success view) ************/
				req.setAttribute("pdVO", pdVO); // 資料庫取出的pdVO物件,存入req
				req.setAttribute("PromVOLIST", PromVOLIST); // 資料庫取出的promnoByMealno物件,存入req
				req.setAttribute("mealno", mealno); // mealno物件,存入req
				String url = "/back-end/prom/listPromByMealno.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);// 成功轉交 update_meal_input.jsp
				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 **********************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得要修改的資料:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/prom/listAllProm.jsp");
				failureView.forward(req, res);
			}
		}

		if ("getOneByPk".equals(action)) { // 來自listAllEmp.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/*************************** 1.接收請求參數 ****************************************/
				Integer promno = new Integer(req.getParameter("promno"));
				/*************************** 2.開始查詢資料 ****************************************/
				PromDetailJDBCDAO pddao = new PromDetailJDBCDAO();
				List<PromDetailVO> pdVO = pddao.findByPrimaryKey(promno);
								/*************************** 3.查詢完成,準備轉交(Send the Success view) ************/
				req.setAttribute("pdVO", pdVO); // 資料庫取出的empVO物件,存入req
				req.setAttribute("pdVOpromno", promno); // 資料庫取出的promno物件,存入req
				String url = "/back-end/prom/listPromDetailByPk.jsp";
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
//				取得折扣狀態
				Integer promState = new Integer(req.getParameter("promState").trim());
//				測試取得的promState
//				System.out.println(promState);
//				取得折扣百分比/金額
				Double promValues ;
				try {
				promValues = new Double(req.getParameter("promValues").trim());
					if (promValues % 1 != 0 || promValues <= 0 || promValues == null) {
						errorMsgs.add("折扣百分比/金額價格: 請輸入正整數");
					}
				} catch (NumberFormatException e) {
					promValues = 0.0;
					errorMsgs.add("折扣百分比/金額請填數字.");
				}
//				處理取得的數字
				if(promState == 1) {
					promValues = (100-promValues)/100;
				}if(promState == 0) {
					promValues = Math.abs(-promValues);
				}
//				測試處理後的promValues
//				System.out.println(promValues);

//				取得日期
				java.sql.Date promTimeStart = null;
				try {
					promTimeStart = java.sql.Date.valueOf(req.getParameter("promTimeStart").trim());
				} catch (IllegalArgumentException e) {
					promTimeStart=new java.sql.Date(System.currentTimeMillis());
					errorMsgs.add("請輸入日期!");
				}
//				取得日期
				java.sql.Date promTimeEnd = null;
				try {
					promTimeEnd = java.sql.Date.valueOf(req.getParameter("promTimeEnd").trim());
					System.out.println(promTimeEnd);
				} catch (IllegalArgumentException e) {
					promTimeEnd=new java.sql.Date(System.currentTimeMillis());
					errorMsgs.add("請輸入日期!");
				}
				String[] mealno=null;
				try {
				mealno = req.getParameterValues("mealno");
				if(Arrays.toString(mealno) == null) {
					errorMsgs.add("尚未選擇欲促銷之餐點");
				}
				}catch (IllegalArgumentException e) {
					errorMsgs.add("尚未選擇欲促銷之餐點");
				}
//				確認mealno陣列內物件是否存在並印出
//				System.out.println(Arrays.toString(mealno));
//				System.out.println(mealno[1]);
				HttpSession session = req.getSession();
				PromVO promvo = (PromVO) session.getAttribute("PROMVO");
//				檢查是否取得session內物件並嘗試印出
//				System.out.println("PDSERVLET取得"+promvo.getPromno());
				Integer promno = promvo.getPromno();
				PromDetailService pdSVC = new PromDetailService();
				for(int size=0;size < (mealno.length);size++) {
					Integer mealno1 = Integer.parseInt(mealno[size]);
					pdSVC.addPromDetail(promno,mealno1,promValues,promTimeStart,promTimeEnd,promState);
					System.out.println(promTimeEnd);
				}
//				新增方法complete
//				System.out.println("addpromdetal跑完");
					
				
				
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("PromDetailService", pdSVC); // 含有輸入格式錯誤的mealVO物件,也存入req
					RequestDispatcher failureView = req.getRequestDispatcher("/back-end/prom/addPromDetail.jsp");
					failureView.forward(req, res);
					return;
				}
//				確認有無勾選發送促銷信
				try {
				String[] prommail =  (req.getParameterValues("prommail"));
					if(prommail!=null) {
						System.out.println("有勾選prommail");
//						呼叫預先寫好的mailSVC
						promMailService PMSVC = new promMailService();
//						呼叫memberJDBCDAO並使用getall2取得所有已驗證會員的list
						MemberJDBCDAO mdao = new MemberJDBCDAO();
						List<MemberVO> listVO = mdao.getAll2();
//						System.out.println(listVO.size());
//						呼叫prom及promdetail的service並取得信內需使用的資訊()
						PromJDBCDAO pdao = new PromJDBCDAO();
//						以findbypk方法取得PromVO，並以變數promcontent裝下其中的promcontent
						PromVO promVO=pdao.findByPrimaryKey(promno);
						String promContent=promVO.getProm_content();
						String mainprom = promContent+"<br>活動時間自"+promTimeStart+"至"+promTimeEnd;
//						將本次新增的promtime轉換為字串型別
						SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
						String dateStartString = sdf.format(promTimeStart);
//						System.out.println(dateStartString);
						String dateEndString = sdf.format(promTimeEnd);
//						System.out.println(dateEndString);
						
									
						for(MemberVO memberVO1:listVO) {
//							System.out.println(memberVO1.getMem_email());
//							System.out.println(memberVO1.getMname());
//							System.out.println(promContent);
//							System.out.println(dateStartString);
//							System.out.println(dateEndString);
							new promMailService().sendMail(memberVO1.getMem_email(),memberVO1.getMname(),mainprom,dateStartString,dateEndString);
							
						}
						
						
						
					}else if(prommail == null) {
						System.out.println("沒勾選prommail");
					}
				}catch (IllegalArgumentException e) {
						
					}
				
				/*************************** 3.新增完成,準備轉交(Send the Success view) ***********/
				String url = "/back-end/prom/listAllProm.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 新增成功後轉交listAllMeal.jsp
				successView.forward(req, res);
				
				/*************************** 其他可能的錯誤處理 **********************************/
			} catch (Exception e) {
				errorMsgs.add(e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/prom/addPromDetail.jsp");
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
				Integer promno = new Integer(req.getParameter("promno"));

				/*************************** 2.開始刪除資料 ***************************************/
				PromDetailService pdSvc = new PromDetailService();
				pdSvc.deletePromDetail(promno,mealno);
				PromDetailJDBCDAO pddao = new PromDetailJDBCDAO();
				List<PromDetailVO> pdVOlist = pddao.findByPrimaryKey(promno);
				/*************************** 3.刪除完成,準備轉交(Send the Success view) ***********/
				req.setAttribute("pdVO", pdVOlist); // 資料庫取出的empVO物件,存入req
				req.setAttribute("pdVOpromno", promno); // 資料庫取出的promno物件,存入req
				String url = "/back-end/prom/listPromDetailByPk.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);// 刪除成功後,轉交回送出刪除的來源網頁
				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 **********************************/
			} catch (Exception e) {
				errorMsgs.add("刪除資料失敗:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/prom/listPromDetailByPk.jsp");
				failureView.forward(req, res);
			}
		}
	
	
	if ("sendprom".equals(action)) { // 來自addprommail.jsp的請求

		List<String> errorMsgs = new LinkedList<String>();
		// Store this set in the request scope, in case we need to
		// send the ErrorPage view.
		req.setAttribute("errorMsgs", errorMsgs);

		try {
			/*********************** 1.接收請求參數 - 輸入格式的錯誤處理 *************************/

			Integer promno = new Integer(req.getParameter("promno"));
				
			try {
//					呼叫預先寫好的mailSVC
					promMailService PMSVC = new promMailService();
//					呼叫memberJDBCDAO並使用getall2取得所有已驗證會員的list
					MemberJDBCDAO mdao = new MemberJDBCDAO();
					List<MemberVO> listVO = mdao.getAll2();
					System.out.println(listVO.size());
//					呼叫prom及promdetail的service並取得信內需使用的資訊()
					PromJDBCDAO pdao = new PromJDBCDAO();
					PromDetailJDBCDAO pddao = new PromDetailJDBCDAO();
					
//					以findByPrimaryKeyLimitOne方法取得pdVO，並以變數promTimeStart、promTimeEnd裝下其中的promcontent
					PromDetailVO pdVO = pddao.findByPrimaryKeyLimitOne(promno);
					Date TimeStart = pdVO.getProm_time_start();
					Date TimeEnd = pdVO.getProm_time_end();
					//設定日期格式
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
					//進行轉換
					String promTimeStart = sdf.format(TimeStart);
					String promTimeEnd = sdf.format(TimeEnd);
					
//					以findbypk方法取得PromVO，並以變數promcontent裝下其中的promcontent
					PromVO promVO=pdao.findByPrimaryKey(promno);
					String promContent=promVO.getProm_content();
					String mainprom = promContent+"<br>活動時間自"+promTimeStart+"至"+promTimeEnd;
											
					for(MemberVO memberVO1:listVO) {
						System.out.println(memberVO1.getMem_email());
						System.out.println(memberVO1.getMname());
						System.out.println(promContent);
						System.out.println(promTimeStart);
						System.out.println(promTimeEnd);
						new promMailService().sendMail(memberVO1.getMem_email(),memberVO1.getMname(),mainprom,promTimeStart,promTimeEnd);
					}
				
			}catch (IllegalArgumentException e) {
				System.out.println("傳送失敗");

				}
			
			/*************************** 3.新增完成,準備轉交(Send the Success view) ***********/
			String url = "/back-end/prom/listAllProm.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(url); // 新增成功後轉交listAllMeal.jsp
			successView.forward(req, res);
			
			/*************************** 其他可能的錯誤處理 **********************************/
		} catch (Exception e) {
			errorMsgs.add(e.getMessage());
			RequestDispatcher failureView = req.getRequestDispatcher("/back-end/prom/listAllProm.jsp");
			failureView.forward(req, res);
		}
	}
	
	if ("prommailfuture".equals(action)) { // 來自addprommail.jsp的請求

		List<String> errorMsgs = new LinkedList<String>();
		// Store this set in the request scope, in case we need to
		// send the ErrorPage view.
		req.setAttribute("errorMsgs", errorMsgs);

		try {
			/*********************** 1.接收請求參數 - 輸入格式的錯誤處理 *************************/

			Integer promno = new Integer(req.getParameter("promno"));
			
//			處理日期
			String maildate = new String(req.getParameter("maildate"));
//			System.out.println(maildate);
			String year = maildate.substring(0, 4);
//			System.out.println(year);
			String mon = maildate.substring(5, 7);
//			System.out.println(mon);
			String day = maildate.substring(8, 10);
//			System.out.println(day);
			String hour = maildate.substring(11, 13);
//			System.out.println(hour);
			String min = maildate.substring(14, 16);
//			System.out.println(min);
			Integer intyear=Integer.parseInt(year);
//			System.out.println(intyear);
			Integer intmon=Integer.parseInt(mon);
			intmon=intmon-1;
//			System.out.println(intmon);
			Integer intday=Integer.parseInt(day);
//			System.out.println(intday);
			Integer inthour=Integer.parseInt(hour);
//			System.out.println(inthour);
			Integer intmin=Integer.parseInt(min);
//			System.out.println(intmin);
			
//			列入排程器
			Timer timer = new Timer();
			TimerTask task = new TimerTask() {
				@Override
				public void run() {
					System.out.println("開始執行:預約之促銷信件寄送程序");
					try {
//					呼叫預先寫好的mailSVC
						promMailService PMSVC = new promMailService();
//					呼叫memberJDBCDAO並使用getall2取得所有已驗證會員的list
						MemberJDBCDAO mdao = new MemberJDBCDAO();
						List<MemberVO> listVO = mdao.getAll2();
//						System.out.println(listVO.size());
//					呼叫prom及promdetail的service並取得信內需使用的資訊()
						PromJDBCDAO pdao = new PromJDBCDAO();
						PromDetailJDBCDAO pddao = new PromDetailJDBCDAO();
						
//					以findByPrimaryKeyLimitOne方法取得pdVO，並以變數promTimeStart、promTimeEnd裝下其中的promcontent
						PromDetailVO pdVO = pddao.findByPrimaryKeyLimitOne(promno);
						Date TimeStart = pdVO.getProm_time_start();
						Date TimeEnd = pdVO.getProm_time_end();
						//設定日期格式
						SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
						//進行轉換
						String promTimeStart = sdf.format(TimeStart);
						String promTimeEnd = sdf.format(TimeEnd);
						
//					以findbypk方法取得PromVO，並以變數promcontent裝下其中的promcontent
						PromVO promVO=pdao.findByPrimaryKey(promno);
						String promContent=promVO.getProm_content();
						String mainprom = promContent+"<br>活動時間自"+promTimeStart+"至"+promTimeEnd;
						
						for(MemberVO memberVO1:listVO) {
						System.out.println("寄送對象:會員信箱"+memberVO1.getMem_email());
						System.out.println("寄送對象:會員姓名"+memberVO1.getMname());
						System.out.println("寄送內容:促銷內容"+promContent);
						System.out.println("寄送內容:促銷開始日"+promTimeStart);
						System.out.println("寄送內容:促銷結束日"+promTimeEnd);
						System.out.println("================");
						new promMailService().sendMail(memberVO1.getMem_email(),memberVO1.getMname(),mainprom,promTimeStart,promTimeEnd);
						}
						
					}catch (IllegalArgumentException e) {
						System.out.println("傳送失敗");
					}
					}
			};
			Calendar cal = new GregorianCalendar(intyear, intmon, intday, inthour, intmin);
			timer.scheduleAtFixedRate(task, cal.getTime(), 60*5);
			System.out.println("預約促銷已於伺服器啟動時同步執行中");
			
			TimerTask taskclose = new TimerTask() {
				@Override
				public void run() {
					timer.cancel();
					System.out.println("寄送完畢，將預約程序關閉");
			}
			};
			Calendar calclose = new GregorianCalendar(intyear, intmon, intday, inthour, intmin);
			timer.scheduleAtFixedRate(taskclose, calclose.getTime(), 60*6);
			/*************************** 3.新增完成,準備轉交(Send the Success view) ***********/
			String url = "/back-end/prom/listAllProm.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(url); // 新增成功後轉交listAllMeal.jsp
			successView.forward(req, res);
			
			/*************************** 其他可能的錯誤處理 **********************************/
		} catch (Exception e) {
			errorMsgs.add(e.getMessage());
			RequestDispatcher failureView = req.getRequestDispatcher("/back-end/prom/listAllProm.jsp");
			failureView.forward(req, res);
		}
	}
	
	if ("addpromdtmeal".equals(action)) { // 來自addMeal.jsp的請求

		List<String> errorMsgs = new LinkedList<String>();
		// Store this set in the request scope, in case we need to
		// send the ErrorPage view.
		req.setAttribute("errorMsgs", errorMsgs);

		try {
			/*********************** 1.接收請求參數 - 輸入格式的錯誤處理 *************************/

			Integer promno = new Integer(req.getParameter("promno").trim());
			
			Integer mealno =null ;
			try {
				mealno = new Integer(req.getParameter("mealno").trim());
				if (mealno % 1 != 0 || mealno <= 0 || mealno == null) {
					errorMsgs.add("請選擇欲新增之餐點");
				}
			} catch (NumberFormatException e) {
					errorMsgs.add("請選擇欲新增之餐點");
			}


			PromDetailJDBCDAO pddao = new PromDetailJDBCDAO();
			PromDetailVO pdVO = pddao.findByPrimaryKeyLimitOne(promno);
			Double promValue =pdVO.getProm_value();
			Integer promState = pdVO.getProm_state();
			java.sql.Date promTimeStart = pdVO.getProm_time_start();
			java.sql.Date promTimeEnd = pdVO.getProm_time_end();
			
			PromDetailService pdSVC = new PromDetailService();
			pdSVC.addPromDetail(promno, mealno, promValue, promTimeStart, promTimeEnd, promState);

			if (!errorMsgs.isEmpty()) {
				req.setAttribute("PromDetailService", pdSVC); // 含有輸入格式錯誤的mealVO物件,也存入req
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/prom/addPromDetail.jsp");
				failureView.forward(req, res);
				return;
			}
			
			List<PromDetailVO> pdVOlist = pddao.findByPrimaryKey(promno);
			req.setAttribute("pdVO", pdVOlist); // 資料庫取出的empVO物件,存入req
			req.setAttribute("pdVOpromno", promno); // 資料庫取出的promno物件,存入req
			/*************************** 3.新增完成,準備轉交(Send the Success view) ***********/
			String url = "/back-end/prom/listPromDetailByPk.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(url); // 新增成功後轉交listAllMeal.jsp
			successView.forward(req, res);
			
			/*************************** 其他可能的錯誤處理 **********************************/
		} catch (Exception e) {
			errorMsgs.add(e.getMessage());
			RequestDispatcher failureView = req.getRequestDispatcher("/back-end/prom/listPromDetailByPk.jsp");
			failureView.forward(req, res);
		}
	}
	
	
	
	
	
}
}
