<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>

<meta charset="UTF-8">
<title>查詢員工</title>

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

	}
	main ul{		
		list-style: none;
		margin: 0 auto;
		padding: 50px 30px;
		flex-wrap: wrap;
		display: table;
	}
	main ul input, select{
		font-size: 20px;
	}
	b{
		font-size: 25px;
	}

</style>

</head>
<body>

<!-- 樣式套版 -->
<%@ include file="/back-end/sidebar.jsp"%>
<%@ include file="/back-end/background.html"%>
	

<!-- <header>查詢員工</header> -->
	
<!-- <aside> -->
<!-- 	<ul class="nav_list"> -->
<!-- 		<li><a href='listAllEmp.jsp'>顯示全部員工</a></li> -->
<!-- 		<li><a href='select_page.jsp'>查詢單一員工</a></li> -->
<!-- 		<li><a href='addEmp.jsp'>員工管理</a></li> -->
<%-- 		<li><a href='<%= request.getContextPath() %>/back-end/emper/select_page_emper.jsp'>員工權限管理</a></li> --%>
<%-- 		<li><a href='<%= request.getContextPath() %>/back-end/emper/addEmper.jsp'>新增員工權限</a></li> --%>
<%-- 		<li><a href='<%= request.getContextPath() %>/back-end/emperDetail/select_page_emper_detail.jsp'>權限明細查詢</a></li> --%>
<%-- 		<li><a href='<%= request.getContextPath() %>/back-end/emperDetail/listAllEmperDetail.jsp'>所有權限明細</a></li> --%>
<%-- 		<li><a href='<%= request.getContextPath() %>/back-end/emperDetail/addEmperDetail.jsp'>新增權限明細</a></li> --%>
<!-- 	</ul> -->
<!-- </aside> -->

<main>

<%-- 錯誤表列 --%>
<c:if test="${not empty errorMsgs}">
	<font style="color:red; font-size: 15px; text-align: center; display: block;">請修正以下錯誤:</font>
	<ul style="padding: 0;">
		<c:forEach var="message" items="${errorMsgs}">
			<li style="color:red; font-size: 15px; text-align: center;">${message}</li>
		</c:forEach>
	</ul>
</c:if>

	<ul>
		<li>
			<FORM METHOD="post" ACTION="emp.do">
				<b>輸入員工編號:</b> <input type="text" name="empno"> 
				<input type="hidden" name="action" value="getOne_For_Display"> 
				<input type="submit" value="送出">
			</FORM>
		</li>

		<jsp:useBean id="empSvc" scope="page" class="com.emp.model.EmpService" />

		<li>
			<FORM METHOD="post" ACTION="emp.do">
				<b>選擇員工編號:</b> <select size="1" name="empno">
					<c:forEach var="empVO" items="${empSvc.all}">
						<option value="${empVO.empno}">${empVO.empno}
					</c:forEach>
				</select> <input type="hidden" name="action" value="getOne_For_Display">
				<input type="submit" value="送出">
			</FORM>
		</li>

		<li>
			<FORM METHOD="post" ACTION="emp.do">
				<b>選擇員工姓名:</b> <select size="1" name="empno">
					<c:forEach var="empVO" items="${empSvc.all}">
						<option value="${empVO.empno}">${empVO.ename}
					</c:forEach>
				</select> <input type="hidden" name="action" value="getOne_For_Display">
				<input type="submit" value="送出">
			</FORM>
		</li>
	</ul>

</main>

</body>
</html>