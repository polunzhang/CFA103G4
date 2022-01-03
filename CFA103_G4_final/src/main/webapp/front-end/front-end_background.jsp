<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
	<%@ page import="java.util.*"%>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<title>background</title>

	<style>
	body{
		background-image: linear-gradient(rgba(255, 255, 255, 0.4),rgba(255, 255, 255, 0.4)), url('<%=request.getContextPath()%>/front-end/pic/bricks.jpg') ;
		background-size: cover;	
	}
	/* ===============================header==================================== */
	header{
		background-color: white;
		height: 50px;
		display: flex;
		justify-content: space-around;
		position: sticky;
		top: 2px;
		z-index:10;
	}
	nav{
		/* display: inline-block; */
	}
	nav a{
		text-decoration: none;
		padding: 0px 10px;
		color: black;
		line-height: 50px;
		display: inline-block;
	}
	nav a:hover{
		background-color: burlywood;
		text-decoration: none;
		color: white;
	}
	.logo{
		transition: 1s;
	}
	.logo:hover{
		transform: scale(1.3);
	}
	.cart, .user, .logout{
		margin: 0;
		padding: 0;
		height: 20px;
		width: 20px;
		display: inline-block;
		vertical-align: middle;
	}

</style>

</head>
<body>
	
	<header>
		<a href="<%=request.getContextPath()%>/front-end/front-end_homepage.jsp"><img class="logo" src="<%=request.getContextPath()%>/front-end/pic/Business Is Booming.jpg" alt="logo_pic" width="150px" height="150px"></a>
		<nav>
			<a href="<%=request.getContextPath()%>/front-end/aboutus/aboutus.jsp">關於我們</a>
			<a href="<%=request.getContextPath()%>/front-end/news/selectNewsTitle.jsp">最新消息</a>
			<a href="<%=request.getContextPath()%>/front-end/meal/MealListOnFrontPage.jsp" id="menu">菜單</a>
			<a href="<%=request.getContextPath()%>/front-end/rez/addRez.jsp">線上訂位</a>
			<a href="<%=request.getContextPath()%>/front-end/shopping_system/EShop2.jsp"">線上訂餐</a>
			<a href="<%=request.getContextPath()%>/front-end/shopping_system/Cart.jsp"><img class="cart" src="<%=request.getContextPath()%>/front-end/pic/cart.png" alt=""></a>
			<a href="<%=request.getContextPath()%>/front-end/member/front-end_listOneMember.jsp"><img class="user" src="<%=request.getContextPath()%>/front-end/pic/user.png" alt=""></a>
			<c:if test="${not empty sessionScope.memberVO}">
			<a href="<%=request.getContextPath()%>/front-end/member/MemberServlet.do?action=logout"><img class="logout" src="<%=request.getContextPath()%>/front-end/pic/logout.png" alt=""></a>
			</c:if>
		</nav>
	</header>

</body>
</html>