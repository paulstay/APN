<%@ page language="java" import="java.util.*"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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

		<title><%=basePath%></title>

		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<link rel="stylesheet" type="text/css" href="<%=path%>/css/styles.css">
	</head>
	<c:if test="${validUser == null}">
		<c:redirect url="/login.jsp">
			<c:param name="errorMsg" value="You are not logged in!" />
		</c:redirect>
	</c:if>
	<%@ page import="com.teag.bean.*"%>
	<%@ page import="com.teag.view.*"%>
	<jsp:useBean id="validUser" scope="session"
		class="com.teag.bean.AdminBean" />
	<jsp:useBean id="clients" scope="page" class="com.teag.view.ClientView" />
	<body>
		<%@ include file="/inc/aesHeader.jspf"%>
		<%@ include file="/inc/aesQuickJump.jspf"%>
		<c:if test="${validUser == null}">
			<c:redirect url="/login.jsp">
				<c:param name="errorMsg" value="You are not logged in!" />
			</c:redirect>
		</c:if>
		<%
			ArrayList cList = clients.getView(validUser.getId());
			request.setAttribute("cList", cList);
		%>
		<hr size='3' width='60%' />
		<form action="delete.jsp" method="post" name="iform">
			<table class='normal' align='center' width='35%'>
				<tr>
					<td align='center'>
						Clients for Planner
					</td>
				</tr>
				<tr>
					<td align='center'>
						<h3>
							<%=validUser.getFirstName() + " "
					+ validUser.getLastName()%></h3>
					</td>
				</tr>
				<tr>
					<td align='center'>
						<p>
							<span class='text-bold-red'>WARNING:</span>
						</p>
						<p>
							The process of deleting a client is a one way process. Once
							deleted the client cannot be restored. Use of client deletion is
							at your own risk.
						</p>
					</td>
				</tr>
				<tr>
					<td>
						&nbsp;
					</td>
				</tr>
				<c:if test="${ not empty cList }">
					<c:forEach var='c' items="${cList}">
						<tr>
							<td>
								<c:out value="${c.firstName}" />
								&nbsp;
								<c:out value="${c.lastName}" />
							</td>
							<td>
								<a
									href='admin/support/confirm.jsp?clientId=${c.clientId}&personId=${c.personId}&plannerId=${validUser.id}'>Del</a>
							</td>
						</tr>
					</c:forEach>
				</c:if>
				<c:if test="${ empty cList }">
					<tr>
						<td align='center'>
							<h3>
								No clients for planner
							</h3>
						</td>
					</tr>
				</c:if>
			</table>
		</form>
		<div align='center'>
			<a href="admin/index.jsp">Back to Main Menu</a>
		</div>
		<br>
		<%@ include file="/inc/aesFooter.jspf"%>