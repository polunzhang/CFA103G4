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
		
		if ("search".equals(action)) { // �Ӧ�listAllEmp.jsp���ШD

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/*************************** 1.�����ШD�Ѽ� ****************************************/
				Integer mealno = new Integer(req.getParameter("mealnoseahch"));
//				System.out.println("Search���o" + mealno);
				/*************************** 2.�}�l�d�߸�� ****************************************/
				PromDetailJDBCDAO pddao = new PromDetailJDBCDAO();
				List<PromDetailVO> pdVO = pddao.findByMealNo(mealno);
				if(pdVO.size()<=0) {
					errorMsgs.add("�d�L�����P�P����");
				}
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/back-end/prom/listAllProm.jsp");
					failureView.forward(req, res);
					return;// �{�����_
				}
//				System.out.println(pdVO.size());
//				���X�Upromno�ˤJlist��
				ArrayList<PromVO> PromVOLIST = new ArrayList<PromVO>(); 
				PromService PSVC = new PromService();
				for(PromDetailVO promdetailvo: pdVO) {
//					System.out.println(55555);
					PromVO PromVO = PSVC.getOneprom(promdetailvo.getPromno());
					PromVOLIST.add(PromVO);
//					System.out.println("promo�d�ߵ��G�s�JPromVOLIST�Asize=" + PromVOLIST.size());

				}
				/*************************** 3.�d�ߧ���,�ǳ����(Send the Success view) ************/
				req.setAttribute("pdVO", pdVO); // ��Ʈw���X��pdVO����,�s�Jreq
				req.setAttribute("PromVOLIST", PromVOLIST); // ��Ʈw���X��promnoByMealno����,�s�Jreq
				req.setAttribute("mealno", mealno); // mealno����,�s�Jreq
				String url = "/back-end/prom/listPromByMealno.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);// ���\��� update_meal_input.jsp
				successView.forward(req, res);

				/*************************** ��L�i�઺���~�B�z **********************************/
			} catch (Exception e) {
				errorMsgs.add("�L�k���o�n�ק諸���:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/prom/listAllProm.jsp");
				failureView.forward(req, res);
			}
		}

		if ("getOneByPk".equals(action)) { // �Ӧ�listAllEmp.jsp���ШD

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/*************************** 1.�����ШD�Ѽ� ****************************************/
				Integer promno = new Integer(req.getParameter("promno"));
				/*************************** 2.�}�l�d�߸�� ****************************************/
				PromDetailJDBCDAO pddao = new PromDetailJDBCDAO();
				List<PromDetailVO> pdVO = pddao.findByPrimaryKey(promno);
								/*************************** 3.�d�ߧ���,�ǳ����(Send the Success view) ************/
				req.setAttribute("pdVO", pdVO); // ��Ʈw���X��empVO����,�s�Jreq
				req.setAttribute("pdVOpromno", promno); // ��Ʈw���X��promno����,�s�Jreq
				String url = "/back-end/prom/listPromDetailByPk.jsp";
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
//				���o�馩���A
				Integer promState = new Integer(req.getParameter("promState").trim());
//				���ը��o��promState
//				System.out.println(promState);
//				���o�馩�ʤ���/���B
				Double promValues ;
				try {
				promValues = new Double(req.getParameter("promValues").trim());
					if (promValues % 1 != 0 || promValues <= 0 || promValues == null) {
						errorMsgs.add("�馩�ʤ���/���B����: �п�J�����");
					}
				} catch (NumberFormatException e) {
					promValues = 0.0;
					errorMsgs.add("�馩�ʤ���/���B�ж�Ʀr.");
				}
//				�B�z���o���Ʀr
				if(promState == 1) {
					promValues = (100-promValues)/100;
				}if(promState == 0) {
					promValues = Math.abs(-promValues);
				}
//				���ճB�z�᪺promValues
//				System.out.println(promValues);

//				���o���
				java.sql.Date promTimeStart = null;
				try {
					promTimeStart = java.sql.Date.valueOf(req.getParameter("promTimeStart").trim());
				} catch (IllegalArgumentException e) {
					promTimeStart=new java.sql.Date(System.currentTimeMillis());
					errorMsgs.add("�п�J���!");
				}
//				���o���
				java.sql.Date promTimeEnd = null;
				try {
					promTimeEnd = java.sql.Date.valueOf(req.getParameter("promTimeEnd").trim());
					System.out.println(promTimeEnd);
				} catch (IllegalArgumentException e) {
					promTimeEnd=new java.sql.Date(System.currentTimeMillis());
					errorMsgs.add("�п�J���!");
				}
				String[] mealno=null;
				try {
				mealno = req.getParameterValues("mealno");
				if(Arrays.toString(mealno) == null) {
					errorMsgs.add("�|����ܱ��P�P���\�I");
				}
				}catch (IllegalArgumentException e) {
					errorMsgs.add("�|����ܱ��P�P���\�I");
				}
//				�T�{mealno�}�C������O�_�s�b�æL�X
//				System.out.println(Arrays.toString(mealno));
//				System.out.println(mealno[1]);
				HttpSession session = req.getSession();
				PromVO promvo = (PromVO) session.getAttribute("PROMVO");
//				�ˬd�O�_���osession������ù��զL�X
//				System.out.println("PDSERVLET���o"+promvo.getPromno());
				Integer promno = promvo.getPromno();
				PromDetailService pdSVC = new PromDetailService();
				for(int size=0;size < (mealno.length);size++) {
					Integer mealno1 = Integer.parseInt(mealno[size]);
					pdSVC.addPromDetail(promno,mealno1,promValues,promTimeStart,promTimeEnd,promState);
					System.out.println(promTimeEnd);
				}
//				�s�W��kcomplete
//				System.out.println("addpromdetal�]��");
					
				
				
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("PromDetailService", pdSVC); // �t����J�榡���~��mealVO����,�]�s�Jreq
					RequestDispatcher failureView = req.getRequestDispatcher("/back-end/prom/addPromDetail.jsp");
					failureView.forward(req, res);
					return;
				}
//				�T�{���L�Ŀ�o�e�P�P�H
				try {
				String[] prommail =  (req.getParameterValues("prommail"));
					if(prommail!=null) {
						System.out.println("���Ŀ�prommail");
//						�I�s�w���g�n��mailSVC
						promMailService PMSVC = new promMailService();
//						�I�smemberJDBCDAO�èϥ�getall2���o�Ҧ��w���ҷ|����list
						MemberJDBCDAO mdao = new MemberJDBCDAO();
						List<MemberVO> listVO = mdao.getAll2();
//						System.out.println(listVO.size());
//						�I�sprom��promdetail��service�è��o�H���ݨϥΪ���T()
						PromJDBCDAO pdao = new PromJDBCDAO();
//						�Hfindbypk��k���oPromVO�A�åH�ܼ�promcontent�ˤU�䤤��promcontent
						PromVO promVO=pdao.findByPrimaryKey(promno);
						String promContent=promVO.getProm_content();
						String mainprom = promContent+"<br>���ʮɶ���"+promTimeStart+"��"+promTimeEnd;
//						�N�����s�W��promtime�ഫ���r�ꫬ�O
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
						System.out.println("�S�Ŀ�prommail");
					}
				}catch (IllegalArgumentException e) {
						
					}
				
				/*************************** 3.�s�W����,�ǳ����(Send the Success view) ***********/
				String url = "/back-end/prom/listAllProm.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // �s�W���\�����listAllMeal.jsp
				successView.forward(req, res);
				
				/*************************** ��L�i�઺���~�B�z **********************************/
			} catch (Exception e) {
				errorMsgs.add(e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/prom/addPromDetail.jsp");
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
				Integer promno = new Integer(req.getParameter("promno"));

				/*************************** 2.�}�l�R����� ***************************************/
				PromDetailService pdSvc = new PromDetailService();
				pdSvc.deletePromDetail(promno,mealno);
				PromDetailJDBCDAO pddao = new PromDetailJDBCDAO();
				List<PromDetailVO> pdVOlist = pddao.findByPrimaryKey(promno);
				/*************************** 3.�R������,�ǳ����(Send the Success view) ***********/
				req.setAttribute("pdVO", pdVOlist); // ��Ʈw���X��empVO����,�s�Jreq
				req.setAttribute("pdVOpromno", promno); // ��Ʈw���X��promno����,�s�Jreq
				String url = "/back-end/prom/listPromDetailByPk.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);// �R�����\��,���^�e�X�R�����ӷ�����
				successView.forward(req, res);

				/*************************** ��L�i�઺���~�B�z **********************************/
			} catch (Exception e) {
				errorMsgs.add("�R����ƥ���:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/prom/listPromDetailByPk.jsp");
				failureView.forward(req, res);
			}
		}
	
	
	if ("sendprom".equals(action)) { // �Ӧ�addprommail.jsp���ШD

		List<String> errorMsgs = new LinkedList<String>();
		// Store this set in the request scope, in case we need to
		// send the ErrorPage view.
		req.setAttribute("errorMsgs", errorMsgs);

		try {
			/*********************** 1.�����ШD�Ѽ� - ��J�榡�����~�B�z *************************/

			Integer promno = new Integer(req.getParameter("promno"));
				
			try {
//					�I�s�w���g�n��mailSVC
					promMailService PMSVC = new promMailService();
//					�I�smemberJDBCDAO�èϥ�getall2���o�Ҧ��w���ҷ|����list
					MemberJDBCDAO mdao = new MemberJDBCDAO();
					List<MemberVO> listVO = mdao.getAll2();
					System.out.println(listVO.size());
//					�I�sprom��promdetail��service�è��o�H���ݨϥΪ���T()
					PromJDBCDAO pdao = new PromJDBCDAO();
					PromDetailJDBCDAO pddao = new PromDetailJDBCDAO();
					
//					�HfindByPrimaryKeyLimitOne��k���opdVO�A�åH�ܼ�promTimeStart�BpromTimeEnd�ˤU�䤤��promcontent
					PromDetailVO pdVO = pddao.findByPrimaryKeyLimitOne(promno);
					Date TimeStart = pdVO.getProm_time_start();
					Date TimeEnd = pdVO.getProm_time_end();
					//�]�w����榡
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
					//�i���ഫ
					String promTimeStart = sdf.format(TimeStart);
					String promTimeEnd = sdf.format(TimeEnd);
					
//					�Hfindbypk��k���oPromVO�A�åH�ܼ�promcontent�ˤU�䤤��promcontent
					PromVO promVO=pdao.findByPrimaryKey(promno);
					String promContent=promVO.getProm_content();
					String mainprom = promContent+"<br>���ʮɶ���"+promTimeStart+"��"+promTimeEnd;
											
					for(MemberVO memberVO1:listVO) {
						System.out.println(memberVO1.getMem_email());
						System.out.println(memberVO1.getMname());
						System.out.println(promContent);
						System.out.println(promTimeStart);
						System.out.println(promTimeEnd);
						new promMailService().sendMail(memberVO1.getMem_email(),memberVO1.getMname(),mainprom,promTimeStart,promTimeEnd);
					}
				
			}catch (IllegalArgumentException e) {
				System.out.println("�ǰe����");

				}
			
			/*************************** 3.�s�W����,�ǳ����(Send the Success view) ***********/
			String url = "/back-end/prom/listAllProm.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(url); // �s�W���\�����listAllMeal.jsp
			successView.forward(req, res);
			
			/*************************** ��L�i�઺���~�B�z **********************************/
		} catch (Exception e) {
			errorMsgs.add(e.getMessage());
			RequestDispatcher failureView = req.getRequestDispatcher("/back-end/prom/listAllProm.jsp");
			failureView.forward(req, res);
		}
	}
	
	if ("prommailfuture".equals(action)) { // �Ӧ�addprommail.jsp���ШD

		List<String> errorMsgs = new LinkedList<String>();
		// Store this set in the request scope, in case we need to
		// send the ErrorPage view.
		req.setAttribute("errorMsgs", errorMsgs);

		try {
			/*********************** 1.�����ШD�Ѽ� - ��J�榡�����~�B�z *************************/

			Integer promno = new Integer(req.getParameter("promno"));
			
//			�B�z���
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
			
//			�C�J�Ƶ{��
			Timer timer = new Timer();
			TimerTask task = new TimerTask() {
				@Override
				public void run() {
					System.out.println("�}�l����:�w�����P�P�H��H�e�{��");
					try {
//					�I�s�w���g�n��mailSVC
						promMailService PMSVC = new promMailService();
//					�I�smemberJDBCDAO�èϥ�getall2���o�Ҧ��w���ҷ|����list
						MemberJDBCDAO mdao = new MemberJDBCDAO();
						List<MemberVO> listVO = mdao.getAll2();
//						System.out.println(listVO.size());
//					�I�sprom��promdetail��service�è��o�H���ݨϥΪ���T()
						PromJDBCDAO pdao = new PromJDBCDAO();
						PromDetailJDBCDAO pddao = new PromDetailJDBCDAO();
						
//					�HfindByPrimaryKeyLimitOne��k���opdVO�A�åH�ܼ�promTimeStart�BpromTimeEnd�ˤU�䤤��promcontent
						PromDetailVO pdVO = pddao.findByPrimaryKeyLimitOne(promno);
						Date TimeStart = pdVO.getProm_time_start();
						Date TimeEnd = pdVO.getProm_time_end();
						//�]�w����榡
						SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
						//�i���ഫ
						String promTimeStart = sdf.format(TimeStart);
						String promTimeEnd = sdf.format(TimeEnd);
						
//					�Hfindbypk��k���oPromVO�A�åH�ܼ�promcontent�ˤU�䤤��promcontent
						PromVO promVO=pdao.findByPrimaryKey(promno);
						String promContent=promVO.getProm_content();
						String mainprom = promContent+"<br>���ʮɶ���"+promTimeStart+"��"+promTimeEnd;
						
						for(MemberVO memberVO1:listVO) {
						System.out.println("�H�e��H:�|���H�c"+memberVO1.getMem_email());
						System.out.println("�H�e��H:�|���m�W"+memberVO1.getMname());
						System.out.println("�H�e���e:�P�P���e"+promContent);
						System.out.println("�H�e���e:�P�P�}�l��"+promTimeStart);
						System.out.println("�H�e���e:�P�P������"+promTimeEnd);
						System.out.println("================");
						new promMailService().sendMail(memberVO1.getMem_email(),memberVO1.getMname(),mainprom,promTimeStart,promTimeEnd);
						}
						
					}catch (IllegalArgumentException e) {
						System.out.println("�ǰe����");
					}
					}
			};
			Calendar cal = new GregorianCalendar(intyear, intmon, intday, inthour, intmin);
			timer.scheduleAtFixedRate(task, cal.getTime(), 60*5);
			System.out.println("�w���P�P�w����A���ҰʮɦP�B���椤");
			
			TimerTask taskclose = new TimerTask() {
				@Override
				public void run() {
					timer.cancel();
					System.out.println("�H�e�����A�N�w���{������");
			}
			};
			Calendar calclose = new GregorianCalendar(intyear, intmon, intday, inthour, intmin);
			timer.scheduleAtFixedRate(taskclose, calclose.getTime(), 60*6);
			/*************************** 3.�s�W����,�ǳ����(Send the Success view) ***********/
			String url = "/back-end/prom/listAllProm.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(url); // �s�W���\�����listAllMeal.jsp
			successView.forward(req, res);
			
			/*************************** ��L�i�઺���~�B�z **********************************/
		} catch (Exception e) {
			errorMsgs.add(e.getMessage());
			RequestDispatcher failureView = req.getRequestDispatcher("/back-end/prom/listAllProm.jsp");
			failureView.forward(req, res);
		}
	}
	
	if ("addpromdtmeal".equals(action)) { // �Ӧ�addMeal.jsp���ШD

		List<String> errorMsgs = new LinkedList<String>();
		// Store this set in the request scope, in case we need to
		// send the ErrorPage view.
		req.setAttribute("errorMsgs", errorMsgs);

		try {
			/*********************** 1.�����ШD�Ѽ� - ��J�榡�����~�B�z *************************/

			Integer promno = new Integer(req.getParameter("promno").trim());
			
			Integer mealno =null ;
			try {
				mealno = new Integer(req.getParameter("mealno").trim());
				if (mealno % 1 != 0 || mealno <= 0 || mealno == null) {
					errorMsgs.add("�п�ܱ��s�W���\�I");
				}
			} catch (NumberFormatException e) {
					errorMsgs.add("�п�ܱ��s�W���\�I");
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
				req.setAttribute("PromDetailService", pdSVC); // �t����J�榡���~��mealVO����,�]�s�Jreq
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/prom/addPromDetail.jsp");
				failureView.forward(req, res);
				return;
			}
			
			List<PromDetailVO> pdVOlist = pddao.findByPrimaryKey(promno);
			req.setAttribute("pdVO", pdVOlist); // ��Ʈw���X��empVO����,�s�Jreq
			req.setAttribute("pdVOpromno", promno); // ��Ʈw���X��promno����,�s�Jreq
			/*************************** 3.�s�W����,�ǳ����(Send the Success view) ***********/
			String url = "/back-end/prom/listPromDetailByPk.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(url); // �s�W���\�����listAllMeal.jsp
			successView.forward(req, res);
			
			/*************************** ��L�i�઺���~�B�z **********************************/
		} catch (Exception e) {
			errorMsgs.add(e.getMessage());
			RequestDispatcher failureView = req.getRequestDispatcher("/back-end/prom/listPromDetailByPk.jsp");
			failureView.forward(req, res);
		}
	}
	
	
	
	
	
}
}
