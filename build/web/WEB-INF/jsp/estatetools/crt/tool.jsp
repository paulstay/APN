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
		<title>Charitable Remainder Trust Setup Page</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<link rel="stylesheet" type="text/css" href="<%=path%>/css/styles.css">
	</head>
	<jsp:useBean id='crt' scope='session' type='com.teag.estate.CrtTool' />
	<body>
		<%@include file="/inc/aesHeader.jspf"%>
		<h3 align='center'>
			Charitable Remainder Trust
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
						<teag:form name="iform" action="servlet/CrtToolServlet" method="post">
						<teag:hidden name='pageView' value='noUpdate' />
						<table align='left'>
							<tr>
								<td>
									Irs 7520 rate :
								</td>
								<td>
									<teag:text fmt='percent' pattern='##.###%' name='irsRate'
										value='${crt.irsRate }' />
								</td>
							</tr>
							<tr>
								<td>
									IRS 750 Date of Transfer :
								</td>
								<fmt:formatDate var='idate' pattern='M/dd/yyyy' value='${crt.irsDate }'/>
								<td>
									<teag:text name='afrDate' value='${idate}' />
								</td>
							</tr>
							<tr>
								<td>
									Payment Freq. : 
								</td>
								<td>
									<teag:select name='frequency'>
										<teag:option label='Annual' value='1'
											selected='${crt.frequency}' />
										<teag:option label='SemiAnnual' value='2'
											selected='${crt.frequency}' />
										<teag:option label='Quarterly' value='4'
											selected='${crt.frequency}' />
										<teag:option label='Monthly' value='12'
											selected='${crt.frequency}' />
									</teag:select>
								</td>
							</tr>
							<tr>
								<td>Unitrust lag in Months</td>
								<td><teag:text name='uniLag' value='${crt.uniLag }'/></td>
							</tr>
							<tr>
								<td>Investment Return (%)</td>
								<td>
									<teag:text name='investmentReturn' fmt='percent' pattern="##.####%" value='${crt.investmentReturn }'/>
								</td>
							</tr>
							<tr>
								<td>Payout Rate *</td>
								<td>
									<teag:text name='payoutRate' fmt='percent' pattern="##.####%" value='${crt.payoutRate}'/>
									<span style="color: red;">${errorPayout}</span>
								</td>
							</tr>
							<tr>
								<td>Portion of Payout Taxed as Income</td>
								<td>
									<teag:text name='payoutIncome' fmt='percent' pattern="##.###%"  value='${crt.payoutIncome }'/>
								</td>
							</tr>
							<tr>
								<td>Portion of Payout Taxed as Capital Gains</td>
								<td>
									<teag:text name='payoutGrowth' fmt='percent' pattern="##.###%" value='${crt.payoutGrowth }'/>
								</td>
							</tr>
							<tr>
								<td>Adjusted Gross Income</td>
								<td>
									<teag:text name='adjustedGrossIncome' fmt='number' pattern='###,###,###' value='${crt.adjustedGrossIncome}'/>
								</td>
							</tr>
							<tr>
								<td>
									Fed and State Income Tax Rate :
								</td>
								<td>
									<teag:text fmt='percent' name='taxRate' pattern="##.###%"
										value='${crt.taxRate }'/>
								</td>
							</tr>
							<tr>
								<td>Capital Gains Rate</td>
								<td>
									<teag:text fmt='percent' pattern="##.###%" name='capitalGainsRate' value="${crt.capitalGainsRate}"/>
								</td>
							</tr>
							<tr>
								<td>
									Estate Tax Rate (%) :
								</td>
								<td>
									<teag:text fmt='percent' pattern="##.###%" name='estateTaxRate'
										value='${crt.estateTaxRate }' />
								</td>
							</tr>
							<tr>
								<td>Life Insurance Premium (Yearly)</td>
								<td><teag:text name="lifePremium" fmt='number' pattern='###,###,###' value="${crt.lifePremium}"/></td>
							</tr>
							<tr>
								<td>Life Insurance Death Benefit</td>
								<td><teag:text name="lifeDeathBenefit" fmt='number' pattern='###,###,###' value="${crt.lifeDeathBenefit}"/></td>
							</tr>
							<tr>
								<td colspan='2' align='center'>
									<span style='color: red;'>* Min Payout = 5.0%,Max Payout = <fmt:formatNumber pattern="##.###%" value="${crt.maxPayout}"/></span>  
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
