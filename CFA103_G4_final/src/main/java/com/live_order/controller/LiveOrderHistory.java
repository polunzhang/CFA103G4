package com.live_order.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONArray;
import org.json.JSONObject;

import com.live_detail.model.LiveDetailService;
import com.live_detail.model.LiveDetailVO;
import com.live_order.model.LiveOrderService;
import com.live_order.model.LiveOrderVO;
import com.meal.model.MealService;

@WebServlet("/LiveOrderHistory")
public class LiveOrderHistory extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		HttpSession session = req.getSession();

		String action = req.getParameter("action");
		JSONArray array = new JSONArray();
		LiveDetailService LDSvc = new LiveDetailService();
		LiveOrderService LOSvc = new LiveOrderService();
		MealService mealSvc = new MealService();

		if ("FindByCompositeQuery".equals(action)) {
			
			req.setCharacterEncoding("UTF-8");
			String uri = req.getRequestURI();
			
			
				String livenoStr = req.getParameter("liveno");
				String empnoStr = req.getParameter("empno");
				String create_timeStr = req.getParameter("create_time");
				Map<String, String[]> map = new TreeMap<String, String[]>();
				map.put("liveno", new String[] { livenoStr });
				map.put("empno", new String[] { empnoStr });
				map.put("DATE(create_time)", new String[] { create_timeStr });
				
				List<LiveOrderVO> listLOVO = LOSvc.getAll(map);
				for(LiveOrderVO item:listLOVO) {
					System.out.println(item.getEmpno());
				}
				session.setAttribute("listLOVO", listLOVO);
				session.setAttribute("url", "back-end/live_order/LiveOrderHistoryRusult.jsp");

				RequestDispatcher d = req.getRequestDispatcher("back-end/live_order/LiveOrderHistoryRusult.jsp");
				d.forward(req, res);
				return;//µ{¦¡¤¤Â_
			
			

		} else if ("FindByPrimaryKey".equals(action)) {
			Integer livenostr = Integer.valueOf(req.getParameter("liveno"));
			List<LiveDetailVO> listLD = LDSvc.getListByLiveNO(livenostr);

			for (LiveDetailVO item : listLD) {
				JSONObject obj = new JSONObject();
				Integer mealno = item.getMealno();

				String meal_pic = req.getContextPath() + "/MealPicServlet?id=" + mealno;
				obj.put("meal_pic", meal_pic);

				String meal_name = mealSvc.getOneMeal(mealno).getMeal_name();
				obj.put("meal_name", meal_name);

				obj.put("mealno", mealno);

				Integer meal_amount = item.getMeal_amount();
				obj.put("meal_amount", meal_amount);

				Integer meal_price = item.getMeal_price();
				obj.put("meal_price", meal_price);

				String meal_note = item.getMeal_note();
				if (meal_note == null)
					meal_note = "";

				obj.put("meal_note", meal_note);
				array.put(obj);
			}

			res.setContentType("text/plain");
			res.setCharacterEncoding("UTF-8");

			PrintWriter out = res.getWriter();
			out.write(array.toString());
			out.flush();
			out.close();
		}

	}

}
