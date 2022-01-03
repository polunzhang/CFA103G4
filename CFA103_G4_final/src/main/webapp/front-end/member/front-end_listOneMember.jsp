<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ page import="com.member.model.*"%>


<%
MemberVO memberVO = (MemberVO) session.getAttribute("memberVO");
System.out.println(memberVO.getMname());
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

table#table-2 {
	font-size: 10 vmin;
	margin: 0 auto;
	white-space: nowrap;
}

h2 {
	margin-top: 40px;
}

table {
	width: 400px;
	margin-top: 5px;
	margin-bottom: 5px;
	color: black;
}

td {
	padding: 7px;
	font-weight: bold;
	/* 	text-align: center; */
}
</style>

</head>
<body>
	<%@ include file="/front-end/front-end_background.jsp"%>
	<main>
		<table id="table-2">
			<h2 align="center" valign="center">會員資料</h2>
			<%-- 		<tr><th>會員編號</th><td>${memberVO.memno}</td></tr> --%>
			<tr>
				<td>會員姓名</td>
				<td>${memberVO.mname}</td>
			</tr>
			<tr>
				<td>身分證字號</td>
				<td>${memberVO.mem_id}</td>
			</tr>
			<tr>
				<td>性別</td>
				<td>${memberVO.gender}</td>
			</tr>
			<tr>
				<td>生日</td>
				<td>${memberVO.bday}</td>
			</tr>
			<tr>
				<td>地址</td>
				<td>${memberVO.address}</td>
			</tr>
			<tr>
				<td>電話</td>
				<td>${memberVO.phone}</td>
			</tr>
			<tr>
				<td>帳號</td>
				<td>${memberVO.maccount}</td>
			</tr>
			<tr>
				<td>密碼</td>
				<td>${memberVO.mpassword}</td>
			</tr>
			<tr>
				<td>郵箱</td>
				<td>${memberVO.mem_email}</td>
			</tr>
			<%-- 		<tr><th>帳號狀態</th><td>${memberVO.mem_state}</td></tr>	 --%>
			<%-- 		<tr><th>驗證狀態</th><td>${memberVO.mem_verify}</td></tr> --%>

			<td style="width: 100px;">
				<FORM METHOD="post"
					ACTION="<%=request.getContextPath()%>/front-end/member/MemberServlet.do"
					style="margin-bottom: 0px;">
					<input type="submit" value="修改"> <input type="hidden"
						name="memno" value="${memberVO.memno}"> <input
						type="hidden" name="action" value="front-end_member_update">
				</FORM>
			</td>
		</table>
	</main>
	<br>
	<br>
	<br>
</body>
<%@ include file="/front-end/front-end_footer.jsp"%>
</html>