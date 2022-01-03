<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.news.model.*"%>

<%
NewsVO newsVO = (NewsVO) request.getAttribute("newsVO");
%>

<!DOCTYPE html>
<html>
<head>
<title>最新訊息資料</title>
<meta content="text/html; charset=UTF-8" pageEncoding="UTF-8">
<style>
body {
	width: 100%;
	height: auto;
	font-family: "微軟正黑體";
	margin: 0px auto;
	text-align: center;
}
div{
width: 70%;
height: auto;
margin: 0px auto;
}

 #newspic {
	width: 300px;
	height: 300px;
}

 h2 {
	text-align: center;	
}
pre{
font-family:Aial ;			
font-size:18px;
font-weight: normal;
font-style: italic;
font-variant:small-caps;
letter-spacing: 2px;
width:100%;
text-align: center;	
word-wrap:break-word; 
word-break:break-all; 
overflow: hidden;
}
#time {
	margin-left: 85%;
}

#back{
background-image:url('<%=request.getContextPath()%>/pic/newsback.jpg');
background-repeat: no-repeat; 
background-size: 100% 100%;

}

</style>

</head>
<body>
<%@ include file="/front-end/front-end_background.jsp"%>
	<div id="back">

		<h1>${newsVO.news_title}</h1>
		<p id="time">${newsVO.news_time}</p>
		<br> 
		<img src="<%=request.getContextPath()%>/News_ImageFromDB?id=${newsVO.getNewsno()}" id="newpic"/>

		<pre>${newsVO.news_content}</pre>


	</div>
	<a href="<%=request.getContextPath()%>/front-end/news/selectNewsTitle.jsp">回選擇頁面</a>
<footer>
<%@ include file="/front-end/front-end_footer.jsp"%>
</footer>	
</body>
</html>