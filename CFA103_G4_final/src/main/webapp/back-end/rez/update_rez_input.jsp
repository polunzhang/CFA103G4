<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.rez.model.*"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<%
  RezVO rezVO = (RezVO) request.getAttribute("rezVO"); 
%>

<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
<title>訂位資料修改</title>

<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/meyer-reset/2.0/reset.min.css">
<link rel="stylesheet" href="./ass_style.css">

<style>
body {
	margin: 0;
	padding: 0;
	background: #272133;
	}
  table#table-1 {
	background-color: #FFC14F;
    border: 4px solid black;
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
  h2 {
  position: absolute;
  transform: translate;
  top: 8%;
  left: 50%;
  color: black;
  }
  small {
  position: absolute;
  transform: translate;
  top: 15%;
  left: 50%;
  color: black;
  }
</style>

<style>
/*會員*/
	.memno {
	position: absolute;
	transform: translate;
	top: 18%;
	left: 50%;
	color: black;
	font-family: Comic Sans MS;
	}
	.memno1 {
	position: absolute;
	transform: translate;
	top: 21%;
	left: 43%;
	font-family: Comic Sans MS;
	}
/*電話*/	
	.phone {
	position: absolute;
	transform: translate;
	top: 25%;
	left: 49%;
	color: black;
	font-family: Comic Sans MS;
	}
	.phone1 {
	position: absolute;
	transform: translate;
	top: 28%;
	left: 43%;
	font-family: Comic Sans MS;
	}
/*日期*/
	.redate {
	position: absolute;
	transform: translate;
	top: 32%;
	left: 46%;
	color: black;
	font-family: Comic Sans MS;
	}
	.redate1 {
	position: absolute;
	transform: translate;
	top: 35.5%;
	left: 47%;
	}
/*時間*/
	.retime {
	position: absolute;
	transform: translate;
	top: 39.5%;
	left: 46%;
	color: black;
	font-family: Comic Sans MS;
	}
	.retime1 {
	position: absolute;
	transform: translate;
	top: 43%;
	left: 15%;
	font-family: Comic Sans MS;
	}
/*人數*/
	.ppl {
	position: absolute;
	transform: translate;
	top: 48%;
	left: 46%;
	color: black;
	font-family: Comic Sans MS;
	}
	.ppl1 {
	position: absolute;
	transform: translate;
	top: 51.5%;
	left: 43%;
	font-family: Comic Sans MS;
	}
/*email*/
	.email {
	position: absolute;
	transform: translate;
	top: 55%;
	left: 51%;
	color: black;
	font-family: Comic Sans MS;
	}
	.email1 {
	position: absolute;
	transform: translate;
	top: 58%;
	left: 47%;
	font-family: Comic Sans MS;
	}
/*姓氏*/
	.lastname {
	position: absolute;
	transform: translate;
	top: 61.5%;
	left: 49%;
	color: black;
	font-family: Comic Sans MS;
	}
	.lastname1 {
	position: absolute;
	transform: translate;
	top: 64.5%;
	left: 43%;
	font-family: Comic Sans MS;
	}
/*性別*/
	.sex {
	position: absolute;
	transform: translate;
	top: 68%;
	left: 49%;
	color: black;
	font-family: Comic Sans MS;
	}
	.fly {
	position: absolute;
	transform: translate;
	top: 80%;
	left: 50%;
	}
</style>

<style>
  table {
	width: 1000px;
	background-color: white;
	margin-top: 1px;
	margin-bottom: 1px;
  }
  table, th, td {
    border: 0px solid #CCCCFF;
  }
  th, td {
    padding: 5px;
  }

</style>

<style>
	input[type="radio"]{
		display: none;
	}
	label{
		position: relative;
		color: black;
		font-family: 'Poppins',sans-serif;
		font-size: 16px;
		border: 1px solid black;
		border-radius: 2px;
		padding: 5px 25px;
		display: inline-flex;
		align-items: center;
		cursor: pointer;
	}
	label:before{
		content: "";
		height: 15px;
		width: 15px;
		border: 1px solid black;
		border-radius: 50%;
		margin-right: 10px;
	}
	input[type="radio"]:checked + label{
	background-color: black;
	color: white;
	}
	input[type="radio"]:checked + label:before{
	height: 8px;
	width: 8px;
	border: 5px solid white;
	background-color: black;
	}
</style>

<style>
	.wrapper {
	display: inline-flex;
  	height: 100px;
  	width: 400px;
  	align-items: center; 
  	position: absolute;
	transform: translate;
	top: 67%;
	left: 46%;
	font-family: Comic Sans MS;
	}

</style>

</head>
<body>
<%@ include file="/back-end/sidebar.jsp"%>
<%@ include file="/back-end/background.html"%>

<table id="table-1">
	<tr><td>
		 <h3>訂位資料修改</h3>
		 <h4><a href="select_page.jsp">回首頁</a></h4>
	</td></tr>
</table>

<small><font color=red><b>*</b></font>為必填資料</small>
<%-- 錯誤表列 --%>
<c:if test="${not empty errorMsgs}">
	<font style="color:red">請修正以下錯誤:</font>
	<ul>
		<c:forEach var="message" items="${errorMsgs}">
			<li style="color:red">${message}</li>
		</c:forEach>
	</ul>
</c:if>

<FORM METHOD="post" ACTION="rez.do" name="form1">
<table>
	<tr>
		<td>訂位編號:<font color=red><b>*</b></font></td>
		<td><%=rezVO.getRezno()%></td>
	</tr>
	<tr>
		<td class="memno">會員編號:</td>
		<td class="memno1"><input type="TEXT" placeholder="非會員可不填" name="memno" size="45"	value="<%=(rezVO.getMemno()==0)?"":rezVO.getMemno()%>" /></td>
	</tr>
	<tr>
		<td class="phone"><font color=red><b>*</b></font>Phone 連絡電話:</td>
		<td class="phone1"><input type="TEXT" autofocus required placeholder="連絡電話不可空白" name="phone" size="40"
			 value="<%= (rezVO==null)? "" : rezVO.getPhone()%>" /></td>
	</tr>
	<tr>
		<td class="redate"><font color=red><b>*</b></font>Reservation date 訂位日期:</td>
		<td class="redate1"><input name="reservationdate" id="date" autofocus required placeholder="請選擇訂位時間"></td>
	</tr>
	<tr>
		<td class="retime"><font color=red><b>*</b></font>Reservation time 訂位時間:</td>
		<td class="retime1">
			<input type="radio" name="reservationtime" id="11-30" value="11:30:00" autofocus required placeholder="請勾選訂位時間" checked="checked">
			<label for="11-30">11:30</label>
			<input type="radio" name="reservationtime" id="12-00" value="12:00:00" autofocus required placeholder="請勾選訂位時間" checked="checked">
			<label for="12-00">12:00</label>
			<input type="radio" name="reservationtime" id="12-30" value="12:30:00" autofocus required placeholder="請勾選訂位時間" checked="checked">
			<label for="12-30">12:30</label>
			<input type="radio" name="reservationtime" id="13-00" value="13:00:00" autofocus required placeholder="請勾選訂位時間" checked="checked">
			<label for="13-00">13:00</label>
			<input type="radio" name="reservationtime" id="13-30" value="13:30:00" autofocus required placeholder="請勾選訂位時間" checked="checked">
			<label for="13-30">13:30</label>
			
			<input type="radio" name="reservationtime" id="17-00" value="17:00:00" autofocus required placeholder="請勾選訂位時間" checked="checked">
			<label for="17-00">17:00</label>
			<input type="radio" name="reservationtime" id="17-30" value="17:30:00" autofocus required placeholder="請勾選訂位時間" checked="checked">
			<label for="17-30">17:30</label>
			<input type="radio" name="reservationtime" id="18-00" value="18:00:00" autofocus required placeholder="請勾選訂位時間" checked="checked">
			<label for="18-00">18:00</label>
			<input type="radio" name="reservationtime" id="18-30" value="18:30:00" autofocus required placeholder="請勾選訂位時間" checked="checked">
			<label for="18-30">18:30</label>
			<input type="radio" name="reservationtime" id="19-00" value="19:00:00" autofocus required placeholder="請勾選訂位時間" checked="checked">
			<label for="19-00">19:00</label>
		</td>
	</tr>
	<tr>
		<td class="ppl"><font color=red><b>*</b></font>Number of reservations 訂位人數:</td>
		<td class="ppl1"><input type="TEXT" autofocus required placeholder="訂位人數不得為0" name="num_of_ppl" size="40"
			  value="<%= (rezVO==null)? "" : rezVO.getPhone()%>" /></td>
	</tr>
	<tr>
		<td class="email">email:</td>
		<td class="email1"><input placeholder="可填可不填" name="email"
					maxlength="150"
					value="<%=(rezVO == null) ? "" : rezVO.getEmail()%>" /></td>
	</tr>
	<tr>
		<td class="lastname"><font color=red><b>*</b></font>Name姓名:</td>
		<td class="lastname1"><input type="TEXT" autofocus required placeholder="訂位人姓名不可留空" name="lastname" size="40"
			  value="<%= (rezVO==null)? "" : rezVO.getLastname()%>" /></td>
	</tr>
	<tr>
		<td class="sex"><font color=red><b>*</b></font>Gender 性別:</td>
		<td class="wrapper">
			<input type="radio" name="sex" value="男" autofocus required placeholder="請勾選性別" checked="checked" id="男"><label for="男">男</label>
			<input type="radio" name="sex" value="女" autofocus required placeholder="請勾選性別" checked="checked" id="女"><label for="女">女</label></td>  
	</tr>

<jsp:useBean id="rezSvc" scope="page" class="com.rez.model.RezService" />

</table>
<div class="fly">
<input type="hidden" name="action" value="update">
<input type="hidden" name="rezno" value="<%=rezVO.getRezno()%>">
<input type="submit" value="送出修改">
</div>
</FORM>
</body>



<!-- =========================================以下為 datetimepicker 之相關設定========================================== -->

<% 
java.sql.Date reservationdate = null;
  try {
	  reservationdate = rezVO.getReservationdate();
   } catch (Exception e) {
	   reservationdate = new java.sql.Date(System.currentTimeMillis());
   }
%>

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
        $('#date').datetimepicker({
           theme: '',              //theme: 'dark',
 	       timepicker:false,       //timepicker:true,
 	       step: 1,                //step: 60 (這是timepicker的預設間隔60分鐘)
 	       format:'Y-m-d',         //format:'Y-m-d H:i:s',
 		   value: '<%=rezVO.getReservationdate()%>', // value:   new Date(),
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