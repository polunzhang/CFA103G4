<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.news.model.*"%>

<%
NewsVO newsVO = (NewsVO) request.getAttribute("newsVO");
String path = (String) session.getAttribute("path");
%>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
<title>修改最新消息</title>
<style>

.p{
		position: absolute;
		top: 5%;
		left: 37.5%;
		height: calc(100vh - 100px);
		width: calc(100% - 200px);
		}
table#table-1 {
	background-color: #CCCCFF;
	border: 2px solid black;
	text-align: center;
}

table#table-1 h4 {
	color: red;
	display: block;
	margin-bottom: 1px;
}

h4 {
	color: blue;
	display: inline;
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
img{
width: 100px;
height: 100px;
}
</style>
</head>
<body>
<%@ include file="/back-end/sidebar.jsp"%>
<%@ include file="/back-end/background.html"%>

<div class="p">
	<h3>資料修改:</h3>
	<c:if test="${not empty errorMsgs}">
		<font style="color: red">請修正以下錯誤:</font>
		<ul>
			<c:forEach var="message" items="${errorMsgs}">
				<li style="color: red">${message}</li>
			</c:forEach>
		</ul>
	</c:if>

	<FORM METHOD="post" ACTION="<%=request.getContextPath()%>/back-end/news/news.do" name="form1"  enctype="multipart/form-data">
		<table>
			<tr>
				<td>最新消息編號:<font color=red><b>*</b></font></td>
				<td><%=newsVO.getNewsno()%></td>
			</tr>
			<jsp:useBean id="dao" scope="page" class="com.emp.model.EmpJNDIDAO" />
			<tr>
				<td>員工編號:</td>
				<td><select size="1" name="empno">
						<c:forEach var="empVO" items="${dao.all}">
							<option value="${empVO.empno}">${empVO.empno}-${empVO.ename}
						</c:forEach>
				</select></td>
			</tr>
			<tr>
				<td>修改消息項目:</td>
				<td><input type="TEXT" name="news_title" size="45"
					value="<%=newsVO.getNews_title()%>" /></td>
			</tr>
			<tr>
				<td>修改消息資訊:</td>
				<td>
		<textarea  name="news_content" ><%=newsVO.getNews_content()%></textarea>
		</td>
			</tr>
			
			
			<tr>
				<td>修改消息時間:</td>
				<td><input name="news_time" id="f_date1" type="text"></td>
			</tr>
			<tr>
			<td >新增消息圖片:</td>
			<td><img src="<%=request.getContextPath()%>/News_ImageFromDB?id=${newsVO.newsno}"}></td>
			</tr>
			
			<tr>
				<td>圖片修改:</td>
				<td>
					<input type="file" name="updatenewsfile" >
				</td>
			</tr>
		</table>
		
		<br> 
		<input type="hidden" name="action" value="update"> 
		<input type="hidden" name="newsno" value="<%=newsVO.getNewsno()%>">
		<input type="submit" value="送出修改">
	</FORM>
	
	</div>
</body>

<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/datetimepicker/jquery.datetimepicker.css" />
<script src="<%=request.getContextPath()%>/datetimepicker/jquery.js"></script>
<script src="<%=request.getContextPath()%>/datetimepicker/jquery.datetimepicker.full.js"></script>

<style>
  .xdsoft_datetimepicker .xdsoft_datepicker {
           width:  300px;   /* width:  300px; */
  }
  .xdsoft_datetimepicker .xdsoft_timepicker .xdsoft_time_box {
           height: 151px;   /* height:  151px; */
  }
</style>

<script>
        $.datetimepicker.setLocale('zh');
        $('#f_date1').datetimepicker({
           theme: '',              //theme: 'dark',
 	       timepicker:false,       //timepicker:true,
 	       step: 1,                //step: 60 (這是timepicker的預設間隔60分鐘)
 	       format:'Y-m-d',         //format:'Y-m-d H:i:s',
 		   value: '<%=newsVO.getNews_time()%>', 
           //disabledDates:        ['2017/06/08','2017/06/09','2017/06/10'], // 去除特定不含
           //startDate:	            '2017/07/10',  // 起始日
           //minDate:               '-1970-01-01', // 去除今日(不含)之前
           //maxDate:               '+1970-01-01'  // 去除今日(不含)之後
        });
        
        
   
        
</script>
</html>