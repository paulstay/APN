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
		<title>IDIT Setup Page</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<link rel="stylesheet" type="text/css" href="<%=path%>/css/styles.css">
	</head>
	<jsp:useBean id='sidit' scope='session' type='com.teag.estate.SIditTool' />
	<body>
		<%@include file="/inc/aesHeader.jspf"%>
		<h3 align='center'>
			Intentionally Deffective Irrevocable Trust
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
						action='servlet/IditToolServlet'>
						<teag:hidden name='pageView' value='noUpdate' />
						<teag:hidden name="giftType" value="C"/>
						<table align='left'>
							<tr>
								<td>
									Note Rate (AFR Long Term) :
								</td>
								<td>
									<teag:text fmt='percent' pattern='##.###%' name='noteRate'
										value='${sidit.noteRate }' />
								</td>
							</tr>
							<tr>
								<td>Note Type :</td>
								<td>
									<teag:select name="noteType">
										<teag:option label="Interest + Balloon" value="2" selected="${sidit.noteType}"/>
										<teag:option label="Principal + Interest" value="0" selected="${sidit.noteType}"/>
										<teag:option label="Amoritized" value="1" selected="${sidit.noteType}"/>
									</teag:select>
								</td>
							</tr>
							<tr>
								<td>
									Note Term
								</td>
								<td>
									<teag:text name='noteTerm' value='${sidit.noteTerm}' />
								</td>
							</tr>
							<tr>
								<td>
									Federal and State Tax Rate (combined)
								</td>
								<td>
									<teag:text name="taxRate" fmt="percent" value="${sidit.taxRate }"/>
								</td>
							</tr>
							<tr>
								<td>Number of years for comparison</td>
								<td>
									<teag:text name="finalDeath" fmt="number" pattern="###" value="${sidit.finalDeath }"/>
								</td>
							</tr>
							<tr>
								<td>Life Insurance Premium</td>
								<td>
									<teag:text name="lifePremium" fmt="number" pattern="###,###,###" value="${sidit.lifePremium }"/>
								</td>
							</tr>
							<tr>
								<td>Life Insurance Death Benefit</td>
								<td>
									<teag:text name="lifeDeathBenefit" fmt="number" pattern="###,###,###" value="${sidit.lifeDeathBenefit }"/>
								</td>
							</tr>
							<tr>
								<td>Number of years to pay premium</td>
								<td>
									<teag:text name="lifePremiumYears" fmt="number" pattern="###" value="${sidit.lifePremiumYears }"/>
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
