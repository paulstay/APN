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
		<title>Qulaified Personal Residence Trust Setup Page</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<link rel="stylesheet" type="text/css" href="<%=path%>/css/styles.css">
	</head>
	<jsp:useBean id='qprt' scope='session' type='com.teag.estate.QprtTool' />
	<body>
		<%@include file="/inc/aesHeader.jspf"%>
		<h3 align='center'>
			Qualified Personal Residence Trust
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
						action='servlet/QprtToolServlet'>
						<teag:hidden name='pageView' value='noUpdate' />
						<table align='left'>
							<tr>
								<td>Select the Number of Trusts</td>
								<td>
									<teag:select name='numberOfTrusts' onChange="document.iform.pageView.value='PROCESS';document.iform.submit()" >
										<teag:option label='1' value='1' selected='${qprt.numberOfTrusts }'/>
										<teag:option label='2' value='2' selected='${qprt.numberOfTrusts }'/>
									</teag:select>
								</td>
							</tr>
							<tr>
								<td>
									Irs 7520 rate :
								</td>
								<td>
									<teag:text fmt='percent' pattern='##.###%' name='irsRate'
										value='${qprt.afrRate }' />
								</td>
							</tr>
							<tr>
								<td>
									IRS 750 Date of Transfer :
								</td>
								<fmt:parseDate var='sDate' pattern='M/dd/yyyy' value="${qprt.afrDate}"/>
								<fmt:formatDate var='idate' pattern='M/dd/yyyy' value='${sDate}'/>
								<td>
									<teag:text name='afrDate' value='${idate}' />
								</td>
							</tr>
							<tr>
								<td>Client Term Length :</td>
								<td>
									<teag:text name='clientTerm' pattern="###" value="${qprt.clientTerm}"/>
								</td>
							</tr>
							<tr>
								<td>Client Start Term</td>
								<td>
									<teag:text name='clientStartTerm' pattern="###" value="${qprt.clientStartTerm}"/>
								</td>
							</tr>
							<tr>
								<td>Client Prior Gifts</td>
								<td>
									<teag:text name='clientPriorGifts' fmt="number" pattern="###,###,###" value="${qprt.clientPriorGifts }"/>
								</td>
							</tr>
							<c:if test="${qprt.numberOfTrusts > 1 }">
								<tr>
									<td>Spouse Term Length :</td>
									<td>
										<teag:text name='spouseTerm' pattern="###" value="${qprt.spouseTerm}"/>
									</td>
								</tr>
								<tr>
									<td>Spouse Start Term</td>
									<td>
										<teag:text name='spouseStartTerm' pattern="###" value="${qprt.spouseStartTerm}"/>
									</td>
								</tr>
								<tr>
									<td>Spouse Prior Gifts</td>
									<td>
										<teag:text name='spousePriorGifts' fmt="number" pattern="###,###,###" value="${qprt.spousePriorGifts }"/>
									</td>
								</tr>
							</c:if>
							<tr>
								<td>Home Type : </td>
								<td>
									<teag:select name='typeOfHome' >
										<teag:option label='Vacation' value='1' selected='${qprt.typeOfHome}'/>
										<teag:option label='Residence' value='2' selected='${qprt.typeOfHome}'/>
									</teag:select>
								</td>
							</tr>
							<tr>
								<td>Fractional Interest Discount</td>
								<td>
									<teag:text name='fractionalDiscountRate' fmt="percent" pattern="##.###%" value="${qprt.fractionalInterestDiscount}"/>
								</td>
							</tr>
							<tr>
								<td>
									Estate Tax Rate (%) :
								</td>
								<td>
									<teag:text fmt='percent' name='estateTaxRate'
										value='${qprt.estateTaxRate }' />
								</td>
							</tr>
							<tr>
								<td>Rent after Term</td>
								<td>
									<teag:text fmt='percent' name='rentAfterTerm' value='${qprt.rentAfterTerm}'/>
								</td>
							</tr>
							<tr>
								<td>Reversion Retained</td>
								<td>
									<teag:select name="reversion">
										<c:choose>
											<c:when test="${qprt.revisionRetained}">
												<c:set var="rTest" value="True"/>
											</c:when>
											<c:otherwise>
												<c:set var="rTest" value="False"/>
											</c:otherwise>
										</c:choose>
										<teag:option label="Yes" value='True' selected='${rTest}'/>
										<teag:option label="No" value='False' selected='${rTest}'/>
									</teag:select>
								</td>
							</tr>
							<tr>
								<td>Assumed Date of Second Death</td>
								<td>
									<teag:text name='finalDeath' value='${qprt.finalDeath }'/>
								</td>
							</tr>
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
