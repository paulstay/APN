<%@ page language="java" pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.*" %>
<%@ page import="com.teag.bean.*" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<%@ include file="/inc/init.jspf" %>
<c:if test="${validUser == null}" >
	<c:redirect url="/login.jsp">
		<c:param name="errorMsg" value="You are not logged in!" />
	</c:redirect>
</c:if> 
<jsp:useBean id="validUser" scope="session" class="com.teag.bean.AdminBean" />
<! DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<base href="<%=basePath%>">
    <title>Select Client</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<link href="css/style.css" rel="stylesheet" media="screen">
<body>
<jsp:useBean id="client" class="com.teag.bean.PersonBean" scope="session"/>
<jsp:useBean id="planner" class="com.teag.bean.PlannerBean" scope="page"/>?
<%@ include file="/inc/aesHeader.jspf" %>
<%@ include file="/inc/aesQuickJump.jspf" %>
<%
	ArrayList<PlannerBean> pList = planner.getBeans("PLANNER_ID>0 and STATUS='A'");
	request.setAttribute("pList", pList);
%>
<div>
<table align='center'>
<tr>
	<td>First Name</td>
	<td>Last Name</td>
	<td>Phone Number</td>
	<td>User Name</td>
	<td>Password</td>
</tr>
<c:forEach var="c" items="${pList}">
<c:if test="${c.password != 'disable' }" >
<tr>
	<td>${c.firstName}</td>
	<td>${c.lastName}</td>
	<td>${c.phone}</td>
	<td>${c.userName}</td>
</tr>
</c:if>
</c:forEach >
</table>
</div>
<%@ include file="/inc/aesFooter.jspf" %>
</body>
</html>
