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

		<title>Liquid Asset Protection Tool Summary</title>

		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<link rel="stylesheet" type="text/css" href="<%=path%>/css/styles.css"
			media="screen" />
	</head>
	<jsp:useBean id='lap' scope='session'
		type='com.teag.estate.LiquidAssetProtectionTool' />
	<body>
		<%@include file="/inc/aesHeader.jspf"%>
		<h3 align='center'>
			Liquid Asset Protection Tool
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
					<table width='80%' align='left' frame='box'>
						<tr>
							<td align='center' colspan='4'>
								<h4>
									Assumptions
								</h4>
							</td>
						</tr>
						<tr>
							<td align='left'>
								Value of Liquid Assets
							</td>
							<td align='right'>
								<fmt:formatNumber type="currency" value="${lap.amount}" />
							</td>
							<td>
								&nbsp;
							</td>
							<td align='left'>
								Annuity Monthly Payment
							</td>
							<td align='right'>
								<fmt:formatNumber type="currency"
									value="${lap.annuityMonthlyIncome}" />
							</td>
						</tr>
						<tr>
							<td align='left'>
								Earning rate of Liquid Assets
							</td>
							<td align='right'>
								<fmt:formatNumber type="percent" value="${lap.income}" />
							</td>
							<td>
								&nbsp;
							</td>
							<td align='left'>
								Life Insurance Face Value
							</td>
							<td align='right'>
								<fmt:formatNumber type="currency" value="${lap.lifeFaceValue}" />
							</td>
						</tr>

						<tr>
							<td align='left'>
								Current Income Tax Rate
							</td>
							<td align='right'>
								<fmt:formatNumber type="percent" value="${lap.incomeTaxRate}" />
							</td>
							<td>
								&nbsp;
							</td>
							<td align='left'>
								Life Insurance Premium (yearly)
							</td>
							<td align='right'>
								<fmt:formatNumber type="currency" value="${lap.lifePremium}" />
							</td>
						</tr>
						<tr>
							<td align='left'>
								Estatimated Estate Tax Rate
							</td>
							<td align='right'>
								<fmt:formatNumber type="percent" value="${lap.estateTaxRate}" />
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
							<td align='left'>
								Annuity Exclusion Ratio
							</td>
							<td align='right'>
								<fmt:formatNumber type="percent"
									value="${lap.annuityExclusionRatio}" />
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
							<td colspan='5'>
								&nbsp;
							</td>
						</tr>
						<tr>
							<td colspan='5'>
								&nbsp;
							</td>
						</tr>
					</table>
					<br>
					<br>
					<table align='left' width='80%'>
						<tr>
							<td colspan='2'>
								<b>Income Analysis:</b>
							</td>
							<td align='right' class='text-red'>
								Results of
								<fmt:formatNumber type="percent" value="${lap.income}" />
								Assets
							</td>
							<td align='right' class='text-green'>
								Results of LAP Plan
							</td>
						</tr>
						<tr>
							<td>
								&nbsp;&nbsp;&nbsp;
							</td>
							<td>
								Annual Income from Liquid Assets
							</td>
							<td align='right'>
								<fmt:formatNumber pattern="$###,###,###"
									value="${lap.amount * lap.income }" />
							</td>
							<td align='right'>
								<fmt:formatNumber pattern="$###,###,###"
									value="${lap.annualAnnuity }" type="currency" />
							</td>
						</tr>
						<tr>
							<td>
								&nbsp;&nbsp;&nbsp;
							</td>
							<td>
								Less Income Tax Due
							</td>
							<td align='right'>
								<fmt:formatNumber pattern="$###,###,###"
									value="${-(lap.amount * lap.income) * lap.incomeTaxRate }"
									type="currency" />
							</td>
							<td align='right'>
								<fmt:formatNumber pattern="$###,###,###" value="${lap.taxDue }"
									type="currency" />
							</td>
						</tr>
						<tr>
							<td>
								&nbsp;&nbsp;&nbsp;
							</td>
							<td>
								Net After Tax Income
							</td>
							<td align='right' class='text-bold'>
								<fmt:formatNumber pattern="$###,###,###"
									value="${ (lap.amount*lap.income) -(lap.amount * lap.income) * lap.incomeTaxRate }"
									type="currency" />
							</td>
							<td align='right' class='text-bold'>
								<fmt:formatNumber pattern="$###,###,###"
									value="${lap.annualAnnuity + lap.taxDue }" type="currency" />
							</td>
						</tr>
						<tr>
							<td>
								&nbsp;&nbsp;&nbsp;
							</td>
							<td>
								Less Insurance Premium
							</td>
							<td align='right'>
								<fmt:formatNumber pattern="$###,###,###" value="0" />
							</td>
							<td align='right'>
								<fmt:formatNumber pattern="$###,###,###"
									value="${-lap.lifePremium }" type="currency" />
							</td>
						</tr>
						<tr>
							<td>
								&nbsp;&nbsp;&nbsp;
							</td>
							<td>
								Net Spendable Income
							</td>
							<td align='right' class='text-red'>
								<fmt:formatNumber pattern="$###,###,###"
									value="${ (lap.amount*lap.income) -(lap.amount * lap.income) * lap.incomeTaxRate}" />
							</td>
							<td align='right' class='text-green'>
								<fmt:formatNumber pattern="$###,###,###"
									value="${lap.annualAnnuity + lap.taxDue -lap.lifePremium }"
									type="currency" />
							</td>
						</tr>
						<tr>
							<td>
								&nbsp;&nbsp;&nbsp;
							</td>
							<td class='text-green'>
								% of Asset Income
							</td>
							<td align='right' class='text-red'>
								&nbsp;
							</td>
							<td align='right' class='text-green'>
								<fmt:formatNumber type="percent"
									value="${(lap.annualAnnuity + lap.taxDue -lap.lifePremium)/((lap.amount*lap.income) -(lap.amount * lap.income) * lap.incomeTaxRate) }" />
							</td>
						</tr>
						<tr>
							<td colspan='4'>
								&nbsp;&nbsp;
							</td>
						</tr>
						<tr>
							<td colspan='4'>
								<b>Inheritance Analysis:</b>
							</td>
						</tr>
						<tr>
							<td>
								&nbsp;&nbsp;&nbsp;
							</td>
							<td>
								Liquid Asset Value
							</td>
							<td align='right'>
								<fmt:formatNumber pattern="$###,###,###" value="${lap.amount }"
									type="currency" />
							</td>
							<td align='right'>
								<fmt:formatNumber pattern="$###,###,###"
									value="${lap.lifeFaceValue }" type="currency" />
							</td>
						</tr>

						<tr>
							<td>
								&nbsp;&nbsp;&nbsp;
							</td>
							<td class='text-red'>
								ESTATE TAX DUE
							</td>
							<td align='right' class='text-red'>
								<fmt:formatNumber pattern="$###,###,###"
									value="${lap.amount * lap.estateTaxRate }" type="currency" />
							</td>
							<td align='right' class='text-red'>
								<fmt:formatNumber pattern="$###,###,###" value="${0}"
									type="currency" />
							</td>
						</tr>

						<tr>
							<td>
								&nbsp;&nbsp;&nbsp;
							</td>
							<td>
								Net To Family
							</td>
							<td align='right'>
								<fmt:formatNumber pattern="$###,###,###"
									value="${lap.amount - (lap.amount * lap.estateTaxRate) }"
									type="currency" />
							</td>
							<td align='right' class='text-green'>
								<fmt:formatNumber pattern="$###,###,###"
									value="${lap.lifeFaceValue }" type="currency" />
							</td>
						</tr>

						<tr>
							<td>
								&nbsp;&nbsp;&nbsp;
							</td>
							<td class='text-green'>
								% Increase to Family
							</td>
							<td align='right'>
								&nbsp;
							</td>
							<td align='right' class='text-green'>
								<fmt:formatNumber
									value="${lap.lifeFaceValue/(lap.amount - (lap.amount * lap.estateTaxRate)) }"
									type="percent" />
							</td>
						</tr>
						<tr>
							<td colspan='4'>
								&nbsp;
							</td>
						</tr>
						<tr>
							<td colspan='4'>
								<h4>
									Notes:
								</h4>
								<ol>
									<li>
										This illustration compares the net spendable cash flow and the
										net inheritance value of assets held in a fixed income
										portfolio earning
										<fmt:formatNumber type="percent" value="${lap.income }" />
										with the same amount of assets repositioned into the Liquid
										Asset Protection Plan (LAP Plan). The purpose of the LAP Plan
										is to protect liquid assets from the Federal estate Tax while
										providing continuing income within the estate.
									</li>
									<li>
										The LAP Plan strategy utilizes a guaranteed income annuity and
										a guaranteed lapse protected life insurance policy. Premiums
										for the life insurance policy are paid annually.
									</li>
									<li>
										The LAP Plan assumes that from the
										<fmt:formatNumber pattern="$###,###,###">${lap.amount}</fmt:formatNumber>
										, the intial annual life insurance premium of
										<fmt:formatNumber pattern="$###,###,###">${lap.lifePremium}</fmt:formatNumber>
										is paid, leaving
										<fmt:formatNumber pattern="$###,###,###">${lap.amount - lap.lifePremium}</fmt:formatNumber>
										to purchase a guaranteed income annuity. The cash flow from
										the annuity begins one month after the purchase and continues
										for life. Thus, the second and all future premiums for the
										life insurance policy are funded from the annuity cash flow.
									</li>
									<li>
										During the first
										<fmt:formatNumber pattern="###.#">${lap.taxBasisRecovered}</fmt:formatNumber>
										years of the LAP Plan, cash flow from the annuity will be
										roughly
										<fmt:formatNumber type="percent">${lap.annuityExclusionRatio}</fmt:formatNumber>
										tax free. Thereafter, all cash flow is 100% taxable.&nbsp;
										<c:if test="${lap.overFunded }">
								When that happens, the premiums from the life insurance policy
								will be reduced to absorb the amount of tax increase.
							</c:if>
									</li>
									<li>
									The life insurance policy is presumed to be owned outside the
									taxable estate.
									</li>
								</ol>
							</td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
		<%@include file="/inc/aesFooter.jspf"%>
	</body>
</html>
