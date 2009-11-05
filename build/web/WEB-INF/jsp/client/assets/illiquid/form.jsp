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
		<jsp:useBean id="person" scope="session"
			type="com.teag.bean.PersonBean" />
		<jsp:useBean id="illiquid" scope='request'
			type="com.teag.bean.IlliquidBean" />
		<c:if test="${btn == 'Add' }">
			<c:set var="btn1" value='Add' />
		</c:if>
		<c:if test="${btn == 'Edit' }">
			<c:set var="btn1" value='Edit' />
		</c:if>
		<%@ include file="/inc/aesHeader.jspf"%>
		<h3 align='center'>
			Illiquid Asset Input
		</h3>
		<br>
		<teag:form action="servlet/IlliquidAssetServlet" method="post"
			name="iform">
			<input type='hidden' name='action' value='cancel' />
			<input type='hidden' name='id' value='${illiquid.id}' />
			<input type='hidden' name='ownerId' value='${person.id }' />
			<table align='center'>
				<tr>
					<td align='right'>
						Description :
					</td>
					<td align='left'>
						<teag:text name="description" value="${illiquid.description }" />
					</td>
				</tr>
				<tr>
					<td align="right">
						Ownership :
					</td>
					<td align="left">
						<teag:select name="ownershipId" options="${ownerOptions}" optionValue="${liiquid.ownershipId}"/>
					</td>
				</tr>
				<tr>
					<td align="right">
						Title :
					</td>
					<td align="left">
						<teag:select name="titleId" options="${titleOptions}" optionValue="${liiquid.titleId}"/>
					</td>
				</tr>
				<tr>
					<td align="right">
						Value :
					</td>
					<td align="left">
						<teag:text name="value" fmt="number" pattern="###,###,###" value="${illiquid.value}" />
					</td>
				</tr>
				<tr>
					<td align="right">
						Basis :
					</td>
					<td align="left">
						<teag:text name="basis" fmt="number" pattern="###,###,###" value="${illiquid.basis}" />
					</td>
				</tr>
				<tr>
					<td align="right">
						Income (%) :
					</td>
					<td align="left">
						<teag:text name="interest" fmt="percent" value="${illiquid.divInt}" />
					</td>
				</tr>
				<tr>
					<td align="right">
						Growth :
					</td>
					<td align="left">
						<teag:text name="growth" fmt="percent" value="${illiquid.growthRate}" />
					</td>
				</tr>
                                <tr>
					<td align="right">Anticipated Liquidation Date</td>
					<td align='left'>
						<teag:text name='ald' value='${illiquid.ald}' />
					</td>
				</tr>
				<tr>
					<td align="right">
						Notes :
					</td>
					<td align="left">
						<textarea name="notes">${illiquid.notes }</textarea>
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
							onclick='document.iform.action.value="cancel"' value='Cancel' />
						<input type='submit'
							onclick='document.iform.action.value="delete"' value='Delete' />
					</td>
				</tr>
			</table>
		</teag:form>
		<%@ include file="/inc/aesFooter.jspf"%>
	</body>
</html>
