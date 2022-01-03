<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.live_order.model.*"%>
<aside onload="connect();" onunload="disconnect();" >
    <%@ include file="/back-end/sidebar.jsp"%>
</aside>
<%@ include file="/back-end/background.html"%>
<%
  LiveOrderService liveorderSvc = new LiveOrderService();
  Integer liveno = Integer.valueOf(request.getParameter("liveno"));
  LiveOrderVO liveorderVO = new LiveOrderVO();
  liveorderVO = liveorderSvc.getOneByLiveNO(liveno);
//   System.out.println("liveno=" + liveno);
//   System.out.println("${liveorderSvc.getAll().size()}=" + liveorderSvc.getAll().size());
%>


<html>
<head>
<title>listOneDetail.jsp</title>

<style>
  table#table-1 {
    color: black;
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
</style>

<style>
  table {
	width: 800px;
	margin-top: 5px;
	margin-bottom: 5px;
  }
  table, th, td {
    border: 1px solid black;
  }
  th, td {
    padding: 5px;
    text-align: center;
  }
  main{
    position: absolute;
    top: 3.5%;
    left: 12.5%;
    height: calc(100vh - 100px);
    width: calc(100% - 200px);
    margin-left: 200px;
}
</style>

</head>
<body>
<main>
<table id="table-1">
	<tr><td>
		 <h3>結帳畫面</h3>
	</td></tr>
</table>

<table>
    <tr>
		<th>現場訂單編號</th>
		<th>桌號</th>
		<th>結帳狀態</th>
		<th>訂單成立日期</th>
		<th>訂單總金額</th>
		<th>付款方式</th>
		<th>結帳</th>
	</tr>
	<tr>
		<td><%=liveorderVO.getLiveno()%></td>
		<td><%=liveorderVO.getTableno()%></td>
		<td><%=liveorderVO.getPay_status()%></td>
		<td><%=liveorderVO.getCreate_time()%></td>
		<td><%=liveorderVO.getTotal()%></td>
	<FORM METHOD="post" ACTION="<%=request.getContextPath()%>/back-end/checkout/checkout.do" style="margin-bottom: 0px;">
		<td>
		    <input type="radio" name="pay_way" value="0" checked />現金
		    <input type="radio" name="pay_way" value="1" />信用卡
		</td>	
		<td>
		    <input type="submit" value="付款去">
		    <input type="hidden" name="liveno" value="<%=liveorderVO.getLiveno()%>">
			<input type="hidden" name="action" value="liveOrder_Checkout">
		</td>
	</FORM>
	</tr>
	</table>
</main>
</body>
</html>