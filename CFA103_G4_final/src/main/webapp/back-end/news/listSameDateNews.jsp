<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.news.model.*"%>

<%
NewsService newsSvc=new NewsService();
List<NewsVO> list = (List)request.getAttribute("list");
pageContext.setAttribute("list", list);
%>

<!DOCTYPE html>
<html>
<head>
<title>最新資訊</title>
<meta content="text/html; charset=UTF-8" pageEncoding="UTF-8">
<style>

.p{
		position: absolute;
		top: 35.5%;
		left: 20.5%;
		height: calc(100vh - 100px);
		width: calc(100% - 200px);
		}

</style>

<style>
table {
	width: 80%;
	background-color:white;
	margin-top: 5px;
	margin-bottom: 5px;
	border-collapse:collapse;;
	border-spacing:0;
	border: 3px solid black;
}


th{
background-color:gray;
color:white;
}

th, td {
	padding: 5px;
	text-align: center;
}
td{
width: 200px;
height: 80px;
border: 1px solid black;
text-align: center;
}
img{
width: 100px;
height: 100px;
}
#content{
width: 40%;
height: 100px;
}
 tr.trdata:hover {
background-color:#bebebe;
}
</style>

</head>
<body>
	
<%@ include file="/back-end/sidebar.jsp"%>
<%@ include file="/back-end/background.html"%>
	<div class="p">
	
	<table>
		<tr>
			<th>最新消息編號</th>
			<th>員工編號</th>
			<th>最新消息項目</th>
			<th>最新消息資訊</th>
			<th>最新消息新增時間</th>
			<th>圖片</th>
			<th>修改</th>
			<th>刪除</th>
			
		</tr>

		<c:forEach var="newsVO" items="${list}">
			<tr class="trdata">
				<td>${newsVO.newsno}</td>
				<td>${newsVO.empno}</td>
				<td>${newsVO.news_title}</td>
				<td id="content">${newsVO.news_content}</td>
				<td>${newsVO.news_time}</td>

				<td>
				<img
						src="<%=request.getContextPath()%>/News_ImageFromDB?id=${newsVO.getNewsno()}" />
				</td>
				<td>
				
			  <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/back-end/news/news.do" style="margin-bottom: 0px;">
			    <input type="image" class="button_update" src="<%=request.getContextPath()%>/pic/icon/edit.svg"  width="20" height="20" >
			     <input type="hidden" name="newsno"  value="${newsVO.newsno}">
			     <input type="hidden" name="action"	value="getOne_For_Update"></FORM>
			</td>
			<td>
			  <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/back-end/news/news.do" style="margin-bottom: 0px;">
			     <input type="image" class="button_update" src="<%=request.getContextPath()%>/pic/icon/trash.svg"  width="20" height="20" >
			     <input type="hidden" name="newsno"  value="${newsVO.newsno}">
			     <input type="hidden" name="action" value="delete"></FORM>
			</td>
			</tr>
		</c:forEach>

	</table>
</div>

</body>
</html>