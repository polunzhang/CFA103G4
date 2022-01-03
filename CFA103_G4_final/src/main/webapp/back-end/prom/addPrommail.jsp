<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page import="com.prom.model.*"%>
<%@page import="com.emp.model.*"%>
<%@ page import="java.util.*" %>
<%
PromJDBCDAO Pdao = new PromJDBCDAO();
List<PromVO> plist = Pdao.getAll();
pageContext.setAttribute("Plist", plist);
%>
<% Date date = new Date();%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
<title>促銷信件設定</title>

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
	
		 <h3>促銷活動信件設定</h3>
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


<FORM METHOD="post" ACTION="<%=request.getContextPath()%>/promdetail/promdetail.do" name="form1" >
<table>
	<tr>
		<td style="width:250px;">促銷活動推薦信(立即寄出):</td>
		<td>				
			<select size="1" name="promno">
				<c:forEach var="promVO" items="${Plist}">
					<option value="${promVO.promno}">${promVO.promno}.${promVO.prom_title}
				</c:forEach>
			</select>
		</td>
		<td>
			<input type="hidden" name="action" value="sendprom">
			<input type="submit" value="寄出"  class="icon">
		</td>
	</tr>
	</table>
</FORM>
<br><br>
<form METHOD="post" ACTION="<%=request.getContextPath()%>/promdetail/promdetail.do" name="form1">
	<table>
		<tr>
			<td style="width:250px;">促銷活動推薦信(預約寄出):</td>
			<td><input type="datetime-local" name="maildate" id="maildate" min="" max="" required>
			</td>	
			<td>
			<select size="1" name="promno">
				<c:forEach var="promVO" items="${Plist}">
					<option value="${promVO.promno}">${promVO.promno}.${promVO.prom_title}
				</c:forEach>
			</select>
			</td>	
			<td>
				<input type="hidden" name="action" value="prommailfuture">
				<input type="submit" value="寄出"  class="icon">
			</td>
		</tr>
	</table>
</form>
</div>
</body>

</html>