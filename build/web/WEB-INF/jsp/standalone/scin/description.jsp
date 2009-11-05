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

		<title>SCIN</title>
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
			Self Canceling Installment Note
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
									<li>Deferral of Capital Gains Taxes</li>
									<li>Conversion of assets to income stream from note</li>
									<li>May be useful for transferring ownership in family business</li>
									<li>By-pass GST rules</li>
									<li>Step-up depreciable basis for transferee</li>
									<li>Obligation to pay note terminates at the death of the seller</li>
								</ul>
							</td>
						</tr>
						<tr>
							<td>
								Concerns and Where Not to Use
							</td>
							<td>
								<ul>
									<li>If seller survives note term, propery FMV + prem,ium will be paid unless note renegotiated</li>
									<li>Depreciation recapture may cause problems to seller.</li>
									<li>related party tax provisions may restrict transferee from reselling.</li>
								</ul>
							</td>
						</tr>
						<tr>
							<td>
								Other Comments
							</td>
							<td>
								<ul>
									<li>Avoid if property is debt-encumbered</li>
									<li>Don't use dbt financed assets</li>
									<li>Cannot use if sole proprietor/general partnership as this requres management.</li>
									<li>
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
