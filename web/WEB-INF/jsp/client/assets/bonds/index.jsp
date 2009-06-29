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
    <title>Bonds</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<link href="<%=path %>/css/styles.css" rel="stylesheet" media="screen">
<body>
<jsp:useBean id='person' class='com.teag.bean.PersonBean' scope='session' />
<%@ include file="/inc/aesHeader.jspf" %>
<h3 align='center'> Asset Data for ${person.firstName } ${person.lastName}</h3>
<h3 align='center'>Bond Assets</h3>
<!-- Add asset data from array list and put in request-->
<c:set var="inc" value="0" />
<table align='center' width='80%'>
<tr class='bg-color6'>
	<td align='center'>Description</td>
	<td align='center'>Ownership</td>
	<td align='center'>Title</td>
	<td align='center'>Value</td>
	<td align='center'>Interest/Dividend</td>
	<td align='center'></td>
</tr>
<c:forEach var="s" items="${cList}" >
<tr class="l-color${inc%2}">
	<td align='right'>${s.name}</td>
	<td align='right'><teag:ownership value="${s.ownership}" /></td>
	<td align='right'><teag:title value="${s.title}" /></td>
	<td align='right'><fmt:formatNumber type="currency" value="${s.currentValue }" /></td>
	<td align='right'><fmt:formatNumber type="percent" pattern="###.####%" value="${s.interest }" /></td>
	<td align='right'>
		<a href="servlet/BondAssetServlet?id=${s.id}&action=view">View</a>
		<a href="servlet/BondAssetServlet?id=${s.id}&action=edit">Edit</a>
	</td>
</tr>
<c:set var="inc" value="${inc + 1}" />
</c:forEach>
</table>
<table align='center'>
<tr>
	<td colspan='8' align='center'>
		<teag:form name='aform' action='servlet/BondAssetServlet' method='post'>
			<input type='hidden' name='action' value='add'/>
			<input type='hidden' name='id' value='0'/>
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
