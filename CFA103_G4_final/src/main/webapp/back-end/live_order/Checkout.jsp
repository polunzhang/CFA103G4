<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ page import="java.util.*"%>
<%@ page import="java.util.* , com.live_detail.model.*"%>
<html>
<head>
<title>Mode II 範例程式 - Checkout.jsp</title>
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/css/ShoppingCart.css">
<style>
.showMealPic {
	width: 60px;
	height: 60px;
	object-fit: cover;
	flex: 1;
}
</style>
</head>
<body>
	<img src="images/tomcat.gif">
	<font size="+3">網路書店 - 結帳：（Checkout.jsp）</font>
	<hr>
	<p>
	<table id="table-1" style="margin: auto;">
		<tr>
			<th width="200">圖片</th>
			<th width="100">名稱</th>
			<th width="100">數量</th>
			<th width="100">價格</th>
			<th width="100">套餐</th>
		</tr>
	</table>
	<table style="margin: auto;">

		<%
		@SuppressWarnings("unchecked")
		Vector<LiveDetailVO> buylist = (Vector<LiveDetailVO>) session.getAttribute("shoppingcart");
		String total = (String) request.getAttribute("total");
		%>
		<%
		for (int i = 0; i < buylist.size(); i++) {
			LiveDetailVO order = buylist.get(i);
			Integer mealno = order.getMealno();
			Integer meal_amount = order.getMeal_amount();
			Integer meal_price = order.getMeal_price();
			Integer meal_set = order.getMeal_set();
		%>
		<tr>
			<td width="200"><img class="showMealPic"
				src="<%=request.getContextPath()%>/MealPicFromDB?id=<%=order.getMealno()%>"></td>
			<td width="100"><%=mealno%></td>
			<td width="100"><%=meal_amount%></td>
			<td width="100"><%=meal_price%></td>
			<td width="100"><%=meal_set%></td>
			<td width="120"></td>
		</tr>
		<%
		}
		%>


		<tr>
			<td colspan="6" style="text-align: right;"><font size="+2">總金額：
					<h4>
						$<%=total%></h4>
			</font></td>
		</tr>
	</table>


	<p>
		<a
			href="<%=request.getContextPath()%>/back-end/live_order/OrderSystem.jsp"><font
			size="+1"> 是 否 繼 續 購 物</font></a>
</body>
</html>