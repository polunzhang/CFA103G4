<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.* , com.live_detail.model.*"%>
<jsp:useBean id="mealSvc" scope="page" class="com.meal.model.MealJDBCDAO" />
<html>
<head>
	<title>Mode II 範例程式 - Cart.jsp</title>
	<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/css/ShoppingCart.css">

	<jsp:useBean id="MealDAO" scope="page"
	class="com.meal.model.MealJDBCDAO" />


</head>
<body>
	<div id="cart">
		<font size="+3">目前購物車的內容如下:(Cart.jsp)</font>
		<br>
		<table id="cart-table">
			<c:forEach var="order" items="${shoppingcart}" varStatus="varStatusName">
				<tr>
					<td width="60">
						<img class="showMealPic" src="<%=request.getContextPath()%>/MealPicFromDB?id=${order.mealno}"></td>
						<td width="200">${mealSvc.findByPrimaryKey(order.mealno).meal_name}</td>
						<td width="50" >${order.meal_amount}</td>
						<td width="50" >${order.meal_price}</td>
						<td width="50" >${order.meal_set}</td>
						<td width="50" >
							<form name="deleteForm" action="<%=request.getContextPath()%>/order/liveOrder.do" method="POST">
								<input type="hidden" name="action" value="DELETE"> 
								<input type="hidden" name="del" value="${varStatusName.index}"> 
								<input type="image" class="button_delete" src="<%=request.getContextPath()%>/pic/icon/trash.svg">
							</form>
						</td>
					</tr>

				</c:forEach>
			</table>
			
			<form name="checkoutForm" action="<%=request.getContextPath()%>/order/liveOrder.do" method="POST">
				<input type="hidden" name="action" value="CHECKOUT">
				<input type="submit" value="付款結帳" class="button">
			</form>
			
		</div>
	</body>
	</html>