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

		<title>Private Annuity</title>
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
			Private Annuity
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
									<li>Conversion of assets to an income stream.</li>
									<li>Unseful for transferring a closely held business</li>
									<li>By-pass GST rules</li>
									<li>Step-up in basis of assts transferred</li>
									<li>Obligation to pay annuity terminates at the death of the annuitant</li>
								</ul>
							</td>
						</tr>
						<tr>
							<td>
								Concerns and Where Not to Use
							</td>
							<td>
								<ul>
									<li>If seller survives longer than life expectancy</li>
									<li>Annuity payments are not deductible</li>
									<li>Not useful for terminally ill seller</li>
									<li>Payment obligation cannot be secured</li>
									<li>IRS Regs, cause taxable gain on the transfer</li>
								</ul>
							</td>
						</tr>
						<tr>
							<td>
								Other Comments
							</td>
							<td>
								<ul>
									<li>Best with rapidly appreciating assets</li>
									<li>Use valuyation discounts to increase leverage by reducing required annuity payments</li>
									<li>Use where transferor's life expectarncy is short but greater than 1 year</li>
									<li>Useful for keeping assets in the family</li>
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
