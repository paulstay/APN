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

		<title>CRT Summary</title>

		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<link rel="stylesheet" type="text/css" href="<%=path%>/css/styles.css"
			media="screen" />
	</head>
	<jsp:useBean id='crt' scope='session' type='com.teag.estate.CrtTool' />
	<body>
		<%@include file="/inc/aesHeader.jspf"%>
		<table border='0' width='100%'>
			<tr>
				<td width='15%' valign='top'>
					<%@ include file="menu.jspf"%>
				</td>
				<td valign='top' align='right' width='65%'>
					<table align='center' width='100%' border='0'>
						<tr>
							<td colspan='7' align='center'>
								<span class='header-bold'>Charitable Remainder Trust
									(CRT)</span>
						</tr>
						<tr>
							<td colspan='7' align='center'>
								<span class='header-bold'>Assumptions</span>
							</td>
						</tr>
						<tr>
							<td width='5%'>
								&nbsp;
							</td>
							<td width='30%' align='left'>
								Property Value
							</td>
							<td width='10%' align='right'>
								<fmt:formatNumber pattern="###,###,###"
									value="${crt.assetValue}" />
							</td>
							<td width='5%'>
								&nbsp;
							</td>
							<td width='30%' align='left'>
								Date Calculated
							</td>
							<td width='10%' align='right'>
								<fmt:formatDate pattern="M/dd/yyyy" value="${crt.irsDate}" />
							</td>
							<td width='10%'>
								&nbsp;
							</td>
						</tr>
						<tr>
							<td width='5%'>
								&nbsp;
							</td>
							<td width='30%' align='left'>
								Liability
							</td>
							<td width='10%' align='right'>
								<fmt:formatNumber pattern="###,###,###"
									value='${crt.assetLiability}' />
							</td>
							<td width='5%'>
								&nbsp;
							</td>
							<td width='30%' align='left'>
								IRC Sec. 7520(b) rate used
							</td>
							<td width='10%' align='right'>
								<fmt:formatNumber pattern="##.####%" value="${crt.irsRate}" />
							</td>
							<td width='10%'>
								&nbsp;
							</td>
						</tr>
						<tr>
							<td width='5%'>
								&nbsp;
							</td>
							<td width='30%' align='left'>
								Basis
							</td>
							<td width='10%' align='right'>
								<fmt:formatNumber pattern="###,###,###"
									value="${crt.assetBasis}" />
							</td>
							<td width='5%'>
								&nbsp;
							</td>
							<td width='30%' align='left'>
								Charitable Deduction Factor
							</td>
							<td width='10%' align='right'>
								<fmt:formatNumber pattern="##.####"
									value="${crt.charitableDeductionFactor}" />
							</td>
							<td width='10%'>
								&nbsp;
							</td>
						</tr>
						<tr>
							<td width='5%'>
								&nbsp;
							</td>
							<td width='30%' align='left'>
								Marginal Income Tax Rate (Fed and State)
							</td>
							<td width='10%' align='right'>
								<fmt:formatNumber pattern="##.####%" value="${crt.taxRate}" />
							</td>
							<td width='5%'>
								&nbsp;
							</td>
							<td width='30%' align='left'>
								Charitable Deduction
							</td>
							<td width='10%' align='right'>
								<fmt:formatNumber pattern="###,###,###"
									value="${crt.charitableDeduction}" />
							</td>
							<td width='10%'>
								&nbsp;
							</td>
						</tr>
						<tr>
							<td width='5%'>
								&nbsp;
							</td>
							<td width='30%' align='left'>
								Future Estate tax Rate
							</td>
							<td width='10%' align='right'>
								<fmt:formatNumber pattern="##.####%"
									value="${crt.estateTaxRate}" />
							</td>
							<td width='5%'>
								&nbsp;
							</td>
							<td width='30%' align='left'>
								Adjusted Gross Income before payout
							</td>
							<td width='10%' align='right'>
								<fmt:formatNumber pattern="###,###,###"
									value="${crt.adjustedGrossIncome}" />
							</td>
							<td width='10%'>
								&nbsp;
							</td>
						</tr>
						<tr>
							<td width='5%'>
								&nbsp;
							</td>
							<td width='30%' align='left'>
								Capital Gains Rate (Fed and State)
							</td>
							<td width='10%' align='right'>
								<fmt:formatNumber pattern="##.##%"
									value="${crt.capitalGainsRate}" />
							</td>
							<td width='5%'>
								&nbsp;
							</td>
							<td width='30%' align='left'>
								Deduction Limitation of AGI
							</td>
							<td width='10%' align='right'>
								<fmt:formatNumber pattern="##%" value="${crt.agiLimitation}" />
							</td>
							<td width='10%'>
								&nbsp;
							</td>
						</tr>
						<tr>
							<td width='5%'>
								&nbsp;
							</td>
							<td width='30%' align='left'>
								Investment Return
							</td>
							<td width='10%' align='right'>
								<fmt:formatNumber pattern="##.###%"
									value="${crt.investmentReturn}" />
							</td>
							<td width='5%'>
								&nbsp;
							</td>
							<td width='30%' align='left'>
								Donors Ages (${epg.clientFirstName} , ${epg.spouseFirstName})
							</td>
							<td width='10%' align='right'>
								${crt.clientAge} ${crt.spouseAge}
							</td>
							<td width='10%'>
								&nbsp;
							</td>
						</tr>
						<tr>
							<td width='5%'>
								&nbsp;
							</td>
							<td width='30%' align='left'>
								Spending Payout Rate
							</td>
							<td width='10%' align='right'>
								<fmt:formatNumber pattern="##.####%" value="${crt.payoutRate}" />
							</td>
							<td width='5%'>
								&nbsp;
							</td>
							<td width='30%' align='left'>
								Life Expectancy (Averages per tables)
							</td>
							<td width='10%' align='right'>
								${epg.clientLifeExp} ${epg.spouseLifeExp }
							</td>
							<td width='10%'>
								&nbsp;
							</td>
						</tr>
						<tr>
							<td width='5%'>
								&nbsp;
							</td>
							<td width='30%' align='left'>
								Capital Gains Tax
							</td>
							<td width='10%' align='right'>
								<fmt:formatNumber pattern="###,###,###"
									value="${(crt.assetValue - tool.assetBasis) * crt.capitalGainsRate }" />
							</td>
							<td width='5%'>
								&nbsp;
							</td>
							<td width='30%' align='left'>
								Insurance
							</td>
							<td width='10%' align='right'>
								${crt.lifeDeathBenefit}
							</td>
							<td width='10%'>
								&nbsp;
							</td>
						</tr>
					</table>
					<br>
					<Table cellpadding='2' rules='groups' align='center'>
						<tr>
							<td align='center' colspan='5'>
								<span class='header-bold'>Outright Sale</span>
							</td>
							<td align='center' colspan='7'>
								<span class='header-bold'>Sale With Charitable Remainder Trust (CRT)</span>
							</td>
						</tr>
						<colgroup span='5'>
							<col align='center' valign='top' />
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
						<c:forEach var='i' begin='0' end='${tableLength}'>
							<c:choose>
								<c:when test="${i == cle}">
									<c:set var="style" value="bg-color6"/>
								</c:when>
								<c:when test="${i == sle}">
									<c:set var="style" value="bg-color6"/>
								</c:when>
								<c:otherwise>
									<c:set var="style" value="text-blue"/>
								</c:otherwise>
							</c:choose>
							<tr class="${style}">
								<td align='center'>
									${i}
								</td>
								<td align='right'>
									<fmt:formatNumber pattern='$###,###,###'
										value="${crt.outRightSale[i][0] }" />
								</td>
								<td align='right'>
									<fmt:formatNumber pattern='$###,###,###'
										value="${crt.outRightSale[i][1] }" />
								</td>
								<td align='right'>
									<fmt:formatNumber pattern='$###,###,###'
										value="${crt.outRightSale[i][2] }" />
								</td>
								<td align='right'>
									<fmt:formatNumber pattern='$###,###,###'
										value="${crt.outRightSale[i][3] }" />
								</td>
								<td align='center'>
									${i}
								</td>
								<td align='right'>
									<fmt:formatNumber pattern='$###,###,###'
										value="${crt.crtTable[i][0] }" />
								</td>
								<td align='right'>
									<fmt:formatNumber pattern='$###,###,###'
										value="${crt.crtTable[i][1] }" />
								</td>
								<td align='right'>
									<fmt:formatNumber pattern='$###,###,###'
										value="${crt.crtTable[i][2] }" />
								</td>
								<c:choose>
									<c:when test="${crt.crtTable[i][3] lt 0 }">
										<td align='right'><span class='text-red'>
											(<fmt:formatNumber pattern='$###,###,###'
												value="${-1 * crt.crtTable[i][3] }" />)</span>
										</td>
									</c:when>
									<c:otherwise>
										<td align='right'>
											<fmt:formatNumber pattern='$###,###,###'
												value="${crt.crtTable[i][3] }" />
										</td>
									</c:otherwise>
								</c:choose>
								<td align='right'>
									<fmt:formatNumber pattern='$###,###,###'
										value="${crt.crtTable[i][4] }" />
								</td>
								<td align='right'>
									<fmt:formatNumber pattern='$###,###,###'
										value="${crt.crtTable[i][5] }" />
								</td>
							</tr>
						</c:forEach>
					</Table>
				</td>
			</tr>
		</table>
		<%@include file="/inc/aesFooter.jspf"%>
	</body>
</html>
