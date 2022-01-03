package com.emper.controller;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.*;
import javax.servlet.http.*;

import com.emper.model.EmperService;
import com.emper.model.EmperVO;



public class EmperServlet extends HttpServlet{

	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");
		
if ("getOne_For_Update".equals(action)) { // 來自select_page_emper.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/*************************** 1.接收請求參數 ****************************************/
				Integer emperno = new Integer(req.getParameter("emperno"));

				/*************************** 2.開始查詢資料 ****************************************/
				EmperService emperSvc = new EmperService();
				EmperVO emperVO = emperSvc.getOneEmper(emperno);

				/*************************** 3.查詢完成,準備轉交(Send the Success view) ************/
				req.setAttribute("emperVO", emperVO); // 資料庫取出的emperVO物件,存入req
				String url = "/back-end/emper/updateEmper_input.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);// 成功轉交 updateEmper_input.jsp
				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 **********************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得要修改的資料:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/emper/select_page_emper.jsp");
				failureView.forward(req, res);
			}
		}

if ("update".equals(action)) { // 來自updateEmper_input.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 **********************/
				Integer emperno = new Integer(req.getParameter("emperno").trim());

				String emper_name = req.getParameter("emper_name");
				String emper_nameReg = "^[\u4e00-\u9fa5]{2,10}$";
				if (emper_name == null || emper_name.trim().length() == 0) {
					errorMsgs.add("權限名稱請勿空白");
				} else if (!emper_name.trim().matches(emper_nameReg)) { // 以下練習正則(規)表示式(regular-expression)
					errorMsgs.add("權限名稱: 只能是中文, 且長度必需在2到10之間");
				}

				
				EmperVO emperVO = new EmperVO();
				emperVO.setEmperno(emperno);
				emperVO.setEmper_name(emper_name);
				

				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("emperVO", emperVO); // 含有輸入格式錯誤的empVO物件,也存入req
					RequestDispatcher failureView = req.getRequestDispatcher("/back-end/emper/updateEmper_input.jsp");
					failureView.forward(req, res);
					return; // 程式中斷
				}

				/*************************** 2.開始修改資料 *****************************************/
				EmperService emperSvc = new EmperService();
				emperVO = emperSvc.updateEmper(emperno, emper_name);

				/*************************** 3.修改完成,準備轉交(Send the Success view) *************/
				req.setAttribute("emperVO", emperVO); // 資料庫update成功後,正確的的emperVO物件,存入req
				String url = "/back-end/emper/select_page_emper.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 修改成功後,轉交select_page_emper.jsp
				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 *************************************/
			} catch (Exception e) {
				errorMsgs.add("修改資料失敗:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/emper/updateEmper_input.jsp");
				failureView.forward(req, res);
			}
		}

if ("insert".equals(action)) { // 來自addEmper.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/*********************** 1.接收請求參數 - 輸入格式的錯誤處理 *************************/
				
				String emper_name = req.getParameter("emper_name");
				String emper_nameReg = "^[\u4e00-\u9fa5]{2,10}$";
				if (emper_name == null || emper_name.trim().length() == 0) {
					errorMsgs.add("權限名稱請勿空白");
				} else if (!emper_name.trim().matches(emper_nameReg)) { // 以下練習正則(規)表示式(regular-expression)
					errorMsgs.add("權限名稱: 只能是中文, 且長度必需在2到10之間");
				}

				EmperVO emperVO = new EmperVO();
				emperVO.setEmper_name(emper_name);

				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("emperVO", emperVO); // 含有輸入格式錯誤的empVO物件,也存入req
					RequestDispatcher failureView = req.getRequestDispatcher("/back-end/emper/addEmper.jsp");
					failureView.forward(req, res);
					return; // 程式中斷
				}

				/*************************** 2.開始新增資料 ***************************************/
				EmperService emperSvc = new EmperService();
				emperVO = emperSvc.addEmper(emper_name);

				/*************************** 3.新增完成,準備轉交(Send the Success view) ***********/
				String url = "/back-end/emper/select_page_emper.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 新增成功後轉交select_page_emper.jsp
				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 **********************************/
			} catch (Exception e) {
				errorMsgs.add(e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/emper/addEmper.jsp");
				failureView.forward(req, res);
			}
		}

if ("delete".equals(action)) { // 來自select_page_emper.jsp

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/*************************** 1.接收請求參數 ***************************************/
				Integer emperno = new Integer(req.getParameter("emperno"));

				/*************************** 2.開始刪除資料 ***************************************/
				EmperService emperSvc = new EmperService();
				emperSvc.deleteEmper(emperno);

				/*************************** 3.刪除完成,準備轉交(Send the Success view) ***********/
				String url = "/back-end/emper/select_page_emper.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);// 刪除成功後,轉交回送出刪除的來源網頁
				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 **********************************/
			} catch (Exception e) {
				errorMsgs.add("刪除資料失敗:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/emper/select_page_emper.jsp");
				failureView.forward(req, res);
			}
		}

	}
	
}
