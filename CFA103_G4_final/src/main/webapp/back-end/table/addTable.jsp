<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.table.model.*"%>
<aside onload="connect();" onunload="disconnect();" >
    <%@ include file="/back-end/sidebar.jsp"%>
</aside>
<%@ include file="/back-end/background.html"%>
<%
TableVO tableVO = (TableVO) request.getAttribute("tableVO");
%>

<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
<title>����Ʒs�W - addTable.jsp</title>

<style>
table#table-1 {
    color: black;
	border: 2px solid black;
	text-align: center;
	width: 500px;
	height: 30px;
}

table#table-1 h4 {
	color: red;
	display: block;
	margin-bottom: 1px;
}

h4 {
	color: blue;
	display: inline;
}
main{
position: absolute;
top: 3.5%;
left: 12.5%;
height: calc(100vh - 100px);
width: calc(100% - 200px);
margin-left: 200px;
}


</style>

</head>
<body>
<main>
	<table id="table-1">
		<tr>
			<td>
				<h3>�s�W�����</h3>
			</td>
			<td>
				<h4>
					<a href="select_page_table.jsp">�^����</a>
				</h4>
			</td>
		</tr>
	</table>

	<h3>��Ʒs�W:</h3>

	<%-- ���~��C --%>
	<c:if test="${not empty errorMsgs}">
		<font style="color: red">�Эץ��H�U���~:</font>
		<ul>
			<c:forEach var="message" items="${errorMsgs}">
				<li style="color: red">${message}</li>
			</c:forEach>
		</ul>
	</c:if>

	<FORM METHOD="post" ACTION="table.do" name="form1">
		<table>
			<tr>
				<td>���H��:</td>
				<td><input type="TEXT" name="table_nop" size="45"
					value="<%=(tableVO == null) ? "0" : tableVO.getTable_nop()%>" /></td>
			</tr>
		</table>
		<table>
			<tr>
				<td>��쪬�A:</td>
				<td><input type="radio" name="table_status" value="<%=(tableVO == null) ? "0" : tableVO.getTable_status()%>" />�Ŧ�
					<input type="radio" name="table_status" value="<%=(tableVO == null) ? "1" : tableVO.getTable_status()%>" />�w�J�y
					<input type="radio" name="table_status" value="<%=(tableVO == null) ? "2" : tableVO.getTable_status()%>" />�O�d��
				</td>
			</tr>

			<%-- 	<jsp:useBean id="deptSvc" scope="page" class="com.dept.model.DeptService" /> --%>
			<!-- 	<tr> -->
			<!-- 		<td>����:<font color=red><b>*</b></font></td> -->
			<!-- 		<td><select size="1" name="deptno"> -->
			<%-- 			<c:forEach var="deptVO" items="${deptSvc.all}"> --%>
			<%-- 				<option value="${deptVO.deptno}" ${(empVO.deptno==deptVO.deptno)? 'selected':'' } >${deptVO.dname} --%>
			<%-- 			</c:forEach> --%>
			<!-- 		</select></td> -->
			<!-- 	</tr> -->
		</table>

		<br> <input type="hidden" name="action" value="insert"> <input
			type="submit" value="�e�X�s�W">
	</FORM>
</main>
</body>

</html>