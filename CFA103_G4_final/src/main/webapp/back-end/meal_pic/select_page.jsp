<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>IBM Meal Home</title>

<style>
table#table-1 {
	width: 450px;
	background-color: #CCCCFF;
	margin-top: 5px;
	margin-bottom: 10px;
	border: 3px ridge Gray;
	height: 80px;
	text-align: center;
}

table#table-1 h4 {
	color: red;
	display: block;
	margin-bottom: 1px;
}

h4 {
	color: blue;
	display: inline;
}
</style>

</head>

<body bgcolor='white'>

	<table id="table-1">
		<tr>
			<td><h3>IBM Meal Home</h3>
				<h4>( MVC )</h4></td>
		</tr>
	</table>

	<p>This is the Home page for IBM Meal Home</p>

	<h3>資料查詢:</h3>

	<%-- 錯誤表列 --%>
	<c:if test="${not empty errorMsgs}">
		<font style="color: red">請修正以下錯誤:</font>
		<ul>
			<c:forEach var="message" items="${errorMsgs}">
				<li style="color: red">${message}</li>
			</c:forEach>
		</ul>
	</c:if>

	<ul>
		<li><a
			href='<%=request.getContextPath()%>/back-end/meal/listAllMeal.jsp'>List</a>
			all Meals. <br> <br></li>


		<li>
			<FORM METHOD="post"
				ACTION="<%=request.getContextPath()%>/meal/meal.do">
				<b>輸入餐點編號 (如 1):</b> <input type="text" name="mealno"> <input
					type="hidden" name="action" value="getOne_For_Display"> <input
					type="submit" value="送出">
			</FORM>
		</li>

		<jsp:useBean id="mealSvc" scope="page"
			class="com.meal.model.MealService" />
		<!-- 待mealtype service完成 -->
		<jsp:useBean id="mealtypeSvc" scope="page"
			class="com.meal_type.model.MealTypeJDBCDAO" />

		<li>
			<FORM METHOD="post"
				ACTION="<%=request.getContextPath()%>/meal/meal.do">
				<b>輸入餐點名稱 (如:沙拉等):</b> <input type="text" name="mealname"> <input
					type="hidden" name="action" value="getOne_For_Display_Byname">
				<input type="submit" value="送出">
			</FORM>
		</li>

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


	<h3>餐點管理</h3>

	<ul>
		<li><a href='<%=request.getContextPath()%>/back-end/meal/addMeal.jsp'>Add</a> a new Meal.</li>
	</ul>

</body>
</html>