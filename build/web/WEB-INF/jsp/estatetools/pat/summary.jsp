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

		<title>Private Annuity Summary</title>

		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<link rel="stylesheet" type="text/css" href="<%=path%>/css/styles.css"
			media="screen" />
	</head>
	<jsp:useBean id='epg' scope='session'
		type='com.teag.webapp.EstatePlanningGlobals' />
	<jsp:useBean id='pat' scope='session' type='com.teag.estate.PrivateAnnuityTool' />
	<body>
		<%@include file="/inc/aesHeader.jspf"%>
		<table border='0' width='100%'>
			<tr>
				<td width='15%' valign='top'>
					<%@ include file="menu.jspf"%>
				</td>
				<td valign='top' align='left' width='65%'>
					<table width='85%' align='left' frame="box">
						<tr>
							<td align='center' colspan='5'>
								<h4>
									Assumptions
								</h4>
							</td>
						</tr>
						<tr>
							<td align='right'>
								Value of Assets
							</td>
							<td align='right'>
								<fmt:formatNumber type="currency" value="${pat.amount}" />
							</td>
							<td>&nbsp;&nbsp;</td>
							<td align='left'>
								7520 Rate
							</td>
							<td align='right'>
								<fmt:formatNumber pattern="##.###%" value="${pat.afrRate}" />
							</td>
						</tr>
						<tr>
							<td align='right'>
								Basis
							</td>
							<td align='right'>
								<fmt:formatNumber type="currency" value="${pat.basis}" />
							</td>
							<td>&nbsp;&nbsp;</td>
							<td align='left'>
								7520 Date
							</td>
							<td align='right'>
								<fmt:formatDate pattern="M/d/y" value="${pat.afrDate}" />
							</td>
						</tr>

						<tr>
							<td align='right'>
								Income Tax Rate
							</td>
							<td align='right'>
								<fmt:formatNumber type="percent" value="${pat.incomeTaxRate}" />
							</td>
							<td>&nbsp;&nbsp;</td>
							<td align='left'>
								Annuity Payments per Year
							</td>
							<td align='right'>
								<fmt:formatNumber value="${pat.annuityFreq}" />
							</td>
						</tr>
						<tr>
							<td align='right'>
								Capital Gains Tax
							</td>
							<td align='right'>
								<fmt:formatNumber type="percent" value="${pat.capitalGainsRate}" />
							</td>
							<td>&nbsp;&nbsp;</td>
							<td align='left'>
								Annuity increase per Year
							</td>
							<td align='right'>
								<fmt:formatNumber type="percent" value="${pat.annuityIncrease}" />
							</td>
						</tr>
						<tr>
							<td align='right'>
								Estate Tax Rate
							</td>
							<td align='right'>
								<fmt:formatNumber type="percent" value="${pat.estateTaxRate}" />
							</td>
							<td>&nbsp;&nbsp;</td>
							<td align='left'>
								AnnuityPayment Begin/End
							</td>
							<c:if test="${pat.annuityType == 1 }">
								<td align='right'>
									End
								</td>
							</c:if>
							<c:if test="${pat.annuityType == 0 }">
								<td align='right'>
									Begin
								</td>
							</c:if>
						</tr>
						<tr>
							<td align='right'>
								Clients age(s)
							</td>
							<td align='right'>${epg.clientAge} and ${epg.spouseAge}</td>
							<td>&nbsp;&nbsp;</td>
							<td align='left'>
								Number of Annuitants
							</td>
							<td align='right'>
								<c:if test="${pat.useBoth == 'B' }">2</c:if>
								<c:if test="${pat.useBoth == 'C' }">1</c:if>
								<c:if test="${pat.useBoth == 'S' }">1</c:if>
							</td>
						</tr>
					</table>
					<br>
					<table align='center' width='90%'>
							<tr>
								<td>&nbsp;</td>
							</tr>
							<tr>
								<td align='center'><b>Up Front Capital Gains Tax</b> : <fmt:formatNumber pattern="$###,###,###" value="${pat.capGains}"/></td>
							</tr>
					</table>
					<br>
					<table align='center' border='1' width='55%'>
						<tr>
							<td colspan='4' align='center'>Annuity Payment Schedule</td>
						</tr>
						<tr>
							<td>Year</td>
							<td align='right'>Annuity Payment</td>
							<td align='right'>Ordinary Income</td>
							<td align='right'>Tax Free Portion</td>
						</tr>
						<c:forEach var="idx" begin="0" end="${epg.clientLifeExp + 5 }">
							<tr>
							<td>${idx + 1 }</td>
							<td align='right'><fmt:formatNumber pattern="###,###,###" value="${pat.annPayment[idx]}"/></td>
							<td align='right'><fmt:formatNumber pattern="###,###,###" value="${pat.ordIncome[idx]}"/></td>
							<td align='right'><fmt:formatNumber pattern="###,###,###" value="${pat.taxFreeTable[idx]}"/></td>
							</tr>
						</c:forEach>
					</table>
				</td>
			</tr>
		</table>
		<%@include file="/inc/aesFooter.jspf"%>
	</body>
</html>
