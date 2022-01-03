<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.* , com.online_detail.model.*"%>

<jsp:useBean id="mealSvc" scope="page" class="com.meal.model.MealJDBCDAO" />

<html>
<head>
<meta charset="UTF-8">
<title>Cart.jsp</title>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/ShoppingCart.css">

<jsp:useBean id="MealDAO" scope="page" class="com.meal.model.MealJDBCDAO" />
<style>

img{
width:200px;
height:200px;
}
.preview {
	position: absolute;
	left: 200px;
	z-index: 1;
	width: 87.5%;
	height: 98%;
	padding: 15px;
	/* 	border: 2px solid red; */
}


table, th, td {
	border: 1px solid #CCCCFF;
	width:750px;
}

table#table-1 h3{
	display: block;
	text-align:left;
}
table#table-1 h4{
	display: block;
	text-align:left;
}

th, td {
	padding: 3px;
}

td, div.listallmealpic img{
	width:80px;
	heigth:80px;
	border: 1px solid black; 	
	text-align:center;
}
a{ 
	text-decoration:none;
} 
a:hover{ 
	text-decoration:underline;
}
#number {
  width: 3em;
}
</style>
</head>
<body>

<!-- include側邊欄(sidebar) -->
<aside onload="connect();" onunload="disconnect();" >
		<%@ include file="/back-end/sidebar.jsp"%>
</aside>
<!-- include背景圖片(毛玻璃拉長特化meal版) -->
<%@ include file="/back-end/shopping_system/background/ListAllBG.html"%>

	<div id="cart" class="preview">
		<font size="+3">目前購物車的內容如下:(Cart.jsp)</font>
		<br>
		<table id="cart-table" >
			<c:forEach var="order" items="${shoppingcart}" varStatus="varStatusName">
				<tr>
					<td width="60">
						<img class="showMealPic" src="<%=request.getContextPath()%>/MealPicServlet?id=${order.mealno}"></td>
						<td width="200">${mealSvc.findByPrimaryKey(order.mealno).meal_name}</td>
						<td width="50" >${order.meal_amount}</td>
						<td width="50" >${order.meal_price}</td>
						<td width="50" >${order.meal_set}</td>
						<td width="50" >
							<form name="deleteForm" action="<%=request.getContextPath()%>/back-end/shopping_system/shoppingSystem.do" method="POST">
								<input type="hidden" name="action" value="DELETE"> 
								<input type="hidden" name="del" value="${varStatusName.index}"> 
								<input type="image" class="button_delete" src="<%=request.getContextPath()%>/pic/icon/trash.svg" width="30" height="30" >
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