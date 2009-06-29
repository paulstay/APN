<%@ page language="java" import="com.teag.bean.*" pageEncoding="ISO-8859-1"%>
<%@ page import="com.estate.constants.*" %>
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
<jsp:useBean id="bond" scope="request" type="com.teag.bean.BondBean" />
<%@ include file="/inc/aesHeader.jspf" %>
<h3 align='center'> Bond Asset Details</h3>
<br>
<table align='center' width='70%'>
<tr>
	<td align="right">Description : </td>
	<td align="left">${bond.name}</td>
</tr>
<tr>
	<td align="right">Ownership : </td>
	<td align="left"><teag:ownership value="${bond.ownership}"/></td>
</tr>
<tr>
	<td align="right">Title : </td>
	<td align="left"><teag:title value="${bond.title}" /></td>
</tr>
<tr>
	<td align="right">Value : </td>
	<td align="left"><fmt:formatNumber value="${bond.currentValue }" type="currency"/></td>
</tr>
<tr>
	<td align="right">Basis : </td>
	<td align="left"><fmt:formatNumber value="${bond.basis }" type="currency"/></td>
</tr>
<tr>
	<td align="right">Interest/Divdend : </td>
	<td align="left"><fmt:formatNumber value="${bond.interest }" pattern="###.####%" /></td>
</tr>
<tr>
	<td align="right">Growth : </td>
	<td align="left"><fmt:formatNumber value="${bond.growth }" pattern="###.####%" /></td>
</tr>
<tr>
	<td align='right'>Subject to AMT : </td>
	<td align='left'>${bond.amt }</td>
</tr>
<tr>
	<td align='right'>Notes : </td>
	<td align='left'>${bond.notes}</td>
</tr>
<tr>
	<td align='right'>
		<teag:form name="bform" action="servlet/BondAssetServlet" method="post">
			<input type="hidden" name="action" value="list"/>
			<input type='submit' value="Back"/>
		</teag:form>
	</td>
	<td align='left'>
		<teag:form name="eform" action="servlet/BondAssetServlet" method="post">
			<input type="hidden" name="action" value="edit"/>
			<input type="hidden" name="id" value="${bond.id}"/>
			<input type='submit' value="Edit"/>
		</teag:form>
	</td>
</tr>
</table>
<%@ include file="/inc/aesFooter.jspf" %>
</body>
</html>
