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

		<title>GRAT Summary</title>

		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<link rel="stylesheet" type="text/css" href="${path}/css/styles.css"
			media="screen" />

	</head>
	<jsp:useBean id='grat' scope='session' type='com.teag.estate.GratTool' />
	<jsp:useBean id='gsv' scope='session'
		type='com.estate.view.GratSummaryView' />
	<jsp:useBean id='cGrat' scope='session'
		type='com.teag.estate.GratTrust' />
	<c:if test="${grat.numTrusts>1 }">
		<jsp:useBean id='sGrat' scope='session'
			type='com.teag.estate.GratTrust' />
	</c:if>
	<body>
		<%@ include file="/inc/aesHeader.jspf"%>
		<h3 align='center'>
			Grantor Retained Annuity Trust
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
					<table>
						<tr>
							<td colspan='5'>
								<h4 align='center'>
									Grat Assumptions
								</h4>
							</td>
							<td>
								&nbsp;
							</td>
						</tr>
						<tr>
							<td>
								&nbsp;
							</td>
							<td>
								&nbsp;
							</td>
							<td>
								&nbsp;
							</td>
							<td>
								&nbsp;
							</td>
						</tr>
						<tr>
							<td>
								&nbsp;
							</td>
							<td>
								Keep in Estate
								<fmt:formatNumber pattern="###" value="${grat.finalDeath }" />
								years
							</td>
							<td>
								&nbsp;
							</td>
							<td>
								GRAT(s)
							</td>
							<td>
								&nbsp;
							</td>
							<td>
								Difference
							</td>
						</tr>
						<tr>
							<td>
								Total Value
							</td>
							<td>
								<fmt:formatNumber pattern="###,###,###"
									value="${gsv.estateTotalValue}" />
							</td>
							<td>
								&nbsp;
							</td>
							<td>
								<fmt:formatNumber pattern="###,###,###"
									value="${gsv.gratTotalValue}" />
							</td>
							<td>
								&nbsp;
							</td>
							<td>
								<fmt:formatNumber pattern="###,###,###"
									value="${gsv.estateTotalValue - gsv.gratTotalValue}" />
							</td>
						</tr>
						<tr>
							<td>
								Estate/Gift Taxable
							</td>
							<td>
								<fmt:formatNumber pattern="###,###,###"
									value="${gsv.estateTotalValue}" />
							</td>
							<td>
								&nbsp;
							</td>
							<td>
								<fmt:formatNumber pattern="###,###,###"
									value="${gsv.gratTaxableGift}" />
							</td>
							<td>
								&nbsp;
							</td>
							<td>
								<fmt:formatNumber pattern="###,###,###"
									value="${gsv.estateTotalValue - gsv.gratTaxableGift}" />
							</td>
						</tr>
						<tr>
							<td>
								Total Estate/Gift Taxes
							</td>
							<td>
								<fmt:formatNumber pattern="###,###,###" value="${gsv.estateFet}" />
							</td>
							<td>
								&nbsp;
							</td>
							<td>
								<fmt:formatNumber pattern="###,###,###"
									value="${gsv.gratEstateTax}" />
								*
							</td>
							<td>
								&nbsp;
							</td>
							<td>
								<fmt:formatNumber pattern="###,###,###" value="${gsv.estateFet}" />
							</td>
						</tr>
						<tr>
							<td>
								Total To Family
							</td>
							<td>
								<fmt:formatNumber pattern="###,###,###"
									value="${gsv.estateTotalValue - gsv.estateFet}" />
							</td>
							<td>
								&nbsp;
							</td>
							<td>
								<fmt:formatNumber pattern="###,###,###"
									value="${gsv.gratTotalValue - gsv.gratEstateTax}" />
							</td>
							<td>
								&nbsp;
							</td>
							<td>
								<fmt:formatNumber pattern="###,###,###"
									value="<%=Math
							.abs((gsv.getEstateTotalValue() - gsv
									.getEstateFet())
									- (gsv.getGratTotalValue() - gsv
											.getGratEstateTax()))%>" />
							</td>
						</tr>
						<tr>
							<td>
								&nbsp;
							</td>
						</tr>
					</table>
					<table>
						<tr>
							<td>
								<table>
									<tr>
										<td colspan='3' align='center'>
											GRAT for
											<%=epg.getClientFirstName()%>
										</td>
									</tr>
									<tr>
										<td colspan='3'>
											&nbsp;
										</td>
									</tr>
									<tr>
										<td><%=epg.getClientFirstName()%>'s Age
										</td>
										<td>
											&nbsp;
										</td>
										<td align='right'>
											<fmt:formatNumber pattern='###'
												value='<%=epg.getClientAge()%>' />
										</td>
									</tr>
									<tr class='text-blue'>
										<td>
											Transfer Date
										</td>
										<td>
											&nbsp;
										</td>
										<td align='right'>
											${grat.afrDate}
										</td>
									</tr>
									<tr class='text-blue'>
										<td>
											Section 7520 Rate
										</td>
										<td>
											&nbsp;
										</td>
										<td align='right'>
											<fmt:formatNumber pattern='##.###%' value='${cGrat.irsRate}' />
										</td>
									</tr>
									<tr class='text-blue'>
										<td>
											Term
										</td>
										<td>
											&nbsp;
										</td>
										<td align='right'>
											<fmt:formatNumber pattern='###' value='${cGrat.term }' />
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
											<fmt:formatNumber pattern='###,###,###' value='${cGrat.fmv}' />
										</td>
									</tr>
									<tr>
										<td>
											Discount Rate
										</td>
										<td>
											&nbsp;
										</td>
										<td align='right'>
											<fmt:formatNumber pattern='##.###%'
												value='${1 - (cGrat.discount/cGrat.fmv) }' />
										</td>
									</tr>
									<tr>
										<td>
											Discounted Principal
										</td>
										<td>
											&nbsp;
										</td>
										<td align='right'>
											<fmt:formatNumber pattern='###,###,###'
												value='${cGrat.discount}' />
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
											<fmt:formatNumber pattern='##.#####'
												value='${cGrat.annuityFactor}' />
										</td>
									</tr>
									<tr>
										<td>
											Optimal Payment Rate
										</td>
										<td>
											&nbsp;
										</td>
										<td align='right'>
											<fmt:formatNumber pattern='##.####%'
												value='${cGrat.optimalPaymentRate}' />
										</td>
									</tr>
									<tr>
										<td>
											Actual Payment Rate
										</td>
										<td>
											&nbsp;
										</td>
										<td align='right'>
											<fmt:formatNumber pattern='##.####%'
												value='${cGrat.actualPaymentRate}' />
										</td>
									</tr>
									<tr class='text-blue'>
										<td>
											Annuity Payment
										</td>
										<td>
											&nbsp;
										</td>
										<td align='right'>
											<fmt:formatNumber pattern='###,###,###'
												value='${cGrat.annuityPayment }' />
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
											<fmt:formatNumber pattern='###,###,###'
												value='${cGrat.annuityInterest}' />
										</td>
									</tr>
									<tr class='text-bold'>
										<td>
											Remainder Interest
										</td>
										<td>
											&nbsp;
										</td>
										<td align='right'>
											<fmt:formatNumber pattern='###,###,###'
												value='${cGrat.remainderInterest }' />
										</td>
									</tr>
								</table>
							</td>
							<c:if test="${grat.numTrusts > 1 }">
								<td width='15%'>
									&nbsp;&nbsp;&nbsp;&nbsp;
								</td>
								<td>
									<table>
										<tr>
											<td colspan='3' align='center'>
												GRAT for
												<%=epg.getSpouseFirstName()%>
											</td>
										</tr>
										<tr>
											<td colspan='3'>
												&nbsp;
											</td>
										</tr>
										<tr>
											<td><%=epg.getSpouseFirstName()%>'s Age
											</td>
											<td>
												&nbsp;
											</td>
											<td align='right'>
												<fmt:formatNumber pattern='###'
													value='<%=epg.getSpouseAge()%>' />
											</td>
										</tr>
										<tr class='text-blue'>
											<td>
												Transfer Date
											</td>
											<td>
												&nbsp;
											</td>
											<td align='right'>
												${grat.afrDate }
											</td>
										</tr>
										<tr class='text-blue'>
											<td>
												Section 7520 Rate
											</td>
											<td>
												&nbsp;
											</td>
											<td align='right'>
												<fmt:formatNumber pattern='##.###%' value='${sGrat.irsRate}' />
											</td>
										</tr>
										<tr class='text-blue'>
											<td>
												Term
											</td>
											<td>
												&nbsp;
											</td>
											<td align='right'>
												<fmt:formatNumber pattern='###' value='${sGrat.term }' />
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
												<fmt:formatNumber pattern='###,###,###' value='${sGrat.fmv}' />
											</td>
										</tr>
										<tr>
											<td>
												Discount Rate
											</td>
											<td>
												&nbsp;
											</td>
											<td align='right'>
												<fmt:formatNumber pattern='##.###%'
													value='${1 - (sGrat.discount/sGrat.fmv) }' />
											</td>
										</tr>
										<tr>
											<td>
												Discounted Principal
											</td>
											<td>
												&nbsp;
											</td>
											<td align='right'>
												<fmt:formatNumber pattern='###,###,###'
													value='${sGrat.discount}' />
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
												<fmt:formatNumber pattern='##.#####'
													value='${sGrat.annuityFactor}' />
											</td>
										</tr>
										<tr>
											<td>
												Optimal Payment Rate
											</td>
											<td>
												&nbsp;
											</td>
											<td align='right'>
												<fmt:formatNumber pattern='##.####%'
													value='${sGrat.optimalPaymentRate}' />
											</td>
										</tr>
										<tr>
											<td>
												Actual Payment Rate
											</td>
											<td>
												&nbsp;
											</td>
											<td align='right'>
												<fmt:formatNumber pattern='##.####%'
													value='${sGrat.actualPaymentRate}' />
											</td>
										</tr>
										<tr class='text-blue'>
											<td>
												Annuity Payment
											</td>
											<td>
												&nbsp;
											</td>
											<td align='right'>
												<fmt:formatNumber pattern='###,###,###'
													value='${sGrat.annuityPayment }' />
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
												<fmt:formatNumber pattern='###,###,###'
													value='${sGrat.annuityInterest}' />
											</td>
										</tr>
										<tr class='text-bold'>
											<td>
												Remainder Interest
											</td>
											<td>
												&nbsp;
											</td>
											<td align='right'>
												<fmt:formatNumber pattern='###,###,###'
													value='${sGrat.remainderInterest }' />
											</td>
										</tr>
									</table>
								</td>
							</c:if>
						<tr>
							<td>
								&nbsp;
							</td>
							<td>
								&nbsp;
							</td>
							<td>
								&nbsp;
							</td>
						</tr>
						<tr>
							<td colspan='3' align='center'>
								Internal Grat Value for Calculating Insurance
							</td>
						</tr>
						<tr>
							<td colspan='3' align='center'>
								<table>
									<tr>
										<td width='20%' align='center'>
											TERM
										</td>
										<td width='20%' align='center'>
											Grat Value
										</td>
										<td width='20%' align='center'>
											Estate Tax
										</td>
									</tr>
									<c:forEach var='i' begin='0' end='${grat.clientTermLength -1 }'>
										<tr>
											<td align='center'>
												${i }
											</td>
											<td align='right'>
												<fmt:formatNumber pattern='###,###,###'
													value='${grat.cfTotalValue[i]}' />
											</td>
											<td align='right'>
												<fmt:formatNumber pattern='###,###,###'
													value='${grat.cfTotalValue[i]*grat.estateTaxRate}' />
											</td>
										</tr>
									</c:forEach>
								</table>
							</td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
		<%@ include file="/inc/aesFooter.jspf"%>
	</body>
</html>
