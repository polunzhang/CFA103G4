<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.online_order.model.*" %>

<%
	OnlineOrderVO onlineOrderVO = (OnlineOrderVO) request.getAttribute("onlineOrderVO");
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>線上訂單資料 - listOneOnlineOrder.jsp</title>

<style>
  table#table-1 {
	background-color: #CCCCFF;
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
	width: 600px;
	background-color: white;
	margin-top: 5px;
	margin-bottom: 5px;
  }
  table, th, td {
    border: 1px solid #CCCCFF;
  }
  th, td {
    padding: 5px;
    text-align: center;
  }
</style>

</head>
<body bgcolor='white'>

<table id="table-1">
	<tr><td>
		 <h3>員工資料 - ListOneOnlineOrder.jsp</h3>
		 <h4><a href="select_page_online_order.jsp"><img src="images/back1.gif" width="100" height="32" border="0">回首頁</a></h4>
	</td></tr>
</table>

<table>
	<tr>
		<th>訂單編號</th>
		<th>員工編號</th>
		<th>會員編號</th>
		<th>結帳狀態</th>
		<th>地址</th>
		<th>取餐/送達時間</th>
		<th>訂單成立時間</th>
		<th>訂單金額</th>
		<th>付款方式</th>
	</tr>
	<tr>
		<td><%=onlineOrderVO.getOlno()%></td>
		<td><%=onlineOrderVO.getEmpno()%></td>
		<td><%=onlineOrderVO.getMemno()%></td>
		<td><%=onlineOrderVO.getPay_status()%></td>
		<td><%=onlineOrderVO.getAddress()%></td>
		<td><%=onlineOrderVO.getSet_time()%></td>
		<td><%=onlineOrderVO.getCreate_time()%></td>
		<td><%=onlineOrderVO.getTotal()%></td>
		<td><%=onlineOrderVO.getPay_way()%></td>
	</tr>
</table>

</body>
</html>