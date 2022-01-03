package com.live_order.controller;

import java.util.*;
import java.io.*;
import java.sql.Date;
import java.time.Year;

import javax.servlet.*;
import javax.servlet.http.*;

import org.json.JSONArray;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.live_detail.model.LiveDetailService;
import com.live_detail.model.LiveDetailVO;
import com.live_order.model.LiveOrderService;
import com.live_order.model.LiveOrderVO;
import com.meal.model.MealJDBCDAO;

public class LiveOrderServlet2 extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		req.setCharacterEncoding("UTF-8");
		HttpSession session = req.getSession();

		@SuppressWarnings("unchecked")
		List<LiveDetailVO> buylist = (Vector<LiveDetailVO>) session.getAttribute("shoppingcart");

		String action = req.getParameter("action");

		if (!"CHECKOUT".equals(action)) {
			// 刪除購物車中的書籍
			if (action.equals("DELETE")) {
				String del = req.getParameter("del");
				int d = Integer.parseInt(del);
				buylist.remove(d);
			}
			// 新增書籍至購物車中
			else if (action.equals("ADD")) {
				// 取得後來新增的書籍
				LiveDetailVO meal = getMeal(req);
				if (buylist == null) {
					buylist = new Vector<LiveDetailVO>();
					buylist.add(meal);
				} else {
					if (buylist.contains(meal)) {
						LiveDetailVO innerLiveDetailVO = buylist.get(buylist.indexOf(meal));
						innerLiveDetailVO.setMeal_amount(innerLiveDetailVO.getMeal_amount() + meal.getMeal_amount());
					} else {
						buylist.add(meal);
					}
				}
			} else if (action.equals("UPDATE")) {
				String upd = req.getParameter("upd");
				int u = Integer.parseInt(upd);
				String meal_note = req.getParameter("meal_note");
				buylist.get(u).setMeal_note(meal_note);
			}

			session.setAttribute("shoppingcart", buylist);
			JSONArray array = new JSONArray();
			// 記得改成MealService
			MealJDBCDAO mealSvc = new MealJDBCDAO();
			Integer total = 0;

			for (LiveDetailVO item : buylist) {
				JSONObject obj = new JSONObject();
				Integer mealno = item.getMealno();
				String meal_name = mealSvc.findByPrimaryKey(mealno).getMeal_name();
				String url = req.getContextPath() + "/MealPicServlet?id=" + mealno;
				String trash = req.getContextPath() + "/pic/icon/trash.svg";
				obj.put("meal_pic", url);
				obj.put("meal_name", meal_name);
				obj.put("mealno", mealno);
				Integer meal_amount = item.getMeal_amount();
				obj.put("meal_amount", meal_amount);
				Integer meal_price = item.getMeal_price();
				obj.put("meal_price", meal_price);
				obj.put("meal_set", item.getMeal_set());
				String meal_note = item.getMeal_note();
				if (meal_note == null) {
					meal_note = "";
				}

				obj.put("meal_note", meal_note);
				obj.put("trash", trash);
				total += meal_amount * meal_price;
				obj.put("total", total);
				array.put(obj);
			}

			session.setAttribute("shoppingcartJsonArray", array);
			res.setContentType("text/plain");
			res.setCharacterEncoding("UTF-8");
			PrintWriter out = res.getWriter();
			out.write(array.toString());
			out.flush();
			out.close();

//			String url = "/back-end/live_order/OrderSystem2.jsp";
//			RequestDispatcher rd = req.getRequestDispatcher(url);
//			rd.forward(req, res);

		}

//		 結帳，計算購物車書籍價錢總數
		else if ("CHECKOUT".equals(action)) {

			JSONArray array = new JSONArray();

			if (!(buylist == null) && buylist.size() > 0) {

				Integer total = 0;
				for (int i = 0; i < buylist.size(); i++) {
					LiveDetailVO order = buylist.get(i);
					Integer price = order.getMeal_price();
					Integer amount = order.getMeal_amount();
					total += (price * amount);
				}
				LiveOrderService liveOrderSvc = new LiveOrderService();
				LiveDetailService svc = new LiveDetailService();

				Integer pay_status = 0;
				Integer pay_way = 0;
				Integer meal_status = 0;

				Integer empno = (Integer) session.getAttribute("empno");

				if (empno == null) {
					empno = 1;
				}
				session.setAttribute("empno", empno);

				Integer tableno = (Integer) session.getAttribute("tableno");
				session.setAttribute("tableno", tableno);

//				LiveOrderService liveOrderSvc = new LiveOrderService();
//				Integer pay_status = new Integer(session.getAttribute(pay_status).trim());
//				Integer pay_way = new Integer(session.getAttribute(pay_way).trim());

				Integer liveno = (Integer) session.getAttribute("liveno");

				if (liveno == null) {
					// 新增訂單同時，取得新增的內用訂單編號
					liveno = liveOrderSvc.addOrderWithDetail(empno, tableno, pay_status, total, pay_way, buylist);
					session.removeAttribute("url");
				} else {
					// 加點
					for (LiveDetailVO item : buylist) {
						item.setMeal_set(item.getMeal_set() + 5);
						item.setMeal_status(0);
						svc.addOrderDetail(liveno, item.getMealno(), item.getMeal_amount(), item.getMeal_price(),
								item.getMeal_set());
					}
				}
				if (tableno == null)
					tableno = 0;

				array = (JSONArray) session.getAttribute("shoppingcartJsonArray");
				for (int i = 0; i < array.length(); i++) {
					array.getJSONObject(i).put("liveno", liveno);
					array.getJSONObject(i).put("tableno", tableno);
					array.getJSONObject(i).put("pay_status", pay_status);
					array.getJSONObject(i).put("meal_status", meal_status);
					array.getJSONObject(i).put("pay_way", pay_way);
					array.getJSONObject(i).put("empno", empno);
					array.getJSONObject(i).put("CMS", svc.getCMS(liveno));
					array.getJSONObject(i).put("total", total);
				}
			}
			session.removeAttribute("liveno");
			session.removeAttribute("tableno");
			session.removeAttribute("shoppingcart");
			session.removeAttribute("shoppingcartJsonArray");
			session.removeAttribute("url");

//			String url = (String) session.getAttribute("url");
//			System.out.println(url + "==================");
//			if (!(url==null)) {
//				RequestDispatcher rd = req.getRequestDispatcher(url);
//				rd.forward(req, res);
//				return;
//			}

			res.setContentType("text/plain");
			res.setCharacterEncoding("UTF-8");
			PrintWriter out = res.getWriter();
			out.write(array.toString());
			out.flush();
			out.close();

		}

	}

	private LiveDetailVO getMeal(HttpServletRequest req) {

		String mealno = req.getParameter("mealno");
		String meal_amount = req.getParameter("meal_amount");
		String meal_price = req.getParameter("meal_price");
		String meal_set = req.getParameter("meal_set");
		String meal_status = req.getParameter("meal_status");

		LiveDetailVO meal = new LiveDetailVO();

		meal.setMealno(Integer.parseInt(mealno));
		meal.setMeal_amount(Integer.parseInt(meal_amount));
		meal.setMeal_price(Integer.parseInt(meal_price));
		meal.setMeal_set(Integer.parseInt(meal_set));
		meal.setMeal_status(Integer.parseInt(meal_status));
		return meal;
	}
}