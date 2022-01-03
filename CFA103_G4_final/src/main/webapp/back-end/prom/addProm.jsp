<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page import="com.prom.model.*"%>
<%@page import="com.emp.model.*"%>
<%@ page import="java.util.*" %>

<%
  PromVO promVO = (PromVO) request.getAttribute("promVO");
%>

<%
EmpJDBCDAO dao = new EmpJDBCDAO();
List<EmpVO> list = dao.getAll();
pageContext.setAttribute("list", list);
%>

<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
<title>促銷資料新增</title>

<style>

  table {
	margin-top: 1px;
	margin-bottom: 1px;
  }
  table, th, td {
    border: 0px solid #CCCCFF;
  }
  th, td {
    padding: 1px;
  }
 .preview {
	position: absolute;
	top: 10px;
	left: 130px;
	z-index: 1;
	width: 87.5%;
	height: 94%;
	padding: 15px;
	/* 	border: 2px solid red; */
}
a{ 
	text-decoration:none;
} 
a:hover{ 
	text-decoration:underline;
}
</style>

</head>
<body class="preview">
<!-- include側邊欄(sidebar) -->
<aside onload="connect();" onunload="disconnect();" >
		<%@ include file="/back-end/sidebar.jsp"%>
</aside>
<!-- include背景圖片(毛玻璃拉長特化meal版) -->
		<%@ include file="/back-end/meal/background/ListAllBG.html"%>


<div class="preview">
<header id="table-1" >
	
		 <h3>促銷活動新增</h3>
		 <h4>
		 <a href="<%=request.getContextPath()%>/back-end/prom/listAllProm.jsp">
		 <img src="<%=request.getContextPath()%>/back-end/meal/pic/images/mealHome.svg"
						width="15" height="15" border="0">返回促銷管理頁面
		 </a>
		 </h4>		
	
</header>



<%-- 錯誤表列 --%>
<c:if test="${not empty errorMsgs}">
	<font style="color:red">請修正以下錯誤:</font>
	<ul>
		<c:forEach var="message" items="${errorMsgs}">
			<li style="color:red">${message}</li>
		</c:forEach>
	</ul>
</c:if>

<jsp:useBean id="mealSvc" scope="page" class="com.meal.model.MealService" />
<jsp:useBean id="mealtypeSvc" scope="page" class="com.meal_type.model.MealTypeJDBCDAO" />	

<FORM METHOD="post" ACTION="<%=request.getContextPath()%>/prom/prom.do" name="form1" enctype="multipart/form-data">
<table>
	<tr>
		<td style="width:150px;">員工:</td>
		<td>				
			<select size="1" name="empno">
				<c:forEach var="empVO" items="${list}">
					<option value="${empVO.empno}">${empVO.empno}.${empVO.ename}
				</c:forEach>
			</select>
		</td>
	</tr>
	
	<tr>
		<td style="width:150px;">促銷活動名稱:</td>
		<td><input type="TEXT" name="prom_title" size="30" style="background-color:#00FFFF;"
			 placeholder="請輸入促銷活動名稱，須為繁體中文。" />
		</td>
	</tr>
	
	<tr>
		<td>活動介紹:</td>
		<td><textarea type="TEXT" name="prom_content" cols="50" rows="10" style="background-color:#00FFFF;" 
			 placeholder="請輸入活動介紹。"></textarea>
		</td>
	</tr>
	<tr>
		<td>圖片新增:</td>
		<td>
			<input type="file" name="uppromfile" >
		</td>
	</tr>
	
</table>
<br>

<input type="hidden" name="action" value="insert">
<input type="submit" value="下一步(設定促銷明細)"  class="icon">

</FORM>
</div>
</body>

</html>