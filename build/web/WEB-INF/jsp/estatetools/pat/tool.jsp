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
		<title>Private Annuity Setup Page</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<link rel="stylesheet" type="text/css" href="<%=path%>/css/styles.css">
	</head>
	<jsp:useBean id='pat' scope='session' type='com.teag.estate.PrivateAnnuityTool' />
	<body>
		<%@include file="/inc/aesHeader.jspf"%>
		<h3 align='center'>
			Private Annuity
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
						action='servlet/PatToolServlet'>
						<teag:hidden name='pageView' value='noUpdate' />
						<table align='left'>
							<tr>
								<td>Description</td>
								<td>
									<teag:text name="description" value="${pat.description }"/>
								</td>
							</tr>
							<tr>
								<td>
									Irs 7520 rate :
								</td>
								<td>
									<teag:text fmt='percent' pattern='##.###%' name='afrRate'
										value='${pat.afrRate }' />
								</td>
							</tr>
							<tr>
								<td>
									IRS 750 Date of Transfer :
								</td>
								<fmt:formatDate var='idate' pattern='M/dd/yyyy' value='${pat.afrDate}'/>
								<td>
									<teag:text name='afrDate' value='${idate}' />
								</td>
							</tr>
							<tr>
								<td>Income Tax Rate (%)</td>
								<td>
									<teag:text name="incomeTaxRate" fmt="percent" pattern="##.###%" value="${pat.incomeTaxRate}"/>
								</td>
							</tr>
							<tr>
								<td>Estate Tax Rate (%)</td>
								<td>
									<teag:text name="estateTaxRate" fmt="percent" pattern="##.###%" value="${pat.estateTaxRate}"/>
								</td>
							</tr>
							<tr>
								<td>Capital Gains Rate</td>
								<td>
									<teag:text name="capitalGainsRate" fmt="percent" pattern="##.###%" value="${pat.capitalGainsRate }"/>
								</td>
							</tr>
							<tr>
								<td>Annuity Increase</td>
								<td>
									<teag:text name="annuityIncrease" fmt="percent" pattern="##.###%" value="${pat.annuityIncrease}"/>
								</td>
							</tr>
							<tr>
								<td>Pay at Begin or End</td>
								<td>
									<teag:select name="annuityType">
										<teag:option label="Begin" value="1" selected="${pat.annuityType}"/>
										<teag:option label="End" value="0" selected="${pat.annuityType}"/>
									</teag:select>
								</td>
							</tr>
							<tr>
								<td>Number of Payments per year</td>
								<td>
									<teag:select name="annuityFreq">
										<teag:option label="Annual" value="1" selected="${pat.annuityFreq}"/>
										<teag:option label="Semi-Annual" value="2" selected="${pat.annuityFreq}"/>
										<teag:option label="Quarterly" value="4" selected="${pat.annuityFreq}"/>
										<teag:option label="Monthly" value="12" selected="${pat.annuityFreq}"/>
									</teag:select>
								</td>
							</tr>
							<tr>
								<td>Annuity Payment Type</td>
								<td>
									<teag:select name="useBoth">
										<teag:option label="Both" value="B" selected="${pat.useBoth }"/>
										<teag:option label="Client" value="C" selected="${pat.useBoth }"/>
										<teag:option label="Spouse/Partner" value="S" selected="${pat.useBoth }"/>
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
