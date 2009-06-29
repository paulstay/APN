<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ page language="java" import="java.util.*,com.teag.view.*"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<html>
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
		<%@ include file="/inc/init.jspf"%>
		<%@ include file="/inc/aesHeader.jspf"%>
		<%@ include file="/inc/aesQuickJump.jspf"%>
		<br>
		<div id='client-index'>
			<table width='684px' align='center'>
				<tr>
					<td colspan='4' align='center'>
						<h3>
							Select Client
						</h3>
					</td>
				</tr>
				<c:forEach var="c" items="${cList}">
					<tr>
						<td>
							<a href="servlet/AdminInitClient?clientid=${c.clientId}"><b>${c.firstName}
									${c.lastName}</b>
							</a>
						</td>
						<td>
							&nbsp;
						</td>
						<td width='5%' align='center'>
							<a href="servlet/AdminClientViewServlet?action=view&id=${c.personId}">View</a>
						</td>
						<td width='5%' align='center'>
							<a href="servlet/AdminClientViewServlet?action=edit&id=${c.personId}">Edit</a>
						</td>
					</tr>
				</c:forEach>
				<tr>
					<td colspan='4' align='center'>
						<a href="admin/index.jsp">Back to Main Menu</a>
					</td>
				</tr>
			</table>
			<br>
		</div>
		<%@ include file="/inc/aesFooter.jspf"%>
	</body>
</html>
