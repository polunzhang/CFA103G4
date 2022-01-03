<%@page import="com.meal_pic.model.MealPicVO"%>
<%@page import="com.meal_pic.model.MealPicDAO"%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>

<%
MealPicDAO dao = new MealPicDAO();
List<MealPicVO> list = dao.getAll();
pageContext.setAttribute("list", list);
%>


<!DOCTYPE html>
<html>
<head>
<meta charset="BIG5">
<title>目前內用訂單</title>


<style>
table#table-1 {
	background-color: #CCCCFF;
	border: 2px solid black;
	text-align: center;
}

table#table-1 h4 {
	color: red;
	display: block;
	margin-bottom: 1px;
}

h4 {
	color: blue;
	display: inline;
}
</style>

<style>
table {
	width: 200px;
	background-color: white;
	margin-top: 5px;
	margin-bottom: 5px;
}

table, th, td {
	border: 1px solid #CCCCFF;
}

th, td {
	padding: 5px;
	text-align: center;
}

table tr td img {
	width: 100px;
	height: 100px;
	object-fit: cover;
	flex: 1;
}
</style>


</head>
<body>




	<table>
		<tr>
			<th>圖片編號</th>
			<th>餐點編號</th>
			<th>餐點圖片</th>
		</tr>

		<c:forEach var="MealPicVO" items="${list}">
			<tr>
				<td>${MealPicVO.meal_pic_no}</td>
				<td>${MealPicVO.mealno}</td>
				<td>
				<img
				src="<%=request.getContextPath()%>/MealPicServlet?id=${MealPicVO.meal_pic_no}">
				</td>
			</tr>
		</c:forEach>

	</table>








</body>
</html>