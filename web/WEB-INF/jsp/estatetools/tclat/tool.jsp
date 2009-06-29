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
		<title>Testamentary Charitable Lead Annuity Trust</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<link rel="stylesheet" type="text/css" href="<%=path%>/css/styles.css">
	</head>
	<jsp:useBean id='tclat' scope='session' type='com.teag.estate.TClatTool' />
	<body>
		<%@include file="/inc/aesHeader.jspf"%>
		<h3 align='center'>
			Testamentary Charitable Lead Annuity Trust
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
				<td align='left'>
					<teag:form name='iform' method='post'
						action='servlet/TClatToolServlet'>
						<teag:hidden name='pageView' value='noUpdate' />
						<table align='center' width='60%'>
							<tr>
								<td>Term Length</td>
								<td>
									<teag:text name="term" fmt="number" pattern="###" value="${tclat.term }"/>
								</td>
							</tr>
							<tr>
								<td>Approximate IRS Section 7520 Rate *</td>
								<td>
									<teag:text name="afrRate" fmt="percent" pattern="##.###%" value="${tclat.afrRate }"/>
								</td>
							</tr>
							<tr>
								<td>Annuity Payout Frequency</td>
								<td>
									<teag:select name="annuityFreq">
										<teag:option label="Yearly" value="1" selected="${tclat.annuityFreq }"/>
										<teag:option label="Semi-Yearly" value="2" selected="${tclat.annuityFreq }"/>
										<teag:option label="Quarterly" value="4" selected="${tclat.annuityFreq }"/>
										<teag:option label="Monthly" value="12" selected="${tclat.annuityFreq }"/>
									</teag:select>
								</td>
							</tr>
							<tr>
								<td>Make payment at End or Begin</td>
								<td>
									<teag:select name="annuityType">
										<teag:option label="End" value="0" selected="${tclat.annuityType}"/>
										<teag:option label="Begin" value="1" selected="${tclat.annuityType}"/>
									</teag:select>
								</td>
							</tr>
							<tr>
								<td colspan='2'>&nbsp;</td>
							</tr>
							<tr>
								<td colspan='2' align='left'>
									<p style="text-align: left">This tool takes the assets remaining in the estate at final death and deducts a remainder equal to the yearly exemption
									amount. The value of the assets are placed in the TCLAT and payments are made.</p>
									<p style="text-align: left; color: red;">* Since we do not know the IRS section 7520 rate at final death, we take a guess.</p>
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
