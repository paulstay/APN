<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%@ page import="com.teag.bean.*" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
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
		<link rel="stylesheet" type="text/css" href="css/styles.css">
	</head>
	<body>
	<%@include file="/inc/aesHeader.jspf" %>
	<jsp:useBean id="ob" scope='request' class="com.teag.bean.ObjectivesBean"/>
	<jsp:useBean id="client" scope="session" class="com.teag.bean.ClientBean"/>
	<teag:form name="uForm" action="servlet/ClientObjectivesServlet" method="post">
	<teag:hidden name='id' value='${ob.id}'/>
	<teag:hidden name='action' value='Cancel'/>
	<teag:hidden name="ownerId" value="${client.primaryId}"/>
	<table align='center' width='60%'>
		<tr>
			<td align='center'><h3>The Objective</h3></td>
		</tr>
		<tr>
			<td>
				<teag:textarea name="objective" rows="10" cols="80" >${ob.objective}</teag:textarea>
			</td>
		</tr>
		<tr>
			<td align='center'>
				<c:if test="${param.action=='Edit' }">
					<input type='submit' value='Save' onclick="document.uForm.action.value='update'"/>
				</c:if>
				<c:if test="${param.action=='Add' }">
					<input type='submit' value='Add' onclick="document.uForm.action.value='Insert'"/>
				</c:if>
				<input type='submit' value='Cancel' onclick="document.uForm.action.value='Cancel'"/>
				<input type='submit' value='Delete' onclick="document.uForm.action.value='Delete'"/>
			</td>
		</tr>
	</table>
	</teag:form>
	<%@include file="/inc/aesFooter.jspf" %>
	</body>
</html>
