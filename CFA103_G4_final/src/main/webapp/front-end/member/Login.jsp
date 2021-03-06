<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>

<head>


<link rel="apple-touch-icon" type="image/png"
	href="https://cpwebassets.codepen.io/assets/favicon/apple-touch-icon-5ae1a0698dcc2402e9712f7d01ed509a57814f994c660df9f7a952f3060705ee.png" />
<meta name="apple-mobile-web-app-title" content="BIB">

<link rel="shortcut icon" type="image/bib.icon" href="bib.jpeg" />

<link rel="mask-icon" type="image/x-icon"
	href="https://cpwebassets.codepen.io/assets/favicon/logo-pin-8f3771b1072e3c38bd662872f6b673a722f4b3ca2421637d5596661b4e2132cc.svg"
	color="#111" />


<title>Login</title>
<link href="https://fonts.googleapis.com/css?family=Open+Sans:400,700"
	rel="stylesheet">



<style>
* {
	font-family: monospace;
	line-height: 150%;
	font-size: 14px;
}

/* config.css */
:root { -
	-baseColor: #606468;
}

/* helpers/align.css */
.align {
	display: grid;
	-webkit-box-align: center;
	-ms-flex-align: center;
	align-items: center;
	justify-items: center;
	place-items: center;
}

.grid {
	width: 90%;
	margin-left: auto;
	margin-right: auto;
	max-width: 20rem;
}

/* helpers/hidden.css */
.hidden {
	border: 0;
	clip: rect(0, 0, 0, 0);
	height: 1px;
	margin: -1px;
	overflow: hidden;
	padding: 0;
	position: absolute;
	width: 1px;
}

/* helpers/icon.css */
:root { -
	-iconFill: var(- -baseColor);
}

.icons {
	display: none;
}

.icon {
	height: 1em;
	display: inline-block;
	fill: #606468;
	fill: var(- -iconFill);
	width: 1em;
	vertical-align: middle;
}

/* layout/base.css */
:root { -
	-htmlFontSize: 100%; -
	-bodyBackgroundColor: #2c3338; -
	-bodyColor: var(- -baseColor); -
	-bodyFontFamily: "Open Sans"; -
	-bodyFontFamilyFallback: sans-serif; -
	-bodyFontSize: 0.875rem; -
	-bodyFontWeight: 400; -
	-bodyLineHeight: 1.5;
}

* {
	box-sizing: inherit;
}

html {
	-webkit-box-sizing: border-box;
	box-sizing: border-box;
}

body {
	background: url(<%=request.getContextPath()%>/front-end/pic/bricks.jpg);
	color: #606468;
	color: var(- -bodyColor);
	font-family: "Open Sans", sans-serif;
	font-family: var(- -bodyFontFamily), var(- -bodyFontFamilyFallback);
	font-size: 0.875rem;
	font-size: var(- -bodyFontSize);
	font-weight: 400;
	font-weight: var(- -bodyFontWeight);
	line-height: 1.5;
	line-height: var(- -bodyLineHeight);
	margin: 0;
	min-height: 100vh;
	background-size: cover;
}

/* modules/anchor.css */
:root { -
	-anchorColor: #eee;
}

a {
	color: black;
	color: var(- -anchorColor);
	outline: 0;
	text-decoration: none;
}

a:focus, a:hover {
	text-decoration: underline;
}

/* modules/form.css */
:root { -
	-formGap: 0.875rem;
}

input {
	border: 0;
	/* 	color: inherit; */
	font: inherit;
	margin: 0;
	outline: 0;
	padding: 0;
	-webkit-transition: background-color 0.3s;
	-o-transition: background-color 0.3s;
	transition: background-color 0.3s;
}

input[type="submit"] {
	cursor: pointer;
}

.form {
	display: grid;
	grid-gap: 0.875rem;
	gap: 0.875rem;
	grid-gap: var(- -formGap);
	gap: var(- -formGap);
}

.form input[type="password"], .form input[type="text"], .form input[type="submit"]
	{
	width: 100%;
}

.form__field {
	display: -webkit-box;
	display: -ms-flexbox;
	display: flex;
}

.form__input {
	-webkit-box-flex: 1;
	-ms-flex: 1;
	flex: 1;
}

/* modules/login.css */
:root {
	-loginBorderRadus: 0.25rem; -
	-loginColor: #eee; -
	-loginInputBackgroundColor: #3b4148; -
	-loginInputHoverBackgroundColor: #434a52; -
	-loginLabelBackgroundColor: #363b41; -
	-loginSubmitBackgroundColor: #8fa9ca; -
	-loginSubmitColor: #eee; -
	-loginSubmitHoverBackgroundColor: #8fa9ca;
	-
}

.login {
	color: #eee;
	color: var(- -loginColor);
}

.login label, .login input[type="text"], .login input[type="password"],
	.login input[type="submit"] {
	border-radius: 0.25rem;
	border-radius: var(- -loginBorderRadus);
	padding: 1rem;
}

.login label {
	border-bottom-right-radius: 0;
	border-top-right-radius: 0;
	padding-left: 1.25rem;
	padding-right: 1.25rem;
	border: 1px solid black;
}

.login input[type="password"], .login input[type="text"] {
	border-bottom-left-radius: 0;
	border-top-left-radius: 0;
	border: 1px solid black;
	background-color: transparent;
}

.login input[type="password"]:focus, .login input[type="password"]:hover,
	.login input[type="text"]:focus, .login input[type="text"]:hover {
	background-color: #FAF1E4;
	background-color: var(- -loginInputHoverBackgroundColor);
}

.login input[type="submit"] {
	background-color: #FAF1E4;
	color: black;
	color: var(- -loginSubmitColor);
	font-weight: 700;
	text-transform: uppercase;
	border: 1px solid black;
}

.login input[type="submit"]:focus, .login input[type="submit"]:hover {
	background-color: #D6ECCE;
	background-color: var(- -loginSubmitHoverBackgroundColor);
}

/* modules/text.css */
p {
	margin-top: 1.5rem;
	margin-bottom: 1.5rem;
	font-weight: bold;
}

.text--center {
	text-align: center;
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
<body class="align" style="text-align: center;">

	<c:if test="${not empty errorMsgs}">
		<font style="color: red">?????????????????????:</font>
		<ul>
			<c:forEach var="message" items="${errorMsgs}">
				<li style="color: red">${message}</li>
			</c:forEach>
		</ul>
	</c:if>
	<div class="grid">

		<form action="MemberServlet.do" method="POST" class="form login">

			<div class="form__field">
				<label for="login__username"><svg class="icon">
            <use xlink:href="#icon-user"></use>
          </svg><span class="hidden">Username</span></label> <input type="hidden"
					name="action" value="login"> <input id="login__username"
					type="text" autocomplete="off" name="maccount" class="form__input"
					placeholder="Username">
			</div>

			<div class="form__field">
				<label for="login__password"><svg class="icon">
            <use xlink:href="#icon-lock"></use>
          </svg><span class="hidden">Password</span></label><input type="hidden"
					name="action" value="login"> <input id="login__password"
					type="password" name="mpassword" class="form__input"
					placeholder="Password">
			</div>

			<div class="form__field">
				<input type="submit" type="hidden" name="action" value="??????">
			</div>

		</form>

		<p class="text--center">
			<a href="Signup.jsp">????????????</a>
			<svg class="icon">
        <use xlink:href="#icon-arrow-right"></use>
      </svg>
		</p>

		<p class="text--center">
			<a href="forgetpassword.jsp">????????????</a>
			<svg class="icon">
        <use xlink:href="#icon-arrow-right"></use>
      </svg>
		</p>

	</div>

	<svg xmlns="http://www.w3.org/2000/svg" class="icons">
    <symbol id="icon-arrow-right" viewBox="0 0 1792 1792">
      <path
			d="M1600 960q0 54-37 91l-651 651q-39 37-91 37-51 0-90-37l-75-75q-38-38-38-91t38-91l293-293H245q-52 0-84.5-37.5T128 1024V896q0-53 32.5-90.5T245 768h704L656 474q-38-36-38-90t38-90l75-75q38-38 90-38 53 0 91 38l651 651q37 35 37 90z" />
    </symbol>
    <symbol id="icon-lock" viewBox="0 0 1792 1792">
      <path
			d="M640 768h512V576q0-106-75-181t-181-75-181 75-75 181v192zm832 96v576q0 40-28 68t-68 28H416q-40 0-68-28t-28-68V864q0-40 28-68t68-28h32V576q0-184 132-316t316-132 316 132 132 316v192h32q40 0 68 28t28 68z" />
    </symbol>
    <symbol id="icon-user" viewBox="0 0 1792 1792">
      <path
			d="M1600 1405q0 120-73 189.5t-194 69.5H459q-121 0-194-69.5T192 1405q0-53 3.5-103.5t14-109T236 1084t43-97.5 62-81 85.5-53.5T538 832q9 0 42 21.5t74.5 48 108 48T896 971t133.5-21.5 108-48 74.5-48 42-21.5q61 0 111.5 20t85.5 53.5 62 81 43 97.5 26.5 108.5 14 109 3.5 103.5zm-320-893q0 159-112.5 271.5T896 896 624.5 783.5 512 512t112.5-271.5T896 128t271.5 112.5T1280 512z" />
    </symbol>
  </svg>

</body>




</html>

