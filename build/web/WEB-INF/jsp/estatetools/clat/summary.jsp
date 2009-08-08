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

		<title>Clat Summary</title>

		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<link rel="stylesheet" type="text/css" href="<%=path%>/css/styles.css"
			media="screen" />
	</head>
	<jsp:useBean id='clat' scope='session' type='com.teag.estate.ClatTool' />
	<jsp:useBean id='epg' scope='session'
		type='com.teag.webapp.EstatePlanningGlobals' />
	<body>
		<%@include file="/inc/aesHeader.jspf"%>
		<h3 align='center'>
			Charitable Lead Annuity Trust
		</h3>
		<h3 align='center'>
			Summary Page
		</h3>
		<table width='100%'>
			<tr>
				<td valign='top' width='20%'>
					<%@ include file="menu.jspf"%>
				</td>
				<td valign='top' align='center' width='80%'>
					<table align='left' frame="box">
						<tr>
							<th
								style='background-color: #cdcdcd; font-weight: bold; font-size: 1.2em;'
								align='center' colspan='5'>
								Assumptions
							</th>
						</tr>
						<tr>
							<td align='left'>
								FMV of Assets in Trust
							</td>
							<td align='right'>
								<fmt:formatNumber pattern='###,###,###'
									value='${clat.totalValue}' />
							</td>
							<td width='5%'>
								&nbsp;
							</td>
							<td align='left'>
								Section 7520 Rate
							</td>
							<td align='right'>
								<fmt:formatNumber pattern='##.##%' value='${clat.afrRate}' />
							</td>
						</tr>
						<tr>
							<td align='left'>
								Value for Calculations
							</td>
							<td align='right'>
								<fmt:formatNumber pattern="###,###,###"
									value="${clat.nonDiscountValue + clat.discountedValue }" />
							</td>
							<td>
								&nbsp;
							</td>
							<td align='left'>
								Estimated Transfer Date
							</td>
							<td align='right'>
								${clat.afrDate}
							</td>
						</tr>
						<tr>
							<td align='left'>
								Annuity Interest (Charitable Deduction)
							</td>
							<td align='right'>
								<fmt:formatNumber pattern="###,###,###"
									value="${clat.clatDeduction}" />
							</td>
							<td>
								&nbsp;
							</td>
							<td align='left'>
								Annuity Factor
							</td>
							<td align='right'>
								<fmt:formatNumber pattern='#,###.####'
									value='${clat.annuityFactor}' />
							</td>
						</tr>
						<tr>
							<td align='left'>
								Remainder Interest
							</td>
							<td align='right'>
								<fmt:formatNumber pattern='###,###,###'
									value='${clat.remainderInterest}' />
							</td>
							<td>
								&nbsp;
							</td>
							<td align='left'>
								Annuity Payout
							</td>
							<td align='right'>
								<fmt:formatNumber pattern='###,###,###' value='${clat.annuity}' />
							</td>
						</tr>
						<tr>
							<td align='left'>
								Term
							</td>
							<td align='right'>
								<fmt:formatNumber pattern='###' value='${clat.clatTerm}' />
							</td>
							<td>
								&nbsp;
							</td>
							<td align='left'>
								Annuity Payment Increase
							</td>
							<td align='right'>
								<fmt:formatNumber pattern='##.#%'
									value='${clat.annuityIncrease}' />
							</td>
						</tr>
						<tr>
							<td align='left'>
								Is Grantor Trust?
							</td>
							<td align='right'>
								<c:if test="${clat.grantorFlag == 'Y' }">Yes</c:if>
								<c:if test="${clat.grantorFlag == 'N' }">No</c:if>
							</td>
							<td>
								&nbsp;
							</td>
							<td align='left'>
								First Year Annuity Payment Rate
							</td>
							<td align='right'>
								<fmt:formatNumber pattern='##.###%' value='${clat.paymentRate}' />
							</td>
						</tr>
						<tr>
							<td align='left'>
								Trust Type
							</td>
							<td align='right'>
								<c:if test="${clat.clatType == 'T' }">Term Certain</c:if>
								<c:if test="${clat.clatType == 'L' }">Life</c:if>
								<c:if test="${clat.clatType == 'S' }">Shorter of Term and Life</c:if>
							</td>
							<td>
								&nbsp;
							</td>
							<td align='left'>
								Ages
							</td>
							<td align='right'>
								(
								<fmt:formatNumber pattern="###" value="${epg.clientAge}" />
								)(
								<fmt:formatNumber pattern="###" value="${epg.spouseAge}" />
								)
							</td>
						</tr>
						<tr>
							<td colspan='5'>
								<c:if test="${clat.annuityExhaustionTest }">
									<h3 align='center'>
										<span style="color:red; border:black;">Annuity
											Exhaustion Test Faild</span>
									</h3>
								</c:if>
							</td>
						</tr>
						<tr>
							<td colspan='5'>
								<c:if test="${clat.presentValueRegs }">
									<h3 align='center'>
										<span style='color: red; border:black;'> Annuity
											Interest (Charitable Deduction) Limited By IRS 7520 Regs. </span>
									</h3>
								</c:if>
							</td>
						</tr>
						<tr>
							<td colspan='5'><span style='color: red; font-weight: bold;'>
								The maximum annuity payment for this type of annuity trust
								without falling into the special revaluation rules under Rev.
								Rul. 77-454 or Code Regulations Section 25.7520-3(b)(2)(i).
								known as Exhausting Annuities is
								<fmt:formatNumber pattern="$###,###,###"
									value="${clat.maxPayment}" /></span>
							</td>
						</tr>
					</table>
					<br>
					<table style="margin-top: 20px; color: blue;" align='left'
						width='80%' frame='box'>
						<tr>
							<th colspan='7' style='background-color: #cccccd;'>
								Charitable Lead Annuity Trust Cash Flow
							</th>
						</tr>
						<tr>
							<th>
								Year
							</th>
							<th>
								Beg. Balance
							</th>
							<th>
								Growth
							</th>
							<th>
								Income
							</th>
							<th>
								Payment
							</th>
							<th style="color: red;">
								Taxes
							</th>
							<th>
								End Balance
							</th>
						</tr>
						<c:set var='idx' value='1' />
						<c:forEach var='cf' items='${schedule}'>
							<tr>
								<td align='center'>
									<fmt:formatNumber pattern="##" value='${idx}' />
								</td>
								<td align='right'>
									<fmt:formatNumber pattern='###,###,###' value='${cf[0] }' />
								</td>
								<td align='right'>
									<fmt:formatNumber pattern='###,###,###' value='${cf[1] }' />
								</td>
								<td align='right'>
									<fmt:formatNumber pattern='###,###,###' value='${cf[2] }' />
								</td>
								<td align='right'>
									<fmt:formatNumber pattern='###,###,###' value='${cf[3] }' />
								</td>
								<td style="color: red; text-align: right;">
									<fmt:formatNumber pattern='###,###,###' value='${cf[4] }' />
								</td>
								<td align='right'>
									<fmt:formatNumber pattern='###,###,###' value='${cf[5] }' />
								</td>
							</tr>
							<c:set var='idx' value='${idx+1}' />
						</c:forEach>
					</table>
				</td>
			</tr>
		</table>
		<%@include file="/inc/aesFooter.jspf"%>
	</body>
</html>