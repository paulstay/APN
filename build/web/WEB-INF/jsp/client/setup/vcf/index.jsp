<%@ page language="java" import="java.util.*"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="teagtags" prefix="teag" %>
<%@ page import="java.text.*"%>
<%@ page import="com.teag.bean.*"%>
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
<c:if test="${validUser == null}">
	<c:redirect url="/login.jsp">
		<c:param name="errorMsg" value="You are not logged in!" />
	</c:redirect>
</c:if>
<jsp:useBean id="validUser" scope="session"
	class="com.teag.bean.AdminBean" />
<jsp:useBean id="client" scope="session"
	class="com.teag.bean.ClientBean" />
<%@ include file="/inc/aesHeader.jspf"%>
<%@ include file="/inc/aesQuickJump.jspf"%>
<h3 align='center'>Addition Line Items for Cash Flow Analysis</h3>
<table width='40%' align='center'>
	<tr>
		<td> <a href="servlet/VcfCashReceiptsServlet">Cash Receipt Line Items</a></td>
	</tr>
	<tr>
		<td> <a href="servlet/VcfCashDisbursementServlet">Cash Disbursement Line Items</a></td>
	</tr>
	<tr>
		<td> <a href="servlet/VcfNetWorthServlet">Networth Line Items</a></td>
	</tr>
	<tr>
		<td> <a href="servlet/VcfEstateDistServlet">Estate Distribution Line Items (To Taxable Estate)</a></td>
	</tr>
	<tr>
		<td> <a href="servlet/VcfFamilyServlet">Estate Distribution Line Items (To Family)</a></td>
	</tr>
	<tr>
		<td> <a href="servlet/VcfCharityServlet">Estate Distribution To Charity Line Items</a></td>
	</tr>
</table>
<br>
<div align='center'>
	<teag:form name="iform" method="post" action="servlet/LinkServlet">
		<input type='hidden' name='target' value='/WEB-INF/jsp/client/setup/index.jsp' />
		<input type='submit' value='Back' />
	</teag:form>
</div>
<br>
<%@ include file="/inc/aesFooter.jspf" %>
</body>
</html>
