<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.* , com.online_detail.model.*"%>
<%@ page import="com.online_order.model.*" %>
<%@ page import="com.member.model.*" %>
<%-- <%@ include file="/front-end/shopping_system/payByCard.jsp" %> --%>
<jsp:useBean id="mealSvc" scope="page" class="com.meal.model.MealJDBCDAO" />



<%
	OnlineOrderVO onlineOrderVO = (OnlineOrderVO) request.getAttribute("onlineOrderVO");//EmpServlet.java (Concroller) 存入req的empVO物件 (包括幫忙取出的empVO, 也包括輸入資料錯誤時的empVO物件)
%>

<%
MemberVO memberVO = (MemberVO) session.getAttribute("memberVO");
%>


<html>
<head>
<meta charset="UTF-8">
<title>購物車清單 - Cart.jsp</title>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/ShoppingCart.css">

<jsp:useBean id="MealDAO" scope="page" class="com.meal.model.MealJDBCDAO" />
<style>

.showMealPic{
width:100px;
height:100px;
}
.preview {
/* 	position: absolute; */
	left: 200px;
	z-index: 1;
	width: 61.5%;
	height: 100%;
	padding: 15px;
	margin-bottom:100%;
/* 	border: 2px solid red; */
}


table, th, td {
	border: 1px solid black;
	border-radius:10px;
	width:750px;
	margin-left: 20%;
}

/* table#table-1 h3{ */
/* 	display: block; */
/* 	text-align:left; */
/* } */
/* table#table-1 h4{ */
/* 	display: block; */
/* 	text-align:left; */
/* } */

th, td, tr {
	padding: 3px;
	border-radius:10px;
}

tr, td, div.listallmealpic img{
	width:80px;
	heigth:80px;
	border: 1px solid #b5b4ae; 	
	text-align:center;
}
a{ 
	text-decoration:none;
} 
a:hover{ 
	text-decoration:underline;
}
#number {
  width: 3em;
}
#map{
width:200px;
height:200px;
float:right;
}
#second{
width:900px;
height:auto;
margin:40px auto;
}

#third{
width:500px;
float:left;
}

#main{
height:auto;

}


.button{
 display: inline-block;
  border:0;
  background-color:#003C9D;
  color:#fff;
  border-radius:10px;
  cursor:pointer;
  width: 100px;
  height: 30px;
  margin-top:30px;
  margin-left: 450px;
  font-size: 18px;
  text-align: center;
  padding: 3px;
  }

.button:hover{
  color:#003C9D;
  background-color:#fff;
  border:2px #003C9D solid;
}

</style>
</head>
<body>


   
   

<!-- include側邊欄(sidebar) -->
<!-- <aside onload="connect();" onunload="disconnect();" > -->
<%-- 		<%@ include file="/back-end/sidebar.html"%> --%>
<!-- </aside> -->
<!-- include背景圖片(毛玻璃拉長特化meal版) -->
<main id="main">
<%@ include file="/front-end/front-end_background.jsp"%>

	<div id="cart" class="preview">
		<font size="+3">目前購物車的內容如下:</font>
		<br>
		<div id="second">
		<div id="third">
		<table id="cart-table" style="background-color: #ffe6ea;">
			<tr>
				<th style="width:500px;">餐點圖片</th>
				<th style="width:500px;">餐點名稱</th>
				<th style="width:200px;">數量</th>
				<th style="width:500px;">價格</th>
				<th style="width:300px;">刪除</th>
			</tr>
			
			<c:forEach var="order" items="${shoppingcart}" varStatus="varStatusName">
				<tr>
					<td width="10">
						<img class="showMealPic" src="<%=request.getContextPath()%>/MealPicServlet?id=${order.mealno}"></td>
						<td width="200">${mealSvc.findByPrimaryKey(order.mealno).meal_name}</td>
						<td width="50" >${order.meal_amount}</td>
						<td width="50" >${order.meal_price}</td>
						<td width="50" >
							<form name="deleteForm" action="<%=request.getContextPath()%>/front-end/shopping_system/shoppingSystem.do" method="POST">
								<input type="hidden" name="action" value="DELETE"> 
								<input type="hidden" name="del" value="${varStatusName.index}"> 
								<input type="image" class="button_delete" src="<%=request.getContextPath()%>/pic/icon/trash.svg" width="30" height="30" >
							</form>
						</td>
					</tr>
				</c:forEach>
				<tr>
			<td colspan="6" style="text-align: right;"><font size="+2">總金額：${sum}
			</font></td>
		</tr>
			</table>
<%-- 錯誤表列 --%>
<c:if test="${not empty errorMsgs}">
	<font style="color:red; margin-left: 100px;">請修正以下錯誤:</font>
	<ul>
		<c:forEach var="message" items="${errorMsgs}">
			<li style="color:red; margin-left: 100px;">${message}</li>
		</c:forEach>
	</ul>
</c:if>



<Form id="form1" name="form1" action="<%=request.getContextPath()%>/front-end/shopping_system/shoppingSystem.do" method="POST" >
	<table>
		<tr>
			<td style="color: #003C9D; font-weight:bold;">會員編號:</td>
			<td style="color: #003C9D; font-weight:bold;">${memberVO.memno}</td>
		</tr>
		<tr>
			<td>地址:</td>
			<td><input type="TEXT" id="keyin" name="address" size="45" placeholder="未輸入代表來店外帶"
			 value="<%= (onlineOrderVO==null)? "" : onlineOrderVO.getAddress()%>" /></td>
		</tr>
		<tr>
			<td>取餐/送達時間:</td>
			<td><input name="set_time" id="f_date1" type="text"></td>
		</tr>
		
		<tr>
			<td>付款方式:</td>
				<td><select size="1" name="pay_way" >
						<option value="0">貨到付款</option>
						<option value="1">信用卡付款</option>
					 </select>
				</td>
		</tr>
	</table>
		<div style="text-align:center;">
 			<input type="hidden" name="action" value="CHECKOUT">
			<input type="submit" value="送出" class="button" ></td>
		</div>
</form>
			</div>
			
		</div>	
	</div>
	
		

</main>
<%@ include file="/front-end/front-end_footer.jsp"%>
</body>



<!-- =========================================以下為 datetimepicker 之相關設定========================================== -->

<% 
  java.sql.Timestamp set_time = null;
  try {
	  set_time = onlineOrderVO.getSet_time();
   } catch (Exception e) {
	   set_time = new java.sql.Timestamp(System.currentTimeMillis());
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
        $('#f_date1').datetimepicker({
	       theme: '',              //theme: 'dark',
	       timepicker:true,       //timepicker:true,
	       step: 10,                //step: 60 (這是timepicker的預設間隔60分鐘)
	       format:'Y-m-d H:i:s',         //format:'Y-m-d H:i:s',
		   value: '<%=set_time%>', // value:   new Date(),
           //disabledDates:        ['2017/06/08','2017/06/09','2017/06/10'], // 去除特定不含
           //startDate:	            '2017/07/10',  // 起始日
           minDate:               '-1970-01-01', // 去除今日(不含)之前
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