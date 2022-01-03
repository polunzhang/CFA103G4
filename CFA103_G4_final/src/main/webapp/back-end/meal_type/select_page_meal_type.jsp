<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.meal_type.model.*" %>

<%
	MealTypeService mealTypeSvc = new MealTypeService();
	List<MealTypeVO>list = mealTypeSvc.getAll();
	pageContext.setAttribute("list",list);
%>

<!DOCTYPE html>

<html>
<head>
<meta charset="UTF-8">
<title>餐點分類查詢</title>

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
    color: green;
    display: inline;
  }
</style>

<style>
  table {
	width: 800px;
	background-color: white;
	margin-top: 5px;
	margin-bottom: 5px;
  }
  table, th, td {
    border: 1px solid #CCFFD2;
  }
  th, td {
    padding: 5px;
    text-align: center;
  }
</style>

</head>
<body bgcolor='white'>

<table id="table-1">
   <tr><td><h3>所有餐點分類資料 - listAllMealType</h3><h4>( MVC )</h4></td></tr>
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

<table>
	<tr>
		<th>餐點分類編號</th>
		<th>餐點分類名稱</th>
		<th>修改</th>
		<th>刪除</th>
	</tr>
	
	<c:forEach var="mealTypeVO" items="${list }">
		<tr>
			<td>${mealTypeVO.meal_type_no}</td>
			<td>${mealTypeVO.meal_type_name}</td>
			<td>
				 <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/back-end/meal_type/mealType.do" style="margin-bottom: 0px;">
			     <input type="submit" value="修改">
			     <input type="hidden" name="meal_type_no"  value="${mealTypeVO.meal_type_no}">
			     <input type="hidden" name="action"	value="getOne_For_Update"></FORM>
				</td>
				<td>
			  	<FORM METHOD="post" ACTION="<%=request.getContextPath()%>/back-end/meal_type/mealType.do" style="margin-bottom: 0px;">
			    <input type="submit" value="刪除">
			    <input type="hidden" name="meal_type_no"  value="${mealTypeVO.meal_type_no}">
			    <input type="hidden" name="action" value="delete"></FORM>
				</td>
		</tr>
	</c:forEach>
</table>
<ul>
  <li><a href='addMealType.jsp'>新增</a> 餐點分類</li>
</ul>

</body>
</html>