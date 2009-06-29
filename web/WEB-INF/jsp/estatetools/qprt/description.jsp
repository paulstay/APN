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

		<title>QPRT</title>
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
			Qualified Personal Residence Trust
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
									<li>Discount Value of asset(s) being transferred to heirs</li>
									<li>Where objective is to keep residence or vacation home in family for generations</li>
									<li>use his/her trusts for fractional interest discount and preserve half if one dies before their trust ends.</li>
									<li>A creation of IRS Regs. with predictable results</li>
								</ul>
							</td>
						</tr>
						<tr>
							<td>
								Concerns and Where Not to Use
							</td>
							<td>
								<ul>
									<li>Be careful where age/health will shorten life expectancy</li>
									<li>Grantor must outlive trust term or full value reverts to taxable estate (per IRC 2036)</li>
									<li>No step-up in basis upon death (however the capital gains tax bracket is much lower than the estate tax bracket).</li>
								</ul>
							</td>
						</tr>
						<tr>
							<td>
								Other Comments
							</td>
							<td>
								<ul>
									<li>Leverage is much less than available with GRATs/CLATs</li>
									<li>Do no place residence in LLC/FLP</li>
									<li>If numbers do not work, use life insurance to buy residence/vacation home from the estate at deaths</li>
									<li>Maximum of three QPRTS per married couple</li>
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
