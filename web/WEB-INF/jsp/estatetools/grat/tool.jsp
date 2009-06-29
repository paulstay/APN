<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
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
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<base href="<%=basePath%>">
		<title>Grat Setup Page</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<link rel="stylesheet" type="text/css" href="<%=path%>/css/styles.css">
	</head>
	<jsp:useBean id='grat' scope='session' type='com.teag.estate.GratTool' />
	<body>
		<%@include file="/inc/aesHeader.jspf"%>
		<h3 align='center'>
			Grantor Retained Annuity Trust
		</h3>
		<table align='center' width='100%'>
			<tr>
				<td colspan='2' align='center'>
					<h3>
						Tool Setup
					</h3>
				</td>
			</tr>
			<tr>
				<td>
					<%@ include file="menu.jspf"%>
				</td>
				<td>
					<teag:form name='iform' method='post'
						action='servlet/GratToolServlet'>
						<teag:hidden name='pageView' value='noUpdate' />
						<table align='left'>
							<tr>
								<td>
									Number of Trusts :
								</td>
								<td>
									<teag:select name='numTrusts'
										onChange="javascript: document.iform.pageView.value='TRUST'; document.iform.submit()">
										<teag:option label='1' value='1' selected='${grat.numTrusts }' />
										<teag:option label='2' value='2' selected='${grat.numTrusts }' />
									</teag:select>
								</td>
							</tr>
							<tr>
								<td>
									Use Life Insurance :
								</td>
								<td>
									<teag:select name='useLife'
										onChange="javascript: document.iform.pageView.value='LIFE'; document.iform.submit()">
										<teag:option label='Y' value='true'
											selected='${grat.useLife }' />
										<teag:option label='N' value='false'
											selected='${grat.useLife }' />
									</teag:select>
								</td>
							</tr>
							<tr>
								<td>
									IRS 7520 rate :
								</td>
								<td>
									<teag:text fmt='percent' pattern='##.###%' name='irsRate'
										value='${grat.afrRate }' />
								</td>
							</tr>
							<tr>
								<td>
									IRS 7520 Date of Transfer :
								</td>
								<td>
									<teag:text name='afrDate' value='${grat.afrDate}' />
								</td>
							</tr>
							<tr>
								<td>
									Calculation Type :
								</td>
								<td>
									<teag:select name='calcType'
										onChange="javascript: document.iform.pageView.value='CTYPE'; document.iform.submit()">
										<teag:option label='Annuity Payment' value='A'
											selected='${grat.calcType }' />
										<teag:option label='Remainder Interest' value='R'
											selected='${grat.calcType }' />
										<teag:option label='Zero Out Grat' value='Z'
											selected='${grat.calcType }' />
									</teag:select>
								</td>
							</tr>
							<tr>
								<c:if test="${grat.calcType == 'A' }">
									<td>
										Annuity Payment :
									</td>
									<td>
										<teag:text name='annuity' fmt='number' value='${grat.annuity}' />
									</td>
								</c:if>
								<c:if test="${grat.calcType == 'R' }">
									<td>
										Remainder Interest :
									</td>
									<td>
										<teag:text name='remainderInterest' fmt='number'
											value='${grat.remainderInterest }' />
									</td>
								</c:if>
								<c:if test="${grat.calcType == 'Z' }">
									<td>
										Zero Out Grat :
									</td>
									<td>
										$000,000
									</td>
								</c:if>
							</tr>
							<tr>
								<td>
									Annuity Payment Freq. :
								</td>
								<td>
									<teag:select name='annuityFreq'>
										<teag:option label='Annual' value='1'
											selected='${grat.annuityFreq}' />
										<teag:option label='SemiAnnual' value='2'
											selected='${grat.annuityFreq}' />
										<teag:option label='Quarterly' value='3'
											selected='${grat.annuityFreq}' />
										<teag:option label='Monthly' value='12'
											selected='${grat.annuityFreq}' />
									</teag:select>
								</td>
							</tr>
				<!-- 
							<tr>
								<td>
									Annuity Increase (% per year)
								</td>
								<td>
									<teag:text pattern="##.###%" name="annuityIncrease" value="${grat.annuityIncrease }"/>
								</td>
							</tr>
				-->
							<tr>
								<td>
									Client Term Length :
								</td>
								<td>
									<teag:text fmt='number' name='clientTermLength'
										value='${grat.clientTermLength }' />
								</td>
							</tr>
							<tr>
								<td>
									Client Start Term :
								</td>
								<td>
									<teag:text fmt='number' name='clientStartTerm'
										value='${grat.clientStartTerm }' />
								</td>
							</tr>
							<tr>
								<td>
									Client Prior Gifts :
								</td>
								<td>
									<teag:text fmt='number' name='clientPriorGifts'
										value='${grat.clientPriorGifts }' />
								</td>
							</tr>
							<c:if test="${grat.numTrusts > 1 }">
								<tr>
									<td>
										Spouse Term Length :
									</td>
									<td>
										<teag:text fmt='number' name='spouseTermLength'
											value='${grat.spouseTermLength }' />
									</td>
								</tr>
								<tr>
									<td>
										Spouse Start Term :
									</td>
									<td>
										<teag:text fmt='number' name='sposueStartTerm'
											value='${grat.spouseStartTerm }' />
									</td>
								</tr>
								<tr>
									<td>
										Spouse Prior Gifts :
									</td>
									<td>
										<teag:text fmt='number' name='spousePriorGifts'
											value='${grat.spousePriorGifts }' />
									</td>
								</tr>
							</c:if>
							<tr>
								<td>
									Fed and State Income Tax Rate :
								</td>
								<td>
									<teag:text fmt="number" pattern="##.##%" name='incomeTaxRate'
										value='${grat.incomeTaxRate }' />
								</td>
							</tr>
							<tr>
								<td>
									Estate Tax Rate (%) :
								</td>
								<td>
									<teag:text fmt='number' pattern="##.##%" name='estateTaxRate'
										value='${grat.estateTaxRate }' />
								</td>
							</tr>
							<c:if test="${grat.useLife }">
								<tr>
									<td>
										Life Insurance Premium :
									</td>
									<td>
										<teag:text fmt='number' name='lifeInsPremium'
											value='${grat.lifeInsPremium }' />
									</td>
								</tr>
								<tr>
									<td>
										Life Insurance Death Benefit :
									</td>
									<td>
										<teag:text fmt='number' name='lifeDeathBenefit'
											value='${grat.lifeDeathBenefit }' />
									</td>
								</tr>
								<tr>
									<td>
										Life Insurance Cash Value :
									</td>
									<td>
										<teag:text fmt='number' name='lifeCashValue'
											value='${grat.lifeCashValue }' />
									</td>
								</tr>
							</c:if>
							<tr>
								<td colspan='3' align='center'>
									<input type="image" src="toolbtns/Update.png" alt="Update Tool"
										onclick="document.iform.pageView.value='PROCESS'"
										onmouseover="this.src='toolbtns/Update_over.png';"
										onmouseout="this.src='toolbtns/Update.png';"
										onmousedown="this.src='toolbtns/Update_down.png';" />
								</td>
							</tr>
						</table>
					</teag:form>
				</td>
			</tr>
		</table>
	</body>
</html>
