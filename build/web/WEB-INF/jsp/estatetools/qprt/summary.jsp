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

		<title>QPRT Summary</title>

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
	<jsp:useBean id='qprt' scope='session' type='com.teag.estate.QprtTool' />
	<jsp:useBean id='cQprt' scope='request'
		type='com.teag.estate.QprtTrustTool' />
	<c:if test="${qprt.numberOfTrusts > 1 }">
		<jsp:useBean id='sQprt' scope='request'
			type='com.teag.estate.QprtTrustTool' />
	</c:if>
	<body>
		<%@include file="/inc/aesHeader.jspf"%>
		<table border='0' width='100%'>
			<tr>
				<td width='15%'>
					<%@ include file="menu.jspf"%>
				</td>
				<td valign='top' align='right' width='65%'>
					<table align='center' width='80%' border='0'>
						<tr>
							<td colspan='4' align='center'>
								<span class='header-bold'>Qualified Personal Residence
									Trust (QPRT)</span>
						</tr>
						<tr>
							<td colspan='4' align='center'>
								<span class='header-bold'>Assumptions</span>
							</td>
						</tr>
						<tr>
							<td>
								&nbsp;
							</td>
							<td align='right' style='border-bottom: 2px groove green;'>
								In Estate (${eYears} Years)
							</td>
							<td align='right' style='border-bottom: 2px groove green;'>
								Direct Gift Today
							</td>
							<td align='right' style='border-bottom: 2px groove green;'>
								Qprt
							</td>
						</tr>
						<tr>
							<td align='left'>
								Value for Estate/Gift Taxes
							</td>
							<td align='right'>
								<fmt:formatNumber pattern="###,###,###" value="${valueOfEstate}" />
							</td>
							<td align='right'>
								<fmt:formatNumber pattern="###,###,###" value="${qprt.value }" />
							</td>
							<td align='right'>
								<fmt:formatNumber pattern="###,###,###" value="${qprtTaxEstate}" />
							</td>
						</tr>
						<tr style='color: red;'>
							<td align='left'>
								Estate/Gift Taxes (
								<fmt:formatNumber type="percent" value="${qprt.estateTaxRate}" />
								)
							</td>
							<td align='right'>
								<fmt:formatNumber pattern="###,###,###"
									value="${valueOfEstate * qprt.estateTaxRate}" />
							</td>
							<td align='right'>
								<fmt:formatNumber pattern="###,###,###"
									value="${qprt.value * qprt.estateTaxRate}" />
							</td>
							<td align='right'>
								<fmt:formatNumber pattern="###,###,###" value="${qprtGiftTax}" />
							</td>
						</tr>
					</table>
					<br>
					<table align='center' width='80%'>
						<tr>
							<td colspan='5' align='center'>
								<h3>
									Trust Details
								</h3>
							</td>
						</tr>
						<tr>
							<td align='left' valign='top' width='25%'>
								${epg.clientFirstName} Age
							</td>
							<td align='right' width='20%'>
								<fmt:formatNumber pattern='###' value='${epg.clientAge}' />
							</td>
							<td>
								&nbsp;
							</td>
							<td align='left' width='25%' valign='top'>
								Prior Gifts
							</td>
							<td align='right' width='25%'>
								<fmt:formatNumber pattern="###,###,###"
									value="${qprt.clientPriorGifts }" />
							</td>
						</tr>
						<tr>
							<td align='left'>
								Transfer Date
							</td>
							<td align='right'>
								${qprt.afrDate }
							</td>
							<td>
								&nbsp;
							</td>
							<td align='left'>
								Beginning Value of Qprt
							</td>
							<td align='right'>
								<fmt:formatNumber pattern="###,###,###" value="${cQprt.value }" />
							</td>
						</tr>
						<tr>
							<td align='left'>
								Irs 7520 Rate
							</td>
							<td align='right'>
								<fmt:formatNumber pattern="##.##%" value="${qprt.afrRate }" />
							</td>
							<td>
								&nbsp;
							</td>
							<td align='left'>
								Discounted Value of Qprt
							</td>
							<td align='right'>
								<fmt:formatNumber pattern="###,###,###"
									value="${cQprt.discountValue }" />
							</td>
						</tr>
						<tr>
							<td align='left'>
								Fractional Discount
							</td>
							<td align='right'>
								<fmt:formatNumber pattern="##.##%"
									value="${qprt.fractionalInterestDiscount }" />
							</td>
							<td>
								&nbsp;
							</td>
							<td align='left'>
								Basis of Residence
							</td>
							<td align='right'>
								<fmt:formatNumber pattern="###,###,###" value="${qprt.basis }" />
							</td>
						</tr>
						<tr>
							<td align='left'>
								Estate Tax Rate
							</td>
							<td align='right'>
								<fmt:formatNumber pattern="##.##%"
									value="${qprt.estateTaxRate }" />
							</td>
							<td>
								&nbsp;
							</td>
							<td align='left'>
								Growth (%) of Residence
							</td>
							<td align='right'>
								<fmt:formatNumber pattern="##.##%" value="${qprt.growth }" />
							</td>
						</tr>
						<tr>
							<td align='left'>
								Revision Retained
							</td>
							<c:choose>
								<c:when test="${qprt.revisionRetained}">
									<td align='right'>
										TRUE
									</td>
								</c:when>
								<c:otherwise>
									<td align='right'>
										False
									</td>
								</c:otherwise>
							</c:choose>
							<td>
								&nbsp;
							</td>
							<td align='left'>
								Rent After Term
							</td>
							<td align='right'>
								<fmt:formatNumber pattern="##.##%" value="${qprt.rentAfterTerm}" />
							</td>
						</tr>
					</table>
			<br>
			<table align='center' border='1'>
				<tr>
					<td>
						<table>
							<tr>
								<td colspan='2' align='center'>
									<b>Trust for ${epg.clientFirstName}</b>
								</td>
							</tr>
							<tr>
								<td align='left'>
									Current Age
								</td>
								<td align='right'>
									${epg.clientAge}
								</td>
							</tr>
							<tr>
								<td align='left'>
									Life Expectancy
								</td>
								<td align='right'>
									<fmt:formatNumber pattern="###" value="${cQprt.lifeExp }" />
							</tr>
							<tr>
								<td align='left'>
									Prior Gifts
								</td>
								<td align='right'>
									<fmt:formatNumber pattern="###,###,###"
										value="${qprt.clientPriorGifts}" />
								</td>
							</tr>
							<tr>
								<td colspan='2'>
									&nbsp;
								</td>
							</tr>
							<tr>
								<td align='left'>
									Income Interest
								</td>
								<td align='right'>
									<fmt:formatNumber pattern="###,###,###"
										value="${cQprt.incomeIntValue}" />
								</td>
							</tr>
							<tr>
								<td align='left'>
									Pre-Term Remainder Interest
								</td>
								<td align='right'>
									<fmt:formatNumber pattern="###,###,###"
										value="${cQprt.reversionaryIntValue}" />
								</td>
							</tr>
							<tr>
								<td align='left'>
									Post-Term Remainder Interest
								</td>
								<td align='right'>
									<fmt:formatNumber pattern="###,###,###"
										value="${cQprt.remainderIntValue}" />
								</td>
							</tr>
							<tr>
								<td colspan='2'>
									&nbsp;
								</td>
							</tr>
							<tr>
								<td colspan='2'>
									&nbsp;
								</td>
							</tr>
							<tr>
								<td align='left'>
									Total Interest
								</td>
								<td align='right'>
									<fmt:formatNumber pattern="###,###,###"
										value="${cQprt.totalIntValue}" />
								</td>
							</tr>
							<tr>
								<td align='left'>
									Retained Interest
								</td>
								<td align='right'>
									<fmt:formatNumber pattern="###,###,###"
										value="${cQprt.retainedIntValue}" />
								</td>
							</tr>
							<tr>
								<td align='left'>
									Taxable Gift
								</td>
								<td align='right'>
									<fmt:formatNumber pattern="###,###,###"
										value="${cQprt.taxableGiftValue}" />
								</td>
							</tr>
							<tr>
								<td colspan='2'>
									&nbsp;
								</td>
							</tr>
							<tr>
								<td colspan='2'>
									&nbsp;
								</td>
							</tr>
							<tr>
								<td align='left'>
									Trust Duration (Term)
								</td>
								<td align='right'>
									<fmt:formatNumber pattern="###" value="${cQprt.termLength}" />
								</td>
							</tr>
							<tr>
								<td align='left'>
									Ending Value of Residence
								</td>
								<td align='right'>
									<fmt:formatNumber pattern="###,###,###"
										value="${cQprt.endResidenceValue}" />
								</td>
							</tr>
							<tr>
								<td align='left'>
									Built-in Gain
								</td>
								<td align='right'>
									<fmt:formatNumber pattern="###,###,###"
										value="${cQprt.builtInGain}" />
								</td>
							</tr>
							<tr>
								<td align='left'>
									Estate Tax Savings
								</td>
								<td align='right'>
									<fmt:formatNumber pattern="###,###,###"
										value="${cQprt.estateTaxSavings}" />
								</td>
							</tr>
							<tr>
								<td align='left'>
									Value of Gift to Heirs
								</td>
								<td align='right'>
									<fmt:formatNumber pattern="###,###,###"
										value="${cQprt.valueGiftToHeirs}" />
								</td>
							</tr>
							<tr>
								<td align='left'>
									Gift Leverage
								</td>
								<td align='right'>
									<fmt:formatNumber pattern="#####" value="${cQprt.giftLeverage}" />
									:1
								</td>
							</tr>
						</table>
					</td>
					<c:if test="${qprt.numberOfTrusts > 1 }">
						<td>&nbsp;&nbsp;&nbsp;</td>
						<td>
							<table>
								<tr>
									<td colspan='2' align='center'>
										<b>Trust for ${epg.spouseFirstName}</b>
									</td>
								</tr>
								<tr>
									<td align='left'>
										Current Age
									</td>
									<td align='right'>
										${epg.spouseAge}
									</td>
								</tr>
								<tr>
									<td align='left'>
										Life Expectancy
									</td>
									<td align='right'>
										<fmt:formatNumber pattern="###" value="${sQprt.lifeExp }" />
								</tr>
								<tr>
									<td align='left'>
										Prior Gifts
									</td>
									<td align='right'>
										<fmt:formatNumber pattern="###,###,###"
											value="${qprt.spousePriorGifts}" />
									</td>
								</tr>
								<tr>
									<td colspan='2'>
										&nbsp;
									</td>
								</tr>
								<tr>
									<td align='left'>
										Income Interest
									</td>
									<td align='right'>
										<fmt:formatNumber pattern="###,###,###"
											value="${sQprt.incomeIntValue}" />
									</td>
								</tr>
								<tr>
									<td align='left'>
										Pre-Term Remainder Interest
									</td>
									<td align='right'>
										<fmt:formatNumber pattern="###,###,###"
											value="${sQprt.reversionaryIntValue}" />
									</td>
								</tr>
								<tr>
									<td align='left'>
										Post-Term Remainder Interest
									</td>
									<td align='right'>
										<fmt:formatNumber pattern="###,###,###"
											value="${sQprt.remainderIntValue}" />
									</td>
								</tr>
								<tr>
									<td colspan='2'>
										&nbsp;
									</td>
								</tr>
								<tr>
									<td colspan='2'>
										&nbsp;
									</td>
								</tr>
								<tr>
									<td align='left'>
										Total Interest
									</td>
									<td align='right'>
										<fmt:formatNumber pattern="###,###,###"
											value="${sQprt.totalIntValue}" />
									</td>
								</tr>
								<tr>
									<td align='left'>
										Retained Interest
									</td>
									<td align='right'>
										<fmt:formatNumber pattern="###,###,###"
											value="${sQprt.retainedIntValue}" />
									</td>
								</tr>
								<tr>
									<td align='left'>
										Taxable Gift
									</td>
									<td align='right'>
										<fmt:formatNumber pattern="###,###,###"
											value="${sQprt.taxableGiftValue}" />
									</td>
								</tr>
								<tr>
									<td colspan='2'>
										&nbsp;
									</td>
								</tr>
								<tr>
									<td colspan='2'>
										&nbsp;
									</td>
								</tr>
								<tr>
									<td align='left'>
										Trust Duration (Term)
									</td>
									<td align='right'>
										<fmt:formatNumber pattern="###" value="${sQprt.termLength}" />
									</td>
								</tr>
								<tr>
									<td align='left'>
										Ending Value of Residence
									</td>
									<td align='right'>
										<fmt:formatNumber pattern="###,###,###"
											value="${sQprt.endResidenceValue}" />
									</td>
								</tr>
								<tr>
									<td align='left'>
										Built-in Gain
									</td>
									<td align='right'>
										<fmt:formatNumber pattern="###,###,###"
											value="${sQprt.builtInGain}" />
									</td>
								</tr>
								<tr>
									<td align='left'>
										Estate Tax Savings
									</td>
									<td align='right'>
										<fmt:formatNumber pattern="###,###,###"
											value="${sQprt.estateTaxSavings}" />
									</td>
								</tr>
								<tr>
									<td align='left'>
										Value of Gift to Heirs
									</td>
									<td align='right'>
										<fmt:formatNumber pattern="###,###,###"
											value="${sQprt.valueGiftToHeirs}" />
									</td>
								</tr>
								<tr>
									<td align='left'>
										Gift Leverage
									</td>
									<td align='right'>
										<fmt:formatNumber pattern="#####"
											value="${sQprt.giftLeverage}" />
										:1
									</td>
								</tr>
							</table>
						</td>
					</c:if>
				</tr>
			</table>
			</td>
			</tr>
		</table>
		<%@include file="/inc/aesFooter.jspf"%>
	</body>
</html>
