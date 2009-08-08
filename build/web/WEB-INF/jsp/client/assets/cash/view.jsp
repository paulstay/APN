<%@ page language="java" import="com.teag.bean.*" pageEncoding="ISO-8859-1"%>
<%@ page import="com.estate.constants.*" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="teagtags" prefix="teag"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
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
<jsp:useBean id="cash" scope="request" type="com.teag.bean.CashBean" />
<%@ include file="/inc/aesHeader.jspf" %>
<h3 align='center'> Cash Asset Details</h3>
<br>
<table align='center' width='70%'>
<tr>
	<td align="right">Description : </td>
	<td align="left">${cash.name}</td>
</tr>
<tr>
	<td align="right">Ownership : </td>
	<td align="left"><teag:ownership value="${cash.ownership}"/></td>
</tr>
<tr>
	<td align="right">Title : </td>
	<td align="left"><teag:title value="${cash.title}" /></td>
</tr>
<tr>
	<td align="right">Amount : </td>
	<td align="left"><fmt:formatNumber value="${cash.currentValue }" type="currency"/></td>
</tr>
<tr>
	<td align="right">Basis : </td>
	<td align="left"><fmt:formatNumber value="${cash.basis}" type="currency"/></td>
</tr>
<tr>
	<td align="right">Interest Rate/Dividend : </td>
	<td align="left"><fmt:formatNumber value="${cash.interest }" pattern="###.####%" type="percent"/></td>
</tr>
<tr>
	<td align="right">Growth : </td>
	<td align="left"><fmt:formatNumber value="${cash.growth }" pattern="###.####%" type="percent"/></td>
</tr>
<tr>
	<td align="right">Bank : </td>
	<td align="left">${cash.bank }</td>
</tr>
<tr>
	<td align="right">Notes : </td>
	<td align="left">${cash.notes }</td>
</tr>
<tr>
	<td align='right'>
		<teag:form name='bform' action="servlet/CashAssetServlet" method="post">
			<input type="hidden" name="action" value="edit"/>
			<input type='submit' value="Back"/>
		</teag:form>
	</td>
	<td align='left'>
		<teag:form name='eform' action="servlet/CashAssetServlet" method="post">
		<input type="hidden" name="id" value="${cash.id}"/>
		<input type="hidden" name="action" value="edit"/>
		<input type='submit' value="Edit"/>
		</teag:form>
	</td>
</tr>
</table>
<%@ include file="/inc/aesFooter.jspf" %>
</body>
</html>
