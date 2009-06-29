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
		<title>Irrevocable Life Insurance Trust</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<link rel="stylesheet" type="text/css" href="<%=path%>/css/styles.css">
	</head>
	<jsp:useBean id='crum' scope='session' type='com.teag.estate.CrummeyTool' />
	<body>
		<%@include file="/inc/aesHeader.jspf"%>
		<h3 align='center'>
			Irrevocable Life Insurance Trust
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
				<td valign='top'>
					<%@ include file="menu.jspf"%>
				</td>
				<td>
					<teag:form name='iform' method='post'
						action='servlet/CrumToolServlet'>
						<teag:hidden name='pageView' value='noUpdate' />
						<table align='left'>
							<tr>
								<td colspan='2' align='center'><h3>ILIT Tool Values</h3></td>
							</tr>
							<tr>
								<td>Annual Gift</td>
								<td>
									<teag:text name="annualGift" fmt="number" pattern="###,###,###" value="${crum.annualGift}"/>
								</td>
							</tr>
							<tr>
								<td>Number of years to pay gift</td>
								<td>
									<teag:text name="term" fmt="number" pattern="###" value="${crum.term}"/>
								</td>
							</tr>
							<tr>
								<td>Total of Life Insurance Premium(s)/year</td>
								<td>
									<teag:text name="lifePremium" fmt="number" pattern="###,###,###" value="${crum.lifePremium}"/>
								</td>
							</tr>
							<tr>
								<td>Total of Life Death Benefit(s)/year</td>
								<td>
									<teag:text name="lifeDeathBenefit" fmt="number" pattern="###,###,###" value="${crum.lifeDeathBenefit}"/>
								</td>
							</tr>
							<tr>
								<td>Use with MGT</td>
								<td>
									<c:set var="a1" value="F"/>
									<c:if test="${crum.withMgt}">
										<c:set var="a1" value="T"/>
									</c:if>
									<teag:select name="withMgt">
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
