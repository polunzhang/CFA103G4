<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.emper.model.*"%>

<%
    EmperService emperSvc = new EmperService();
    List<EmperVO> list = emperSvc.getAll();
    pageContext.setAttribute("list",list);
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>查詢員工權限</title>

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
		background-color: hotpink;
		height: 100px;
		font-size : 50px;
		font-weight:bold;
		line-height: 100px;
		text-align: center;

	}
	aside{
		height: calc(100% - 101px);
		width: 200px;
		background-color: pink;
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
/* 	table-layout: fixed; */
	}
	table, th, td {
    	border: 1px solid black;
  	}
  	th, td {
  		font-size: 16px;
    	text-align: center;
    	padding: 5px;
  	}
  	b{
  	font-size: 18px;
  	}
  	form{
  	display: inline-block;
  	}
  	.divForm{
  	text-align: center;
  	}
  	
  	#customers {
  border-collapse: collapse;
  width: 85%;
}

#customers td, #customers th {
  border: 1px solid #ddd;
  padding: 6px;
}

#customers tr:nth-child(even){background-color: #f2f2f2;}
#customers tr{background-color: white;}
#customers tr:hover {background-color: #ddd;}

#customers th {
  padding-top: 10px;
  padding-bottom: 10px;
  text-align: center;
  background-color: CornflowerBlue;
  color: white;
}
.edit {
  background-image: linear-gradient(#0dccea, #0d70ea);
  border: 0;
  border-radius: 4px;
  box-shadow: rgba(0, 0, 0, .3) 0 2px 12px;
  box-sizing: border-box;
  color: #fff;
  cursor: pointer;
  font-family: Montserrat,sans-serif;
  font-size: .4em;
  margin: 2px;
  padding: 3px 10px;
  text-align: center;
  user-select: none;
  -webkit-user-select: none;
  touch-action: manipulation;
}
.delete {
  background-image: linear-gradient(#ffa8a8, #e03131);
  border: 0;
  border-radius: 4px;
  box-shadow: rgba(0, 0, 0, .3) 0 2px 12px;
  box-sizing: border-box;
  color: #fff;
  cursor: pointer;
  font-family: Montserrat,sans-serif;
  font-size: .4em;
  margin: 2px;
  padding: 3px 10px;
  text-align: center;
  user-select: none;
  -webkit-user-select: none;
  touch-action: manipulation;
}

</style>

</head>
<body>

<!-- 樣式套版 -->

<%@ include file="/back-end/sidebar.jsp"%>
<%@ include file="/back-end/background.html"%>
<%-- 錯誤表列 --%>
<c:if test="${not empty errorMsgs}">
	<font style="color:red; font-size: 15px; text-align: center;">請修正以下錯誤:</font>
	<ul>
		<c:forEach var="message" items="${errorMsgs}">
			<li style="color:red; font-size: 15px; text-align: center;">${message}</li>
		</c:forEach>
	</ul>
</c:if>

<!-- <header>員工權限</header> -->
		
<main>	
	<table id="customers">
	<tr>
		<th>權限編號</th>
		<th>權限名稱</th>
		<th>修改</th>
		<th>刪除</th>
	</tr>

<c:forEach var="emperVO" items="${list}">
		
		<tr>
			<td>${emperVO.emperno}</td>
			<td>${emperVO.emper_name}</td>
			<td>
			  <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/back-end/emper/emper.do" style="margin-bottom: 0px;">
			     <input type="submit" value="修改" class="edit">
			     <input type="hidden" name="emperno"  value="${emperVO.emperno}">
			     <input type="hidden" name="action"	value="getOne_For_Update"></FORM>
			</td>
			<td>
			  <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/back-end/emper/emper.do" style="margin-bottom: 0px;">
			     <input type="submit" value="x" class="delete">
			     <input type="hidden" name="emperno"  value="${emperVO.emperno}">
			     <input type="hidden" name="action" value="delete"></FORM>
			</td>
		</tr>
	</c:forEach>
	</table>	
</main>

</body>
</html>