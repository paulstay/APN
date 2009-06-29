<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<jsp:useBean id="validUser" scope="session"
	class="com.teag.bean.AdminBean" />
<html>
<c:if test="${validUser == null}">
	<c:redirect url="/login.jsp">
		<c:param name="errorMsg" value="You are not logged in!" />
	</c:redirect>
</c:if>
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
		<%@ include file="/inc/aesHeader.jspf"%>
		<%@ include file="/inc/aesQuickJump.jspf"%>
		<table width="90%" align="center">
			<tr>
				<td colspan="2" align="Center">
					<span class='header-bold'>Manage profile for <%=validUser.getFirstName() + " "
					+ validUser.getLastName()%></span>
				</td>
			</tr>
		</table>
		<hr size='3' width='700px' />
		<div id='client-index'>
			<table class='normal' align='center'>
				<tr>
					<td align='right' valign='top'>
						<a href='admin/profile/password.jsp?action=add'><b>Change
								Password</b> </a>
					</td>
				</tr>
			</table>
		</div>
		<br>
		<div align='center'>
			<a href="admin/index.jsp">Back to Main Menu</a>
		</div>
		<%@ include file="/inc/aesFooter.jspf"%>
	</body>
</html>
