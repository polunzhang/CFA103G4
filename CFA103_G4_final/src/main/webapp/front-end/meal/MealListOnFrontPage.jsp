<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page import="com.meal.model.*"%>
<%@page import="com.meal.model.MealJDBCDAO"%>
<%@page import="com.meal_type.model.*"%>
<%
MealJDBCDAO dao = new MealJDBCDAO();
List<MealVO> list = dao.getAll();
pageContext.setAttribute("list", list);
%>
<jsp:useBean id="MealTypeSvc" scope="page" class="com.meal_type.model.MealTypeService" />
<jsp:useBean id="mealSvc" scope="page" class="com.meal.model.MealService" />
<jsp:useBean id="PromDetailService" scope="page" class="com.prom_detail.model.PromDetailService" />


<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>BIB:菜單</title>
<script src="http://code.jquery.com/jquery-1.12.4.min.js"></script>
<style>
.section{
/* 	border:2px black solid; */
	text-align:center;
	background-color: rgba(196, 220, 179, 0.2);
	font-size:0;
	z-index:-100;
	top:50px;
	bottom:100px;
}
.titleh1 h1{
	text-align:center;
	font-size:25px;
	line-height:50px;;
}
.menu-item{
	display:inline-block;
	line-height:normal;
	overflow : hidden;
	background:	#666666;
	border:1px black solid;
	width:350px;
	height:350px;
	position:relative;
	top:125px;
}
.mealPic{
 	max-height: 100%;  
    max-width: 100%; 
    width: auto;
    height: auto;
    position: absolute;  
    top: 0;  
    bottom: 0;  
    left: 0;  
    right: 0;  
    margin: auto;
    opacity:1;
}
.mealnameFrame{
	font-size:25px;
	height: 350px;
	width: 350px;
/* 	border:2px black solid; */
	position: absolute;  
	top: 0;         
	bottom: 0;      
	transition:0.3s;
	font-family: "微軟正黑體";
	color:white;
	opacity:0.5;
}

.mealintroFrame{
	height: 350px;
	width: 350px;
	left:-5px;
	font-size:27px;
	text-align:center;
	opacity:0;
	position: absolute;
	bottom: -5px;
	margin:5px;
	color:white;
	text-align:left;
	transition:0.8s;
/* 	border:1px red solid; */
	line-height:110px;
	font-family: "微軟正黑體";
}
.menu-item:hover .mealintroFrame{
opacity:1;
line-height:30px;
bottom: -200px;
}
.menu-item:hover .mealnameFrame{
opacity:1;
font-size:35px;
position: absolute;
}
.menu-item:hover .mealPic{
opacity:0.5;
}

.buttondiv{
	position: absolute;
 	top:125px;
	display:inline;
	right:120px;
}
.btn{
  box-sizing: border-box;
  appearance: none;
  background-color: transparent;
  border: 2px solid #3498db;
  border-radius: 0.6em;
  color: #3498db;
  cursor: pointer;
  align-self: center;
  font-size: 1rem;
  font-weight: 400;
  line-height: 1;
  margin: 20px;
  padding: 1.2em 2.8em;
  text-decoration: none;
  text-align: center;
  text-transform: uppercase;
  font-family: 'Montserrat', sans-serif;
  font-weight: 700;
}

  
.btn {
  border-color: #3498db;
  color: #fff;
  box-shadow: 0 0 40px 40px #3498db inset, 0 0 0 0 #3498db;
  transition: all 150ms ease-in-out;
  
 }
.btn:hover {
  color: black;
  outline: 0;
  box-shadow: 0 0 10px 0 #3498db inset, 0 0 10px 4px #3498db;
}
.menu-item:hover .meal_price{
position: absolute;
opacity:1;
z-index:100;
color:white;
height:200px;
width:200px;
weight:20px;
font-size:30px;
top: 16%;   
right:-5%;      
}
.meal_price_delete{
	text-decoration:line-through;
	transition:0.3s;
}
.meal_price2{
transition:0.6s;
}
.menu-item:hover .meal_price2{
position: absolute;
opacity:1;
z-index:100;
color:white;
height:200px;
width:200px;
weight:20px;
font-size:30px;
top: 25%;   
right:-1%;
  
}
  
</style>
</head>
<script>
// 按鈕事件
	$(document).on("click", ".buttondiv .btn-meal-type", function(event){
		if($(this).val() === "0"){
			$(".menu-item").hide();
			$(".menu-item").show();
		}else{
			let str = ".meal_type"+ $(this).val();
			$(".menu-item").hide();
			$(str).parent().show();
		}
	});
</script>


<body>
<%@ include file="/front-end/front-end_background.jsp"%>
<br><br>
<main>

<section class="section">

<!--    <div class="titleh1"> -->
<!--    		<h1>全部餐點</h1> </div> -->
   		
<!--    全部button+mealtype的button	 -->

	<div class="buttondiv">	
	<button type="button" class="btn btn-secondary btn-meal-type" value="0">全部</button>
	<c:forEach var="MealTypeVO" items="${MealTypeSvc.all}">
		<button type="button" class="btn btn-secondary btn-meal-type" value="${MealTypeVO.meal_type_no}">${MealTypeVO.meal_type_name}</button>
	</c:forEach>
	</div><br>
    
<c:forEach var="MealVO" items="${mealSvc.all}">    
		<c:choose>
			<c:when test="${empty PromDetailService.checkMPByMn(MealVO.mealno)}">
					
			      <div class="menu-item">
			      <div class="meal_type${MealVO.meal_type_no}" style="display:none;">${MealVO.meal_type_no}</div>
				      <div class="meal_price">NT.${MealVO.meal_price}</div>
				<!--     	div裝著pic -->
				      <img class="mealPic"src="<%=request.getContextPath()%>/MealPicServlet?id=${MealVO.getMealno()}"/>
				      <div class="mealintroFrame">${MealVO.meal_intro}</div>
				      <div class="mealnameFrame">${MealVO.meal_name}</div>
			      </div>
		     </c:when>
	        
        	<c:otherwise>
        		<div class="menu-item">
			      <div class="meal_type${MealVO.meal_type_no}" style="display:none;">${MealVO.meal_type_no}</div>
				      <div class="meal_price meal_price_delete">NT.${MealVO.meal_price}</div>
				      <div class="meal_price2">促銷價!NT.${PromDetailService.checkMPByMn(MealVO.mealno)}</div>
				<!--     	div裝著pic -->
				      <img class="mealPic"src="<%=request.getContextPath()%>/MealPicServlet?id=${MealVO.getMealno()}"/>
				      <div class="mealintroFrame">${MealVO.meal_intro}</div>
				      <div class="mealnameFrame">${MealVO.meal_name}</div>
			      </div>
			 </c:otherwise>
     </c:choose>
 </c:forEach>   
  </section>
<br><br><br><br><br><br><br><br>
</main>
<footer>
<%@ include file="/front-end/front-end_footer.jsp"%></footer>
</body>
</html>