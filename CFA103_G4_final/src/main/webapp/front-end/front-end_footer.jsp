<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
	<%@ page import="java.util.*"%>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<title>footer</title>

	<style>

	/* ===============================footer==================================== */

	footer{
		background-color: tan;
		height: 100px;
	}
	div{
		margin: 0 auto;
		/* display: inline-block; */
		text-align: center;
	}
	span{
		display: inline-block;
		margin: 40px 30px;
		color: white;
	}

</style>

</head>
<body>
	

	<footer>
		<div>
			<span>©Business Is Booming 2021</span>
		</div>
	</footer>
<c:if test="${not empty memberVO}">
<%@ include file="/front-end/chatWeb/frontchat.jsp"%>
</c:if>
</body>
</html>