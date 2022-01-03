<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<aside onload="connect();" onunload="disconnect();" >
    <%@ include file="/back-end/sidebar.jsp"%>
</aside>
<%@ include file="/back-end/background.html"%>
<html>

<head>
<title>BIB桌位管理</title>

<style>
h4 {
	color: blue;
	display: inline;
}
#main{
position: absolute;
top: 3.5%;
left: 12.5%;
height: calc(100vh - 100px);
width: calc(100% - 200px);
margin-left: 200px;
}
</style>

</head>
<body>
<div id="main">
	
<h3>BIB桌位管理</h3>

	<h3>桌位查詢:</h3>

	<%-- 錯誤表列 --%>
	<c:if test="${not empty errorMsgs}">
		<font style="color: red">請修正以下錯誤:</font>
		<ul>
			<c:forEach var="message" items="${errorMsgs}">
				<li style="color: red">${message}</li>
			</c:forEach>
		</ul>
	</c:if>

	<ul>
		<li><a href='listAllTable.jsp'>List</a> all Tables.<br> <br></li>

		<li>
			<FORM METHOD="post" ACTION="table.do">
				<b>輸入桌位編號 (如1):</b>
				<input type="text" name="tableno">
				<input type="hidden" name="action" value="getOne_For_Display">
				<input type="submit" value="送出">
			</FORM>
		</li>

		<jsp:useBean id="tableSvc" scope="page"
			class="com.table.model.TableService" />

		<li>
			<FORM METHOD="post" ACTION="table.do">
				<b>選擇桌位編號 (如1):</b> <select size="1" name="tableno">
					<c:forEach var="tableVO" items="${tableSvc.all}">
						<option value="${tableVO.tableno}">${tableVO.tableno}
					</c:forEach>
				</select>
				<input type="hidden" name="action" value="getOne_For_Display">
				<input type="submit" value="送出">
			</FORM>
		</li>
		
		<li>
			<FORM METHOD="post" ACTION="table.do">
				<b>輸入桌位人數 (如1):</b>
				<input type="text" name="table_nop">
				<input type="hidden" name="action" value="findByTableNop">
				<input type="submit" value="送出">
			</FORM>
		</li>

		<li>
			<FORM METHOD="post" ACTION="table.do">
				<b>輸入訂位狀態:</b> 
				<input type="radio" name="table_status" value="0">空位
				<input type="radio" name="table_status" value="1">已入座
				<input type="radio" name="table_status" value="2">保留位 
				<input type="hidden" name="action" value="findByTableStatus"> 
				<input type="submit" value="送出">
			</FORM>
		</li>
	</ul>

	<h3>桌位管理</h3>

	<ul>
		<li><a href='addTable.jsp'>Add</a> a new Table.</li>
	</ul>
</div>
</body>
</html>