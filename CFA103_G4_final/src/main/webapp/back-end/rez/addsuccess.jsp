<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.rez.model.*"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<%
  RezVO rezVO = (RezVO) request.getAttribute("rezVO");
%>

<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
<title>訂位成功</title>
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
		 <h4><a href="<%=request.getContextPath()%>/back-end/rez/select_page.jsp">回首頁</a></h4>
	</td></tr>
</table>

<tr>您好,${rezVO.lastname}${rezVO.sex}</tr>
<tr>已為您預約<fmt:formatDate value="${rezVO.reservationdate}" pattern="yyyy-MM-dd"/>,
			 <fmt:formatDate value="${rezVO.reservationtime}" pattern="HH:mm"/>入場,
			 人數${rezVO.num_of_ppl}人</tr>
<tr>將為您候位10分鐘, 煩請於時間內入場, 逾時將取消訂位</tr>			

<jsp:useBean id="rezSvc" scope="page" class="com.rez.model.RezService" /> 

</body>
</html>