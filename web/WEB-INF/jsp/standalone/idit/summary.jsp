<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%@ page import="com.estate.constants.*" %>
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
    
    <title>IDIT Summary</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link rel="stylesheet" type="text/css" href="<%=path%>/css/styles.css">
  </head>
  <body>
	<%@include file="/inc/aesHeader.jspf" %>
  <h3 align='center'>Intentionally Defective Irrevocable Trust</h3>
  <h4 align='center'>Summary</h4>
  <jsp:useBean id='userInfo' scope='session' type='com.teag.bean.PdfBean'/>
  <jsp:useBean id='idit' scope='session' type='com.estate.toolbox.Idit'/>
<table>
  	<tr>
  		<td valign='top'><%@include file='menu.jspf' %></td>
  		<td align='center' width='100%'>
			<table width='60%' align='center'>
				<tr>
					<td>Asset Value</td>
					<td>&nbsp;</td>
					<td align='right'><fmt:formatNumber pattern="$###,###,###" value="${idit.fmv }"/></td>
				</tr>
				<tr>
					<td>Discount</td>
					<td>&nbsp;</td>
					<td align='right'><fmt:formatNumber pattern="##.###%" value="${idit.discount }"/></td>
				</tr>
				<tr>
					<td>Initial Gift</td>
					<td>&nbsp;</td>
					<td align='right'><fmt:formatNumber pattern="$###,###,###" value="${idit.giftAmount}"/></td>
				</tr>
				<tr>
					<td>Note Value</td>
					<td>&nbsp;</td>
					<td align='right'><fmt:formatNumber pattern="$###,###,###" value="${idit.dmv }"/></td>
				</tr>
				<tr>
					<td>Note Term</td>
					<td>&nbsp;</td>
					<td align='right'><fmt:formatNumber pattern="###" value="${idit.noteTerm }"/></td>
				</tr>
				<tr>
					<td>Note Rate</td>
					<td>&nbsp;</td>
					<td align='right'><fmt:formatNumber pattern='##.###%' value='${idit.noteRate }'/></td>
				</tr>
				<tr>
					<td>Note Payment Strucuture</td>
					<td>&nbsp;</td>
					<td align='right'>
						<c:choose>
							<c:when test="${idit.noteType == 0 }">
								Level Premium + Interest
							</c:when>
							<c:when test="${idit.noteType == 1 }">
								Amoritized
							</c:when>
							<c:when test="${idit.noteType == 2 }">
								Interest + Balloon
							</c:when>
						</c:choose>
					</td>
				</tr>
				<tr>
					<td>Life Insurance Death Benefit</td>
					<td>&nbsp;</td>
					<td align='right'><fmt:formatNumber pattern='$###,###,###' value='${idit.lifeDeathBenefit }'/></td>
				</tr>
				<tr>
					<td>Life Insurance Premium</td>
					<td>&nbsp;</td>
					<td align='right'><fmt:formatNumber pattern='$###,###,###' value='${idit.lifePremium }'/></td>
				</tr>
				<tr>
					<td>Number of Years to Pay Premium</td>
					<td>&nbsp;</td>
					<td align='right'><fmt:formatNumber pattern='###' value='${idit.lifePremiumYears }'/></td>
				</tr>
			</table>
			<br>
			<table border='1'>
				<tr>
					<th width='5%'>Year</th>
					<th width='15%'>Beginning Balance</th>
					<th width='15%'>Growth</th>
					<th width='15%'>Income</th>
					<th width='15%'>Payment</th>
					<th width='15%'>Premium</th>
					<th width='15%'>Ending Balance</th>
					<th width='15%'>Note Balance</th>
				</tr>
				<c:set var="yr" value="0" />
				<c:set var="balance" value="${idit.fmv + idit.giftAmount}"/>
				<c:set var="growth" value="${idit.assetGrowth }"/>
				<c:set var="income" value="${idit.assetIncome }"/>
				<c:forEach begin="1" end="${idit.noteTerm}" >
					<tr>
						<c:set var="payment" value="${idit.notePayment[yr] }"/>
						<td align='center'>${yr+1}</td>
						<td align='right'><fmt:formatNumber pattern="$###,###,###" value="${idit.balance[yr]}"/></td>
						<td align='right'><fmt:formatNumber pattern="$###,###,###" value="${idit.growth[yr] }"/></td>
						<td align='right'><fmt:formatNumber pattern="$###,###,###" value="${idit.income[yr] }"/></td>
						<td align='right'><fmt:formatNumber pattern="$###,###,###" value="${idit.notePayment[yr] }"/></td>
						<td align='right'><fmt:formatNumber pattern="$###,###,###" value="${idit.lifePayment[yr] }"/></td>
						<td align='right'><fmt:formatNumber pattern="$###,###,###" value="${idit.endBalance[yr] }"/></td>
						<td align='right'><fmt:formatNumber pattern="$###,###,###" value="${idit.noteBalance[yr] }"/></td>
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
