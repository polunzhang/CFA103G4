<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.emperDetail.model.*"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>查詢權限明細</title>

<style>
* {
	padding: 0;
	margin: 0;
	box-sizing: border-box;
}

html {
	height: 100px;
	font-size: 50px;
}

header {
	background-color: cornflowerblue;
	height: 100px;
	font-size: 50px;
	font-weight: bold;
	line-height: 100px;
	text-align: center;
}

aside {
	height: calc(100% - 101px);
	width: 200px;
	background-color: lightskyblue;
	position: fixed;
	left: 0;
	overflow-y: auto;
}

ul.nav_list {
	padding-top: 1px;
	list-style: none;
}

.nav_list li {
	background-color: white;
	width: 135px;
	height: 40px;
	margin: 50px auto;
	/* padding: 10px 0px; */
	border-radius: 15px;
	box-shadow: 5px 5px dimgray;
}

.nav_list li:hover {
	transform: scale(1.1);
}

.nav_list li a {
	font-size: 18px;
	font-weight: bold;
	display: block;
	text-align: center;
	line-height: 40px;
	text-decoration: none;
	color: black;
}

main {
	position: absolute;
	top: 3.5%;
	left: 12.5%;
	height: calc(100vh - 100px);
	width: calc(100% - 200px);
}

main ul {
	list-style: none;
	margin: 0 auto;
	padding: 50px 30px;
	flex-wrap: wrap;
	display: table;
}

main ul input, select {
	font-size: 20px;
}

b {
	font-size: 25px;
}
</style>


</head>
<body>

<!-- 樣式套版 -->
<%@ include file="/back-end/sidebar.jsp"%>
<%@ include file="/back-end/background.html"%>

<!-- <header>查詢權限明細</header> -->

<main>

<%-- 錯誤表列 --%>
	<c:if test="${not empty errorMsgs}">
		<font style="color: red; font-size: 15px; text-align: center; display: block;">請修正以下錯誤:</font>
		<ul>
			<c:forEach var="message" items="${errorMsgs}">
				<li style="color: red; font-size: 15px; text-align: center;">${message}</li>
			</c:forEach>
		</ul>
	</c:if>

	<ul>
		<li>
			<FORM METHOD="post" ACTION="emperDetail.do">
				<b>輸入員工編號:</b> <input type="text" name="empno"> 
				<input type="hidden" name="action" value="getEmpno_For_Display"> 
				<input type="submit" value="送出">
			</FORM>
		</li>
		<li>
			<FORM METHOD="post" ACTION="emperDetail.do">
				<b>輸入權限編號:</b> <input type="text" name="emperno"> 
				<input type="hidden" name="action" value="getEmperno_For_Display"> 
				<input type="submit" value="送出">
			</FORM>
		</li>
		
		<jsp:useBean id="empSvc" scope="page" class="com.emp.model.EmpService" />

		<li>
			<FORM METHOD="post" ACTION="emperDetail.do">
				<b>選擇員工編號:</b> <select size="1" name="empno">
					<c:forEach var="empVO" items="${empSvc.all}">
						<option value="${empVO.empno}">${empVO.empno}${empVO.ename}
					</c:forEach>
				</select> <input type="hidden" name="action" value="getEmpno_For_Display">
				<input type="submit" value="送出">
			</FORM>
		</li>

		<jsp:useBean id="emperSvc" scope="page" class="com.emper.model.EmperService" />

		<li>
			<FORM METHOD="post" ACTION="emperDetail.do">
				<b>選擇權限編號:</b> <select size="1" name="emperno">
					<c:forEach var="emperVO" items="${emperSvc.all}">
						<option value="${emperVO.emperno}">${emperVO.emperno}${emperVO.emper_name}
					</c:forEach>
				</select> <input type="hidden" name="action" value="getEmperno_For_Display">
				<input type="submit" value="送出">
			</FORM>
		</li>
	</ul>

</main>


</body>
</html>