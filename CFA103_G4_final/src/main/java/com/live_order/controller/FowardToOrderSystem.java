package com.live_order.controller;

import java.io.IOException;
import java.util.List;
import java.util.Vector;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.live_detail.model.LiveDetailVO;

/**
 * Servlet implementation class fowardToOrderSystem
 */
@WebServlet("/fowardToOrderSystem")
public class FowardToOrderSystem extends HttpServlet {
	private static final long serialVersionUID = 1L;
    public FowardToOrderSystem() {
    }
	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		HttpSession session = req.getSession();
		String action = req.getParameter("action");
		String uri = req.getRequestURI();
		if ("add-meal".equals(action)) {
			String livenostr = req.getParameter("liveno");
			String tablenostr = req.getParameter("tableno");
			System.out.println(uri+"----------------");
			Integer liveno = Integer.valueOf(livenostr);
			Integer tableno = Integer.valueOf(tablenostr);
			session.setAttribute("liveno", liveno);
			session.setAttribute("tableno", tableno);
			session.setAttribute("url", "back-end/live_order/UnServedList.jsp");

			RequestDispatcher d = req.getRequestDispatcher("back-end/live_order/OrderSystem2.jsp");
			d.forward(req, res);
			return;//µ{¦¡¤¤Â_
		}
	}
}
