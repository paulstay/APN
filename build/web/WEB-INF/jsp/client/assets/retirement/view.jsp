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
	<title>Retirement Plan</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
	<link href="<%=path%>/css/styles.css" rel="stylesheet" media="screen">
	<body>
		<jsp:useBean id="retire" scope="request"
			type="com.teag.bean.RetirementBean" />
		<%@ include file="/inc/aesHeader.jspf"%>
		<h3 align='center'>
			Retirement Plan Details
		</h3>
		<br>
		<table align='center' width='70%'>
			<tr>
				<td align="right">
					Description :
				</td>
				<td align="left">
					${retire.description}
				</td>
			</tr>
			<tr>
				<td align="right">
					Ownership :
				</td>
				<td align="left">
					<teag:ownership value="${retire.ownershipId}" />
				</td>
			</tr>
			<tr>
				<td align="right">
					Title :
				</td>
				<td align="left">
					<teag:title value="${retire.titleId }" />
				</td>
			</tr>
			<tr>
				<td align='right'>
					Annual Contribution :
				</td>
				<td align='left'>
					<fmt:formatNumber value='${retire.annualContrib}' />
				</td>
			</tr>
			<tr>
				<td align="right">
					Amount :
				</td>
				<td align="left">
					<fmt:formatNumber value="${retire.value}"
						pattern="###,###,###" />
				</td>
			</tr>
			<tr>
				<td align="right">
					Plan Growth :
				</td>
				<td align='left'>
					<fmt:formatNumber pattern='###.####%'
						value='${retire.growthRate}' />
				</td>
			</tr>
			<tr>
				<td align="right">
					Employee Contributions :
				</td>
				<td align='left'>
					<fmt:formatNumber value="${retire.employeeContrib}"
						pattern="###,###,###" />
				</td>
			</tr>
			<tr>
				<td align="right">
					Employer Contributions :
				</td>
				<td align='left'>
					<fmt:formatNumber value="${retire.employerContrib}"
						pattern="###,###,###" />
				</td>
			</tr>
			<tr>
				<td align="right">
					Beneficiary :
				</td>
				<td align="left">
					${retire.namedBeneficiary}
				</td>
			</tr>
			<fmt:parseDate var="bd" value="${retire.birthDate}"
				pattern='M/d/yyyy' />
			<fmt:formatDate var="bfd" value="${bd}" pattern='M/d/yyyy' />
			<tr>
				<td align="right">
					Beneficiary Birth Date :
				</td>
				<td align="left">
					${bfd}
				</td>
			</tr>
			<tr>
				<td align="right">
					Beneficiary Relationship :
				</td>
				<td align="left">
					<c:choose>
						<c:when test="${retire.beneficiaryType == 'S' }">Spouse</c:when>
						<c:otherwise>Non Spouse</c:otherwise>
					</c:choose>
				</td>
			</tr>
			<tr>
				<td align="right">
					Life Expectancy Calculation :
				</td>
				<td align="left">
					<c:choose>
						<c:when test="${retire.lifeExpCalc == 'b' || retire.lifeExpCalc == 'B'}">Both</c:when>
						<c:when test="${retire.lifeExpCalc == 'o' || retire.lifeExpCalc == 'O'}">Owner</c:when>
						<c:when test="${retire.lifeExpCalc == 'f' || retire.lifeExpCalc == 'F'}">Beneficiary</c:when>
						<c:otherwise>Neither</c:otherwise>
					</c:choose>
				</td>
			</tr>
			<tr>
				<td align="right">
					Notes :
				</td>
				<td align="left">
					${retire.notes }
				</td>
			</tr>
			<tr>
				<td align='right'>
					<teag:form name="bform" action="servlet/RetirementAssetServlet"
						method="post">
						<input type="hidden" name="action" value="list"/>
						<input type='submit' value="Back" />
					</teag:form>
				</td>
				<td align='left'>
					<teag:form name="eform"
						action="servlet/RetirementAssetServlet"
						method="post">
						<input type="hidden" name="action" value="edit"/>
						<input type="hidden" name="id" value="${retire.id}"/>
						<input type="hidden" name="ownerId" value="${person.id}"/>
						<input type='submit' value="Edit" />
					</teag:form>
				</td>
			</tr>
		</table>
		<%@ include file="/inc/aesFooter.jspf"%>
	</body>
</html>
