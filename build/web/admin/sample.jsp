<%@ page language="java" import="java.util.*"%>
<%@ page import="com.teag.sample.*"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
	<%
		String path = request.getContextPath();
		String basePath = request.getScheme() + "://"
				+ request.getServerName() + ":" + request.getServerPort()
				+ path + "/";
	%>
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
	<body>
		<%@ include file="/inc/init.jspf"%>
		<c:if test="${validUser == null}">
			<c:redirect url="/login.jsp">
				<c:param name="errorMsg" value="You are not logged in!" />
			</c:redirect>
		</c:if>
        <%
        if(request.isUserInRole("admin"))
            System.out.println("Admin role");
        if(request.isUserInRole("user"))
            System.out.println("User Role");
        %>
		<jsp:useBean id="validUser" scope="session"
			class="com.teag.bean.AdminBean" />
		<%@ include file="/inc/aesHeader.jspf"%>
		<%@ include file="/inc/aesQuickJump.jspf"%>
		<h3 align='center'>
			Load Sample Asset Data
		</h3>
		<table align='center' width='50%'>
			<tr>
				<td>
					<p>
						Select one of the links below to load all of the personal, and
						asset data for the sample case. The following does not add the
						Estate Planning Tools for each case. Documentation for generating
						the Estate Planning Tools will be available shortly. For
						information please refer to the notes from the seminar.
					</p>
					<p>
						After you click on one of these links, you will be returned to the main web page
						where you can select that sample client through the "Select Client" menu.
					</p>
				</td>
			</tr>
		</table>
		<br>
		<table align='center' width='60%'>
			<tr>
				<td align='center'>
					<a href="admin/processSample.jsp?action=C">Clark and Barbara
						Jones</a>
				</td>
			</tr>
			<tr>
				<td align='center'>
					<a href="admin/processSample.jsp?action=B">Bruce and Nadine
						Smith</a>
				</td>
			</tr>
		</table>
		<br>
		<div align='center'>
			<a href="admin/index.jsp">Back to Main Menu</a>
		</div>
		<%@ include file="/inc/aesFooter.jspf"%>
	</body>
</html>