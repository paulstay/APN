<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
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
	<link rel="stylesheet" type="text/css" href="/css/styles.css">

  </head>
  <body>
  	<%@include file="/inc/aesHeader.jspf" %>
  	<%@include file="/inc/aesQuickJump.jspf" %>
    	<h3 align='center'>Personal Data</h3>
    	<br>
    	<table align='center'>
    		<tr>
    			<td align='left'>
    				<ul>
    				<li><a href="servlet/SpouseServlet">Spouse</a></li>
    				<li><a href="servlet/ChildrenGenServlet">Children</a></li>
    				<li><a href="servlet/ResidenceServlet">Residential</a></li>
    				<li><a href="servlet/AdvisorServlet">Advisors</a></li>
    				<!-- <li><a href="servlet/HeirServlet">Heirs</a></li> -->
    				</ul>
    			</td>
    		</tr>
    	</table>
    	<br>
    	<div align='center'>
    		<a href="client/index.jsp">Back to Client Menu</a>
    	</div>
    	<br>
  	<%@include file="/inc/aesFooter.jspf" %>
  </body>
</html>
