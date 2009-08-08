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

		<title>IDIT Summary</title>

		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<link rel="stylesheet" type="text/css" href="<%=path%>/css/styles.css"
			media="screen" />
	</head>
	<jsp:useBean id='sidit' scope='session' type='com.teag.estate.SIditTool' />
	<body>
		<%@include file="/inc/aesHeader.jspf"%>
		<h3 align='center'>
			Sale to an Intentionally Deffective Irrevocable Trust
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
					<table width='60%' align='center'>
						<tr>
						<th colspan='2' align='center'>
							<h3>
								Assumptions
							</h3>
						</th>
						</tr>
						<tr>
							<td>
								Trust Assets Value (Fair Market)
							</td>
							<td align='right'>
								<fmt:formatNumber pattern='$###,###,###' value='${sidit.fmv }' />
							</td>
						</tr>
						<tr>
							<td>Gift</td>
							<td align='right'>
								<fmt:formatNumber pattern='$###,###,###' value='${sidit.giftAmount}'/>
							</td>
						</tr>
						<tr>
							<td>
								Note Value
							</td>
							<td align='right'>
								<fmt:formatNumber pattern='$###,###,###' value='${sidit.dmv}' />
							</td>
						</tr>
						<tr>
							<td>
								Note Term
							</td>
							<td align='right'>
								<fmt:formatNumber pattern='###' value='${sidit.noteTerm }' />
							</td>
						</tr>
						<tr>
							<td>
								Note Rate
							</td>
							<td align='right'>
								<fmt:formatNumber pattern='##.###%' value='${sidit.noteRate }' />
							</td>
						</tr>
						<tr>
							<td>
								Note Payment/Year
							</td>
							<td align='right'>
								<fmt:formatNumber pattern='$###,###,###'
									value='${sidit.payment }' />
							</td>
						</tr>
						<c:if test='${sidit.lifeDeathBenefit > 0 }'>
							<tr>
								<td>
									Life Insurance Death Benefit
								</td>
								<td align='right'>
									<fmt:formatNumber pattern='$###,###,###'
										value='${sidit.lifeDeathBenefit}' />
								</td>
							</tr>
							<tr>
								<td>
									Life Insurance Premium
								</td>
								<td align='right'>
									<fmt:formatNumber pattern='$###,###,###'
										value='${sidit.lifePremium}' />
								</td>
							</tr>
							<tr>
								<td>
									Year to Pay Premium
								</td>
								<td align='right'>
									<fmt:formatNumber pattern='###'
										value='${sidit.lifePremiumYears}' />
								</td>
							</tr>
						</c:if>
					</table>
					<br>
					<table border='1' align='center'>
						<tr>
							<th>
								Year
							</th>
							<th>
								Trust Balance
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
							<th>
								Premium
							</th>
							<th>
								Ending Balance
							</th>
							<th>
								Note Balance
							</th>
						</tr>
						<tr>
							<c:set var='lp' value='${sidit.lifePremium}' />
							<c:forEach var='yr' begin='0' end='${sidit.noteTerm-1 }'>
								<tr>
									<td align='center'>
										${yr+1 }
									</td>
									<td align='right'>
										<fmt:formatNumber pattern='###,###,###'
											value='${sidit.balance[yr] }' />
									</td>
									<td align='right'>
										<fmt:formatNumber pattern='###,###,###'
											value='${sidit.growth[yr] }' />
									</td>
									<td align='right'>
										<fmt:formatNumber pattern='###,###,###'
											value='${sidit.income[yr] }' />
									</td>
									<td align='right'>
										<fmt:formatNumber pattern='###,###,###'
											value='${sidit.notePayment[yr] }' />
									</td>
									<td align='right'>
										<fmt:formatNumber pattern='###,###,###' value='${lp }' />
									</td>
									<td align='right'>
										<fmt:formatNumber pattern='###,###,###'
											value='${sidit.balance[yr] + sidit.growth[yr] + sidit.income[yr] -sidit.notePayment[yr] - lp}' />
									</td>
									<td align='right'>
										<fmt:formatNumber pattern='###,###,###' value='${sidit.noteBalance[yr] }'/>
									</td>
									<c:if test="${yr >= sidit.lifePremiumYears-1 }">
										<c:set var='lp' value='${0}' />
									</c:if>
								</tr>
							</c:forEach>
					</table>
				</td>
			</tr>
		</table>
		<%@include file="/inc/aesFooter.jspf"%>
	</body>
</html>
