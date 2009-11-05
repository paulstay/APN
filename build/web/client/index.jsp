<%@ page language="java" import="java.util.*"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ include file="/inc/init.jspf"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<%
		String path = request.getContextPath();
		String basePath = request.getScheme() + "://"
				+ request.getServerName() + ":" + request.getServerPort()
				+ path + "/";
	%>
	<head>
		<base href="<%=basePath%>">

		<title><%=basePath%></title>

		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<link rel="stylesheet" type="text/css" href="<%=path%>/css/styles.css">
	</head>
	<c:if test="${validUser == null}">
		<c:redirect url="/login.jsp">
			<c:param name="errorMsg" value="You are not logged in!" />
		</c:redirect>
	</c:if>
	<c:if test="${client == null || client.clientId == 0}">
		<c:redirect url="/admin/index.jsp" />
	</c:if>
	<jsp:useBean id="client" scope="session"
		class="com.teag.bean.ClientBean" />
	<jsp:useBean id="person" scope="session"
		class="com.teag.bean.PersonBean" />
	<jsp:setProperty name="person" property="id"
		value="${client.primaryId}" />
	<%
		person.initialize();
	%>
	<%@ include file="/inc/aesHeader.jspf"%>
	<%@ include file="/inc/aesQuickJump.jspf"%>
	<body>
		<div id='client-index'>
			<table align='center'>
				<tr>
					<td>
						<span class='header-bold-blue'>Estate Planning for <%=person.getFirstName() + " " + person.getLastName()%></span>
					</td>
				</tr>
				<tr>
					<td>
						&nbsp;
					</td>
				</tr>
				<tr>
					<td></td>
				</tr>
				<tr>
					<td align="left" valign="top" width="330">
						<ul>
							<li>
								<a href='client/Personal/index.jsp'>Personal Data</a>
							</li>
							<li>
								<a href='servlet/SetupIndexServlet'>Balance Sheet Setup
									(Variables, AB Trust)</a>
							</li>
							<li>
								<a href='servlet/ClientAssetServlet'>Financial - Assets and
									Liabilities</a>
							</li>
							<li>
								<a href='servlet/ClientDocServlet'>Client Documentation</a>
							</li>
							<li>
								<a href='servlet/ClientReportServlet'>Scenario 1 Reports</a>
							</li>
							<li>
								<span class='text-link'><a
									href='servlet/PlanningToolsServlet'>Estate Planning Tools</a> </span>
							</li>
							<li>
								<span class='text-link'><a
									href='documentation/index.jsp?page=client'>Support
										Documentation</a> </span>
							</li>
						</ul>
					</td>
				</tr>
			</table>
		</div>
		<div align='center'>
			<a href="admin/index.jsp">Back to Home</a>
		</div>
		<%@ include file="/inc/aesFooter.jspf"%>
	</body>
</html>
