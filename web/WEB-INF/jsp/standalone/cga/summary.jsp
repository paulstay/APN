<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%@ page import="com.estate.constants.*"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://jakarta.apache.org/taglibs/input-1.0"
	prefix="input"%>
<%@ taglib tagdir="/WEB-INF/tags/teag" prefix="teag"%>
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

		<title>CGA Summary</title>

		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<link rel="stylesheet" type="text/css" href="<%=path%>/css/styles.css" media="screen" />
	</head>
	<body>
		<%@include file="/inc/aesHeader.jspf" %>

		<h3 align='center'>
			Charitable Gift Annuity Contract
		</h3>
		<h4 align='center'>
			Summary
		</h4>
		<jsp:useBean id='userInfo' scope='session'
			type='com.teag.bean.PdfBean' />
		<jsp:useBean id='cga' scope='session' 
			type='com.estate.toolbox.CGA' />
		<%
			cga.calculate();
		%>
		<table>
			<tr>
				<td valign='top'><%@include file='menu.jspf'%></td>
				<td align='center' width='100%'>
					<table width='60%'>
						<tr>
							<td colspan='3' align='center'>
								<h3>
									Assumptions
								</h3>
							</td>
						</tr>
						<tr>
							<td>
								IRS 7520 Rate
							</td>
							<td>
								&nbsp;
							</td>
							<td align='right'>
								<fmt:formatNumber pattern='##.##%' value='${cga.irsRate }' />
							</td>
						</tr>
						<tr>
							<td>
								IRS 7520 Transfer Date
							</td>
							<td>
								&nbsp;
							</td>
							<td align='right'>
								<fmt:formatDate pattern='M/d/yyyy' value='${cga.irsDate }' />
							</td>
						</tr>
						<tr>
							<td>
								Beginning Principal
							</td>
							<td>
								&nbsp;
							</td>
							<td align='right'>
								<fmt:formatNumber pattern='$###,###,###' value='${cga.fmv }' />
							</td>
						</tr>
						<tr>
							<td>
								Beginning Basis
							</td>
							<td>
								&nbsp;
							</td>
							<td align='right'>
								<fmt:formatNumber pattern='$###,###,###' value='${cga.basis }' />
							</td>
						</tr>
						<tr>
							<td>
								Client Name(s)
							</td>
							<td>
								&nbsp;
							</td>
							<td align='right'>
								${userInfo.firstName}
							</td>
						</tr>
						<tr>
							<td>
								Client Age(s)
							</td>
							<td>
								&nbsp;
							</td>
							<td align='right'>
								(
								<fmt:formatNumber pattern='###' value='<%=cga.getClientAge()%>' />
								)
								<c:if test="${cga.spouseAge >0 }">&nbsp;
									(<fmt:formatNumber pattern='###'
										value='<%=cga.getSpouseAge()%>' />)
								</c:if>
							</td>
						</tr>
						<tr>
							<td>
								Life Expectancy (combined)
							</td>
							<td>
								&nbsp;
							</td>
							<td align='right'>
								<fmt:formatNumber pattern='##.#' value='${cga.lifeExpectancy }' />
							</td>
						</tr>
						<tr>
							<td>
								Payment Schedule
							</td>
							<td>
								&nbsp;
							</td>
							<td align='right'>
								<c:choose>
								<c:when test="${cga.frequency==1}">Annual</c:when>
								<c:when test="${cga.frequency==2}">Semi-Annual</c:when>
								<c:when test="${cga.frequency==4}">Quarterly</c:when>
								<c:otherwise>Monthly</c:otherwise>
								</c:choose>
							</td>
						</tr>
						<tr>
							<td>
								Payment at
							</td>
							<td>
								&nbsp;
							</td>
							<td align='right'>
								<c:choose>
									<c:when test="${cga.endBegin == 0 }">End of period</c:when>
									<c:otherwise>Beginning of period</c:otherwise>
								</c:choose>
							</td>
						</tr>
						<tr>
							<td colspan='3'>
								&nbsp;
							</td>
						</tr>
						<tr>
							<td>
								Maximum Annuity Rate
							</td>
							<td>
								&nbsp;
							</td>
							<td align='right'>
								<fmt:formatNumber pattern='##.###%'
									value='${cga.maxAnnuityRate }' />
							</td>
						</tr>
						<tr class='text-bold'>
							<td>
								Annuity Rate
							</td>
							<td>
								&nbsp;
							</td>
							<td align='right'>
								<fmt:formatNumber pattern='##.###%' value='${cga.annuityRate}' />
							</td>
						</tr>
						<tr>
							<td>
								Annuity Factor
							</td>
							<td>
								&nbsp;
							</td>
							<td align='right'>
								<fmt:formatNumber pattern='##.####'
									value='${cga.annuityFactor }' />
							</td>
						</tr>
						<tr class='text-bold'>
							<td>
								Annuity Payment
							</td>
							<td>
								&nbsp;
							</td>
							<td align='right'>
								<fmt:formatNumber pattern='$###,###,###'
									value='${cga.annuityPayment }' /></td>
						</tr>
						<tr>
							<td colspan='3'>
								&nbsp;
							</td>
						</tr>
						<tr>
							<td>
								Annuity Interest
							</td>
							<td>
								&nbsp;
							</td>
							<td align='right'>
								<fmt:formatNumber pattern='$###,###,###'
									value='${cga.annuityInterest }' />
							</td>
						</tr>
						<tr>
							<td>
								Charitable Deduction
							</td>
							<td>
								&nbsp;
							</td>
							<td align='right'>
								<fmt:formatNumber pattern='$###,###,###'
									value='${cga.remainderInterest }' />
							</td>
						</tr>
						<tr>
							<td colspan='3'>
								&nbsp;
							</td>
						</tr>
						<tr class='text-blue'>
							<td>
								Payment
							</td>
							<td>
								&nbsp;
							</td>
							<td align='right'>
								<fmt:formatNumber pattern='$###,###,###'
									value='${cga.annuityPayment }' /></td>
						</tr>
						<tr class='text-green'>
							<td>&nbsp;&nbsp;&nbsp;
								Ordinary Income
							</td>
							<td>
								&nbsp;
							</td>
							<td align='right'>
								<fmt:formatNumber pattern='$###,###,###'
									value='${cga.ordinaryIncome }' /></td>
						</tr>
						<tr class='text-green'>
							<td>&nbsp;&nbsp;&nbsp;
								Capital Gains Income
							</td>
							<td>
								&nbsp;
							</td>
							<td align='right'>
								<fmt:formatNumber pattern='$###,###,###'
									value='${cga.capitalGains }' /></td>
						</tr>
						<tr class='text-green'>
							<td>&nbsp;&nbsp;&nbsp;
								Tax Free Income
							</td>
							<td>
								&nbsp;
							</td>
							<td align='right'>
								<fmt:formatNumber pattern='$###,###,###' value='${cga.taxFree }' /></td>
						</tr>

					</table>
				</td>
			</tr>
		</table>
		<%@include file="/inc/aesFooter.jspf" %>
	</body>
</html>
