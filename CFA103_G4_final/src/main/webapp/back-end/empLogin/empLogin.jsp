<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.emp.model.*"%>

<%
EmpVO empVO = (EmpVO) request.getAttribute("empVO"); //EmpLoginServlet.java(Concroller), 存入req的empVO物件
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Login Page</title>

<style>
	
	body{
		background-image: url('<%=request.getContextPath()%>/pic/login-background.jpg') ;
		background-size: cover;
			
	}
	ul{
		list-style: none;
		padding-inline-start: 0px;
		margin: 0;
		width: 300px;
		height: 300px;
	}
	li{
	margin: 5px auto;
	}
	.title{
	font-size: 48px;
	margin: 10px auto;
	color: white;
	}
	input{
	width: 292px;
/* 	height: 30px; */
	padding: 0px 0px 0px 5px;
	margin: 0px 0px;
	border-radius: 5px;	
	border: 1px solid;
	font-size: 24px;
	}
	.btn{
 	width: 300px;
/* 	height: 34px; */
	margin-top : 18px;
	box-shadow: 0 7px #808080;
	}
	.btn:hover{
	background-color: deepskyblue;
	color: white;
	border: 1px solid black;
	}
	.btn:active {
  	background-color: deepskyblue;
  	box-shadow: 0 5px #666;
  	transform: translateY(4px);
	}
	footer{
	display: inline-block;
	margin-top: 200px;
	
	}
	span{
	color: white;
	}
	.preview{
		width: 800px;
		height: 400px;
		text-align: center;  
		margin: 0 auto;
		display:flex;
  		justify-content:center;
  		
	}
	.preview div{
		margin:auto;
	}
	.error{
	position: absolute;	
	top: 55%;
	left: 50%;
	transform: translateX(-50%);	 
	}
</style>

</head>
<body>

<!-- 樣式套版 -->
<%-- <%@ include file="/back-end/background.html"%> --%>

<div class="preview">

	<div>
		<FORM METHOD="post" ACTION="empLogin.do">
			<ul>
				<li class="title">
					<b>Business is booming</b>
				</li>
				<li>
					<input type="text" name="eaccount"
					 placeholder="帳號">
<%-- 					<span style="color: red">${eaccountError}</span> --%>
				</li>
				<li>
					<input type="password" name="epassword" placeholder="密碼" > 
<%-- 					<span style="color: red">${epasswordError}</span> --%>
				</li>
				<li>
					<input type="hidden" name="action" value="login"> 
					<input class="btn" type="submit" value="登入">
				</li>
				<footer>
				<span class="footer">後台管理系統</span>
				<br>
				<span>後台version: V1.10.0</span>
				</footer>
			</ul>
		</FORM>
	</div>
</div>

	<div class="error">
		<%-- 錯誤表列 --%>
		<c:if test="${not empty errorMsgs}">
		
		<ul>
			<c:forEach var="message" items="${errorMsgs}">
				<li style="color: red; font-size: 15px; text-align: center;">${message}</li>
			</c:forEach>
		</ul>
		</c:if>
	</div>
</body>
</html>