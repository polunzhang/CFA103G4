<%@page import="com.meal.model.MealVO"%>
<%@page import="com.meal.model.MealJDBCDAO"%>
<%@page import="com.meal_type.model.*"%>

<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>

<%
MealJDBCDAO dao = new MealJDBCDAO();
List<MealVO> list = dao.getAll();
pageContext.setAttribute("list", list);
%>
<jsp:useBean id="MTSvc" scope="page" class="com.meal_type.model.MealTypeService" />

<!DOCTYPE html>
<html>
<head>
<meta charset="BIG5">
<title>菜單</title>



<style>
.preview {
	position: absolute;
	left: 200px;
	z-index: 1;
	width: 87.5%;
	height: 98%;
	padding: 15px;
	bottom:20px;
	/* 	border: 2px solid red; */
	
}


table, th, td {
	border: 1px solid #CCCCFF;
	width:1000px;
}

table#table-1 h3{
	display: block;
	text-align:left;
}
table#table-1 h4{
	display: block;
	text-align:left;
}

th, td {
	padding: 3px;
}

td, div.listallmealpic img{
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
tr.trdata:hover td{
border: 2px solid black;
}
.mintro{
padding:8px;
}

</style>
 <style>
    .back-to-top {
      display: none;
      /* 默认是隐藏的，这样在第一屏才不显示 */
      position: fixed;
      /* 位置是固定的 */
      bottom: 20px;
      /* 显示在页面底部 */
      right: 30px;
      /* 显示在页面的右边 */
      z-index: 99;
      /* 确保不被其他功能覆盖 */
      border: 1px solid #5cb85c;
      /* 显示边框 */
      outline: none;
      /* 不显示外框 */
      background-color: #fff;
      /* 设置背景背景颜色 */
      color: #5cb85c;
      /* 设置文本颜色 */
      cursor: pointer;
      /* 鼠标移到按钮上显示手型 */
      padding: 10px 15px 15px 15px;
      /* 增加一些内边距 */
      border-radius: 10px;
      /* 增加圆角 */
    }

    .back-to-top:hover {
      background-color: #5cb85c;
      /* 鼠标移上去时，反转颜色 */
      color: #fff;
    }
  </style>
</head>
<!-- back to top 按鈕的js -->
<script src="https://cdn.staticfile.org/jquery/2.2.4/jquery.min.js"></script>
  <script>
    $(function() {
      var $win = $(window);
      var $backToTop = $('.js-back-to-top');
      // 当用户滚动到离顶部100像素时，展示回到顶部按钮
      $win.scroll(function() {
        if ($win.scrollTop() > 100) {
          $backToTop.show();
        } else {
          $backToTop.hide();
        }
      });
      // 当用户点击按钮时，通过动画效果返回头部
      $backToTop.click(function() {
        $('html, body').animate({
          scrollTop: 0
        }, 200);
      });
    });
  </script>
  
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
				<h3>所有餐點資料</h3>
				<h4>
					<a href="<%=request.getContextPath()%>/back-end/meal/select_page.jsp">
					<img src="<%=request.getContextPath()%>/back-end/meal/pic/images/mealHome.svg"
						width="15" height="15" border="0">返回餐點管理頁面
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

	<table >
		<tr>
			<th style="width:200px;">編號</th>
			<th style="width:480px;">餐點類型</th>
			<th style="width:250px;">價格</th>
			<th>餐點名稱</th>
			<th>餐點介紹</th>
			<th style="width:400px;">餐點圖片</th>
			<th style="width:100px;">修改</th>
			<th style="width:100px;">刪除</th>
			
		</tr>
</header>
		<c:forEach var="MealVO" items="${list}">
			<tr class="trdata">
				<td align="center" >${MealVO.mealno}</td>
				<td align="center" >
				${MealVO.meal_type_no}.${MTSvc.findByPrimaryKey(MealVO.meal_type_no).meal_type_name}
				</td>
				<td align="center">${MealVO.meal_price}</td>
				<td align="center">${MealVO.meal_name}</td>
				<td align="left" class="mintro">${MealVO.meal_intro}</td>
				<td>
					<div class="listallmealpic">
						<img
						src="<%=request.getContextPath()%>/MealPicServlet?id=${MealVO.getMealno()}" />
					</div>
				</td>
				

				<%-- 同列之修改及刪除 --%>
				<td style="width:100px;">
					<FORM METHOD="post" ACTION="<%=request.getContextPath()%>/meal/meal.do" style="margin-bottom: 0px;">
<!-- 					<input type="submit" value="修改" > -->
						<input type="hidden" name="mealno" value="${MealVO.mealno}"> 
						<input type="hidden" name="action" value="getOne_For_Update">
						<input type="image" class="button_update" src="<%=request.getContextPath()%>/back-end/meal/pic/images/updateicon.svg"  width="20" height="20" >
					</FORM>
				</td>
				<td>
					<FORM METHOD="post"
						ACTION="<%=request.getContextPath()%>/meal/meal.do"
						style="margin-bottom: 0px;">
<!-- 						<input type="submit" value="刪除">  -->
						<input type="hidden" name="mealno" value="${MealVO.mealno}"> 
						<input type="hidden" name="action" value="delete">
						<input type="image" class="button_delete" src="<%=request.getContextPath()%>/pic/icon/trash.svg"  width="20" height="20" >
					</FORM>
				</td>
		
			</tr>
		</c:forEach>
	</table>
<button class="js-back-to-top back-to-top" title="回到头部">︽</button>
</div>
</body>
</html>