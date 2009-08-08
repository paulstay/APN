<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%@ page import="com.estate.constants.*"%>
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

		<title>User Information</title>

		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<link rel="stylesheet" type="text/css" href="<%=path%>/css/styles.css">
	</head>
	<jsp:useBean id='userInfo' scope='session'
		class='com.teag.bean.PdfBean' />
	<jsp:useBean id='crt' scope='session'
		class='com.estate.toolbox.CRT' />
	<body>
		<%@include file="/inc/aesHeader.jspf" %>
		<h3 align='center'>
			Charitable Remainder Trust
		</h3>
		<h4 align='center'>
			Tool Information for ${userInfo.firstName} ${userInfo.lastName}
		</h4>
		<c:if test="${param.pageView == 'Update'}">
			<fmt:parseNumber var='b1' pattern='###,###,###' value='${param.fmv }'/>
			<jsp:setProperty name='crt' property='fmv' value='${b1 }'/>
			<fmt:parseNumber var='b2' pattern='###,###,###' value='${param.liability }'/>
			<jsp:setProperty name='crt' property='liability' value='${b2 }'/>
			<fmt:parseNumber var='b3' pattern='###,###,###' value='${param.basis }'/>
			<jsp:setProperty name='crt' property='basis' value='${b3 }'/>
			<teag:parsePercent var='b4' value='${param.investmentReturn }'/>
			<jsp:setProperty name='crt' property='investmentReturn' value='${b4 }'/>
			<teag:parsePercent var='b4' value='${param.marginalTaxRate }'/>
			<jsp:setProperty name='crt' property='marginalTaxRate' value='${b4 }'/>
			<teag:parsePercent var='b5' value='${param.estateTaxRate}'/>
			<jsp:setProperty name='crt' property='estateTaxRate' value='${b5 }'/>
			<teag:parsePercent var='b6' value='${param.capitalGainsTax}'/>
			<jsp:setProperty name='crt' property='capitalGainsTax' value='${b6 }'/>
			<teag:parsePercent var='b7' value='${param.payoutRate}'/>
			<jsp:setProperty name='crt' property='payoutRate' value='${b7 }'/>
			<teag:parsePercent var='b8' value='${param.taxIncome}'/>
			<jsp:setProperty name='crt' property='taxIncome' value='${b8 }'/>
			<teag:parsePercent var='b9' value='${param.taxGrowth}'/>
			<jsp:setProperty name='crt' property='taxGrowth' value='${b9 }'/>
			<fmt:parseNumber var='b10' pattern='###,###,###' value='${param.agi }'/>
			<jsp:setProperty name='crt' property='agi' value='${b10 }'/>
			<teag:parsePercent var='b11' value='${param.irsRate }'/>
			<jsp:setProperty name='crt' property='irsRate' value='${b11 }'/>
			<fmt:parseDate var='b12' pattern='M/d/yyyy' value='${param.irsDate }'/>
			<jsp:setProperty name='crt' property='irsDate' value='${b12 }'/>
			<fmt:parseNumber var='b13' pattern='###,###,###' value='${param.insurancePremium }'/>
			<jsp:setProperty name='crt' property='insurancePremium' value='${b13 }'/>
			<fmt:parseNumber var='b14' pattern='###,###,###' value='${param.insuranceBenefit }'/>
			<jsp:setProperty name='crt' property='insuranceBenefit' value='${b14 }'/>
			<jsp:setProperty name='crt' property='finalDeath' param='finalDeath'/>
			<jsp:setProperty name='crt' property='payoutOption' param='payoutOption'/>
			<jsp:setProperty name='crt' property='crtType' param='crtType'/>
		</c:if>
		<table>
			<tr>
				<td><%@include file='menu.jsp'%></td>
				<td>
					<input:form name='tForm' action='toolbox/crt/tool.jsp'
						method='post'>
						<input type='hidden' name='pageView' value='noUpdate' />
						<table>
							<tr>
								<td colspan='3'>
									Enter the following information for calculating the 
									Charitable Reamainder Trust
								</td>
							</tr>
							<tr>
								<td colspan='3'>
									&nbsp;
								</td>
							</tr>
							<tr>
								<td align='right'>Fair Market Value</td>
								<td>&nbsp;</td>
								<td>
									<fmt:formatNumber var='a1' value='${crt.fmv }'/>
									<input:text name='fmv' default='${a1 }'/>
								</td>
							</tr>
							<tr>
								<td align='right'>Liability</td>
								<td>&nbsp;</td>
								<td>
									<fmt:formatNumber var='a2' value='${crt.liability }'/>
									<input:text name='liability' default='${a2 }'/>
								</td>
							</tr>
							<tr>
								<td align='right'>Basis</td>
								<td>&nbsp;</td>
								<td>
									<fmt:formatNumber var='a3' value='${crt.basis }'/>
									<input:text name='basis' default='${a3 }'/>
								</td>
							</tr>
							<tr>
								<td align='right'>Payout Option for Outright Sale</td>
								<td>&nbsp;</td>
								<td>
									<select name='payoutOption'>
										<option value='P' <%=crt.getPayoutOption().equals("P") ? "selected='selected'" : "" %>>Percentage</option>
										<option value='M' <%=crt.getPayoutOption().equals("M") ? "selected='selected'" : "" %>>Match CRT</option>
										<option value='L' <%=crt.getPayoutOption().equals("L") ? "selected='selected'" : "" %>>Level</option>
									</select>
								</td>
							</tr>
							<tr>
								<td align='right'>Investment Return</td>
								<td>&nbsp;</td>
								<td>
									<fmt:formatNumber var='a4' pattern='##.###%' value='${crt.investmentReturn }'/>
									<input:text name='investmentReturn' default='${a4 }'/>
								</td>
							</tr>
							<tr>
								<td align='right'>Marginal Income Tax Rate</td>
								<td>&nbsp;</td>
								<td>
									<fmt:formatNumber var='a5' pattern='##.####%' value='${crt.marginalTaxRate }'/>
									<input:text name='marginalTaxRate' default='${a5 }'/>
								</td>
							</tr>
							<tr>
								<td align='right'>Estate Tax Rate</td>
								<td>&nbsp;</td>
								<td>
									<fmt:formatNumber var='a6' pattern='##.####%' value='${crt.estateTaxRate }'/>
									<input:text name='estateTaxRate' default='${a6 }'/>
								</td>
							</tr>
							<tr>
								<td align='right'>Capital Gains Rate</td>
								<td>&nbsp;</td>
								<td>
									<fmt:formatNumber var='a7' pattern='##.###%' value='${crt.capitalGainsTax }'/>
									<input:text name='capitalGainsTax' default='${a7 }'/>
								</td>
							</tr>
							<tr>
								<td align='right'>Payout Rate</td>
								<td>&nbsp;</td>
								<td>
									<fmt:formatNumber var='a8' pattern='##.###%' value='${crt.payoutRate }'/>
									<input:text name='payoutRate' default='${a8 }'/>
								</td>
							</tr>
							<tr>
								<td align='right'>Percent of Payout taxed as Income</td>
								<td>&nbsp;</td>
								<td>
									<fmt:formatNumber var='a9' pattern='##.###%' value='${crt.taxIncome }'/>
									<input:text name='taxIncome' default='${a9 }'/>
								</td>
							</tr>
							<tr>
								<td align='right'>Percent of Payout taxed as Growth</td>
								<td>&nbsp;</td>
								<td>
									<fmt:formatNumber var='a10' pattern='##.###%' value='${crt.taxGrowth }'/>
									<input:text name='taxGrowth' default='${a10 }'/>
								</td>
							</tr>
							<tr>
								<td align='right'>Adjusted Gross Income</td>
								<td>&nbsp;</td>
								<td>
									<fmt:formatNumber var='a11' pattern='###,###,###' value='${crt.agi}'/>
									<input:text name='agi' default='${a11 }'/>
								</td>
							</tr>
							<tr>
								<td align='right'>IRS 7520 Rate</td>
								<td>&nbsp;</td>
								<td>
									<fmt:formatNumber var='a12' pattern='##.####%' value='${crt.irsRate }'/>
									<input:text name='irsRate' default='${a12 }'/>
								</td>
							</tr>
							<tr>
								<td align='right'>IRS 7520 Transfer Date</td>
								<td>&nbsp;</td>
								<td>
									<fmt:formatDate var='a13' pattern='M/d/yyyy' value='${crt.irsDate }'/>
									<input:text name='irsDate' default='${a13 }'/>
								</td>
							</tr>
							<tr>
								<td align='right'>Life Insurance Premium (yearly)</td>
								<td>&nbsp;</td>
								<td>
									<fmt:formatNumber var='a14' pattern='###,###,###' value='${crt.insurancePremium }'/>
									<input:text name='insurancePremium' default='${a14 }'/>
								</td>
							</tr>
							<tr>
								<td align='right'>Life Insurance Death Benefit</td>
								<td>&nbsp;</td>
								<td>
									<fmt:formatNumber var='a15' pattern='###,###,###' value='${crt.insuranceBenefit }'/>
									<input:text name='insuranceBenefit' default='${a15 }'/>
								</td>
							</tr>
							<tr>
								<td align='right'>Assumed Date of Second Death</td>
								<td>&nbsp;</td>
								<td>
									<fmt:formatNumber var='a13' pattern='###,###,###' value='${crt.finalDeath }'/>
									<input:text name='finalDeath' default='${a13 }'/>
								</td>
							</tr>
							<tr>
								<td colspan='3' align='center'>
									<input type="image" src="toolbtns/Update.png" alt="Update Tool"
										onclick="document.tForm.pageView.value='Update'"
										onmouseover="this.src='toolbtns/Update_over.png';"
										onmouseout="this.src='toolbtns/Update.png';"
										onmousedown="this.src='toolbtns/Update_down.png';" />
								</td>
							</tr>
						</table>
					</input:form>
				</td>
			</tr>
		</table>
		<%@include file="/inc/aesFooter.jspf" %>
	</body>
</html>
