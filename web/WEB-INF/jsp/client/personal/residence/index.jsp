<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<! DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<base href="<%=basePath%>">
	<title>Select Client</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
	<link href="<%=path%>/css/styles.css" rel="stylesheet" media="screen">
	<body>
		<jsp:useBean id='person' class='com.teag.bean.PersonBean'
			scope='session' />
		<jsp:useBean id='res' class='com.teag.bean.LocationBean' />
		<%@ include file="/inc/aesHeader.jspf"%>
		<!-- insert page data here -->
		<table align='center' width='80%'>
			<tr class='bg-color2'>
				<td colspan='8' align='center' class='header-bold'>
					${person.firstName} ${person.lastName}
				</td>
			</tr>
			<tr>
				<td>
					&nbsp;
				</td>
				<td align='center' colspan='8'>
					<p>
						The following residences, vacation homes, and or offices are
						printed on the Personal Data page of the report. They are not
						included in the Net Worth of the Estate.
					</p>
				</td>
				<td>
					&nbsp;
				</td>
			</tr>
			<tr>
				<td>
					&nbsp;
				</td>
			</tr>
			<tr class='bg-color6'>
				<th width='25%'>
					<span class='text-bold'>Description</span>
				</th>
				<th width='25%'>
					<span class='text-bold'>Address</span>
				</th>
				<th>
					<span class='text-bold'>City</span>
				</th>
				<th>
					<span class='text-bold'>State</span>
				</th>
				<th>
					<span class='text-bold'>Zip</span>
				</th>
				<th width='15%'>
					<span class='text-bold'>Phone</span>
				</th>
				<th width='15%'>
					<span class='text-bold'>Fax</span>
				</th>
				<th width='15%'>
					<span class='text-bold'>&nbsp;&nbsp;</span>
				</th>
			</tr>
			<c:set var="inc" value="0" />
			<c:forEach var="s" items="${cList}">
				<tr class='l-color${inc%2}'>
					<td>
						<c:out value="${s.name}" />
					</td>
					<td>
						<c:out value="${s.address1}" />
					</td>
					<td>
						<c:out value="${s.city}" />
					</td>
					<td>
						<c:out value="${s.state}" />
					</td>
					<td>
						<c:out value="${s.zip}" />
					</td>
					<td>
						<c:out value="${s.phone}" />
					</td>
					<td>
						<c:out value="${s.fax}" />
					</td>
					<td>
						<a href='servlet/ResidenceServlet?action=edit&id=${s.id}'>Edit</a>
					</td>
				</tr>
				<c:set var="inc" value="${inc + 1}" />
			</c:forEach>
			<tr>
			<tr>
				<td colspan='6'>
					&nbsp;
				</td>
			</tr>
			<tr>
				<td align='center' colspan='9'>
					<form action='servlet/ResidenceServlet?action=add' name='iform'
						method='post'>
						<input type='submit' value='Add New' />
					</form>
				</td>
			</tr>
			<tr>
				<td colspan='6'>
					&nbsp;
				</td>
			</tr>
			<tr>
				<td colspan='9' align='center'>
					<a href='client/Personal/index.jsp'>Personal Data Menu</a>
				</td>
			</tr>
		</table>
		<%@ include file="/inc/aesFooter.jspf"%>
	</body>
</html>
