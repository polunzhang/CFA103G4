<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.rez_time.model.*"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<%
  RezTimeVO reztimeVO = (RezTimeVO) request.getAttribute("reztimeVO"); //RezTimeServlet.java(Concroller), 存入req的rezVO物件
%>
<html>
<head>
<meta charset="UTF-8">
<title>桌位數量</title>

<style>
*{
		padding: 0;
		margin: 0;
		box-sizing: border-box;
	}
	html{
		height: 100px;
		font-size : 50px;
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
	table, th, td {
    	border: 1px solid black;
  	}
  	th, td {
  		font-size: 20px;
    	text-align: center;
    	padding: 5px;
  	}
</style>

</head>
<body>
<%@ include file="/back-end/sidebar.jsp"%>
<%@ include file="/back-end/background.html"%>

<main>
<table>
	<tr>
		
		<th>訂位時間</th>
		<th>中午桌位上限</th>
		<th>中午桌位數量</th>
		<th>晚上桌位上限</th>
		<th>晚上桌位數量</th>
	</tr>
	<tr>
		
		<td>${reztimeVO.rez_time_date}</td>
		<td>${reztimeVO.rez_time_mid_limit}</td>
		<td>${reztimeVO.rez_time_mid}</td>
		<td>${reztimeVO.rez_time_eve_limit}</td>
		<td>${reztimeVO.rez_time_eve}</td>
	</tr>
</table>
</main>

</body>
</html>