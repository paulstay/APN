<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%@ page import="com.estate.constants.*"%>
<%@ page import="com.teag.estate.*"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://jakarta.apache.org/taglibs/input-1.0"
	prefix="input"%>
<%@ taglib tagdir="/WEB-INF/tags/teag" prefix="teag"%>
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

		<title>IDIT Grat Summary</title>

		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<link rel="stylesheet" type="text/css" href="<%=path%>/css/styles.css"
			media="screen" />
	</head>
	<jsp:useBean id='idit' scope='session' type='com.teag.estate.IditTool' />
	<body>
		<%@include file="/inc/aesHeader.jspf"%>
		<h3 align='center'>
			Sale to an Intentionally Deffective Irrevocable Trust with Grat
		</h3>
		<h3 align='center'>
			Summary Page
		</h3>
		<table width='100%'>
			<tr>
				<td valign='top' width='20%'>
					<%@ include file="menu.jspf"%>
				</td>
				<td valign='top' align='center' width='80%'>
					<table width='60%' border='1' align='center'>
						<tr>
							<th>Year</th>
							<th>Total Asset Value</th>
							<th>Note Payment</th>
							<th>Total Liquid Asets</th>
							<th>Yeild Liquid Assets</th>
							<th>Grat Payment</th>
							<th>Taxable Estate</th>
							<th>Total Estate Tax</th>
							<th>Federal Estate Tax</th>
							<th>Net To Family</th>
						</tr>
						<c:forEach var='i' begin="0" end="${idit.finalDeath +4}">
							<tr>
								<td width='9%' align='right'><fmt:formatNumber pattern="####" value="${iditTable[i][1]}"/></td>
								<td width='9%' align='right'><fmt:formatNumber pattern="###,###,###" value="${iditTable[i][2]}"/></td>
								<td width='9%' align='right'><fmt:formatNumber pattern="###,###,###" value="${iditTable[i][3]}"/></td>
								<td width='9%' align='right'><fmt:formatNumber pattern="###,###,###" value="${iditTable[i][4]}"/></td>
								<td width='9%' align='right'><fmt:formatNumber pattern="###,###,###" value="${iditTable[i][5]}"/></td>
								<td width='9%' align='right'><fmt:formatNumber pattern="###,###,###" value="${iditTable[i][6]}"/></td>
								<td width='9%' align='right'><fmt:formatNumber pattern="###,###,###" value="${iditTable[i][7]}"/></td>
								<td width='9%' align='right'><fmt:formatNumber pattern="###,###,###" value="${iditTable[i][8]}"/></td>
								<td width='9%' align='right'><fmt:formatNumber pattern="##.##%" value="${iditTable[i][9]}"/></td>
								<td width='9%' align='right'><fmt:formatNumber pattern="###,###,###" value="${iditTable[i][10]}"/></td>
							</tr>
						</c:forEach>
					</table>
				</td>
			</tr>
		</table>
		<%@include file="/inc/aesFooter.jspf"%>
	</body>
</html>
