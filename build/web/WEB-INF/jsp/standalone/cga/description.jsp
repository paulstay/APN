<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
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

		<title>Charitable Gift Annuity</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<link rel="stylesheet" type="text/css"
			href="<%=path%>/css/styles.css">
	</head>

	<body>
		<%@include file="/inc/aesHeader.jspf"%>
		<h4 align='center'>
			Charitable Gift Annuity
		</h4>
		<table align='center' width='100%'>
			<tr>
				<!-- Header -->
				<td colspan='2' align='center'>
				</td>
			</tr>
			<tr>
				<!--  menu  -->
				<td>
					<%@ include file="menu.jspf"%>
				</td>
				<!-- Form/Web Page -->
				<td>
					<h3 align='center'>
						Description
					</h3>
					<table align='center' border='1'>
						<tr>
							<td>
								<p>
									Definition: Charitable gift annuities (CGAs) are one of the
									simplest and most popular forms of life income gifts. It
									involves a simple agreement whereby the charity accepts a gift
									of cash, securities, or property and agrees to pay a specified,
									fixed dollar amount to the annuitant (the donor or another
									beneficiary). The fixed amount is set by the American Council
									on Gift Annuities and is based on the annuitant's age. Because
									the gift is irrevocable, the charity maintains control of the
									gift and becomes responsible for paying income for the lifetime
									of each annuitant.
								</p>
							</td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
		<%@include file="/inc/aesFooter.jspf"%>
	</body>
</html>
