<%@ page language="java" pageEncoding="ISO-8859-1"%>
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
	<title>Cash Asset Edit/Add</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
	<link href="<%=path%>/css/styles.css" rel="stylesheet" media="screen">
	<body>
		<!-- insert bean here using page scope, if its an add, than the id should be passed as zero -->
		<jsp:useBean id="cash" scope='request' type="com.teag.bean.CashBean" />
		<c:if test="${btn == 'Add' }">
			<c:set var="btn1" value='Add' />
		</c:if>
		<c:if test="${btn == 'Edit' }">
			<c:set var="btn1" value='Edit' />
		</c:if>
		<%@ include file="/inc/aesHeader.jspf"%>
		<h3 align='center'>
			Cash or Equivalent Asset Input
		</h3>
		<br>
		<teag:form action="servlet/CashAssetServlet" method="post" name="iform">
			<input type="hidden" name="action" value="cancel" />
			<input type="hidden" name="id" value="${cash.id}" />
			<input type="hidden" name="ownerId" value="${person.id}" />
			<table align='center'>
				<tr>
					<td align='right'>
						Description :
					</td>
					<td align='left'>
						<teag:text name="description" value="${cash.name}" />
					</td>
				</tr>
				<tr>
					<td align="right">
						Ownership :
					</td>
					<td align="left">
						<teag:select name="ownershipId" options="${ownerOptions}"
							optionValue="${cash.ownership}" />
					</td>
				</tr>
				<tr>
					<td align="right">
						Title :
					</td>
					<td align="left">
						<teag:select name="titleId" options="${titleOptions}"
							optionValue="${cash.title}" />
					</td>
				</tr>
				<tr>
					<td align="right">
						Amount :
					</td>
					<td align="left">
						<teag:text name="value" fmt="number" pattern="###,###,###"
							value="${cash.currentValue}" />
					</td>
				</tr>
				<tr>
					<td align="right">
						Basis :
					</td>
					<td align="left">
						<teag:text name="basis" fmt="number" pattern="###,###,###"
							value="${cash.basis}" />
					</td>
				</tr>
				<tr>
					<td align="right">
						Interest Rate/Dividend :
					</td>
					<td align="left">
						<teag:text name="interest" fmt="number" pattern="##.###%"
							value="${cash.interest}" />
					</td>
				</tr>
				<tr>
					<td align="right">
						Growth :
					</td>
					<td align="left">
						<teag:text name="growth" fmt="number" pattern="##.###%"
							value="${cash.growth}" />
					</td>
				</tr>
				<tr>
					<td align="right">
						Bank :
					</td>
					<td align="left">
						<teag:text name="bank" value="${cash.bank }" />
					</td>
				</tr>
				<tr>
					<td align="right">
						Notes :
					</td>
					<td align="left">
						<textarea name="notes" rows='10' cols='40'>${cash.notes}</textarea>
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
							onclick="document.iform.action.value='Cancel'" value='Cancel' />
						<input type='submit'
							onclick="document.iform.action.value='delete'" value='Delete' />
					</td>
				</tr>
			</table>
		</teag:form>
		<%@ include file="/inc/aesFooter.jspf"%>
	</body>
</html>
