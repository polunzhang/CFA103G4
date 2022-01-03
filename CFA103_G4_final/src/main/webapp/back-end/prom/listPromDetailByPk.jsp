<%@page import="com.prom.model.PromJDBCDAO"%>
<%@page import="com.prom.model.PromVO"%>
<%@page import="com.prom_detail.model.*"%>
<%@page import="com.meal.model.*"%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>

<%
List<PromDetailVO> pdVO =(List<PromDetailVO>) request.getAttribute("pdVO"); //promdetailServlet.java(Concroller), 存入req的promVO物件
Integer pdVOpromno =(Integer) request.getAttribute("pdVOpromnoo"); //promdetailServlet.java(Concroller), 存入req的promno物件
%>
<%
MealJDBCDAO dao = new MealJDBCDAO();
List<MealVO> list = dao.getAll();
pageContext.setAttribute("list", list);
%>

<jsp:useBean id="PDJDBCDAO" scope="page" class="com.prom_detail.model.PromDetailJDBCDAO" />
<jsp:useBean id="MSvc" scope="page" class="com.meal.model.MealService" />
<jsp:useBean id="PDdSvc" scope="page" class="com.prom_detail.model.PromDetailService" />
<jsp:useBean id="PSvc" scope="page" class="com.prom.model.PromService" />

<!DOCTYPE html>
<html>
<head>
<meta charset="BIG5">
<title>促銷資料查詢結果(明細)</title>

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


table, th, td {
	border: 1px solid #CCCCFF;
	width:1000px;
}

table#table-1 h3{
	display: block;
	text-align:left;
	height:15px;
}
table#table-1 h4{
	display: block;
	text-align:left;
	height:45px;
}
table#table-1 h5{
	display: block;
	text-align:left;
}

th, td {
	padding: 3px;
}

td, div.listallprompic img{
	width:80px;
	heigth:80px;
	border: 1px solid black; 	
	text-align:center;
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
<!-- include背景圖片(毛玻璃拉長特化meal版) -->
		<%@ include file="/back-end/meal/background/ListAllBG.html"%>
		


<div class="preview">
<header>
	<table id="table-1" height="50px">
		<tr>
			<td>
				<h3>促銷活動明細:${PSvc.getOneprom(pdVOpromno).prom_title}</h3>
				<h4>
					<a href="<%=request.getContextPath()%>/back-end/prom/promSelectPage.jsp">
					<img src="<%=request.getContextPath()%>/back-end/meal/pic/images/mealHome.svg"
						width="15" height="15" border="0">返回促銷管理頁面
					</a>
					<br>
					<a href="<%=request.getContextPath()%>/back-end/prom/listAllProm.jsp">
					<img src="<%=request.getContextPath()%>/back-end/meal/pic/images/back.svg"
						width="15" height="15" border="0">返回上一頁
					</a>
				<form METHOD="post" ACTION="<%=request.getContextPath()%>/promdetail/promdetail.do" style="margin-bottom: 0px;">
					<img src="<%=request.getContextPath()%>/back-end/meal/pic/images/adddetail.svg"
						width="15" height="15" border="0">快速新增餐點
					<select size="1" name="mealno" >
						<c:forEach var="mealVO" items="${list}">
						 	<option value="" disabled selected hidden>請選擇欲新增之餐點</option>
							<option value="${mealVO.mealno}">${mealVO.mealno}.${mealVO.meal_name}
						</c:forEach>
					</select>
					<input type="hidden" name="action" value="addpromdtmeal">
					<input type="hidden" name="promno" value="${pdVOpromno}">
					<input type="submit" value="送出">
				</form>	
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
</header>

	<table >
		<tr style="font-size:12px;">
			<th style="width:130px;">促銷編號</th>
			<th style="width:480px;">餐點編號</th>
			<th style="width:480px;">促銷類型</th>
			<th style="width:400px;">折扣金額/比例</th>			
<!-- 			<th style="width:90px;">修改</th> -->
			<th style="width:90px;">刪除</th>
	
			
		</tr>
		<c:forEach var="pdVO" items="${pdVO}">
			<tr>
				<td align="center">${pdVO.promno}</td>
				<td align="center">${pdVO.mealno}.${MSvc.getOneMeal(pdVO.mealno).meal_name}</td>
				
				
				<c:if test="${PDJDBCDAO.findByPrimaryKeyLimitOne(pdVO.promno).prom_state == 0}">
				<td align="center" >促銷中(金額減少)</td>
				</c:if>
				
				<c:if test="${pdVO.prom_state == 1}">
				<td align="center" >促銷中(打折)</td>
				</c:if>
				
				<c:if test="${pdVO.prom_state < 0}">
				<td align="center" ><font color="red">暫停促銷中</font></td>
				</c:if>
				
				
				<c:if test="${PDJDBCDAO.findByPrimaryKeyLimitOne(pdVO.promno).prom_state == 0}">
				<td align="center" >${pdVO.prom_value}元</td>
				</c:if>
				
				<c:if test="${pdVO.prom_state == 1}">
				
					<c:if test="${(pdVO.prom_value*100)%10 ==0}">
						<td align="center" >打${pdVO.prom_value*10}折</td>
					</c:if>
					
					<c:if test="${(pdVO.prom_value*100)%10 >0}">
						<td align="center" >打${pdVO.prom_value*100}折</td>
					</c:if>
					
				</c:if>
				
				<c:if test="${pdVO.prom_state < 0}">
				<td align="center" ><font color="red">暫停促銷中</font></td>
				</c:if>
										
<%-- 				<td align="center">${pdVO.prom_value}</td> --%>

		
<!-- 				<td style="width:100px;"> -->
<%-- 					<FORM METHOD="post" ACTION="<%=request.getContextPath()%>/promdetail/promdetail.do" style="margin-bottom: 0px;"> --%>
<%-- 						<input type="hidden" name="promno" value="${pdVO.promno}">  --%>
<!-- 						<input type="hidden" name="action" value="getOne_For_Update">   -->
<%-- 						<input type="image" class="button_update" src="<%=request.getContextPath()%>/back-end/meal/pic/images/updateicon.svg"  width="20" height="20" > --%>
<!-- 					</FORM> -->
<!-- 				</td> -->
				<%-- 同列之刪除 --%>
				<td>
					<FORM METHOD="post"
						ACTION="<%=request.getContextPath()%>/promdetail/promdetail.do"
						style="margin-bottom: 0px;">
						<input type="hidden" name="mealno" value="${pdVO.mealno}"> 
						<input type="hidden" name="promno" value="${pdVO.promno}"> 
						<input type="hidden" name="action" value="delete">
						<input type="image" class="button_delete" src="<%=request.getContextPath()%>/pic/icon/trash.svg"  width="20" height="20" >
					</FORM>
				</td>
			</tr>
		</c:forEach>
	</table>

</div>
</body>
</html>