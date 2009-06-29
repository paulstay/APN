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

		<title>SCIN Summary</title>

		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<link rel="stylesheet" type="text/css" href="<%=path%>/css/styles.css"
			media="screen" />
	</head>
	<jsp:useBean id='epg' scope='session' type='com.teag.webapp.EstatePlanningGlobals' />
	<jsp:useBean id='scin' scope='session' type='com.teag.estate.SCINTool' />
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
							<td colspan='2' align='center'>
								<h3 align='center'>
									SCIN Assumptions
								</h3>
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
						</tr>
						<tr>
							<td>
								Type of Note
							</td>
							<td align='right'>
								<c:if test="${scin.noteType == 0 }">
    							Level Principal + Interest
    						</c:if>
								<c:if test="${scin.noteType == 1 }">
    							Self Amoritizing
    						</c:if>
								<c:if test="${scin.noteType == 2 }">
    							Interest Only
    						</c:if>
							</td>
						</tr>
						<tr>
							<td>
								IRS 7520 Rate
							</td>
							<td align='right'>
								<fmt:formatNumber pattern="##.##%" value='${scin.irsRate }' />
							</td>
						</tr>
						<tr>
							<td>
								Transfer Date
							</td>
							<td align='right'>
								<fmt:formatDate pattern='M/d/yyyy' value='${scin.irsDate }' />
							</td>
						</tr>
						<tr>
							<td>
								Beginning Principal
							</td>
							<td align='right'>
								<fmt:formatNumber type='currency' value='${scin.fmv }' />
							</td>
						</tr>
						<tr>
							<td>
								Beginning Basis
							</td>
							<td align='right'>
								<fmt:formatNumber type='currency' value='${scin.basis }' />
							</td>
						</tr>
						<tr>
							<td>
								Term of Note
							</td>
							<td align='right'>
								<fmt:formatNumber pattern="###" value='${scin.term }' />
							</td>
						</tr>
						<tr>
							<td>
								Interest Rate of Note
							</td>
							<td align='right'>
								<fmt:formatNumber pattern="##.###%" value='${scin.noteRate }' />
							</td>
						</tr>
						<tr>
							<td>
								Client Age
							</td>
							<td align='right'><%=scin.getCAge()%></td>
						</tr>
						<tr>
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
							<td align='right'>
								<fmt:formatNumber pattern="$###,###,###"
									value="${scin.principalRiskPremium }" />
							</td>
						</tr>
						<tr>
							<td>
								<b>Adjusted Principal with Risk Premium</b>
							</td>
							<td align='right'>
								<fmt:formatNumber pattern="$###,###,###"
									value="${scin.adjustedPremium }" />
							</td>
						</tr>
						<tr>
							<td>
								Adjusted Interest Rate
							</td>
							<td align='right'>
								<fmt:formatNumber pattern="##.####%"
									value="${scin.adjustedInterest}" />
							</td>
						</tr>
						<!-- Table showing payments, cap gains protion and tax free portion. -->
						<tr>
							<td>
								&nbsp;
							</td>
							<td>
								&nbsp;
							</td>
						</tr>
						<tr>
							<td colspan='2' align='center'>
								<table border=1'>
									<tr>
										<c:if test="${ scin.paymentType == 0 }">
											<th colspan='6'>
												Annual Cash Flow (Principal Risk Premium)
											</th>
										</c:if>
										<c:if test="${ scin.paymentType == 1 }">
											<th colspan='6'>
												Annual Cash Flow (Interest Risk Premium)
											</th>
										</c:if>

									</tr>
									<tr>
										<th>
											Year
										</th>
										<th>
											Beginning Principal
										</th>
										<th>
											Annual Growth
										</th>
										<th>
											Annual Payment
										</th>
										<th>
											Ending Principal
										</th>
									</tr>
									<c:set value='${scin.fmv }' var='prin' />
									<c:set value='${scin.growth }' var='growth' />
									<c:forEach var="i" begin="0"
										end="${scin.term + scin.yrsDeferred-1}">
										<tr>
											<td>
												${i+1}
											</td>
											<td align='right'>
												<fmt:formatNumber pattern="###,###,###" value="${prin}" />
											</td>
											<td align='right'>
												<fmt:formatNumber pattern="###,###,###"
													value="${prin * growth }" />
											</td>
											<td align='right'>
												<fmt:formatNumber pattern="###,###,###"
													value="${scin.payment[i] }" />
											</td>
											<td align='right'>
												<fmt:formatNumber pattern="###,###,###"
													value="${prin + (prin * growth) - scin.payment[i] }" />
											</td>
											<c:set value='${prin + (prin * growth) - scin.payment[i] }'
												var='prin' />
										</tr>
									</c:forEach>
								</table>
							</td>
						</tr>
						<tr>
							<td colspan='2' align='center'>
								<table border=1'>
									<tr>
										<c:if test="${ scin.paymentType == 0 }">
											<th colspan='6'>
												Payment Breakdown (Principal Risk Premium)
											</th>
										</c:if>
										<c:if test="${ scin.paymentType == 1 }">
											<th colspan='6'>
												Payment Breakdown (Interest Risk Premium)
											</th>
										</c:if>
									</tr>
									<tr>
										<th>
											Year
										</th>
										<th>
											Annual Payment
										</th>
										<th>
											Interest Portion
										</th>
										<th>
											Capital Gain Portion
										</th>
										<th>
											Tax Free Portion
										</th>
										<th>
											Note Balance
										</th>
									</tr>
									<c:forEach var="i" begin="0"
										end="${scin.term + scin.yrsDeferred-1}">
										<tr>
											<td>
												${i+1}
											</td>
											<td align='right'>
												<fmt:formatNumber pattern="###,###,###"
													value="${scin.payment[i] }" />
											</td>
											<td align='right'>
												<fmt:formatNumber pattern="###,###,###"
													value="${scin.interestPayment[i] }" />
											</td>
											<td align='right'>
												<fmt:formatNumber pattern="###,###,###"
													value="${scin.capGains[i] }" />
											</td>
											<td align='right'>
												<fmt:formatNumber pattern="###,###,###"
													value="${scin.taxFree[i] }" />
											</td>
											<td align='right'>
												<fmt:formatNumber pattern="###,###,###"
													value="${scin.note[i] }" />
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
		<%@include file="/inc/aesFooter.jspf"%>
	</body>
</html>
