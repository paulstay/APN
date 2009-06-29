<%@ page language="java" import='com.teag.view.*'
	pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.*"%>
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
	<title>Children Page</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
	<link href="<%=path%>/css/styles.css" rel="stylesheet" media="screen">
	<body>
		<jsp:useBean id="person" class="com.teag.bean.PersonBean"
			scope="session" />
		<%@ include file="/inc/aesHeader.jspf"%>
		<c:set var="inc" value="0" />
		<br>
		<table width='70%' align='center'>
			<tr>
				<td>
					&nbsp;&nbsp;
				</td>
			</tr>
			<tr class='bg-color6'>
				<td colspan='6' align='center' id='main-title'>
					<span class='header-bold'>Children of ${person.firstName}
						${person.lastName}</span>
				</td>
			<tr class='bg-color6'>
				<th>
					<span class='text-bold'>Name</span>
				</th>
				<th>
					<span class='text-bold'>Occupation</span>
				</th>
				<th>
					<span class='text-bold'>DOB</span>
				</th>
				<th>
					<span class='text-bold'>Spouse</span>
				</th>
				<td colspan='2'></td>
			</tr>
			<c:forEach var="c" items="${cList}">
				<tr class="l-color${inc%2}">
					<td>
						${c.firstName} ${c.lastName}
					</td>
					<td>
						${c.occupation}
					</td>
					<td>
						<fmt:formatDate type="date" value="${c.birthDate}" />
					</td>
					<c:if test="${c.spouseId > 0 }">
						<td>
							<a
								href='servlet/ChildSpouseServlet?action=edit&id=${c.spouseId}&cid=${c.id}&action=Edit'>${c.spouseFirstName}
								${c.spouseLastName}</a>
						</td>
					</c:if>
					<c:if test="${c.spouseId == 0}">
						<td>
							<a href='servlet/ChildSpouseServlet?cid=${c.id}&action=Add'>Add
								Spouse</a>
						</td>
					</c:if>
					<td colspan=2>
						[
						<a href="servlet/ChildProcessServlet?action=edit&id=${c.id}">Edit</a>]
					</td>
				</tr>
				<c:set var="inc" value="${inc + 1}" />
			</c:forEach>
			<tr>
				<td colspan='6'>
					&nbsp;
				</td>
			</tr>
			<tr>
				<td colspan='6' align='center'>
					<form action="servlet/ChildProcessServlet?action=add" method="post" name="iform">
						<input type='submit' value='Add Child' />
					</form>
				</td>
			</tr>
			<tr>
				<td colspan='6'>
					&nbsp;
				</td>
			</tr>
			<tr>
				<td colspan='6' align='center'>
					<a href="client/Personal/index.jsp">Return to Personal Data</a>
				</td>
			</tr>
		</table>
		<%@ include file="/inc/aesFooter.jspf"%>
	</body>
</html>
