<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
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
		<%@ include file="/inc/aesHeader.jspf"%>
		<br>
		<table align="center" width='70%'>
			<tr>
				<td>
					Welcome to
					<i>The Advanced Practice Network Edge Software System</i>. Please
					login using your username and password.
				</td>
			</tr>
		</table>
		<br>
		<form action="PlannerLogin" name="iForm" method="post">
			<br>
			<table align="center">
				<tr>
					<td colspan='2' align='center'>
						<span class='text-red'><c:out value="${param.errorMsg}" />
						</span>
					</td>
				</tr>
				<tr>
					<td>
						Username:
					</td>
					<td>
						<input type="text" name='j_username' size="20"/>
					</td>
				<tr>
				<tr>
					<td>
						Password:
					</td>
					<td>
						<input type="password" name='j_password' size="20" />
					</td>
				</tr>
			</table>
			<br>
			<center>
				<button type="submit">
					Submit
				</button>
			</center>
		</form>
		<%@ include file="/inc/aesFooter.jspf"%>
	</body>
</html>