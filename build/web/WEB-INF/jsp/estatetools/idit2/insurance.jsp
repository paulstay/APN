<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%@ page import="com.estate.constants.*"%>
<%@ page import="com.teag.estate.*"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="teagtags" prefix="teag"%>
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

		<title>IDIT/GRAT Insurance</title>

		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<link rel="stylesheet" type="text/css" href="/teag/css/styles.css"
			media="screen" />

	</head>
	<jsp:useBean id='gv' scope='session' type='com.estate.view.GratInsuranceView' />
	<jsp:useBean id="idit" scope='session' type='com.teag.estate.IditTool'/>
	<body>
		<%@ include file="/inc/aesHeader.jspf" %>
		<h3 align='center'>
			Intentionally Defective Irrevocable Trust with Grat Combo
		</h3>
		<h3 align='center'>
			Eliminating the IDIT/GRAT Side Effects
		</h3>
		<table>
			<tr>
				<td valign='top'>
					<%@ include file="menu.jspf"%>
				</td>
				<c:if test="${idit.useInsurance}">
				<td>
					<table width='70%' align='center'>
						<tr>
							<td colspan='3' align='center'>
								<p>
								By Strategically combining the tax advantages of the IDIT/GRAT with life insurance, your plan may eliminate the side effects (see below)
								of the GRAT technique. The insurance is designed to provide you with at least 96% of the tax savings, regardless of when death occurs.
								</p>
							</td>
						</tr>
						<tr>
							<td colspan='3'>&nbsp;</td>
						</tr>
						<tr class='text-bold'>
							<td colspan='3' align='left'>How Does This Work?</td>
						</tr>
						<tr>
							<td width='60%'>Growth and Income of the Grat (<fmt:formatNumber pattern='###' value='${gv.term}'/>)</td>
							<td width='15%'>&nbsp;</td>
							<td align='right' width='25%'>
								<fmt:formatNumber pattern='$###,###,###' value='${gv.gratValue}'/>
							</td>
						</tr>
						<tr>
							<td>Estate Taxes (if death occurs to soon)</td>
							<td>&nbsp;</td>
							<td align='right'>
								<fmt:formatNumber pattern='$###,###,###' value='${gv.estateTaxes}'/>
							</td>
						</tr>
						<tr>
							<td>Life Insurance Death Benefit</td>
							<td>(a)</td>
							<td align='right'>
								<fmt:formatNumber pattern='###,###,###' value='${gv.deathBenefit }'/>
							</td>
						</tr>
						<tr>
							<td>Annual Insurance Premium</td>
							<td>&nbsp;</td>
							<td align='right'>
								<fmt:formatNumber pattern='###,###,###' value='${gv.premium}'/>
							</td>
						</tr>
						<tr>
							<td>Accumulated Cost of Insurance</td>
							<td>(b)</td>
							<td align='right'>
								<fmt:formatNumber pattern='###,###,###' value='${gv.insuranceCost}'/>
							</td>
						</tr>
						<tr>
							<td>Cash Value of Life Insurance</td>
							<td>(c)</td>
							<td align='right'>
								<fmt:formatNumber pattern='###,###,###' value='${gv.cashValue }'/>
							</td>
						</tr>
						<tr>
							<td>Cost to Preserve Tax Savings</td>
							<td>(d) = (b-c)</td>
							<td align='right'>
								<fmt:formatNumber pattern='###,###,###' value='${gv.costProtection}'/>
							</td>
						</tr>
						<tr>
							<td>Cost of Protection as a percent of Tax Savings</td>
							<td>(d)/(a)</td>
							<td align='right'>
								<fmt:formatNumber pattern='##.###%' value='${gv.costProtection/gv.deathBenefit }'/>
							</td>
						</tr>
						<tr class='text-bold'>
							<td>Percent of Tax Savings Realized</td>
							<td>&nbsp;</td>
							<td align='right'>
								<fmt:formatNumber pattern='##.###%' value='${1.0 - (gv.costProtection/gv.deathBenefit) }'/>
							</td>
						</tr>
						<tr>
							<td colspan='3'>&nbsp;</td>
						</tr>
						<tr>
							<td colspan='3'>&nbsp;</td>
						</tr>
						<tr class='text-red'>
							<td colspan='3'>Side Effects</td>
						</tr>
						<tr>
							<td>
								<p>If deaths occur before the end of the GRAT term, GRAT payments and the assets revert to the taxable
								estate - not as cash, but as continued annuity payments.
								</p>
							</td>
						</tr>
						<tr class='text-red'>
							<td colspan='3'>Solution</td>
						</tr>
						<tr>
							<td colspan='3'>
								<p>Insurance proceeds replace the tax savings if early death occur. These tax-free proceeds (when insurance is owned outside of the estate) are in addition of 
								any net of tax assets passing through the estate.
								</p>
							</td>
						</tr>
						<tr class='text-red'>
							<td colspan='3'>Summary</td>
						</tr>
						<tr>
							<td colspan='3'>
								<p>Prefessionals often refer to the Grantor Retained Annuity Trust (GRAT) as a "head you win, tails you tie" 
								scenario. However, combining the power of the GRAT with life insurance yeilds a "heads you win, tails you win" outcome for you and
								your spouse.</p>
							</td>
						</tr>
					</table>
				</td>
				</c:if>
			</tr>
		</table>
		<%@ include file="/inc/aesFooter.jspf" %>
	</body>
</html>
