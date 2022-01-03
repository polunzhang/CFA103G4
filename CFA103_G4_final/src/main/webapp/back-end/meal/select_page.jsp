<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>BMB:菜單管理</title>

<style>

header.header{
  width: 100%;
  font-size:15px;
  font-family: 'STCaiyun', sans-serif;
  margin: 0 auto 10px auto;
}



.preview {
	position: absolute;
	top: 70px;
	left: 250px;
	right:100px;
	z-index: 1;
	width: 87.5%;
	height: 94%;
	padding: 15px;
/* 	 	border: 2px solid red;  */
}

a{ 
	text-decoration:none;
} 
a:hover{ 
	text-decoration:underline;
}
</style>
<style>
    @import
  url("https://fonts.googleapis.com/css?family=Neuton|Oswald");


p.titlep {
  text-transform: uppercase;
  letter-spacing: .5em;
  display: inline-block;

  border-width: 4px 0;
  padding: 1.5em 0em;
  position: absolute;
  top: 1%;
  left: 90%;
  width: 40em;
  margin: 0 0 -10em -20em;
  }
  span.titlep {
    font: 700 2em "Oswald", sans-serif;
    letter-spacing: 0;
    padding: .25em 0 .325em;
    display: block;
    margin: 0 auto;
    text-shadow: 0 0 80px rgba(255,255,255,.5);
    background: url(https://i.ibb.co/RDTnNrT/animated-text-fill.png) repeat-y;
    -webkit-background-clip: text;
    background-clip: text;
    -webkit-text-fill-color: transparent;
    -webkit-animation: aitf 80s linear infinite;
    -webkit-transform: translate3d(0,0,0);
    -webkit-backface-visibility: hidden;
  }

@-webkit-keyframes aitf {
  0% { background-position: 0% 50%; }
  100% { background-position: 100% 50%; }
}
  </style>
</head>
<body>
<!-- include側邊欄(sidebar) -->
<aside onload="connect();" onunload="disconnect();" >
		<%@ include file="/back-end/sidebar.jsp"%>
</aside>
<!-- include背景圖片(正常統一版) -->
		<%@ include file="/back-end/meal/background/background.html"%>
		
<!-- 資料管理抬頭 (header)-->
<div class="preview">
<header class="header">
	<h1><p class="titlep"><span class="titlep">餐點資料管理</span></p></h1>
</header>

<main>
<%-- 錯誤表列 --%>
	<c:if test="${not empty errorMsgs}">
		<font style="color: red;">請修正以下錯誤:</font>
		<ul>
			<c:forEach var="message" items="${errorMsgs}">
				<li style="color: red">${message}</li>
			</c:forEach>
		</ul>
	</c:if>
<!-- 顯示全部餐點 -->
	<ul style="list-style-type:none;">
		<li>
			<a href='<%=request.getContextPath()%>/back-end/meal/listAllMeal.jsp'>
			<img src="<%=request.getContextPath()%>/back-end/meal/pic/images/listAllIcon.svg" 
			width="15" height="15" border="0">列示全部餐點
			</a>
		</li>
<!-- 	新增餐點	 -->

	
		<li>
			<a href='<%=request.getContextPath()%>/back-end/meal/addMeal.jsp'>
			<img src="<%=request.getContextPath()%>/back-end/meal/pic/images/addIcon.svg"
			width="15" height="15" border="0">新增餐點
			</a>
		</li>
	
		<li>
			<FORM METHOD="post" ACTION="<%=request.getContextPath()%>/back-end/meal_type/mealType.do">
			<img src="<%=request.getContextPath()%>/back-end/meal/pic/images/addmealtype.svg"
			width="15" height="15" border="0">
			<b>新增餐點種類:</b>
			<input type="text" name="mealtype"> 
			<input type="hidden" name="action" value="insert"> 
			<input type="submit" value="送出">
			</FORM>
			<br> <br>
		</li>

<!-- 查詢餐點 -->
		<li>
			<FORM METHOD="post"
				ACTION="<%=request.getContextPath()%>/meal/meal.do">
				<b>輸入餐點編號 :</b> <input type="text" name="mealno"> <input
					type="hidden" name="action" value="getOne_For_Display"> <input
					type="submit" value="送出">
			</FORM>
		</li>
<!-- 宣告usebean -->
		<jsp:useBean id="mealSvc" scope="page"
			class="com.meal.model.MealService" />
		<!-- 待mealtype service完成 -->
		<jsp:useBean id="mealtypeSvc" scope="page"
			class="com.meal_type.model.MealTypeJDBCDAO" />
<!-- 查詢餐點(以餐點名查詢) -->
		<li>
			<FORM METHOD="post"
				ACTION="<%=request.getContextPath()%>/meal/meal.do">
				<b>輸入餐點名稱 :</b> <input type="text" name="mealname"> <input
					type="hidden" name="action" value="getOne_For_Display_Byname">
				<input type="submit" value="送出">
			</FORM>
		</li>
<!-- 查詢餐點(選擇餐點編號以查詢) -->
		<li>
			<FORM METHOD="post"
				ACTION="<%=request.getContextPath()%>/meal/meal.do">
				<b>選擇餐點編號:</b> 
				<select size="1" name="mealno">
					<c:forEach var="mealVO" items="${mealSvc.all}">
						<option value="${mealVO.mealno}">${mealVO.mealno}
					</c:forEach>
				</select> 
				
				<input type="hidden" name="action" value="getOne_For_Display">
				<input type="submit" value="送出">
			</FORM>
		</li>
<!-- 查詢餐點(選擇餐點名稱以查詢) -->
		<li>
			<FORM METHOD="post"
				ACTION="<%=request.getContextPath()%>/meal/meal.do">
				<b>選擇餐點名稱:</b> <select size="1" name="mealno">
					<c:forEach var="mealVO" items="${mealSvc.all}">
						<option value="${mealVO.mealno}">${mealVO.meal_name}
					</c:forEach>

				</select> <input type="hidden" name="action" value="getOne_For_Display">
				<input type="submit" value="送出">
			</FORM>
		</li>
<!-- 查詢餐點(以餐種類查詢) -->
		<li>
			<FORM METHOD="post"
				ACTION="<%=request.getContextPath()%>/meal/meal.do">
				<b>選擇餐點種類:</b> <select size="1" name="meal_type_no">
					<c:forEach var="mealtypeVO" items="${mealtypeSvc.all}">
						<option value="${mealtypeVO.meal_type_no}">
							${mealtypeVO.meal_type_name}
					</c:forEach>
				</select> <input type="hidden" name="action" value="getOne_Type_For_Display">
				<input type="submit" value="送出">
			</FORM>
		</li>
	</ul>
</div>
</main>
</body>
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/2.1.3/jquery.min.js"></script>

</html>