package com.news.controller;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import com.news.model.NewsVO;

@WebServlet("/showOneNews")
public class showOneNews extends HttpServlet {
	private static final long serialVersionUID = 1L;

	Connection con;

	public showOneNews() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		res.setContentType("text/html; charset=Big5");
		PrintWriter out = res.getWriter();
		NewsVO newsVO = null;
		try {
			Statement stmt = con.createStatement();
			String id = req.getParameter("id").trim();
			ResultSet rs = stmt.executeQuery(

					"SELECT * FROM NEWS WHERE NEWSNO =" + id);

				while (rs.next()) {
				
				newsVO = new NewsVO();
				newsVO.setNewsno(rs.getInt("NEWSNO"));
				newsVO.setEmpno(rs.getInt("EMPNO"));
				newsVO.setNews_title(rs.getString("NEWS_TITLE"));
				newsVO.setNews_content(rs.getString("NEWS_CONTENT"));
				newsVO.setNews_time(rs.getDate("NEWS_TIME"));
			}
				
			
			
			rs.close();
			stmt.close();
			
			req.setAttribute("newsVO", newsVO);
			
			String url = "/front-end/news/showNews.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(url); // 成功轉交 listOneEmp.jsp
			successView.forward(req, res);
		} catch (Exception e) {

			
		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doGet(request, response);
	}

	public void init() throws ServletException {

		try {
			Context ctx = new javax.naming.InitialContext();
			DataSource ds = (DataSource) ctx.lookup("java:comp/env/jdbc/CFA103_G4");
			con = ds.getConnection();
		} catch (NamingException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public void destroy() {
		try {
			if (con != null)
				con.close();
		} catch (SQLException e) {
			System.out.println(e);
		}
	}

}
