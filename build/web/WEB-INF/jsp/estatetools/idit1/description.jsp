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

		<title>IDIT</title>
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
			Intentionally Defective Irrevocable Trust
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
									<li>Effective estate Freeze</li>
									<li>For income tax purposes the trust is traxed to grantor</li>
									<li>For estate tax purposes the trust is out of the estate</li>
									<li>More flexible than a GRAT</li>
									<li>FMV of note is in the estate</li>
								</ul>
							</td>
						</tr>
						<tr>
							<td>
								Concerns and Where Not to Use
							</td>
							<td>
								<ul>
									<li>Special trust provisions for S corps</li>
									<li>Sale may be a gift if the trust is unfunded</li>
									<li>Carryover basis for trust beneficiaries</li>
									<li>Capital gains upon premature death</li>
								</ul>
							</td>
						</tr>
						<tr>
							<td>
								Other Comments
							</td>
							<td>
								<ul>
									<li>Useful for highly appreciating assets with significant tax exposure</li>
									<li>Trust property should be expected to outperform AFR</li>
									<li>Can use a balloon note with annual interest payments</li>
								</ul>
							</td>
						</tr>
						<tr>
							<td>Comparison between IDIT and GRAT</td>
							<td>
								<table>
									<tr>
										<td>Sale to IDIT</td>
										<td>GRAT</td>
									</tr>
									<tr>
										<td>
											<ul>
												<li>Tax treatment uncertain (less settled)</li>
												<li>May require up-front taxable gift (funding 10%)</li>
												<li>Interest only balloon note permissible-but still in estate</li>
												<li>Lower AFR hurdle rate</li>
												<li>Favorable GST planning</li>
												<li>Effective Estate Freeze Technique</li>
											</ul>
										</td>
										<td>
											<ul>
												<li>tax treatment certain</li>
												<li>Can zero-out remainder gift</li>
												<li>Annuity payments = value transfered</li>
												<li>7520 hurdle rate</li>
												<li>Major mortality rish - must servive trust period or reverts to taxable estate</li>
												<li>Favorable GST planning with the sale of the remainder interest</li>
											</ul>
										</td>
									</tr>
								</table>
							</td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
<%@include file="/inc/aesFooter.jspf" %>
	</body>
</html>
