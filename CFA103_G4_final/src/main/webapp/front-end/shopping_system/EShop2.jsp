<%@page import="com.meal.model.MealVO"%>
<%@page import="com.meal.model.MealJDBCDAO"%>
<%@page import="com.meal_type.model.*"%>

<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>

<%
MealJDBCDAO dao = new MealJDBCDAO();
List<MealVO> list = dao.getAll();
pageContext.setAttribute("list", list);
%>

<jsp:useBean id="MTSvc" scope="page" class="com.meal_type.model.MealTypeService" />

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>購物車</title>



<style>
.preview {
/* 	position: absolute; */
	left: 200px;
	z-index: 1;
	width: 65.5%;
	height: 98%;
	padding: 15px;
	/* 	border: 2px solid red; */
}

tr{
	height: 94px;
}

table, th, td {
	border: 1px solid black;
	border-radius:10px;
	width:1000px;
}

table#table-1 h3{
	display: block;
	text-align:left;
	margin-left:450px
	
}
table#table-1 h4{
	display: block;
	text-align:left;
	margin-left:450px
	
}

th, td {
	padding: 3px;
	border: 1px solid #b5b4ae;
	
}

td, div.listallmealpic img{
	width:80px;
	heigth:80px;
	border: 1px solid #b5b4ae; 	
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
.button{
  border:0;
  background-color:#003C9D;
  color:#fff;
  border-radius:10px;
  cursor:pointer;
  width: 100px;
  }

.button:hover{
  color:#003C9D;
  background-color:#fff;
  border:2px #003C9D solid;
}

.add{
	
}

</style>
</head>

<body>



<%@ include file="/front-end/front-end_background.jsp"%>
		

<div class="preview">
<!-- <header> -->
	<table id="table-1">
		<tr>
			<td>
				<h3>所有餐點</h3>
				<h4>
					<a href="<%=request.getContextPath()%>/front-end/shopping_system/Cart.jsp">
					<img src="<%=request.getContextPath()%>/pic/icon/shopping-cart.svg"
						width="15" height="15" border="0">購物清單
					</a>
				</h4>
			</td>
		</tr>
	</table>

	<%-- 錯誤表列 --%>
	<c:if test="${not empty errorMsgs}">
		<font style="color: red">請修正以下錯誤:</font>
		<ul>
			<c:forEach var="message" items="${errorMsgs}">
				<li style="color: red">${message}</li>
			</c:forEach>
		</ul>
	</c:if>

	<table style="background-color: #ffe6ea;">
		<tr>
			<th style="width:50px;">編號</th>
			<th style="width:500px;">價格</th>
			<th style="width:500px;">餐點名稱</th>
			<th style="width:500px;">餐點介紹</th>
			<th style="width:500px;">餐點圖片</th>
			<th style="width:200px;">數量</th>
		</tr>
<!-- </header> -->
		<c:forEach var="MealVO" items="${list}">
			<tr>
				<td align="center" >${MealVO.mealno}</td>
				<td align="center">${MealVO.meal_price}</td>
				<td align="center">${MealVO.meal_name}</td>
				<td align="left">${MealVO.meal_intro}</td>
				<td>
					<div class="listallmealpic">
						<img src="<%=request.getContextPath()%>/MealPicServlet?id=${MealVO.getMealno()}" />
					</div>
				</td>
				

				<%-- 同列之數量增減及加入購物車 --%>
				<td class="add">
					<FORM METHOD="post" ACTION="<%=request.getContextPath()%>/front-end/shopping_system/shoppingSystem.do" style="margin-bottom: 0px;">
						<input id="number" type="number" name="meal_amount" size="2" value="1" min="1"><p>
						<input type="hidden" name="action" value="ADD"> 
						<input type="hidden" name="mealno" value="${MealVO.mealno}">
						<input type="hidden" name="meal_price" value="${MealVO.meal_price}">
						<input type="hidden" name="meal_set" value="0">
						<input type="hidden" name="meal_status" value="0">
						<input type="submit" class="button" font-size="12px" value="放入購物車">
					</FORM>
				</td>
		
			</tr>
		</c:forEach>
	</table>
</div>



<footer>
<%@ include file="/front-end/front-end_footer.jsp"%>
</footer>
</body>
</html>