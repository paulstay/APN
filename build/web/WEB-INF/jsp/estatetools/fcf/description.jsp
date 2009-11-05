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

		<title>Family Charitable Foundations</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<link rel="stylesheet" type="text/css" href="<%=path %>/css/styles.css">
	</head>

<body>
<%@include file="/inc/aesHeader.jspf" %>
		<h4 align='center'>Family Charitable Foundations</h4>
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
							<td colspan='2'>
								<p>Under the general category of a "foundations" there are three (3) different types.
								The term generally refers to a private charity as contrasted with a public charity.</p>
								<p>The differences relate to tax benefits, paper work, and control. As an introduciton
								the following summary is pertinent.</p>
							</td>
						</tr>
						<tr>
							<td colspan='2'>
								<table align='center' border='1'>
									<tr>
										<td>Name of Foundation</td>
										<td align='center'>Complication</td>
										<td align='center'>Tax Benefits</td>
										<td align='center'>Family Control</td>
									</tr>
									<tr>
										<td>Donor Advised Fund</td>
										<td align='center'>Low</td>
										<td align='center'>Maximum</td>
										<td align='center'>No</td>
									</tr>
									<tr>
										<td>Supporting Organization</td>
										<td align='center'>Medium</td>
										<td align='center'>Maximum</td>
										<td align='center'>No</td>
									</tr>
									<tr>
										<td>Private Foundation</td>
										<td align='center'>High</td>
										<td align='center'>Least</td>
										<td align='center'>Yes</td>
									</tr>
								</table>
							</td>
						</tr>
						<tr>
							<td>
								Advantages and Where/Why to use
							</td>
							<td>
								<ul>
									<li>When client wants current tax deduction - but wishes to extend charitable gifts into the future.</li>
									<li>Donor Advise Fund - Usually ip to $1 million/25 years (Public Charity Tax Treatment)</li>
									<li>Supporting Organizations - Usually over $1 million/25 years (Pbulic Charity Tax Treatment)</li>
									<li>Private Fundation - Over %5 million, Private Charity Tax Treatment, where much control is desired</li>
								</ul>
							</td>
						</tr>
						<tr>
							<td>
								Concerns and Where Not to Use
							</td>
							<td>
								<ul>
									<li>Where client will be dispersing all funds to the charity(ies) within the calendar year.</li>
									<li> DAF - Where much control is desired.</li>
									<li> SO - Where total control of directors is desired.</li>
									<li> PF - Where Public Charity Tax treatment is desired, where lower adminstrative costs are desired.</li>
								</ul>
							</td>
						</tr>
						<tr>
							<td>
								Other Comments
							</td>
							<td>
								<ul>
									<li>Donations are made to charities in the family's name</li>
									<li>Enables an immediate tax deduction while client.family selects the desired charities</li>
									<li>Children, grandchildren, etc. can give input regarding charities while ultimate control can still reside with the parents.</li>
									<li>Wonderful way to teach/involve heirs in philanthropy</li>
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
