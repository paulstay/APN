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
		<title>IDIT Grat Combo Setup Page</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<link rel="stylesheet" type="text/css" href="<%=path%>/css/styles.css">
	</head>
	<jsp:useBean id='idit' scope='session'
		type='com.teag.estate.IditTool' />
	<body>
		<%@include file="/inc/aesHeader.jspf"%>
		<h3 align='center'>
			Intentionally Deffective Irrevocable Trust with Grat Combination
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
						action='servlet/IditGratToolServlet'>
						<teag:hidden name='pageView' value='noUpdate' />
						<table align='left'>
							<tr>
								<td>
									Note Rate (Use AFR Long Term) :
								</td>
								<td>
									<teag:text fmt='number' pattern='##.###%'
										name='noteInterest' value='${idit.noteInterest }' />
								</td>
							</tr>
							<tr>
								<td>
									Note Term
								</td>
								<td>
									<teag:text name='noteLength' fmt="number" pattern="###" value='${idit.noteLength}' />
								</td>
							</tr>
							<tr>
								<td>
									State Tax Rate
								<td>
									<teag:text name="stateIncomeTax" fmt="number" pattern="##.###%"
										value="${idit.stateIncomeTax }" />
								</td>
							</tr>
							<tr>
								<td>
									Assumed Date of Second Death for Comparison
								</td>
								<td>
									<teag:text name="finalDeath" fmt="number" pattern="###"
										value="${idit.finalDeath }" />
								</td>
							</tr>
							<tr>
								<td colspan='2'>&nbsp;</td>
							</tr>
							<tr>
								<td colspan='2' align='center'>
									<h3>GRAT Values</h3>
								</td>
							</tr>
							<tr>
								<td>Use Insurance</td>
								<c:choose>
									<c:when test="${idit.useInsurance}">
										<c:set var='ins' value='true'/>
									</c:when>
									<c:otherwise>
										<c:set var='ins' value='false'/>
									</c:otherwise>
								</c:choose>
								<td>
									<teag:select name="useInsurance" onChange="javascript: document.iform.pageView.value='LIFE'; document.iform.submit()">
										<teag:option label="Yes" value="true" selected="${ins}"/>
										<teag:option label="No" value="false" selected="${ins}"/>
									</teag:select>
								</td>
							</tr>
							<tr>
								<td>
									Section 7520 Rate
								</td>
								<td>
									<teag:text name="afrRate" fmt="number" pattern="##.####%" value="${idit.afrRate}" />
								</td>
							</tr>
							<tr>
								<td>
									Section 7520 Date (M/d/yyyy)
								</td>
								<td>
									<teag:text name="afrDate" value="${idit.afrDate}" />
								</td>
							</tr>
							<tr>
								<td>
									GRAT Term
								</td>
								<td>
									<teag:text name="gratTerm" fmt="number" pattern="###"
										value="${idit.gratTerm}" />
								</td>
							</tr>
							<tr>
								<td>
									Grat Payment Freq
								</td>
								<td>
									<teag:select name="annuityFreq">
										<teag:option label="Annual" value="1"
											selected="${idit.annuityFreq}" />
										<teag:option label="Semi Annual" value="1"
											selected="${idit.annuityFreq}" />
										<teag:option label="Quarterly" value="1"
											selected="${idit.annuityFreq}" />
										<teag:option label="Monthly" value="1"
											selected="${idit.annuityFreq}" />
									</teag:select>
								</td>
							</tr>
							<tr>
								<td>
									Grat GP or Managing Member Interests (%)
								</td>
								<td>
									<teag:text name="noteFlpGPInterest" fmt="percent"
										value="${idit.noteFlpGPInterest}" />
								</td>
							</tr>
							<tr>
								<td>
									Grat GP or Managing Member Interests (%)
								</td>
								<td>
									<teag:text name="noteFlpLPInterest" fmt="percent"
										value="${idit.noteFlpLPInterest}" />
								</td>
							</tr>
							<tr>
								<td>
									Discount (%)
								</td>
								<td>
									<teag:text name="noteFlpDiscount" fmt="percent"
										value="${idit.noteFlpDiscount}" />
								</td>
							</tr>
							<c:if test="${idit.useInsurance }">
								<tr>
									<td>
										Life Insurance Premium
									</td>
									<td>
										<teag:text name="lifePremium" fmt="number"
											pattern="###,###,###" value="${idit.lifePremium }" />
									</td>
								</tr>
								<tr>
									<td>
										Life Insurance Death Benefit
									</td>
									<td>
										<teag:text name="lifeDeathBenefit" fmt="number"
											pattern="###,###,###" value="${idit.lifeDeathBenefit }" />
									</td>
								</tr>
								<tr>
									<td>
										Life Cash Value at end of Grat Term
									</td>
									<td>
										<teag:text name="lifeCashValue" fmt="number" pattern="###,###,###"
											value="${idit.lifeCashValue }" />
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
