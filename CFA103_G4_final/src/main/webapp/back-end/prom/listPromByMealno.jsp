<%@page import="com.prom.model.PromJDBCDAO"%>
<%@page import="com.prom.model.PromVO"%>
<%@page import="com.prom_detail.model.*"%>
<%@page import="com.meal.model.*"%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>

<%
List<PromDetailVO> pdVO =(List<PromDetailVO>) request.getAttribute("pdVO"); //promdetailServlet.java(Concroller), 存入req的promVO物件
List<PromVO> PromVOLIST =(List<PromVO>) request.getAttribute("PromVOLIST");//promdetailServlet.java(Concroller), 存入req的promno物件
%>
<%
PromJDBCDAO Pdao = new PromJDBCDAO();
List<PromVO> plist = Pdao.getAll();
pageContext.setAttribute("Plist", plist);
%>
<%
MealJDBCDAO dao = new MealJDBCDAO();
List<MealVO> list = dao.getAll();
pageContext.setAttribute("list", list);
%>

<jsp:useBean id="PDJDBCDAO" scope="page" class="com.prom_detail.model.PromDetailJDBCDAO" />
<jsp:useBean id="MSvc" scope="page" class="com.meal.model.MealService" />

<!DOCTYPE html>
<html>
<head>
<meta charset="BIG5">
<title>促銷資料查詢結果</title>

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
	height:20px;
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
.promcontent{
	overflow:hidden;
	white-space: nowrap;
	text-overflow: ellipsis;
	display: -webkit-box;
	-webkit-line-clamp: 3;
	-webkit-box-orient: vertical;
	white-space: normal;
}
.shtitle{
  font-family: 'Montserrat', sans-serif;
  margin:0;
  background:linear-gradient(to bottom,#2af598,#009efd);
  -webkit-text-fill-color:transparent;
  -webkit-background-clip:text;
  font-size:22px;
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
	<table id="table-1">
		<tr>
			<td>
				<h3>促銷查詢結果:所有包含餐點<font class="shtitle">${MSvc.getOneMeal(mealno).meal_name}</font>的促銷活動</h3>
				<h4>
					<a href="<%=request.getContextPath()%>/back-end/prom/listAllProm.jsp">
					<img src="<%=request.getContextPath()%>/back-end/meal/pic/images/mealHome.svg"
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
</header>

	<table >
		<tr style="font-size:12px;">
			<th style="width:130px;">編號</th>
			<th style="width:480px;">促銷活動</th>
			<th style="width:480px;">活動簡介</th>
			<th style="width:400px;">活動開始日</th>			
			<th style="width:400px;">活動結束日</th>		
			<th style="width:300px;">活動圖片</th>
			<th style="width:300px;">促銷狀態</th>
			<th style="width:90px;">查看</th>
			<th style="width:90px;">修改</th>
			<th style="width:90px;">刪除</th>
	
			
		</tr>
		<c:forEach var="PromVO" items="${PromVOLIST}">	
			<tr>
				<td align="center" >${PromVO.promno}</td>
				<td align="left" >${PromVO.prom_title}</td>
				
				<td align="left" ><div class="promcontent">${PromVO.prom_content}</div></td>
		
				<td align="center" >
				${PDJDBCDAO.findByPrimaryKeyLimitOne(PromVO.promno).prom_time_start}
				</td>
				<td align="center" >
				${PDJDBCDAO.findByPrimaryKeyLimitOne(PromVO.promno).prom_time_end}
				</td>
				<td>
					<div class="listallprompic">
						<img
						src="<%=request.getContextPath()%>/PromImageFromDB?id=${PromVO.promno}" />
					</div>
				</td>
				
				<c:if test="${PDJDBCDAO.findByPrimaryKeyLimitOne(PromVO.promno).prom_state == 1}">
				<td align="center" >促銷中(打折)
				</td>
				</c:if>
				<c:if test="${PDJDBCDAO.findByPrimaryKeyLimitOne(PromVO.promno).prom_state == 0}">
				<td align="center" >促銷中(金額折扣)
				</td>
				</c:if>
				<c:if test="${PDJDBCDAO.findByPrimaryKeyLimitOne(PromVO.promno).prom_state < 0}">
				<td align="center" ><font color="red">暫停促銷中</font>
				</td>
				</c:if>
				<%-- 同列之修改及刪除 --%>
				<td style="width:100px;">
					<FORM METHOD="post" ACTION="<%=request.getContextPath()%>/promdetail/promdetail.do" style="margin-bottom: 0px;">
<!-- 						<input type="submit" value="查看">  -->
						<input type="hidden" name="promno" value="${PromVO.promno}"> 
						<input type="hidden" name="action" value="getOneByPk">  
						<input type="image" class="button_search" src="<%=request.getContextPath()%>/back-end/meal/pic/images/searcheye.svg"  width="20" height="20" >
					</FORM>
				</td>
				<td style="width:100px;">
					<FORM METHOD="post" ACTION="<%=request.getContextPath()%>/prom/prom.do" style="margin-bottom: 0px;">
<!-- 						<input type="submit" value="修改">  -->
						<input type="hidden" name="promno" value="${PromVO.promno}"> 
						<input type="hidden" name="action" value="getOne_For_Update">  
						<input type="image" class="button_update" src="<%=request.getContextPath()%>/back-end/meal/pic/images/updateicon.svg"  width="20" height="20" >
						
					</FORM>
				</td>
				<td>
					<FORM METHOD="post"
						ACTION="<%=request.getContextPath()%>/prom/prom.do"
						style="margin-bottom: 0px;">
<!-- 						<input type="submit" value="刪除">  -->
						<input type="hidden" name="promno" value="${PromVO.promno}"> 
						<input type="hidden" name="action" value="delete">
						<input type="image" class="button_delete" src="<%=request.getContextPath()%>/pic/icon/trash.svg"  width="20" height="20" >
						
					</FORM>
				</td>
	
		</c:forEach>
	</table>

</div>
</body>
</html>