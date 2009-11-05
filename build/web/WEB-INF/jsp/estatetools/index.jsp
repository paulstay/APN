<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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

		<title>My JSP 'index.jsp' starting page</title>

		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<link rel="stylesheet" type="text/css" href="<%=path %>/css/styles.css">
	</head>
	<body>
		<%@ include file="/inc/aesHeader.jspf"%>
		<%@ include file="/inc/aesQuickJump.jspf"%>
		<c:choose>
			<c:when test="${epg.single }">
				<h2 align='center'>
					${epg.clientFirstName} ${epg.lastName}
				</h2>
			</c:when>
			<c:otherwise>
				<h2 align='center'>
					${epg.clientFirstName} and ${epg.spouseFirstName}
					${epg.lastName}
				</h2>
			</c:otherwise>
		</c:choose>
		<table align='center' width='50%'>
			<tr>
				<td>
					If you are just starting to build an Estate Plan for your clients
					or wish to start over click on the "New" for a new Estate Plan.
					Other wise click on the the "Continue" to use your last Estate Plan
					Session".
				</td>
			</tr>
		</table>
		<form name='iform' id='iform' action="servlet/PlanningScenarioServlet" method='post'>
			<input type='hidden' name='action' value='new' />
			<table align='center'>
				<tr>
					<td>
						<button type='submit' onclick="document.iform.action.value='new'">
							New
						</button>
					</td>
					<td>
						&nbsp;
					</td>
					<td>
						<button type='submit'
							onclick="document.iform.action.value='update'">
							Continue
						</button>
					</td>
					<td colspan='3' align='center'>
						<button type='submit'
							onclick="document.iform.action.value='return'">
							Quit
						</button>
					</td>
				</tr>
			</table>
		</form>
		<%@ include file="/inc/aesFooter.jspf"%>
	</body>
</html>
