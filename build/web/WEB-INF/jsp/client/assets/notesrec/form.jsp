<%@ page language="java" pageEncoding="ISO-8859-1"%>
<%@ page import="com.estate.constants.*"%>
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
	<link href="<%=path%>/css/styles.css" rel="stylesheet" media="screen">
	<body>
		<!-- insert bean here using page scope, if its an add, than the id should be passed as zero -->
		<jsp:useBean id="note" scope='request' type="com.teag.bean.NotesBean" />
		<c:if test="${btn == 'Add' }">
			<c:set var="btn1" value='Add' />
		</c:if>
		<c:if test="${btn == 'Edit' }">
			<c:set var="btn1" value='Edit' />
		</c:if>
		<%@ include file="/inc/aesHeader.jspf"%>
		<h3 align='center'>
			Note Receivable Asset Input
		</h3>
		<br>
		<teag:form action="servlet/NotesRecAssetServlet" method="post"
			name="iform">
			<input type="hidden" name="action" value="cancel" />
			<input type="hidden" name="id" value="${note.id}" />
			<input type="hidden" name="ownerId" value="${person.id}" />
			<table align='center'>
				<tr>
					<td align='right'>
						Description :
					</td>
					<td align='left'>
						<teag:text name="description" value="${note.description }" />
					</td>
				</tr>
				<tr>
					<td align="right">
						Ownership :
					</td>
					<td align="left">
						<teag:select name="ownershipId" options="${ownerOptions}"
							optionValue="${note.ownershipId}" />
					</td>
				</tr>
				<tr>
					<td align="right">
						Title :
					</td>
					<td align="left">
						<teag:select name="titleId" options="${titleOptions}"
							optionValue="${note.titleId}" />
					</td>
				</tr>
				<tr>
					<td align="right">
						Note Type :
					</td>
					<td align="left">
						<teag:select name="noteType" options="<%=InputMaps.noteRecTypes%>"
							optionValue="${note.noteType}" />
					</td>
				</tr>
				<tr>
					<td align="right">
						Amount :
					</td>
					<td align="left">
						<teag:text name="loanAmount" fmt="number" pattern="###,###,###"
							value="${note.loanAmount}" />
					</td>
				</tr>
				<tr>
					<td align="right">
						Interest Rate/Dividend :
					</td>
					<td align="left">
						<teag:text name="interestRate" fmt="number" pattern="###,###,###"
							value="${note.interestRate}" />
					</td>
				</tr>
				<tr>
					<td align="right">
						Payments per year :
					</td>
					<td align="left">
						<teag:text name="paymentsPerYear" value="${note.paymentsPerYear }" />
					</td>
				</tr>
				<tr>
					<td align="right">
						Term (years) :
					</td>
					<td align="left">
						<teag:text name="years" fmt="number" pattern="###"
							value="${note.years}" />
					</td>
				</tr>
				<tr>
					<td align='center' colspan='2'>
						<c:choose>
							<c:when test="${btn=='Add' }">
								<input type='submit'
									onclick="document.iform.action.value='insert'" value='${btn1 }' />
							</c:when>
							<c:otherwise>
								<input type='submit'
									onclick="document.iform.action.value='update'" value='${btn1 }' />
							</c:otherwise>
						</c:choose>
						<input type='submit'
							onclick='document.iform.action.value="Cancel"' value='Cancel' />
						<input type='submit'
							onclick='document.iform.action.value="Delete"' value='Delete' />
					</td>
				</tr>
			</table>
		</teag:form>
		<%@ include file="/inc/aesFooter.jspf"%>
	</body>
</html>
