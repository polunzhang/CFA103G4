<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.online_order.model.*" %>

<%
	OnlineOrderService onlineOrderSvc = new OnlineOrderService();
	List<OnlineOrderVO>list = onlineOrderSvc.findByAddress2();
	pageContext.setAttribute("list",list);
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>線上外帶訂單資料 - toGo.jsp</title>
<style>
  table#table-1 {
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
	width: 800px;
	margin: 5px auto;
  }
  table, th, td {
    border: 1px solid black;
  }
  th, td {
    padding: 5px;
    text-align: center;
  }
  main{
		position: absolute;
		top: 3.5%;
		left: 12.5%;	
		height: calc(100vh - 100px);
		width: calc(100% - 200px);
		padding-top: 45px;		
	}
</style>

</head>
<body>

<!-- include側邊欄(sidebar) -->
<%@ include file="/back-end/sidebar.jsp"%>
<!-- include背景圖片(毛玻璃拉長特化meal版) -->
<%@ include file="/back-end/meal/background/ListAllBG.html"%>

<main>


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
		<th>員工編號</th>
		<th>會員編號</th>
		<th>結帳狀態</th>
		<th>取餐/送達時間</th>
		<th>訂單成立時間</th>
		<th>訂單金額</th>
		<th>付款方式</th>
	</tr>
	
	<%@ include file="page1.file" %>
	<c:forEach var="onlineOrderVO" items="${list }" begin="<%=pageIndex%>" end="<%=pageIndex+rowsPerPage-1%>">
			<tr>
				<td>${onlineOrderVO.olno}</td>
				<td>${onlineOrderVO.empno}</td>
				<td>${onlineOrderVO.memno}</td>
				<td>${onlineOrderVO.pay_status}</td>
				<td>${onlineOrderVO.set_time}</td> 
				<td>${onlineOrderVO.create_time}</td>
				<td>${onlineOrderVO.total}</td>
				<td>${onlineOrderVO.pay_way}</td>

		  </tr>
	</c:forEach>
</table>
<%@ include file="page2.file" %>
</main>

</body>
</html>