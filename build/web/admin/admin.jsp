<%@ page language="java" import="java.util.*" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
<%@ include file="/inc/init.jspf" %>
<c:if test="${validUser == null}" >
	<c:redirect url="/login.jsp">
		<c:param name="errorMsg" value="You are not logged in!" />
	</c:redirect>
</c:if> 
<jsp:useBean id="validUser" scope="session" class="com.teag.bean.AdminBean" />
<%@ include file="/inc/aesHeader.jspf" %>
<body>
<table  width="90%" align="center">
	<tr><td>&nbsp;&nbsp;</td></tr>
	<tr>
		<td colspan="2" align="Center" >
		<span class='header-bold'>Administrative Tools</span></td>
	</tr>
</table>
<hr size='3' width='60%'/>
<div id='client-index'>
<table class='normal' align='center'>
<% if(validUser.canCreateCorp())
{
%>
	<tr>
		<td align='right' width='50%' valign='top'><a href='admin/creatorg.jsp?action=corp'><b>Create Corporate Organization</b></a>:</td><td valign="top">Create New Corporate Organization</td>
	</tr>
<% } %>	

<% if(validUser.canCreateRegion())
{
%>
	<tr>
		<td align='right' width='50%' valign='top'><a href='admin/creatorg.jsp?action=region'><b>Create Regional Organization</b></a>:</td><td valign="top">Create New Regional Organization</td>
	</tr>
<% } %>	

<% if(validUser.canCreateDivision())
{
%>
	<tr>
		<td align='right' width='50%' valign='top'><a href='admin/creatorg.jsp?action=division'><b>Create Divisional Organization</b></a>:</td><td valign="top">Create New Divisional Organization</td>
	</tr>
<% } %>	

<% if(validUser.canCreateBranch())
{
%>
	<tr>
		<td align='right' width='50%' valign='top'><a href='admin/creatorg.jsp?action=branch'><b>Create Branch Organization</b></a>:</td><td valign="top">Create New Branch Organization</td>
	</tr>
<% } %>	

<% if(validUser.canCreatePlanner())
{
%>
	<tr>
		<td align='right' width='50%' valign='top'><a href='admin/creatorg.jsp?action=planner'><b>Create Planner</b></a>:</td><td valign="top">Create New Planner</td>
	</tr>
<% } %>	
</table> 
</div>	
<%@ include file="/inc/aesFooter.jspf" %>
</body>
</html>

