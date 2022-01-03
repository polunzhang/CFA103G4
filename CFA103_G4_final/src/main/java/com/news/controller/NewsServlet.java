package com.news.controller;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Date;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import com.news.model.*;
import com.news_pic.model.News_PicDAO;
import com.news_pic.model.News_PicService;

@MultipartConfig(fileSizeThreshold = 1024 * 1024, maxFileSize = 5 * 1024 * 1024, maxRequestSize = 5 * 5 * 1024 * 1024)
public class NewsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}

	
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {	
		
		req.setCharacterEncoding("UTF-8");
		
		String action = req.getParameter("action");
		
		if ("getAll".equals(action)) {
			
			NewsDAO dao = new NewsDAO();
			List<NewsVO> list = dao.getAll();
			
			HttpSession session = req.getSession();
			session.setAttribute("list", list); 
			String url = "/back-end/news/listAllNews_getFromSession.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(url);// ���\���listAllEmp2_getFromSession.jsp
			successView.forward(req, res);
			return;
			
		}
		
		if("getOne_For_InsertPic".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			
			try {
				String str = req.getParameter("newsno");
				if (str == null || (str.trim()).length() == 0) {
					errorMsgs.add("�п�J�̷s�����s��");
				}
				
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/back-end/news/listAllNews.jsp");
					failureView.forward(req, res);
					return;
				}
				
				Integer newsno = null;
				try {
					newsno = new Integer(str);
				} catch (Exception e) {
					errorMsgs.add("�̷s�����s���榡�����T");
				}
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/back-end/news/listAllNews.jsp");
					failureView.forward(req, res);
					return;//�{�����_
				}
				
				NewsService newsSvc=new NewsService();
				NewsVO newsVO=newsSvc.getOneNews(newsno);
				if (newsVO == null) {
					errorMsgs.add("�d�L���");
				}
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/back-end/news/listAllNews.jsp");
					failureView.forward(req, res);
					return;//�{�����_
				}
				
				req.setAttribute("newsVO", newsVO); // ��Ʈw���X��empVO����,�s�Jreq
				String url = "/back-end/news/uploadPic.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // ���\��� listOneEmp.jsp
				successView.forward(req, res);
				
				
			}catch (Exception e) {
				errorMsgs.add("�L�k���o���:" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/back-end/news/listAllNews.jsp");
				failureView.forward(req, res);
			}
		}
		
		if ("getOne_For_Display".equals(action)) {
			
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			
			try {
				String str = req.getParameter("newsno");
				if (str == null || (str.trim()).length() == 0) {
					errorMsgs.add("�п�J�̷s�����s��");
				}
				
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/back-end/news/select_page.jsp");
					failureView.forward(req, res);
					return;
				}
				Integer newsno = null;
				try {
					newsno = new Integer(str);
				} catch (Exception e) {
					errorMsgs.add("�̷s�����s���榡�����T");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/back-end/news/select_page.jsp");
					failureView.forward(req, res);
					return;//�{�����_
				}
				
				NewsService newsSvc=new NewsService();
				NewsVO newsVO=newsSvc.getOneNews(newsno);
				if (newsVO == null) {
					errorMsgs.add("�d�L���");
				}
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/back-end/news/select_page.jsp");
					failureView.forward(req, res);
					return;//�{�����_
				}
				
				req.setAttribute("newsVO", newsVO); // ��Ʈw���X��empVO����,�s�Jreq
				String url = "/back-end/news/listOneNews.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // ���\��� listOneEmp.jsp
				successView.forward(req, res);
				
				
			}catch (Exception e) {
				errorMsgs.add("�L�k���o���:" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/back-end/news/select_page.jsp");
				failureView.forward(req, res);
			}
			
		}
		
		if ("getDate_For_Display".equals(action)) {

			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			
			try {
				String str=req.getParameter("news_time");
				
				if (str == null || (str.trim()).length() == 0) {
					errorMsgs.add("�п�J�̷s�������");
				}
				
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/back-end/news/select_page.jsp");
					failureView.forward(req, res);
					return;
				}
				
				Date date=null;
				
				try {
					date = java.sql.Date.valueOf(str);
				} catch (Exception e) {
					errorMsgs.add("�̷s��������榡�����T");
				}
				
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/back-end/news/select_page.jsp");
					failureView.forward(req, res);
					return;//�{�����_
				}
				
				NewsService newsSvc=new NewsService();
				List<NewsVO> list=newsSvc.getSameDate(date);
				
				if (list.size() == 0) {
					errorMsgs.add("�d�L���");
				}
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/back-end/news/select_page.jsp");
					failureView.forward(req, res);
					return;//�{�����_
				}
				
				req.setAttribute("list", list); // ��Ʈw���X��empVO����,�s�Jreq
				String url = "/back-end/news/listSameDateNews.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // ���\��� listOneEmp.jsp
				successView.forward(req, res);
				
			}catch (Exception e) {
				errorMsgs.add("�L�k���o���:" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/back-end/news/select_page.jsp");
				failureView.forward(req, res);
			}
		}
		
		if ("insert".equals(action)) { 
			
			
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			
			System.out.println(66666777);
			try {
				Integer empno = new Integer(req.getParameter("empno").trim());
				
				String news_title = req.getParameter("news_title").trim();
				if (news_title == null || news_title.trim().length() == 0) {
					errorMsgs.add("���D�ФŪť�");
				}
				
				String news_content = req.getParameter("news_content").trim();
				if (news_content == null || news_content.trim().length() == 0) {
					errorMsgs.add("���e�ФŪť�");
				}
				
				Part part = req.getPart("upnewsfile");
				System.out.println(part.getSize());
				InputStream in = part.getInputStream();
				byte[] news_pic = new byte[in.available()];
				in.read(news_pic);
				in.close();
				System.out.println(part);
				/*���~�d�I*/
				if(part.getSize() <= 0) {
					errorMsgs.add("�|���W�ǹϤ�!");		
				}
				
				java.sql.Date news_time = null;
				
					news_time = java.sql.Date.valueOf(java.time.LocalDate.now() );
				
				NewsVO newsVO=new NewsVO();
				newsVO.setEmpno(empno);
				newsVO.setNews_title(news_title);
				newsVO.setNews_content(news_content);
				newsVO.setNews_title(news_title);
				
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("newsVO", newsVO); // �t����J�榡���~��empVO����,�]�s�Jreq
					RequestDispatcher failureView = req
							.getRequestDispatcher("/back-end/news/addNews.jsp");
					failureView.forward(req, res);
					return;
				}
				
				NewsService newsSvc=new NewsService();
				newsVO=newsSvc.addNews(empno,news_title,news_content,news_time,news_pic);
				String url = "/back-end/news/listAllNews.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // �s�W���\�����listAllEmp.jsp
				successView.forward(req, res);
				
			}catch (Exception e) {
				errorMsgs.add(e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/back-end/news/addNews.jsp");
				failureView.forward(req, res);
			}
		}
		
		
		if ("getOne_For_Update".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			try {
				Integer newsno = new Integer(req.getParameter("newsno"));
				
				NewsService newsSvc = new NewsService();
				NewsVO newsVO = newsSvc.getOneNews(newsno);
				
				req.setAttribute("newsVO", newsVO);         // ��Ʈw���X��empVO����,�s�Jreq
				String url = "/back-end/news/update_news_input.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);// ���\��� update_emp_input.jsp
				successView.forward(req, res);
				
			}catch (Exception e) {
				errorMsgs.add("�L�k���o�n�ק諸���:" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/back-end/news/listAllNews.jsp");
				failureView.forward(req, res);
			}
			
		}
		
		
		if ("update".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			HttpSession session=req.getSession();
			
			try {
				Integer newsno = new Integer(req.getParameter("newsno").trim());
				Integer empno = new Integer(req.getParameter("empno").trim());
				
				String news_title = req.getParameter("news_title").trim();
				if (news_title == null || news_title.trim().length() == 0) {
					errorMsgs.add("���D�ФŪť�");
				}
				
				String news_content = req.getParameter("news_content").trim();
				if (news_content == null || news_content.trim().length() == 0) {
					errorMsgs.add("���e�ФŪť�");
				}
				
				
				
				java.sql.Date news_time = null;
				try {
					news_time = java.sql.Date.valueOf(req.getParameter("news_time").trim());
				} catch (IllegalArgumentException e) {
					news_time=new java.sql.Date(System.currentTimeMillis());
					errorMsgs.add("�п�J���!");
				}
				
				
				NewsVO newsVO=new NewsVO();
				newsVO.setNewsno(newsno);
				newsVO.setNewsno(empno);
				newsVO.setEmpno(empno);
				newsVO.setNews_title(news_title);
				newsVO.setNews_content(news_content);
				newsVO.setNews_title(news_title);
				
				
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("newsVO", newsVO); // �t����J�榡���~��empVO����,�]�s�Jreq
					RequestDispatcher failureView = req
							.getRequestDispatcher("/back-end/news/update_news_input.jsp");
					failureView.forward(req, res);
					return;
				}
				
				Part part = req.getPart("updatenewsfile");
				System.out.println(part.getSize());
				if(part.getSize() > 0) {
				InputStream in = part.getInputStream();
				byte[] news_pic = new byte[in.available()];
				in.read(news_pic);
				in.close();
				System.out.println(part);
				News_PicDAO dao = new News_PicDAO();
				News_PicService news_picSvc = new News_PicService();
				news_picSvc.updateNewsPic(newsno,news_pic);
				}
			
				NewsService newsSvc=new NewsService();
				newsVO=newsSvc.updateNews(newsno,empno,news_title,news_content,news_time);
				
				String url = "/back-end/news/listAllNews.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // �s�W���\�����listAllEmp.jsp
				successView.forward(req, res);
				
			}
			catch (Exception e) {
				e.printStackTrace();
				errorMsgs.add("�ק��ƥ���:"+e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/back-end/news/update_news_input.jsp");
				failureView.forward(req, res);
			}
		}
		
		if ("delete".equals(action)) { // �Ӧ�listAllEmp.jsp

			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			
			try {
				/***************************1.�����ШD�Ѽ�***************************************/
				Integer newsno = new Integer(req.getParameter("newsno"));
				/***************************2.�}�l�R�����***************************************/
				NewsService newsSvc = new NewsService();			
				newsSvc.deleteNews(newsno);			
				/***************************3.�R������,�ǳ����(Send the Success view)***********/								
				String url = "/back-end/news/listAllNews.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);// �R�����\��,���^�e�X�R�����ӷ�����
				successView.forward(req, res);
				/***************************��L�i�઺���~�B�z**********************************/
			} catch (Exception e) {
				errorMsgs.add("�R����ƥ���:"+e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/back-end/news/listAllNews.jsp");
				failureView.forward(req, res);
			}
		}
	}

}
