package com.live_order.controller;

import java.util.*;
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

import com.live_detail.model.LiveDetailVO;
import com.live_order.model.LiveOrderService;

public class LiveOrderServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		req.setCharacterEncoding("UTF-8");
		HttpSession session = req.getSession();

		@SuppressWarnings("unchecked")
		List<LiveDetailVO> buylist = (Vector<LiveDetailVO>) session.getAttribute("shoppingcart");
		String action = req.getParameter("action");

		if (!"CHECKOUT".equals(action)) {

			// §R°£ÁÊª«¨®¤¤ªº®ÑÄy
			if (action.equals("DELETE")) {
				String del = req.getParameter("del");
				int d = Integer.parseInt(del);
				buylist.remove(d);
			}
			// ·s¼W®ÑÄy¦ÜÁÊª«¨®¤¤
			else if (action.equals("ADD")) {
				// ¨ú±o«á¨Ó·s¼Wªº®ÑÄy
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
			}

			session.setAttribute("shoppingcart", buylist);
			String url = "/back-end/live_order/OrderSystem.jsp";
			RequestDispatcher rd = req.getRequestDispatcher(url);
			rd.forward(req, res);
		}

//		 µ²±b¡A­pºâÁÊª«¨®®ÑÄy»ù¿úÁ`¼Æ
		else if ("CHECKOUT".equals(action)) {
			Integer total = 0;
			for (int i = 0; i < buylist.size(); i++) {
				LiveDetailVO order = buylist.get(i);
				Integer price = order.getMeal_price();
				Integer amount = order.getMeal_amount();
				total += (price * amount);
			}
			LiveOrderService liveOrderSvc = new LiveOrderService();

			//°²¸ê®Æ³á³á³á³á³á³á³á³á³á³á³á³á³á³á³á³á³á³á³á³á³á³á³á³á³á³á³á³á³á³á³á³á³á³á³á³á³á³á³á³á³á³á³á³á³á³á³á³á³á³á³á³á³á³á³á³á³á³á³á³á³á³á³á
			Integer empno = 2;
			Integer tableno = 2;
			Integer pay_status = 0;
			Integer pay_way = 0;

//			LiveOrderService liveOrderSvc = new LiveOrderService();
//			Integer empno = new Integer(session.getAttribute(empno).trim());
//			Integer tableno = new Integer(session.getAttribute(tableno).trim());
//			Integer pay_status = new Integer(session.getAttribute(pay_status).trim());
//			Integer pay_way = new Integer(session.getAttribute(pay_way).trim());

			liveOrderSvc.addOrderWithDetail(empno, tableno, pay_status, total, pay_way, buylist);

			String sum = String.valueOf(total);
			req.setAttribute("total", sum);
			session.removeAttribute("shoppingcart");
			String url = "/back-end/live_order/OrderSystem.jsp";
//			String url = "/back-end/live_order/Checkout.jsp";
			RequestDispatcher rd = req.getRequestDispatcher(url);
			rd.forward(req, res);
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