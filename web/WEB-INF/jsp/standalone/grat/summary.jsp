<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">

    <title>User Information</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link rel="stylesheet" type="text/css" href="<%=path%>/css/styles.css">
  </head>
  <body>
	<%@include file="/inc/aesHeader.jspf" %>
  <h3 align='center'>Grantor Retained Annuity Trust</h3>
  <h4 align='center'>Summary</h4>
  <jsp:useBean id='userInfo' scope='session' type='com.teag.bean.PdfBean'/>
  <jsp:useBean id='grat' scope='session' type='com.estate.controller.GratController'/>
<table>
  	<tr>
  		<td><%@include file='menu.jspf' %></td>
  		<td align='center' width='100%'>
			<table width='100%'>
				<tr>
					<td>${userInfo.firstName }'s Age</td><td align='right'><fmt:formatNumber pattern="##" value="${userInfo.age }"/></td>
					<td>&nbsp;</td>
					<td>Section 7520 Rate</td><td align='right'><fmt:formatNumber pattern="##.###%" value="${grat.irsRate }"/></td>
				</tr>
				<tr>
					<td>Beginning Principal</td><td align='right'><fmt:formatNumber pattern="$###,###,###" value="${grat.fmv }"/></td>
					<td>&nbsp;</td>
					<td>Transfer Date</td><td align='right'><fmt:formatDate pattern="M/d/yyyy" value="${grat.irsDate }"/></td>
				</tr>
				<tr>
					<td>Discounted Principal</td><td align='right'><fmt:formatNumber pattern="$###,###,###" value="${grat.discountValue }"/></td>
					<td>&nbsp;</td>
					<td>Remainder Interest</td><td align='right'><fmt:formatNumber pattern="$###,###,###" value="${grat.remainderInterest }"/></td>
				</tr>
				<tr>
					<td>Principal Growth Rate</td><td align='right'><fmt:formatNumber pattern="##.###%" value="${grat.assetGrowth }"/></td>
					<td>&nbsp;</td>
					<td>Annuity Factor</td><td align='right'><fmt:formatNumber pattern="##.####" value="${grat.annuityFactor }"/></td>
				</tr>
				<tr>
					<td>Principal Income Rate</td><td align='right'><fmt:formatNumber pattern="##.###%" value="${grat.assetIncome }"/></td>
					<td>&nbsp;</td>
					<td>Annuity Payment</td><td align='right'><fmt:formatNumber pattern="$###,###,###" value="${grat.annuityPayment }"/></td>
				</tr>
				<tr>
					<td>Grat Term</td><td align='right'><fmt:formatNumber pattern="##" value="${grat.term }"/></td>
					<td>&nbsp;</td>
					<td>Optimal Payment Rate</td><td align='right'><fmt:formatNumber pattern="##.###%" value="${grat.optimalPaymentRate }"/></td>
				</tr>
				<tr>
					<td>Annuity Interest</td><td align='right'><fmt:formatNumber pattern="$###,###,###" value="${grat.annuityInterest }"/></td>
					<td>&nbsp;</td>
					<td>Actual Payment Rate</td><td align='right'><fmt:formatNumber pattern="##.###%" value="${grat.payoutRate }"/></td>
				</tr>
			</table>
			<br>
			<table width='90%' border='1'>
				<tr>
					<th>Beginning Balance</th>
					<th>Growth</th>
					<th>Income</th>
					<th>Grat Payment</th>
					<th>Ending Balance</th>
				</tr>
				<c:set var="yr" value="0" />
				<c:forEach begin="1" end="${grat.term}" >
					<tr>
						<td align='right'><fmt:formatNumber pattern="$###,###,###" value="${grat.balance[yr] }"/></td>
						<td align='right'><fmt:formatNumber pattern="$###,###,###" value="${grat.growth[yr] }"/></td>
						<td align='right'><fmt:formatNumber pattern="$###,###,###" value="${grat.income[yr] }"/></td>
						<td align='right'><fmt:formatNumber pattern="$###,###,###" value="${grat.annuityPayment }"/></td>
						<td align='right'><fmt:formatNumber pattern="$###,###,###" value="${grat.balance[yr] + grat.growth[yr] + grat.income[yr] - grat.annuityPayment }"/></td>
					</tr>
					<c:set var="yr" value="${yr+1}" />
				</c:forEach>
			</table>
  		</td>
  	</tr>
  </table>
	<%@include file="/inc/aesFooter.jspf" %>
  </body>
</html>
