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
		<title>Rpm Setup Page</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<link rel="stylesheet" type="text/css" href="<%=path%>/css/styles.css">
	</head>
	<jsp:useBean id='rpm' scope='session' type='com.teag.estate.RpmTool' />
	<body>
		<%@include file="/inc/aesHeader.jspf"%>
		<h3 align='center'>
			Retirement Plan Maximizer &trade;
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
						action='servlet/RpmToolServlet'>
						<teag:hidden name='pageView' value='noUpdate' />
						<table align='left'>
							<tr>
								<td>Distribution Years</td>
								<td>
									<teag:text name="term" pattern="###" fmt="number" value="${rpm.term}"/>
								</td>
							</tr>
							<tr>
								<td>State Income Tax Rate</td>
								<td>
									<teag:text fmt="percent" pattern="##.##%" name="stateIncomeTaxRate" value="${rpm.stateIncomeTaxRate}"/>
								</td>
							</tr>
							<tr>
								<td>Life Insurance Premium (year)</td>
								<td>
									<teag:text fmt="number" pattern="###,###,###" name="lifePremium" value="${rpm.lifePremium}"/>
								</td>
							</tr>
							<tr>
								<td>Life Insurance Death Benefit</td>
								<td>
									<teag:text fmt="number" pattern="###,###,###" name="lifeDeathBenefit" value="${rpm.lifeDeathBenefit}"/>
								</td>
							</tr>
							<tr>
								<td>Trust Securities Growth Rate</td>
								<td>
									<teag:text fmt="percent" pattern="##.###%" name="securitiesGrowth" value="${rpm.securitiesGrowth }"/>
								</td>
							</tr>
							<tr>
								<td>Securities Dividends Rate</td>
								<td>
									<teag:text fmt="percent" pattern="##.###%" name="securitiesIncome" value="${rpm.securitiesIncome}"/>
								</td>
							</tr>
							<tr>
								<td>Securities Turnover Rate</td>
								<td>
									<teag:text fmt="percent" pattern="##.###%" name="securitiesTurnover" value="${rpm.securitiesTurnover}"/>
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
