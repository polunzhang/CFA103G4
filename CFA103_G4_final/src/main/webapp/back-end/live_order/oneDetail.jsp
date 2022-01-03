<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
	<%@ page import="java.util.*"%>
	<%@page import="com.live_detail.model.*"%>
	<%@page import="com.live_order.model.*"%>
	<jsp:useBean id="liveDetailSvc" scope="page"
	class="com.live_detail.model.LiveDetailService" />
	<jsp:useBean id="liveOrderSvc" scope="page"
	class="com.live_order.model.LiveOrderService" />
	<jsp:useBean id="mealSvc" scope="page"
	class="com.meal.model.MealService" />
	<jsp:useBean id="empSvc" scope="page"
	class="com.emp.model.EmpService" />

<%Integer liveno = Integer.valueOf(request.getParameter("liveno"));
request.setAttribute("liveno", liveno);
System.out.println("liveno" + liveno);
LiveOrderVO liveOrderVO = liveOrderSvc.getOneByLiveNO(liveno);
request.setAttribute("liveOrderVO", liveOrderVO);
%>
	<!DOCTYPE html>
	<html>
	<head>
		<script src="http://code.jquery.com/jquery-1.12.4.min.js"></script>
		<meta charset="UTF-8">

		<style>
		* {
			margin: 0;
		}

		body::-webkit-scrollbar {
			display: none;
		}

		table {
			width: 99%;
			margin: 5px;
			background-color: rgba(255, 255, 255, 0.7);
			top:0;
		}

		table, th, td {
			border-collapse:collapse;
			border-bottom: 2px solid black;
		}

		th, td {
			text-align: center;
		}

		thead th { 
			position: sticky;
			top: 0; 
			background-color: rgba(50, 50, 50, 1);
			color:white;
		}

		.container-CookedList {
			overflow-y: scroll;
			position: absolute;
			top: 10%;
			left: 12.5%;
			z-index: 1;
			width: 85%;
			height: 87%;
		}

		.style-3::-webkit-scrollbar-track {
			-webkit-box-shadow: inset 0 0 6px rgba(0, 0, 0, 0.3);
			background-color: #F5F5F5;
		}

		.style-3::-webkit-scrollbar {
			width: 6px;
			background-color: #F5F5F5;
		}

		.style-3::-webkit-scrollbar-thumb {
			background-color: #8E8E8E;
		}

		.liveOrderVO_tr:hover {
			filter: sepia(0) blur(0px);
			border: 2px solid black;
			box-shadow: 0 0 2px 2px gray;
			cursor: pointer;
		}

		.showDetails{

			background-color: lightgray;
			position: sticky;
			top:24px;
		}



		.showMealPic {
			width: 60px;
			height: 60px;
			object-fit: cover;
			flex: 1;
		}
		.showingDetails th{
			background-color: rgba(100, 100, 100, 1);
			color:white;
			position: sticky;
			top:50px;
		}
		.showingDetails td{
			border-right: 2px dashed gray;
			border-top: 2px dashed gray;
		}
		
		.input-div{
			position: absolute;
			top: 3.5%;
			left: 12.5%;
			z-index: 1;
			height:40px;
			color:white;
			font-size: 20px;
		}

		.input-div *{
			width: 120px;
			height: 30px;
			font-size: 20px;
			text-align:center;
		}
		
		.input-div form{
		width:auto;
		
		}

	</style>

	<title>LiveOrderHistory.jsp</title>
</head>
<body>
	<script type="text/javascript">
		$(document).ready(function() {
			$(".liveOrderVO_tr").click(function() {
				$(".showDetails").removeClass("showDetails");
				$(this).addClass("showDetails");
				$.ajax({
					type:"POST",
					url:"<%=request.getContextPath()%>/LiveOrderHistory",
					data : $(this).find(".liveOrderVO_input").serialize(),
					dataType : "json",
					success:function(result){
						$(".showingDetails").remove();
						$(result).each(function(key,value){
							
							$(".showDetails").after(showLiveDetails(
								value.mealno,
								value.meal_pic,value.meal_name,	value.meal_amount,
								value.meal_price,value.meal_note));
						});
						$(".showDetails").after(showLiveDetailsTitle());
					},error:function(err){
						alert("failure");
					}
				})
			});		
		});



function showLiveDetailsTitle(){
	let tr = document.createElement("tr");
	tr.classList.add("showLiveDetailsTitle");
	tr.classList.add("showingDetails");
	tr.innerHTML=`
	<th width='60'>餐點編號</th>
	<th width='60'>餐點圖片</th>
	<th width='200'>餐點名稱</th>
	<th width='50'>餐點數量</th>
	<th width='50'>餐點價格</th>
	<th width='50'>餐點註記</th>
	`
	return tr;
}


function showLiveDetails(mealno,meal_pic,meal_name,meal_amount,meal_price,meal_note){
	let tr = document.createElement("tr");
	tr.classList.add("showingDetails");
	tr.innerHTML=`
	<td width='60'>`+mealno+`</td>
	<td width='60'>
	<img src='`+meal_pic+`' class='showMealPic'>
	</td>
	<td width='200'>`+meal_name+`</td>
	<td width='50'>`+meal_amount+`</td>
	<td width='50'>`+meal_price+`</td>
	<td width='50'>`+meal_note+`</td>
	`
	return tr;
}

</script>

<%@ include file="/back-end/sidebar.jsp"%>
<%@ include file="/back-end/background.html"%>

	<div class="input-div">
		可透過條件搜尋:
		<form METHOD="post" ACTION="<%=request.getContextPath()%>/LiveOrderHistory">
			<input type="hidden" name="action" value="FindByCompositeQuery"	class="liveOrderVO_CompositeQuery">
			<input placeholder="訂單編號" type="number" name="liveno" value="" min="0" class="liveOrderVO_CompositeQuery">
			<select class="liveOrderVO_search" name="empno">

				<option value="">員工姓名</option>
				<c:forEach var="empVO" items="${empSvc.all}">		
					<option value="${empVO.empno}"}>${empVO.ename}</option>
				</c:forEach>
			</select>
			<input class="liveOrderVO_CompositeQuery" name="create_time" type="date" style="width:180px" placeholder="dd-mm-yyyy" >
			<input type="submit" value="Search">
		</form>
	</div>



<div class="container-CookedList style-3">
	<table class="liveOrderVO-table">
		<thead >
			<tr>
				<th>訂單編號</th>
				<th>員工編號</th>
				<th>成立時間</th>
				<th>餐點總價</th>
				<th>付款方式</th>
				<th>結帳狀態</th>
			</tr>
		</thead>
		<tbody>
		
		
				<tr class="liveOrderVO_tr">
					<input type="hidden" name="action" value="FindByPrimaryKey"	class="liveOrderVO_input">
					<input type="hidden" name="liveno" value="${liveOrderVO.liveno}" class="liveOrderVO_input">
					<td>${liveOrderVO.liveno}</td>
					<td>${liveOrderVO.empno}</td>
					<td>${liveOrderVO.create_time}</td>
					<td>${liveOrderVO.total}</td>
					<c:choose>
						<c:when test="${liveOrderVO.pay_status==0}">
							<td>未付款</td>
						</c:when>
						<c:otherwise>
							<td>已結帳</td>
						</c:otherwise>
					</c:choose>
					<c:choose>
						<c:when test="${liveOrderVO.pay_way==0}">
							<td>現金支付</td>
						</c:when>
						<c:otherwise>
							<td>信用卡付款</td>
						</c:otherwise>
					</c:choose>
				</tr>
		</tbody>
	</table>
</div>
</body>


</html>