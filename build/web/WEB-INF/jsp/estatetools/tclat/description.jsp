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

		<title>Testametary Charitable Lead Annuity Trust(TCLAT)</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<link rel="stylesheet" type="text/css" href="<%=path %>/css/styles.css">
	</head>

<body>
<%@include file="/inc/aesHeader.jspf" %>
		<h4 align='center'>Testamentary Charitable Lead Annuity Trust (TCLAT)</h4>
		<table align='center' width='100%'>
			<tr>
				<td colspan='2' align='center'>
				</td>
			</tr>
			<tr>
				<!--  menu  -->
				<td valign='top'>
					<%@ include file="menu.jspf"%>
				</td>
				<!-- Form/Web Page -->
				<td>
					<h3 align='left'>
						Description
					</h3>
					<table align='left' frame='box'>
						<tr>
							<td>
								Advantages and Where/Why to use
							</td>
							<td>
								<ul>
									<li>Enables the estate to be zeroed out for estate tax pruposes.</li>
									<li>Helps the estate to become "audit-proofed"</li>
								</ul>
							</td>
						</tr>
						<tr>
							<td>
								Concerns and Where Not to Use
							</td>
							<td>
								<ul>
									<li>Delays the inheritance to family for several years</li>
									<li>Does not work with QTIP assets unless QTIP trust is reformed</li>
								</ul>
							</td>
						</tr>
						<tr>
							<td>
								Other Comments
							</td>
							<td>
								<ul>
									<li>Jackie Onassis helped make this tool famous</li>
									<li>Inheritance delay can be solved with life insurance</li>
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
