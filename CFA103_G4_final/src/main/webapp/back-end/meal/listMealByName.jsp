<%@page import="com.meal.model.*"%>
<%@page import="com.meal.model.MealJDBCDAO"%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>

<%
List<MealVO> mealVO =(List<MealVO>) request.getAttribute("mealVO"); //mealServlet.java(Concroller), 存入req的mealVO物件
String meal_name =(String) request.getAttribute("meal_name");
%>
<jsp:useBean id="MTSvc" scope="page" class="com.meal_type.model.MealTypeService" />



<!DOCTYPE html>
<html>
<head>
<meta charset="BIG5">
<title>菜單</title>
<style>
.preview {
	position: absolute;
	left: 200px;
	z-index: 1;
	width: 87.5%;
	height: 98%;
	padding: 15px;
	/* 	border: 2px solid red; */
}
table#table-1 h4{
	display: block;
	text-align:left;
}
table#table-1 h3{
	display: block;
	text-align:left;
}

table {
	margin-top: 5px;
	margin-bottom: 5px;
}

table, th, td {
	border: 1px solid #CCCCFF;
	width:600px;
}

th, td {
	padding: 3px;
}
td, div.listallmealpic img{
	width:120px;

	border: 1px solid black; 	
	text-align:center;
}

  table {
	width: 1000px;
	margin-top: 5px;
	margin-bottom: 5px;
  }
  table, th, td {
    border: 1px solid #CCCCFF;
  }
  th, td {
    padding: 5px;

  }
  table#table-1 h4{
	display: block;
	text-align:left;
	
}
table#table-1 h3{
	display: block;
	text-align:left;
}
</style>

</head>
<body >

<body>
<!-- include側邊欄(sidebar) -->
<aside onload="connect();" onunload="disconnect();" >
		<%@ include file="/back-end/sidebar.jsp"%>
</aside>
<!-- include背景圖片(毛玻璃拉長特化meal版) -->
		<%@ include file="/back-end/meal/background/ListAllBG.html"%>

<div class="preview">
<table id="table-1">
	<tr><td>
		 <h3>依關鍵字搜尋餐點--<font style="color:red;">${meal_name}</font></h3>
		 <h4><a href="<%=	request.getContextPath()%>/back-end/meal/select_page.jsp">
			<img src="<%=request.getContextPath()%>/back-end/meal/pic/images/mealHome.svg"width="15" height="15" border="0">返回餐點管理頁面</a>
		</h4>
	</td></tr>
</table>

<%-- 錯誤表列 --%>
<c:if test="${not empty errorMsgs}">
	<font style="color:red">請修正以下錯誤:</font>
	<ul>
		<c:forEach var="message" items="${errorMsgs}">
			<li style="color:red">${message}</li>
		</c:forEach>
	</ul>
</c:if>

<table >
	<tr>
			<th style="width:200px;">編號</th>
			<th style="width:480px;">餐點類型</th>
			<th style="width:250px;">價格</th>
			<th>餐點名稱</th>
			<th>餐點介紹</th>
			<th style="width:500px;">餐點圖片</th>
			<th style="width:100px;">修改</th>
			<th style="width:100px;">刪除</th>
	</tr>
		<c:forEach var="mealVO" items="${mealVO}">
			<tr>
				<td align="center">${mealVO.mealno}</td>
				<td align="center">
				${mealVO.meal_type_no}.${MTSvc.findByPrimaryKey(mealVO.meal_type_no).meal_type_name}</td>
				<td align="center">${mealVO.meal_price}</td>
				<td align="center">${mealVO.meal_name}</td>
				<td align="left">${mealVO.meal_intro}</td>
				<td>
					<div class="listallmealpic">
						<img
						src="<%=request.getContextPath()%>/MealPicServlet?id=${mealVO.getMealno()}" />
					</div>
				</td>
				<%-- 同列之修改及刪除 --%>
				<td style="width:100px;">
					<FORM METHOD="post"
						ACTION="<%=request.getContextPath()%>/meal/meal.do"
						style="margin-bottom: 0px;">
						<input type="image" class="button_update" src="<%=request.getContextPath()%>/back-end/meal/pic/images/updateicon.svg"  width="20" height="20" > 
						<input type="hidden" name="mealno" value="${mealVO.mealno}"> 
						<input type="hidden" name="action" value="getOne_For_Update">
					</FORM>
				</td>
				<td>
					<FORM METHOD="post"
						ACTION="<%=request.getContextPath()%>/meal/meal.do"
						style="margin-bottom: 0px;">
						<input type="image" class="button_delete" src="<%=request.getContextPath()%>/pic/icon/trash.svg"  width="20" height="20" >
						<input type="hidden" name="mealno" value="${mealVO.mealno}"> 
						<input type="hidden" name="action" value="delete">
					</FORM>
				</td>
				
			</tr>
		</c:forEach>
</table>
</div>
</body>
</html>