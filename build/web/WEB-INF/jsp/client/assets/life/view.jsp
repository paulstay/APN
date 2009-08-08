<%@ page language="java" import="com.teag.bean.*" pageEncoding="ISO-8859-1"%>
<%@ page import="com.estate.constants.*"  %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="teagtags" prefix="teag" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<! DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<base href="<%=basePath%>">
    <title>Life Insurance</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<link href="<%=path%>/css/styles.css" rel="stylesheet" media="screen">
<body>
<jsp:useBean id="life" scope="request" type="com.teag.bean.InsuranceBean" />
<%@ include file="/inc/aesHeader.jspf" %>
<h3 align='center'> Life Insurance Details</h3>
<br>
<table align='center' width='90%'>
<tr>
	<td align='right' width='50%'>Insured : </td>
	<td align='left' width='50%'>${life.insured}</td>
</tr>
<tr>
	<td align="right">Owner : </td>
	<td align="left">${life.owner}</td>
</tr>
<tr>
	<td align="right">Named Beneficiary : </td>
	<td align="left">${life.beneficiary}</td>
</tr>
<fmt:parseDate var="d" value="${life.dateAcquired}" pattern="M/d/yyyy" />
<fmt:formatDate var="d2" value="${d2}" pattern="M/d/yyyy" />
<tr>
	<td align="right">Date Aquired : </td>
	<td align="left">${d2}</td>
</tr>
<tr>
	<td align="right">Insurance Company : </td>
	<td align="left">${life.insuranceCompany}</td>
</tr>
<tr>
	<td align="right">Company Policy Number : </td>
	<td align="left">${life.policyNumber}</td>
</tr>
<tr>
	<td align="right">Face Value : </td>
	<td align="left"><fmt:formatNumber pattern="###,###,###" value="${life.faceValue}"/></td>
</tr>
<tr>
	<td align="right">Current Cash Value : </td>
	<td align="left"><fmt:formatNumber pattern="###,###,###" value="${life.value}"/></td>
</tr>
<tr>
	<td align="right">Approximate Cash Value in 10 years : </td>
	<td align="left">
		<fmt:formatNumber pattern="###,###,###" value="${life.futureCashValue}" />
	</td>
</tr>
<tr>
	<td align='right'>Policy Type : </td>
	<td align='left'><teag:life value="${life.policyTypeId}"/></td>
</tr>
<fmt:formatNumber var='p' type='currency' value='${life.premiums}'/>
<tr>
	<td align="right">Preium Payment : </td>
	<td align="left">${p}</td>
</tr>
<tr>
	<td align="right">Premium Frequency : </td>
	<td align="left">${life.premiumFreqId}</td>
</tr>
<fmt:formatNumber var='loan' value='${life.loans }' />
<tr>
	<td align="right">Loans : </td>
	<td align="left">${loan}</td>
</tr>
<tr>
	<td align='right'>Is Life Death Benefit inclusive or in addition to the cash value? : </td>
	<td align='left'>${life.taxableLifeInc }</td>
</tr>
<tr>
	<td align="right">Notes : </td>
	<td align="left">${life.notes }</td>
</tr>
<tr>
	<td align='right'>
		<teag:form name="bform" action="servlet/LifeInsAssetServlet" method="post">
			<input type="hidden" name="action" value="list"/>		
			<input type='submit' value="Back"/>
		</teag:form>
	</td>
	<td align='left'>
		<teag:form name="eform" action="servlet/LifeInsAssetServlet" method="post">
			<input type="hidden" name="action" value="edit"/>		
			<input type="hidden" name="id" value="${life.id }"/>		
			<input type='submit' value="Edit"/>
		</teag:form>
	</td>
</tr>
</table>
<%@ include file="/inc/aesFooter.jspf" %>
</body>
</html>
