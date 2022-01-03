<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page import="com.prom.model.*"%>
<%@page import="com.prom_detail.model.*"%>
<%@page import="com.emp.model.*"%>
<%@page import="com.meal.model.*"%>
<%@ page import="java.util.*" %>

<%
  PromVO promVO = (PromVO) request.getAttribute("promVO");
%>

<%
MealJDBCDAO dao = new MealJDBCDAO();
List<MealVO> list = dao.getAll();
pageContext.setAttribute("list", list);
%>

<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
<title>促銷資料新增</title>

<style>

  table {
	margin-top: 1px;
	margin-bottom: 1px;
  }
  table, th, td {
    border: 0px solid #CCCCFF;
  }
  th, td {
    padding: 1px;
  }
 .preview {
	position: absolute;
	top: 15px;
	left: 130px;
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

<script type="text/javascript">
Array.prototype.remove = function(index)
{
  var head=this.slice(0,index);
  var tail=this.slice(index+1);
  return head.concat(tail);
 /// 這個地方會有更好的寫法，我只是先找最快能用的
}

var optArr = new Array(); /// 紀錄選擇的部分

function noCtrlSelect(sel)
    {
          var index = sel.selectedIndex;

          if(optArr.indexOf(index)<0)
            optArr.push(index)
          else {
             optArr.remove(optArr.indexOf(index));
          }
          var totalLen = sel.length;

          for(var i = 0 ; i < totalLen ; i++)
          {
             if( optArr.indexOf(i)>=0 ){
                   sel.options[i].selected=true;
             } else { sel.options[i].selected=false; }
          }
    }
</script>
<body class="preview">
<!-- include側邊欄(sidebar) -->
<aside onload="connect();" onunload="disconnect();" >
		<%@ include file="/back-end/sidebar.jsp"%>
</aside>
<!-- include背景圖片(毛玻璃拉長特化meal版) -->
		<%@ include file="/back-end/meal/background/ListAllBG.html"%>


<div class="preview">
<header id="table-1" >
	
		 <h3>促銷明細新增</h3>
		 <h4>
		 <a href="<%=request.getContextPath()%>/back-end/prom/listAllProm.jsp">
		 <img src="<%=request.getContextPath()%>/back-end/meal/pic/images/mealHome.svg"
						width="15" height="15" border="0">返回促銷管理頁面
		 </a>
		 </h4>		
		 
	
</header>



<%-- 錯誤表列 --%>
<c:if test="${not empty errorMsgs}">
	<font style="color:red">請修正以下錯誤:</font>
	<ul>
		<c:forEach var="message" items="${errorMsgs}">
			<li style="color:red">${message}</li>
		</c:forEach>
	</ul>
</c:if>

<jsp:useBean id="mealSvc" scope="page" class="com.meal.model.MealService" />
<jsp:useBean id="mealtypeSvc" scope="page" class="com.meal_type.model.MealTypeJDBCDAO" />	

<FORM METHOD="post" ACTION="<%=request.getContextPath()%>/promdetail/promdetail.do" name="form1" >
<table>

	<tr>
		<td style="width:200px;">促銷商品(按住CTRL可多選):</td>
		<td>				
			<select size="5" name="mealno"  multiple="multiple" onclick="noCtrlSelect(this)">
				<c:forEach var="mealVO" items="${list}">
					<option value="${mealVO.mealno}">${mealVO.mealno}.${mealVO.meal_name}
				</c:forEach>
			</select>
		</td>
	</tr>
	
	<tr>
		<td style="width:150px;">促銷類型:</td>
		<td>
			<select size="1" name="promState" >
				<option value="1">1.百分比折扣</option>
				<option value="0">0.金額折扣</option>
			</select>
		</td>
	</tr>
	
	<tr>
		<td>折扣調整:</td>
		<td>
		<input type="number" name="promValues" min="1"  placeholder="請輸入折扣百分比/數字。"
			 ></input>
		</td>
	</tr>
	<tr>
		<td>活動開始日期:</td>
		<td>
			<input type="date" name="promTimeStart" id="time1">
		</td>
	</tr>
	<tr>
		<td>活動結束日期:</td>
		<td>
			<input type="date" name="promTimeEnd" id="time2">
		</td>
	</tr>
	<tr>
		<td>發送促銷信:</td>
		<td>
			<input type="checkbox" name="prommail" value="1" checked>新增完畢後發送促銷信息予客戶
		</td>
	</tr>
</table>
<br>

<input type="hidden" name="action" value="insert">
<input type="submit" value="送出"  class="icon">

</FORM>
</div>
<!-- 限制選擇日期 -->
<script>
var date = new Date();
var year = date.getFullYear();
var month = ('0'+ (date.getMonth() + 1)).slice(-2);
var day = ('0' + date.getDate()).slice(-2);
// var day = date.getDay();
var time = year +'-'+ month +'-'+ day;
console.log(time);
// 設定進入addpromdetail時日立預設之日期為當日
document.getElementById('time1').value = time;
document.getElementById('time2').value = time;
//限制不能选择今天之后的日期（加上属性max）
document.getElementById('time1').setAttribute('min', time);
document.getElementById('time2').setAttribute('min', time);
</script>
</body>

</html>