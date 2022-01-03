<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>

<jsp:useBean id="MealPicDAO" scope="page" class="com.meal_pic.model.MealPicDAO" />
<jsp:useBean id="MealPicSvc" scope="page" class="com.meal_pic.model.MealPicService" />
<jsp:useBean id="mealSvc" scope="page" class="com.meal.model.MealService" />
<jsp:useBean id="MealTypeSvc" scope="page" class="com.meal_type.model.MealTypeService" />
<jsp:useBean id="TableSvc" scope="page"	class="com.table.model.TableService" />
<jsp:useBean id="PromDetailService" scope="page" class="com.prom_detail.model.PromDetailService" />



<!DOCTYPE html>
<html>
<head>
	<script src="http://code.jquery.com/jquery-1.12.4.min.js"></script>
	<link href="<%=request.getContextPath()%>/js/bootstrap.min.css" rel="stylesheet" integrity="sha384-F3w7mX95PdgyTmZZMECAngseQB83DfGTowi0iMjiWaeVhAn4FJkqJByhZMI3AhiU" crossorigin="anonymous">
	
	<meta charset="UTF-8">

	<title>OrderSystem.jsp</title>
	<style>
	@import
	url("https://fonts.googleapis.com/css?family=Rajdhani:300&display=swap")
	;
	* {
		margin: 0;
		box-sizing: border-box;
	}

	.preview {
		position: absolute;
		top: 3.5%;
		left: 12.5%;
		z-index: 1;
		width: 85%;
		height: 94%;
		/* 	border: 2px solid red; */
	}

	.container-menu {
		display: inline-block;
		text-align: center;
		position: relative;
	}

	.mealButton {
		width: 200px;
		height: 200px;
		object-fit: cover;
		flex: 1;
		border: 2px solid transparent;
		border-radius: 25px;
	}

	.showMealPic {
		width: 60px;
		height: 60px;
		object-fit: cover;
		flex: 1;
	}

	.mealButton:hover {
		filter: sepia(0) blur(0px);
		border: 2px solid black;
		box-shadow: 0 0 2px 2px gray;
		cursor: pointer;
	}

	.meal_name {
		position: absolute;
		left: 5px;
		top: 170px;
		color: #fff;
		line-height: 20px;
		font-size: 16px;
		background-color: rgba(0, 0, 0, 0.3);
		overflow: visible;
	}

	.meal_price {
		position: absolute;
		left: 5px;
		top: 150px;
		line-height: 20px;
		color: #fff;
		font-size: 20px;
		background-color: rgba(0, 0, 0, 0.3);
	}

	.button_delete {
		width: 20px;
		height: 20px;
	}

	.button_delete:hover {
		cursor: pointer;
	}

	#cart {
		overflow: hidden;
		background: url("<%=request.getContextPath()%>/pic/cart-background.png") no-repeat center;
		background-size: 100% 100%;
		position: fixed;
		display: none;
		bottom: 3%;
		right: 0;
		z-index: 5;
		vertical-align: middle;
		text-align: center;
		width: 680px;
		height: 95%;
	}

	#cart .btn-group{
		position: absolute;
		top:5%;
		right: 10%;
	}
	
	#cart-table-div{
		overflow-y:scroll;
		position: absolute;
		right:5%;
		top:14.5%;
		max-height:705px;
	}
	#cart-table {
	}

	#cart-table td {
		border-radius: 5%;
		text-align: center;
		vertical-align: middle;
		margin: 0 auto;
		font-weight:bolder;
		border-bottom: 2px solid black;
		margin-bottom:2px;
		background-color: rgba(255,255 ,255,0.4);
	}

	#button_check {
		width: 50px;
		display: inline-block;
		position: absolute;
		bottom: 10px;
	}

	#button_check:hover {
		cursor: pointer;
	}

	#cart-button {
		position: fixed;
		bottom: 30px;
		right: 40px;
		border-radius: 50px;
		width: 70px;
		height: 70px;
		line-height: 70px;
		vertical-align: middle;
		text-align: center;
		z-index: 1;
		background:rgba(255, 255, 255, 0.3);
	}

	#cart-button:hover {
		cursor: pointer;
	}

	#cart-button.hide {
		display: none;
	}

	#cart-button img {
		margin: auto;
		vertical-align: middle;
	}

	#showListCount {
		z-index: 1;
		position: fixed;
		bottom: 85px;
		right: 30px;
		color: white;
		background: red;
		font-size: 20px;
		font-weight: bolder;
		border-radius: 50%;
		text-align: center;
		width: 30px;
		hieght: 30px;
	}
	
	#show_total{
		display: inline-block;
		font-size:35px;
		color:red;
	}
	
	#show_total_div{
		display: inline-block;
		position: absolute;
		bottom: 5px;
		right:10%;
	}
	#total_text{
		display: inline-block;
	}
	
	.style-3::-webkit-scrollbar-track{
		-webkit-box-shadow: inset 0 0 6px rgba(0,0,0,0.3);
		background-color: #F5F5F5;
	}

	.style-3::-webkit-scrollbar	{
		width: 6px;
		background-color: #F5F5F5;
	}

	.style-3::-webkit-scrollbar-thumb{
		background-color: #8E8E8E;
	}
	
	.prom{
		font-size:20px;
		color: red;
		font-weight:bold;
		top: 130px;
	}
	
	.meal_price_delete{
		text-decoration:line-through;
	}
	
	
</style>
<style>
#cart-button img.active {
	animation: antzone 0.5s 1 both;
}
@keyframes antzone {
	0% {transform: none;}
	15% {transform: translate3d(-25%,0,0) rotate3d(0,0,1,-5deg);}
	30% {transform: translate3d(20%,0,0) rotate3d(0,0,1,3deg);}     
	45% {transform: translate3d(-15%,0,0) rotate3d(0,0,1,-3deg);}
	60% {transform: translate3d(10%,0,0) rotate3d(0,0,1,2deg);}
	75% {transform: translate3d(-5%,0,0) rotate3d(0,0,1,-1deg);}
	100% {transform: none;}
}

@media ( max-width : 959px) {
	.preview{
		position: absolute;
		top: 2%;
		left: 4%;
		z-index: 1;
		width: 95%;
		height: 94%;
	}
	.preview .btn-group{
		position:fixed;
		z-index:3;
	}
	
}

@media ( max-width : 1537px) {
	#cart {
	width:660px;
	
	}
}


}
</style>

</head>

<!-- =============== 購物車按鈕相關 =============== -->
<script>
	$(document).ready(function() {

		let listCount = 0;
		$(".mealButton").click(function() {
			$.ajax({
				type:"POST",
				url:"<%=request.getContextPath()%>/order/liveOrder2.do",
				data:$(this).parent().serialize(),
				dataType: "json",
				success:function(result){
					clearCart();
					$(result).each(function(key,value){
						$("#cart-table").append(addCartList(
							value.meal_pic,value.meal_name,	value.meal_amount,
							value.meal_price,value.meal_set,value.meal_note,key,value.trash));
						listCount = ++key;
						$("#show_total").text(value.total);
					});
					$("#showListCount").text(listCount);
				},
				error:function(err){
					alert("failure");
				}
			})
			
			$("#cart-button").children("img").addClass("active");
			setTimeout(removeActive,200);
			
			
		});
	});

	$(document).on("click", ".button_delete", function(event){

		$.ajax({
			type:"POST",
			url:"<%=request.getContextPath()%>/order/liveOrder2.do",
			data:$(this).parent().serialize(),
			dataType: "json",
			success:function(result){
				clearCart();
				listCount=0;
				$("#show_total").text(0);
				$(result).each(function(key,value){
					$("#cart-table").append(addCartList(
						value.meal_pic,value.meal_name,	value.meal_amount,
						value.meal_price,value.meal_set,value.meal_note,key,value.trash));
					listCount = ++key
					$("#show_total").text(value.total);
				});
				$("#showListCount").text(listCount);
			},
			error:function(err){
				alert("failure");
			}
		})



	});

	$(document).on("click", "#button_check", function(event){
		$.ajax({
			type:"POST",
			url:"<%=request.getContextPath()%>/order/liveOrder2.do",
			data:$(this).parent().serialize(),
			success:function(result){
				clearCart();
				$("#showListCount").text(0);
				$("#show_total").text(0);
				webSocket.send(result);
				$(".tableno-select").prop("selectedIndex",0);
				$("#show_total").text(0);
				redirect();
			},error:function(err){
				alert("failure");
			}
		})
	});

	function removeActive(){
		$("#cart-button").children("img").removeClass("active");
	}


	function clearCart(){
		$("#cart-table").empty();
	}

	function addCartList(meal_pic,meal_name,meal_amount,meal_price,meal_set,meal_note,key,trash){
		let tr = document.createElement("tr");
		tr.innerHTML=`
		<td width='110'>
		<img src='`+meal_pic+`' class='showMealPic'>
		</td>
		<td width='200'>`+meal_name+`</td>
		<td width='50'>`+meal_amount+`</td>
		<td width='50'>`+meal_price+`</td>
		
		<td width='50'>
		
		<form name='updateForm'>
		<input type='text' name='meal_note' size="4" class="type-meal-note" value="`+meal_note+`">
		<input type='hidden' name='action' value='UPDATE'>
		<input type='hidden' name='upd' value='`+key+`'>
		</form>
		</td>
		
		<td width='50'>
		<form name='deleteForm'>
		<input type='hidden' name='action' value='DELETE'>
		<input type='hidden' name='del' value='`+key+`'>
		<img class='button_delete' src='`+trash+`'>
		</form>
		</td>
		`
		return tr;
	}

	function redirect(){
		let url = "${url}";
		if (!(url === "")){
			window.location.replace(url);
		}
	}
</script>

<!-- =============== websocket =============== -->
<script>
	var MyPoint = "/OrderStatusChange/OrderSystem";
	var host = window.location.host;
	var path = window.location.pathname;
	var webCtx = path.substring(0, path.indexOf('/', 1));
	var endPointURL = "ws://" + window.location.host + webCtx + MyPoint;

	var statusOutput = document.getElementById("statusOutput");
	var webSocket;

	function connect() {
		// create a websocket
		webSocket = new WebSocket(endPointURL);

		webSocket.onopen = function(event) {
			console.log("connect!");
		};

		webSocket.onclose = function(event) {

			console.log("disconnect!");
		};
	}

	function disconnect() {
		webSocket.close();
	}


</script>

<script>
	$(document).on("click", ".btn-group .btn-meal-type", function(event){
		if($(this).val() === "0"){
			$(".container-menu").hide();
			$(".container-menu").show();
		}else{
			let str = ".meal_type"+ $(this).val();
			$(".container-menu").hide();
			$(str).parent().show();
		}
	});

	$(document).on("click", ".btn-group .btn-meal-note", function(event){
		$(".selected-meal-note").val($(this).val());
		$.ajax({
			type:"POST",
			url:"<%=request.getContextPath()%>/order/liveOrder2.do",
			data:$(".selected-meal-note").parent().serialize(),
			dataType: "json",
			success:function(result){
				clearCart();
				listCount=0;
				$(result).each(function(key,value){
					
					$("#cart-table").append(addCartList(
						value.meal_pic,value.meal_name,	value.meal_amount,
						value.meal_price,value.meal_set,value.meal_note,key,value.trash));
					listCount = ++key
				});
				$("#showListCount").text(listCount);
			},
			
			error:function(err){
			}
		})
	});

	$(document).on("click", ".type-meal-note", function(event){
		$(".type-meal-note").removeClass("selected-meal-note");
		$(this).addClass("selected-meal-note");
	});

	$(document).on("change", ".tableno-select", function(event){
		$.ajax({
			type:"POST",
			url:"<%=request.getContextPath()%>/order/MealStatusChange.do",
			data:$(this).parent().serialize(),
			dataType: "json",
			success:function(result){
			},
			
			error:function(err){
			}
		});
	});
	
	$(document).on("change", ".type-meal-note", function(event){
		$.ajax({
			type:"POST",
			url:"<%=request.getContextPath()%>/order/MealStatusChange.do",
			data:$(this).parent().serialize(),
			dataType: "json",
			success:function(result){
			},
			
			error:function(err){
			}
		});
	});


</script>




<body onload="connect();" onunload="disconnect();">
	<%@ include file="/back-end/sidebar2.jsp"%>
	<%@ include file="/back-end/background.html"%>


	<div class="preview">
		<div class="btn-group" role="group" aria-label="...">
			<form action="<%=request.getContextPath()%>/order/MealStatusChange.do" method="POST">
				<input type="hidden" name="action" value="table-change">
				<select class="tableno-select" name="tableno" style="height:40px;">
					<option value="">外帶</option>
					<c:forEach var="TableVO" items="${TableSvc.all}">
						<option value="${TableVO.tableno}" ${(TableVO.tableno==tableno)?'selected':''}>${TableVO.tableno} 桌</option>
					</c:forEach>
				</select>
			</form>
			
			<button type="button" class="btn btn-secondary btn-meal-type" value="0">全部</button>
			<c:forEach var="MealTypeVO" items="${MealTypeSvc.all}">
				<button type="button" class="btn btn-secondary btn-meal-type" value="${MealTypeVO.meal_type_no}">${MealTypeVO.meal_type_name}</button>
			</c:forEach>
		</div>
		<br>
		<br>
		<c:forEach var="mealVO" items="${mealSvc.all}">
			<c:choose>
				<c:when test="${empty PromDetailService.checkMPByMn(mealVO.mealno)}">
					<div class=container-menu>
						<div class="meal_type${mealVO.meal_type_no}" style="display:none;">${mealVO.meal_type_no}</div>
						<div class="meal_price">NT.${mealVO.meal_price}</div>
						<div class="meal_name">${mealVO.meal_name}</div>
						<form action="<%=request.getContextPath()%>/order/liveOrder2.do" method="POST">
							<input type="hidden" name="action" value="ADD">
							<input type="hidden" name="mealno" value="${mealVO.mealno}">
							<input type="hidden" name="meal_amount" value="1">
							<input type="hidden" name="meal_price" value="${mealVO.meal_price}">
							<input type="hidden" name="meal_price" value="${mealVO.meal_price}">
							<input type="hidden" name="meal_set" value="0"> 
							<input type="hidden" name="meal_status" value="0">
							<image class="mealButton" src="<%=request.getContextPath()%>/MealPicServlet?id=${mealVO.mealno}">
							</form>
						</div>
					</c:when>
					<c:otherwise>
						<div class=container-menu>
							<div class="meal_type${mealVO.meal_type_no}" style="display:none;">${mealVO.meal_type_no}</div>
							<div class="meal_price prom">   NT.${PromDetailService.checkMPByMn(mealVO.mealno)}</div>
							<div class="meal_price meal_price_delete">NT.<s>${mealVO.meal_price}</s></div>
							<div class="meal_name">${mealVO.meal_name}</div>
							<form action="<%=request.getContextPath()%>/order/liveOrder2.do" method="POST">
								<input type="hidden" name="action" value="ADD">
								<input type="hidden" name="mealno" value="${mealVO.mealno}">
								<input type="hidden" name="meal_amount" value="1">
								<input type="hidden" name="meal_price" value="${PromDetailService.checkMPByMn(mealVO.mealno)}">							
								<input type="hidden" name="meal_price" value="${mealVO.meal_price}">
								<input type="hidden" name="meal_set" value="0"> 
								<input type="hidden" name="meal_status" value="0">
								<image class="mealButton" src="<%=request.getContextPath()%>/MealPicServlet?id=${mealVO.mealno}">
								</form>
							</div>
						</c:otherwise>
					</c:choose>	
				</c:forEach>
			</div>


		<div id="cart">
			<br>
			<div class="btn-group" role="group" aria-label="...">
				<button type="button" class="btn btn-secondary btn-meal-note" value="">無</button>
				<button type="button" class="btn btn-secondary btn-meal-note" value="小辣">小辣</button>
				<button type="button" class="btn btn-secondary btn-meal-note" value="小辣">大辣</button>
				<button type="button" class="btn btn-secondary btn-meal-note" value="不要蒜">不要蒜</button>
				<button type="button" class="btn btn-secondary btn-meal-note" value="不要蔥">不要蔥</button>
				<button type="button" class="btn btn-secondary btn-meal-note" value="蛋奶素">蛋奶素</button>
				<button type="button" class="btn btn-secondary btn-meal-note" value="飯少">飯少</button>
				<button type="button" class="btn btn-secondary btn-meal-note" value="加飯">加飯</button>
			</div>
			<br>
			<div id="cart-table-div" class="style-3">
				<table id="cart-table"></table>
			</div>

			
			<div>
				<form name="checkoutForm">
					<input type="hidden" name="action" value="CHECKOUT">
					<img id="button_check" width=60px src="<%=request.getContextPath()%>/pic/icon/file-check.svg">
				</form>
			</div>
			<div id="show_total_div">
				<p id="total_text">小記 : <span id="show_total"></span></p>
			</div>
			
		</div>
		<div id="cart-button">
			<img width="50px" src="<%=request.getContextPath()%>/pic/icon/hamburger.svg">
		</div>
		<div id="showListCount"></div>
		
		<!-- =============== 購物車按鈕相關 =============== -->
		<script type="text/javascript">
			$(document).on("click", "#cart-button",function(){
				$("#cart").show();
				$(this).hide();
			});
			$(document).on("click", "#button_check", function(){
				$("#cart").hide();
			});
			$("#cart").hover(function(){
				$("#cart-button").hide();
				$(this).show();
			},function(){
				$("#cart-button").show();
				$(this).hide();
			});
		</script>

	</body>
	</html>
