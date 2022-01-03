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
  main {
  position: relative;
  transfrom: translate(-50%, -50%);
  text-align: center; 
  font-size: 20px;
  margin: auto;
  font-family: "微軟正黑體";
  height: 500px;
  line-height: 60px;
  }

</style>

</head>
<body>
<%@ include file="/front-end/front-end_background.jsp"%>
<main>

<div><tr>您好，<td>${rezVO.lastname}</td><td>先生/小姐</td></tr></div>
<tr>已為您預約<td><fmt:formatDate value="${rezVO.reservationdate}" pattern="yyyy-MM-dd"/></td>，
			 <td><fmt:formatDate value="${rezVO.reservationtime}" pattern="HH:mm"/></td>入場，
			 <td>人數${rezVO.num_of_ppl}人</td></tr>
<div><tr>將為您候位10分鐘，煩請於時間內入場，逾時將取消訂位。</tr></div>		
	 
</main>
<br><br><br><br><br><br><br><br><br><br><br><br><br><br>
<%@ include file="/front-end/front-end_footer.jsp"%>
</body>
</html>