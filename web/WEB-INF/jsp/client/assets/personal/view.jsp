<%@ page language="java" import="com.teag.bean.*" pageEncoding="ISO-8859-1"%>
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
    <title>Personal Property</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<link href="<%=path %>/css/styles.css" rel="stylesheet" media="screen">
<body>
<jsp:useBean id="prop" scope="request" type="com.teag.bean.PropertyBean" />
<%@ include file="/inc/aesHeader.jspf" %>
<h3 align='center'> Personal Property Details</h3>
<br>
<table align='center' width='70%'>
<tr>
	<td align="right">Description : </td>
	<td align="left">${prop.description}</td>
</tr>
<tr>
	<td align="right">Ownership : </td>
	<td align="left"><teag:ownership value="${prop.ownershipId}"/></td>
</tr>
<tr>
	<td align="right">Title : </td>
	<td align="left"><teag:title value="${prop.titleId }" /></td>
</tr>
<tr>
	<td align="right">Amount : </td>
	<td align="left"><fmt:formatNumber value="${prop.value }" type="currency"/></td>
</tr>
<fmt:formatNumber var="b" value="${prop.basis}" pattern="###,###,###" />
<tr>
	<td align="right">Basis : </td>
	<td align="left">${b}</td>
</tr>
<tr>
	<td align="right">Growth Rate : </td>
	<td align="left"><fmt:formatNumber value="${prop.growthRate}" pattern="##.####%"  /></td>
</tr>
<tr>
	<td align="right">Loan Balance : </td>
	<td align="left"><fmt:formatNumber value='${prop.loanBalance }'/></td>
</tr>
<tr>
	<td align="right">Loan Interest Rate : </td>
	<td align="left"><fmt:formatNumber pattern="###.####%" value='${prop.loanInterest }'/></td>
</tr>
<tr>
	<td align="right">Loan Freq. (# of payments per Year)</td>
	<td align="left">${prop.loanFreq}</td>
</tr>
<tr>
	<td align="right">Loan Payment : </td>
	<td align="left"><fmt:formatNumber pattern="###.####%" value='${prop.loanPayment}'/></td>
</tr>
<tr>
	<td align="right">Interest Deductable on Personal Taxes : </td>
	<td align="left">${prop.intDed}</td>
</tr>
<tr>
	<td align="right">Notes : </td>
	<td align="left">${prop.notes }</td>
</tr>
<tr>
	<td align='right'>
		<teag:form name="bform" action="servlet/PersonalAssetServlet" method="post">
		<input type='submit' value="Back"/>
		</teag:form>
	</td>
	<td align='left'>
		<teag:form name="eform" action="servlet/PersonalAssetServlet" method="post">
			<input type="hidden" name="action" value="edit"/>
			<input type="hidden" name="id" value="${prop.id}"/>
			<input type="hidden" name="ownerId" value="${prop.ownerId}"/>
			<input type='submit' value="Edit"/>
		</teag:form>
	</td>
</tr>
</table>
<%@ include file="/inc/aesFooter.jspf" %>
</body>
</html>
