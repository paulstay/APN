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

		<title>Irrevocable Life Insurance Trust</title>
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
			Irrevocable Life Insurance Trust ("ILIT")
		</h4>
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
					<h3 align='center'>
						Description
					</h3>
					<table align='center' frame='box'>
						<tr>
							<td>
								<p style='text-align: left'>
									Life Insurance remains one of the most effective legal tax
									avoidance techniques for transfering wealth to heirs. The
									entire value of an insurance policy death benefit can pass to
									heirs-free of income, estate and gift taxes - if properly
									owned. Funding for the insurance premium to pay by the trustee
									of the ILIT can be by "Crummey" gifts of $13,000 from each
									donor for the benefit of each donee (beneficiary of the ILIT).
								</p>
								<p style='text-align: left'>
									Combine the values from the various tools (GRAT, CLAT, RPM,
									IDIT) for the premiums, and death benefits throughout the plan.
								</p>
							</td>
						</tr>
						<tr>
							<td>
								<table>
									<tr>
										<td>
											<h3>
												Key Benefits
											</h3>
										</td>
									</tr>
									<tr>
										<td>
											<ul>
												<li>
													Make current gifts to family members.
												</li>
												<li>
													Accumulate assets outside grantor's taxable estate.
												</li>
												<li>
													Protect assets from claims of creditors.
												</li>
												<li>
													Avoid income tax on the accumulation of funds.
												</li>
												<li>
													Avoid estate tax upon the distribution of funds to the
													family
												</li>
												<li>
													Create a source of liquidity to cover estate taxes or
													expenses
												</li>
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
		<%@include file="/inc/aesFooter.jspf"%>
	</body>
</html>
