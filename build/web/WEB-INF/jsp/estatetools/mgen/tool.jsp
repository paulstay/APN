<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%@ page import="com.estate.constants.*"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="teagtags" prefix="teag"%>
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
		<title>Multi Generational Trust Setup Page</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<link rel="stylesheet" type="text/css" href="<%=path%>/css/styles.css">
	</head>
	<jsp:useBean id='mgen' scope='session'
		type='com.teag.estate.MGTrustTool' />
	<body>
		<%@include file="/inc/aesHeader.jspf"%>
		<h3 align='center'>
			Multi Generational Trust
		</h3>
		<table align='center' width='100%'>
			<tr>
				<td colspan='2' align='center'>
					<h3>
						Tool Setup
					</h3>
				</td>
			</tr>
			<tr>
				<td>
					<%@ include file="menu.jspf"%>
				</td>
				<td>
					<teag:form name='iform' method='post'
						action='servlet/MgenToolServlet'>
						<teag:hidden name='pageView' value='noUpdate' />
						<table align='left'>
							<tr>
								<td>
									Years per Generation
								</td>
								<td>
									<teag:text name="yearsPerGeneration" fmt="number"
										pattern="###" value="${mgen.yearsPerGeneration }" />
								</td>
							</tr>
							<tr>
								<td>
									Inflation Rate (%)
								</td>
								<td>
									<teag:text name="inflationRate" fmt="percent"
										value="${mgen.inflationRate }" />
								</td>
							</tr>
							<tr>
								<td>
									Years MGT Tax Paid
								</td>
								<td>
									<teag:text name="term" value="${mgen.term}" />
								</td>
							</tr>
							<tr>
								<td>
									Annual Payout Rate (2-4 Generations)
								</td>
								<td>
									<teag:text name="payoutRate" fmt="percent"
										value="${mgen.payoutRate }" />
								</td>
							</tr>
							<tr>
								<td>
									Trust Management Fee (%)
								</td>
								<td>
									<teag:text name="trusteeRate" fmt="percent"
										value="${mgen.trusteeRate }" />
								</td>
							</tr>
							<tr>
								<td colspan='2'>
									&nbsp;
								</td>
							</tr>
							<tr>
								<td colspan='2' align='center'>
									<h4>
										Residential State
									</h4>
								</td>
							</tr>
							<tr>
								<td>
									Select State
								</td>
								<td>
									<teag:select name="trustState" options="${states}" optionValue="${mgen.trustState}"/>
								</td>
							</tr>
							<tr>
								<td>
									Combined State and Federal Tax Rate (%)
								</td>
								<td>
									<teag:text name="combinedIncomeTaxRate" fmt="percent"
										value="${mgen.combinedIncomeTaxRate }" />
								</td>
							</tr>
							<tr>
								<td>
									Capital Gains Rate
								</td>
								<td>
									<teag:text name="capitalGainsRate" fmt="percent"
										value="${mgen.capitalGainsRate }" />
								</td>
							</tr>
							<tr>
								<td>
									Is Current State subject to Rule against Perpetuities?
								</td>
								<td>
									<c:choose>
										<c:when test="${mgen.lawOfPerpetuity}">
											<c:set var="lop" value="true"/>
										</c:when>
										<c:otherwise>
											<c:set var="lop" value="false"/>
										</c:otherwise>
									</c:choose>
									<teag:select name="lawOfPerpetuity">
										<teag:option value="true" label="Yes" selected="${lop}"/>
										<teag:option value="false" label="No" selected="${lop}"/>
									</teag:select>
								</td>
							</tr>
							<tr>
								<td colspan='2'>
									&nbsp;
								</td>
							</tr>
							<tr>
								<td colspan='2' align='center'>
									<h4>
										Multi-Generational Trust State
									</h4>
								</td>
							</tr>
							<tr>
								<td>Select Trust State *</td>
								<td>
									<teag:select name='mgenTrustState' options="${mgenStates}" optionValue="${mgen.mgenTrustState}"/>
								</td>
							</tr>
							<tr>
								<td>
									Trust combined State and Fed. Tax Rate
								</td>
								<td>
									<teag:text name="delawareCombinedTaxRate" fmt="percent"
										value="${mgen.delawareCombinedTaxRate }" />
								</td>
							</tr>
							<tr>
								<td>
									Trust State and Fed Capital Gains Rate
								</td>
								<td>
									<teag:text name="delawareCapitalGainsRate" fmt="percent"
										value="${mgen.delawareCapitalGainsRate }" />
								</td>
							</tr>
							<tr>
								<td colspan='3' align='center'>
									<input type="image" src="toolbtns/Update.png" alt="Update Tool"
										onclick="document.iform.pageView.value='PROCESS'"
										onmouseover="this.src='toolbtns/Update_over.png';"
										onmouseout="this.src='toolbtns/Update.png';"
										onmousedown="this.src='toolbtns/Update_down.png';" />
								</td>
							</tr>
						</table>
					</teag:form>
				</td>
			</tr>
		</table>
	</body>
</html>
