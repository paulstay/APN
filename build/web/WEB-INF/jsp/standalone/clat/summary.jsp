<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%@ page import="com.estate.constants.*" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://jakarta.apache.org/taglibs/input-1.0" prefix="input"%>
<%@ taglib tagdir="/WEB-INF/tags/teag" prefix="teag"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>CLAT Summary</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link rel="stylesheet" type="text/css" href="<%=path%>/css/styles.css">
  </head>
  <body>
<%@include file="/inc/aesHeader.jspf" %>
  <h3 align='center'>Charitable Lead Annuity Trust</h3>
  <h4 align='center'>Summary</h4>
  <jsp:useBean id='userInfo' scope='session' type='com.teag.bean.PdfBean'/>
  <jsp:useBean id='clat' scope='session' type='com.estate.toolbox.Clat'/>
<table>
  	<tr>
  		<td valign='top'><%@include file='menu.jspf' %></td>
  		<td align='center' width='100%'>
			<table width='100%'>
				<tr>
					<td>Age(s) used for Calculation</td><td align='right'>(<fmt:formatNumber pattern="##" value="${clat.age1}"/>) (<fmt:formatNumber pattern="##" value="${clat.age2}"/>)</td>
					<td>&nbsp;</td>
					<td>Section 7520 Rate</td><td align='right'><fmt:formatNumber pattern="##.###%" value="${clat.irsRate}"/></td>
				</tr>
				<tr>
					<td>Beginning Principal</td><td align='right'><fmt:formatNumber pattern="$###,###,###" value="${clat.fmv }"/></td>
					<td>&nbsp;</td>
					<td>Transfer Date</td><td align='right'><fmt:formatDate pattern="M/d/yyyy" value="${clat.irsDate }"/></td>
				</tr>
				<tr>
					<td>Discounted Principal</td><td align='right'><fmt:formatNumber pattern="$###,###,###" value="${clat.discountValue }"/></td>
					<td>&nbsp;</td>
					<td>Remainder Interest</td><td align='right'><fmt:formatNumber pattern="$###,###,###" value="${clat.remainderInterest }"/></td>
				</tr>
				<tr>
					<td>Principal Growth Rate</td><td align='right'><fmt:formatNumber pattern="##.###%" value="${clat.assetGrowth }"/></td>
					<td>&nbsp;</td>
					<td>Annuity Factor</td><td align='right'><fmt:formatNumber pattern="##.####" value="${clat.annuityFactor }"/></td>
				</tr>
				<tr>
					<td>Principal Income Rate</td><td align='right'><fmt:formatNumber pattern="##.###%" value="${clat.assetIncome }"/></td>
					<td>&nbsp;</td>
					<td>Annuity Payment</td><td align='right'><fmt:formatNumber pattern="$###,###,###" value="${clat.annuityPayment }"/></td>
				</tr>
				<tr>
					<td>CLAT Term</td><td align='right'><fmt:formatNumber pattern="##" value="${clat.clatTermLength }"/></td>
					<td>&nbsp;</td>
					<td>Optimal Payment Rate</td><td align='right'><fmt:formatNumber pattern="##.###%" value="${clat.optimalRate }"/></td>
				</tr>
				<tr>
					<td>Charitable Deduction</td><td align='right'><fmt:formatNumber pattern="$###,###,###" value="${clat.charitableDeduction }"/></td>
					<td>&nbsp;</td>
					<td>Actual Payment Rate</td><td align='right'><fmt:formatNumber pattern="##.###%" value="${clat.payoutRate }"/></td>
				</tr>
				<tr>
					<td>Payment Frequency</td>
					<td align='right'>
						<c:choose>
							<c:when test="${clat.freq == 1 }">Annual</c:when>
							<c:when test="${clat.freq == 2 }">Semi Annual</c:when>
							<c:when test="${clat.freq == 4 }">Quarterly</c:when>
							<c:otherwise>Monthly</c:otherwise>
						</c:choose>
					</td>
					<td>&nbsp;</td>
					<td>Annuity Increase</td>
					<td align='right'><fmt:formatNumber pattern="###.###%" value="${clat.annuityIncrease}"/></td>
				</tr>
			</table>
			<br>
			<table>
				<tr>
					<th colspan='6' align='center'>Trust Balance</th>
				</tr>
				<tr>
					<th width='16%' align='right'>Beginning Balance</th>
					<th width='16%' align='right'>Growth</th>
					<th width='16%' align='right'>Income</th>
					<c:if test="${not clat.grantor }">
						<th width='16%' align='right'>Taxes</th>
					</c:if>
					<th width='16%' align='right'>Payment</th>
					<th width='16%' align='right'>Ending Balance</th>
				</tr>
				<c:set var="yr" value="0" />
				<c:set var="balance" value="${clat.fmv}"/>
				<c:set var="growth" value="${clat.assetGrowth }"/>
				<c:set var="income" value="${clat.assetIncome }"/>
				<c:set var="payment" value="${clat.annuityPayment }"/>
				<c:set var="taxes" value="0"/>
				<c:set var="sumTaxes" value="0"/>
				<c:forEach begin="1" end="${clat.clatTermLength}" >
					<tr>
						<td align='right'><fmt:formatNumber pattern="$###,###,###" value="${balance}"/></td>
						<td align='right'><fmt:formatNumber pattern="$###,###,###" value="${balance * growth }"/></td>
						<td align='right'><fmt:formatNumber pattern="$###,###,###" value="${balance * income }"/></td>
						<c:set var="sumTaxes" value="${sumTaxes + (balance * income * clat.taxRate) }"/>
						<c:if test="${not clat.grantor}">
							<c:set var='taxes' value='${balance * income * clat.taxRate }'/>
							<td align='right'><fmt:formatNumber pattern="$###,###" value="${taxes}"/></td>
						</c:if>
						<td align='right'><fmt:formatNumber pattern="$###,###,###" value="${payment }"/></td>
						<c:set var="balance" value="${balance + (balance*(growth+income)) - payment - taxes }"/>
						<td align='right'><fmt:formatNumber pattern="$###,###,###" value="${balance }"/></td>
					</tr>
					<c:set var="yr" value="${yr+1}" />
					<c:set var="payment" value="${payment * (1 +clat.annuityIncrease)}"/>
				</c:forEach>
				<tr>
					<td colspan='6'>&nbsp;</td>
				</tr>
				<tr>
					<td colspan='6' align='center'>
						Total to Family <fmt:formatNumber pattern="$###,###,###" value="${clat.netToFamily }"/>&nbsp;
					</td>
				</tr>
				<tr>
					<td colspan='6' align='center'>
						Total to Charity <fmt:formatNumber pattern="$###,###,###" value="${clat.netToCharity }"/>&nbsp;
					</td>
				</tr>
				<c:if test="${clat.grantor }">
				<tr>
					<td colspan='6' align='center'>Income Taxes paid by Grantor &nbsp;=&nbsp;<fmt:formatNumber pattern="$###,###,###" value="${sumTaxes}" />
					</td>
				</tr>
				</c:if>
			</table>
			
  		</td>
  	</tr>
  </table>
<%@include file="/inc/aesFooter.jspf" %>
  </body>
</html>
