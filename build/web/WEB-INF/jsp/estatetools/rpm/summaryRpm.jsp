<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%@ page import="com.estate.constants.*"%>
<%@ page import="com.teag.estate.*"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="teagtags" prefix="teag"%>
<%@ page import="com.estate.constants.*"%>
<%@ include file="/inc/init.jspf"%>
<%@ include file="/inc/ept.jspf"%>
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

		<title>RPM Summary</title>

		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<link rel="stylesheet" type="text/css" href="${path}/css/styles.css"
			media="screen" />
	</head>
	<jsp:useBean id='rpm' scope='session' type='com.teag.estate.RpmTool' />
	<body>
		<%@include file="/inc/aesHeader.jspf"%>
		<h3 align='center'>
			Retirement Plan Maximizer &trade;
		</h3>
		<table>
			<tr>
				<td valign='top'>
					<%@ include file="menu.jspf"%>
				</td>
				<td valign='top' align='center' width='80%'>
					<div align='center'>
						<form name='iform' id='iform' action="servlet/RpmSummaryServlet"
							method="post">
							<teag:select name="pageView" onChange="document.iform.submit()">
								<teag:option label="Before Planning" value="current" />
								<teag:option label="After Planning" value="rpm"  selected="rpm"/>
								<teag:option label="Comparison" value="comparison" />
							</teag:select>
						</form>
					</div>
					<table align='left' width='95%' border='1'>
						<tr>
							<td align='center' colspan='12'>
								<h3 align='center'>
									Current Summary Page
								</h3>
							</td>
						</tr>
						<tr>
							<td class='bg-color2' colspan='6' align='center'>
								The RETIREMENT PLAN MAXIMIZER (RPM)
							</td>
							<td class='bg-color2' colspan='6' align='center'>
								DISTRIBUTION AFTER BOTH DEATHS
							</td>
						</tr>
						<tr>
							<td align='center' colspan='2'>
								Year
							</td>
							<td align='center'>
								Plan Balance Beg Of Year
							</td>
							<td align='center'>
								Earning @
								<fmt:formatNumber type='percent' value='${rpm.planGrowth}' />
							</td>
							<td align='center'>
								Plan Distribution
							</td>
							<td align='center'>
								Balance End of Year
							</td>
							<td align='center'>
								Total Taxes
							</td>
							<td align='center'>
								Net Distribution to Trust
							</td>
							<td align='center'>
								Trust Owned Ins Premium
							</td>
							<td align='center'>
								Year End Value of Trust
							</td>
							<td align='center'>
								Life Insurance Death Benefit
							</td>
							<td align='center'>
								Net To Family
							</td>
						</tr>
						<c:forEach var="idx" begin='0' end='${tableLen}'>
							<tr>
								<td width='5%' align='right'>
									<fmt:formatNumber pattern="###" value="${rTable[idx][0] }" />
								</td>
								<td width='5%' align='center'>
									<fmt:formatNumber pattern="###" value="${rTable[idx][1] }" />
								</td>
								<td width='9%' align='right'>
									<fmt:formatNumber pattern="###,###,###"
										value="${rTable[idx][2] }" />
								</td>
								<td width='9%' align='right'>
									<fmt:formatNumber pattern="###,###,###"
										value="${rTable[idx][3] }" />
								</td>
								<td width='9%' align='right'>
									<fmt:formatNumber pattern="###,###,###"
										value="${rTable[idx][4] }" />
								</td>
								<td width='9%' align='right'>
									<fmt:formatNumber pattern="###,###,###"
										value="${rTable[idx][5] }" />
								</td>
								<td class='text-red' width='9%' align='right'>
									<fmt:formatNumber pattern="###,###,###"
										value="${rTable[idx][6] }" />
								</td>
								<td class='text-red' width='9%' align='right'>
									<fmt:formatNumber pattern="###,###,###" value="${rTable[idx][7] }" />
								</td>
								<td width='9%' align='right'>
									<fmt:formatNumber pattern="###,###,###"
										value="${rTable[idx][8] }" />
								</td>
								<td width='9%' align='right'>
									<fmt:formatNumber pattern="###,###,###"
										value="${rTable[idx][9] }" />
								</td>
								<td style='color: red;' width='9%' align='right'>
									<fmt:formatNumber pattern="###,###,###"
										value="${rTable[idx][10] }" />
								</td>
								<td style='color: green;' width='9%' align='right'>
									<fmt:formatNumber pattern="###,###,###"
										value="${rTable[idx][11] }" />
								</td>
							</tr>
						</c:forEach>
					</table>
				</td>
			</tr>
		</table>
		<%@include file="/inc/aesFooter.jspf"%>
	</body>
</html>
