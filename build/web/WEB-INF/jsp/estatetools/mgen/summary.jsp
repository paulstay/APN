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

		<title>Multi Generational Trust Summary</title>

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
	<jsp:useBean id='mgen' scope='session'
		type='com.teag.estate.MGTrustTool' />
	<body>
		<%@include file="/inc/aesHeader.jspf"%>
		<table border='0' width='100%'>
			<tr>
				<td width='15%' valign='top'>
					<%@ include file="menu.jspf"%>
				</td>
				<td valign='top' align='left' width='65%'>
					<table>
						<tr>
							<td>
								<table frame='box' align='left' width='80%'>
									<tr>
										<td colspan='4' align='center'>
											<span class='header-bold'>Multi-Generational Trust
												Tool</span>
									</tr>
									<tr>
										<td colspan='4' align='center'>
											<span class='header-bold'>Assumptions</span>
										</td>
									</tr>
									<tr>
										<td align='left'>
											Current Trust Assets Value
										</td>
										<td align='right'>
											<fmt:formatNumber pattern="###,###,###"
												value="${mgen.totalValue}" />
										</td>
										<td>
											&nbsp;&nbsp;
										</td>
										<td align='left'>
											Annual Income Rate (Gross)
										</td>
										<td align='right'>
											<fmt:formatNumber pattern="##.##%" value="${.02}" />
										</td>
									</tr>
									<tr>
										<td align='left'>
											Years per Generation
										</td>
										<td align='right'>
											<fmt:formatNumber pattern="###"
												value="${mgen.yearsPerGeneration}" />
										</td>
										<td>
											&nbsp;&nbsp;
										</td>
										<td align='left'>
											Annual Growth Rate (Gross)
										</td>
										<td align='right'>
											<fmt:formatNumber pattern="##.##%" value="${mgen.growth}" />
										</td>
									</tr>
									<tr>
										<td align='left'>
											Estate Tax Rate
										</td>
										<td align='right'>
											<fmt:formatNumber pattern="##.##%" value="${.55}" />
										</td>
										<td>
											&nbsp;&nbsp;
										</td>
										<td align='left'>
											Annual Payout Rate (2nd-4th generation)
										</td>
										<td align='right'>
											<fmt:formatNumber pattern="##.##%"
												value="${mgen.payoutRate }" />
										</td>
									</tr>
									<tr>
										<td align='left'>
											Inflation Rate
										</td>
										<td align='right'>
											<fmt:formatNumber pattern="##.###%"
												value="${mgen.inflationRate }" />
										</td>
										<td>
											&nbsp;&nbsp;
										</td>
										<td align='left'>
											Trustee Fee
										</td>
										<td align='right'>
											<fmt:formatNumber pattern="##.##%"
												value="${mgen.trusteeRate }" />
										</td>
									</tr>
									<tr>
										<td align='left'>
											${epg.clientFirstName} Life Expectancy
										</td>
										<td align='right'>
											<fmt:formatNumber pattern="###" value="${epg.clientLifeExp}" />
										</td>
										<td>
											&nbsp;&nbsp;
										</td>
										<td align='left'>
											${epg.spouseFirstName} Life Expectancy
										</td>
										<td align='right'>
											<fmt:formatNumber pattern="###" value="${epg.spouseLifeExp}" />
										</td>
									</tr>
								</table>
							</td>
						</tr>
						<tr>
							<td>&nbsp;</td>
						</tr>
						<tr>
							<td>
								<table frame='box' align='left' width='100%'>
									<tr>
										<td width='20%'>
											&nbsp;
										</td>
										<td align='right' width='25%'>
											Normal Estate
										</td>
										<td align='right'>
											Mult-Gen Trust in ${mgen.trustState}
										</td>
										<td align='right' >
											Mult-Gen Trust in ${mgen.mgenTrustState}
										</td>
									</tr>
									<c:forEach var="idx" begin="0" end="3">
										<tr>
											<td colspan='4' align='center' >
												&nbsp;
											</td>
										</tr>
										<tr>
											<td colspan='4' align='center'>
												<span style="font-weight: bold;">${hdrs[idx]}</span>
											</td>
										</tr>
										<tr>
											<td>
												Starting Value
											</td>
											<td align='right' >
												<fmt:formatNumber pattern="###,###,###"
													value="${mgen.keepInEstate[idx][0]}" />
											</td>
											<td align='right'>
												<fmt:formatNumber pattern="###,###,###"
													value="${mgen.currentStateTrust[idx][0]}" />
											</td>
											<td align='right'>
												<fmt:formatNumber pattern="###,###,###"
													value="${mgen.delawareTrust[idx][0]}" />
											</td>
										</tr>
										<tr>
											<td>
												Net Growth of Assets
											</td>
											<td align='right'>
												<fmt:formatNumber pattern="###,###,###"
													value="${mgen.keepInEstate[idx][1] - mgen.keepInEstate[idx][0]}" />
											</td>
											<td align='right'>
												<fmt:formatNumber pattern="###,###,###"
													value="${mgen.currentStateTrust[idx][1]- mgen.currentStateTrust[idx][0]}" />
											</td>
											<td align='right'>
												<fmt:formatNumber pattern="###,###,###"
													value="${mgen.delawareTrust[idx][1] - mgen.delawareTrust[idx][0]}" />
											</td>
										</tr>
										<tr>
											<td>
												Value at Death
											</td>
											<td align='right'>
												<fmt:formatNumber pattern="###,###,###"
													value="${mgen.keepInEstate[idx][1]}" />
											</td>
											<td align='right'>
												<fmt:formatNumber pattern="###,###,###"
													value="${mgen.currentStateTrust[idx][1]}" />
											</td>
											<td align='right'>
												<fmt:formatNumber pattern="###,###,###"
													value="${mgen.delawareTrust[idx][1]}" />
											</td>
										</tr>
										<tr style='color: red;'>
											<td>
												Less Estate Tax
											</td>
											<td align='right'>
												<fmt:formatNumber pattern="###,###,###"
													value="${mgen.keepInEstate[idx][2]}" />
											</td>
											<td align='right'>
												<fmt:formatNumber pattern="###,###,###"
													value="${mgen.currentStateTrust[idx][2]}" />
											</td>
											<td align='right' >
												<fmt:formatNumber pattern="###,###,###"
													value="${mgen.delawareTrust[idx][2]}" />
											</td>
										</tr>
										<tr>
											<td>
												Total Benefit To Family
											</td>
											<td align='right'>
												<fmt:formatNumber pattern="###,###,###"
													value="${mgen.keepInEstate[idx][1] - mgen.keepInEstate[idx][2]}" />
											</td>
											<td align='right'>
												<fmt:formatNumber pattern="###,###,###"
													value="${mgen.currentStateTrust[idx][1]- mgen.currentStateTrust[idx][2]}" />
											</td>
											<td align='right'>
												<fmt:formatNumber pattern="###,###,###"
													value="${mgen.delawareTrust[idx][1] - mgen.delawareTrust[idx][2]}" />
											</td>
										</tr>
										<tr>
											<td>
												Present Value of Amount to Family (
												<fmt:formatNumber pattern="##.#%"
													value="${mgen.inflationRate}" />
												Infl.)
											</td>
											<td align='right'>
												<fmt:formatNumber pattern="###,###,###"
													value="${mgen.keepInEstate[idx][5]}" />
											</td>
											<td align='right'>
												<fmt:formatNumber pattern="###,###,###"
													value="${mgen.currentStateTrust[idx][5]}" />
											</td>
											<td align='right'>
												<fmt:formatNumber pattern="###,###,###"
													value="${mgen.delawareTrust[idx][5]}" />
											</td>
										</tr>
										<tr>
											<td colspan='4'>
												&nbsp;
											</td>
										</tr>
									</c:forEach>
									<tr>
										<td colspan='4' align='center'><span style="font-weight:bold;">Comparison of Principal Accounts</span></td>
									</tr>
									<tr>
										<td>Unadjusted Balance</td>
											<td align='right'>
												<fmt:formatNumber pattern="###,###,###"
													value="${mgen.keepInEstate[3][1] - mgen.keepInEstate[3][2]}" />
											</td>
											<td align='right'>
												<fmt:formatNumber pattern="###,###,###"
													value="${mgen.currentStateTrust[3][1]- mgen.currentStateTrust[3][2]}" />
											</td>
											<td align='right'>
												<fmt:formatNumber pattern="###,###,###"
													value="${mgen.delawareTrust[3][1] - mgen.delawareTrust[3][2]}" />
											</td>
									</tr>
									<tr>
										<td>Inflation Adjusted</td>
											<td align='right'>
												<fmt:formatNumber pattern="###,###,###"
													value="${mgen.keepInEstate[3][5]}" />
											</td>
											<td align='right'>
												<fmt:formatNumber pattern="###,###,###"
													value="${mgen.currentStateTrust[3][5]}" />
											</td>
											<td align='right'>
												<fmt:formatNumber pattern="###,###,###"
													value="${mgen.delawareTrust[3][5]}" />
											</td>
									</tr>
									<tr>
										<td>Effective Index</td>
										<td align='right'><fmt:formatNumber pattern="##" value="${lev1}"/> : 1</td>
										<td align='right'><fmt:formatNumber pattern="###.##" value="${lev2}"/> : 1</td>
										<td align='right'><fmt:formatNumber pattern="###.##" value="${lev3}"/> : 1</td>
									</tr>
								</table>
							</td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
		<%@include file="/inc/aesFooter.jspf"%>
	</body>
</html>
