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

		<title>Testamentary Charitable Lead Annuity Trust</title>

		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<link rel="stylesheet" type="text/css" href="<%=path%>/css/styles.css"
			media="screen" />
	</head>
	<jsp:useBean id='tclat' scope='session' type='com.teag.estate.TClatTool' />
	<body>
		<%@include file="/inc/aesHeader.jspf"%>
		<h3 align='center'>
			Testamentary Charitable Lead Annuity Trust (TCLAT)
		</h3>
		<table width='100%'>
			<tr>
				<td valign='top' width='20%'>
					<%@ include file="menu.jspf"%>
				</td>
				<td valign='top' align='left' width='60%'>
					<table width='60%' align='left' frame="box">
						<tr>
							<td colspan='2' align='center'><h3>Summary</h3></td>
						</tr>
						<tr>
							<td>Term</td>
							<td align='right'><fmt:formatNumber pattern="###" value="${tclat.term }"/></td>
						</tr>
						<tr>
							<td>Section 7520 Rate</td>
							<td align='right'><fmt:formatNumber pattern="##.###%" value="${tclat.afrRate }"/></td>
						</tr>
						<tr>
							<td>Annuity Frequency</td>
							<td align='right'>${frequency}</td>
						</tr>
						<tr>
							<td>Annuity Factor</td>
							<td align='right'><fmt:formatNumber pattern="###.####" value="${tclat.annuityFactor}"/></td>
						</tr>
						<tr>
							<td>Annuity Payment Rate</td>
							<td align='right'><fmt:formatNumber pattern="###.###%" value="${tclat.paymentRate}"/></td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
		<%@include file="/inc/aesFooter.jspf"%>
	</body>
</html>
