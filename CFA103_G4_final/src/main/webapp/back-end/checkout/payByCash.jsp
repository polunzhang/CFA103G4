<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.live_order.model.*"%>
<%@ page import="com.online_order.model.*"%>
<aside onload="connect();" onunload="disconnect();" >
    <%@ include file="/back-end/sidebar.jsp"%>
</aside>
<c:if test="${liveorderVO != null}">
<%
  LiveOrderService liveorderSvc = new LiveOrderService();
  LiveOrderVO liveorderVO = (LiveOrderVO) request.getAttribute("liveorderVO"); //EmpServlet.java(Concroller), 存入req的empVO物件
//   System.out.println(liveorderVO);
  System.out.println(1111);
  Integer liveno = Integer.valueOf(request.getParameter("liveno"));
  liveorderVO = liveorderSvc.getOneByLiveNO(liveno);
//   System.out.println("tableno"+tableno);
%>
</c:if>
<c:if test="${onlineorderVO != null}">
<%
System.out.println(2222);
  OnlineOrderService onlineorderSvc = new OnlineOrderService();
  OnlineOrderVO onlineorderVO = (OnlineOrderVO) request.getAttribute("onlineorderVO"); //EmpServlet.java(Concroller), 存入req的empVO物件
  Integer olno = Integer.valueOf(request.getParameter("olno"));
  onlineorderVO = onlineorderSvc.getOneOnlineOrder(olno);
//   System.out.println("tableno"+tableno);
%>
</c:if>
<!DOCTYPE html>
<html>
<head>
<meta charset="BIG5">
<title>Insert title here</title>
<style>
	html, body, div, span, applet, object, iframe,
	h1, h2, h3, h4, h5, h6, p, blockquote, pre,
	a, abbr, acronym, address, big, cite, code,
	del, dfn, em, font, img, ins, kbd, q, s, samp,
	small, strike, strong, sub, sup, tt, var,
	b, u, i, center,
	dl, dt, dd, ol, ul, li,
	fieldset, form, label, legend,
	table, caption, tbody, tfoot, thead, tr, th, td {
		margin: 0;
		padding: 0;
		border: 0;
		outline: 0;
		font-size: 16px;
		vertical-align: baseline;
		background: transparent;

	}
	body {
		line-height: 1;
	}
	ul {
		list-style: none;
	}

	body {
		width: 500px;
	}
	
	form {
		text-align: center;
		padding: 7px;
		bottom: 0;
		right: 0;
		position: relative;
		top: 50%;
		left: 50%;
	}

	#display1 {
		width: 98%;
		height: 30px;
		text-align: right;
		font-size: 1.5rem;
	}
	#display2 {
		width: 98%;
		height: 30px;
		text-align: right;
		font-size: 1.5rem;
	}
	#display3 {
		width: 98%;
		height: 30px;
		text-align: right;
		font-size: 1.5rem;
	}
	.digit {
		font-size: 2rem;
		background-color: white;
		height: 55px;
		width: 20%;
		border-radius: 5px;
		display: inline-block;
		padding: 5px;
	}
	.oper {
		font-size: 2rem;
		background-color: yellow;
		height: 55px;
		width: 20%;
		border-radius: 5px;
		display: inline-block;
		padding: 5px;
	}
	#clearMem {
		background-color: red;
	}
	main{
    position: absolute;
    top: 10%;
    margin-left: 200px;
}
</style>

</head>
<body>
<%@ include file="/back-end/background.html"%>
<main>
<form name="case">
    <div>
    <c:if test="${liveorderVO != null}">
		總金額：<input name="display" id="display1" value="${liveorderVO.total}">
	</c:if>
    <c:if test="${onlineorderVO != null}">
		總金額：<input name="display" id="display1" value="${onlineorderVO.total}">
	</c:if>
	</div>
	<div>
		現金：<input  name="display" id="display2" value="">
	</div>
	<div>
		找零：<input name="display" id="display3" value="">
    </div>
        <br>
		<br>
		<input type="button" class="digit" value="1" onclick="run1()">
		<input type="button" class="digit" value="2" onclick="run2()">
		<input type="button" class="digit" value="3" onclick="run3()">

		<input type="button" class="digit" value="4" onclick="run4()">
		<input type="button" class="digit" value="5" onclick="run5()">
		<input type="button" class="digit" value="6" onclick="run6()">

		<input type="button" class="digit" value="7" onclick="run7()">
		<input type="button" class="digit" value="8" onclick="run8()">
		<input type="button" class="digit" value="9" onclick="run9()">

		<input type="button" class="digit" value="0" onclick="run0()">
		<input type="button" id="clearMem" class="oper" value="C" onclick="runC()">


		<input type="button" id="enter" class="oper" value="Enter">
		<br>
		<br>
	<FORM METHOD="post" ACTION="<%=request.getContextPath()%>/back-end/checkout/checkout.do" style="margin-bottom: 0px;">
		<c:if test="${liveorderVO != null}">
		<input type="hidden" name="liveno"  value="${liveorderVO.liveno}">
		<input type="hidden" name="tableno"  value="${liveorderVO.tableno}">
		<input type="hidden" name="order"  value="live">
		</c:if>
		<c:if test="${onlineorderVO != null}">
		<input type="hidden" name="olno"  value="${onlineorderVO.olno}">
		<input type="hidden" name="order"  value="online">
		</c:if>
		<input type="submit" value="完成結帳">
		<input type="hidden" name="action"	value="payByCash">
	</FORM>

	</div> <!--  END opercase --> 


</div><!-- END of case  -->
<script>
let display1 =document.querySelector("#display1");
let display2 =document.querySelector("#display2");
let display3 =document.querySelector("#display3");
let enter =document.querySelector("#enter");

	function run1(){
		document.case.display2.value += "1";
	};
	function run2(){
		document.case.display2.value += "2";
	};
	function run3(){
		document.case.display2.value += "3";
	};
	function run4(){
		document.case.display2.value += "4";
	};
	function run5(){
		document.case.display2.value += "5";
	};

	function run6(){
		document.case.display2.value += "6";
	};

	function run7(){
		document.case.display2.value += "7";
	};

	function run8(){
		document.case.display2.value += "8";
	};

	function run9(){
		document.case.display2.value += "9";
	};

	function run0(){
		document.case.display2.value += "0";
			
	};

	function runC(){
		document.case.display2.value = "";
	};

	enter.addEventListener("click",function(){
	display3.value=display2.value-display1.value;
		
	})
	
	
</script>
</main>
</body>
</html>