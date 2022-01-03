<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.rez_time.model.*"%>

<%
  RezTimeVO reztimeVO = (RezTimeVO) request.getAttribute("reztimeVO"); 
%>
<html>
<head>
<meta charset="UTF-8">
<title>修</title>

<style>
  table#table-1 {
	background-color: #FFC14F;
    border: 4px solid black;
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
	width: 1000px;
	background-color: white;
	margin-top: 1px;
	margin-bottom: 1px;
  }
  table, th, td {
    border: 0px solid #CCCCFF;
  }
  th, td {
    padding: 5px;
  }
</style>

</head>
<body bgcolor='white'>

<table id="table-1">
	<tr><td>
		 <h3>資料修改</h3>
		 <h4><a href="selectRezTime_page.jsp">回首頁</a></h4>
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

<FORM METHOD="post" ACTION="reztime.do" name="form1">

<table>

	<tr style="display:none">
		<td>編號</td>
		<td><%=reztimeVO.getRez_time_no() %></td>
	</tr>
	<tr style="display:none">
		<td>日期</td>
		<td><%=reztimeVO.getRez_time_date() %></td>
	</tr>
	<tr style="display:none">
		<td>中午上限</td>
		<td><%=reztimeVO.getRez_time_mid_limit() %></td>
	</tr>
	<tr style="display:none">
		<td>晚上上限</td>
		<td><%=reztimeVO.getRez_time_eve_limit() %></td>
	</tr>
	<tr>
		<td>中午</td>
		<td><input type="number" value="<%=reztimeVO.getRez_time_mid()%>" name="rez_time_mid" min="0" max="24" step="1"></td>
	</tr>
	<tr>
		<td>晚上</td>
		<td><input type="number" value="<%=reztimeVO.getRez_time_eve()%>" name="rez_time_eve" min="0" max="24" step="1"></td>
	</tr>
	
<jsp:useBean id="reztimeSvc" scope="page" class="com.rez_time.model.RezTimeService" />	
</table>
<input type="hidden" name="action" value="update">
<input type="hidden" name="rez_time_no" value="<%=reztimeVO.getRez_time_no()%>">
<input type="submit" value="送出修改"></FORM>
</body>
</html>