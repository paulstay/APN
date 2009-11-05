<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%@ page import="com.estate.constants.*"%>
<%@ page import="com.teag.estate.*"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://jakarta.apache.org/taglibs/input-1.0"
	prefix="input"%>
<%@ taglib tagdir="/WEB-INF/tags/teag" prefix="teag"%>
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

		<title>FLP Summary</title>

		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<link rel="stylesheet" type="text/css" href="<%=path%>/css/styles.css"
			media="screen" />

	</head>
	<jsp:useBean id='flp' scope='session' type='com.teag.estate.FlpTool' />
	<%
		flp.calculate();
	%>
	<body>
		<%@ include file="/inc/aesHeader.jspf" %>
		<h3 align='center'>
			Family Limited Partnership
		</h3>
		<h3 align='center'>
			Summary Page
		</h3>
		<table>
			<tr>
				<td valign='top'>
					<%@ include file="menu.jspf"%>
				</td>
				<td valign='top' align='center' width='80%'>
					<table align='center' width='40%'>
						<tr>
							<td align='right'>
								Total Value
							</td>
							<td align='right'>
								Growth
							</td>
							<td align='right'>
								Income
							</td>
						</tr>
						<tr>
							<td align='right'>
								<fmt:formatNumber pattern="$###,###,###,###"
									value="${flp.assets.totalValue }" />
							</td>
							<td align='right'>
								<fmt:formatNumber pattern="##.###%"
									value="${flp.assets.weightedGrowth }" />
							</td>
							<td align='right'>
								<fmt:formatNumber pattern="##.###%"
									value="${flp.assets.weightedIncome }" />
							</td>
						</tr>
					</table>
					<br>
					<br>
					<table align='center' cellpadding='2'>
						<tr>
							<td>
								Value of FLP General Partnership Shares
							</td>
							<td align='right'>
								<fmt:formatNumber pattern="$###,###,###,###"
									value="${flp.generalPartnerShares * flp.assets.totalValue }" />
							</td>
						</tr>
						<tr>
							<td>
								Value of FLP Limited Partnership Shares
							</td>
							<td align='right'>
								<fmt:formatNumber pattern="$###,###,###,###"
									value="${flp.limitedPartnerShares * flp.assets.totalValue }" />
							</td>
						</tr>
						<tr>
							<td>
								<span class='text-green'>Discounted Value (<fmt:formatNumber
										type="percent" value="${flp.discountRate}" />) of FLP Limited
									Partnership Shares</span>
							</td>
							<td align='right'>
								<span class='text-green'> <fmt:formatNumber
										pattern="$###,###,###"
										value="${(1 - flp.discountRate) * (flp.limitedPartnerShares * flp.assets.totalValue)}" />
								</span>
							</td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
		<%@ include file="/inc/aesFooter.jspf" %>
	</body>
</html>
