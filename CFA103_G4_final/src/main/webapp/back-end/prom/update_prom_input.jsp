<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.meal.model.*"%>
<%@ page import="com.prom.model.*"%>
<%@ page import="com.prom_detail.model.*"%>
<%@ page import="java.util.*"%>

<%
PromVO promVO = (PromVO) request.getAttribute("promVO"); //EmpServlet.java (Concroller) 存入req的empVO物件 (包括幫忙取出的empVO, 也包括輸入資料錯誤時的empVO物件)
List<PromDetailVO> promdetailVO = (List<PromDetailVO>) request.getAttribute("promdetailVO"); //EmpServlet.java (Concroller) 存入req的empVO物件 (包括幫忙取出的empVO, 也包括輸入資料錯誤時的empVO物件)
PromDetailVO pdVOLO = (PromDetailVO) request.getAttribute("pdVOLO"); //EmpServlet.java (Concroller) 存入req的empVO物件 (包括幫忙取出的empVO, 也包括輸入資料錯誤時的empVO物件)
%>
<%
MealJDBCDAO dao = new MealJDBCDAO();
List<MealVO> list = dao.getAll();
pageContext.setAttribute("list", list);
%>

<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
<title>促銷資料修改 - update_meal_input.jsp</title>

<style>
.preview {
	position: absolute;
	left: 200px;
	z-index: 1;
	width: 87.5%;
	height: 98%;
	padding: 15px;
	/* 	border: 2px solid red; */
}
div#oldprompic img{
 	width: 200px; 
 	heigth:200px; 
 	border: 2px solid black; 
}
table, th, td {
	border: 1px solid #000000;
}
table{ 
 	width:750px; 
 } 
h3{
	height:20px;
}
h4{
	height:30px;
}

</style>

</head>
<body >

<!-- include側邊欄(sidebar) -->
<aside onload="connect();" onunload="disconnect();" >
		<%@ include file="/back-end/sidebar.jsp"%>
</aside>
<!-- include背景圖片(毛玻璃拉長特化meal版) -->
		<%@ include file="/back-end/meal/background/ListAllBG.html"%>
		
<div class="preview">		
	<table id="table-1">
		<tr>
			<td>
				<h3>促銷資料修改</h3>
				<h4>
					<a
						href="<%=request.getContextPath()%>/back-end/prom/promSelectPage.jsp">
						<img
						src="<%=request.getContextPath()%>/back-end/meal/pic/images/mealHome.svg"
						width="15" height="15" border="0">回首頁
					</a>
					<br>
					<a href="<%=request.getContextPath()%>/back-end/prom/listAllProm.jsp">
					<img src="<%=request.getContextPath()%>/back-end/meal/pic/images/back.svg"
						width="15" height="15" border="0">返回上一頁
					</a>
				</h4>
			</td>
		</tr>
	</table>
	<%-- 錯誤表列 --%>
	<c:if test="${not empty errorMsgs}">
		<font style="color: red">請修正以下錯誤:</font>
		<ul>
			<c:forEach var="message" items="${errorMsgs}">
				<li style="color: red">${message}</li>
			</c:forEach>
		</ul>
	</c:if>

	<FORM METHOD="post" ACTION="<%=request.getContextPath()%>/prom/prom.do"
		name="form1" enctype="multipart/form-data">
		<table>
			<tr>
				<td style="width:150px;">促銷活動編號:<font color=red><b>*</b></font></td>
				<td><%=promVO.getPromno()%></td>
			</tr>

			<tr>
				<td class="titlename">活動設置管理員:<font color=red><b>*</b></font></td>
				<td><%=promVO.getEmpno()%></td>
			</tr>
			<tr>
				<td class="titlename">促銷活動名稱:</td>
				<td><input type="TEXT" name="prom_title" size="45"
					value="<%=promVO.getProm_title()%>" /></td>
			</tr>
			<tr>
				<td class="titlename">活動內容:</td>
				<td><textarea name="prom_content" cols="50" rows="10" 
				><%=promVO.getProm_content()%></textarea></td>
			</tr>

<%-- 			<jsp:useBean id="mealSvc" scope="page" --%>
<%-- 				class="com.meal.model.MealService" /> --%>
<%-- 			<jsp:useBean id="mealtypeSvc" scope="page" --%>
<%-- 				class="com.meal_type.model.MealTypeJDBCDAO" /> --%>
<!-- 			<tr> -->
<!-- 				<td>餐點類型:<font color=red><b>*</b></font></td> -->
<!-- 				<td><select size="1" name="mealtypeno"> -->
<%-- 						<c:forEach var="mealtypeVO" items="${mealtypeSvc.all}"> --%>
<%-- 							<option value="${mealtypeVO.meal_type_no}" --%>
<%-- 								${(mealVO.meal_type_no==mealtypeVO.meal_type_no)? 'selected':'' }>${mealtypeVO.meal_type_name} --%>
<%-- 						</c:forEach> --%>
<!-- 				</select></td> -->
<!-- 			</tr> -->
			<tr>
				<td class="titlename">餐點圖片:</td>
				<td>	
				<div id="oldprompic">
				<img
				src="<%=request.getContextPath()%>/PromImageFromDB?id=${promVO.getPromno()}">
				</div>
				</td>			
			</tr>
			<tr>
				<td class="titlename">圖片修改:</td>
				<td>
					<input type="file" name="uppromfile" >
				</td>
			</tr>
			
				<jsp:useBean id="PDSvc" scope="page"
				class="com.prom_detail.model.PromDetailJDBCDAO" /> 
			
			<tr>
				<td class="titlename">促銷類型:</td>
					<td>
						<select size="1" name="promState" >
							<option value="1" 
							${(pdVOLO.prom_state ==1)? 'selected':'' }>1.打折</option>
							<option value="0" 
							${(pdVOLO.prom_state ==0)? 'selected':'' }>0.金額折扣</option>
							<option value="${pdVOLO.prom_state-10}" 
							${(pdVOLO.prom_state <0)? 'selected':'' }>促銷暫停中</option>
						</select>
				</td>
			</tr>
			<tr>
				<td>折扣調整:</td>
				
				<c:if test="${pdVOLO.prom_state ==1}">
					<td>
					<input type="number" name="promValues" min="1"  value="${100-(pdVOLO.prom_value*100)}"></input>
					</td>
				</c:if>
					
				<c:if test="${pdVOLO.prom_state ==0}">
					<td>
					<input type="number" name="promValues" min="1"  value="${pdVOLO.prom_value}"></input>
					</td>
				</c:if>
				
				<c:if test="${pdVOLO.prom_state <0}">
					<td>
					<input type="number" name="promValues" min="1"  value=""></input>
					</td>
				</c:if>
			</tr>
			
			<tr>
				<td>活動開始日期:</td>
				<td>
					<input type="date" name="promTimeStart" id="time1" value="${pdVOLO.prom_time_start}">
				</td>
			</tr>
			<tr>
				<td>活動結束日期:</td>
				<td>
					<input type="date" name="promTimeEnd" id="time2" value="${pdVOLO.prom_time_end}">
				</td>
			</tr>	
	
		</table>
		<br> 
		<input type="hidden" name="action" value="update"> 
		<input type="hidden" name="promno" value="<%=promVO.getPromno()%>">
		<input type="hidden" name="empno" value="<%=promVO.getEmpno()%>">
		<input type="submit" value="送出修改">
	</FORM>
</div>
</body>

</html>