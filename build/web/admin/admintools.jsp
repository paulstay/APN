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
	<body>
		<%@ include file="/inc/init.jspf"%>
		<c:if test="${validUser == null}">
			<c:redirect url="/login.jsp">
				<c:param name="errorMsg" value="You are not logged in!" />
			</c:redirect>
		</c:if>
		<jsp:useBean id="validUser" scope="session"
			class="com.teag.bean.AdminBean" />
		<%@ include file="/inc/aesHeader.jspf"%>
		<%@ include file="/inc/aesQuickJump.jspf"%>
		<%
			if (validUser.isAdmin() == false) {
				return;
			}
			long orgLevel = validUser.getOrg().getOrgLevel();
			long orgID = validUser.getOrg().getId();
		%>
		<table width="90%" align="center">
			<tr>
				<td colspan="2" align="Center">
					<span class='header-bold'>Administrative Tools</span>
				</td>
			</tr>
		</table>
		<hr size='3' width='700px' />
		<div id='client-index'>
			<table class='normal' align='center'>
				<tr>
					<td align='right' width='50%'>
						<a href='admin/organizations/ListPlanner.jsp'>List Active
							Planners</a>
					</td>
					<td>
						Active Planners
					</td>
				</tr>
			</table>
		</div>
		<%@ include file="/inc/aesFooter.jspf"%>
	</body>
</html>
