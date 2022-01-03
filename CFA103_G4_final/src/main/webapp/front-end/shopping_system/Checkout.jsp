<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*"%>
<%@ page import="java.util.* , com.online_detail.model.*"%>
<html>
<head>
<title>Checkout.jsp</title>
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/css/ShoppingCart.css">
<style>
.showMealPic {
	width: 100px;
	height: 100px;
 	object-fit: cover; 
 	flex: 1; 
 	border: 1px solid black;
}
img{
width:150px;
height:150px;
}


main{
		top: 3.5%;
		left: 12.5%;
		height: calc(100vh - 200px);
		width: calc(100% - 200px);
		padding-top: 45px;
	}
	
#cart{
	margin-left: 650px;
	margin-top: 100px;
	width: 80px;
	height: 80px;

}
#title {
	font-size: 50px;
	color: red;
}
#title2 {
	font-size: 30px;
	color: black;
	margin-left: 520px;
}



</style>
</head>
<body>

<%@ include file="/front-end/front-end_background.jsp"%>


<main>
	<table id="table-1" style="margin: auto;">
		<img id="cart" src="<%=request.getContextPath()%>/pic/icon/shopping-cart-check.svg">
		<font id="title" >已送出訂單</font>
	</table>
	<table id="table-2">
		<font id="title2">為您製作餐點中，餐點現點現做，請耐心等候</font>
	</table>
	
		
</main>
<%@ include file="/front-end/front-end_footer.jsp"%>

</body>
</html>