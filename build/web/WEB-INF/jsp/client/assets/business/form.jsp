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
	<title>Business Asset Form</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
	<link href="<%=path%>/css/styles.css" rel="stylesheet" media="screen">
	<body>
		<!-- insert bean here using page scope, if its an add, than the id should be passed as zero -->
		<jsp:useBean id="biz" scope='request' class="com.teag.bean.BusinessBean" />
		<c:if test="${btn == 'Add' }">
			<c:set var="btn1" value='Add' />
		</c:if>
		<c:if test="${btn == 'Edit' }">
			<c:set var="btn1" value='Edit' />
		</c:if>
		<%@ include file="/inc/aesHeader.jspf"%>
		<h3 align='center'>
			Business Asset Input
		</h3>
		<br>
		<teag:form action="servlet/BusinessAssetServlet" method="post"
			name="iform">
			<input type='hidden' name='action' value='Cancel' />
			<input type='hidden' name='ownerId' value='${person.id}' />
			<input type='hidden' name='id' value='${biz.id}' />
			<table align='center'>
				<tr>
					<td align='right'>
						Description :
					</td>
					<td align='left'>
						<teag:text name="description" value="${biz.description }" />
					</td>
				</tr>
				<tr>
					<td align="right">
						Ownership :
					</td>
					<td align="left">
						<teag:select name="ownershipId" options="${ownerOptions }"
							optionValue="${biz.ownershipId}" />
					</td>
				</tr>
				<tr>
					<td align="right">
						Title :
					</td>
					<td align="left">
						<teag:select name="titleId" options="${titleOptions }"
							optionValue="${biz.titleId}" />
					</td>
				</tr>
				<tr>
					<td align="right">
						Current Value :
					</td>
					<td align="left">
						<teag:text name="value" fmt="number" pattern="###,###,###"
							value="${biz.value}" />
					</td>
				</tr>
				<tr>
					<td align="right">
						Basis :
					</td>
					<td align="left">
						<teag:text name="basis" fmt="number" pattern="###,###,###"
							value="${biz.basis}" />
					</td>
				</tr>
				<tr>
					<td align="right">
						Growth :
					</td>
					<td align="left">
						<teag:text name="growth" fmt="number" pattern="##.###%"
							value="${biz.growthRate}" />
					</td>
				</tr>
				<tr>
					<td align="right">
						Income :
					</td>
					<td align="left">
						<teag:text name="annualIncome" fmt="number" pattern="###,###,###"
							value="${biz.annualIncome}" />
					</td>
				</tr>
				<tr>
					<td align="right">
						Income Growth :
					</td>
					<td align="left">
						<teag:text name="incomeGrowth" fmt="number" pattern="##.###%"
							value="${biz.incomeGrowth}" />
					</td>
				</tr>
				<tr>
					<td align="right">
						Percent Owned (%) :
					</td>
					<td align="left">
						<teag:text name="percentOwned" fmt="percent"
							value="${biz.percentOwned}" />
					</td>
				</tr>
				<tr>
					<td align="right">
						Business Type :
					</td>
					<td align='left'>
						<teag:select name="businessType" options="${bizOptions }"
							optionValue="${biz.businessTypeId}" />
					</td>
				</tr>
				<tr>
					<td align="right">
						Anticipated Liquidation Date
					</td>
					<td align='left'>
						<teag:text name='ald' value='${biz.ald}' />
					</td>
				</tr>
				<tr>
					<td align="right">
						Notes :
					</td>
					<td align="left">
						<textarea name="notes">${biz.notes}</textarea>
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
