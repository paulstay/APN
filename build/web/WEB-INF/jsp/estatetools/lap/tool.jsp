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
		<title>Lap Setup Page</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<link rel="stylesheet" type="text/css" href="<%=path%>/css/styles.css">
	</head>
	<jsp:useBean id='lap' scope='session' type='com.teag.estate.LiquidAssetProtectionTool' />
	<body>
		<%@include file="/inc/aesHeader.jspf"%>
		<h3 align='center'>
			Liquid Asset Protection Tool
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
						action='servlet/LapToolServlet'>
						<teag:hidden name='pageView' value='noUpdate' />
						<table align='left'>
							<tr>
								<td>Description</td>
								<td>
									<teag:text name="description" value="${lap.description }"/>
								</td>
							</tr>
							<tr>
								<td>Monthly Annuity Payment</td>
								<td>
									<teag:text name="annuityMonthlyIncome" fmt="number" pattern="###,###,###" value="${lap.annuityMonthlyIncome }"/>
								</td>
							</tr>
							<tr>
								<td>Annuity Exclusion Ration</td>
								<td>
									<teag:text name="annuityExclusionRatio" fmt="percent" pattern="##.###%" value="${lap.annuityExclusionRatio}"/>
								</td>
							</tr>
							<tr>
								<td>Income Tax Rate</td>
								<td>
									<teag:text name="incomeTaxRate" fmt="percent" value="${lap.incomeTaxRate}"/>
								</td>
							</tr>
							<tr>
								<td>Estate Tax Rate</td>
								<td>
									<teag:text name="estateTaxRate" fmt="percent" value="${lap.estateTaxRate}"/>
								</td>
							</tr>
							<tr>
								<td>Life Insurance Death Benefit</td>
								<td>
									<teag:text name="lifeFaceValue" fmt="number" pattern="###,###,###" value="${lap.lifeFaceValue}"/>
								</td>
							</tr>
							<tr>
								<td>Life Preimum (Yearly)</td>
								<td>
									<teag:text name="lifePremium" fmt="number" pattern="###,###,###" value="${lap.lifePremium}"/>
								</td>
							</tr>
							<tr>
								<td>Is Life Insurance Overfunded</td>
								<c:set var="a1" value="F"/>
								<c:if test="${lap.overFunded}">
									<c:set var="a1" value="T"/>
								</c:if>
								<td>
									<teag:select name="overFunded">
										<teag:option label="Yes" value="T" selected="${a1}"/>
										<teag:option label="No" value="F" selected="${a1}"/>
									</teag:select>
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
