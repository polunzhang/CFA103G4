<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.online_detail.model.*" %>


<%
	OnlineDetailService onlineDetailSvc = new OnlineDetailService();
	List<OnlineDetailVO>list = onlineDetailSvc.getAll();
	pageContext.setAttribute("list",list);
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>所有線上訂單明細 - listAllOnlineDetail.jsp</title>

<style>
  table#table-1 {
	background-color: #FFCCDA;
    border: 2px solid black;
    text-align: center;
  }
  table#table-1 h4 {
    color: red;
    display: block;
    margin-bottom: 1px;
  }
  h4 {
    color: pink;
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
    border: 1px solid #CCCCFF;
  }
  th, td {
    padding: 5px;
    text-align: center;
  }
</style>


</head>
<body bgcolor='white'>

<table id="table-1">
	<tr><td>
		 <h3>所有線上訂單資料 - listAllOnlineDetail.jsp</h3>
		 <h4><a href="select_page_online_detail.jsp"><img src="images/back1.gif" width="100" height="32" border="0">回首頁</a></h4>
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

<table>
	<tr>
		<th>訂單編號</th>
		<th>餐點編號</th>
		<th>餐點數量</th>
		<th>餐點金額</th>
		<th>餐點狀態</th>
		<th>備註</th>
		<th>套餐狀態</th>
	</tr>
	<%@ include file="page1.file" %>
	<c:forEach var="onlineDetailVO" items="${list }" begin="<%=pageIndex%>" end="<%=pageIndex+rowsPerPage-1%>">
		<tr>
			<td>${onlineDetailVO.olno}</td>
			<td>${onlineDetailVO.mealno}</td>
			<td>${onlineDetailVO.meal_amount}</td>
			<td>${onlineDetailVO.meal_price}</td>
			<td>${onlineDetailVO.meal_status}</td>
			<td>${onlineDetailVO.meal_note}</td>
			<td>${onlineDetailVO.meal_set}</td>
		</tr>
	</c:forEach>
</table>

<%@ include file="page2.file" %>
</body>
</html>