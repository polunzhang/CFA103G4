<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.table.model.*"%>
<%@ page import="com.live_order.model.*"%>
<aside onload="connect();" onunload="disconnect();" >
    <%@ include file="/back-end/sidebar.jsp"%>
</aside>
<%@ include file="/back-end/background.html"%>

<jsp:useBean id="tableSvc" scope="page" class="com.table.model.TableService" />
<jsp:useBean id="liveorderSvc" scope="page" class="com.live_order.model.LiveOrderService" />
<jsp:useBean id="rezdetailSvc" scope="page" class="com.rez_detail.model.RezDetailService" />

<html>
<head>
<title>BIB Tables</title>

<style>
h3 {
	color: black;
}

th, td {
	padding: 5px;
	text-align: center;
}

.draggable {
    background-color: #FFE4E1;
    border: solid black;
	width: 150px;
	height: 180px;
	padding: 0.5em;
	left: 100px;
	display: inline-block;
}
#container {
background-color: #F5F5F5;
position:relative;
    border: solid black;
	width: 880px;
	height: 475px;
}
#save {
position: absolute;
left: 50%;
bottom: -13% ;
transform:translateX(-50%);
}
#main{
position: absolute;
top: 3.5%;
left: 12.5%;
height: calc(100vh - 100px);
width: calc(100% - 200px);
margin-left: 200px;
}
.button {
    display: inline-block;
}
body::-webkit-scrollbar {
    display: none;
}
</style>
<script src="https://code.jquery.com/jquery-3.6.0.js"></script>
<script src="https://code.jquery.com/ui/1.13.0/jquery-ui.js"></script>
<script>
	$(function() {
		$(".draggable").draggable({ containment: "#container", scroll: false });
		$('#mySubmit').on('click', function(e){
			e.preventDefault();
			let data = [];
			for(let i = 0; i < $(".draggable").length; i++) {
				let obj = {};
				obj.tableno = $($(".draggable")[i]).attr('data-tableno');
				obj.left = parseInt($($(".draggable")[i]).css('left').split('px'));
				obj.top = parseInt($($(".draggable")[i]).css('top').split('px'));
				data.push(obj);
			}
			console.log(data);
			$('#data').val(JSON.stringify(data));
			$('#myForm').submit(); // submit
		});
	});
</script>
</head>
<body>
<div id="main">
				<h3>BIB Tables</h3>
	<%-- 錯誤表列 --%>
	<c:if test="${not empty errorMsgs}">
		<font style="color: red">請修正以下錯誤:</font>
		<ul>
			<c:forEach var="message" items="${errorMsgs}">
				<li style="color: red">${message}</li>
			</c:forEach>
		</ul>
	</c:if>
<div id="container">
	<c:forEach var="tableVO" items="${tableSvc.getAll()}">
	<div class="draggable" data-tableno="${tableVO.tableno}" style="left: ${tableVO.table_left}px; top: ${tableVO.table_top}px">
			桌號：${tableVO.tableno}
			<br>
			座位數：${tableVO.table_nop}位
			<br>
			<c:if test="${tableVO.table_status==0 }">
			可入座
			</c:if>
			<c:if test="${tableVO.table_status==1 }">
			預定保留位
			</c:if>
			<c:if test="${tableVO.table_status==2 }">
			已入座
			</c:if>
			<br>
			點餐明細：<a href="<%=request.getContextPath()%>/back-end/live_order/oneDetail.jsp?liveno=${liveorderSvc.getOneByTableNO(tableVO.tableno).liveno}">${liveorderSvc.getOneByTableNO(tableVO.tableno).liveno}</a>
			<br>
			訂位明細：<a href="<%=request.getContextPath()%>/back-end/table/oneRezDetail.jsp?rezno=${rezdetailSvc.getOneRezno(tableVO.tableno).rezno}">${rezdetailSvc.getOneRezno(tableVO.tableno).rezno}</a>
<div class="button">
            <c:if test="${tableVO.table_status==2 }">
			    <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/back-end/table/table.do" style="margin-bottom: 0px;">
					<input type="submit" value="點餐去">
					<input type="hidden" name="tableno" value="${tableVO.tableno}">
					<input type="hidden" name="action" value="go_to_order">
				</FORM>
			</c:if>
			<c:if test="${tableVO.table_status!=2 }">
			    <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/back-end/table/table.do" style="margin-bottom: 0px;">
					<input type="submit" value="點餐去" disabled>
					<input type="hidden" name="tableno" value="${tableVO.tableno}">
					<input type="hidden" name="action" value="go_to_order">
				</FORM>
			</c:if>
</div>
<div class="button">
			<c:if test="${liveorderSvc.getOneByTableNO(tableVO.tableno).pay_status==0 }">
				<FORM METHOD="post" ACTION="<%=request.getContextPath()%>/back-end/checkout/tableCheckout.jsp" style="margin-bottom: 0px;">
					<input type="submit" value="結帳去">
					<input type="hidden" name="liveno" value="${liveorderSvc.getOneByTableNO(tableVO.tableno).liveno}">
					<input type="hidden" name="tableno" value="${tableVO.tableno}">
				</FORM>
			</c:if>
			<c:if test="${liveorderSvc.getOneByTableNO(tableVO.tableno).pay_status!=0 }">
				<FORM METHOD="post" ACTION="<%=request.getContextPath()%>/back-end/checkout/tableCheckout.jsp" style="margin-bottom: 0px;">
					<input type="submit" value="結帳去" disabled>
				</FORM>
			</c:if>
</div>
<div class="button">
            <c:if test="${tableVO.table_status!=2 }">
			    <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/back-end/table/table.do" style="margin-bottom: 0px;">
					<input type="submit" value="安排入座">
					<input type="hidden" name="tableno" value="${tableVO.tableno}">
					<input type="hidden" name="action" value="take_A_Seat">
				</FORM>
			</c:if>
            <c:if test="${tableVO.table_status==2 }">
			    <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/back-end/checkout/tableCheckout.jsp" style="margin-bottom: 0px;">
					<input type="submit" value="安排入座" disabled>
				</FORM>
			</c:if>
</div>
	</div>
	</c:forEach>
	
	<div id="save">

<FORM id="myForm" METHOD="post" ACTION="<%=request.getContextPath()%>/back-end/table/table.do">
	<input type="hidden" id="data" name="data" value="">
	<input type="hidden"  name="action" value="save">
	<button id="mySubmit">儲存</button>
</FORM>
</div>
	
</div>

</div>
</body>
</html>