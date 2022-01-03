<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.emperDetail.model.*"%>

<%   
    List<EmperDetailVO> list = (List) request.getAttribute("list");
%>

<jsp:useBean id="emperSvc" scope="page" class="com.emper.model.EmperService" />
<jsp:useBean id="empSvc" scope="page" class="com.emp.model.EmpService" />

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>查詢權限明細結果</title>
</head>
<body>

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
		background-color: cornflowerblue;
		height: 100px;
		font-size : 50px;
		font-weight:bold;
		line-height: 100px;
		text-align: center;

	}
	aside{
		height: calc(100% - 101px);
		width: 200px;
		background-color: lightskyblue;
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
	padding-top: 45px;		
	}
	main table{
	margin: auto;
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

.delete {
  background-image: linear-gradient(#ffa8a8, #e03131);
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


<!-- <header>查詢權限明細結果</header> -->

<!-- <aside> -->
<!-- 	<ul class="nav_list"> -->
<%-- 		<li><a href='<%= request.getContextPath() %>/back-end/emp/listAllEmp.jsp'>顯示全部員工</a></li> --%>
<%-- 		<li><a href='<%= request.getContextPath() %>/back-end/emp/select_page.jsp'>查詢單一員工</a></li> --%>
<%-- 		<li><a href='<%= request.getContextPath() %>/back-end/emp/addEmp.jsp'>員工管理</a></li> --%>
<%-- 		<li><a href='<%= request.getContextPath() %>/back-end/emper/select_page_emper.jsp'>員工權限管理</a></li> --%>
<%-- 		<li><a href='<%= request.getContextPath() %>/back-end/emper/addEmper.jsp'>新增員工權限</a></li> --%>
<!-- 		<li><a href='select_page_emper_detail.jsp'>權限明細查詢</a></li> -->
<!-- 		<li><a href='listAllEmperDetail.jsp'>所有權限明細</a></li> -->
<!-- 		<li><a href='addEmperDetail.jsp'>新增權限明細</a></li> -->
		
<!-- 	</ul> -->
<!-- </aside> -->

<main>

<%-- 錯誤表列 --%>
<c:if test="${not empty errorMsgs}">
	<font style="color:red; font-size: 15px; text-align: center;">請修正以下錯誤:</font>
	<ul>
		<c:forEach var="message" items="${errorMsgs}">
			<li style="color:red; font-size: 15px; text-align: center;">${message}</li>
		</c:forEach>
	</ul>
</c:if>

<div class="divForm">
<FORM METHOD="post" ACTION="emperDetail.do">
				<b>輸入員工編號:</b> <input type="text" name="empno" size="5"> 
				<input type="hidden" name="action" value="getEmpno_For_Display"> 
				<input type="submit" value="送出" class="send">
</FORM>
<FORM METHOD="post" ACTION="emperDetail.do">
				<b>輸入權限編號:</b> <input type="text" name="emperno" size="5"> 
				<input type="hidden" name="action" value="getEmperno_For_Display"> 
				<input type="submit" value="送出" class="send">
</FORM>	
		<jsp:useBean id="empSvc1" scope="page" class="com.emp.model.EmpService" />

<FORM METHOD="post" ACTION="emperDetail.do">
				<b>選擇員工:</b> <select size="1" name="empno">
					<c:forEach var="empVO" items="${empSvc1.all}">
						<option value="${empVO.empno}">${empVO.empno}${empVO.ename}
					</c:forEach>
				</select> <input type="hidden" name="action" value="getEmpno_For_Display">
				<input type="submit" value="送出" class="send">
</FORM>
		<jsp:useBean id="emperSvc1" scope="page" class="com.emper.model.EmperService" />

<FORM METHOD="post" ACTION="emperDetail.do">
				<b>選擇權限:</b> <select size="1" name="emperno">
					<c:forEach var="emperVO" items="${emperSvc1.all}">
						<option value="${emperVO.emperno}">${emperVO.emperno}${emperVO.emper_name}
					</c:forEach>
				</select> <input type="hidden" name="action" value="getEmperno_For_Display">
				<input type="submit" value="送出" class="send">
</FORM>
</div>


<table id="customers">
	<tr>
		<th>權限編號</th>
		<th>權限名稱</th>
		<th>員工編號</th>
		<th>員工姓名</th>
		<th>刪除</th>
	</tr>

<c:forEach var="emperDetailVO" items="${list}">
		
		<tr>
			<td>${emperDetailVO.emperno}</td>
			<td>${emperSvc.getOneEmper(emperDetailVO.emperno).emper_name}</td>
			<td>${emperDetailVO.empno}</td>
			<td>${empSvc.getOneEmp(emperDetailVO.empno).ename}</td>	
			<td>
				<FORM METHOD="post"
						ACTION="<%=request.getContextPath()%>/back-end/emperDetail/emperDetail.do"
						style="margin-bottom: 0px;">
						<input type="submit" value="x" class="delete"> <input type="hidden"
							name="empno" value="${emperDetailVO.empno}"> <input
							type="hidden" name="emperno" value="${emperDetailVO.emperno}">
						<input type="hidden" name="action" value="delete">
				</FORM>
			</td>		
		</tr>
	</c:forEach>
</table>
</main>


</body>
</html>