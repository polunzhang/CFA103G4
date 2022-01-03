<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!-- include側邊欄(sidebar) -->
<aside onload="connect();" onunload="disconnect();">
	<%@ include file="/back-end/sidebar.jsp"%>
</aside>
<!-- include背景圖片(毛玻璃拉長特化meal版) -->
<%@ include file="/front-end/shopping_system/background/ListAllBG.html"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Member</title>
<style>
* {
	box-sizing: border-box;
	font-family: monospace;
	line-height: 150%;
}

img {
	max-width: 100%;
}

body {
	margin: 0;
	color: black;
}

table#table-1 {
	text-align: center;
	width: 55%;
	height: 100px;
}

table#table-2 {
	font-size: 10 vmin;
	margin: 0 auto;
	white-space: nowrap;
}

table {
	width: 300px;
	/* 	background-color: white; */
	margin-top: 1px;
	margin-bottom: 1px;
}

table, th, td {
	border: 0px solid #CCCCFF;
	white-space: nowrap;
}

main {
	position: absolute;
	top: 3.5%;
	/* 	left: 12.5%; */
	height: calc(100vh - 100px);
	width: calc(100% - 200px);
	margin-left: 200px;
}

</style>

</head>
<main>
	<center>
		<body>
			<header class="header">
				<h1>BIB Member : Home</h1>
			</header>
			<p>This is the Home page for Buisness is Booming Member: Home</p>
			<table id="table1">
				<ul>
					<h3>會員查詢</h3>
				</ul>
			</table>
			<%-- 錯誤表列 --%>
			<c:if test="${not empty errorMsgs}">
				<font style="color: red">請修正以下錯誤:</font>

				<c:forEach var="message" items="${errorMsgs}">
					<font color="red">${message}</font>
				</c:forEach>

			</c:if>
			<table id="table2">
				<ul>
					<a href='listAllMember.jsp'>List All Member</a>
				</ul>
				<ul>
					<a href='addMember.jsp'>Add a new Member</a>
				</ul>

				<ul>
					<FORM METHOD="post" ACTION="MemberServlet.do">
						<b>會員編號 ：</b> <input type="text" name="memno"size:"50"><input
							type="hidden" name="action" value="getOne_For_Display"> <input
							type="submit" value="送出">
					</FORM>
				</ul>

				<ul>
					<jsp:useBean id="memberSvc" scope="page"
						class="com.member.model.MemberService" />

					<FORM METHOD="post" ACTION="MemberServlet.do">
						<b>會員姓名 ：</b> <input type="text" name="mname"size:"10"> <input
							type="hidden" name="action" value="getOne_For_Display_By_Name">
						<input type="submit" value="送出">
					</FORM>
				</ul>

				<ul>
					<FORM METHOD="post" ACTION="MemberServlet.do">
						<b>手機號碼 ：</b> <select size="1" name="memno">
							<c:forEach var="memberVO" items="${memberSvc.all}">
								<option value="${memberVO.memno}">${memberVO.phone}
							</c:forEach>
						</select> <input type="hidden" name="action" value="getOne_For_Display">
						<input type="submit" value="送出">
					</FORM>
				</ul>

				<ul>
					<jsp:useBean id="memeberSvc" scope="page"
						class="com.member.model.MemberService" />


					<FORM METHOD="post" ACTION="MemberServlet.do">
						<b>身分證字號：</b> <select size="1" name="memno">
							<c:forEach var="memberVO" items="${memberSvc.all}">
								<option value="${memberVO.memno}">${memberVO.mem_id}
							</c:forEach>
						</select> <input type="hidden" name="action" value="getOne_For_Display">
						<input type="submit" value="送出">
					</FORM>
				</ul>
			</table>
		</body>
</main>
</html>