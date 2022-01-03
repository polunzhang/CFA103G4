<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>

<html>
<head>
<meta charset="UTF-8">
<title>線上訂單查詢</title>

<style>
  table#table-1 {
	width: 450px;
	background-color: #CCCCFF;
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
    color: blue;
    display: inline;
  }
</style>

</head>
<body bgcolor='white'>

<table id="table-1">
   <tr><td><h3>Online Order: Home</h3><h4>( MVC )</h4></td></tr>
</table>

<p>This is the Home page for Online Order: Home</p>

<h3>線上訂單查詢:</h3>

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
  <li><a href='listAllOnlineOrder.jsp'>List</a>all Online Order.</li>
  
  <li>
    <FORM METHOD="post" ACTION="onlineOrder.do" >
        <b>輸入訂單編號(如1):</b>
        <input type="text" name="olno">
        <input type="hidden" name="action" value="getOne_For_Display">
        <input type="submit" value="送出">
    </FORM>
  </li>

<jsp:useBean id="onlineOrderSvc" scope="page" class="com.online_order.model.OnlineOrderService" />
   
  <li>
     <FORM METHOD="post" ACTION="onlineOrder.do" >
       <b>選擇訂單編號:</b>
       <select size="1" name="olno">
         <c:forEach var="onlineOrderVO" items="${onlineOrderSvc.all}" > 
          <option value="${onlineOrderVO.olno}">${onlineOrderVO.olno}
         </c:forEach>   
       </select>
       <input type="hidden" name="action" value="getOne_For_Display">
       <input type="submit" value="送出">
    </FORM>
  </li>
  
  <li>
     <FORM METHOD="post" ACTION="onlineOrder.do" >
       <b>選擇會員編號:</b>
       <select size="1" name="olno">
         <c:forEach var="onlineOrderVO" items="${onlineOrderSvc.all}" > 
          <option value="${onlineOrderVO.olno}">${onlineOrderVO.memno}
         </c:forEach>   
       </select>
       <input type="hidden" name="action" value="getOne_For_Display">
       <input type="submit" value="送出">
     </FORM>
  </li>
</ul>

<h3>訂單管理</h3>

<ul>
  <li><a href='addOnlineOrder.jsp'>Add</a> a new OnlineOrder.</li>
</ul>
</body>
</html>