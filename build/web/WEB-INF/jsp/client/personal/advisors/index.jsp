<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.teag.bean.AdvisorBean"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="teagtags" prefix="teag"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<! DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<base href="<%=basePath%>">
	<title>Advisors</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
	<link href="<%=path%>/css/styles.css" rel="stylesheet" media="screen">
	<body>
		<c:if test="${validUser == null}">
			<c:redirect url="/login.jsp">
				<c:param name="errorMsg" value="You are not logged in!" />
			</c:redirect>
		</c:if>
		<jsp:useBean id="client" scope="session"
			class="com.teag.bean.ClientBean" />
		<jsp:useBean id="person" scope="page" class="com.teag.bean.PersonBean" />
		<%@ include file="/inc/aesHeader.jspf"%>
		<br>
		<h3 align='center'>
			Advisors
		</h3>
		<table align='center' width='90%'>
			<c:set var='inc' value='0' />
			<tr class='bg-color6'>
				<th>
					Name
				</th>
				<th>
					Firm
				</th>
				<th>
					Type
				</th>
				<th>
					Phone
				</th>
				<th>
					Email
				</th>
				<th>
					&nbsp;
				</th>
			</tr>
			<c:forEach var='a' items="${cList}">
				<tr class='l-color${inc%2}'>
					<td>
						${a.firstName} ${a.lastName}
					</td>
					<td>
						${a.firmName}
					</td>
					<td>
						${a.type}
					</td>
					<td>
						${a.phone}
					</td>
					<td>
						${a.email}
					</td>
					<td>
						[
						<a href='servlet/AdvisorServlet?action=edit&id=${a.id}'>Edit</a>]
					</td>
				</tr>
				<c:set var='inc' value='${inc + 1 }' />
			</c:forEach>
			<tr>
				<td>
					&nbsp;
				</td>
			</tr>
			<tr>
				<td colspan='6' align='center'>
					<a href="servlet/AdvisorServlet?action=add">Add New Advisor</a>
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
