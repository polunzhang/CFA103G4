<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">

<title> 訂位 </title>

<style>
  *{
		padding: 0;
		margin: 0;
		box-sizing: border-box;
	}
	html{
		height: 100px;
		font-size : 50px;
	}
	header{
		background-color: lightsalmon;
		height: 100px;
		font-size : 50px;
		font-weight:bold;
		line-height: 100px;
		text-align: center;

	}
	aside{
		height: calc(100% - 101px);
		width: 200px;
		background-color: bisque;
		position: fixed;
		left: 0;
		overflow-y: auto;
	}
	ul.nav_list{
		padding-top: 1px;
		list-style: none;

	}
	.nav_list li{
		background-color: white;
		width: 135px;
		height: 40px;		
		margin: 50px auto;
		/* padding: 10px 0px; */		
		border-radius: 15px;
		box-shadow: 5px 5px dimgray;	
	}
	.nav_list li:hover{
		transform: scale(1.1);
	}
	.nav_list li a{
		font-size: 18px;
		font-weight:bold;
		display: block;
		text-align: center; 
		line-height: 40px;
		text-decoration: none;
		color: black;		
	}

	main{
		position: absolute;
		top: 3.5%;
		left: 12.5%;
		height: calc(100vh - 100px);
		width: calc(100% - 200px);

	}
	main ul{		
		list-style: none;
		margin: 0 auto;
		padding: 50px 30px;
		flex-wrap: wrap;
		display: table;
	}
	main ul input, select{
		font-size: 20px;
	}
  b {
   font-size: 25px;
  }
</style>



</head>
<body>

<%@ include file="/back-end/sidebar.jsp"%>
<%@ include file="/back-end/background.html"%>

<main>
<%-- 錯誤表列 --%>
<c:if test="${not empty errorMsgs}">
	<font style="color:red; font-size: 15px; text-align: center; display: block;">請修正以下錯誤:</font>
	<ul style="padding: 0;">
		<c:forEach var="message" items="${errorMsgs}">
			<li style="color:red; font-size: 15px; text-align: center;">${message}</li>
		</c:forEach>
	</ul>
</c:if>

<ul>

  <li>
    <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/back-end/rez/rez.do" >
        <b class="input-no">輸入訂位編號 (如1):</b>
        <input type="text" name="rezno">
        <input type="hidden" name="action" value="getOne_For_Display">
        <input type="submit" value="送出">
    </FORM>
  </li>
  
  <jsp:useBean id="rezSvc" scope="page" class="com.rez.model.RezService" />
  
  <li>
     <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/back-end/rez/rez.do" >
       <b>選擇訂位編號:</b>
       <select size="1" name="rezno">
         <c:forEach var="rezVO" items="${rezSvc.all}" > 
          <option value="${rezVO.rezno}">${rezVO.rezno}
         </c:forEach>   
       </select>
       <input type="hidden" name="action" value="getOne_For_Display">
       <input type="submit" value="送出">
    </FORM>
  </li>
  
  <li>
     <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/back-end/rez/rez.do" >
       <b>選擇會員編號:</b>
       <select size="1" name="rezno">
         <c:forEach var="rezVO" items="${rezSvc.all}" > 
          <option value="${rezVO.rezno}">${(rezVO.memno==0)? '非會員':rezVO.memno }
         </c:forEach>   
       </select>
       <input type="hidden" name="action" value="getOne_For_Display">
       <input type="submit" value="送出">
     </FORM>
  </li>
</ul>

</main>
</body>
</html>