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
		<title>LLC Setup Page</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<link rel="stylesheet" type="text/css" href="<%=path%>/css/styles.css">
	</head>
	<jsp:useBean id='llc' scope='session' type='com.teag.estate.LlcTool' />
	<body>
		<%@include file="/inc/aesHeader.jspf" %>
		<h3 align='center'>
			Limited Liability Company
		</h3>
		<table align='center' width='100%'>
			<tr>
				<td colspan='2' align='center'>
					<h3>Tool Setup</h3>
				</td>
			</tr>
			<tr>
				<td>
					<%@ include file="menu.jspf"%>
				</td>
				<td>
					<teag:form name='iform' method='post'
						action='servlet/LlcToolServlet'>
						<teag:hidden name='pageView' value='noUpdate' />
						<table align='left'>
							<tr>
								<td align='right'>Description :</td>
								<td>&nbsp;</td>
								<td>
									<input type="text" size="40" name="description" value="${llc.name}"/>
								</td>
							</tr>
							<tr>
								<td align='right'>Manager Member Interests (%) :</td>
								<td>&nbsp;</td>
								<td>
									<fmt:formatNumber var="gp1" pattern="##.###%" value="${llc.managerShares}"/>
									<input type="text" name="managerShares" value="${gp1}"/>
								</td>
							</tr>
							<tr>
								<td align='right'>Member Interests (%) :</td>
								<td>&nbsp;</td>
								<td>
									<fmt:formatNumber var="lp1" pattern="##.###%" value="${llc.memberShares}"/>
									<input type="text" name="memberShares" value="${lp1}"/>
								</td>
							</tr>
							<tr>
								<td align='right'>Assumed/Existing Discount Rate  (%):</td>
								<td>&nbsp;</td>
								<td>
									<fmt:formatNumber var="dr1" pattern="##.###%" value="${llc.discountRate}"/>
									<input type="text" name="discountRate" value="${dr1}"/>
								</td>
							</tr>
							<tr>
								<td align='right'>Premium Value for Manager Member Interest  (%):</td>
								<td>&nbsp;</td>
								<td>
									<fmt:formatNumber var="pr1" pattern="##.###%" value="${llc.premiumGpValue}"/>
									<input type="text" name="premiumGPValue" value="${pr1}"/>
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
