<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ page import="com.member.model.*"%>


<%
MemberVO memberVO = (MemberVO) session.getAttribute("memberVO");
System.out.println(memberVO.getMname());
%>

<html>
<head>
<title>�|�����</title>

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
			<h2 align="center" valign="center">�|�����</h2>
			<%-- 		<tr><th>�|���s��</th><td>${memberVO.memno}</td></tr> --%>
			<tr>
				<td>�|���m�W</td>
				<td>${memberVO.mname}</td>
			</tr>
			<tr>
				<td>�����Ҧr��</td>
				<td>${memberVO.mem_id}</td>
			</tr>
			<tr>
				<td>�ʧO</td>
				<td>${memberVO.gender}</td>
			</tr>
			<tr>
				<td>�ͤ�</td>
				<td>${memberVO.bday}</td>
			</tr>
			<tr>
				<td>�a�}</td>
				<td>${memberVO.address}</td>
			</tr>
			<tr>
				<td>�q��</td>
				<td>${memberVO.phone}</td>
			</tr>
			<tr>
				<td>�b��</td>
				<td>${memberVO.maccount}</td>
			</tr>
			<tr>
				<td>�K�X</td>
				<td>${memberVO.mpassword}</td>
			</tr>
			<tr>
				<td>�l�c</td>
				<td>${memberVO.mem_email}</td>
			</tr>
			<%-- 		<tr><th>�b�����A</th><td>${memberVO.mem_state}</td></tr>	 --%>
			<%-- 		<tr><th>���Ҫ��A</th><td>${memberVO.mem_verify}</td></tr> --%>

			<td style="width: 100px;">
				<FORM METHOD="post"
					ACTION="<%=request.getContextPath()%>/front-end/member/MemberServlet.do"
					style="margin-bottom: 0px;">
					<input type="submit" value="�ק�"> <input type="hidden"
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