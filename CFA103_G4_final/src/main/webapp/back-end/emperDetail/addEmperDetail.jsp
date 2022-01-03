<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.emperDetail.model.*"%>

<%
  EmperDetailVO emperDetailVO = (EmperDetailVO) request.getAttribute("emperDetailVO"); //EmpServlet.java(Concroller), 存入req的empVO物件
%>
<%-- --<%= emperDetailVO==null %>--${emperDetailVO.empno}-- --%>


<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>新增權限明細</title>

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
		background-color: cornflowerblue;
		height: 100px;
		font-size : 50px;
		font-weight:bold;
		line-height: 100px;
		text-align: center;

	}
	aside{
		height: calc(100% - 101px);
		width: 200px;
		background-color: lightskyblue;
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
/*     	border: 1px solid black; */
  	}
  	th, td {
  		font-size: 20px;
  		padding: 5px;
/*     	text-align: center; */
  	}
  	input, select{
		font-size: 20px;
	}
	.holder{ 
		text-align: center; 
		margin-top: -50px;
	}
	.buttonHolder{ 
		 
		margin-top: -100px;
	}
	main ul{		
		font-size: 20px;
		list-style: none;
		padding: 50px 30px;
		flex-wrap: wrap;
	}
	main ul li{
	 margin: 20px 0px;
	}
	b{
		font-size: 25px;
	}
	.send {
	background-image: linear-gradient(#69db7c, #37b24d);
	border: 0;
	border-radius: 4px;
	box-shadow: rgba(0, 0, 0, .3) 0 5px 15px;
	box-sizing: border-box;
	color: #fff;
	cursor: pointer;
	font-family: Montserrat, sans-serif;
	font-size: .4em;
	margin: 5px;
	padding: 10px 15px;
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

<!-- <header>新增權限明細</header> -->
	
<!-- <aside> -->
<!-- 	<ul class="nav_list"> -->
<%-- 		<li><a href='<%= request.getContextPath() %>/back-end/emp/listAllEmp.jsp'>顯示全部員工</a></li> --%>
<%-- 		<li><a href='<%= request.getContextPath() %>/back-end/emp/select_page.jsp'>查詢單一員工</a></li> --%>
<%-- 		<li><a href='<%= request.getContextPath() %>/back-end/emp/addEmp.jsp'>員工管理</a></li> --%>
<%-- 		<li><a href='<%= request.getContextPath() %>/back-end/emper/select_page_emper.jsp'>員工權限管理</a></li> --%>
<%-- 		<li><a href='<%= request.getContextPath() %>/back-end/emper/addEmper.jsp'>新增員工權限</a></li> --%>
<!-- 		<li><a href='select_page_emper_detail.jsp'>權限明細查詢</a></li> -->
<!-- 		<li><a href='listAllEmperDetail.jsp'>所有權限明細</a></li> -->
<!-- 		<li><a href='addEmperDetail.jsp'>新增權限明細</a></li> -->
<!-- 	</ul> -->
<!-- </aside> -->

<main>	
<%-- 錯誤表列 --%>
	<c:if test="${not empty errorMsgs}">
		<font style="color: red">請修正以下錯誤:</font>
		<ul>
			<c:forEach var="message" items="${errorMsgs}">
				<li style="color: red">${message}</li>
			</c:forEach>
		</ul>
	</c:if>
<div class="holder">
	<FORM METHOD="post" ACTION="emperDetail.do" name="form1">
<!-- 		<table> -->
<!-- 			<tr> -->
<!-- 				<td>權限編號:</td> -->
<!-- 				<td><input type="TEXT" name="emper_name" size="45" -->
<%-- 					value="<%=(emperVO == null) ? "輸入權限名稱" : emperVO.getEmper_name()%>" /></td> --%>
<!-- 			</tr>			 -->
<!-- 			<tr> -->
<!-- 				<td>員工編號:</td> -->
<!-- 				<td><input type="TEXT" name="emper_name" size="45" -->
<%-- 					value="<%=(emperVO == null) ? "輸入權限名稱" : emperVO.getEmper_name()%>" /></td> --%>
<!-- 			</tr>			 -->
<!-- 		</table> -->
		
		<ul>
		
		<jsp:useBean id="empSvc" scope="page" class="com.emp.model.EmpService" />
			<li>			
				<b>選擇員工:</b> <select size="1" name="empno">
					<c:forEach var="empVO" items="${empSvc.all}">
						<option value="${empVO.empno}">${empVO.ename}
					</c:forEach>
				</select> 			
			</li>

		<jsp:useBean id="emperSvc" scope="page" class="com.emper.model.EmperService" />
			<li>
				<b>選擇權限:</b> <select size="1" name="emperno">
					<c:forEach var="emperVO" items="${emperSvc.all}">
						<option value="${emperVO.emperno}">${emperVO.emper_name}
					</c:forEach>
				</select> 			
			</li>
		</ul>		
		<br> 
			<div class="buttonHolder">
			<input type="hidden" name="action" value="insert"> 
			<input type="submit" value="送出新增" class="send">
			</div>		
	</FORM>
</div>
</main>

</body>
</html>