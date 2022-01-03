<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.table.model.*"%>
<aside onload="connect();" onunload="disconnect();" >
    <%@ include file="/back-end/sidebar.jsp"%>
</aside>
<%@ include file="/back-end/background.html"%>

<%
  TableVO tableVO = (TableVO) request.getAttribute("tableVO"); //TableServlet.java (Concroller) �s�Jreq��tableVO���� (�]�A�������X��tableVO, �]�]�A��J��ƿ��~�ɪ�tableVO����)
%>

<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
<title>����ƭק�</title>

<style>
main{
position: absolute;
top: 3.5%;
left: 12.5%;
height: calc(100vh - 100px);
width: calc(100% - 200px);
margin-left: 200px;
}
  table#table-1 {
  color: black;
    border: 2px solid black;
    text-align: center;
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
</style>

<style>
  table {
	width: 450px;
	margin-top: 1px;
	margin-bottom: 1px;
  }
  table, th, td {
    border: 0px solid #CCCCFF;
  }
  th, td {
    padding: 1px;
  }
</style>

</head>
<body>
<main>
<table id="table-1">
	<tr><td>
		 <h3>����ƭק�</h3>
		 <h4><a href="select_page_table.jsp">�^����</a></h4>
	</td></tr>
</table>

<h3>��ƭק�:</h3>

<%-- ���~��C --%>
<c:if test="${not empty errorMsgs}">
	<font style="color:red">�Эץ��H�U���~:</font>
	<ul>
		<c:forEach var="message" items="${errorMsgs}">
			<li style="color:red">${message}</li>
		</c:forEach>
	</ul>
</c:if>

<FORM METHOD="post" ACTION="table.do" name="form1">
<table>
	<jsp:useBean id="tableSvc" scope="page" class="com.table.model.TableService" />
	<tr>
		<td>�ู:<font color=red><b>*</b></font></td>
		<td><%=tableVO.getTableno()%></td>
	</tr>
	<tr>
		<td>���H��:</td>
		<td><input type="TEXT" name="table_nop" size="45" value="<%=tableVO.getTable_nop()%>" /></td>
	</tr>
</table>
<table>
	<tr>
		<td>��쪬�A:
		<input type="radio" name="table_status" value="0" />0
		<input type="radio" name="table_status" value="1" />1
		<input type="radio" name="table_status" value="2" />2
		</td>
	</tr>
</table>
<br>
<input type="hidden" name="action" value="update">
<input type="hidden" name="tableno" value="<%=tableVO.getTableno()%>">
<input type="submit" value="�e�X�ק�"></FORM>
</main>
</body>
</html>