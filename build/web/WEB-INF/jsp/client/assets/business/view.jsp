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
    <title>Select Client</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<link href="css/style.css" rel="stylesheet" media="screen">
<body>
<jsp:useBean id="biz" scope="request" type="com.teag.bean.BusinessBean" />
<%@ include file="/inc/aesHeader.jspf" %>
<h3 align='center'> Business Asset Details</h3>
<br>
<table align='center' width='70%'>
<tr>
	<td align="right">Description : </td>
	<td align="left">${biz.description}</td>
</tr>
<tr>
	<td align="right">Ownership : </td>
	<td align="left"><teag:ownership value="${biz.ownershipId}"/></td>
</tr>
<tr>
	<td align="right">Title : </td>
	<td align="left"><teag:title value="${biz.titleId }" /></td>
</tr>
<fmt:formatNumber var="b" value="${biz.basis}" pattern="$###,###,###" />
<tr>
	<td align="right">Basis : </td>
	<td align="left">${b}</td>
</tr>
<fmt:formatNumber var="v" value="${biz.value}" pattern="$###,###,###" />
<tr>
	<td align="right">Current Value : </td>
	<td align="left">${v}</td>
</tr>
<fmt:formatNumber var="g" value="${biz.growthRate}" type='percent'  />
<tr>
	<td align="right">Growth : </td>
	<td align="left">${g}</td>
</tr>
<fmt:formatNumber var="i" value="${biz.annualIncome}" pattern="###,###,###" />
<tr>
	<td align="right">Income : </td>
	<td align="left">${i}</td>
</tr>
<fmt:formatNumber var="ig" value="${biz.incomeGrowth}" pattern="###,###,###"  />
<tr>
	<td align="right">Income Growth : </td>
	<td align="left">${ig}</td>
</tr>
<fmt:formatNumber var="p" value="${biz.percentOwned}" pattern="##.####%"  />
<tr>
	<td align="right">Percent Owned (%) : </td>
	<td align="left">${p}</td>
</tr>
<tr>
	<td align="right">Business Type : </td>
	<td align='left'><teag:business value="${biz.businessTypeId}" /></td>
</tr>
<tr>
	<td align="right">Business Anticipated Liquidation Date</td>
	<c:choose>
	<c:when test="${biz.ald != null }">
		<td align="left">${biz.ald}</td>
	</c:when>
	<c:otherwise>
		<td align="left"></td>
	</c:otherwise>
	</c:choose>
	
</tr>
<tr>
	<td align="right">Notes : </td>
	<td align="left">${biz.notes}</td>
</tr>
<tr>
	<td align='right'>
		<teag:form name="bform" action="servlet/BusinessAssetServlet" method="post">
		<input type='submit' value="Back"/>
		</teag:form>
	</td>
	<td align='left'>
		<teag:form name='eform' action="servlet/BusinessAssetServlet" method="post">
			<input type="hidden" name="action" value="edit"/>
			<input type="hidden" name="id" value="${biz.id}"/>
		<input type='submit' value="Edit"/>
		</teag:form>
	</td>
</tr>
</table>
<%@ include file="/inc/aesFooter.jspf" %>
</body>
</html>
