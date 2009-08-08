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
	<jsp:useBean id="person" scope="session"
		class="com.teag.bean.PersonBean" />
	<body>
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
					<td align="left" valign="top" width="330">
						<br>
						<h4>
							Scenario 1 PDF Report
						</h4>
						<ul>
							<li>
								<a href="servlet/BaselineReport">Report</a>
							</li>
						</ul>
						<h4>
							Individual Pages for Scenario 1
						</h4>
						<ul>
							<li>
								<a href="servlet/PDFReport?TPAGE=TITLE">Title Page</a>
							</li>
							<li>
								<a href="servlet/PDFReport?TPAGE=PERSONAL">Personal Page</a>
							</li>
							<li>
								<a href="servlet/PDFReport?TPAGE=NETWORTH">Net worth Page</a>
							</li>
							<li>
								<a href="servlet/PDFReport?TPAGE=ASSETS">Asset and
									Liabilities</a>
							</li>
							<li>
								<a href="servlet/PDFReport?TPAGE=OBJECTIVES">Objectives Page</a>
							</li>
							<li>
								<a href="servlet/PDFReport?TPAGE=OBSERVATIONS">Observations
									Page</a>
							</li>
							<li>
								<a href="servlet/PDFReport?TPAGE=PHILOSOPHY">Philosophy Page</a>
							</li>
							<li>
								<a href="servlet/PDFReport?TPAGE=RECOMMENDATIONS">Recommendations
									Page</a>
							</li>
							<li>
								<a href="servlet/PDFReport?TPAGE=CASHFLOW">Scenario 1 Cash
									Flow</a>
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