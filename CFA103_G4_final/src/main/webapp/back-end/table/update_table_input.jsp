<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.table.model.*"%>
<aside onload="connect();" onunload="disconnect();" >
    <%@ include file="/back-end/sidebar.jsp"%>
</aside>
<%@ include file="/back-end/background.html"%>

<%
  TableVO tableVO = (TableVO) request.getAttribute("tableVO"); //TableServlet.java (Concroller) 存入req的tableVO物件 (包括幫忙取出的tableVO, 也包括輸入資料錯誤時的tableVO物件)
%>

<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
<title>桌位資料修改</title>

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
		 <h3>桌位資料修改</h3>
		 <h4><a href="select_page_table.jsp">回首頁</a></h4>
	</td></tr>
</table>

<h3>資料修改:</h3>

<%-- 錯誤表列 --%>
<c:if test="${not empty errorMsgs}">
	<font style="color:red">請修正以下錯誤:</font>
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
		<td>桌號:<font color=red><b>*</b></font></td>
		<td><%=tableVO.getTableno()%></td>
	</tr>
	<tr>
		<td>桌位人數:</td>
		<td><input type="TEXT" name="table_nop" size="45" value="<%=tableVO.getTable_nop()%>" /></td>
	</tr>
</table>
<table>
	<tr>
		<td>桌位狀態:
		<input type="radio" name="table_status" value="0" />0
		<input type="radio" name="table_status" value="1" />1
		<input type="radio" name="table_status" value="2" />2
		</td>
	</tr>
</table>
<br>
<input type="hidden" name="action" value="update">
<input type="hidden" name="tableno" value="<%=tableVO.getTableno()%>">
<input type="submit" value="送出修改"></FORM>
</main>
</body>
</html>