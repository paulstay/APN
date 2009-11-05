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

		<title>CLAT</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<link rel="stylesheet" type="text/css" href="<%=path %>/css/styles.css">
	</head>

<body>
<%@include file="/inc/aesHeader.jspf" %>
		<h4 align='center'>
			Charitable Remainder Trusts
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
								Advantages and Where/Why to use
							</td>
							<td>
								<ul>
									<li>Avoids up front Capital Gains tax</li>
									<li>Most useful when client needs/wants to sell appreciated assets</li>
									<li>Provides cash flow to client(s) while asset(s) removed from estate</li>
								</ul>
							</td>
						</tr>
						<tr>
							<td>
								Concerns and Where Not to Use
							</td>
							<td>
								<ul>
									<li>Avoid if property is debt-encumbered</li>
									<li>Don't use debt financed assets</li>
									<li>Cannot use if sole proprietor/general partnership as this requires management</li>
								</ul>
							</td>
						</tr>
						<tr>
							<td>
								Other Comments
							</td>
							<td>
								<ul>
									<li>Life insurance can effectively replace assets passing to charity upon death</li>
									<li>Tax savings pays for the insurance premiums</li>
									<li>Can be an excellent base of needed lifetime cash flow</li>
								</ul>
							</td>
						</tr>
					
					</table>
				</td>
			</tr>
		</table>
<%@include file="/inc/aesFooter.jspf" %>
	</body>
</html>
