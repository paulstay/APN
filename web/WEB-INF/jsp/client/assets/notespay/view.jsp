<%@ page language="java" import="com.teag.bean.*"
	pageEncoding="ISO-8859-1"%>
<%@ page import="com.estate.constants.*"%>
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
	<title>View Note Payable</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
	<link href="<%=path%>/css/styles.css" rel="stylesheet" media="screen">
	<body>
		<jsp:useBean id="notePay" scope="request" type="com.teag.bean.NotePayableBean" />
		<%@ include file="/inc/aesHeader.jspf"%>
		<h3 align='center'>
			Note Payable Details
		</h3>
		<br>
		<table align='center' width='70%'>
			<tr>
				<td align="right">
					Description :
				</td>
				<td align="left">${notePay.description }</td>
			</tr>
			<tr>
				<td align="right">
					Ownership :
				</td>
				<td align="left">
					<teag:ownership value="${notePay.ownershipId}" />
				</td>
			</tr>
			<tr>
				<td align="right">
					Title :
				</td>
				<td align="left">
					<teag:title value="${notePay.titleId }" />
				</td>
			</tr>
			<tr>
				<td align="right">
					Note Type :
				</td>
				<td align="left">
					${noteType}
				</td>
			</tr>
			<tr>
				<td align="right">
					Amount :
				</td>
				<td align="left">
					<fmt:formatNumber value="${notePay.loanAmount}"
						pattern="$###,###,###" />
				</td>
			</tr>
			<tr>
				<td align="right">
					Interest Rate :
				</td>
				<td align="left">
					<fmt:formatNumber value="${notePay.interestRate}"
						pattern="###.####%" />
				</td>
			</tr>
			<tr>
				<td align='right'>
					Payments per Year :
				</td>
				<td align='left'>
					<fmt:formatNumber value="${notePay.paymentsPerYear}" />
				</td>
			</tr>
			<tr>
				<td align='right'>
					Term (years) :
				</td>
				<td align='left'>
					<fmt:formatNumber value="${notePay.years}" />
				</td>

			</tr>
			<tr>
				<td align='right'>
					<teag:form name="bform" action="servlet/NotesPayAssetServlet"
						method="post">
						<input type='submit' value="Back" />
					</teag:form>
				</td>
				<td align='left'>
					<teag:form name="eform" action="servlet/NotesPayAssetServlet"
						method="post">
						<input type="hidden" name="action" value="edit" />
						<input type="hidden" name="id" value="${notePay.id}" />
						<input type='submit' value="Edit" />
					</teag:form>
				</td>
			</tr>
		</table>
		<%@ include file="/inc/aesFooter.jspf"%>
	</body>
</html>
