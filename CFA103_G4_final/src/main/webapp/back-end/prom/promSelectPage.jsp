<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>BMB:促銷管理</title>

<style>
.preview {
	position: absolute;
	top: 30px;
	left: 250px;
	z-index: 1;
	width: 87.5%;
	height: 94%;
	padding: 15px;
	/* 	border: 2px solid red; */
}
a{ 
	text-decoration:none;
} 
a:hover{ 
	text-decoration:underline;
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
	<h1>促銷資料管理:</h1>
</header>

<main>
<%-- 錯誤表列 --%>
	<c:if test="${not empty errorMsgs}">
		<font style="color: red">請修正以下錯誤:</font>
		<ul>
			<c:forEach var="message" items="${errorMsgs}">
				<li style="color: red">${message}</li>
			</c:forEach>
		</ul>
	</c:if>
<!-- 顯示全部促銷方案 -->
	<ul style="list-style-type:none;">
		<li>
			<a href='<%=request.getContextPath()%>/back-end/prom/listAllProm.jsp'>
			<img src="<%=request.getContextPath()%>/back-end/meal/pic/images/listAllIcon.svg" 
			width="15" height="15" border="0">列示全部促銷方案
			</a>
		</li>
<!-- 	新增餐點	 -->

	
		<li>
			<a href='<%=request.getContextPath()%>/back-end/prom/addProm.jsp'>
			<img src="<%=request.getContextPath()%>/back-end/meal/pic/images/addIcon.svg"
			width="15" height="15" border="0">新增促銷方案
			</a>
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
				<b>選擇餐點編號:</b> <select size="1" name="mealno">
					<c:forEach var="mealVO" items="${mealSvc.all}">
						<option value="${mealVO.mealno}">${mealVO.mealno}
					</c:forEach>
				</select> <input type="hidden" name="action" value="getOne_For_Display">
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
</html>