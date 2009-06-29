<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
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

		<title>Planner Maintenance</title>

		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="description" content="Planner Maintenance">
		<link rel="stylesheet" type="text/css" href="<%=path%>/css/styles.css">
	</head>
	<body>
		<%@ include file="/inc/aesHeader.jspf"%>
		<h3 align='center'>
			Planner Maintenance
		</h3>
		<table align='center'>
			<teag:form action="servlet/PlannerDispatch" method='post'
				name='aform'>
				<teag:hidden name='pageView' value='none' />
				<teag:hidden name='page' value='0' />
				<tr>
					<td>
						Add New Planner
					</td>
					<td align='left'>
						<teag:input type="button" value="Add"
							onclick="document.aform.pageView.value='add';document.aform.submit()" />
					</td>
				</tr>
				<teag:hidden name='page' value='0' />
				<tr>
					<td>
						List Planners
					</td>
					<td align='left'>
						<teag:input type="button" value="List"
							onclick="document.aform.pageView.value='list';document.aform.submit()" />
					</td>
				</tr>
			</teag:form>
			<tr>
				<td>
					&nbsp;
				</td>
				<td>
					&nbsp;
				</td>
			</tr>
			<tr>
				<td>
					&nbsp;
				</td>
				<td>
					&nbsp;
				</td>
			</tr>
			<tr>
				<td align='center' colspan='2'>
					<teag:input type="button" value="Return"
						onclick="document.aform.pageView.value='home';document.aform.submit()" />
				</td>
			</tr>
		</table>
		<%@ include file="/inc/aesFooter.jspf"%>
	</body>
</html>
