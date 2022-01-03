<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.emp.model.*"%>

<%
  EmpVO empVO = (EmpVO) request.getAttribute("empVO"); //EmpServlet.java(Concroller), 存入req的empVO物件
%>
<%-- --<%= empVO==null %>--${empVO.empno}-- --%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>新增員工</title>

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
		padding-top: 45px;		
	}
	main table{
	margin: auto;
/* 	table-layout: fixed; */
	}
	table, th, td {
/*     	border: 1px solid black; */
  	}
  	th, td {
  		font-size: 20px;  		
  		padding: 5px; 		
/*     	text-align: center; */
  	}
  	input{
		font-size: 20px;
	}
	.buttonHolder{ 
		text-align: center; 
		margin-top: -50px;
	}
	.send {
	background-image: linear-gradient(#69db7c, #37b24d);
	border: 0;
	border-radius: 4px;
	box-shadow: rgba(0, 0, 0, .3) 0 5px 15px;
	box-sizing: border-box;
	color: #fff;
	cursor: pointer;
	font-family: Montserrat, sans-serif;
	font-size: .4em;
	margin: 5px;
	padding: 10px 15px;
	text-align: center;
	user-select: none;
	-webkit-user-select: none;
	touch-action: manipulation;
}

</style>

</head>
<body>

<!-- 樣式套版 -->
<%@ include file="/back-end/sidebar.jsp"%>
<%@ include file="/back-end/background.html"%>
	

<!-- <header>新增員工</header> -->

<!-- <aside> -->
<!-- 	<ul class="nav_list"> -->
<!-- 		<li><a href='listAllEmp.jsp'>顯示全部員工</a></li> -->
<!-- 		<li><a href='select_page.jsp'>查詢單一員工</a></li> -->
<!-- 		<li><a href='addEmp.jsp'>員工管理</a></li> -->
<%-- 		<li><a href='<%= request.getContextPath() %>/back-end/emper/select_page_emper.jsp'>員工權限管理</a></li> --%>
<%-- 		<li><a href='<%= request.getContextPath() %>/back-end/emper/addEmper.jsp'>新增員工權限</a></li> --%>
<%-- 		<li><a href='<%= request.getContextPath() %>/back-end/emperDetail/select_page_emper_detail.jsp'>權限明細查詢</a></li> --%>
<%-- 		<li><a href='<%= request.getContextPath() %>/back-end/emperDetail/listAllEmperDetail.jsp'>所有權限明細</a></li> --%>
<%-- 		<li><a href='<%= request.getContextPath() %>/back-end/emperDetail/addEmperDetail.jsp'>新增權限明細</a></li> --%>
<!-- 	</ul> -->
<!-- </aside>	 -->
		
<main>

<%-- 錯誤表列 --%>
	<c:if test="${not empty errorMsgs}">
		<font style="color: red; font-size: 15px; text-align: center; display: block;">請修正以下錯誤:</font>
		<ul>
			<c:forEach var="message" items="${errorMsgs}">
				<li style="color: red; font-size: 15px; text-align: center;">${message}</li>
			</c:forEach>
		</ul>
	</c:if>

	<FORM METHOD="post" ACTION="emp.do" name="form1">
		<table>
			<tr>
				<td>職位:</td>
				<td><input type="TEXT" name="job" size="45"
					value="<%=(empVO == null) ? "員工" : empVO.getJob()%>" /></td>
			</tr>
			<tr>
				<td>薪水:</td>
				<td><input type="TEXT" name="sal" size="45"
					value="<%=(empVO == null) ? "22000" : empVO.getSal()%>" /></td>
			</tr>
			<tr>
				<td>員工姓名:</td>
				<td><input type="TEXT" name="ename" size="45"
					value="<%=(empVO == null) ? "輸入姓名" : empVO.getEname()%>" />
					</td>
			</tr>
			<tr>
				<td>雇用日期:</td>
				<td><input name="hiredate" id="f_date1" type="text"></td>
			</tr>
			<tr>
				<td>帳號:</td>
				<td><input type="TEXT" name="eaccount" size="45"
					value="<%=(empVO == null) ? "例如:A12345" : empVO.getEaccount()%>" />
					<span style="color: red">${eaccountError}</span>
					</td>
			</tr>
			<tr>
				<td>密碼:</td>
				<td><input type="TEXT" name="epassword" size="45"
					value="<%=(empVO == null) ? "例如:Ab123456" : empVO.getEpassword()%>" /></td>
			</tr>

			<tr>
				<td>在職狀態:</td>
				<td>
<!-- 					<input type="TEXT" name="job_status" size="45" -->
<%-- 					value="<%=(empVO == null) ? "1" : empVO.getJob_status()%>" /> --%>

						<%if(empVO == null || empVO.getJob_status()==1){ %>
 						<input type="radio" id="1" name="job_status" value="1" checked >
  						<label for="1">1 (在職)</label>
  						<input type="radio" id="0" name="job_status" value="0" >
 						<label for="0">0 (離職)</label>
						<%}else{ %>
						<input type="radio" id="1" name="job_status" value="1" >
  						<label for="1">1 (在職)</label>
  						<input type="radio" id="0" name="job_status" value="0" checked>
 						<label for="0">0 (離職)</label>
 						<%} %>

<!-- 					<select size="1" name="job_status">							 -->
<%-- 					<option value="<%=(empVO == null) ? "1" : empVO.getJob_status()%>" ${(empVO.job_status==1)? 'selected':'' } >1(在職) --%>
<%-- 					<option value="<%=(empVO == null) ? "0" : empVO.getJob_status()%>" ${(empVO.job_status==0)? 'selected':'' } >0(離職)							 --%>
<!-- 					</select> -->
				</td>
			</tr>

		</table>
		
		<br> 
		<div class="buttonHolder">
		<input type="hidden" name="action" value="insert"> 
		<input type="submit" value="送出新增" class="send">
		</div>
		
	</FORM>
</main>

</body>

<!-- =========================================以下為 datetimepicker 之相關設定========================================== -->

<% 
  java.sql.Date hiredate = null;
  try {
	    hiredate = empVO.getHiredate();
   } catch (Exception e) {
	    hiredate = new java.sql.Date(System.currentTimeMillis());
   }
%>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/datetimepicker/jquery.datetimepicker.css" />
<script src="<%=request.getContextPath()%>/datetimepicker/jquery.js"></script>
<script src="<%=request.getContextPath()%>/datetimepicker/jquery.datetimepicker.full.js"></script>

<script>
        $.datetimepicker.setLocale('zh');
        $('#f_date1').datetimepicker({
	       theme: '',              //theme: 'dark',
	       timepicker:false,       //timepicker:true,
	       step: 1,                //step: 60 (這是timepicker的預設間隔60分鐘)
	       format:'Y-m-d',         //format:'Y-m-d H:i:s',
		   value: '<%=hiredate%>', // value:   new Date(),
           //disabledDates:        ['2017/06/08','2017/06/09','2017/06/10'], // 去除特定不含
           //startDate:	            '2017/07/10',  // 起始日
           //minDate:               '-1970-01-01', // 去除今日(不含)之前
           //maxDate:               '+1970-01-01'  // 去除今日(不含)之後
        });
        
        
   
        // ----------------------------------------------------------以下用來排定無法選擇的日期-----------------------------------------------------------

        //      1.以下為某一天之前的日期無法選擇
        //      var somedate1 = new Date('2017-06-15');
        //      $('#f_date1').datetimepicker({
        //          beforeShowDay: function(date) {
        //        	  if (  date.getYear() <  somedate1.getYear() || 
        //		           (date.getYear() == somedate1.getYear() && date.getMonth() <  somedate1.getMonth()) || 
        //		           (date.getYear() == somedate1.getYear() && date.getMonth() == somedate1.getMonth() && date.getDate() < somedate1.getDate())
        //              ) {
        //                   return [false, ""]
        //              }
        //              return [true, ""];
        //      }});

        
        //      2.以下為某一天之後的日期無法選擇
        //      var somedate2 = new Date('2017-06-15');
        //      $('#f_date1').datetimepicker({
        //          beforeShowDay: function(date) {
        //        	  if (  date.getYear() >  somedate2.getYear() || 
        //		           (date.getYear() == somedate2.getYear() && date.getMonth() >  somedate2.getMonth()) || 
        //		           (date.getYear() == somedate2.getYear() && date.getMonth() == somedate2.getMonth() && date.getDate() > somedate2.getDate())
        //              ) {
        //                   return [false, ""]
        //              }
        //              return [true, ""];
        //      }});


        //      3.以下為兩個日期之外的日期無法選擇 (也可按需要換成其他日期)
        //      var somedate1 = new Date('2017-06-15');
        //      var somedate2 = new Date('2017-06-25');
        //      $('#f_date1').datetimepicker({
        //          beforeShowDay: function(date) {
        //        	  if (  date.getYear() <  somedate1.getYear() || 
        //		           (date.getYear() == somedate1.getYear() && date.getMonth() <  somedate1.getMonth()) || 
        //		           (date.getYear() == somedate1.getYear() && date.getMonth() == somedate1.getMonth() && date.getDate() < somedate1.getDate())
        //		             ||
        //		            date.getYear() >  somedate2.getYear() || 
        //		           (date.getYear() == somedate2.getYear() && date.getMonth() >  somedate2.getMonth()) || 
        //		           (date.getYear() == somedate2.getYear() && date.getMonth() == somedate2.getMonth() && date.getDate() > somedate2.getDate())
        //              ) {
        //                   return [false, ""]
        //              }
        //              return [true, ""];
        //      }});
        
</script>

</html>