<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>

<html>
<head>
<meta charset="UTF-8">
<title>線上訂單明細查詢</title>

<style>
  table#table-1 {
	width: 450px;
	background-color: #FFCCDA;
	margin-top: 5px;
	margin-bottom: 10px;
    border: 3px ridge Gray;
    height: 80px;
    text-align: center;
  }
  table#table-1 h4 {
    color: red;
    display: block;
    margin-bottom: 1px;
  }
  h4 {
    color: pink;
    display: inline;
  }
</style>

</head>
<body bgcolor='white'>

<table id="table-1">
   <tr><td><h3>Online Detail: Home</h3><h4>( MVC )</h4></td></tr>
</table>

<p>This is the Home page for Online Detail: Home</p>

<h3>線上訂單明細查詢:</h3>

<%-- 錯誤表列 --%>
<c:if test="${not empty errorMsgs}">
	<font style="color:red">請修正以下錯誤:</font>
	<ul>
	    <c:forEach var="message" items="${errorMsgs}">
			<li style="color:red">${message}</li>
		</c:forEach>
	</ul>
</c:if>

<ul>
	<li><a href='listAllOnlineDetail.jsp'>List</a>all Online Detail.</li>
	
	
	<li>
    <FORM METHOD="post" ACTION="onlineDetail.do" >
        <b>輸入訂單編號(如1):</b>
        <input type="text" name="olno">
        <input type="hidden" name="action" value="getOne_For_Display">
        <input type="submit" value="送出">
    </FORM>
  </li>
	
	<jsp:useBean id="onlineDetailSvc" scope="page" class="com.online_detail.model.OnlineDetailService" />
	
	<li>
     <FORM METHOD="post" ACTION="onlineDetail.do" >
       <b>選擇餐點狀態:</b> 
       <input type="radio" name="meal_status" value="0">
       <label>未製作</label>
       <input type="radio" name="meal_status" value="1">
       <label>已製作</label>
        <input type="radio" name="meal_status" value="2">
       <label>已送達</label>
       <input type="hidden" name="action" value="getOne_For_Display2">
       <input type="submit" value="送出">
    </FORM>
  </li>
<!-- 	<li> -->
<!--      <FORM METHOD="post" ACTION="onlineDetail.do" > -->
<!--        <b>選擇餐點狀態:</b> -->
<!--        <select size="1" name="meal_status"> -->
<%--          <c:forEach var="onlineDetailVO" items="${onlineDetailSvc.all}" >  --%>
<%--           <option value="${onlineDetailVO.meal_status}">${onlineDetailVO.meal_status} --%>
<%--          </c:forEach>    --%>
<!--        </select> -->
<!--        <input type="radio" name="action" value="getOne_For_Display"> -->
<!--        <input type="submit" value="送出"> -->
<!--     </FORM> -->
<!--   </li> -->
</ul>


</body>
</html>