<%@ page language="java" import='com.teag.bean.*' pageEncoding="ISO-8859-1"%>
<%@ page import='java.util.*' %>
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
    <title>Variable Line Items</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<link href="<%=path %>/css/styles.css" rel="stylesheet" media="screen">
<body>
<jsp:useBean id='person' class='com.teag.bean.PersonBean' scope='session' />
<%@ include file="/inc/aesHeader.jspf" %>
<%@ include file="/inc/aesQuickJump.jspf" %>
<h3 align='center'> Asset Data for ${person.firstName } ${person.lastName}</h3>
<h3 align='center'>Variable Line Items</h3>
<!-- Add asset data from array list and put in request-->
<c:set var="inc" value="0" />
<table align='center' width='80%'>
<tr class='bg-color6'>
	<td align='center'>Description</td>
	<td align='center'>Starting Year</td>
	<td align='center'>Ending Year</td>
	<td align='center'>Value</td>
	<td align='center'>Growth</td>
	<td align='center'>% Taxable</td>
	<td align='center'>Scenario Use</td>
	<td align='center'></td>
</tr>
<c:forEach var="s" items="${cList}" >
<c:if test="${ s.cfFlag == 'C'}">
<tr class="l-color${inc%2}">
		<td align='right'>${s.description}</td>
		<td align='right'><fmt:formatNumber value='${s.startYear}' pattern='####'/></td>
		<td align='right'><fmt:formatNumber value='${s.endYear}' pattern='####' /></td>
		<td align='right'><fmt:formatNumber type='currency' value='${s.value}' /></td>
		<td align='right'><fmt:formatNumber pattern='###.####%' value='${s.growth}' /></td>
		<td align='right'><fmt:formatNumber pattern='###.####%' value='${s.prctTax}' /></td>
		<td align='right'>${s.useFlag}</td>
		<td align='right'>
			<a href="servlet/VcfCashReceiptsServlet?id=${s.id}&action=Edit">Edit</a>
		</td>
</tr>
<c:set var="inc" value="${inc + 1}" />
</c:if>
</c:forEach>
</table>
<table align='center'>
<tr>
	<td colspan='8' align='center'>
		<teag:form name='aform' action='servlet/VcfCashReceiptsServlet?action=Add&id=0' method='post'>
			<input type='submit' name="btn" value='Add' />
		</teag:form>
	</td>
</tr>
</table>
<br>
<div align='center'>
<a href='servlet/VCFItemServlet'>Back to Variable Line Items</a>
</div>
<%@ include file="/inc/aesFooter.jspf" %>
</body>
</html>
