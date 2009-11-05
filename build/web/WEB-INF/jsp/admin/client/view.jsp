<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%@ page import="com.teag.bean.*"%>
<%@ page import="com.teag.bean.*"%>
<%@ page import="com.teag.client.*"%>
<%@ page import="com.estate.constants.*" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>View Client</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link rel="stylesheet" type="text/css" href="css/styles.css">
  </head>
  <body>
    <%@ include file="/inc/aesHeader.jspf" %>
    <%@ include file="/inc/aesQuickJump.jspf" %>
  	<jsp:useBean id="person" scope="request" type="com.teag.bean.PersonBean" />
    <br>
	<table width='70%' align='center'>
		<tr class='header-bold-blue'>
			<td colspan='3' align='center'>Client Personal Data</td>
		</tr>
		<tr>
			<td colspan='3'>&nbsp;</td>
		</tr>
		<tr class='text-bold'>
			<td align='right' width='40%'>First Name :</td>
			<td width='10%'>&nbsp;</td>
			<td width='40%'>${person.firstName }</td>
		</tr>
		<tr class='text-bold'>
			<td align='right' width='40%'>Middle Name :</td>
			<td width='10%'>&nbsp;</td>
			<td width='40%'>${person.middleName }</td>
		</tr>
		<tr class='text-bold'>
			<td align='right' width='40%'>Last Name :</td>
			<td width='10%'>&nbsp;</td>
			<td width='40%'>${person.lastName }</td>
		</tr>
		<tr class='text-bold'>
			<td align='right' width='40%'>Occupation :</td>
			<td width='10%'>&nbsp;</td>
			<td width='40%'>${person.occupation }</td>
		</tr>
		<tr class='text-bold'>
			<td align='right' width='40%'>Gender :</td>
			<td width='10%'>&nbsp;</td>
			<td>
				<c:choose>
					<c:when test="${person.gender == 'M' }">Male</c:when>
					<c:otherwise>Female</c:otherwise>
				</c:choose>
			</td>
		</tr>
		<tr class='text-bold'>
			<td align='right' width='40%'>Health Insurance :</td>
			<td width='10%'>&nbsp;</td>
			<td width='40%'>${hDesc}</td>
		</tr>
		<tr class='text-bold'>
			<td align='right' width='40%'>Birth Date :</td>
			<td width='10%'>&nbsp;</td>
			<td width='40%'>${person.birthDate }</td>
		</tr>
		<tr>
			<td colspan='3'>&nbsp;</td>
		</tr>
		<tr class='header-bold-blue'>
			<td colspan='3' align='center'>Place of Birth</td>
		</tr>
		<tr class='text-bold'>
			<td align='right' width='40%'>City :</td>
			<td width='10%'>&nbsp;</td>
			<td width='40%'>${person.city }</td>
		</tr>
		<tr class='text-bold'>
			<td align='right' width='40%'>State :</td>
			<td width='10%'>&nbsp;</td>
			<td width='40%'>${person.state }</td>
		</tr>
		<tr>
			<td colspan='3'>&nbsp;</td>
		</tr>
		<tr>
			<td colspan='3' align='center'>
				<a href="servlet/AdminSelectClientServlet">Return to Client Selection</a>
			</td>
		</tr>
	</table>
	<br>
    <%@ include file="/inc/aesFooter.jspf" %>
  </body>
</html>
