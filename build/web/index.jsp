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
	<body><%@ include file="/inc/aesHeader.jspf"%>
		<br>
		<table align='center' width='70%'>
			<tr>
				<td>
					<p>
						Advanced Estate Planning is much like a chess match with the IRS.
						The pieces are your assets. You and your family win by keeping
						what you need and transferring to your family as many of the
						"extra" pieces as possible. The IRS is your opponent, trying to
						capture as many pieces as they can, in the form of various taxes,
						during your lifetime and upon your death(s). Most people feel
						powerless, playing against this monolith.
					</p>
					<p>
						The combined planning team's job is to empower and assist you and
						your family in retaining as many of these assets as possible - by
						utilizing them to fully meet your own needs and then moving the
						excess assets safely past the IRS and directing them to those whom
						you desire and in the manner you wish.
					</p>
				</td>
			</tr>
		</table>
		<br>
		<form action="login.jsp" method="post">
			<center>
				<button type="submit">
					Login
				</button>
			</center>
		</form>
		<%@ include file="/inc/aesFooter.jspf"%>
	</body>
</html>
