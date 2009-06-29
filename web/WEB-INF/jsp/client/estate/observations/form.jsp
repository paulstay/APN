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

		<title>Observations</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<link rel="stylesheet" type="text/css" href="css/styles.css">
	</head>
	<body>
	<%@ include file="/inc/aesHeader.jspf" %>
	<jsp:useBean id="ob" scope='request' type="com.teag.bean.ObservationBean"/>
	<jsp:useBean id="client" scope="session" class="com.teag.bean.ClientBean"/>
	<jsp:setProperty name="ob" property="id" param="id"/>
	<teag:form name="uForm" action="servlet/ClientObservationsServlet" method="post">
	<teag:hidden name='id' value='${ob.id}'/>
	<teag:hidden name="ownerId" value='${client.primaryId}'/>
	<teag:hidden name='action' value='cancel'/>
	<table align='center' width='60%'>
		<tr>
			<td align='center'><h3>Observation</h3></td>
		</tr>
		<tr>
			<td>
				<teag:textarea name="observation" rows="10" cols="80" >${ob.observation}</teag:textarea>
			</td>
		</tr>
		<tr>
			<td align='center'>
				<c:if test="${param.action=='Edit' }">
					<input type='submit' value='Save' onclick="document.uForm.action.value='update'"/>
				</c:if>
				<c:if test="${param.action=='Add' }">
					<input type='submit' value='Add' onclick="document.uForm.action.value='insert'"/>
				</c:if>
				<input type='submit' value='Cancel' onclick="document.uForm.action.value='cancel'"/>
				<input type='submit' value='Delete' onclick="document.uForm.action.value='delete'"/>
			</td>
		</tr>
	</table>
	</teag:form>
	<%@ include file="/inc/aesFooter.jspf" %>
	</body>
</html>
