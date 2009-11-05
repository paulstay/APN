<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%@ page import="com.estate.constants.*"%>
<%@ page import="com.teag.estate.*"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ page import="com.estate.constants.*"%>
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
		<link rel="stylesheet" type="text/css" href="<%=path%>/css/styles.css"
			media="screen" />
	</head>
	<jsp:useBean id='fcf' scope='session' type='com.teag.estate.FCFTool' />
	<body>
		<%@include file="/inc/aesHeader.jspf"%>
		<h3 align='center'>
			Family Charitable Foundations
		</h3>
		<h3 align='center'>
			Summary Page
		</h3>
		<table width='100%'>
			<tr>
				<td valign='top' width='20%'>
					<%@ include file="menu.jspf"%>
				</td>
				<td valign='top' align='left' width='60%'>
					<c:choose>
						<c:when test="${fcf.type == 'D' }">
							<h3>
								Donor Advised Funds
							</h3>
							<p style='text-align: left'>
								The Donor Advised Fund ("DAF") will be set up by a public
								charity and becomes a kind of holding fund for your
								contributions while you decide what public charity or charities
								you want to support. You can take a charitable deduction when
								making the contribution even though there might be a delay for
								the funds to go to a public charity. The deduction will be the
								same for cash as if you gave directly to the public charity. If
								you make a gift of appreciated personal or real property then
								the deduction is limited to 30% of your adjusted gross income
								("AGI") with a 5 year carry over.
							</p>
							<p style='text-align: left'>
								The public charity you work with when setting up the DAF will
								take care of the paper work.
							</p>
							<p style='text-align: left'>
								This is a low maintenance program with a life of about 25 years.
								It is useful for smaller contributions.
							</p>
						</c:when>
						<c:when test="${fcf.type=='S' }">
							<h3>
								Supporting Organization
							</h3>
							<p style='text-align: left'>
								The Supporting Organization ("SO") is useful for a minimum
								$1,000,000 or more on a long term basis. The $1,000,000 minimum
								is my opinion and not some statutory amount. It is of medium
								maintenance. It can remain in tact through multi-generations if
								desirable. Paper work will be taken care of by the primary
								public charity which is supported.
							</p>
							<p style='text-align: left'>
								You can have substantial input with less than a majority of the
								directors. This means that you do not have ultimate control. I
								am inclosing a sample of a SO which supports specific charities
								with 2/3's of the income and various other public charities with
								1/3 of the income for your review. If you were to proceed with
								this particular kind of SO, the list of supported organizations
								(Schedule A) can be adjusted for the public charities you are
								interested in. The majority of the directors are non-family but
								will usually look to you and your family for guidance.
							</p>
						</c:when>
						<c:when test="${fcf.type=='P' }">
							<h3>
								Private Foundation
							</h3>
							<p style='text-align: left'>
								A true Private Foundation ("PF") is a high maintenance
								organization. You are responsible for all of the reporting and
								in making sure there are no violations of the IRS rules. For
								these reasons it is usually recommend the client fund the PF
								with about $5,000,000.
							</p>
							<p style='text-align: left'>
								One significant advantage of the PF is that you and your family
								can have ultimate control but at a price which not only includes
								the increased reporting but also the IRS percentages change.
							</p>
						</c:when>
					</c:choose>
				</td>
			</tr>
		</table>
		<%@include file="/inc/aesFooter.jspf"%>
	</body>
</html>
