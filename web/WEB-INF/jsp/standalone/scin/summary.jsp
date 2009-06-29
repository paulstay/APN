<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%@ page import="com.estate.constants.*"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
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

		<title>SCIN Summary</title>

		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<link rel="stylesheet" type="text/css" href="<%=path%>/css/styles.css">
	</head>
	<body>
		<%@include file="/inc/aesHeader.jspf" %>
		<h3 align='center'>
			Self Canceling Installment Note
		</h3>
		<h4 align='center'>
			Summary
		</h4>
		<jsp:useBean id='userInfo' scope='session'
			type='com.teag.bean.PdfBean' />
		<jsp:useBean id='scin' scope='session' type='com.estate.toolbox.SCIN' />
		<%
			scin.calculate();
		%>
		<table>
			<tr>
				<td valign='top'><%@include file='menu.jspf'%></td>
				<td align='center' width='100%'>
					<table width='60%'>
						<tr>
							<td>
								Type of Note
							</td>
							<td>
								&nbsp;
							</td>
							<td align='right'>
								<c:if test="${scin.noteType == 0 }">Level Principlan + Interest</c:if>
								<c:if test="${scin.noteType == 1 }">Self Amoritizing</c:if>
								<c:if test="${scin.noteType == 2 }">Interest Only</c:if>
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
								<fmt:formatNumber pattern='##.##%' value='${scin.irsRate }' />
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
								<fmt:formatDate pattern='M/d/yyyy' value='${scin.irsDate }' />
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
								<fmt:formatNumber pattern='$###,###,###' value='${scin.fmv }' />
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
								<fmt:formatNumber pattern='$###,###,###' value='${scin.basis }' />
							</td>
						</tr>
						<tr>
							<td>
								Term of Note
							</td>
							<td>
								&nbsp;
							</td>
							<td align='right'>
								<fmt:formatNumber pattern='###' value='${scin.term }' />
							</td>
						</tr>
						<tr>
							<td>
								Interest Rate of Note
							</td>
							<td>
								&nbsp;
							</td>
							<td align='right'>
								<fmt:formatNumber pattern='##.###%' value='${scin.noteRate }' />
							</td>
						</tr>
						<tr>
							<td>
								Client Age
							</td>
							<td>
								&nbsp;
							</td>
							<td align='right'>
								<fmt:formatNumber pattern='###' value='<%=scin.getClientAge()%>' />
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
						</tr>
						<tr>
							<td>
								<b>Principal Risk Premium</b>
							</td>
							<td>
								&nbsp;
							</td>
							<td align='right'>
								<fmt:formatNumber pattern='$###,###,###'
									value='${scin.principalRiskPremium }' />
							</td>
						</tr>
						<tr>
							<td>
								<b>Adjusted Principal with Risk Premium</b>
							</td>
							<td>
								&nbsp;
							</td>
							<td align='right'>
								<fmt:formatNumber pattern='$###,###,###'
									value='${scin.adjustedPremium }' />
							</td>
						</tr>
						<tr>
							<td>
								Adjusted Interest Rate
							</td>
							<td>
								&nbsp;
							</td>
							<td align='right'>
								<fmt:formatNumber pattern='##.####%'
									value='${scin.adjustedInterest }' />
							</td>
						</tr>
						<tr>
							<td>
								Note Payment (Principal Risk Premium)
							</td>
							<td>
								&nbsp;
							</td>
							<td align='right'>
								<fmt:formatNumber pattern='$###,###,###'
									value='${scin.noteRate * scin.adjustedPremium }' />
							</td>
						</tr>
						<tr>
							<td>
								Note Payment (Interest Risk Premium)
							</td>
							<td>
								&nbsp;
							</td>
							<td align='right'>
								<fmt:formatNumber pattern='$###,###,###'
									value='${scin.fmv * scin.adjustedInterest }' />
							</td>
						</tr>
						<tr>
							<td colspan='3'>&nbsp;</td>
						</tr>
						<tr>
							<td colspan='3'>
								<table  align='center'>
									<tr>
										<th colspan='5'>
											<c:if test='${scin.paymentType == 0 }'>Annual Cash Flow(Principal Risk Premium)</c:if>
											<c:if test='${scin.paymentType == 1 }'>Annual Cash Flow(Interest Risk Premium)</c:if>
										</th>
									</tr>
									<tr>
										<th>Year</th>
										<th>Beginning Principal</th>
										<th>Annual Growth</th>
										<th>Annual Payment</th>
										<th>Ending Principal</th>
									</tr>
									<c:set value='${scin.fmv }' var='prin'/>
									<c:set value='${scin.growth}' var='growth'/>
									<c:forEach var='i' begin='0' end='${scin.term + scin.yrsDeffered -1}'>
									<tr>
										<td>${i+1}</td>
										<td align='right'><fmt:formatNumber pattern='###,###,###' value='${prin }'/></td>
										<td align='right'><fmt:formatNumber pattern='###,###,###' value='${prin * growth }'/></td>
										<td align='right'><fmt:formatNumber pattern='###,###,###' value='${scin.payment[i] }'/></td>
										<c:set var='prin' value='${prin + (prin * growth) - scin.payment[i]}'/>
										<td align='right'><fmt:formatNumber pattern='###,###,###' value='${prin}'/></td>
									</tr>
									</c:forEach>
								</table>
							</td>
						</tr>
						<tr>
							<td colspan='3'>
								<table  align='center'>
									<tr>
										<th colspan='6'>
											<c:if test='${scin.paymentType == 0 }'>Payment Breakdown(Principal Risk Premium)</c:if>
											<c:if test='${scin.paymentType == 1 }'>Payment Breakdown(Interest Risk Premium)</c:if>
										</th>
									</tr>
									<tr>
										<th>Year</th>
										<th>Annual Payment</th>
										<th>Interest Portion</th>
										<th>Capital Gains Portion</th>
										<th>Tax Free Portion</th>
										<th>Note Balance</th>
									</tr>
									<c:set value='###,###,###' var='p'/>
									<c:forEach var='i' begin='0' end='${scin.term + scin.yrsDeffered -1 }'>
										<tr>
											<td>${i+1 }</td>
											<td align='right'><fmt:formatNumber pattern='${p}' value='${scin.payment[i] }'/></td>
											<td align='right'><fmt:formatNumber pattern='${p}' value='${scin.interestPayment[i] }'/></td>
											<td align='right'><fmt:formatNumber pattern='${p}' value='${scin.capGains[i] }'/></td>
											<td align='right'><fmt:formatNumber pattern='${p}' value='${scin.taxFree[i] }'/></td>
											<td align='right'><fmt:formatNumber pattern='${p}' value='${scin.note[i] }'/></td>
										</tr>
									</c:forEach>
								</table>
							</td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
		<%@include file="/inc/aesFooter.jspf" %>
	</body>
</html>
