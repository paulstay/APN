<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%@ page import="com.estate.constants.*"%>
<%@ page import="com.estate.toolbox.*"%>
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

		<title>Qualified Personal Residence Trust Summary</title>

		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<link rel="stylesheet" type="text/css" href="<%=path%>/css/styles.css"
			media="screen" />
	</head>
	<body>
		<%@include file="/inc/aesHeader.jspf" %>
		<h3 align='center'>
			Qualified Personal Residence Trust
		</h3>
		<h4 align='center'>
			Summary
		</h4>
		<jsp:useBean id='userInfo' scope='session'
			type='com.teag.bean.PdfBean' />
		<jsp:useBean id='qprt' scope='session'
			type='com.estate.controller.QprtController' />
		<%
			qprt.calculate();
			QPRT q1 = qprt.getQ1();
			QPRT q2 = null;
			if (qprt.getTrusts() > 1)
				q2 = qprt.getQ2();

			request.setAttribute("q1", q1);
			request.setAttribute("q2", q2);
		%>
		<table>
			<tr>
				<td valign='top'><%@include file='menu.jspf'%></td>
				<td align='center' width='100%'>
					<table width='60%' frame='box'>
						<tr>
							<td colspan='3' align='center'>
								<h3>
									Assumptions
								</h3>
							</td>
						</tr>
						<tr>
							<td>
								Residence Value
							</td>
							<td>
								&nbsp;
							</td>
							<td align='right'>
								<fmt:formatNumber pattern='###,###,###' value='${qprt.fmv}' />
							</td>
						</tr>
						<tr>
							<td>
								Basis
							</td>
							<td>
								&nbsp;
							</td>
							<td align='right'>
								<fmt:formatNumber pattern='###,###,###' value='${qprt.basis}' />
							</td>
						</tr>
						<tr>
							<td>
								Fractional Discount
							</td>
							<td>
								&nbsp;
							</td>
							<td align='right'>
								<fmt:formatNumber pattern='##.##%'
									value='${qprt.fractionalDiscount}' />
							</td>

						</tr>
						<tr>
							<td>
								Discounted Residence Value
							</td>
							<td>
								&nbsp;
							</td>
							<td align='right'>
								<fmt:formatNumber pattern='###,###,###'
									value='${qprt.fmv * (1 - qprt.fractionalDiscount)}' />
							</td>
						</tr>
						<tr>
							<td>
								Residence Growth
							</td>
							<td>
								&nbsp;
							</td>
							<td align='right'>
								<fmt:formatNumber pattern='##.##%' value='${qprt.growth }' />
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
								<fmt:formatNumber pattern='##.##%' value='${qprt.irsRate }' />
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
								<fmt:formatDate pattern='M/d/yyyy' value='${qprt.irsDate }' />
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
								<fmt:formatNumber pattern='###' value='<%=qprt.getClientAge()%>' />
								)
								<c:if test="${qprt.spouseAge >0 }">&nbsp;
									(<fmt:formatNumber pattern='###'
										value='<%=qprt.getSpouseAge()%>' />)
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
								<fmt:formatNumber pattern='##.#' value='${q1.lifeExpectancy }' />
							</td>
						</tr>
						<tr>
							<td>
								Reversion Retained
							</td>
							<td>
								&nbsp;
							</td>
							<td align='right'>
								${qprt.reversionRetained}
							</td>
						</tr>
						<tr>
							<td colspan='3'>
								&nbsp;
							</td>
						</tr>
						<tr>
							<td>
								<table width='100%' align='center' frame='box'>
									<!-- Table for first trust -->
										<tr>
											<td align='center' colspan='3'><h4>Trust 1</h4></td>
										</tr>
									<tr>
										<td>
											Term
										</td>
										<td align='right'>
											<fmt:formatNumber pattern='###' value='${q1.term }' />
										</td>
									</tr>
									<tr>
										<td>
											Income Interest
										</td>
										<td align='right'>
											<fmt:formatNumber pattern='$###,###,###'
												value='${q1.incomeIntValue }' />
										</td>
									</tr>
									<tr>
										<td>
											Reversionary Interest
										</td>
										<td align='right'>
											<fmt:formatNumber pattern='$###,###,###'
												value='${q1.reversionaryIntValue }' />
										</td>
									</tr>
									<tr>
										<td>
											Remainder Interest
										</td>
										<td align='right'>
											<fmt:formatNumber pattern='$###,###,###'
												value='${q1.remainderIntValue }' />
										</td>
									</tr>
									<tr>
										<td colspan='2'>
											&nbsp;
										</td>
									</tr>
									<tr>
										<td>
											Total Interest
										</td>
										<td align='right'>
											<fmt:formatNumber pattern='$###,###,###'
												value='<%=q1.getDValue()%>' />
										</td>
									</tr>
									<tr>
										<td>
											Retained Interest
										</td>
										<td align='right'>
											<fmt:formatNumber pattern='$###,###,###'
												value='${q1.retainedIntValue }' />
										</td>
									</tr>
									<tr>
										<td>
											Taxable Gift
										</td>
										<td align='right'>
											<fmt:formatNumber pattern='$###,###,###'
												value='${q1.taxableGiftValue }' />
										</td>
									</tr>
									<tr>
										<td colspan='3'>&nbsp;</td>
									</tr>
									<tr>
										<td>Ending Value of Residence</td>
										<td align='right'>
											<fmt:formatNumber pattern='$###,###,###' value='${q1.endResidenceValue }'/>
										</td>
									</tr>
									<tr>
										<td>Built-in Gain</td>
										<td align='right'>
											<fmt:formatNumber pattern='###,###,###' value='${q1.builtInGain }'/>
										</td>
									</tr>
									<tr>
										<td>Estate Tax Savings</td>
										<td align='right'>
											<fmt:formatNumber pattern='###,###,###' value='${q1.estateTaxSavings}'/>
										</td>
									</tr>
								</table>
							</td>
							<td>
								&nbsp;
							</td>
							<c:if test="${qprt.trusts>1 }">
								<td>
									<table width='100%' frame='box'>
										<!-- Table for second trust if needed -->
										<tr>
											<td align='center' colspan='3'><h4>Trust 2</h4></td>
										</tr>
										<tr>
											<td>
												Term
											</td>
											<td>
												&nbsp;
											</td>
											<td align='right'>
												<fmt:formatNumber pattern='###' value='${q2.term }' />
											</td>
										</tr>
										<tr>
											<td>
												Income Interest
											</td>
											<td>
												&nbsp;
											</td>
											<td align='right'>
												<fmt:formatNumber pattern='$###,###,###'
													value='${q2.incomeIntValue }' />
											</td>
										</tr>
										<tr>
											<td>
												Reversionary Interest
											</td>
											<td>
												&nbsp;
											</td>
											<td align='right'>
												<fmt:formatNumber pattern='$###,###,###'
													value='${q2.reversionaryIntValue }' />
											</td>
										</tr>
										<tr>
											<td>
												Remainder Interest
											</td>
											<td>
												&nbsp;
											</td>
											<td align='right'>
												<fmt:formatNumber pattern='$###,###,###'
													value='${q2.remainderIntValue }' />
											</td>
										</tr>
										<tr>
											<td colspan='2'>
												&nbsp;
											</td>
										</tr>
										<tr>
											<td>
												Total Interest
											</td>
											<td>
												&nbsp;
											</td>
											<td align='right'>
												<fmt:formatNumber pattern='$###,###,###'
													value='<%=q2.getDValue()%>' />
											</td>
										</tr>
										<tr>
											<td>
												Retained Interest
											</td>
											<td>
												&nbsp;
											</td>
											<td align='right'>
												<fmt:formatNumber pattern='$###,###,###'
													value='${q2.retainedIntValue }' />
											</td>
										</tr>
										<tr>
											<td>
												Taxable Gift
											</td>
											<td>
												&nbsp;
											</td>
											<td align='right'>
												<fmt:formatNumber pattern='$###,###,###'
													value='${q2.taxableGiftValue }' />
											</td>
										</tr>
										<tr>
										<td colspan='3'>&nbsp;</td>
									</tr>
									<tr>
										<td>Ending Value of Residence</td>
										<td>&nbsp;</td>
										<td align='right'>
											<fmt:formatNumber pattern='$###,###,###' value='${q1.endResidenceValue }'/>
										</td>
									</tr>
									<tr>
										<td>Built-in Gain</td>
										<td>&nbsp;</td>
										<td align='right'>
											<fmt:formatNumber pattern='###,###,###' value='${q1.builtInGain }'/>
										</td>
									</tr>
									<tr>
										<td>Estate Tax Savings</td>
										<td>&nbsp;</td>
										<td align='right'>
											<fmt:formatNumber pattern='###,###,###' value='${q1.estateTaxSavings}'/>
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
		<%@include file="/inc/aesFooter.jspf" %>
	</body>
</html>
