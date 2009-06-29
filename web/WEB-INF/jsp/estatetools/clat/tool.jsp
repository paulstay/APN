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
		<title>Clat Setup Page</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<link rel="stylesheet" type="text/css" href="<%=path%>/css/styles.css">
	</head>
	<jsp:useBean id='clat' scope='session' type='com.teag.estate.ClatTool' />
	<jsp:useBean id='epg' scope='session'
		type='com.teag.webapp.EstatePlanningGlobals' />
	<body>
		<%@include file="/inc/aesHeader.jspf"%>
		<h3 align='center'>
			Charitable Lead Annuity Trust
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
						action='servlet/ClatToolServlet'>
						<teag:hidden name='pageView' value='noUpdate' />
						<table align='left'>
							<tr>
								<td>
									Trust Type
								</td>
								<td>
									<teag:select name='trustType'
										onChange="javascript: document.iform.pageView.value='PROCESS'; document.iform.submit()">
										<teag:option label="Term Certain" value="T"
											selected='${clat.clatType}' />
										<teag:option label="Life" value="L"
											selected='${clat.clatType}' />
										<teag:option label="Shorter Of" value="S"
											selected='${clat.clatType}' />
									</teag:select>
								</td>
							</tr>
							<c:if test="${clat.clatType != 'L'}">
								<tr>
									<td>
										Term Length :
									</td>
									<td>
										<teag:text fmt='number' name='term' value='${clat.term}' />
									</td>
								</tr>
								</c:if>
								<c:if test="${clat.clatType != 'T' }">
									<tr>
										<td>
											Life Type
										</td>
										<td>
											<teag:select name='lifeType'
												onChange="javascript: document.iform.pageView.value='PROCESS'; document.iform.submit()">
												<teag:option label="Last to Die" value="L"
													selected='${clat.lifeType}' />
												<teag:option label="First to Die" value="F"
													selected='${clat.lifeType}' />
											</teag:select>
										</td>
									</tr>
									<tr>
										<td>
											Age(s)
										</td>
										<td>
											(${epg.clientAge}) (${epg.spouseAge})
										</td>
									</tr>
								</c:if>
								<tr>
									<td>
										Irs 7520 rate :
									</td>
									<td>
										<teag:text fmt='percent' pattern='##.###%' name='irsRate'
											value='${clat.afrRate }' />
									</td>
								</tr>
								<tr>
									<td>
										IRS 750 Date of Transfer :
									</td>
									<td>
										<teag:text name='afrDate' value='${clat.afrDate}' />
									</td>
								</tr>
								<tr>
									<td>
										Calculation Type :
									</td>
									<td>
										<teag:select name='calcType'
											onChange="javascript: document.iform.pageView.value='PROCESS'; document.iform.submit()">
											<teag:option label='Annuity Payment' value='A'
												selected='${clat.calcType }' />
											<teag:option label='Remainder Interest' value='R'
												selected='${clat.calcType }' />
											<teag:option label='Zero Remainder Interest' value='Z'
												selected='${clat.calcType }' />
										</teag:select>
									</td>
								</tr>
								<tr>
									<c:if test="${clat.calcType == 'A' }">
										<td>
											Annuity Payment :
										</td>
										<td>
											<teag:text name='annuity' fmt='number' pattern="###,###,###"
												value='${clat.annuity}' />
										</td>
									</c:if>
									<c:if test="${clat.calcType == 'R' }">
										<td>
											Remainder Interest :
										</td>
										<td>
											<teag:text name='remainderInterest' fmt='number'
												pattern="###,###,###" value='${clat.remainderInterest }' />
										</td>
									</c:if>
									<c:if test="${clat.calcType == 'Z' }">
										<td>
											Zero Out Clat :
										</td>
										<td>
											No Remainder Interest
										</td>
									</c:if>
								</tr>
								<tr>
									<td>
										Annuity Payment Freq. :
									</td>
									<td>
										<teag:select name='annuityFreq'>
											<teag:option label='Annual' value='1.0'
												selected='${clat.annuityFreq}' />
											<teag:option label='SemiAnnual' value='2.0'
												selected='${clat.annuityFreq}' />
											<teag:option label='Quarterly' value='4.0'
												selected='${clat.annuityFreq}' />
											<teag:option label='Monthly' value='12.0'
												selected='${clat.annuityFreq}' />
										</teag:select>
									</td>
								</tr>
								<tr>
									<td>
										Annuity Payment at
									</td>
									<td>
										<teag:select name='annuityType'>
											<teag:option label='End' value='0'
												selected='${clat.annuityType}' />
											<teag:option label='Begin' value='1'
												selected='${clat.annuityType}' />
										</teag:select>
									</td>
								</tr>
								<tr>
									<td>
										Annuity Increase (%)
									</td>
									<td>
										<teag:text fmt="percent" name="annuityIncrease"
											value="${clat.annuityIncrease }" />
									</td>
								</tr>
								<tr>
									<td>
										Is this a Grantor Turst
									</td>
									<td>
										<teag:select name='grantorFlag'>
											<teag:option label='Yes' value="Y"
												selected='${clat.grantorFlag}' />
											<teag:option label='No' value="N"
												selected='${clat.grantorFlag}' />
										</teag:select>
									</td>
								</tr>
								<tr>
									<td>
										Fed and State Income Tax Rate :
									</td>
									<td>
										<teag:text fmt='percent' pattern="##.###%"
											name='incomeTaxRate' value='${clat.incomeTaxRate }' />
									</td>
								</tr>
								<tr>
									<td>
										Estate Tax Rate (%) :
									</td>
									<td>
										<teag:text fmt='percent' name='estateTaxRate'
											value='${clat.estateTaxRate }' />
									</td>
								</tr>
								<tr>
									<td colspan='3' align='center'>
										<input type="image" src="toolbtns/Update.png"
											alt="Update Tool"
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
