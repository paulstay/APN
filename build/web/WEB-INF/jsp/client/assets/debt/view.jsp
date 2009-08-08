<%@ page language="java" import="com.teag.bean.*"
	pageEncoding="ISO-8859-1"%>
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
	<title>Debt View</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
	<link href="<%=path%>/css/styles.css" rel="stylesheet" media="screen">
	<body>
		<jsp:useBean id="debt" scope="request" type="com.teag.bean.DebtBean" />
		<%@ include file="/inc/aesHeader.jspf"%>
		<h3 align='center'>
			Debt Asset Details
		</h3>
		<br>
		<table align='center' width='70%'>
			<tr>
				<td align="right">
					Description :
				</td>
				<td align="left">
					${debt.description}
				</td>
			</tr>
			<tr>
				<td align="right">
					Ownership :
				</td>
				<td align="left">
					<teag:ownership value="${debt.ownershipId}" />
				</td>
			</tr>
			<tr>
				<td align="right">
					Title :
				</td>
				<td align="left">
					<teag:title value="${debt.titleId }" />
				</td>
			</tr>
			<tr>
				<td align='right'>
					Debt Type :
				</td>
				<td align='left'>
					<teag:debt value="${debt.debtTypeId}" />
				</td>
			</tr>
			<tr>
				<td align="right">
					Amount :
				</td>
				<td align="left">
					<fmt:formatNumber value="${debt.value }" type="currency" />
				</td>
			</tr>
			<fmt:formatNumber var="i" value="${debt.interestRate}"
				pattern="##.####%" />
			<tr>
				<td align="right">
					Interest Rate :
				</td>
				<td align="left">
					${i}
				</td>
			</tr>
			<tr>
				<td align="right">
					Term (years) :
				</td>
				<td align="left">
					${debt.loanTerm}"
				</td>
			</tr>
			<tr>
				<td align="right">
					Bank :
				</td>
				<td align="left">
					${debt.bank}
				</td>
			</tr>
			<tr>
				<td align="right">
					Notes :
				</td>
				<td align="left">
					${debt.notes}
				</td>
			</tr>
			<tr>
				<td align='right'>
					<teag:form name='backForm' action="servlet/DebtAssetServlet" method="post">
						<input type="hidden" name="action" value="list" />
						<input type='submit' value="Back" />
					</teag:form>
				</td>
				<td align='left'>
					<teag:form name='editForm' action="servlet/DebtAssetServlet" method="post">
						<input type="hidden" name="action" value="edit" />
						<input type="hidden" name="id" value="${debt.id}" />
						<input type="hidden" name="ownerId" value="${person.id}" />
						<input type='submit' value="Edit" />
					</teag:form>
				</td>
			</tr>
		</table>
		<%@ include file="/inc/aesFooter.jspf"%>
	</body>
</html>
