<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.emp.model.*"%>

<%
    EmpService empSvc = new EmpService();
    List<EmpVO> list = empSvc.getAll();
    pageContext.setAttribute("list",list);
%>

<jsp:useBean id="empSvc1" scope="page" class="com.emp.model.EmpService" />


<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>所有員工資料</title>

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
		top: 3.5%;
		left: 12.5%;	
		height: calc(100vh - 100px);
		width: calc(100% - 200px);
/* 		padding-top: 45px;		 */
	}
	main table{
	margin: auto;
	width: 85%;
/* 	table-layout: fixed; */
	}
	table, th, td {
    	border: 1px solid black;
  	}
  	th, td {
  		font-size: 16px;
    	text-align: center;
    	padding: 5px;
  	}
  	b{
  	font-size: 18px;
  	}
  	form{
  	display: inline-block;
  	}
  	.divForm{
  	text-align: center;
  	}
  	
  	#customers {
  border-collapse: collapse;
  width: 85%;
}

#customers td, #customers th {
  border: 1px solid #ddd;
  padding: 6px;
}

#customers tr:nth-child(even){background-color: #f2f2f2;}
#customers tr{background-color: white;}
#customers tr:hover {background-color: #ddd;}

#customers th {
  padding-top: 10px;
  padding-bottom: 10px;
  text-align: center;
  background-color: CornflowerBlue;
  color: white;
}
.edit {
  background-image: linear-gradient(#0dccea, #0d70ea);
  border: 0;
  border-radius: 4px;
  box-shadow: rgba(0, 0, 0, .3) 0 2px 12px;
  box-sizing: border-box;
  color: #fff;
  cursor: pointer;
  font-family: Montserrat,sans-serif;
  font-size: .4em;
  margin: 2px;
  padding: 3px 10px;
  text-align: center;
  user-select: none;
  -webkit-user-select: none;
  touch-action: manipulation;
}
.send {
  background-image: linear-gradient(#69db7c, #37b24d);
  border: 0;
  border-radius: 4px;
  box-sizing: border-box;
  color: #fff;
  cursor: pointer;
  font-family: Montserrat,sans-serif;
  font-size: .2em;
  margin: 2px;
  padding: 3px 4px;
  text-align: center;
  user-select: none;
  -webkit-user-select: none;
  touch-action: manipulation;
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
	<font style="color:red; font-size: 15px; text-align: center; display: block;">請修正以下錯誤:</font>
	<ul>
		<c:forEach var="message" items="${errorMsgs}">
			<li style="color:red; font-size: 15px; text-align: center;">${message}</li>
		</c:forEach>
	</ul>
</c:if>
 <div class="divForm">
<FORM METHOD="post" ACTION="emp.do">
				<b>輸入員工編號:</b> <input type="text" name="empno"> 
				<input type="hidden" name="action" value="getOne_For_Display"> 
				<input type="submit" value="送出" class="send">
</FORM>
<FORM METHOD="post" ACTION="emp.do">
				<b>選擇員工編號:</b> <select size="1" name="empno">
					<c:forEach var="empVO" items="${empSvc1.all}">
						<option value="${empVO.empno}">${empVO.empno}
					</c:forEach>
				</select> <input type="hidden" name="action" value="getOne_For_Display">
				<input type="submit" value="送出" class="send">
</FORM>
<FORM METHOD="post" ACTION="emp.do">
				<b>選擇員工姓名:</b> <select size="1" name="empno">
					<c:forEach var="empVO" items="${empSvc1.all}">
						<option value="${empVO.empno}">${empVO.ename}
					</c:forEach>
				</select> <input type="hidden" name="action" value="getOne_For_Display">
				<input type="submit" value="送出" class="send">
</FORM>
</div>
<table id="customers">
	<tr>
		<th>員工編號</th>
		<th>職位</th>
		<th>薪水</th>
		<th>員工姓名</th>
		<th>雇用日期</th>
		<th>帳號</th>
		<th>密碼</th>
		<th>在職狀態</th>
		<th>修改</th>
	</tr>

<c:forEach var="empVO" items="${list}">
		
		<tr>
			<td>${empVO.empno}</td>
			<td>${empVO.job}</td>
			<td>${empVO.sal}</td>
			<td>${empVO.ename}</td>
			<td>${empVO.hiredate}</td>
			<td>${empVO.eaccount}</td> 
			<td>${empVO.epassword}</td>
			<td>${(empVO.job_status == 1) ? "在職" : "離職"}
			</td>
			
<%-- 			<td>${empVO.job_status}</td> --%>
			<td>
			  <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/back-end/emp/emp.do" style="margin-bottom: 0px;">
			     <input type="submit" value="修改" class="edit">
			     <input type="hidden" name="empno"  value="${empVO.empno}">
			     <input type="hidden" name="action"	value="getOne_For_Update"></FORM>
			</td>
		</tr>
	</c:forEach>
</table>
</main>

</body>
</html>