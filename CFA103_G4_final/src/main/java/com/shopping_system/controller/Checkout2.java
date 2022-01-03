package com.shopping_system.controller;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.List;
import java.util.Vector;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.online_detail.model.OnlineDetailService;
import com.online_detail.model.OnlineDetailVO;
import com.online_order.model.OnlineOrderService;
import com.online_order.model.OnlineOrderVO;

public class Checkout2 extends HttpServlet{ //�H�Υd�I�ڡA�p���ʪ����\�I�����`�ơA����payByCard.jsp���ҡC
private static final long serialVersionUID = 1L;
	
	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		
		req.setCharacterEncoding("UTF-8");
		HttpSession session = req.getSession();
		
		@SuppressWarnings("unchecked")
		List<OnlineDetailVO> buylist = (Vector<OnlineDetailVO>) session.getAttribute("shoppingcart");
		
		String action = req.getParameter("action");
		
		if (action.equals("Checkout2")) {
//			System.out.println(777);
//					JSONArray array = new JSONArray();
					
					List<String> errorMsgs = new LinkedList<String>();
					// Store this set in the request scope, in case we need to
					// send the ErrorPage view.
					req.setAttribute("errorMsgs", errorMsgs);
					
					try {
//		System.out.println(666);			
							
							/***********************1.�����ШD�Ѽ� - ��J�榡�����~�B�z*************************/
							OnlineOrderVO onlineOrderVO = (OnlineOrderVO) session.getAttribute("onlineOrderVO");
							List<OnlineDetailVO> buylist2 = (Vector<OnlineDetailVO>) session.getAttribute("shoppingcart");
						
							Integer total = 0;
						if(!(buylist2==null) && buylist2.size()>0) {
							
							for (int i = 0; i < buylist2.size(); i++) {
								OnlineDetailVO order = buylist2.get(i);
								Integer price = order.getMeal_price();
								Integer amount = order.getMeal_amount();
								total += (price * amount);
							}
					}	
//							Integer memno = null;
//							try {
//								memno = new Integer(req.getParameter("memno").trim());
//							} catch(NumberFormatException e) {
//								memno = 0;
//								errorMsgs.add("�ж�|���s��.");
//							}
//							
//							String address = req.getParameter("address");
//							String addressReg = "^[(\\u4e00-\\u9fa5)(a-zA-Z0-9_)]{0,50}$";
//							if(!address.trim().matches(addressReg)) { //���\�ŭȡA�ŭȪ�ܥ~�a�q��
////							System.out.print(3);
//								errorMsgs.add("�a�}�u��O���B�^��r���B�Ʀr�M_�A�B���ץ����b0��50�r����");
//							}
//							
//							java.sql.Timestamp set_time = null;
//							try {
//								set_time = java.sql.Timestamp.valueOf(req.getParameter("set_time").trim());
//							} catch (IllegalArgumentException e) {
//								set_time = new java.sql.Timestamp(System.currentTimeMillis());
//								errorMsgs.add("�п�J���\�ɶ�");
//							}
							
//							Integer pay_way = null;
//							try {
//								pay_way = new Integer(req.getParameter("pay_way").trim());
//							} catch(NumberFormatException e) {
//								pay_way = 0;
//								errorMsgs.add("�ж�I�ڤ覡.");
//							}
							
//							Integer pay_way =new Integer(req.getParameter("pay_way"));
//							System.out.println(pay_way);
//							
							
							Integer memno = onlineOrderVO.getMemno();
		System.out.println(2);	
							String address = onlineOrderVO.getAddress();
							Timestamp set_time = onlineOrderVO.getSet_time();
							Integer pay_way = onlineOrderVO.getPay_way();
							
							if (!errorMsgs.isEmpty()) {
								req.setAttribute("onlineOrderVO", onlineOrderVO); // �t����J�榡���~��onlineOrderVO����,�]�s�Jreq
								RequestDispatcher failureView = req
										.getRequestDispatcher("/front-end/shopping_system/Cart.jsp");
								failureView.forward(req, res);
								return; //�{�����_
							}
							//�����=======================================
							Integer empno = 2;
							Integer pay_status = 1;
							Integer meal_status = 0;
							
							/***************************2.�}�l�s�W���*****************************************/
							OnlineOrderService onlineOrderSvc = new OnlineOrderService();
							OnlineDetailService svc = new OnlineDetailService();
							
							
							
							
//						Integer empno = (Integer) session.getAttribute("empno");
//						if (empno==null) {
//							empno = 1;
//						}
//						session.setAttribute("empno", empno);
//						
//						
////					Integer empno = new Integer(session.getAttribute(empno).trim());
//						Integer memno = new Integer(session.getAttribute(memno).trim());
//						Integer pay_status = new Integer(session.getAttribute(pay_status).trim());
//						String address = new String(session.getAttribute(address).trim());
//						Timestamp set_time = new java.sql.Timestamp(System.currentTimeMillis());
//						Timestamp create_time = new Timestamp(System.currentTimeMillis());
//						Integer pay_way = new Integer(session.getAttribute(pay_way).trim());
							
							
							
//						String olnoStr = (String) session.getAttribute("olno"); //json
//						session.removeAttribute("olno");
//						System.out.println(olnoStr);
							
//						if (olnoStr == null) {
							// �s�W�q��P�ɡA���o�s�W�����έq��s��
							
							onlineOrderSvc.addOrderWithDetail(empno, memno, pay_status, address, set_time, total, pay_way,
									buylist2);
							
//							Integer olno = onlineOrderSvc.addOrderWithDetail(empno, memno, pay_status, address, set_time, total, pay_way,
//									buylist);
//							array = (JSONArray) session.getAttribute("shoppingcartJsonArray");
//							for (int i = 0; i < array.length(); i++) {
//								array.getJSONObject(i).put("olno", olno);
//								array.getJSONObject(i).put("memno", memno);
//								array.getJSONObject(i).put("pay_status", pay_status);
//								array.getJSONObject(i).put("meal_status", meal_status);
//								array.getJSONObject(i).put("pay_way", pay_way);
//								array.getJSONObject(i).put("empno", empno);
//								array.getJSONObject(i).put("CMS", svc.getCMS(olno));
//							}
//						}
//						else { //�u�W�q�椣�|���b�q�椺�[�I�����D�A�ӬO�������ߥt�@�i�s���q��
//							// �[�I
//							Integer addMealLiveno = new Integer(livenoStr);
//							for (LiveDetailVO item : buylist) {
//								item.setMeal_set(item.getMeal_set()+100);
//								item.setMeal_status(0);
//								System.out.println(item.getMeal_set());
//								svc.addOrderDetail(addMealLiveno, item.getMealno(), item.getMeal_amount(), item.getMeal_price(),
//										item.getMeal_set());
		//
//							}
		//
//							array = (JSONArray) session.getAttribute("shoppingcartJsonArray");
//							for (int i = 0; i < array.length(); i++) {
//								array.getJSONObject(i).put("liveno", addMealLiveno);
//								array.getJSONObject(i).put("tableno", tableno);
//								array.getJSONObject(i).put("pay_status", pay_status);
//								array.getJSONObject(i).put("meal_status", meal_status);
//								array.getJSONObject(i).put("pay_way", pay_way);
//								array.getJSONObject(i).put("empno", empno);
//								array.getJSONObject(i).put("CMS", svc.getCMS(addMealLiveno));
//							}
//						}
							
							String sum = String.valueOf(total);
							req.setAttribute("sum", sum);
//						}
						
						
						
//					/***************************3.�s�W����,�ǳ����(Send the Success view)***********/
						
						
//					session.removeAttribute("shoppingcart");
//					session.removeAttribute("shoppingcartJsonArray");
//					
//					res.setContentType("text/plain");
//					res.setCharacterEncoding("UTF-8");
//					PrintWriter out = res.getWriter();
//					out.write(array.toString());
//					out.flush();
//					out.close();
						
							session.removeAttribute("shoppingcart");
							session.removeAttribute("sum");
							
						String url = "/front-end/shopping_system/Checkout.jsp";
						RequestDispatcher rd = req.getRequestDispatcher(url);
						rd.forward(req, res);
						
					} catch(Exception e) {
						errorMsgs.add(e.getMessage());
						RequestDispatcher failureView = req
								.getRequestDispatcher("/front-end/shopping_system/payByCard.jsp");
						failureView.forward(req, res);
					}
				}

				
			}
}
