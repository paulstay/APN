<%@ page language="java" pageEncoding="ISO-8859-1"%>
<%@ page import="com.estate.constants.*"%>
<%@ page import="java.util.*"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="teagtags" prefix="teag"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
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
		<jsp:useBean id="realEstate" scope='request'
			type="com.teag.bean.RealEstateBean" />
		<jsp:useBean id="person" scope="session"
			class="com.teag.bean.PersonBean" />
		<c:if test="${btn == 'Add' }">
			<c:set var="btn1" value='Add' />
		</c:if>
		<c:if test="${btn == 'Edit' }">
			<c:set var="btn1" value='Edit' />
		</c:if>
		<%@ include file="/inc/aesHeader.jspf"%>
		<%@ include file="/inc/aesQuickJump.jspf"%>
		<h3 align='center'>
			Real Estate Input
		</h3>
		<br>
		<teag:form action="servlet/RealEstateAssetServlet" method="post"
			name="iform">
			<input type="hidden" name='action' value='cancel' />
			<input type="hidden" name='id' value='${realEstate.id}' />
			<input type="hidden" name="ownerId" value='${person.id }' />
			<table align='center'>
				<tr>
					<td align='right'>
						Description :
					</td>
					<td align='left'>
						<teag:text name="description" value="${realEstate.description}" />
					</td>
				</tr>
				<tr>
					<td align="right">
						Ownership :
					</td>
					<td align="left">
						<teag:select name="ownershipId" options="${ownerOptions}"
							optionValue="${realEstate.ownershipId}" />
				</tr>
				<tr>
					<td align="right">
						Title :
					</td>
					<td align="left">
						<teag:select name="titleId" options="${titleOptions}"
							optionValue="${realEstate.titleId}" />
					</td>
				</tr>
				<tr>
					<td align='center' colspan='2'>
						Location of Real Estate
					</td>
				</tr>
				<tr>
					<td align="right">
						City
					</td>
					<td align='left'>
						<teag:text name='city' value='${realEstate.locationCity}' />
					</td>
				</tr>
				<tr>
					<td align="right">
						State
					</td>
					<td align="left">
						<teag:select name="state" options="${stateOptions}"
							optionValue="${realEstate.locationState}" />
					</td>
				</tr>
				<tr>
					<td align='center' colspan='2' class='text-bold'>
						Details
					</td>
				</tr>
				<tr>
					<td align="right">
						Client's Share of Value :
					</td>
					<td align="left">
						<teag:text name="value" fmt="number" pattern="###,###,###"
							value="${realEstate.value }" />
					</td>
				</tr>
				<tr>
					<td align="right">
						Client's Share of Basis :
					</td>
					<td align="left">
						<teag:text name="basis" fmt="number" pattern="###,###,###"
							value="${realEstate.basis}" />
					</td>
				</tr>
				<tr>
					<td align="right">
						Growth Rate :
					</td>
					<td align="left">
						<teag:text name="growth" fmt="number" pattern="##.###%"
							value="${realEstate.growthRate}" />
					</td>
				</tr>
				<tr>
					<td align='right'>
						Gross Rents (Yearly) :
					</td>
					<td align='left'>
						<teag:text name='grossRents' fmt="number" pattern="###,###,###"
							value='${realEstate.grossRents}' />
					</td>
				</tr>
				<tr>
					<td align='right'>
						Gross Rents Growth (%) :
					</td>
					<td align='left'>
						<teag:text name='grossRentsGrowth' fmt="number" pattern="##.###%"
							value='${realEstate.grossRentsGrowth}' />
					</td>
				</tr>
				<tr>
					<td align='right'>
						Operating Expenses ($) :
					</td>
					<td align='left'>
						<teag:text name='operatingExpenses' fmt="number"
							pattern="###,###,###" value='${realEstate.operatingExpenses}' />
					</td>
				</tr>
				<tr>
					<td align='right'>
						Operating Expenses Growth (%) :
					</td>
					<td align='left'>
						<teag:text name='growthExpenses' fmt="percent"
							value='${realEstate.growthExpenses}' />
					</td>
				</tr>
				<tr>
					<td align='right'>
						Loan Balance :
					</td>
					<td align='left'>
						<teag:text name="loanBalance" fmt="number" pattern="###,###,###"
							value="${realEstate.loanBalance}" />
					</td>
				</tr>
				<tr>
					<td align='right'>
						Loan Interest Rate (%) :
					</td>
					<td align='left'>
						<teag:text name="loanInterest" fmt="number" pattern="##.###%"
							value="${realEstate.loanInterest}" />
					</td>
				</tr>
				<tr>
					<td align='right'>
						Loan Payment :
					</td>
					<td align='left'>
						<teag:text name="loanPayment" fmt="number" pattern="###,###,###"
							value="${realEstate.loanPayment}" />
					</td>
				</tr>
				<tr>
					<td align="right">
						Loan Freq. (# of payments per Year)
					</td>
					<td align="left">
						<teag:select name="loanFreq">
							<teag:option label="Yearly" value="1"
								selected="${realEstate.loanFreq}" />
							<teag:option label="Semi Yearly" value="2"
								selected="${realEstate.loanFreq}" />
							<teag:option label="Quarterly" value="4"
								selected="${realEstate.loanFreq}" />
							<teag:option label="Monthly" value="12"
								selected="${realEstate.loanFreq}" />
						</teag:select>
				</tr>
				<tr>
					<td align='right'>
						Loan Term (Years Remaining) :
					</td>
					<td align='left'>
						<teag:text name="loanTerm" fmt="number" pattern="###"
							value="${realEstate.loanTerm}" />
					</td>
				</tr>
				<tr>
					<td align='center' class='text-bold' colspan='2'>
						General Notes
					</td>
				</tr>
				<tr>
					<td align='center' colspan='2'>
						Depreciation
					</td>
				</tr>
				<tr>
					<td align='right'>
						Current Depreciation Value
					</td>
					<td align='left'>
						<teag:text name='depreciationValue' fmt="number" pattern='###,###,###'
							value='${realEstate.depreciationValue }' />
				</tr>
				<tr>
					<td align='right'>
						Salvage Value
					</td>
					<td align='left'>
						<teag:text name='salvageValue' fmt="number" pattern='###,###,###'
							value='${realEstate.salvageValue }' />
				</tr>
				<tr>
					<td align='right'>
						Liquidation Year
					</td>
					<td align='left'>
						<teag:text name='depreciationYears' fmt="number" pattern='###'
							value='${realEstate.depreciationYears }' />
				</tr>
				<tr>
					<td align="right">
						Notes :
					</td>
					<td align="left">
						<textarea name="notes" rows='10' cols='40'>${realEstate.notes}</textarea>
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
