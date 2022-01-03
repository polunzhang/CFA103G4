<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.*"%>
<%@ page import="com.emperDetail.model.*"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html lang="zh-Hant">
<head>
<meta charset="UTF-8">
<script src="http://code.jquery.com/jquery-1.12.4.min.js"></script>

<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Document</title>
<style>
@import
	url('https://netdna.bootstrapcdn.com/font-awesome/4.0.3/css/font-awesome.min.css')
	;

@font-face {
	font-family: 'Open Sans';
	font-style: normal;
	font-weight: 300;
	font-stretch: normal;
	src:
		url(https://fonts.gstatic.com/s/opensans/v27/memSYaGs126MiZpBA-UvWbX2vVnXBbObj2OVZyOOSr4dVJWUgsiH0B4gaVc.ttf)
		format('truetype');
}

@font-face {
	font-family: 'Open Sans';
	font-style: normal;
	font-weight: 400;
	font-stretch: normal;
	src:
		url(https://fonts.gstatic.com/s/opensans/v27/memSYaGs126MiZpBA-UvWbX2vVnXBbObj2OVZyOOSr4dVJWUgsjZ0B4gaVc.ttf)
		format('truetype');
}

@font-face {
	font-family: 'Open Sans';
	font-style: normal;
	font-weight: 700;
	font-stretch: normal;
	src:
		url(https://fonts.gstatic.com/s/opensans/v27/memSYaGs126MiZpBA-UvWbX2vVnXBbObj2OVZyOOSr4dVJWUgsg-1x4gaVc.ttf)
		format('truetype');
}

body::-webkit-scrollbar {
	display: none;
}

body {
	color: #5D5F63;
	background: #fff;
	font-family: 'Open Sans', sans-serif;
	padding: 0;
	margin: 0;
	text-rendering: optimizeLegibility;
	-webkit-font-smoothing: antialiased;
}

.sidebar {
	width: 11.5%;
	height: 100%;
	background: #293949;
	position: fixed;
	top: 0;
	left: 0;
	-webkit-transition: all 0.3s ease-in-out;
	-moz-transition: all 0.3s ease-in-out;
	-o-transition: all 0.3s ease-in-out;
	-ms-transition: all 0.3s ease-in-out;
	transition: all 0.3s ease-in-out;
	z-index: 100;
}

.sidebar * {
	overflow-wrap: normal;
}

@media ( max-width : 959px) {
	.sidebar {
		display: none;
	}
}

.sidebar #leftside-navigation ul, .sidebar #leftside-navigation ul ul {
	margin: -2px 0 0;
	padding: 0;
}

.sidebar #leftside-navigation ul li {
	list-style-type: none;
	border-bottom: 1px solid rgba(255, 255, 255, 0.05);
}

.sidebar #leftside-navigation ul li.active>a {
	color: #1abc9c;
}

.sidebar #leftside-navigation ul li.active ul {
	display: block;
}

.sidebar #leftside-navigation ul li a {
	color: #aeb2b7;
	text-decoration: none;
	display: block;
	padding: 18px 0 18px 10px;
	font-size: 14px;
	outline: 0;
	-webkit-transition: all 200ms ease-in;
	-moz-transition: all 200ms ease-in;
	-o-transition: all 200ms ease-in;
	-ms-transition: all 200ms ease-in;
	transition: all 200ms ease-in;
}

.sidebar #leftside-navigation ul li a:hover {
	color: #1abc9c;
}

.sidebar #leftside-navigation ul li a span {
	display: inline-block;
}

.sidebar #leftside-navigation ul li a i {
	width: 20px;
}

.sidebar #leftside-navigation ul li a i .fa-angle-left, .sidebar #leftside-navigation ul li a i .fa-angle-right
	{
	padding-top: 3px;
}

.sidebar #leftside-navigation ul ul {
	display: none;
}

.sidebar #leftside-navigation ul ul li {
	background: #23313f;
	margin-bottom: 0;
	margin-left: 0;
	margin-right: 0;
	border-bottom: none;
}

.sidebar #leftside-navigation ul ul li a {
	font-size: 14px;
	padding-top: 13px;
	padding-bottom: 13px;
	color: #aeb2b7;
}

.sidebar #leftside-navigation li.logout {
	position: absolute;
	left: 0;
	bottom: 0;
}

.sidebar #leftside-navigation ul li a {
	
}
</style>
</head>
<body>
	<aside class="sidebar">
		<div id="leftside-navigation" class="nano">
			<ul class="nano-content">

				<li><a
					href="<%=request.getContextPath()%>/back-end/back-end-home.jsp"><i
						class="fa fa-home"></i><span>HOME</span></a></li>


				<%
				List<Integer> list2 = (List<Integer>) session.getAttribute("list2");

				System.out.println("list2=" + list2);
				System.out.println(list2.contains(5));
				%>

		<c:if test="<%=list2.contains(5)%>">

					<li class="sub-menu"><a href="javascript:void(0);"><i
							class="fa fa-users"></i><span>員工資料管理</span><i
							class="arrow fa fa-angle-right pull-right"></i></a>
						<ul>
							<li><a
								href="<%=request.getContextPath()%>/back-end/emp/listAllEmp.jsp">員工管理</a></li>
							<li><a
								href="<%=request.getContextPath()%>/back-end/emp/addEmp.jsp">新增員工</a></li>
							<li><a
								href="<%=request.getContextPath()%>/back-end/emper/select_page_emper.jsp">員工權限管理</a></li>
							<li><a
								href="<%=request.getContextPath()%>/back-end/emper/addEmper.jsp">新增員工權限</a></li>
							<li><a
								href="<%=request.getContextPath()%>/back-end/emperDetail/listAllEmperDetail.jsp">權限明細管理</a></li>
							<li><a
								href="<%=request.getContextPath()%>/back-end/emperDetail/addEmperDetail.jsp">新增權限明細</a></li>
						</ul></li>
		</c:if>

		<c:if test="<%=list2.contains(5)%>" >
						<li class="sub-menu"><a
						href="<%=request.getContextPath()%>/back-end/member/select_page.jsp"><i
						class="fa fa-users"></i><span>會員資料管理</span></a></li>
		</c:if>	
				
		<c:if test="<%=list2.contains(5) || list2.contains(2)%>">
						<li class="sub-menu"><a href="javascript:void(0);"><i
						class="fa fa-coffee"></i><span>現場管理系統</span><i
						class="arrow fa fa-angle-right pull-right"></i></a>
		</c:if>
					<ul>
		<c:if test="<%=list2.contains(5)%>">
							<li><a
								href="<%=request.getContextPath()%>/back-end/prom/listAllProm.jsp">促銷管理</a></li>
							<li><a
								href="<%=request.getContextPath()%>/back-end/meal/select_page.jsp">菜單管理</a></li>

							<li><a
								href="<%=request.getContextPath()%>/back-end/table/select_page_table.jsp">桌位佈局設定</a></li>
		</c:if>
		<c:if test="<%=list2.contains(5) || list2.contains(2)%>">
							<li><a
								href="<%=request.getContextPath()%>/back-end/table/tables.jsp">桌位狀態管理</a></li>
		</c:if>
					</ul></li>
					<!-- 記住，可以自訂網頁帶過去的欄位，新增class為active即可。 -->
		<c:if test="<%=list2.contains(5)%>">
					<li class="sub-menu"><a href="javascript:void(0);"><i
						class="fa  fa-globe"></i><span>最新消息管理</span><i
						class="arrow fa fa-angle-right pull-right"></i></a>
						<ul>
							<li><a
								href="<%=request.getContextPath()%>/back-end/news/select_page.jsp">最新消息查詢</a></li>
							<li><a
								href="<%=request.getContextPath()%>/back-end/news/listAllNews.jsp">顯示所有消息</a></li>
							<li><a
								href="<%=request.getContextPath()%>/back-end/news/addNews.jsp">新增最新消息</a></li>
						</ul></li>
		</c:if>
		<c:if test="<%=list2.contains(4) || list2.contains(5)%>">						
					<li class="sub-menu"><a href="javascript:void(0);"><i
						class="fa fa-users"></i><span>訂位資訊管理</span><i
						class="arrow fa fa-angle-right pull-right"></i></a>
						<ul>
							<li><a
							href="<%=request.getContextPath()%>/back-end/rez/addRez.jsp">新增訂位</a></li>
							<li><a
							href="<%=request.getContextPath()%>/back-end/rez/listAllRez.jsp">顯示全部訂位</a></li>
							<li><a
							href="<%=request.getContextPath()%>/back-end/rez/select_page.jsp">查詢訂位資訊</a></li>
							<li><a
							href="<%=request.getContextPath()%>/back-end/reztime/listAllRezTime.jsp">顯示訂位數量</a></li>
							<li><a
							href="<%=request.getContextPath()%>/back-end/reztime/selectRezTime_page.jsp">查詢訂位數量</a></li>
							<li><a
							href="<%=request.getContextPath()%>/back-end/rezdetail/allrezdetail.jsp">安排訂位桌位</a></li>
						</ul></li>
		</c:if>

					<li class="sub-menu"><a href="javascript:void(0);"><i
						class="fa fa-book"></i><span>訂單管理系統</span><i
						class="arrow fa fa-angle-right pull-right"></i></a>
						<ul>
		<c:if test="<%=list2.contains(5)%>">
							<li><a
							href="<%=request.getContextPath()%>/back-end/live_order/LiveOrderHistory.jsp">歷史訂單查詢</a></li>
		</c:if>

		<c:if test="<%=list2.contains(3) || list2.contains(5)%>">
							<li><a
								href="<%=request.getContextPath()%>/back-end/online_order/delivery.jsp">外送訂單管理</a></li>
		</c:if>
		<c:if test="<%=list2.contains(4) || list2.contains(5)%>">
							<li><a
								href="<%=request.getContextPath()%>/back-end/online_order/toGo.jsp">外帶訂單管理</a></li>
		</c:if>
		<c:if test="<%=list2.contains(1) || list2.contains(5)%>">
							<li><a
								href="<%=request.getContextPath()%>/back-end/live_order/UnCookedList.jsp">內場訂單管理</a></li>
		</c:if>
		<c:if test="<%=list2.contains(2) || list2.contains(5)%>">
							<li><a
								href="<%=request.getContextPath()%>/back-end/live_order/UnServedList.jsp">外場訂單管理</a></li>
		</c:if>
					</ul></li>

		<c:if test="<%=list2.contains(2) || list2.contains(5)%>">
					<li class="sub-menu"><a
						href="<%=request.getContextPath()%>/back-end/live_order/OrderSystem2.jsp"><i
							class="fa fa-cutlery"></i><span>點餐系統</span></a></li>
		</c:if>
		<c:if test="<%=list2.contains(5) || list2.contains(4)%>">
					<li class="sub-menu"><a
						href="<%=request.getContextPath()%>/back-end/checkout/checkout.jsp"><i
							class="fa fa-shopping-cart"></i><span>結帳系統</span></a></li>
		</c:if>
				<li class="logout"><a
					href="<%=request.getContextPath()%>/back-end/empLogin/empLogin.do?action=logOut"><i
						class="fa fa-power-off"></i><span>Logout</span></a></li>
			</ul>
		</div>
	</aside>
</body>
<script>
	$("#leftside-navigation .sub-menu > a").click(
			function(e) {
				$("#leftside-navigation ul ul").slideUp(), $(this).next().is(
						":visible")
						|| $(this).next().slideDown(), e.stopPropagation()
			})
</script>
<c:if test="<%=list2.contains(5)%>">
<%@ include file="/back-end/chatWeb/backchat.jsp"%>
</c:if>
</html>