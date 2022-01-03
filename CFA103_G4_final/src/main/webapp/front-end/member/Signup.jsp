<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.member.model.*"%>

<%
MemberVO memberVO = (MemberVO) request.getAttribute("memberVO");
%>




<!DOCTYPE html>
<html lang="en">

<head>

<meta charset="UTF-8">

<link rel="apple-touch-icon" type="image/png"
	href="https://cpwebassets.codepen.io/assets/favicon/apple-touch-icon-5ae1a0698dcc2402e9712f7d01ed509a57814f994c660df9f7a952f3060705ee.png" />
<meta name="apple-mobile-web-app-title" content="CodePen">

<link rel="shortcut icon" type="image/bib.icon" href="bib.jpeg" />

<link rel="mask-icon" type="image/x-icon"
	href="https://cpwebassets.codepen.io/assets/favicon/logo-pin-8f3771b1072e3c38bd662872f6b673a722f4b3ca2421637d5596661b4e2132cc.svg"
	color="#111" />


<title>SignUp</title>
<link rel="stylesheet"
	href="//maxcdn.bootstrapcdn.com/bootstrap/3.3.4/css/bootstrap.min.css">



<style>
* {
	box-sizing: border-box;
	font-family: monospace;
	line-height: 150%;
}

.form [class*='col'] {
	border: solid 1px #a0a0a0;
	border-top: 0;
	padding-left: 0;
	padding-right: 0;
	position: relative;
}

@media ( max-width : 991px) {
	.form .row:first-of-type [class*='col']:first-of-type {
		border-top: solid 1px #a0a0a0;
	}
}

@media ( min-width : 992px) {
	.form>.row:first-of-type>[class*='col'] {
		border-top: solid 1px #a0a0a0;
	}
	.form>div>[class*='col']+[class*='col'] {
		border-left: 0;
	}
}

.form label {
	color: black;
	font-size: 0.8em;
	position: absolute;
	left: 1em;
	top: 1em;
}

.form input[type="text"], .form input[type="password"] {
	background-color: transparent;
	border: 0;
	box-sizing: border-box;
	display: block;
	padding: 2.5em 1em 1em;
	width: 100%;
}

.form input[type="date"] {
	background-color: transparent;
	border: 0;
	box-sizing: border-box;
	display: block;
	padding: 1.45em 1em 1em;
	width: 100%;
}

.form input:focus {
	box-shadow: inset 0 0 5px #5b9dd9;
	outline: 0;
}

.form input::-webkit-input-placeholder {
	color: transparent;
}

.form input::-moz-placeholder {
	color: transparent;
}

.form input:-ms-input-placeholder {
	color: transparent;
}

.form input::input-placeholder {
	color: transparent;
}

/* Header / Footer */
.form header, .form footer {
	padding: 1em;
	width: 100%;
}

.signup {
	background-color: transparent;
}

.form header {
	font-size: 1.6em;
	text-align: center;
	font-weight: bold;
}

.form footer {
	box-sizing: border-box;
	border-collapse: separate;
	display: table;
	text-align: center;
}

.form footer>* {
	display: table-cell;
}

.form footer p {
	font-size: 0.8em;
}

.form footer button {
	background-color: rgb(255 255 255/ 15%);
	border: 0;
	color: black;
	font-size: 1em;
	margin: 0 0 0.5em 0.5em;
	padding: 0.5em 1em;
	font-weight: bold;
	border: 1px solid black;
}
/* Custom code for the demo */
html, body {
	height: 100%;
}

html {
	display: table;
	width: 100%;
}

html, button, input, select, textarea {
	color: black;
	font-family: "Segoe UI", Frutiger, "Frutiger Linotype", "Dejavu Sans",
		"Helvetica Neue", Arial, sans-serif;
}

body {
	display: table-cell;
	padding: 0 3rem;
	vertical-align: middle;
	background-color: #2c3338;
	color: #fcf8e3;
}

.form {
	max-width: 640px;
	margin: 0 auto;
}

h2, h4 {
	text-align: center;
	font-weight: bold;
	color: black;
}

</style>

<script>
	window.console = window.console || function(t) {
	};
</script>



<script>
	if (document.location.search.match(/type=embed/gi)) {
		window.parent.postMessage("resize", "*");
	}
</script>


</head>
<main>
	<body translate="no" style="text-align: center;">
		<%@ include file="/front-end/front-end_background.jsp"%>
		<h2>
			<a href="Login.jsp" style="color: black">Business Is Booming</a>
		</h2>
		<%-- 錯誤表列 --%>
		<c:if test="${not empty errorMsgs}">
			<font style="color: red">請修正以下錯誤:</font>
			<ul>
				<c:forEach var="message" items="${errorMsgs}">
					<li style="color: red">${message}</li>
				</c:forEach>
			</ul>
		</c:if>
		<FORM METHOD="post" ACTION="MemberServlet.do" name="form1">
			<div class="form">
				<h4>會員註冊</h4>
				<div class="row">
					<div class="col-md-6">
						<label>帳號</label> <input id="maccount" type="text"
							autocomplete="off" size="20" name="maccount"
							value="<%=(memberVO == null) ? "" : memberVO.getMaccount()%>"
							autofocus />
					</div>
					<div class="col-md-6">
						<label>密碼</label> <input id="mpassword" type="password" size="15"
							name="mpassword"
							value="<%=(memberVO == null) ? "" : memberVO.getMpassword()%>" />
					</div>
				</div>

				<div class="row">
					<div class="col-md-6">
						<label>姓名</label> <input id="mname" type="text" autocomplete="off"
							size="10" name="mname"
							value="<%=(memberVO == null) ? "" : memberVO.getMname()%>" />
					</div>
					<div class="col-md-6">
						<label>地址</label> <input id="address" type="text"
							autocomplete="off" size="30" name="address"
							value="<%=(memberVO == null) ? "" : memberVO.getAddress()%>" />
					</div>
				</div>

				<div class="row">
					<div class="col-md-6">
						<label>手機電話</label> <input id="phone" type="text"
							autocomplete="off" maxlength="10" name="phone"
							value="<%=(memberVO == null) ? "" : memberVO.getPhone()%>" />
					</div>
					<div class="col-md-6">
						<label>身分證字號</label> <input id="mem_id" type="text"
							autocomplete="off" maxlength="10" name="mem_id"
							value="<%=(memberVO == null) ? "" : memberVO.getMem_id()%>" />
					</div>
				</div>

				<div class="row">
					<div class="col-md-6">
						<label>生日</label> <input id="Bday" type="date" name="bday"
							value="<%=(memberVO == null) ? "" : memberVO.getBday()%>" />
					</div>
					<div class="col-md-6">
						<label>E-MAIL</label> <input id="mem_email" type="text"
							autocomplete="off" maxlength="50" name="mem_email"
							value="<%=(memberVO == null) ? "" : memberVO.getMem_email()%>" />
					</div>
				</div>
				<br>
				<footer class="signup">
					<button type="submit" type="hidden" name="action" value="signup" />
					Sign Up
					</button>
				</footer>
			</div>
		</FORM>
</main>
<%@ include file="/front-end/front-end_footer.jsp"%>

</body>

</html>


