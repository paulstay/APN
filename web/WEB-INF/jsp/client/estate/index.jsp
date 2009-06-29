<%@ page language="java" import="java.util.*"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
	<body>
		<c:if test="${validUser == null}">
			<c:redirect url="/login.jsp">
				<c:param name="errorMsg" value="You are not logged in!" />
			</c:redirect>
		</c:if>
		<jsp:useBean id="person" scope="session"
			class="com.teag.bean.PersonBean" />
		<%@ include file="/inc/aesHeader.jspf"%>
		<%@ include file="/inc/aesQuickJump.jspf"%>
		<div id='client-index'>
			<table align='center'>
				<tr>
					<td colspan='3'>
						<span class='header-bold-blue'>Estate Planning for
							${person.firstName} ${person.lastName}</span>
					</td>
				</tr>
				<tr>
					<td id='client-index' align="left" valign="top" width="330">
						<br>
						<h4>
							Client Documentation
						</h4>
						<ul>
							<li>
								<a href="servlet/ClientObjectivesServlet">Objectives</a>
							</li>
							<li>
								<a href="servlet/ClientPhilosophyServlet">Philosophy</a>
							</li>
							<li>
								<a href="servlet/ClientObservationsServlet">Observations</a>
							</li>
							<li>
								<a href="servlet/ClientRecommendationsServlet">Recommendations</a>
							</li>
						</ul>
					</td>
				</tr>
			</table>
			<br>
			<div align='center'>
				<a href="client/index.jsp">Back to Main Menu</a>
			</div>
			<%@ include file="/inc/aesFooter.jspf"%>
		</div>
	</body>
</html>