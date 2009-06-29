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

		<title>CRT Summary</title>

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
		<h3 align='center'>
			Charitable Remainder Trust
		</h3>
		<h4 align='center'>
			Summary
		</h4>
		<jsp:useBean id='userInfo' scope='session'
			type='com.teag.bean.PdfBean' />
		<jsp:useBean id='sCrt' scope='session' type='com.estate.toolbox.CRT' />
		<table>
			<tr>
				<td valign='top'><%@include file='menu.jspf'%></td>
				<td align='center' width='100%'>
					<table>
						<tr>
							<td colspan='5' align='center'>
								<h3>
									Assumptions
								</h3>
							</td>
						</tr>
						<tr>
							<td>
								Fair market Value of Assets
							</td>
							<td align='right'>
								<fmt:formatNumber pattern='###,###,###' value='${sCrt.fmv }' />
							</td>
							<td>
								&nbsp;
							</td>
							<td>
								Date Calculated
							</td>
							<td align='right'>
								<fmt:formatDate pattern='M/d/yyyy' value='${sCrt.irsDate }' />
							</td>
						</tr>
						<tr>
							<td>
								Liability
							</td>
							<td align='right'>
								<fmt:formatNumber pattern='###,###,###'
									value='${sCrt.liability }' />
							</td>
							<td>
								&nbsp;
							</td>
							<td>
								IRC Sec. 7520 rate used
							</td>
							<td align='right'>
								<fmt:formatNumber pattern='##.##%' value='${sCrt.irsRate }' />
							</td>
						</tr>
						<tr>
							<td>
								Basis
							</td>
							<td align='right'>
								<fmt:formatNumber pattern='###,###,###' value='${sCrt.basis }' />
							</td>
							<td>
								&nbsp;
							</td>
							<td>
								Charitable Deduction Factor
							</td>
							<td align='right'>
								<fmt:formatNumber pattern='##.#####'
									value='${sCrt.charitableDeductionFactor }' />
							</td>
						</tr>
						<tr>
							<td>
								Marginal Income Tax Rate (fed and State)
							</td>
							<td align='right'>
								<fmt:formatNumber pattern='##.###%'
									value='${sCrt.marginalTaxRate }' />
							</td>
							<td>
								&nbsp;
							</td>
							<td>
								Charitable Deduction
							</td>
							<td align='right'>
								<fmt:formatNumber pattern='###,###,###'
									value='${sCrt.charitableDeduction }' />
							</td>
						</tr>
						<tr>
							<td>
								Future Estate Tax Rate
							</td>
							<td align='right'>
								<fmt:formatNumber pattern='##.##%'
									value='${sCrt.estateTaxRate }' />
							</td>
							<td>
								&nbsp;
							</td>
							<td>
								Adjusted Gross Income (AGI) before payout
							</td>
							<td align='right'>
								<fmt:formatNumber pattern='###,###,###' value='${sCrt.agi }' />
							</td>
						</tr>
						<tr>
							<td>
								Capital Gains Tax Rate (Fed and State)
							</td>
							<td align='right'>
								<fmt:formatNumber pattern='##.###%'
									value='${sCrt.capitalGainsTax }' />
							</td>
							<td>
								&nbsp;
							</td>
							<td>
								Deduction Limitation of AGI
							</td>
							<td align='right'>
								<fmt:formatNumber pattern='##.###%'
									value='${sCrt.agiLimitation }' />
							</td>
						</tr>
						<tr>
							<td>
								Investment Return
							</td>
							<td align='right'>
								<fmt:formatNumber pattern='##.###%'
									value='${sCrt.investmentReturn }' />
							<td>
								&nbsp;
							</td>
							<td>
								Donor(s)
							</td>
							<td align='right'>
								${userInfo.firstName }
							</td>
						</tr>
						<tr>
							<td>
								Spending/Payout Rate(1)
							</td>
							<td align='right'>
								<fmt:formatNumber pattern='##.###%' value='${sCrt.payoutRate }' />
							</td>
							<td>
								&nbsp;
							</td>
							<td>
								Life Expectancy
							</td>
							<td align='right'>
								(
								<fmt:formatNumber pattern='###' value='${sCrt.clientLe }' />
								)&nbsp; (
								<fmt:formatNumber pattern='###' value='${sCrt.spouseLe }' />
								)
							</td>
						</tr>
						<tr>
							<td>
								Capital Gains Tax
							</td>
							<td align='right'>
								<fmt:formatNumber pattern='###,###,###'
									value='${sCrt.capitalGains }' />
							</td>
							<td>
								&nbsp;
							</td>
							<td>
								Insurance Death Benefit
							</td>
							<td align='right'>
								<fmt:formatNumber pattern='###,###,###'
									value='${sCrt.insuranceBenefit }' />
							</td>
						</tr>
						<tr>
							<td>
								UniTrust Lag in Months
							</td>
							<td align='right'>
								<fmt:formatNumber pattern='###' value='${sCrt.uniLag }' />
							</td>
							<td>
								&nbsp;
							</td>
							<td>
								Payment Frequency
							</td>
							<td align='right'>
								<c:if test='${sCrt.pmtPeriod == 1 }'>Annual</c:if>
								<c:if test='${sCrt.pmtPeriod == 2 }'>Semi Annual</c:if>
								<c:if test='${sCrt.pmtPeriod == 4 }'>Quarterly</c:if>
								<c:if test='${sCrt.pmtPeriod == 12 }'>Monthly</c:if>
							</td>
						</tr>
					</table>
					<table cellpadding='2' rules='groups' align='center'>
						<tr>
							<td align='center' colspan='5'>
								<span class='header-bold'>Outright Sale</span>
							</td>
							<td align='center' colspan='7'>
								<span class='header-bold'>Sale With Charitable Remainder
									Trust</span>
							</td>
						</tr>
						<colgroup span='5'>
							<col align='center' valign='top'/>
							<col span='4' align='right' />
						</colgroup>
						<colgroup span='7'>
							<col align='center' valign='top'/>
							<col span='6' align='right' />
						</colgroup>
						<tr>
							<td>
								<span class='wpt-bold'>Year</span>
							</td>
							<td>
								<span class='wpt-bold'>Investment Return</span>
							</td>
							<td>
								<span class='wpt-bold'>Income Tax</span>
							</td>
							<td>
								<span class='wpt-bold'>Net Spendable</span>
							</td>
							<td>
								<span class='wpt-bold'>Portfolio Balance</span>
							</td>
							<td>
								<span class='wpt-bold'>Year</span>
							</td>
							<td>
								<span class='wpt-bold'>Annual Payout</span>
							</td>
							<td>
								<span class='wpt-bold'>Charitable Deduction</span>
							</td>
							<td>
								<span class='wpt-bold'>Insurance Premium</span>
							</td>
							<td>
								<span class='wpt-bold'>Income tax</span>
							</td>
							<td>
								<span class='wpt-bold'>Net Spendable</span>
							</td>
							<td>
								<span class='wpt-bold'>Trust Balance</span>
							</td>
						</tr>
						<%
							long idx = 0;
						%>
						<c:forEach var='cnt' begin="0" end="${sCrt.finalDeath-1 }">
							<%
								if (Math.round(sCrt.getClientLe()) == idx
											|| Math.round(sCrt.getSpouseLe()) == idx) {
							%>
							<tr class='bg-color4'>
								<%
									} else {
								%>
							
							<tr>
								<%
									}
										idx++;
								%>
								<td>
									${cnt}
								</td>
								<td align='right'>
									<fmt:formatNumber pattern='###,###,###'
										value='${sCrt.noCrt[cnt][0]}' />
								</td>
								<td align='right'>
									<fmt:formatNumber pattern='###,###,###'
										value='${sCrt.noCrt[cnt][1]}' />
								</td>
								<td align='right'>
									<fmt:formatNumber pattern='###,###,###'
										value='${sCrt.noCrt[cnt][2]}' />
								</td>
								<td align='right'>
									<fmt:formatNumber pattern='###,###,###'
										value='${sCrt.noCrt[cnt][3]}' />
								</td>
								<td>
									${cnt}
								</td>
								<td align='right'>
									<fmt:formatNumber pattern='###,###,###'
										value='${sCrt.crt[cnt][0]}' />
								</td>
								<td align='right'>
									<fmt:formatNumber pattern='###,###,###'
										value='${sCrt.crt[cnt][1]}' />
								</td>
								<td align='right'>
									<fmt:formatNumber pattern='###,###,###'
										value='${sCrt.crt[cnt][2]}' />
								</td>
								<c:choose>
									<c:when test="${sCrt.crt[cnt][3] < 0 }">
										<td align='right'>
											<span class='text-red'><fmt:formatNumber
													pattern='(###,###,###)' value='${-sCrt.crt[cnt][3]}' />
											</span>
										</td>
									</c:when>
									<c:otherwise>
										<td align='right'>
											<fmt:formatNumber pattern='###,###,###'
												value='${sCrt.crt[cnt][3]}' />
										</td>
									</c:otherwise>
								</c:choose>
								<td align='right'>
									<fmt:formatNumber pattern='###,###,###'
										value='${sCrt.crt[cnt][4]}' />
								</td>
								<td align='right'>
									<fmt:formatNumber pattern='###,###,###'
										value='${sCrt.crt[cnt][5]}' />
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
