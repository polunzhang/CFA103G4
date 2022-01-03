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

body::-webkit-scrollbar {
	display: none;
}

        .showMealPic {
            width: 60px;
            height: 60px;
            object-fit: cover;
            flex: 1;
        }

        .meal_detail {
            border: 2px solid pink;
        }


        .uncooked-tr:hover {
            filter: sepia(0) blur(0px);
            box-shadow: 0 0 5px 5px red;
        }

        #container-CookedList {
            overflow-y:scroll;
            position: absolute;
            top: 3.5%;
            left: 12.5%;
            z-index: 1;
            width: 85%;
            height: 93%;
            padding: 15px;
        }
        
        @media (max-width: 959px){
#container-CookedList {
    left: 5%;
    width: 90%;
  }
}

        .live-order-table{
            border: 2px solid black;
            width:100%;
            text-align: center;
            vertical-align: middle;
            
            background-color: rgba(255, 255, 255, 0.5);
        }
        .live-order-table .meal_name{
            width:40%;
        }
.live-order-table .meal_note{
            width:10%;
        }
        
        .live-order-table .meal_note{
            width:10%;
        }
        .live-order-table td{
        	font-size: 20px;
            border: 2px solid gray;
            width:auto;
            height:60px;
        }

        .order-time-display-none{
            display:none;
        }
        
        .diff-time{
            display:none;
        }

        .nervous{
            background-color:#FFFF93;
        }

        .urgent{
            background-color:#FF7575;
        }
                .urgent td{
                color:white;
        }
                        .urgent .cooked-check{
                color:black;
        }
        .cooked-check{
           background-color:#0DFF0D;
       }

   </style>

   <title>UnCookedList.jsp</title>
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
        
        
        $(document).on("click", ".live-order-div", function(e){
            if($(this).find('tr.uncooked-tr')[0] == undefined){
                $(this).remove();
            }
            ;
        });


        function timeFormat(now) {
            let Minutes = Math.floor(now/1000/60);
            let Seconds = Math.floor(now/1000%60);
            return  Minutes + ': ' + Seconds;
        }

        let now = new Date();
        setInterval(function() {
            now = new Date();
            let order_time = document.querySelectorAll('.order-time');
            let time_hidden = document.querySelectorAll('.order-time-display-none');

            for(let i = 0; i<time_hidden.length;i++){
                let time1 = now.getTime();
                let time2 = time_hidden[i].textContent;
                let time3 = time1-time2;
                order_time[i].textContent = timeFormat(time3);
                if (time3 > 600000){
                    order_time[i].parentElement.classList.add("urgent");
                }else if (time3 > 300000){
                    order_time[i].parentElement.classList.add("nervous");
                }
            }

        }, 1000);


    </script>
    <script>
        var MyPoint = "/OrderStatusChange/Uncooked";
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
                if(jsonObj[i].meal_status === 0){
                    console.log(jsonObj[i].liveno);
                    let tableId = "#"+jsonObj[i].liveno;
                    let table = document.getElementById(tableId);
                    if (table===null){
                        let container_CookedList = document.getElementById("container-CookedList");
                        let div = document.createElement("div");
                        div.classList.add("live-order-div");
                        div.innerHTML=`
                        <table class="live-order-table" id="#`+jsonObj[i].liveno+`">
                        <tr>
                        <th>Order #`+jsonObj[i].liveno+`</th>
                        <th>Table #`+jsonObj[i].tableno+`</th></tr>
                        <tr>
                        <th>Time</th>
                        <th>Meal_Name</th>
                        <th>Qlt</th>
                        <th>Note</th>
                        <th>Status</th>
                        </tr>
                        <tr class="uncooked-tr">
                        <td class="order-time-display-none">`+jsonObj[i].CMS+`</td>
                        <td class="order-time"></td>
                        <td class="meal_name">`+jsonObj[i].meal_name+`</td>
                        <td >`+jsonObj[i].meal_amount+`</td>
                        <td class="meal_note">`+jsonObj[i].meal_note+`</td>
                        <td class="cooked-check">Check
                        <FORM>
                        <input type="hidden" name="action" value="change_meal_status">
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
                        let cell1=row.insertCell(0);
                        let cell2=row.insertCell(1);
                        let cell3=row.insertCell(2);
                        let cell4=row.insertCell(3);
                        let cell5=row.insertCell(4);
                        let cell6=row.insertCell(5);
                        cell1.innerHTML=`<td >`+jsonObj[i].CMS+`</td>`;
                        cell1.className="order-time-display-none";
                        cell2.innerHTML=`<td ></td>`;
                        cell2.className="order-time";
//                         cell3.innerHTML=`<td ><img class="showMealPic" src="`+jsonObj[i].meal_pic+`"></td>`;
                        cell3.innerHTML=`<td >`+jsonObj[i].meal_name+`</td>`;
                        cell3.className="meal_name";
                        cell4.innerHTML=`<td >`+jsonObj[i].meal_amount+`</td>`;
                        cell5.innerHTML=`<td >`+jsonObj[i].meal_note+`</td>`;
                        cell5.className="meal_note";
                        cell6.innerHTML=
                        `<td  bgcolor="#0DFF0D">Check
                        <FORM>
                        <input type="hidden" name="action" value="change_meal_status">
                        <input type="hidden" name="liveno" value="`+jsonObj[i].liveno+`">
                        <input type="hidden" name="mealno" value="`+jsonObj[i].mealno+`">
                        <input type="hidden" name="meal_set" value="`+jsonObj[i].meal_set+`">
                        </FORM>
                        </td>`;
                        cell6.className="cooked-check";
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
    }


</script>
<%@ include file="/back-end/sidebar2.jsp"%>
<%@ include file="/back-end/background.html"%>


<div class="style-3" id="container-CookedList">

    <c:forEach var="liveOrderVO" items="${liveOrderSvc.getAllUncooked()}">
        <div class="live-order-div">
            <table class="live-order-table" id="#${liveOrderVO.liveno}">
                <tr>
                    <th>Order #${liveOrderVO.liveno}</th>
                    <th>Table #${liveOrderVO.tableno}</th>
                </tr>
                <tr>
                    <th>Time</th>
                    <th>Meal_Name</th>
                    <th>Qlt</th>
                    <th>Note</th>
                    <th>Status</th>
                </tr>

                <c:forEach var="liveDetailVO" items="${liveDetailSvc.getAllUncooked(liveOrderVO.liveno) }">

                    <tr class="uncooked-tr">
                        <td class="order-time-display-none">${liveDetailSvc.getCMS(liveDetailVO.liveno)}</td>
                        <td class="order-time"></td>
                            <td class="meal_name">${mealSvc.getOneMeal(liveDetailVO.mealno).meal_name}</td>
                            <td >${liveDetailVO.meal_amount}</td>
                            <td class="meal_note">${liveDetailVO.meal_note}</td>
                            <td class="cooked-check">Check
                                <FORM>
                                    <input type="hidden" name="action" value="change_meal_status">
                                    <input type="hidden" name="liveno" value="${liveDetailVO.liveno}">
                                    <input type="hidden" name="mealno" value="${liveDetailVO.mealno}">
                                    <input type="hidden" name="meal_set" value="${liveDetailVO.meal_set}">
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