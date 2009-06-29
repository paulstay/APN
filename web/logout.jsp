<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="java.util.*" %>
<%@ page import="java.text.*" %>
<html>	
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"
	+request.getServerPort()+path+"/";
%>
  <head>
    <base href="<%=basePath%>">
    
    <title><%=basePath %></title>
    
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="This is my page">
    <link rel="stylesheet" type="text/css" href="<%=path%>/css/styles.css">
  </head>
<body>
<jsp:useBean id="validUser" scope="session" class="com.teag.bean.AdminBean"></jsp:useBean>
<%@ include file="/inc/aesHeader.jspf" %>
<br>
<%
	String action = (request.getParameter("action") == null ? "" : request.getParameter("action"));
	String action2 = (request.getParameter("action") == null ? "" : request.getParameter("action2"));

	if (action.equals("yes"))
	{
		Date now = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("M/d/y hh:mm:ss");
		System.out.println("User: " + validUser.getUserName() + " has logged off at " + dateFormat.format(now));  
		session.invalidate();
		System.gc();
%>
	<c:redirect url="index.jsp" />
<%
		return;
	} else if ( action.equals("no")){
%>
		<c:redirect url="admin/index.jsp"/>
<% 
		return;
	}
%>
<table width='100%' align='center' cellspacing='2' cellpadding='0'>
	<tr>
		<td>
		<div align='center'>Are you absolutly sure you want to logout of the current session?</div>
		</td>
	</tr>
</table>
<div align='center'>
<form name="iform" id="iform" action='logout.jsp' method='post'>
<input type="hidden" name="action" value="yes"/>
	<button type="submit" onclick="document.iform.action.value='yes'">Yes</button>
	<button type="submit" onclick="document.iform.action.value='no'">No</button>
</form>
</div>
<%@ include file="inc/aesFooter.jspf" %>
