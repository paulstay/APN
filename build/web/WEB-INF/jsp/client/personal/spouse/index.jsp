<%@ page language="java" import="com.teag.view.*" pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.ArrayList" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="teagtags" prefix="teag" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<! DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<base href="<%=basePath%>">
    <title>Spouse Data</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<link href="<%=path%>/css/styles.css" rel="stylesheet" media="screen">
<body>
<jsp:useBean id='person' class='com.teag.bean.PersonBean' scope='session' />
<%@ include file="/inc/aesHeader.jspf" %>
<%@ include file="/inc/aesQuickJump.jspf" %>
<c:set var="inc" value="0" />
<table width='70%' align='center'>
	<tr class='bg-color2' >
		<td class='header-bold' colspan='6' align='center'>${person.firstName } ${person.lastName }</td>
	</tr>
	<tr>
		<td colspan='6'>&nbsp;</td>
	</tr>
	<tr class='bg-color6' >
		<td>Spouse</td>
		<td>Birthday</td>
		<td>Date Married</td>
		<td>Marriage Status</td>
		<td>&nbsp;</td>
	</tr>
<c:forEach var="s" items="${sList}" >
	<c:if test="${s.status == 'C' }" >
		<tr class='l-color${inc}'>
		<td><c:out value="${s.firstName}"/> <c:out value="${s.lastName}"/></td>
		<td><fmt:formatDate type="date" value="${s.birthDate}"/></td>
		<td><fmt:formatDate type="date" value="${s.marriageDate}"/></td>
		<td>Cuurent</td>
		<td><a href="servlet/SpouseServlet?action=edit&id=${s.id}&mid=${s.marriageId}">Edit</a></td>
		</tr>
	</c:if>
	<c:if test="${s.status == 'P' }" >
		<tr class='l-color${inc}'>
		<td><c:out value="${s.firstName}"/> <c:out value="${s.lastName}"/></td>
		<td><fmt:formatDate type="date" value="${s.birthDate}"/></td>
		<td><fmt:formatDate type="date" value="${s.marriageDate}"/></td>
		<td>Previous</td>
		<td><a href="servlet/SpouseServlet?action=edit&id=${s.id}&mid=${s.marriageId}">Edit</a></td>
		</tr>
	</c:if>
<c:set var="inc" value="${inc + 1}"/>
</c:forEach>
<tr>
	<td colspan='8' align='center'>
		<teag:form name='iform' action='servlet/SpouseServlet' method='post'>
			<input type='hidden' name='action' value='Add' />
			<input type='submit' name="btn" value='Add' />
		</teag:form>
	</td>
</tr>
<tr><td colspan='6'>&nbsp;</td></tr>
<tr><td colspan='6' align='center'><a href="client/Personal/index.jsp">Return to Personal Data</a></td></tr>
</table>
<br>
<%@ include file="/inc/aesFooter.jspf" %>
</body>
</html>
