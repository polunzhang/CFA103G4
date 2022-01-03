<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.member.model.*"%>
<!-- include側邊欄(sidebar) -->
<aside onload="connect();" onunload="disconnect();">
	<%@ include file="/back-end/sidebar.jsp"%>
</aside>
<!-- include背景圖片(毛玻璃拉長特化meal版) -->
<%@ include file="/front-end/shopping_system/background/ListAllBG.html"%>


<%
MemberService memberSvc = new MemberService();
List<MemberVO> list = memberSvc.getAll();
pageContext.setAttribute("list", list);
%>


<html>
<head>
<title>會員資料</title>

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
}

table#table-1 {
	text-align: center;
	margin: 0 auto;
}

table#table-1 h4 {
	color: red;
	display: block;
	margin-bottom: 1px;
}

table#table-2 {
	font-size: 10 vmin;
	margin: 0 auto;
	white-space: nowrap;
}

h4 {
	display: inline;
}

table {
	width: 1000px;
	margin-top: 5px;
	margin-bottom: 5px;
	color: black;
}

table#table2 {
	border: 1px solid #CCCCFF;
}

th, td {
	padding: 5px;
	text-align: center;
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
	<body>
		<center>

			<table id="table-1">
				<tr>
					<td>
						<h3>會員資料</h3>
						<h4>
							<a href="select_page.jsp"><img src="icon/light-bulb.png"
								width="30" height="30" border="0"> HomePage</a><br>
						</h4>
					</td>
				</tr>
			</table>
			<%-- 錯誤表列 --%>
			<c:if test="${not empty errorMsgs}">
				<font style="color: red">請修正以下錯誤:</font>
				<ul>
					<c:forEach var="message" items="${errorMsgs}">
						<li style="color: red">${message}</li>
					</c:forEach>
				</ul>
			</c:if>

			<table id="table-2">
				<tr>
					<th>會員編號</th>
					<th>會員姓名</th>
					<th>性別</th>
					<th>生日</th>
					<th>地址</th>
					<th>電話</th>
					<th>帳號</th>
					<th>密碼</th>
					<th>郵箱</th>
					<th>帳號狀態</th>
					<th>驗證狀態</th>
					<th>身分證字號</th>
				</tr>
				<%@ include file="page1.file"%>
				<c:forEach var="memberVO" items="${list}" begin="<%=pageIndex%>"
					end="<%=pageIndex+rowsPerPage-1%>">

					<tr>
						<td>${memberVO.memno}</td>
						<td>${memberVO.mname}</td>
						<td>${memberVO.gender}</td>
						<td>${memberVO.bday}</td>
						<td>${memberVO.address}</td>
						<td>${memberVO.phone}</td>
						<td>${memberVO.maccount}</td>
						<td>${memberVO.mpassword}</td>
						<td>${memberVO.mem_email}</td>
						<td>${memberVO.mem_state}</td>
						<td>${memberVO.mem_verify}</td>
						<td>${memberVO.mem_id}</td>
						<td>
							<FORM METHOD="post"
								ACTION="<%=request.getContextPath()%>/back-end/member/MemberServlet.do"
								style="margin-bottom: 0px;">
								<input type="submit" value="修改"> <input type="hidden"
									name="memno" value="${memberVO.memno}"> <input
									type="hidden" name="action" value="getOne_For_Update">
							</FORM>
						</td>
						<td>
							<FORM METHOD="post"
								ACTION="<%=request.getContextPath()%>/back-end/member/MemberServlet.do"
								style="margin-bottom: 0px;">
								<input type="submit" value="刪除"> <input type="hidden"
									name="memno" value="${memberVO.memno}"> <input
									type="hidden" name="action" value="delete">
							</FORM>
						</td>
					</tr>
				</c:forEach>
			</table>
			<%@ include file="page2.file"%>
	</body>
</main>
</html>
