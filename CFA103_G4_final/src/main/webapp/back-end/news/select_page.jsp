<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="BIG5">
<title>最新消息</title>

<style>
		.p{
		position: absolute;
		top: 25.5%;
		left: 12.5%;
		height: calc(100vh - 100px);
		width: calc(100% - 200px);
		}
		
		.content{
		list-style: none;
		margin: 0 auto;
		padding: 50px 30px;
		flex-wrap: wrap;
		display: table;
		}
		ul input{
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

<div class="p">

	<c:if test="${not empty errorMsgs}">
		<font style="color: red">請修正以下錯誤:</font>
		<ul class="content">
			<c:forEach var="message" items="${errorMsgs}">
				<li style="color: red">${message}</li>
			</c:forEach>
		</ul>
	</c:if>
	<ul class="content">
	

	
	<li>
		<FORM METHOD="post" ACTION="<%=request.getContextPath()%>/back-end/news/news.do">
			<b>輸入最新消息編號:</b> <input type="text" name="newsno"> <input
				type="hidden" name="action" value="getOne_For_Display"> <input
				type="submit" value="送出">
		</FORM>
	</li>
<br>
	<li>
		<FORM METHOD="post" ACTION="<%=request.getContextPath()%>/back-end/news/news.do">
			<b>輸入最新消息日期:</b> <input type="date" name="news_time" > <input
				type="hidden" name="action" value="getDate_For_Display"> <input
				type="submit" value="送出">
		</FORM>
	</li>
<br>
	<jsp:useBean id="dao" scope="page" class="com.news.model.NewsDAO" />
	
	<li>
		<FORM METHOD="post" ACTION="<%=request.getContextPath()%>/back-end/news/news.do">
			<b>查詢最新消息編號:</b> <select size="1" name="newsno">
				<c:forEach var="newsVO" items="${dao.all}">
					<option value="${newsVO.newsno}">${newsVO.newsno}
				</c:forEach>
			</select> <input type="hidden" name="action" value="getOne_For_Display">
			<input type="submit" value="送出">
		</FORM>
	</li>
</ul>
		
	
	
</div>	
</body>


</html>