<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%@ page import="com.estate.constants.*"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="teagtags" prefix="teag"%>
<%
        String path = request.getContextPath();
        String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
    <head>
        <base href="<%=basePath%>">

        <title>CRT Tool Information</title>

        <meta http-equiv="pragma" content="no-cache">
        <meta http-equiv="cache-control" content="no-cache">
        <meta http-equiv="expires" content="0">
        <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
        <meta http-equiv="description" content="This is my page">
        <link rel="stylesheet" type="text/css" href="<%=path%>/css/styles.css">
        <style type="text/css">
        </style>
    </head>
    <jsp:useBean id='userInfo' scope='session'
                 class='com.teag.bean.PdfBean' />
    <jsp:useBean id='sCrt' scope='session' type='com.estate.toolbox.CRT' />
    <body>
        <%@include file="/inc/aesHeader.jspf"%>
        <h3 align='center'>
            Charitable Remainder Trust
        </h3>
        <h4 align='center'>
            Tool Information for ${userInfo.firstName} ${userInfo.lastName}
        </h4>
        <table>
            <tr>
                <td valign='top'><%@include file='menu.jspf'%></td>
                <td align='left'>
                    <teag:form name='tForm' action='servlet/StdCrtServlet'
                               method='post'>
                        <input type='hidden' name='pageView' value='tool' />
                        <input type="hidden" name='action' value='none' />
                        <table align='left' width='70%'>
                            <tr>
                                <td colspan='3'>
                                    Enter the following information for calculating the Charitable
                                    Reamainder Trust
                                </td>
                            </tr>
                            <tr>
                                <td colspan='3'>
                                    &nbsp;
                                </td>
                            </tr>
                            <tr>
                                <td align='right'>
                                    Fair Market Value
                                </td>
                                <td>
                                    &nbsp;
                                </td>
                                <td>
                                    <teag:text name="fmv" fmt="number" pattern="###,###,###"
                                               value="${sCrt.fmv}" />
                                </td>
                            </tr>
                            <tr>
                                <td align='right'>
                                    Liability
                                </td>
                                <td>
                                    &nbsp;
                                </td>
                                <td>
                                    <teag:text name="liability" fmt="number" pattern="###,###,###"
                                               value="${sCrt.liability}" />
                                </td>
                            </tr>
                            <tr>
                                <td align='right'>
                                    Basis
                                </td>
                                <td>
                                    &nbsp;
                                </td>
                                <td>
                                    <teag:text name="basis" fmt="number" pattern="###,###,###"
                                               value="${sCrt.basis}" />
                                </td>
                            </tr>
                            <tr>
                                <td align='right'>
                                    Investment Return
                                </td>
                                <td>
                                    &nbsp;
                                </td>
                                <td>
                                    <teag:text name="investmentReturn" fmt="number"
                                               pattern="##.###%" value="${sCrt.investmentReturn}" />
                                </td>
                            </tr>
                            <tr>
                                <td align='right'>
                                    Marginal Income Tax Rate
                                </td>
                                <td>
                                    &nbsp;
                                </td>
                                <td>
                                    <teag:text name="marginalTaxRate" fmt="number"
                                               pattern="##.###%" value="${sCrt.marginalTaxRate}" />
                                </td>
                            </tr>
                            <tr>
                                <td align='right'>
                                    Estate Tax Rate
                                </td>
                                <td>
                                    &nbsp;
                                </td>
                                <td>
                                    <teag:text name="estateTaxRate" fmt="number" pattern="##.###%"
                                               value="${sCrt.estateTaxRate}" />
                                </td>
                            </tr>
                            <tr>
                                <td align='right'>
                                    Capital Gains Rate
                                </td>
                                <td>
                                    &nbsp;
                                </td>
                                <td>
                                    <teag:text name="capitalGainsTax" fmt="number"
                                               pattern="##.###%" value="${sCrt.capitalGainsTax}" />
                                </td>
                            </tr>
                            <tr>
                                <td align='right'>
                                    Payout Rate
                                    <span style='color: red;'>*</span>
                                </td>
                                <td>
                                    &nbsp;
                                </td>
                                <td>
                                    <teag:text name="payoutRate" fmt="number" pattern="##.###%"
                                               value="${sCrt.payoutRate}" />
                                    <span style='color: red;'>${errorPayout}</span>
                                </td>
                            </tr>
                            <tr>
                                <td align='right'>
                                    Percent of Payout taxed as Income
                                </td>
                                <td>
                                    &nbsp;
                                </td>
                                <td>
                                    <teag:text name="taxIncome" fmt="number" pattern="##.###%"
                                               value="${sCrt.taxIncome}" />
                                </td>
                            </tr>
                            <tr>
                                <td align='right'>
                                    Percent of Payout taxed as Capital Gains
                                </td>
                                <td>
                                    &nbsp;
                                </td>
                                <td>
                                    <teag:text name="taxGrowth" fmt="number" pattern="##.###%"
                                               value="${sCrt.taxGrowth}" />
                                </td>
                            </tr>
                            <tr>
                                <td align='right'>
                                    Adjusted Gross Income
                                </td>
                                <td>
                                    &nbsp;
                                </td>
                                <td>
                                    <teag:text name="agi" fmt="number" pattern="###,###,###"
                                               value="${sCrt.agi}" />
                                </td>
                            </tr>
                            <tr>
                                <td align='right'>
                                    Client Age
                                </td>
                                <td>
                                    &nbsp;
                                </td>
                                <td>
                                    <teag:text name='clientAge' fmt="number" pattern="###"
                                               value='${sCrt.clientAge}' />
                                </td>
                            </tr>
                            <tr>
                                <td align='right'>
                                    Spouse Age
                                </td>
                                <td>
                                    &nbsp;
                                </td>
                                <td>
                                    <teag:text name='spouseAge' fmt="number" pattern="###"
                                               value='${sCrt.spouseAge}' />
                                </td>
                            </tr>
                            <tr>
                                <td align='right'>
                                    Payment Period
                                </td>
                                <td>
                                    &nbsp;
                                </td>
                                <td>
                                    <teag:select name="pmtPeriod">
                                        <teag:option label="Yearly" value="1"
                                                     selected="${sCrt.pmtPeriod}" />
                                        <teag:option label="Semi Annual" value="2"
                                                     selected="${sCrt.pmtPeriod}" />
                                        <teag:option label="Quarterly" value="3"
                                                     selected="${sCrt.pmtPeriod}" />
                                        <teag:option label="Monthly" value="4"
                                                     selected="${sCrt.pmtPeriod}" />
                                    </teag:select>
                                </td>
                            </tr>
                            <tr>
                                <td align='right'>
                                    UniTrust lag in Months
                                </td>
                                <td>
                                    &nbsp;
                                </td>
                                <td>
                                    <teag:text name='uniLag' fmt="number" pattern="###"
                                               value='${sCrt.uniLag }' />
                                </td>
                            </tr>
                            <tr>
                                <td align='right'>
                                    IRS 7520 Rate
                                </td>
                                <td>
                                    &nbsp;
                                </td>
                                <td>
                                    <teag:text name="irsRate" fmt="number" pattern="##.###%"
                                               value="${sCrt.irsRate}" />
                                </td>
                            </tr>
                            <tr>
                                <td align='right'>
                                    IRS 7520 Transfer Date (click to change)
                                </td>
                                <td>
                                    &nbsp;
                                </td>
                                <td>
                                    <fmt:formatDate var='a13' pattern='M/d/yyyy'
                                                    value='${sCrt.irsDate }' />
                                    <p>
                                        <script>DateInput('irsDate', true, 'MM/DD/YYYY','${a13}')</script>
                                    </p>
                                </td>
                            </tr>
                            <tr>
                                <td align='right'>
                                    Life Insurance Premium (yearly)
                                </td>
                                <td>
                                    &nbsp;
                                </td>
                                <td>
                                    <teag:text name="insurancePremium" fmt="number"
                                               pattern="###,###,###" value="${sCrt.insurancePremium}" />
                                </td>
                            </tr>
                            <tr>
                                <td align='right'>
                                    Life Insurance Death Benefit
                                </td>
                                <td>
                                    &nbsp;
                                </td>
                                <td>
                                    <teag:text name="insuranceBenefit" fmt="number"
                                               pattern="###,###,###" value="${sCrt.insuranceBenefit}" />
                                </td>
                            </tr>
                            <tr>
                                <td align='right'>
                                    #Years for Asset Comparison
                                </td>
                                <td>
                                    &nbsp;
                                </td>
                                <td>
                                    <teag:text name="finalDeath" fmt="number" pattern="###"
                                               value="${sCrt.finalDeath}" />
                                </td>
                            </tr>
                            <tr>
                                <td colspan='3' align='center'>
                                    <input type="image" src="toolbtns/Update.png" alt="Update Tool"
                                           onclick="document.tForm.action.value='update';"
                                           onmouseover="this.src='toolbtns/Update_over.png';"
                                           onmouseout="this.src='toolbtns/Update.png';"
                                           onmousedown="this.src='toolbtns/Update_down.png';"
                                           />
                                </td>
                            </tr>
                            <tr>
                                <td colspan='3' align='right'>
                                <span style='color: red;'> Based on the entered IRS
                                    section 7520 rate, the age of the client and spouse, the
                                    maximum rate that the CRT can payout is <fmt:formatNumber
                                        pattern="##.###%" value="${sCrt.maxPayout}" /> , the minimum
                                    payout is 5%. If you enter a value outside of this range, the
                                tool will adjust the payout to the minimum or maximum value. </span>
                            </tr>
                        </table>
                    </teag:form>
                </td>
            </tr>
        </table>
        <%@include file="/inc/aesFooter.jspf"%>
    </body>
</html>
