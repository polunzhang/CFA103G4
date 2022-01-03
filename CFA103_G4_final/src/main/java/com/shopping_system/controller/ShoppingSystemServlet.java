package com.shopping_system.controller;

import java.util.*;
import java.io.*;
import java.sql.Timestamp;

import javax.servlet.*;
import javax.servlet.http.*;

import org.json.JSONArray;
import org.json.JSONObject;
import com.meal.model.*;
import com.member.model.MemberVO;
import com.online_order.model.*;
import com.online_detail.model.*;

public class ShoppingSystemServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}
	
	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		
		req.setCharacterEncoding("UTF-8");
		HttpSession session = req.getSession();
		
		@SuppressWarnings("unchecked")
		List<OnlineDetailVO> buylist = (Vector<OnlineDetailVO>) session.getAttribute("shoppingcart");
		
		String action = req.getParameter("action");

		
	
			
			// �R���ʪ��������\�I
			if(action.equals("DELETE")) {
				String del = req.getParameter("del");
				int d = Integer.parseInt(del);
				buylist.remove(d);
				
				Integer total = 0;
				for (int i = 0; i < buylist.size(); i++) {
					OnlineDetailVO order = buylist.get(i);
					Integer price = order.getMeal_price();
					Integer amount = order.getMeal_amount();
					total += (price * amount);
				}
				String sum = String.valueOf(total);
				session.setAttribute("sum", sum);
				
				
				session.setAttribute("shoppingcart", buylist);
				String url = "/front-end/shopping_system/Cart.jsp";
				RequestDispatcher rd = req.getRequestDispatcher(url);
				rd.forward(req, res);
			}
			// �s�W�\�I���ʪ�����
			if (action.equals("ADD")) {
				// ���o�s�W���\�I
				OnlineDetailVO meal = getMeal(req);
				if (buylist == null) {
					buylist = new Vector<OnlineDetailVO>();
					buylist.add(meal);
				} else {
					if (buylist.contains(meal)) {//�Y�s�W���\�I�M�ʪ������\�I�@��
						OnlineDetailVO innerOnlineDetailVO = buylist.get(buylist.indexOf(meal));
						innerOnlineDetailVO.setMeal_amount(innerOnlineDetailVO.getMeal_amount() + meal.getMeal_amount());
					} else {
						buylist.add(meal);
					}
				}
				
				Integer total = 0;
				for (int i = 0; i < buylist.size(); i++) {
					OnlineDetailVO order = buylist.get(i);
					Integer price = order.getMeal_price();
					Integer amount = order.getMeal_amount();
					total += (price * amount);
				}
				String sum = String.valueOf(total);
				session.setAttribute("sum", sum);
				
				session.setAttribute("shoppingcart", buylist);
				String url = "/front-end/shopping_system/EShop2.jsp";
				RequestDispatcher rd = req.getRequestDispatcher(url);
				rd.forward(req, res);
			}
//			else if (action.equals("UPDATE")) {//�i�H�ק��\�I�Ƶ�
//				String upd = req.getParameter("upd");
//				int u = Integer.parseInt(upd);
//				String meal_note = req.getParameter("meal_note");
//				System.out.println(meal_note);
//				buylist.get(u).setMeal_note(meal_note);
//			}

			
			
			
//			JSONArray array = new JSONArray();//json
			// �O�o�令MealService
//			MealJDBCDAO mealSvc = new MealJDBCDAO();      //json
//			for (OnlineDetailVO item : buylist) {
//				JSONObject obj = new JSONObject();
//				Integer mealno = item.getMealno();
//				String meal_name = mealSvc.findByPrimaryKey(mealno).getMeal_name();
//				String url = req.getContextPath() + "/MealPicServlet?id=" + mealno;
//				String trash = req.getContextPath() + "/pic/icon/trash.svg";
//				obj.put("meal_pic", url);
//				obj.put("meal_name", meal_name);
//				obj.put("mealno", mealno);
//				obj.put("meal_amount", item.getMeal_amount());
//				obj.put("meal_price", item.getMeal_price());
//				obj.put("meal_set", item.getMeal_set());
//				String meal_note = item.getMeal_note();
				// ��ƥ��g�����մ���
//				if (meal_note == null) {   //json
//					meal_note = "";
//				}
//				obj.put("meal_note", meal_note);
//				obj.put("trash", trash);
//				array.put(obj);
//			}

//			session.setAttribute("shoppingcartJsonArray", array);    //json
//			res.setContentType("text/plain");
//			res.setCharacterEncoding("UTF-8");
//			PrintWriter out = res.getWriter();
//			out.write(array.toString());
//			out.flush();
//			out.close();

//			String url = "/front-end/shopping_system/EShop2.jsp";
//			RequestDispatcher rd = req.getRequestDispatcher(url);
//			rd.forward(req, res);

		
		
		
		// ���b�A�p���ʪ����\�I�����`��
		if (action.equals("CHECKOUT")) {
			
//			JSONArray array = new JSONArray();
			
			
			
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
			
			try {
				
				if(!(buylist==null) && buylist.size()>0) {
					Integer total = 0;
					for (int i = 0; i < buylist.size(); i++) {
						OnlineDetailVO order = buylist.get(i);
						Integer price = order.getMeal_price();
						Integer amount = order.getMeal_amount();
						total += (price * amount);
					}
					String sum = String.valueOf(total);
					req.setAttribute("sum", sum);
				
				
				/***********************1.�����ШD�Ѽ� - ��J�榡�����~�B�z*************************/
				MemberVO memberVO = (MemberVO) session.getAttribute("memberVO");//"setAttribute���N��"�A�hget�A�j���૬������A���s�W�r�A
				Integer memno = memberVO.getMemno();//�γo�ӷs����h���Ʈw��memno
				//�h���W��""�ѼơA�j���૬��A�ᤩ�s���ܼơA�ϥΦ��ܼƨ��o�Ӫ����ݩʡA�s�J��x���ȱo�ѼơC
				
				String address = req.getParameter("address");
				String addressReg = "^[(\\u4e00-\\u9fa5)(0-9)]{0,30}$";
				if(!address.trim().matches(addressReg)) { //���\�ŭȡA�ŭȪ�ܥ~�a�q��
//					System.out.print(3);
					errorMsgs.add("�a�}�u��O����B�Ʀr�A�B���ץ����b0��30�r����");
				}
				
				java.sql.Timestamp set_time = null;
				try {
					set_time = java.sql.Timestamp.valueOf(req.getParameter("set_time").trim());
				} catch (IllegalArgumentException e) {
					set_time = new java.sql.Timestamp(System.currentTimeMillis());
					errorMsgs.add("�п�J���\�ɶ�");
				}
				
//				Integer pay_way = null;
//				try {
//					pay_way = new Integer(req.getParameter("pay_way").trim());
//				} catch(NumberFormatException e) {
//					pay_way = 0;
//					errorMsgs.add("�ж�I�ڤ覡.");
//				}
				Integer pay_way =new Integer(req.getParameter("pay_way"));
				System.out.println(pay_way);
				
				
				OnlineOrderVO onlineOrderVO = new OnlineOrderVO();
				onlineOrderVO.setMemno(memno);
				onlineOrderVO.setAddress(address);
				onlineOrderVO.setSet_time(set_time);
				onlineOrderVO.setPay_way(pay_way);
				
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("onlineOrderVO", onlineOrderVO); // �t����J�榡���~��onlineOrderVO����,�]�s�Jreq
					RequestDispatcher failureView = req
							.getRequestDispatcher("/front-end/shopping_system/Cart.jsp");
					failureView.forward(req, res);
					return; //�{�����_
				}
				//�����=======================================
				Integer empno = 2;
				Integer pay_status = 0;
				Integer meal_status = 0;
				
				/***************************2.�}�l�s�W���*****************************************/
				OnlineOrderService onlineOrderSvc = new OnlineOrderService();
				OnlineDetailService svc = new OnlineDetailService();
				


				
//				Integer empno = (Integer) session.getAttribute("empno");
//				if (empno==null) {
//					empno = 1;
//				}
//				session.setAttribute("empno", empno);
//				
//				
////			Integer empno = new Integer(session.getAttribute(empno).trim());
//				Integer memno = new Integer(session.getAttribute(memno).trim());
//				Integer pay_status = new Integer(session.getAttribute(pay_status).trim());
//				String address = new String(session.getAttribute(address).trim());
//				Timestamp set_time = new java.sql.Timestamp(System.currentTimeMillis());
//				Timestamp create_time = new Timestamp(System.currentTimeMillis());
//				Integer pay_way = new Integer(session.getAttribute(pay_way).trim());
				
				
				
//				String olnoStr = (String) session.getAttribute("olno"); //json
//				session.removeAttribute("olno");
//				System.out.println(olnoStr);
				
//				if (olnoStr == null) {
				
				
				
				// �s�W�q��P�ɡA���o�s�W�����έq��s��
				if(pay_way == 0) { //�f��I�ګ���B�z
					onlineOrderSvc.addOrderWithDetail(empno, memno, pay_status, address, set_time, total, pay_way,
							buylist);
					
					session.removeAttribute("shoppingcart");
					session.removeAttribute("sum");
					
					String url = "/front-end/shopping_system/Checkout.jsp";
					RequestDispatcher rd = req.getRequestDispatcher(url);
					rd.forward(req, res);
					
				} else if(pay_way ==1) {//�H�Υd�I�ګ���B�z
				    session.setAttribute("onlineOrderVO", onlineOrderVO);
				    
				    
				    
				    String url = "/front-end/shopping_system/payByCard.jsp";
					RequestDispatcher rd = req.getRequestDispatcher(url);
					rd.forward(req, res);
					
				}
//					Integer olno = onlineOrderSvc.addOrderWithDetail(empno, memno, pay_status, address, set_time, total, pay_way,
//							buylist);
//					array = (JSONArray) session.getAttribute("shoppingcartJsonArray");
//					for (int i = 0; i < array.length(); i++) {
//						array.getJSONObject(i).put("olno", olno);
//						array.getJSONObject(i).put("memno", memno);
//						array.getJSONObject(i).put("pay_status", pay_status);
//						array.getJSONObject(i).put("meal_status", meal_status);
//						array.getJSONObject(i).put("pay_way", pay_way);
//						array.getJSONObject(i).put("empno", empno);
//						array.getJSONObject(i).put("CMS", svc.getCMS(olno));
//					}
//				}
//				else { //�u�W�q�椣�|���b�q�椺�[�I�����D�A�ӬO�������ߥt�@�i�s���q��
//					// �[�I
//					Integer addMealLiveno = new Integer(livenoStr);
//					for (LiveDetailVO item : buylist) {
//						item.setMeal_set(item.getMeal_set()+100);
//						item.setMeal_status(0);
//						System.out.println(item.getMeal_set());
//						svc.addOrderDetail(addMealLiveno, item.getMealno(), item.getMeal_amount(), item.getMeal_price(),
//								item.getMeal_set());
//
//					}
//
//					array = (JSONArray) session.getAttribute("shoppingcartJsonArray");
//					for (int i = 0; i < array.length(); i++) {
//						array.getJSONObject(i).put("liveno", addMealLiveno);
//						array.getJSONObject(i).put("tableno", tableno);
//						array.getJSONObject(i).put("pay_status", pay_status);
//						array.getJSONObject(i).put("meal_status", meal_status);
//						array.getJSONObject(i).put("pay_way", pay_way);
//						array.getJSONObject(i).put("empno", empno);
//						array.getJSONObject(i).put("CMS", svc.getCMS(addMealLiveno));
//					}
//				}
					
//				String sum = String.valueOf(total);
//				req.setAttribute("sum", sum);
			}
			

			
//			/***************************3.�s�W����,�ǳ����(Send the Success view)***********/

				
//			session.removeAttribute("shoppingcart");
//			session.removeAttribute("shoppingcartJsonArray");
//			
//			res.setContentType("text/plain");
//			res.setCharacterEncoding("UTF-8");
//			PrintWriter out = res.getWriter();
//			out.write(array.toString());
//			out.flush();
//			out.close();
		

			
		} catch(Exception e) {
			errorMsgs.add(e.getMessage());
			RequestDispatcher failureView = req
					.getRequestDispatcher("/front-end/shopping_system/Cart.jsp");
			failureView.forward(req, res);
		}
		}
		
		if ("confirm".equals(action)) {
			session.removeAttribute("shoppingcart");
			String url = "/front-end/shopping_system/EShop2.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(url); 
			successView.forward(req, res);
			return;
			}
		
	}
		
		private OnlineDetailVO getMeal(HttpServletRequest req) {
			
			String mealno = req.getParameter("mealno");
			String meal_amount = req.getParameter("meal_amount");
			String meal_price = req.getParameter("meal_price");
			String meal_set = req.getParameter("meal_set");
			String meal_status = req.getParameter("meal_status");
			
			OnlineDetailVO meal = new OnlineDetailVO();
			System.out.println(meal_price);
			meal.setMealno(Integer.parseInt(mealno));
			meal.setMeal_amount(Integer.parseInt(meal_amount));
			meal.setMeal_price(Integer.parseInt(meal_price));
			meal.setMeal_set(Integer.parseInt(meal_set));
			meal.setMeal_status(Integer.parseInt(meal_status));
			return meal;
		
	}
}
