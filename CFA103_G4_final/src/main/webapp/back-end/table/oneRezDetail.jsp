<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="com.rez.model.*"%>
<jsp:useBean id="rezSvc" scope="page" class="com.rez.model.RezService" />
<aside onload="connect();" onunload="disconnect();" >
    <%@ include file="/back-end/sidebar.jsp"%>
</aside>
<%@ include file="/back-end/background.html"%>
<%
Integer rezno = Integer.valueOf(request.getParameter("rezno"));
request.setAttribute("rezno", rezno);
System.out.println("rezno" + rezno);
RezVO rezVO = rezSvc.getOneRez(rezno);
request.setAttribute("rezVO", rezVO);
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="BIG5">
<title>Insert title here</title>
<style>
  table#table-1 {
	background-color: #FFC14F;
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
	width: 1000px;
	margin-top: 5px;
	margin-bottom: 5px;
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
margin-left: 200px;
}
</style>
</head>
<body>
<main>
<h4></h4>
<table id="table-1">
	<tr><td>
		 <h3>訂位資料 - ListOneRez.jsp</h3>
		 <h4><a href="<%=request.getContextPath()%>/back-end/table/tables.jsp">回首頁</a></h4>
	</td></tr>
</table>


<table>
	<tr>
		<th>訂位編號</th>
		<th>會員編號</th>
		<th>連絡電話</th>
		<th>訂位人數</th>
		<th>訂位日期</th>
		<th>訂位時間</th>
		<th>email</th>
		<th>姓氏</th>
		<th>性別</th>
	</tr>
	<tr>
		<td>${rezVO.rezno}號</td>
		<td>${(rezVO.memno==0)? '非會員':rezVO.memno }</td>
		<td>${rezVO.phone}</td>
		<td>${rezVO.num_of_ppl}人</td>
		<td><fmt:formatDate value="${rezVO.reservationdate}" pattern="yyyy-MM-dd"/></td>
		<td><fmt:formatDate value="${rezVO.reservationtime}" pattern="HH:mm"/></td>
			<td>${rezVO.email}</td>
			<td>${rezVO.lastname}</td>
			<td>${rezVO.sex}</td>
	</tr>
	
</table>
</main>
</body>
</html>