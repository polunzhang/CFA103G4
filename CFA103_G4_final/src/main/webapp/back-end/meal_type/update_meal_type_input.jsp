<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.meal_type.model.*" %>

<%
	MealTypeVO mealTypeVO = (MealTypeVO) request.getAttribute("mealTypeVO");//MealTypeServlet.java (Concroller) 存入req的mealTypeVO物件 (包括幫忙取出的mealTypeVO, 也包括輸入資料錯誤時的mealTypeVO物件)
%>

<html>
<head>
<meta charset="UTF-8">
<title>餐點分類資料修改 - update_meal_type_input.jsp</title>

<style>
  table#table-1 {
	background-color: #CCFFD2;
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
	width: 450px;
	background-color: white;
	margin-top: 1px;
	margin-bottom: 1px;
  }
  table, th, td {
    border: 0px solid #CCFFD2;
  }
  th, td {
    padding: 1px;
  }
</style>

</head>
<body bgcolor='white'>

<table id="table-1">
	<tr><td>
		 <h3>餐點分類資料修改 - update_meal_type_input.jsp</h3></td><td>
		 <h4><a href="select_page_meal_type.jsp"><img src="images/tomcat.png" width="100" height="100" border="0">回首頁</a></h4>
	</td></tr>
</table>

<h3>資料修改:</h3>

<%-- 錯誤表列 --%>
<c:if test="${not empty errorMsgs}">
	<font style="color:red">請修正以下錯誤:</font>
	<ul>
		<c:forEach var="message" items="${errorMsgs}">
			<li style="color:red">${message}</li>
		</c:forEach>
	</ul>
</c:if>

<FORM METHOD="post" ACTION="mealType.do" name="form1">
<table>
	<tr>
		<td>餐點分類編號:<font color=red><b>*</b></font></td>
		<td><%=mealTypeVO.getMeal_type_no()%></td>
	</tr>
	<tr>
		<td>餐點分類名稱:</td>
		<td><input type="TEXT" name="meal_type_name" size="45" 
			 value="<%= (mealTypeVO==null)? "0" : mealTypeVO.getMeal_type_name()%>" /></td>
	</tr>

</table>
<br>
<input type="hidden" name="action" value="update">
<input type="hidden" name="meal_type_no" value="<%=mealTypeVO.getMeal_type_no()%>">
<input type="submit" value="送出修改">
</FORM>

</body>
</html>