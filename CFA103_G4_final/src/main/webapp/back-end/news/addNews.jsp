<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.news.model.*"%>

<%
NewsVO newsVO = (NewsVO) request.getAttribute("newsVO");

%>
<!DOCTYPE html>
<html>
<head>
<meta charset="BIG5">
<title>新增最新消息</title>

<style>

.p{
		position: absolute;
		top: 17.5%;
		left: 37.5%;
		height: calc(100vh - 100px);
		width: calc(100% - 200px);
		}


#a {
 
  height:100px;
}
</style>

<style>
table {
	width: 800px;

	margin-top: 1px;
	margin-bottom: 1px;
}

table, th, td {
	border: 0px solid #CCCCFF;	
}
td{
width:100px;
}
th, td {
	padding: 1px;
}
textarea{
width:350px;
height:200px;
}
</style>


</head>
<body>


<%@ include file="/back-end/sidebar.jsp"%>
<%@ include file="/back-end/background.html"%>

<div class="p">
<%-- 錯誤表列 --%>
<c:if test="${not empty errorMsgs}">
	
	<ul>
		<c:forEach var="message" items="${errorMsgs}">
			<li style="color: red">${message}</li>
		</c:forEach>
	</ul>
</c:if>

<jsp:useBean id="dao" scope="page" class="com.emp.model.EmpJNDIDAO" />
<h3>消息新增:</h3>
<FORM METHOD="post" ACTION="<%=request.getContextPath()%>/back-end/news/news.do" name="form1" enctype="multipart/form-data" >
	<table>
		

		<tr>
			<td>新增消息標題:</td>
			<td><input placeholder="消息標題" type="TEXT" name="news_title" size="45"
				value="<%=(newsVO == null) ? "" : newsVO.getNews_title()%>" /></td>
		</tr>
		<tr>
		<td>新增消息內容:</td>
		<td>
		<textarea placeholder="消息內文"  name="news_content"><%=(newsVO == null) ? "" : newsVO.getNews_content()%></textarea>
		</td>
		</tr>
		
		<tr>
		<td>圖片新增:</td>
		<td>
			<input type="file" name="upnewsfile" >
		</td>
	</tr>
			
	</table>
			
	<input type="hidden" name="empno" value="${empVO.empno}">
	<input type="hidden" name="action" value="insert">
	 <input type="submit" value="送出新增">
</FORM>

</div>
</body>



</html>