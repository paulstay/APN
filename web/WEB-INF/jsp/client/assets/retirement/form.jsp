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
	<title>Retirement Plan</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
	<link href="<%=path%>/css/styles.css" rel="stylesheet" media="screen">
	<body>
		<!-- insert bean here using page scope, if its an add, than the id should be passed as zero -->
		<jsp:useBean id="retire" scope='request'
			class="com.teag.bean.RetirementBean" />
		<c:if test="${btn == 'Add' }">
			<c:set var="btn1" value='Add' />
		</c:if>
		<c:if test="${btn == 'Edit' }">
			<c:set var="btn1" value='Edit' />
		</c:if>
		<%@ include file="/inc/aesHeader.jspf"%>
		<h3 align='center'>
			Retirement Plan Input
		</h3>
		<br>
		<teag:form action="servlet/RetirementAssetServlet" method="post"
			name="iform">
			<input type='hidden' name='action' value='cancel' />
			<input type='hidden' name='id' value='${retire.id}' />
			<input type='hidden' name='ownerId' value="${person.id}"/>
			<table align='center'>
				<tr>
					<td align='right'>
						Description :
					</td>
					<td align='left'>
						<teag:text name="description" value="${retire.description }" />
					</td>
				</tr>
				<tr>
					<td align="right">
						Ownership :
					</td>
					<td align="left">
						<teag:select name="ownershipId" options="${ownerOptions}"
							optionValue="${retire.ownershipId}" />
					</td>
				</tr>
				<tr>
					<td align="right">
						Title :
					</td>
					<td align="left">
						<teag:select name="titleId" options="${titleOptions}"
							optionValue="${retire.titleId}" />
					</td>
				</tr>
				<tr>
					<td align='right'>
						Annual Contribution :
					</td>
					<td align='left'>
						<teag:text name='annualCont' fmt="number" pattern="###,###,###"
							value='${retire.annualContrib }' />
					</td>
				</tr>
				<tr>
					<td align="right">
						Amount :
					</td>
					<td align="left">
						<teag:text name="value" fmt="number" pattern="###,###,###"
							value="${retire.value}" />
					</td>
				</tr>
				<tr>
					<td align="right">
						Plan Growth :
					</td>
					<td align='left'>
						<teag:text name='growthRate' fmt="percent"
							value='${retire.growthRate}' />
					</td>
				</tr>
				<tr>
					<td align="right">
						Employee Contributions :
					</td>
					<td align='left'>
						<teag:text name='employeeContrib' fmt="number"
							pattern="###,###,###" value='${retire.employeeContrib}' />
					</td>
				</tr>
				<tr>
					<td align="right">
						Employer Contributions :
					</td>
					<td align='left'>
						<teag:text name='employerContrib' fmt="number"
							pattern="###,###,###" value='${retire.employerContrib}' />
					</td>
				</tr>
				<tr>
					<td align="right">
						Beneficiary :
					</td>
					<td align="left">
						<teag:text name="namedBeneficiary" value="${retire.namedBeneficiary}" />
					</td>
				</tr>
				<fmt:parseDate var="bd" value="${retire.birthDate}"
					pattern='M/d/yyyy' />
				<fmt:formatDate var="bfd" value="${bd}" pattern='M/d/yyyy' />
				<tr>
					<td align="right">
						Beneficiary Birth Date (M/dd/yyyy):
					</td>
					<td align="left">
						<teag:text name="birthDate" value="${bfd}" />
					</td>
				</tr>
				<tr>
					<td align="right">
						Beneficiary Relationship :
					</td>
					<td align="left">
						<teag:select name="beneficiaryType"
							options='<%=InputMaps.retSpouse%>'
							optionValue="${retire.beneficiaryType }" />
					</td>
				</tr>
				<tr>
					<td align="right">
						Life Expectancy Calculation :
					</td>
					<td align="left">
						<teag:select name="lifeExpCalc" options='<%=InputMaps.lifeExp%>'
							optionValue="${retire.lifeExpCalc }" />
					</td>
				</tr>
				<tr>
					<td align="right">
						Notes :
					</td>
					<td align="left">
						<textarea name="notes">${retire.notes }</textarea>
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
