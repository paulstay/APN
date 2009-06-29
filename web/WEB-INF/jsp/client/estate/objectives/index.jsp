<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%@ page import="com.teag.bean.*"%>
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

		<title>Objectives</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<link rel="stylesheet" type="text/css" href="css/styles.css"
			media="screen">
	</head>
	<%@ include file="/inc/init.jspf"%>
	<body>
		<%@ include file="/inc/aesHeader.jspf"%>
		<%@ include file="/inc/aesQuickJump.jspf"%>
		<jsp:useBean id="client" scope="session"
			class="com.teag.bean.ClientBean" />
		<c:set var="inc" value="1" />
		<teag:form action="servlet/ClientObjectivesServlet" method="post"
			name="aForm">
			<teag:hidden name="action" value="Add" />
			<table
				style="text-align: left; margin-left: auto; margin-right: auto; width: 784px; height: 1px;"
				border="0" cellpadding="4" cellspacing="0" align="center">
				<tr>
					<th colspan='4' align='center'>
						<h3>
							Objectives
						</h3>
					</th>
				</tr>
				<c:forEach var="s" items="${cList}">
					<c:set var="cclass" value="assets-clr1" />
					<c:if test="${inc %2 == 1 }">
						<c:set var="cclass" value="assets-clr2" />
					</c:if>
					<tr class="${cclass}">
						<td width="60%">
							${inc}. ${s.objective}
						</td>
						<td width='2%'>
							&nbsp;
						</td>
						<td width='10%' align='right' valign='top'>
							<a
								href="servlet/ClientObjectivesServlet?action=Edit&id=${s.id}">Edit</a>&nbsp;
							<a
								href="servlet/ClientObjectivesServlet?action=Delete&id=${s.id}">Delete</a>
						</td>
					</tr>
					<c:set var="inc" value="${inc+1}" />
				</c:forEach>
				<tr>
					<td colspan='4'>
						&nbsp;
					</td>
				</tr>
				<tr>
					<td colspan='4' align='center'>
						<input type='submit' value='Add' />
					</td>
				</tr>
				<tr>
					<td colspan='4'>
						&nbsp;
					</td>
				</tr>
				<tr>
					<td colspan='4' align='center'>
						<a href="servlet/ClientDocServlet">Back to Previous Menu</a>
					</td>
				</tr>
			</table>
		</teag:form>
		<%@ include file="/inc/aesFooter.jspf"%>
	</body>
</html>
