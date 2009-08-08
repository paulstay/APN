<%@ page language="java" import='com.teag.bean.*' pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.*" %>
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
	<link href="<%=path %>/css/styles.css" rel="stylesheet" media="screen">
<body>
<jsp:useBean id='person' class='com.teag.bean.PersonBean' scope='session' />
<%@ include file="/inc/aesHeader.jspf" %>
<h3 align='center'> Asset Data for ${person.firstName } ${person.lastName}</h3>
<h3 align='center'>Life Insurance</h3>
<!-- Add asset data from array list and put in request-->
<c:set var="inc" value="0" />
<table align='center' width='80%'>
<tr class='bg-color6'>
	<td align='center'>Insured</td>
	<td align='center'>Owner</td>
	<td align='center'>Face Value</td>
	<td align='center'>Cash Value</td>
	<td align='center'>Policy Type</td>
	<td align='center'></td>
</tr>
<c:forEach var="s" items="${cList}" >
<tr class="l-color${inc%2}">
	<td align='right'>${s.insured}</td>
	<td align='right'>${s.owner}</td>
	<td align='right'><fmt:formatNumber value='${s.faceValue}' type='currency' /></td>
	<td align='right'><fmt:formatNumber value='${s.value}' type='currency' /></td>
	<td align='center'><teag:life value='${s.policyTypeId}' /></td>
	<td align='right'>
		<a href="servlet/LifeInsAssetServlet?id=${s.id}&action=view">View</a>
		<a href="servlet/LifeInsAssetServlet?id=${s.id}&action=edit">Edit</a>
	</td>
</tr>
<c:set var="inc" value="${inc + 1}" />
</c:forEach>
</table>
<table align='center'>
<tr>
	<td colspan='8' align='center'>
		<teag:form name='aform' action='servlet/LifeInsAssetServlet' method='post'>
			<input type="hidden" name="action" value="add"/>
			<input type="hidden" name="id" value="0"/>
			<input type='submit' name="btn" value='Add' />
		</teag:form>
	</td>
</tr>
</table>
<br>
<div align='center'>
<a href='servlet/ClientAssetServlet'>Back to Assets and Liabilities</a>
</div>
<%@ include file="/inc/aesFooter.jspf" %>
</body>
</html>
