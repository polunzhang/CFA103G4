<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.member.model.*"%>
<!-- include������(sidebar) -->
<aside onload="connect();" onunload="disconnect();">
	<%@ include file="/back-end/sidebar.jsp"%>
</aside>
<!-- include�I���Ϥ�(������Ԫ��S��meal��) -->
<%@ include file="/front-end/shopping_system/background/ListAllBG.html"%>

<%
MemberVO memberVO = (MemberVO) request.getAttribute("memberVO");
%>

<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
<title>�|����ƭק�</title>

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
</style>

<style>
table {
	width: 450px;
	margin-top: 1px;
	margin-bottom: 1px;
}

table, th, td {
	white-space: nowrap;
}

th, td {
	padding: 1px;
	line-height: 35px;
}

h2 {
	margin: 0 auto;
	color: black;
}

table {
	width: 450px;
	margin-top: 1px;
	margin-bottom: 1px;
}

table, th, td {
	border: 0px solid #CCCCFF;
}

th, td {
	padding: 1px;
	font-weight: bold;
	color: black;
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
<body>
	<main>
		<center>
			<table id="table-1">
				<tr>
					<td>
						<h2>�|����ƭק�</h2>
						<h4>
							<a href="select_page.jsp"><img src="icon/light-bulb.png"
								width="30" height="30" border="0"> HomePage</a>
						</h4>
					</td>
				</tr>
			</table>

			<%-- ���~���C --%>
			<c:if test="${not empty errorMsgs}">
				<font style="color: red">�Эץ��H�U���~:</font>
				<ul>
					<c:forEach var="message" items="${errorMsgs}">
						<li style="color: red">${message}</li>
					</c:forEach>
				</ul>
			</c:if>

			<FORM METHOD="post" ACTION="MemberServlet.do" name="form1">
				<table>

					<tr>
						<td>�|���m�W:</td>
						<td><input type="TEXT" name="mname" size="45"
							value="<%=memberVO.getMname()%>" /></td>
					</tr>

					<tr>
						<td>�����Ҧr��:</td>
						<td><input type="TEXT" name="mem_id" maxlength="10"
							value="<%=(memberVO == null) ? "" : memberVO.getMem_id()%>" /></td>
					</tr>

					<tr>
						<td>�ʧO:</td>
						<td>
							<%
							if (memberVO.getGender().equals("�k")) {
							%> <lable> <input type="radio" name="gender" value="�k"
								checked>�k</lable> <lable> <input type="radio"
								name="gender" value="�k">�k</lable>
						</td>
						<%
						} else {
						%>
						<lable> <input type="radio" name="gender" value="�k">�k</lable>
						<lable> <input type="radio" name="gender" value="�k"
							checked>�k</lable>
						</td>
						<%
						}
						%>
					</tr>
					<tr>
						<td>�ͤ�:</td>
						<td><input name="bday" id="birthday" type="Date"
							value="<%=(memberVO == null) ? "" : memberVO.getBday()%>"></td>
					</tr>
					<tr>
						<td>�a�}:</td>
						<td><input placeholder="�п�J�a�}" name="address" size="30"
							value="<%=(memberVO == null) ? "" : memberVO.getAddress()%>" /></td>
					</tr>
					<tr>
						<td>�q��:</td>
						<td><input placeholder="�п�J�q��" name="phone" maxlength="10"
							value="<%=(memberVO == null) ? "" : memberVO.getPhone()%>" /></td>
					</tr>

					<tr>
						<td>EMAIL:</td>
						<td><input placeholder="�п�JEMAIL" name="mem_email"
							maxlength="45"
							value="<%=(memberVO == null) ? "" : memberVO.getMem_email()%>" /></td>
					</tr>

					<tr>
						<td>�b��:</td>
						<td><input placeholder="�п�J�b��" name="maccount" size="15"
							value="<%=(memberVO == null) ? "" : memberVO.getMaccount()%>" /></td>
					</tr>

					<tr>
						<td>�K�X:</td>
						<td><input type="PASSWORD" name="mpassword" size="15"
							value="<%=(memberVO == null) ? "" : memberVO.getMpassword()%>" /></td>
					</tr>

					<tr>
						<td>�|�����A:</td>
						<td>
							<%
							if (memberVO.getMem_state().equals(1)) {
							%> <lable> <input type="radio" name="mem_state" value="1"
								checked>�w�ҥ�</lable> <lable> <input type="radio"
								name="mem_state" value="0">���v</lable> <%
 } else {
 %> <lable> <input type="radio" name="mem_state" value="1">�w�ҥ�</lable>
							<lable> <input type="radio" name="mem_state" value="0"
								checked>���v</lable> <%
 }
 %>
						</td>
					</tr>

					<tr>
						<td>���Ҫ��A:</td>
						<td>
							<%
							if (memberVO.getMem_verify().equals(1)) {
							%> <lable> <input type="radio" name="mem_verify"
								value="1" checked>�w����</lable> <lable> <input
								type="radio" name="mem_verify" value="0">������</lable> <%
 } else {
 %> <lable> <input type="radio" name="mem_verify" value="1">�w����</lable>
							<lable> <input type="radio" name="mem_verify" value="0"
								checked>������</lable> <%
 }
 %>
						</td>
					</tr>



				</table>
				<br> <input type="hidden" name="action" value="update">
				<input type="hidden" name="memno" value="<%=memberVO.getMemno()%>">
				<input type="submit" value="�e�X�ק�">
			</FORM>
	</main>
</body>



<!-- =========================================�H�U�� datetimepicker �������]�w========================================== -->

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
        $.datetimepicker.setLocale('zh');
        $('#f_date1').datetimepicker({
           theme: '',              //theme: 'dark',
 	       timepicker:false,       //timepicker:true,
 	       step: 1,                //step: 60 (�o�Otimepicker���w�]���j60����)
 	       format:'Y-m-d',         //format:'Y-m-d H:i:s',
 		   value: '<%=memberVO.getBday()%>
		', // value:   new Date(),
		//disabledDates:        ['2017/06/08','2017/06/09','2017/06/10'], // �h���S�w���t
		//startDate:	            '2017/07/10',  // �_�l��
		//minDate:               '-1970-01-01', // �h������(���t)���e
		//maxDate:               '+1970-01-01'  // �h������(���t)����
		});

		// ----------------------------------------------------------�H�U�ΨӱƩw�L�k��ܪ����-----------------------------------------------------------

		//      1.�H�U���Y�@�Ѥ��e������L�k���
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

		//      2.�H�U���Y�@�Ѥ��᪺����L�k���
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

		//      3.�H�U����Ӥ�����~������L�k��� (�]�i���ݭn������L���)
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