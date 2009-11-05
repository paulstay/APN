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
	<title>Property Input</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
	<link href="<%=path%>/css/styles.css" rel="stylesheet" media="screen">
	<body>
		<!-- insert bean here using page scope, if its an add, than the id should be passed as zero -->
		<jsp:useBean id="prop" scope='request'
			class="com.teag.bean.PropertyBean" />
		<jsp:useBean id="person" scope="session"
			class="com.teag.bean.PersonBean" />
		<c:if test="${btn == 'Add' }">
			<c:set var="btn1" value='Add' />
		</c:if>
		<c:if test="${btn == 'Edit' }">
			<c:set var="btn1" value='Edit' />
		</c:if>
		<%@ include file="/inc/aesHeader.jspf"%>
		<h3 align='center'>
			Personal Property Input
		</h3>
		<br>
		<teag:form action="servlet/PersonalAssetServlet" method="post"
			name="iform">
			<input type="hidden" name='action' value='cancel' />
			<input type="hidden" name='id' value='${prop.id}' />
			<input type="hidden" name="ownerId" value="${person.id }" />
			<table align='center'>
				<tr>
					<td align='right'>
						Description :
					</td>
					<td align='left'>
						<teag:text name="description" value="${prop.description }" />
					</td>
				</tr>
				<tr>
					<td align="right">
						Ownership :
					</td>
					<td align="left">
						<teag:select name="ownershipId" options="${ownerOptions}"
							optionValue="${prop.ownershipId}" />
					</td>
				</tr>
				<tr>
					<td align="right">
						Title :
					</td>
					<td align="left">
						<teag:select name="titleId" options="${titleOptions}"
							optionValue="${prop.titleId}" />
					</td>
				</tr>
				<tr>
					<td align="right">
						Amount :
					</td>
					<td align="left">
						<teag:text name="value" fmt="number" pattern="###,###,###"
							value="${prop.value}" />
					</td>
				</tr>
				<tr>
					<td align="right">
						Basis :
					</td>
					<td align="left">
						<teag:text name="basis" fmt="number" pattern="###,###,###"
							value="${prop.basis}" />
					</td>
				</tr>
				<tr>
					<td align="right">
						Growth Rate :
					</td>
					<td align="left">
						<teag:text name="growthRate" fmt="percent"
							value="${prop.growthRate}" />
					</td>
				</tr>
				<tr>
					<td align="right">
						Loan Balance :
					</td>
					<td align="left">
						<teag:text name="loanBalance" fmt="number" pattern="###,###,###"
							value="${prop.loanBalance}" />
					</td>
				</tr>
				<tr>
					<td align="right">
						Loan Interest Rate :
					</td>
					<td align="left">
						<teag:text name="loanInterest" fmt="percent"
							value="${prop.loanInterest}" />
					</td>
				</tr>
				<tr>
					<td align="right">
						Loan Term (Months)
					</td>
					<td align="left">
						<teag:text name="loanTerm" fmt="number" pattern="###"
							value="${prop.loanTerm}" />
					</td>
				</tr>
				<tr>
					<td align="right">
						Loan Freq. (# of payments per Year)
					</td>
					<td align="left">
						<teag:select name="loanFreq" options="<%=InputMaps.freqTypes%>"
							optionValue="${prop.loanFreq}" />
					</td>
				</tr>
				<fmt:formatNumber var='lp' pattern="###,###,###"
					value='${prop.loanPayment}' />
				<tr>
					<td align="right">
						Loan Payment :
					</td>
					<td align="left">
						<teag:text name="loanPayment" fmt="number" pattern="###,###,###"
							value="${prop.loanPayment}" />
					</td>
				</tr>
				<tr>
					<td align="right">
						Interest Deductable on Personal Taxes
					</td>
					<td align="left">
						<teag:select name="interestDed" options="<%=InputMaps.yesNo%>"
							optionValue="${prop.intDed}" />
					</td>
				</tr>
				<tr>
					<td align="right">
						Notes :
					</td>
					<td align="left">
						<textarea name="notes">${prop.notes }</textarea>
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
