<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="java.util.*"%>
<%@ page import="com.rez.model.*"%>

<%
    RezService rezSvc = new RezService();
    List<RezVO> list = rezSvc.getAll();
    pageContext.setAttribute("list",list);
%>


<html>
<head>
<title>所有訂位資料</title>
<style>
	*{
		padding: 0;
		margin: 0;
		box-sizing: border-box;
	}
	html{
		height: 100px;
		font-size : 20px;
	}
	header{
		background-color: lightsalmon;
		height: 100px;
		font-size : 50px;
		font-weight:bold;
		line-height: 100px;
		text-align: center;
	}
	aside{
		height: calc(100% - 101px);
		width: 200px;
		background-color: bisque;
		position: fixed;
		left: 0;
		overflow-y: auto;
	}
	ul.nav_list{
		padding-top: 1px;
		list-style: none;

	}
	.nav_list li{
		background-color: white;
		width: 135px;
		height: 40px;		
		margin: 50px auto;
		/* padding: 10px 0px; */		
		border-radius: 15px;
		box-shadow: 5px 5px dimgray;	
	}
	.nav_list li:hover{
		transform: scale(1.1);
	}
	.nav_list li a{
		font-size: 18px;
		font-weight:bold;
		display: block;
		text-align: center; 
		line-height: 40px;
		text-decoration: none;
		color: black;		
	}
	main{
		position: absolute;
		top: 3.5%;
		left: 12.5%;	
		height: calc(100vh - 100px);
		width: calc(100% - 200px);
		padding-top: 45px;		
	}
	main table{
	margin: auto;
	background-color: rgba(255, 255, 255, 0.5);
/* 	table-layout: fixed; */
	}
</style>

<style>
  
  table, th, td {
    border: 1px solid black;
  }
  th, td {
    font-size: 16px;
    text-align: center;
    padding: 5px;
  }
</style>

</head>
<body>
<%@ include file="/back-end/sidebar.jsp"%>
<%@ include file="/back-end/background.html"%>
<main>
<%-- 錯誤表列 --%>
<c:if test="${not empty errorMsgs}">
	<font style="color:red; font-size: 15px; text-align: center; display: block;">請修正以下錯誤:</font>
	<ul>
		<c:forEach var="message" items="${errorMsgs}">
			<li style="color:red; font-size: 15px; text-align: center;">${message}</li>
		</c:forEach>
	</ul>
</c:if>

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
	<%@ include file="page1.file" %> 
	<c:forEach var="rezVO" items="${list}" begin="<%=pageIndex%>" end="<%=pageIndex+rowsPerPage-1%>">
		
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
			<td>
			  <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/back-end/rez/rez.do" style="margin-bottom: 0px;">
			     <input type="submit" value="修改">
			     <input type="hidden" name="rezno"  value="${rezVO.rezno}">
			     <input type="hidden" name="action"	value="getOne_For_Update"></FORM>
			</td>
			<td>
			  <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/back-end/rez/rez.do" style="margin-bottom: 0px;">
			     <input type="submit" value="刪除">
			     <input type="hidden" name="rezno"  value="${rezVO.rezno}">
			     <input type="hidden" name="action" value="delete"></FORM>
			</td>
		</tr>
	</c:forEach>
</table>
<%@ include file="page2.file" %>
</main>
</body>
</html>