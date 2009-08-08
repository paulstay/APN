<%@ page language="java" import="java.util.*"%>
<%@ page import="com.teag.bean.*"%>
<%@ page import="com.teag.view.*"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="input"
	uri="http://jakarta.apache.org/taglibs/input-1.0"%>
<%@ taglib prefix="teag" tagdir="/WEB-INF/tags/teag"%>
<%@ include file="/inc/init.jspf"%>
<c:if test="${validUser == null}">
	<c:redirect url="/login.jsp">
		<c:param name="errorMsg" value="You are not logged in!" />
	</c:redirect>
</c:if>
<jsp:useBean id="validUser" scope="session"
	class="com.teag.bean.AdminBean" />
<html>
	<%
		String path = request.getContextPath();
		String basePath = request.getScheme() + "://"
				+ request.getServerName() + ":" + request.getServerPort()
				+ path + "/";
	%>
	<head>
		<base href="<%=basePath%>">
		<title>Manage Organizations</title>

		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<link rel="stylesheet" type="text/css" href="<%=path%>/css/styles.css">
	</head>
	<body>
		<%@ include file="/inc/aesHeader.jspf"%>
		<%@ include file="/inc/aesQuickJump.jspf"%>
		<%
			String plannerId = request.getParameter("plannerId");
			PlannerBean pb = new PlannerBean();
			pb.setId(Long.parseLong(plannerId));
			pb.initialize();
			ClientView clients = new ClientView();
			ArrayList cList = clients.getView(Long.parseLong(plannerId));
			request.setAttribute("cList", cList);
		%>
		<hr size='3' width='60%' />
		<input:form action="delete.jsp" method="post" name="iform">
			<table class='normal' width='40%' align='center'>
				<tr>
					<td align='center'>
						Clients for Planner
					</td>
				</tr>
				<tr>
					<td align='center'>
						<p>
							<span class='text-red'>
							WARNING:
							</span>
							The process of deleting a client is a one way process. Once
							deleted the client cannot be restored. Use of client deletion is
							at your own risk.
						</p>
					</td>
				</tr>
				<tr>
					<td align='center'>
						<h3>
							<%=pb.getFirstName() + " " + pb.getLastName()%></h3>
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
									href='delete.jsp?clientId=${c.clientId}&personId=${c.personId}&plannerId=<%=plannerId%>'>Del</a>
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
		</input:form>
		<input:form action='admin/maintenance/index.jsp' method='post'>
			<table class='normal' align='center'>
				<tr>
					<td align='center'>
						<input type='submit' value='Back'/>
					</td>
			</table>
		</input:form>
		<%@ include file="/inc/aesFooter.jspf"%>