<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ page import="com.member.model.*"%>
<!-- include������(sidebar) -->
<aside onload="connect();" onunload="disconnect();">
	<%@ include file="/back-end/sidebar.jsp"%>
</aside>
<!-- include�I���Ϥ�(������Ԫ��S��meal��) -->
<%@ include file="/front-end/shopping_system/background/ListAllBG.html"%>

<%
MemberVO memberVO = (MemberVO) request.getAttribute("memberVO"); //EmpServlet.java(Concroller), �s�Jreq��empVO����
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

table#table-1 {
	text-align: center;
	margin: 0 auto;
}

table#table-1 h4 {
	color: red;
	margin-bottom: 1px;
}

table#table-2 {
	font-size: 10 vmin;
	margin: 0 auto;
	white-space: nowrap;
}

h2{
	margin-top: 40px;
}

h4 {
	color: blue;
	display: inline;
}

table {
	width: 1000px;
	margin-top: 5px;
	margin-bottom: 5px;
	color: black;
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
	<body bgcolor='white'>

		<table id="table-1">
			<tr>
				<td>
					<h2>�|�����</h2>
					<h4>
						<a href="select_page.jsp"><img src="icon/light-bulb.png"
							width="30" height="30" border="0"> HomePage</a>
					</h4>
				</td>
			</tr>
		</table>

		<table id="table-2">
			<tr>
				<th>�|���s��</th>
				<th>�|���m�W</th>
				<th>�ʧO</th>
				<th>�ͤ�</th>
				<th>�a�}</th>
				<th>�q��</th>
				<th>�b��</th>
				<th>�K�X</th>
				<th>�l�c</th>
				<th>�b�����A</th>
				<th>���Ҫ��A</th>
				<th>�����Ҧr��</th>
			</tr>
			<tr>
				<td><%=memberVO.getMemno()%></td>
				<td><%=memberVO.getMname()%></td>
				<td><%=memberVO.getGender()%></td>
				<td><%=memberVO.getBday()%></td>
				<td><%=memberVO.getAddress()%></td>
				<td><%=memberVO.getPhone()%></td>
				<td><%=memberVO.getMaccount()%></td>
				<td><%=memberVO.getMpassword()%></td>
				<td><%=memberVO.getMem_email()%></td>
				<td><%=memberVO.getMem_state()%></td>
				<td><%=memberVO.getMem_verify()%></td>
				<td><%=memberVO.getMem_id()%></td>
				<td>
					<FORM METHOD="post"
						ACTION="<%=request.getContextPath()%>/back-end/member/MemberServlet.do"
						style="margin-bottom: 0px;">
						<input type="submit" value="�ק�"> <input type="hidden"
							name="memno" value="${memberVO.memno}"> <input
							type="hidden" name="action" value="getOne_For_Update">
					</FORM>
				</td>
				<td>
					<FORM METHOD="post"
						ACTION="<%=request.getContextPath()%>/back-end/member/MemberServlet.do"
						style="margin-bottom: 0px;">
						<input type="submit" value="�R��"> <input type="hidden"
							name="memno" value="${memberVO.memno}"> <input
							type="hidden" name="action" value="delete">
					</FORM>
				</td>
			</tr>

		</table>
	</body>
</main>
</html>