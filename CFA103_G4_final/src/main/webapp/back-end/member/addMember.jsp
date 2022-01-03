<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.member.model.*"%>
<!-- include側邊欄(sidebar) -->
<aside onload="connect();" onunload="disconnect();">
	<%@ include file="/back-end/sidebar.jsp"%>
</aside>
<!-- include背景圖片(毛玻璃拉長特化meal版) -->
<%@ include file="/front-end/shopping_system/background/ListAllBG.html"%>


<%
MemberVO memberVO = (MemberVO) request.getAttribute("memberVO");
%>

<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
<title>會員資料新增</title>

<style>
* {
	box-sizing: border-box;
	font-family: monospace;
	line-height: 150%;
}

img {
	max-width: 100%;
}

body {
	margin: 0;
	color: black;
}

table#table-1 {
	text-align: center;
	width: 55%;
	height: 100px;
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

table {
	width: 300px;
	/* 	background-color: white; */
	margin-top: 1px;
	margin-bottom: 1px;
}

table, th, td {
	border: 0px solid #CCCCFF;
	white-space: nowrap;
}

th, td {
	padding: 1px;
	line-height: 35px;
}

h2 {
	margin: 0 auto;
}

main {
	position: absolute;
	top: 3.5%;
	/* 	left: 12.5%; */
	height: calc(100vh - 100px);
	width: calc(100% - 200px);
	margin-left: 200px;
}
</style>

</head>
<center>
	<main>
		<body bgcolor='white'>
			<table id="table-1">
				<tr>
					<td>
						<h2>會員資料新增</h2>
					</td>
					<td>
						<h4>
							<a href="select_page.jsp"><img src="icon/light-bulb.png"
								width="30" height="30" border="0"> HomePage</a>
						</h4>
					</td>
				</tr>
			</table>

			<%-- 錯誤表列 --%>
			<c:if test="${not empty errorMsgs}">
				<font style="color: red">請修正以下錯誤:</font>
				<ul>
					<c:forEach var="message" items="${errorMsgs}">
						<li style="color: red">${message}</li>
					</c:forEach>
				</ul>
			</c:if>
			<br>
			<FORM METHOD="post" ACTION="MemberServlet.do" name="form1">
				<table>
					<tr>
						<td>姓名:</td>
						<td><input placeholder="請輸入姓名" name="mname" size="10"
							value="<%=(memberVO == null) ? "" : memberVO.getMname()%>" /></td>
					</tr>

					<tr id=mem_id>
						<td>身分證字號:</td>
						<td><input placeholder="請輸入身分證字號" name="mem_id"
							maxlength="10"
							value="<%=(memberVO == null) ? "" : memberVO.getMem_id()%>" /></td>
					</tr>

					<tr>
						<td>性別:</td>
						<td><lable> <input type="radio" name="gender"
								value="男" checked>男</lable> <lable> <input type="radio"
								name="gender" value="女">女</lable></td>
					</tr>

					<tr>
						<td>生日:</td>
						<td><input name="bday" id="birthday" type="Date"></td>
					</tr>
					<tr>
						<td>地址:</td>
						<td><input placeholder="請輸入地址" name="address" size="30"
							value="<%=(memberVO == null) ? "" : memberVO.getAddress()%>" /></td>
					</tr>
					<tr>
						<td>電話:</td>
						<td><input placeholder="請輸入電話" name="phone" maxlength="10"
							value="<%=(memberVO == null) ? "" : memberVO.getPhone()%>" /></td>
					</tr>

					<tr>
						<td>EMAIL:</td>
						<td><input placeholder="請輸入EMAIL" name="mem_email"
							maxlength="100"
							value="<%=(memberVO == null) ? "" : memberVO.getMem_email()%>" /></td>
					</tr>

					<tr>
						<td>帳號:</td>
						<td><input placeholder="請輸入帳號" name="maccount" size="20"
							value="<%=(memberVO == null) ? "" : memberVO.getMaccount()%>" /></td>
					</tr>

					<tr>
						<td>密碼:</td>
						<td><input type="PASSWORD" name="mpassword" size="15"
							value="<%=(memberVO == null) ? "" : memberVO.getMpassword()%>" /></td>
					</tr>

					<tr>
						<td>會員狀態:</td>
						<td><lable> <input type="radio" name="mem_state"
								value="1">已啟用</lable> <lable> <input type="radio"
								name="mem_state" value="0" checked>停權</lable></td>
					</tr>

					<tr>
						<td>驗證狀態:</td>
						<td><lable> <input type="radio" name="mem_verify"
								value="1">已驗證</lable> <lable> <input type="radio"
								name="mem_verify" value="0" checked>未驗證</lable></td>
					</tr>

				</table>
				<br> <input type="hidden" name="action" value="insert"><input
					type="submit" value="送出新增">
			</FORM>
		</main>
	</body>


	<!-- =========================================以下為 datetimepicker 之相關設定========================================== -->

	<%
	java.sql.Date hiredate = null;
	try {
		hiredate = memberVO.getBday();
	} catch (Exception e) {
		hiredate = new java.sql.Date(System.currentTimeMillis());
	}
	%>
	<link rel="stylesheet" type="text/css"
		href="<%=request.getContextPath()%>/datetimepicker/jquery.datetimepicker.css" />
	<script src="<%=request.getContextPath()%>/datetimepicker/jquery.js"></script>
	<script
		src="<%=request.getContextPath()%>/datetimepicker/jquery.datetimepicker.full.js"></script>

	<style>
.xdsoft_datetimepicker .xdsoft_datepicker {
	width: 300px; /* width:  300px; */
}

.xdsoft_datetimepicker .xdsoft_timepicker .xdsoft_time_box {
	height: 151px; /* height:  151px; */
}
</style>

	<script>
		//         $.datetimepicker.setLocale('zh');
		//         $('#f_date1').datetimepicker({
		// 	       theme: '',              //theme: 'dark',
		// 	       timepicker:false,       //timepicker:true,
		// 	       step: 1,                //step: 60 (這是timepicker的預設間隔60分鐘)
		// 	       format:'Y-m-d',         //format:'Y-m-d H:i:s',
	<%-- 		   value: '<%=hiredate%> --%>
		// 	', // value:   new Date(),
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