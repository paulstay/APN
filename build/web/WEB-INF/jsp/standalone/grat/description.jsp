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
		<title>GRAT Description</title>
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
			Grantor Retained Annuity Trust
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
									<li>Discount assets being transferred to heirs</li>
									<li>Assets should generate good cash flow</li>
									<li>Can add liquid assets or borrow money to augment cash flow</li>
								
								</ul>
							</td>
						</tr>
						<tr>
							<td>
								Concerns and Where Not to Use
							</td>
							<td>
								<ul>
									<li>Grantor must outlive trust term or assets revert to estate</li>
									<li>No step-up in basis at death</li>
									<li>Cash flow gone (from client) at end of trust term</li>
								
								</ul>
							</td>
						</tr>
						<tr>
							<td>
								Other Comments
							</td>
							<td>
								<ul>
									<li>Use separate trusts if both spouses involved</li> 
									<li>Adding life insurance provides heads or tails/win</li>
									<li>Can layer/stagger the periods</li>
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
