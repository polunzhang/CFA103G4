<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

	<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
	<%@ page import="java.util.*"%>
	<%@page import="com.live_detail.model.*"%>
	<jsp:useBean id="liveDetailSvc" scope="page" class="com.live_detail.model.LiveDetailService" />
	<jsp:useBean id="liveOrderSvc" scope="page" class="com.live_order.model.LiveOrderService" />
	<jsp:useBean id="mealSvc" scope="page"  class="com.meal.model.MealService" />

	<!DOCTYPE html>
	<html>
	<head>
		<script src="http://code.jquery.com/jquery-1.12.4.min.js"></script>
		<meta charset="UTF-8">

		<style>
		* {
			margin: 0;
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


		.showMealPic {
			width: 60px;
			height: 60px;
			object-fit: cover;
			flex: 1;
		}



		.uncooked-tr:hover {
			filter: sepia(0) blur(0px);
			box-shadow: 0 0 5px 5px red;
		}

		#container-ServedList {
			overflow: auto;
			position: absolute;
			top: 3.5%;
			left: 12.5%;
			z-index: 1;
			width: 85%;
			height: 93%;
			padding: 15px;
		}
		.live-order-table{
			width:100%;
			text-align: center;
			vertical-align: middle;
		}

		.live-order-table td{
			border-bottom: 2px solid gray;
			border-right: 2px solid gray;
			border-top: 2px solid gray;
		}
		
		.meal-status-display-none{
			display:none;
		}

		
		.add-meal{
			width:30px;
			height:auto;
		}
		.meal_note{
			width:10%;
		}
		.meal_name{
			width:40%;
		}
		.live-order-table-div{
			border:2px solid black;
			overflow-y:scroll;
			display:inline-block;
			background-color:rgba(255, 255, 255, 0.6);
			width:33%;
			height:50%;
		}
		.meal_status1 {
			
			background-color:rgba(216, 237, 100, 0.5);
		}
		@media (max-width: 959px){
			#container-ServedList {
				postion:fixed;
				left: 5%;
				width: 90%;
			}


			.live-order-table-div{
				width: 49.5%;
			}
		}
		.cooked-check{
			background-color:#0DFF0D;
		}
		.cooked-revise{
		background-color:#FF8040;
		}
	</style>

	<title>UnServedList.jsp</title>
</head>
<body onload="connect();" onunload="disconnect();">
	<script>
		$(document).on("click", ".cooked-check", function(e){

			$.ajax({
				type:"POST",
				url:"<%=request.getContextPath()%>/order/MealStatusChange.do",
				data:$(this).find("form").serialize(),
				success:function(result){
					webSocket.send(result);
				},
				error:function(result){
				},
			}),
			$(this).parent().remove();
		});
		
		$(document).on("click", ".cooked-revise", function(e){

			$.ajax({
				type:"POST",
				url:"<%=request.getContextPath()%>/order/MealStatusChange.do",
				data:$(this).find("form").serialize(),
				success:function(result){
					webSocket.send(result);
				},
				error:function(result){
				},
			}),
			$(this).parent().remove();
		});
		
		$(document).on("click", ".live-order-table", function(e){
			if($(this).find('tr.uncooked-tr')[0] == undefined){
				$(this).parent().remove();
			}
			;
		});
		

	</script>

	<!-- ==============     websocket推播    ============== -->
	<script>
		var MyPoint = "/OrderStatusChange/Userved";
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

        webSocket.onmessage = function(event) {
        	let jsonObj = JSON.parse(event.data);
        	console.log(jsonObj);

        	for (let i = 0 ; i < jsonObj.length ; i++){
        		console.log(jsonObj[i]);
        		if(jsonObj[i].meal_status === 0 || jsonObj[i].meal_status===1){
        			console.log(jsonObj[i].liveno);
        			let tableId = "#"+jsonObj[i].liveno;
        			let table = document.getElementById(tableId);
        			if (table===null){
        				let container_CookedList = document.getElementById("container-ServedList");
        				let div = document.createElement("div");
        				div.classList.add("live-order-table-div");
        				div.classList.add("style-3");
        				div.innerHTML=`
        				<table class="live-order-table" id="#`+jsonObj[i].liveno+`">
        				<tr>
        				<th>單號#`+jsonObj[i].liveno+`</th>
        				<th>桌號#`+jsonObj[i].tableno+`</th></tr>
        				<tr>
        				<th>Meal_Pic</th>
        				<th>Meal_Name</th>
        				<th>Qlt</th>
        				<th>Note</th>
        				<th class="cooked-plus">
        				<FORM METHOD="post" ACTION="<%=request.getContextPath()%>/fowardToOrderSystem">
        				<input type="hidden" name="action" value="add-meal">
        				<input type="hidden" name="liveno" value="`+jsonObj[i].liveno+`">
        				<input type="hidden" name="tableno" value="`+jsonObj[i].tableno+`">
        				<input class="add-meal" type="image" src="<%=request.getContextPath()%>/pic/icon/hamburger.svg">
        				</FORM>
        				</th>
        				</tr>
        				<tr class="uncooked-tr meal_status`+jsonObj[i].meal_status+` mealno`+jsonObj[i].mealno+`">
        				<td class="meal-status-display-none">`+jsonObj[i].meal_status+`</td>
        				<td ><img class="showMealPic" src="`+jsonObj[i].meal_pic+`"></td>
        				<td class="meal_name">`+jsonObj[i].meal_name+`</td>
        				<td >`+jsonObj[i].meal_amount+`</td>
        				<td class="meal_note">`+jsonObj[i].meal_note+`</td>
        				<td class="cooked-revise" bgcolor="#FF8040">取消
        				<FORM>
        				<input type="hidden" name="action" value="change_meal_status_reverse">
        				<input type="hidden" name="liveno" value="`+jsonObj[i].liveno+`">
        				<input type="hidden" name="mealno" value="`+jsonObj[i].mealno+`">
        				<input type="hidden" name="meal_set" value="`+jsonObj[i].meal_set+`">
        				</FORM>
        				</td>
        				</tr>
        				</table>
        				`;
        				container_CookedList.append(div);  

        			}else{
        				let row=table.insertRow(2);
        				row.classList.add("uncooked-tr");
        				let meal_statusClass = "meal_status"+jsonObj[i].meal_status;
        				row.classList.add(meal_statusClass);
        				let mealnoClass = "mealno"+jsonObj[i].mealno;
        				row.classList.add(mealnoClass);
        				let cell1=row.insertCell(0);
        				let cell2=row.insertCell(1);
        				let cell3=row.insertCell(2);
        				let cell4=row.insertCell(3);
        				let cell5=row.insertCell(4);
        				let cell6=row.insertCell(5);
        				cell1.innerHTML=`<td >`+jsonObj[i].meal_status+`</td>`;
        				cell1.className="meal-status-display-none";
        				cell2.innerHTML=`<td ><img class="showMealPic" src="`+jsonObj[i].meal_pic+`"></td>`;
        				cell3.innerHTML=`<td >`+jsonObj[i].meal_name+`</td>`;
        				cell3.className="meal_name";
        				cell4.innerHTML=`<td >`+jsonObj[i].meal_amount+`</td>`;
        				cell5.innerHTML=`<td >`+jsonObj[i].meal_note+`</td>`;
        				cell5.className="meal_note";
        				if (jsonObj[i].meal_status===1){
        					let mealno = ".mealno"+jsonObj[i].mealno;
        					cell6.innerHTML=
        					`
            				<td class="cooked-check" bgcolor="#FF8040">確認
    						<FORM>
    						<input type="hidden" name="action" value="change_meal_status">
            				<input type="hidden" name="liveno" value="`+jsonObj[i].liveno+`">
            				<input type="hidden" name="mealno" value="`+jsonObj[i].mealno+`">
            				</FORM>
            				</td>`;
            				cell6.className="cooked-check";
            				table.querySelectorAll(mealno)[1].remove();
        				}else if (jsonObj[i].meal_status===0){
        				cell6.innerHTML=
            				`
            				<td class="cooked-revise" bgcolor="#FF8040">取消
    						<FORM>
    						<input type="hidden" name="action" value="change_meal_status_reverse">
            				<input type="hidden" name="liveno" value="`+jsonObj[i].liveno+`">
            				<input type="hidden" name="mealno" value="`+jsonObj[i].mealno+`">
            				<input type="hidden" name="meal_set" value="`+jsonObj[i].meal_set+`">
            				<input type="hidden" name="meal_note" value="`+jsonObj[i].meal_note+`">
            				</FORM>
            				</td>`;
            				cell6.className="cooked-revise";
        				}
        					
        			}
        		}else if (jsonObj[i].meal_status <= 0){
        			location.reload();
        		}
        	}
        };

        webSocket.onclose = function(event) {

        	console.log("disconnect!");
        };
    }

    function disconnect() {
    	webSocket.close();
    	document.getElementById('sendMessage').disabled = true;
    	document.getElementById('connect').disabled = false;
    	document.getElementById('disconnect').disabled = true;
    }
   
    
</script>
<%@ include file="/back-end/sidebar2.jsp"%>
<%@ include file="/back-end/background.html"%>


<div id="container-ServedList" class="style-3">
	<c:forEach var="liveOrderVO" items="${liveOrderSvc.getAllUnserved()}">
		<div class="live-order-table-div style-3">
			<table class="live-order-table" id="#${liveOrderVO.liveno}">
				<tr>
					<th>單號#${liveOrderVO.liveno}</th>
					<th>桌號#${liveOrderVO.tableno}</th>
				</tr>
				<tr>
					<th>Meal_Pic</th>
					<th>Meal_Name</th>
					<th>Qlt</th>
					<th>Note</th>
					<th class="cooked-plus">
						<FORM METHOD="post" ACTION="<%=request.getContextPath()%>/fowardToOrderSystem">
							<input type="hidden" name="action" value="add-meal">
							<input type="hidden" name="liveno" value="${liveOrderVO.liveno}">
							<input type="hidden" name="tableno" value="${liveOrderVO.tableno}">
							<input class="add-meal" type="image" src="<%=request.getContextPath()%>/pic/icon/hamburger.svg">
						</FORM>
					</th>
				</tr>
				<c:forEach var="liveDetailVO" items="${liveDetailSvc.getAllUnserved(liveOrderVO.liveno) }">
					<tr class="uncooked-tr meal_status${liveDetailVO.meal_status} mealno${liveDetailVO.mealno}">
						<td class="meal-status-display-none">${liveDetailVO.meal_status}</td>
						<td >
							<img class="showMealPic" src="<%=request.getContextPath()%>/MealPicServlet?id=${liveDetailVO.mealno}">
						</td>
						<td class="meal_name">${mealSvc.getOneMeal(liveDetailVO.mealno).meal_name}</td>
						<td >${liveDetailVO.meal_amount}</td>
						<td class="meal_note">${liveDetailVO.meal_note}</td>
						<td class="cooked-check">確認
							<FORM>
								<input type="hidden" name="action" value="change_meal_status">
								<input type="hidden" name="liveno" value="${liveDetailVO.liveno}">
								<input type="hidden" name="mealno" value="${liveDetailVO.mealno}">
							</FORM>
						</td>
					</tr>
				</c:forEach>
				<c:forEach var="liveDetailVO" items="${liveDetailSvc.getAllUncooked(liveOrderVO.liveno) }">
					<tr class="uncooked-tr meal_status${liveDetailVO.meal_status} mealno${liveDetailVO.mealno}">
						<td class="meal-status-display-none">${liveDetailVO.meal_status}</td>
						<td >
							<img class="showMealPic" src="<%=request.getContextPath()%>/MealPicServlet?id=${liveDetailVO.mealno}">
						</td>
						<td class="meal_name">${mealSvc.getOneMeal(liveDetailVO.mealno).meal_name}</td>
						<td >${liveDetailVO.meal_amount}</td>
						<td class="meal_note">${liveDetailVO.meal_note}</td>
						<td class="cooked-revise" bgcolor="#FF8040">取消
							<FORM>
								<input type="hidden" name="action" value="change_meal_status_reverse">
								<input type="hidden" name="liveno" value="${liveDetailVO.liveno}">
								<input type="hidden" name="mealno" value="${liveDetailVO.mealno}">
							</FORM>
						</td>
					</tr>
				</c:forEach>
			</table>
		</div>
	</c:forEach>
</div>
</body>
</html>