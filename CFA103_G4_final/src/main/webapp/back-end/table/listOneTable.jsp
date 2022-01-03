<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ page import="com.table.model.*"%>
<aside onload="connect();" onunload="disconnect();" >
    <%@ include file="/back-end/sidebar.jsp"%>
</aside>
<%@ include file="/back-end/background.html"%>

<%
  TableVO tableVO = (TableVO) request.getAttribute("tableVO"); //EmpServlet.java(Concroller), 存入req的empVO物件
%>

<html>
<head>
<title>桌位資料 - listOneTable.jsp</title>

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
  table {
	width: 600px;
	margin-top: 5px;
	margin-bottom: 5px;
  }
  table, th, td {
    border: 1px solid black;
  }
  th, td {
    padding: 5px;
    text-align: center;
  }
</style>

</head>
<body>
<main>
<table id="table-1">
	<tr><td>
		 <h3>單一桌位資料</h3>
		 <h4><a href="select_page_table.jsp">回首頁</a></h4>
	</td></tr>
</table>

<table>
	<tr>
		<th>桌號</th>
		<th>桌位人數</th>
		<th>桌位狀態</th>
	</tr>
	<tr>
		<td><%=tableVO.getTableno()%></td>
		<td><%=tableVO.getTable_nop()%></td>
		<td><%=tableVO.getTable_status()%></td>
	</tr>
</table>
</main>
</body>
</html>