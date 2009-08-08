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
			class='com.teag.bean.PdfBean' />
		<jsp:useBean id='crt' scope='session' class='com.estate.toolbox.CRT' />
		<%
			crt.setClientAge(userInfo.getAge());
			crt.calculate();
		%>
		<table>
			<tr>
				<td valign='top'><%@include file='menu.jsp'%></td>
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
								<fmt:formatNumber pattern='###,###,###' value='${crt.fmv }' />
							</td>
							<td>
								&nbsp;
							</td>
							<td>
								Date Calculated
							</td>
							<td align='right'>
								<fmt:formatDate pattern='M/d/yyyy' value='${crt.irsDate }' />
							</td>
						</tr>
						<tr>
							<td>
								Liability
							</td>
							<td align='right'>
								<fmt:formatNumber pattern='###,###,###'
									value='${crt.liability }' />
							</td>
							<td>
								&nbsp;
							</td>
							<td>
								IRC Sec. 7520 rate used
							</td>
							<td align='right'>
								<fmt:formatNumber pattern='##.##%' value='${crt.irsRate }' />
							</td>
						</tr>
						<tr>
							<td>
								Basis
							</td>
							<td align='right'>
								<fmt:formatNumber pattern='###,###,###' value='${crt.basis }' />
							</td>
							<td>
								&nbsp;
							</td>
							<td>
								Charitable Deduction Factor
							</td>
							<td align='right'>
								<fmt:formatNumber pattern='##.#####'
									value='${crt.charitableDeductionFactor }' />
							</td>
						</tr>
						<tr>
							<td>
								Marginal Income Tax Rate (fed and State)
							</td>
							<td align='right'>
								<fmt:formatNumber pattern='##.###%'
									value='${crt.marginalTaxRate }' />
							</td>
							<td>
								&nbsp;
							</td>
							<td>
								Charitable Deduction
							</td>
							<td align='right'>
								<fmt:formatNumber pattern='###,###,###'
									value='${crt.charitableDeduction }' />
							</td>
						</tr>
						<tr>
							<td>
								Future Estate Tax Rate
							</td>
							<td align='right'>
								<fmt:formatNumber pattern='##.##%' value='${crt.estateTaxRate }' />
							</td>
							<td>
								&nbsp;
							</td>
							<td>
								Adjusted Gross Income (AGI) before payout
							</td>
							<td align='right'>
								<fmt:formatNumber pattern='###,###,###' value='${crt.agi }' />
							</td>
						</tr>
						<tr>
							<td>
								Capital Gains Tax Rate (Fed and State)
							</td>
							<td align='right'>
								<fmt:formatNumber pattern='##.###%'
									value='${crt.capitalGainsTax }' />
							</td>
							<td>
								&nbsp;
							</td>
							<td>
								Deduction Limitation of AGI
							</td>
							<td align='right'>
								<fmt:formatNumber pattern='##.###%'
									value='${crt.agiLimitation }' />
							</td>
						</tr>
						<tr>
							<td>
								Investment Return
							</td>
							<td align='right'>
								<fmt:formatNumber pattern='##.###%'
									value='${crt.investmentReturn }' />
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
								<fmt:formatNumber pattern='##.###%' value='${crt.payoutRate }' />
							</td>
							<td>
								&nbsp;
							</td>
							<td>
								Life Expectancy
							</td>
							<td align='right'>
								<fmt:formatNumber pattern='###' value='${crt.clientLe }' />
							</td>
						</tr>
						<tr>
							<td>
								Capital Gains Tax
							</td>
							<td align='right'>
								<fmt:formatNumber pattern='###,###,###'
									value='${crt.capitalGains }' />
							</td>
							<td>
								&nbsp;
							</td>
							<td>
								Insurance Death Benefit
							</td>
							<td align='right'>
								<fmt:formatNumber pattern='###,###,###'
									value='${crt.insuranceBenefit }' />
							</td>
						</tr>
					</table>
					<table cellpadding='2' rules='groups' align='center'>
						<tr>
							<td align='center' colspan='5'>
								<span class='header-bold'>Outright Sale</span>
							</td>
							<td align='center' colspan='7'>
								<span class='header-bold'>Sale With Wealth Preservation
									Trust (WPT)</span>
							</td>
						</tr>
						<colgroup span='5'>
							<col align='center' valign='top' />
							<col span='4' align='right' />
						</colgroup>
						<colgroup span='7'>
							<col align='center' valign='top' />
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
						<c:forEach var='cnt' begin="0" end="${crt.finalDeath-1 }">
							<%
								if (Math.round(crt.getClientLe()) == idx) {
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
										value='${crt.noCrt[cnt][0]}' />
								</td>
								<td align='right'>
									<fmt:formatNumber pattern='###,###,###'
										value='${crt.noCrt[cnt][1]}' />
								</td>
								<td align='right'>
									<fmt:formatNumber pattern='###,###,###'
										value='${crt.noCrt[cnt][2]}' />
								</td>
								<td align='right'>
									<fmt:formatNumber pattern='###,###,###'
										value='${crt.noCrt[cnt][3]}' />
								</td>
								<td>
									${cnt}
								</td>
								<td align='right'>
									<fmt:formatNumber pattern='###,###,###'
										value='${crt.crt[cnt][0]}' />
								</td>
								<td align='right'>
									<fmt:formatNumber pattern='###,###,###'
										value='${crt.crt[cnt][1]}' />
								</td>
								<td align='right'>
									<fmt:formatNumber pattern='###,###,###'
										value='${crt.crt[cnt][2]}' />
								</td>
								<c:choose>
									<c:when test="${crt.crt[cnt][3] < 0 }">
										<td align='right'>
											<span class='text-red'><fmt:formatNumber
													pattern='(###,###,###)' value='${-crt.crt[cnt][3]}' />
											</span>
										</td>
									</c:when>
									<c:otherwise>
										<td align='right'>
											<fmt:formatNumber pattern='###,###,###'
												value='${crt.crt[cnt][3]}' />
										</td>
									</c:otherwise>
								</c:choose>
								<td align='right'>
									<fmt:formatNumber pattern='###,###,###'
										value='${crt.crt[cnt][4]}' />
								</td>
								<td align='right'>
									<fmt:formatNumber pattern='###,###,###'
										value='${crt.crt[cnt][5]}' />
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
