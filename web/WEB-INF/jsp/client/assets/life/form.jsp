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
		<jsp:useBean id="life" scope='request'
			class="com.teag.bean.InsuranceBean" />
		<c:if test="${btn == 'Add' }">
			<c:set var="btn1" value='Add' />
		</c:if>
		<c:if test="${btn == 'Edit' }">
			<c:set var="btn1" value='Edit' />
		</c:if>
		<%@ include file="/inc/aesHeader.jspf"%>
		<h3 align='center'>
			Life Insurance Input
		</h3>
		<br>
		<teag:form action="servlet/LifeInsAssetServlet" method="post"
			name="iform">
			<input type="hidden" name="action" value="cancel" />
			<input type="hidden" name="id" value="${life.id}" />
			<input type="hidden" name="ownerId" value="${person.id}" />
			<table align='center'>
				<tr>
					<td align='right'>
						Insured :
					</td>
					<td align='left'>
						<teag:text name="insured" value="${life.insured }" />
					</td>
				</tr>
				<tr>
					<td align="right">
						Owner :
					</td>
					<td align="left">
						<teag:text name="owner" value="${life.owner}" />
					</td>
				</tr>
				<tr>
					<td align="right">
						Named Beneficiary :
					</td>
					<td align="left">
						<teag:text name="beneficiary" value="${life.beneficiary}" />
					</td>
				</tr>
				<fmt:parseDate var="d" value="${life.dateAcquired}"
					pattern="M/d/yyyy" />
				<fmt:formatDate var="d2" value="${d}" pattern="M/d/yyyy" />
				<tr>
					<td align="right">
						Date Aquired (mm/dd/yyyy):
					</td>
					<td align="left">
						<teag:text name="dateAcquired" value="${d2}" />
					</td>
				</tr>
				<tr>
					<td align="right">
						Insurance Company :
					</td>
					<td align="left">
						<teag:text name="insuranceCompany"
							value="${life.insuranceCompany}" />
					</td>
				</tr>
				<tr>
					<td align="right">
						Company Policy Number :
					</td>
					<td align="left">
						<teag:text name="policyNumber" value="${life.policyNumber}" />
					</td>
				</tr>
				<tr>
					<td align="right">
						Face Value :
					</td>
					<td align="left">
						<teag:text name="faceValue" fmt="number" pattern="###,###,###" value="${life.faceValue}" />
					</td>
				</tr>
				<tr>
					<td align="right">
						Current Cash Value :
					</td>
					<td align="left">
						<teag:text name="value" fmt="number" pattern="###,###,###" value="${life.value}" />
					</td>
				</tr>
				<fmt:formatNumber var="fcv" value="${life.futureCashValue}"
					type='currency' />
				<tr>
					<td align="right">
						Approximate Cash Value in 10 years :
					</td>
					<td align="left">
						<teag:text name="futureCashValue" value="${fcv}" />
					</td>
				</tr>
				<tr>
					<td align='right'>
						Policy Type :
					</td>
					<td align='left'>
						<teag:select name="policyTypeId" options="<%=InputMaps.lifeTypes %>" optionValue="${life.policyTypeId }"/>
					</td>
				</tr>
				<tr>
					<td align="right">
						Preium Payment :
					</td>
					<td align="left">
						<teag:text name="premiums" fmt="number" pattern="###,###,###"  value="${life.premiums}" />
					</td>
				</tr>
				<tr>
					<td align="right">
						Premium Frequency :
					</td>
					<td align="left">
						<teag:select name="premiumFreq" options="<%=InputMaps.premiumFreq%>" 
							optionValue="${life.premiumFreqId}"/>
					</td>
				</tr>
				<tr>
					<td align="right">
						Loans :
					</td>
					<td align="left">
						<teag:text name="loans" fmt="number" pattern="###,###,###" value="${life.loans}" />
					</td>
				</tr>
				<tr>
					<td align='right'>
						Is Life Death Benefit inclusive or in addition to the cash value?
						:
					</td>
					<td align='left'>
						<teag:select name="taxableLifeInc" options="<%=InputMaps.lifeInc%>" optionValue="${life.taxableLifeInc }"/>
					</td>
				</tr>
				<tr>
					<td align="right">
						Notes :
					</td>
					<td align="left">
						<textarea name="notes">${life.notes }</textarea>
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
