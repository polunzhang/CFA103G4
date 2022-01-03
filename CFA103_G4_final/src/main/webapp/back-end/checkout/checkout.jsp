<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.live_order.model.*"%>
<%@ page import="com.live_detail.model.*"%>
<%@ page import="com.online_order.model.*"%>
<%@ page import="com.online_detail.model.*"%>
<aside onload="connect();" onunload="disconnect();" >
    <%@ include file="/back-end/sidebar.jsp"%>
</aside>
<%@ include file="/back-end/background.html"%>

<jsp:useBean id="liveorderSvc" scope="page" class="com.live_order.model.LiveOrderService" />
<jsp:useBean id="livedetailSvc" scope="page" class="com.live_detail.model.LiveDetailService" />
<jsp:useBean id="onlineorderSvc" scope="page" class="com.online_order.model.OnlineOrderService" />
<jsp:useBean id="onlinedetailSvc" scope="page" class="com.online_detail.model.OnlineDetailService" />

<html>
<head>
<title>所有現場未結訂單</title>

<style>
main{

    position: absolute;
    top: 3.5%;
    height: calc(100vh - 100px);
    width: calc(100% - 200px);
    margin-left: 200px;
}
table#table-1 {
position: fixed;
background-color: #FAF0E6;
    color: black;
	border: 2px solid black;
	text-align: center;
}

table#table-1 h4 {
	color: red;
	display: block;
}
h3 {
margin: 10px auto;
color: black;
text-align: center;
}
h4 {
	color: blue;
	display: inline;
}

table {

	width: 1000px;
	margin-top: 5px;
	margin-bottom: 5px;
}

.order {

background-color: #FFF0F5;
	border: 1px solid black;
}

th, td {
	padding: 5px;
	border: solid;
	text-align: center;
}
  .style-3::-webkit-scrollbar-track
  {
   -webkit-box-shadow: inset 0 0 6px rgba(0,0,0,0.3);
   background-color: #F5F5F5;
  }

  .style-3::-webkit-scrollbar
  {
   width: 6px;
   background-color: #F5F5F5;
  }

  .style-3::-webkit-scrollbar-thumb
  {
   background-color: #8E8E8E;
  }

.live-order-table-div{
overflow-y: scroll;
display: inline-block;
height: 112%;
}
.test{
margin-top:90px;
}

body::-webkit-scrollbar {
    display: none;
}
</style>

</head>
<body>
<main>
    <div class="style-3 live-order-table-div">
	<table id="table-1">
		<tr>
			<td>
				<h3>結帳畫面</h3>
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

	<h3 class="test">現場訂單</h3>
	<table class="order">
		<tr>
			<th>現場訂單編號</th>
			<th>桌號</th>
			<th>結帳狀態</th>
			<th>訂單成立日期</th>
			<th>訂單總金額</th>
			<th>付款方式</th>
			<th>結帳</th>
		</tr>
		<c:forEach var="liveorderVO" items="${liveorderSvc.getAllUncheck()}">
			<tr>
				<%-- 		${liveorderSvc.getAll().size()} --%>
				<td>${liveorderVO.liveno}</td>
				<td>${liveorderVO.tableno}</td>
				<c:if test="${liveorderVO.pay_status==0}" >
				<td>未付款</td>
				</c:if>
				<td>${liveorderVO.create_time}</td>
				<td>${liveorderVO.total}</td>
			<FORM METHOD="post" ACTION="<%=request.getContextPath()%>/back-end/checkout/checkout.do" style="margin-bottom: 0px;">
				<td><input type="radio" name="pay_way" value="0" checked/>現金 <input type="radio" name="pay_way" value="1" />信用卡</td>	
				<td>
				    <input type="submit" value="付款去"> <input type="hidden" name="liveno" value="${liveorderVO.liveno}">
					<input type="hidden" name="action" value="liveOrder_Checkout">
				</td>
			</FORM>
			</tr>
		</c:forEach>
	</table>
<h3>線上訂單</h3>
	<table class="order">
		<tr>
			<th>線上訂單編號</th>
			<th>結帳狀態</th>
			<th>取餐時間</th>
			<th>訂單成立日期</th>
			<th>訂單總金額</th>
			<th>付款方式</th>
			<th>結帳</th>
		</tr>
		<c:forEach var="onlineorderVO" items="${onlineorderSvc.getAllUncheck()}">
			<tr>
<%-- 				${onlineorderSvc.getAll().size()} --%>
				<td>${onlineorderVO.olno}</td>
				<c:if test="${onlineorderVO.pay_status==0}" >
				<td>未付款</td>
				</c:if>
				<td>${onlineorderVO.set_time}</td>
				<td>${onlineorderVO.create_time}</td>
				<td>${onlineorderVO.total}</td>
				<c:if test="${onlineorderVO.pay_way==0}">
				<td>現金</td>
				</c:if>
				<FORM METHOD="post" ACTION="<%=request.getContextPath()%>/back-end/checkout/checkout.do" style="margin-bottom: 0px;">
				<td>
				    <input type="submit" value="付款去"> <input type="hidden" name="olno" value="${onlineorderVO.olno}">
					<input type="hidden" name="action" value="onlineOrder_Checkout">
				</td>
			</FORM>
			</tr>
		</c:forEach>
		
				<c:forEach var="onlineorderVO" items="${onlineorderSvc.getAllUncheck()}">
			<tr>
<%-- 				${onlineorderSvc.getAll().size()} --%>
				<td>${onlineorderVO.olno}</td>
				<c:if test="${onlineorderVO.pay_status==0}" >
				<td>未付款</td>
				</c:if>
				<td>${onlineorderVO.set_time}</td>
				<td>${onlineorderVO.create_time}</td>
				<td>${onlineorderVO.total}</td>
				<c:if test="${onlineorderVO.pay_way==0}">
				<td>現金</td>
				</c:if>
				<FORM METHOD="post" ACTION="<%=request.getContextPath()%>/back-end/checkout/checkout.do" style="margin-bottom: 0px;">
				<td>
				    <input type="submit" value="付款去"> <input type="hidden" name="olno" value="${onlineorderVO.olno}">
					<input type="hidden" name="action" value="onlineOrder_Checkout">
				</td>
			</FORM>
			</tr>
		</c:forEach>
				<c:forEach var="onlineorderVO" items="${onlineorderSvc.getAllUncheck()}">
			<tr>
<%-- 				${onlineorderSvc.getAll().size()} --%>
				<td>${onlineorderVO.olno}</td>
				<c:if test="${onlineorderVO.pay_status==0}" >
				<td>未付款</td>
				</c:if>
				<td>${onlineorderVO.set_time}</td>
				<td>${onlineorderVO.create_time}</td>
				<td>${onlineorderVO.total}</td>
				<c:if test="${onlineorderVO.pay_way==0}">
				<td>現金</td>
				</c:if>
				<FORM METHOD="post" ACTION="<%=request.getContextPath()%>/back-end/checkout/checkout.do" style="margin-bottom: 0px;">
				<td>
				    <input type="submit" value="付款去"> <input type="hidden" name="olno" value="${onlineorderVO.olno}">
					<input type="hidden" name="action" value="onlineOrder_Checkout">
				</td>
			</FORM>
			</tr>
		</c:forEach>
	</table>
	</div>
</main>
</body>
</html>