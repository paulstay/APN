<%@ page language="java" import='com.teag.bean.*'
	pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.*"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="teagtags" prefix="teag"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<! DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<base href="<%=basePath%>">
<title>Select Client</title>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<link href="<%=path %>/css/styles.css" rel="stylesheet" media="screen">
<body>
<jsp:useBean id='person' class='com.teag.bean.PersonBean'
	scope='session' />
<%@ include file="/inc/aesHeader.jspf"%>
<h3 align='center'>Asset Data for ${person.firstName } ${person.lastName}</h3>
<h3 align='center'>Notes Payable</h3>
<!-- Add asset data from array list and put in request-->
<c:set var="inc" value="0" />
<table align='center' width='80%'>
	<tr class='bg-color6'>
		<td align='center'>Description</td>
		<td align='center'>Ownership</td>
		<td align='center'>Title</td>
		<td align='center'>Amount</td>
		<td align='center'>Interest Rate</td>
		<td align='center'>Payments/Year</td>
		<td align='center'>Term</td>
		<td align='center'></td>
	</tr>
	<c:forEach var="s" items="${cList}">
		<tr class="l-color${inc%2}">
			<td align='right'>${s.description}</td>
			<td align='right'><teag:ownership value="${s.ownershipId}" /></td>
			<td align='right'><teag:title value="${s.titleId}" /></td>
			<td align='right'><fmt:formatNumber pattern="$###,###,###"
				value='${s.loanAmount}' /></td>
			<td align='right'><fmt:formatNumber type='percent' pattern="###.##%"
				value='${s.interestRate}' /></td>
			<td align='right'>${s.years}</td>
			<td align='right'><a
				href="servlet/NotesPayAssetServlet?id=${s.id}&action=View">View</a> <a
				href="servlet/NotesPayAssetServlet?id=${s.id}&action=Edit">Edit</a></td>
		</tr>
		<c:set var="inc" value="${inc + 1}" />
	</c:forEach>
</table>
<table align='center'>
	<tr>
		<td colspan='8' align='center'>
		<teag:form name='aform' action='servlet/NotesPayAssetServlet' method='post'>
			<input type="hidden" name="action" value="add"/>
			<input type="hidden" name="id" value="0"/>
			<input type='submit' name="btn" value='Add' />
		</teag:form></td>
	</tr>
</table>
<br>
<div align='center'><a href='servlet/ClientAssetServlet'>Back to
Assets and Liabilities</a></div>
<%@ include file="/inc/aesFooter.jspf"%>
</body>
</html>
