<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.meal.model.*"%>

<%
  MealVO mealVO = (MealVO) request.getAttribute("mealVO");
%>


<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
<title>餐點資料新增</title>
<link href='https://maxcdn.bootstrapcdn.com/font-awesome/4.5.0/css/font-awesome.min.css' rel='stylesheet'></link>
<style>
  table#table-1 {
	background-color: #CCCCFF;
    border: 2px solid black;
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

  table {
	width: 450px;
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
	top: 30px;
	left: 130px;
	z-index: 1;
	width: 87.5%;
	height: 94%;
	padding: 15px;
	/* 	border: 2px solid red; */
}
.icon{
  border:0;
  background-color:#003C9D;
  color:#fff;
  border-radius:10px;
  cursor:pointer;
}
.icon:hover{
  color:#003C9D;
  background-color:#fff;
  border:1px #003C9D solid;
}
a{ 
	text-decoration:none;
} 
a:hover{ 
	text-decoration:underline;
}
</style>

<style type="text/css">
	.container{
		border: 1px solid black;
		padding: 10px;
		margin: auto;
		overflow: auto;
	}
	.row{
		padding: 4px;
	}
	.row label {
		font-weight: 500;
	}
	.preview img {
		height: 200px;
		width: auto;
	}
	#preview div {
	position: absolute;
	top: 30px;
	right: 100px;
	}
	#homelogo{
	width:15px; 
	height:15px;
	}
</style>


</head>
<body class="preview">
<!-- include側邊欄(sidebar) -->
<aside onload="connect();" onunload="disconnect();" >
		<%@ include file="/back-end/sidebar.jsp"%>
</aside>
<!-- include背景圖片(正常統一版) -->
		<%@ include file="/back-end/meal/background/background.html"%>


<div class="preview">
<header id="table-1" >
	
		 <h3>餐點資料新增</h3>
		 <h4>
		 <a href="<%=request.getContextPath()%>/back-end/meal/select_page.jsp">
		 <img id="homelogo" src="<%=request.getContextPath()%>/back-end/meal/pic/images/mealHome.svg"
						width="15" height="15" border="0">返回餐點管理頁面
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

<FORM METHOD="post" ACTION="<%=request.getContextPath()%>/meal/meal.do" name="form1" enctype="multipart/form-data">

<table>
	<tr>
		<td>餐點種類: <%--<font color=red><b>*</b></font> --%>  </td>
		<td><select size="1" name="meal_type_no" style="background-color:#FFFF96;border: 2px solid black;">
			<c:forEach var="mealtypeVO" items="${mealtypeSvc.all}">
				<option value="${mealtypeVO.meal_type_no}" >${mealtypeVO.meal_type_name}
			</c:forEach>
		</select></td>	
	</tr>
	
	<tr>
		<td>餐點價格:</td>
		<td><input type="TEXT" name="meal_price" size="45" 
			placeholder="請輸入餐點價格，須為正整數。"
			style="background-color:#FFFF96;"/></td>
	</tr>
	
	<tr>
		<td>餐點名稱:</td>
		<td><input type="TEXT" name="meal_name" size="45" style="background-color:#FFFF96;"
			 placeholder="請輸入餐點名稱，須為繁體中文。" />
			 </td>
	</tr>
	
	<tr>
		<td>餐點介紹:</td>
		<td><input type="TEXT" name="meal_intro" size="45" style="background-color:#FFFF96;"
			 placeholder="請輸入餐點介紹。" />
			 </td>
	</tr>
	<tr>
		<td>圖片新增:</td>
		<td>
			<div class="row"><input type="file" name="upmealfile" id="myFile" accept="image/gif, image/jpeg, image/png"></div>
			<div class="row">
			<div id="preview"></div>
			</div>
		</td>
	</tr>
	
</table>

<br>

<input type="hidden" name="action" value="insert">
<input type="submit" value="送出新增"  class="icon">

</FORM>
<script src="http://code.jquery.com/jquery-1.12.4.min.js"></script>
<script type="text/javascript">
	// 請製作一個可上傳多張圖片的程式碼，並且可以預覽，並可以指定圖片並刪除。 (Event, array, DOM, FileReader ) (HW22 學號 .html)

	function init(){
		//	抓取DOM元素
		let myFile = document.getElementById("myFile");
		let preview = document.getElementById("preview");


		//	註冊檔案上傳後事件
		myFile.addEventListener('change',function(e){
			
			//	抓取觸發事件當下目標的檔案
			let files = e.target.files;
			//	可寫成 if (files !== null ) {}
			if (files){
				//	取出files陣列中的元素
				for (let i = 0 ; i< files.length;i++){
					let file = files[i];
					//	如果file檔案有image字串，若沒有則回傳-1
					if (file.type.indexOf('image')>-1){
						//	創建一FileReader物件，設定要讀取的檔案源頭，並註冊註冊load事件
						let reader = new FileReader();
						reader.readAsDataURL(file);
						reader.addEventListener('load',function(e){
							//	取得觸發load事件的物件，並取得result，此為一URL
							let result = e.target.result;
							//	創建一img標籤，並將取得之URL指定給img.src
							let img = document.createElement('img');
							img.src = result;
							//	創建一div標籤，並且設定innerHTML，實現checkbox在img之上，並且透過css語法，讓每個div可以由左往右排列
							let div = document.createElement('div')
							preview.innerHTML=``
							div.innerHTML=`預覽圖片<br>`
							//	在div標籤後加入img標籤(div>input>br>img)
							div.append(img);
							//	創建一label標籤，之後包覆在div標籤，讓點擊整個div區域可以直接選取checkbox，避免手殘
							let label = document.createElement('label');
							label.append(div);
							//	添加到preview區域
							preview.append(label);
						})
					}	
				}
			}else {
				alert("請上傳正確的格式!");
			}
		})

		//	設定一陣列，存放要刪除的元素
		let deleteArr=[];
		//	註冊preview的click事件，判斷是否是checkBox，並且該是否處於被選擇狀態。如果是則加入deleteArr中
		preview.addEventListener('click',function(e){
			if (e.target.getAttribute('class')==='checkBox'&& e.target.checked){
				deleteArr.push(e.target.parentElement);
			}
		})

		// 抓取DOM元素，並且註冊click事件，將deleteArr的元素全數刪除
		let deleteButton = document.getElementById('deleteButton');
		deleteButton.addEventListener('click',function(){
			for (let i = 0 ; i < deleteArr.length ;i++){
				// deleteArr[i].remove();
				deleteArr[i].remove();
			}
		})

	}

	window.onload = init;
</script>
</div>
</body>

</html>