<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
	<%@ page import="java.util.*"%>


	<!DOCTYPE html>
	<html>
	<head>
		<script src="http://code.jquery.com/jquery-1.12.4.min.js"></script>
		<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-F3w7mX95PdgyTmZZMECAngseQB83DfGTowi0iMjiWaeVhAn4FJkqJByhZMI3AhiU" crossorigin="anonymous">
		
		<meta charset="UTF-8">

		<title>OrderSystem.jsp</title>
		<style>
		@import
	url("https://fonts.googleapis.com/css?family=Rajdhani:300&display=swap")
	;
		.home-text{
		display:block;
		position:absolute;
		left:40%;
		top:45%;
		font:30px bold;
		}
	</style>
	
</head>
<body>
	<%@ include file="/back-end/sidebar.jsp"%>
	<%@ include file="/back-end/background.html"%>

			<span class="home-text">員工編號#${empVO.empno} ${empVO.ename}您好，祝您有美好的一天。</span>

</body>
</html>
