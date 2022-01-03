package com.live_order.controller;

import java.util.*;
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

import org.json.JSONArray;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.live_detail.model.LiveDetailService;
import com.live_detail.model.LiveDetailVO;
import com.live_order.model.LiveOrderService;
import com.live_order.model.LiveOrderVO;
import com.meal.model.MealJDBCDAO;
import com.meal.model.MealService;

public class MealStatusChange extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		req.setCharacterEncoding("UTF-8");
		HttpSession session = req.getSession();

		@SuppressWarnings("unchecked")
		String action = req.getParameter("action");

		if ("listOrderDetail_ByCompositeQuery".equals(action)) {
			Map<String, String[]> map = req.getParameterMap();

			LiveDetailService liveDetailSvc = new LiveDetailService();
			List<LiveDetailVO> list = liveDetailSvc.getAll(map);

			req.setAttribute("listOrderDetail_ByCompositeQuery", list);
			RequestDispatcher successView = req.getRequestDispatcher(req.getRequestURI());

		} else if ("change_meal_status".equals(action)) {
			Map<String, String[]> map = req.getParameterMap();
			LiveDetailService liveDetailSvc = new LiveDetailService();
			List<LiveDetailVO> list = liveDetailSvc.getAll(map);
			LiveDetailService svc = new LiveDetailService();
			LiveOrderService svc2 = new LiveOrderService();
			MealService svc3 = new MealService();
			JSONObject obj = null;
			JSONArray arr = new JSONArray();
			for (LiveDetailVO liveDetailVO : list) {

				obj = new JSONObject();
				liveDetailVO.setMeal_status(liveDetailVO.getMeal_status() + 1);
				svc.updateOrderDetail(liveDetailVO);
				LiveOrderVO liveOrderVO = svc2.getOneByLiveNO(liveDetailVO.getLiveno());
				Integer liveno = liveDetailVO.getLiveno();
				obj.put("liveno", liveno);
				obj.put("tableno", liveOrderVO.getTableno());
				obj.put("create_time", liveOrderVO.getCreate_time());
				obj.put("CMS", svc.getCMS(liveno));
				Integer mealno = liveDetailVO.getMealno();
				obj.put("mealno", mealno);
				String url = req.getContextPath() + "/MealPicServlet?id=" + mealno;
				obj.put("meal_pic", url);
				String meal_name = svc3.getOneMeal(mealno).getMeal_name();
				obj.put("meal_name", meal_name);
				obj.put("meal_amount", liveDetailVO.getMeal_amount());
				obj.put("meal_status", liveDetailVO.getMeal_status());
				String meal_note = liveDetailVO.getMeal_note();
				if (meal_note == null) meal_note ="";
				System.out.println(meal_note);
				obj.put("meal_note", meal_note);
				arr.put(obj);
				System.out.println("單號" + liveno + ":" + "餐點編號" + mealno + ":" + liveDetailVO.getMeal_status() + "套餐編號"
						+ liveDetailVO.getMeal_status());
			}

			res.setContentType("text/plain");
			res.setCharacterEncoding("UTF-8");
			PrintWriter out = res.getWriter();
			out.write(arr.toString());
			out.flush();
			out.close();

		} else if ("change_meal_status_reverse".equals(action)) {
			Map<String, String[]> map = req.getParameterMap();
			LiveDetailService liveDetailSvc = new LiveDetailService();
			List<LiveDetailVO> list = liveDetailSvc.getAll(map);
			LiveDetailService svc = new LiveDetailService();
			LiveOrderService svc2 = new LiveOrderService();
			MealService svc3 = new MealService();
			JSONObject obj = null;
			JSONArray arr = new JSONArray();
			for (LiveDetailVO liveDetailVO : list) {

				obj = new JSONObject();
				liveDetailVO.setMeal_status(liveDetailVO.getMeal_status() - 10);
				
				svc.updateOrderDetail(liveDetailVO);
				
				LiveOrderVO liveOrderVO = svc2.getOneByLiveNO(liveDetailVO.getLiveno());
				Integer liveno = liveDetailVO.getLiveno();
				obj.put("liveno", liveno);
				obj.put("tableno", liveOrderVO.getTableno());
				obj.put("create_time", liveOrderVO.getCreate_time());
				obj.put("CMS", svc.getCMS(liveno));
				Integer mealno = liveDetailVO.getMealno();
				obj.put("mealno", mealno);
				String url = req.getContextPath() + "/MealPicServlet?id=" + mealno;
				obj.put("meal_pic", url);
				String meal_name = svc3.getOneMeal(mealno).getMeal_name();
				obj.put("meal_name", meal_name);
				obj.put("meal_amount", liveDetailVO.getMeal_amount());
				obj.put("meal_status", liveDetailVO.getMeal_status());
				String meal_note = liveDetailVO.getMeal_note();
				if (meal_note == null) meal_note ="";
				System.out.println(meal_note);
				obj.put("meal_note", meal_note);
				arr.put(obj);
				System.out.println("單號" + liveno + ":" + "餐點編號" + mealno + ":" + liveDetailVO.getMeal_status() + "套餐編號"
						+ liveDetailVO.getMeal_status());

			}

			res.setContentType("text/plain");
			res.setCharacterEncoding("UTF-8");
			PrintWriter out = res.getWriter();
			out.write(arr.toString());
			out.flush();
			out.close();
		} else if (action.equals("table-change")) {
			Map<String, String[]> map = req.getParameterMap();
			String tablenostr = map.get("tableno")[0];
			Integer tableno = null;
			if (!(tablenostr == ""))
				tableno = Integer.valueOf(tablenostr);

			System.out.println("更改桌號為"+tableno);
			session.setAttribute("tableno", tableno);
			res.setContentType("text/plain");
			res.setCharacterEncoding("UTF-8");
			PrintWriter out = res.getWriter();
			out.write("YEEEEEEEES");
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