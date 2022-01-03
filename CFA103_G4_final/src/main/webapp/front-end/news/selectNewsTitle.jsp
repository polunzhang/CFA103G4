<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.news.model.*"%>


<%
NewsService newsSvc = new NewsService();
List<NewsVO> list = newsSvc.getAll();
pageContext.setAttribute("list", list);
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="BIG5">
<link rel="stylesheet"
 href="https://stackpath.bootstrapcdn.com/bootstrap/4.2.1/css/bootstrap.min.css"
 integrity="sha384-GJzZqFGwb1QTTN6wy59ffF1BuGJpLSa9DkKMp0DgiMDm4iYMj70gZWKYbI706tWS"
 crossorigin="anonymous">
<title>最新資訊</title>

<style type="text/css">


table {
 margin: 0px auto;
 height: 500px;
 width: 1000px;
 background-image:url('<%=request.getContextPath()%>/pic/newsback.jpg');
 background-repeat: no-repeat; 
    background-size: 100% 100%;
}

#newspic{
 height: 200px;
 width: 200px;
}
#news{
margin-top:5%;
}

</style>
</head>
<body>
<%@ include file="/front-end/front-end_background.jsp"%>

   <div id="news">
   <h1>最新消息</h1>
   <table>
   
    <%@ include file="page1.file"%>
    
    <c:forEach var="newsVO" items="${list}" begin="<%=pageIndex%>"
     end="<%=pageIndex+rowsPerPage-1%>">
     
     <tr>
      <td>
      <img src="<%=request.getContextPath()%>/News_ImageFromDB?id=${newsVO.getNewsno()}" id="newspic" />
      </td>
      <td>
      <h3 id="title">${newsVO.news_title}</h3>
      <a href="<%=request.getContextPath()%>/showOneNews?id=${newsVO.getNewsno()}">查看內文</a>
      </td>
      <td>${newsVO.news_time}</td>
     
      
     </tr>
     
     
    </c:forEach>

   </table>
   <%@ include file="page2.file"%>
  </div>


 <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js"
  integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo"
  crossorigin="anonymous"></script>
 <script
  src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.6/umd/popper.min.js"
  integrity="sha384-wHAiFfRlMFy6i5SRaxvfOCifBUQy1xHdJ/yoi7FRNXMRBu5WHdZYu1hA6ZOblgut"
  crossorigin="anonymous"></script>
 <script
  src="https://stackpath.bootstrapcdn.com/bootstrap/4.2.1/js/bootstrap.min.js"
  integrity="sha384-B0UglyR+jN6CkvvICOB2joaf5I4l3gm9GU6Hc1og6Ls7i6U/mkkaduKaBhlAXv9k"
  crossorigin="anonymous"></script>
 <footer>
<%@ include file="/front-end/front-end_footer.jsp"%>
</footer> 

</body>
</html>