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
		<link rel="stylesheet" type="text/css" href="${path}/css/styles.css" media="screen" />
	</head>
	<jsp:useBean id='rpm' scope='session' type='com.teag.estate.RpmTool'/>
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
						<form name='iform' id='iform' action="servlet/RpmSummaryServlet" method="post">
								<teag:select name="pageView" onChange="document.iform.submit()">
									<teag:option label="Before Planning" value="current" selected="current"/>
									<teag:option label="After Planning" value="rpm"/>
									<teag:option label="Comparison" value="comparison"/>
								</teag:select>
						</form>
					</div>
					<table align='center' width='95%' border='1'>
						<tr>
							<td align='center' colspan='12'><h3 align='center'>Current Summary Page</h3></td>
						</tr>
						<tr>
						<td colspan='2' align='center'>Year</td>
						<td>Plan Balance Beg Of Year</td>
						<td>Earning @ <fmt:formatNumber pattern="##.##%" value="${rpm.planGrowth}"/></td>
						<td>Minimum Distribution</td>
						<td>Balance End of Year</td>
						<td>Retirement Plan Taxes at Death</td>
						<td>% to Taxes</td>
						<td>Accum'd in Trust</td>
						<td>Plan and Trust Money</td>
						<td>Total Taxes</td>
						<td>Net To Family</td>
						</tr>
						<c:forEach var="idx" begin='0' end='${tableLen-1}'>		
							<tr>
								<td width='5%' align='right'><fmt:formatNumber pattern="###" value="${cTable[idx][0] }"/></td>
								<td width='5%' align='center'><fmt:formatNumber pattern="###" value="${cTable[idx][1] }"/></td>
								<td width='9%' align='right'><fmt:formatNumber pattern="###,###,###" value="${cTable[idx][2] }"/></td>
								<td width='9%' align='right'><fmt:formatNumber pattern="###,###,###" value="${cTable[idx][3] }"/></td>
								<td width='9%' align='right'><fmt:formatNumber pattern="###,###,###" value="${cTable[idx][4] }"/></td>
								<td width='9%' align='right'><fmt:formatNumber pattern="###,###,###" value="${cTable[idx][5] }"/></td>
								<td class='text-red' width='9%' align='right'><fmt:formatNumber pattern="###,###,###" value="${cTable[idx][6] }"/></td>
								<td class='text-red' width='9%' align='right'><fmt:formatNumber pattern="##.###%" value="${cTable[idx][7] }"/></td>
								<td width='9%' align='right'><fmt:formatNumber pattern="###,###,###" value="${cTable[idx][8] }"/></td>
								<td width='9%' align='right'><fmt:formatNumber pattern="###,###,###" value="${cTable[idx][9] }"/></td>
								<td style='color: red;' width='9%' align='right'><fmt:formatNumber pattern="###,###,###" value="${cTable[idx][10] }"/></td>
								<td style='color: green;' width='9%' align='right'><fmt:formatNumber pattern="###,###,###" value="${cTable[idx][11] }"/></td>
							</tr>
						</c:forEach>
					</table>
				</td>
			</tr>
		</table>
	<%@include file="/inc/aesFooter.jspf" %>	
	</body>
</html>
