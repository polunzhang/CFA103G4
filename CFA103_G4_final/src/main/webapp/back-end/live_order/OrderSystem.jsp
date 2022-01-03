<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@page import="com.meal.model.MealJDBCDAO"%>
<%@page import="com.meal_pic.model.MealPicVO"%>
<%@page import="com.meal_pic.model.MealPicDAO"%>
<%@page import="com.meal_pic.model.MealPicService"%>

<jsp:useBean id="MealPicDAO" scope="page"
	class="com.meal_pic.model.MealPicDAO" />
<jsp:useBean id="MealPicSvc" scope="page"
	class="com.meal_pic.model.MealPicService" />
<jsp:useBean id="mealSvc" scope="page"
	class="com.meal.model.MealJDBCDAO" />

<!DOCTYPE html>
<html>
<head>
<script src="http://code.jquery.com/jquery-1.12.4.min.js"></script>

<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/js/OrderSystem.css">

<title>OrderSystem.jsp</title>
</head>
<body>


	<div id="preview" style="witdh: 70%">
		<c:forEach var="mealVO" items="${mealSvc.all}">
			<div class=container>
				<div class="meal_price">NT.${mealVO.meal_price}</div>
				<div class="meal_name">${mealVO.meal_name}</div>
				<form action="<%=request.getContextPath()%>/order/liveOrder.do" method="POST">
					<input type="hidden" name="action" value="ADD"> 
					<input type="hidden" name="mealno" value="${mealVO.mealno}">
					<input type="hidden" name="meal_amount" value="1">					
					<input type="hidden" name="meal_price" value="${mealVO.meal_price}">
					<input type="hidden" name="meal_set" value="0"> 
					<input type="hidden" name="meal_status" value="0">
					<input type="image" class="mealButton" src="<%=request.getContextPath()%>/MealPicFromDB?id=${mealVO.mealno}">
				</form>
			</div>
		</c:forEach>
	</div>
	
		<jsp:include page="/back-end/live_order/Cart.jsp" flush="true" />

</body>
</html>
