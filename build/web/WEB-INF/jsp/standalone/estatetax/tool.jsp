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

        <title>Estate Tax Calculator</title>

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
            Estate Tax Calculator
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
                                    Enter the following information for calculating the Estate Tax
                                </td>
                            </tr>
                            <tr>
                                <td colspan='3'>
                                    &nbsp;
                                </td>
                            </tr>
                            <tr>
                                <td align='right'>
                                    Year of Death
                                </td>
                                <td>
                                    &nbsp;
                                </td>
                                <td>
                                    <teag:text name="yearOfDeath" fmt="number" pattern="####"
                                               value="${}" />
                                </td>
                            </tr>
                            <tr>
                                <td align='right'>
                                    Net Estate
                                </td>
                                <td>
                                    &nbsp;
                                </td>
                                <td>
                                    <teag:text name="netEstate" fmt="number" pattern="###,###,###"
                                               value="${}" />
                                </td>
                            </tr>
                            <tr>
                                <td align='right'>
                                    Administrative Expenses
                                </td>
                                <td>
                                    &nbsp;
                                </td>
                                <td>
                                    <teag:text name="adminExpenses" fmt="number" pattern="###,###,###"
                                               value="${}" />
                                </td>
                            </tr>
                            <tr>
                                <td align='right'>
                                     Marital Deduction
                                </td>
                                <td>
                                    &nbsp;
                                </td>
                                <td>
                                    <teag:text name="maritalDeduction" fmt="number"
                                               pattern="###,###,###" value="${}" />
                                </td>
                            </tr>
                            <tr>
                                <td align='right'>
                                    Charitable Deduction
                                </td>
                                <td>
                                    &nbsp;
                                </td>
                                <td>
                                    <teag:text name="charitableDeduction" fmt="number"
                                               pattern="###,###,###" value="${}" />
                                </td>
                            </tr>
                            <tr>
                                <td align='right'>
                                    Family Owned Business Interest Deduction
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
                                    <teag:text name="fobiDeduction" fmt="number"
                                               pattern="###,###,###" value="${}" />
                                </td>
                            </tr>
                            <tr>
                                <td align='right'>
                                    Post 76 Taxable Gifts
                                    <span style='color: red;'>*</span>
                                </td>
                                <td>
                                    &nbsp;
                                </td>
                                <td>
                                    <teag:text name="taxableGifts" fmt="number" pattern="###,###,###"
                                               value="${}" />
                                    <span style='color: red;'>${errorPayout}</span>
                                </td>
                            </tr>
                            <tr>
                                <td align='right'>
                                    Gift Tax Paid (Form 706)
                                </td>
                                <td>
                                    &nbsp;
                                </td>
                                <td>
                                    <teag:text name="giftTaxPaid" fmt="number" pattern="###,###,###"
                                               value="${}" />
                                </td>
                            </tr>
                            <tr>
                                <td align='right'>
                                    Tax Law to Apply
                                </td>
                                <td>
                                    &nbsp;
                                </td>
                                <td>
                                    <teag:select name='taxLaw'>
                                        <teag:option label='Current' value='0'/>
                                        <teag:option label="Freeze 2009" value="3"/>
                                    </teag:select>
                                </td>
                            </tr>
                            <tr>
                                <td align='right'>
                                    Number of years for comparison
                                </td>
                                <td>
                                    &nbsp;
                                </td>
                                <td>
                                    <teag:text name='yearsProjection' fmt="number" pattern="###"
                                               value='${}' />
                                </td>
                            </tr>
                            <tr>
                                <td align='right'>
                                     Growth Rate of Estate
                                </td>
                                <td>
                                    &nbsp;
                                </td>
                                <td>
                                    <teag:text name="growthRate" fmt="number" pattern="##.###%"
                                               value="${}" />
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
