<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.*"%>

<jsp:useBean id="mealSvc" scope="page"
	class="com.meal.model.MealService" />

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>homepage</title>

<style>
/* ===============================sections=======================================*/

/* ====================welcome=======================*/
.welcome {
	background-image: linear-gradient(rgba(255, 255, 255, 0.02),
		rgba(255, 255, 255, 0.20)),
		url("<%=request.getContextPath()%>/front-end/pic/table.jpg");
	background-size: cover;
	height: 90vh;
}

.first {
	color: black;
	font-size: 2.5rem;
	letter-spacing: 1.5rem;
	transform: translateX(10rem);
	animation: moveInLeft 2s ease-out !important;
	margin-top: 0;
	padding-top: 40vh;
	margin-bottom: 80px;
	max-width: 60%;
	font-weight: 700 !important;
}

.second {
	color: black;
	font-size: 2.5rem;
	letter-spacing: 1.5rem;
	transform: translateX(15rem);
	animation: moveInRight 2s ease-out !important;
	margin-top: 0;
	max-width: 60%;
	font-weight: 700 !important;
}

@keyframes moveInLeft {
	from { 
		opacity:0;
		transform: translateX(0rem);
	}
	
	to {
		opacity: 1;
		transform: translate(10rem);
	}
}

@keyframes moveInRight {
	from { 
		opacity:0;
		transform: translateX(25rem);
	}
	
	to {
		opacity: 1;
		transform: translate(15rem);
	}
}

/* ====================info=======================*/
.info {
 	position: relative;
}

.text {
	width: 48%;
	display: inline-block;
	background-image: linear-gradient(rgba(176, 137, 104, 0.5),
		rgba(176, 137, 104, 0.2));
 	z-index: -10;
	margin-top: 80px;
 	position: absolute;
}
.text:hover{
	background-image: linear-gradient(rgba(176, 137, 104, 0.8),
		rgba(176, 137, 104, 0.8)) !important;
}

p {
	font-size: 18px;
	letter-spacing: 6px;
	line-height: 36px
}

.image {
	display: inline;
	width: 45%;
	padding-left: 60px;
/* 	margin-top: 40px; */
	z-index: 100;
}

.kitchen {
	margin-top: 40px;
	z-index: 100;
	max-width: 100vh;
	height: auto;
}
/* ====================menu=======================*/
#carouselExampleIndicators {
	width: 70%;
	height: 700px;
	margin: 30px auto;
}

.carousel-inner {
	height: 600px;
	width: 100%;
}

.d-block {
	height: 600px;
	width: 600px;
	margin: 0px auto;
}

.carousel-control-prev {
	background-color: gray;
	height: 600px;
}

.carousel-control-next {
	background-color: gray;
	height: 600px;
}

.carousel-caption {
	width: 600px;
	background-image: linear-gradient(rgba(255, 255, 255, 0.3),
		rgba(255, 255, 255, 0.3));
	margin-bottom: 0;
}

h1 {
	text-align: center;
	padding-top: 20px;
	font-weight: 700 !important;
}

h5 {
	font-weight: 700 !important;
}
</style>

<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.2.1/css/bootstrap.min.css"
	integrity="sha384-GJzZqFGwb1QTTN6wy59ffF1BuGJpLSa9DkKMp0DgiMDm4iYMj70gZWKYbI706tWS"
	crossorigin="anonymous">

</head>
<body>

	<%@ include file="/front-end/front-end_background.jsp"%>

	<section class="welcome">
		<h1 class="first">???????????????????????????</h1>
		<h1 class="second">??????????????????????????????</h1>
	</section>
	<section class="info">
		<div class="image">
			<a href="<%=request.getContextPath()%>/front-end/aboutus/aboutus.jsp">
			<img class="kitchen"
				src="<%=request.getContextPath()%>/front-end/pic/kitchen.jpg" alt="">
				</a>
		</div>		
		<div class="text">
		
			<h2>ABOUT US</h2>
			<p>
				??????????????? ???????????????????????????????????? <br>???????????????????????????????????????????????? <br>??????????????????Business
				Is Booming?????? <br>??????????????????????????????????????????????????? <br>???????????????????????????????????? <br>???????????????????????????????????????
				<br>???????????????????????????????????????????????? <br>???????????????????????????????????????????????????
			</p>
			
		</div>
		
	</section>

	<section class="menu">

		<h1>????????????</h1>

		<div id="carouselExampleIndicators" class="carousel slide"
			data-ride="carousel">
			<ol class="carousel-indicators">
				<li data-target="#carouselExampleIndicators" data-slide-to="0"
					class="active"></li>
				<li data-target="#carouselExampleIndicators" data-slide-to="1"></li>
				<li data-target="#carouselExampleIndicators" data-slide-to="2"></li>
			</ol>
			<div class="carousel-inner">
				<div class="carousel-item active">
					<a href="<%=request.getContextPath()%>/front-end/meal/MealListOnFrontPage.jsp" id="menu">
					<img
						src="<%=request.getContextPath()%>/pic/meal_pic/meal_pic (13).jpg"
						class="d-block" alt="...">
					</a>
					<div class="carousel-caption d-none d-md-block">
						<h5>${mealSvc.getOneMeal(7).meal_name}</h5>
						<p>${mealSvc.getOneMeal(7).meal_intro}</p>
					</div>
				</div>
				<div class="carousel-item">
					<a href="<%=request.getContextPath()%>/front-end/meal/MealListOnFrontPage.jsp" id="menu">
					<img
						src="<%=request.getContextPath()%>/pic/meal_pic/meal_pic (17).jpg"
						class="d-block " alt="...">
					</a>
					<div class="carousel-caption d-none d-md-block">
						<h5>${mealSvc.getOneMeal(11).meal_name}</h5>
						<p>${mealSvc.getOneMeal(11).meal_intro}</p>
					</div>
				</div>
				<div class="carousel-item">
					<a href="<%=request.getContextPath()%>/front-end/meal/MealListOnFrontPage.jsp" id="menu">
					<img
						src="<%=request.getContextPath()%>/pic/meal_pic/meal_pic (15).jpg"
						class="d-block" alt="...">
					</a>
					<div class="carousel-caption d-none d-md-block">
						<h5>${mealSvc.getOneMeal(9).meal_name}</h5>
						<p>${mealSvc.getOneMeal(9).meal_intro}</p>
					</div>
				</div>
			</div>
			<a class="carousel-control-prev" href="#carouselExampleIndicators"
				role="button" data-slide="prev"> <span
				class="carousel-control-prev-icon" aria-hidden="true"></span> <span
				class="sr-only">Previous</span>
			</a> <a class="carousel-control-next" href="#carouselExampleIndicators"
				role="button" data-slide="next"> <span
				class="carousel-control-next-icon" aria-hidden="true"></span> <span
				class="sr-only">Next</span>
			</a>
		</div>

	</section>


	<%@ include file="/front-end/front-end_footer.jsp"%>

	<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js"
		integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo"
		crossorigin="anonymous"></script>
	<script
		src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.6/umd/popper.min.js"
		integrity="sha384-wHAiFfRlMFy6i5SRaxvfOCifBUQy1xHdJ/yoi7FRNXMRBu5WHdZYu1hA6ZOblgut"
		crossorigin="anonymous"></script>
	<script
		src="https://stackpath.bootstrapcdn.com/bootstrap/4.2.1/js/bootstrap.min.js"
		integrity="sha384-B0UglyR+jN6CkvvICOB2joaf5I4l3gm9GU6Hc1og6Ls7i6U/mkkaduKaBhlAXv9k"
		crossorigin="anonymous"></script>

</body>
</html>