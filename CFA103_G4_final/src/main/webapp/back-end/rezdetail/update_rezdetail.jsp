<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.rez_detail.model.*"%>
<%@ page import="com.rez.model.*"%>
<%@ page import="com.table.model.*"%>
<jsp:useBean id="tableSvc" scope="page" class="com.table.model.TableService" />

<%
  RezDetailVO rezdetailVO = (RezDetailVO) request.getAttribute("rezdetailVO"); 
%>

<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
<title>安排桌位</title>
<style>

*{
		padding: 0;
		margin: 0;
		box-sizing: border-box;
	}
	html{
		height: 100px;
		font-size : 50px;
	}
	header{
		background-color: lightsalmon;
		height: 100px;
		font-size : 50px;
		font-weight:bold;
		line-height: 100px;
		text-align: center;

	}
	aside{
		height: calc(100% - 101px);
		width: 200px;
		background-color: bisque;
		position: fixed;
		left: 0;
		overflow-y: auto;
	}
	ul.nav_list{
		padding-top: 1px;
		list-style: none;

	}
	.nav_list li{
		background-color: white;
		width: 135px;
		height: 40px;		
		margin: 50px auto;
		/* padding: 10px 0px; */		
		border-radius: 15px;
		box-shadow: 5px 5px dimgray;	
	}
	.nav_list li:hover{
		transform: scale(1.1);
	}
	.nav_list li a{
		font-size: 18px;
		font-weight:bold;
		display: block;
		text-align: center; 
		line-height: 40px;
		text-decoration: none;
		color: black;		
	}
	main{		
		position: absolute;
		top: 10%;
		left: 12.5%;
		height: calc(100vh - 100px);
		width: calc(100% - 200px);
		padding-top: 45px;			
	}
	main table{
	margin: auto;
	
/* 	table-layout: fixed; */
	}
	table, th, td {
/*     	border: 1px solid black; */
  	}
  	th, td {
  		font-size: 20px;
  		padding: 5px;
/*     	text-align: center; */
  	}
  	input{
		font-size: 20px;
	}
	.buttonHolder{ 
		text-align: center; 
		top: 70%;
	}
	 b {
   font-size: 25px;
  }

</style>
</head>
<body>

<!-- 樣式套版 -->
<%@ include file="/back-end/sidebar.jsp"%>
<%@ include file="/back-end/background.html"%>

<main>
<%-- 錯誤表列 --%>
<c:if test="${not empty errorMsgs}">
	<font style="color:red">請修正以下錯誤:</font>
	<ul>
		<c:forEach var="message" items="${errorMsgs}">
			<li style="color:red">${message}</li>
		</c:forEach>
	</ul>
</c:if>
<FORM METHOD="post" ACTION="rezdetail.do" name="form1">
<table>
	<tr>
		<td><font color=red><b>*</b></font>訂位編號:</td>
		<td><%=rezdetailVO.getRezno()%></td>
	</tr>
	

	<tr>
	<td>
		<li>
	    <FORM METHOD="post" ACTION="table.do">
				<b>選擇桌位編號:</b>
				<select size="1" name="tableno" style= "font-size: 25px;">
					<c:forEach var="tableVO" items="${tableSvc.getAllAvailable()}">
						<option value="${tableVO.tableno}">${tableVO.tableno}
					</c:forEach>
				</select>
				<input type="hidden" name="action" value="getOne_For_Update">
		</FORM>
		</li>
    </td>
	</tr>
</table>
<div class="buttonHolder">
<input type="hidden" name="action" value="update">
<input type="hidden" name="tableno" value="<%=rezdetailVO.getTableno()%>">
<input type="submit" value="送出修改"></FORM>
</div>
</main>
</body>
</html>