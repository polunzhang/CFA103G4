<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.table.model.*"%>
<aside onload="connect();" onunload="disconnect();" >
    <%@ include file="/back-end/sidebar.jsp"%>
</aside>
<%@ include file="/back-end/background.html"%>

<%
    TableService tableSvc = new TableService();
    ServletContext context = getServletContext();
    Integer table_nop = (Integer)context.getAttribute("table_nop");
System.out.println("table_nop="+table_nop);
    List<TableVO> list = tableSvc.findByTableNop(table_nop);
 System.out.println("list="+list);
    pageContext.setAttribute("list",list);
%>


<html>
<head>
<title>使用桌位人數查詢結果</title>

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
	width: 800px;
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
<body bgcolor='white'>
<main>
<table id="table-1">
	<tr><td>
		 <h3>使用桌位人數查詢結果</h3>
		 <h4><a href="select_page_table.jsp">回首頁</a></h4>
	</td></tr>
</table>

<%-- 錯誤表列 --%>
<c:if test="${not empty errorMsgs}">
	<font style="color:red">請修正以下錯誤:</font>
	<ul>
		<c:forEach var="message" items="${errorMsgs}">
			<li style="color:red">${message}</li>
		</c:forEach>
	</ul>
</c:if>

<table>
	<tr>
		<th>桌號</th>
		<th>桌位人數</th>
		<th>桌位狀態</th>
	</tr>
	<c:forEach var="tableVO" items="${list}">
		<tr>
			<td>${tableVO.tableno}</td>
			<td>${tableVO.table_nop}</td>
			<td>${tableVO.table_status}</td>
		</tr>
	</c:forEach>
</table>
</main>
</body>
</html>