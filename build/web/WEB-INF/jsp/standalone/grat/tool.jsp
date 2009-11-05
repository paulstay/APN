<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="teagtags" prefix="teag"%>
<%
        String path = request.getContextPath();
        request.setAttribute("path", path);
        String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
    <head>
        <base href="<%=basePath%>">

        <title>Grat Tool Information</title>

        <meta http-equiv="pragma" content="no-cache">
        <meta http-equiv="cache-control" content="no-cache">
        <meta http-equiv="expires" content="0">
        <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
        <meta http-equiv="description" content="This is my page">
        <link rel="stylesheet" type="text/css" href="${path}/css/styles.css">
        <script type="text/javascript" src="js/calendarDateInput.js">

            /***********************************************
             * Jason's Date Input Calendar- By Jason Moon http://calendar.moonscript.com/dateinput.cfm
             * Script featured on and available at http://www.dynamicdrive.com
             * Keep this notice intact for use.
             ***********************************************/

        </script>
    </head>

    <body>
        <%@include file="/inc/aesHeader.jspf"%>
        <jsp:useBean id="userInfo" scope="session" type="com.teag.bean.PdfBean" />
        <jsp:useBean id='grat' scope='session' type='com.estate.controller.GratController' />
        <h3 align='center'>
            Grantor Retained Annuity Trust
        </h3>
        <h4 align='center'>
            Tool Information for ${userInfo.firstName} ${userInfo.lastName}
        </h4>
        <table>
            <tr>
                <td><%@include file='menu.jspf'%></td>
                <td>
                    <teag:form name='tForm' action='servlet/StdGratServlet'
                method='post'>
                        <input type='hidden' name='pageView' value='tool' />
                        <input type='hidden' name='action' value='none' />
                        <table>
                            <tr>
                                <td colspan='3'>
                                    Enter the following information for calculating the Grantor
                                    Retained Annuity Trust
                                </td>
                            </tr>
                            <tr>
                                <td colspan='3'>
                                    &nbsp;
                                </td>
                            </tr>
                            <tr>
                                <td align='right'>
                                    Use Insurance :
                                </td>
                                <td>
                                    &nbsp;
                                </td>
                                <td>
                                    <c:set var="insFlag" value="F" />
                                    <c:if test="${grat.useInsurance }">
                                        <c:set var="insFlag" value="T" />
                                    </c:if>
                                    <teag:select name='useIns'
                      onChange="javascript: document.tForm.action.value='ins';document.tForm.submit()">
                                        <teag:option label="Yes" value="T" selected="${insFlag}" />
                                        <teag:option label="No" value="F" selected="${insFlag}" />
                                    </teag:select>
                                </td>
                            </tr>
                            <tr>
                                <td align='right'>
                                    Fair market Value of Assets :
                                </td>
                                <td>
                                    &nbsp;
                                </td>
                                <td>
                                    <teag:text name="fmv" fmt="number" pattern="###,###,###"
                    value="${grat.fmv }" />
                                </td>
                            </tr>
                            <tr>
                                <td align='right'>
                                    Discount Entity :
                                </td>
                                <td>
                                    &nbsp;
                                </td>
                                <td>
                                    <c:set var='lc1' value='F' />
                                    <c:if test="${grat.useLLC }">
                                        <c:set var='lc1' value='L' />
                                    </c:if>
                                    <teag:select name='llc'>
                                        <teag:option label="LLC" value="L" selected="${lc1 }" />
                                        <teag:option label="FLP" value="F" selected="${lc1 }" />
                                    </teag:select>
                                </td>
                            </tr>
                            <tr>
                                <td align='right'>
                                    Discount (From LLC or FLP) :
                                </td>
                                <td>
                                    &nbsp;
                                </td>
                                <td>
                                    <teag:text name="discount" fmt="number" pattern="##.##%"
                    value="${grat.discount}" />
                                </td>
                            </tr>
                            <tr>
                                <td align='right'>
                                    Asset Growth :
                                </td>
                                <td>
                                    &nbsp;
                                </td>
                                <td>
                                    <teag:text name='assetGrowth' fmt="number" pattern="##.###%"
                    value="${grat.assetGrowth}" />
                                </td>
                            </tr>
                            <tr>
                                <td align='right'>
                                    Asset Income :
                                </td>
                                <td>
                                    &nbsp;
                                </td>
                                <td>
                                    <teag:text name="assetIncome" fmt="number" pattern="##.###%"
                    value="${grat.assetIncome}" />
                                </td>
                            </tr>
                            <tr>
                                <td align='right'>
                                    Irs 7520 Rate :
                                </td>
                                <td>
                                    &nbsp;
                                </td>
                                <td>
                                    <teag:text name="irsRate" fmt="number" pattern="##.###%"
                    value="${grat.irsRate}" />
                                </td>
                            </tr>
                            <tr>
                                <td align='right'>
                                    Date of Transfer :
                                </td>
                                <td>
                                    &nbsp;
                                </td>
                                <td>
                                    <fmt:formatDate var='a13' pattern='M/d/yyyy'
                         value='${grat.irsDate }' />
                                    <p>
                                        <script>DateInput('irsDate', true, 'MM/DD/YYYY','${a13}')</script>
                                    </p>
                                </td>
                            </tr>
                            <tr>
                                <td align='right'>
                                    Estate Tax Rate :
                                </td>
                                <td>
                                    &nbsp;
                                </td>
                                <td>
                                    <teag:text name="estateTaxRate" fmt="number" pattern="##.###%"
                    value="${grat.estateTaxRate}" />
                                </td>
                            </tr>
                            <tr>
                                <td align='right'>
                                    Term :
                                </td>
                                <td>
                                    &nbsp;
                                </td>
                                <td>
                                    <teag:text name="term" fmt="number" pattern="###"
                    value="${grat.term }" />
                                </td>
                            </tr>
                            <tr>
                                <td align='right'>
                                    Pay at begin or end :
                                </td>
                                <td>
                                    &nbsp;
                                </td>
                                <td>
                                    <teag:select name="endBegin">
                                        <teag:option value="0" label="End" selected="${grat.endBegin}"/>
                                        <teag:option value="1" label="Begin" selected="${grat.endBegin}"/>
                                    </teag:select>
                                </td>
                            </tr>
                            <tr>
                                <td align='right'>
                                    Payment Frequency :
                                </td>
                                <td>
                                    &nbsp;
                                </td>
                                <td>
                                    <teag:select name="freq">
                                        <teag:option label="Yearly" value="1" selected="${grat.freq}" />
                                        <teag:option label="Semi-Yearly" value="2"
                       selected="${grat.freq}" />
                                        <teag:option label="Quarterly" value="4"
                       selected="${grat.freq}" />
                                        <teag:option label="Monthly" value="12"
                       selected="${grat.freq}" />
                                    </teag:select>
                                </td>
                            </tr>
                            <tr>
                                <td align='right'>
                                    Calculation Method
                                </td>
                                <td>
                                    &nbsp;
                                </td>
                                <td>
                                    <teag:select name="calculationMethod"
                      onChange="javascript: document.tForm.action.value='calc'; document.tForm.submit()" >
                                        <teag:option label="Annuity Payment" value="0"
                          selected="${grat.typeId }" />
                                        <teag:option label="Remainder Interest" value="1"
                          selected="${grat.typeId }" />
                                        <teag:option label="Zero Out Grat" value="2"
                          selected="${grat.typeId }" />
                                    </teag:select>
                                </td>
                            </tr>
                            <tr>
                                <c:choose>
                                    <c:when test="${grat.typeId == 0 }">
                                        <td align='right'>
                                            Annuity Payment
                                        </td>
                                        <td>
                                            &nbsp;
                                        </td>
                                        <td>
                                            <teag:text name="annuityPayment" fmt="number"
                      pattern="###,###,###" value="${grat.annuityPayment}" />
                                        </td>
                                    </c:when>
                                    <c:when test="${grat.typeId == 1 }">
                                        <td align='right'>
                                            Remainder Interest
                                        </td>
                                        <td>
                                            &nbsp;
                                        </td>
                                        <td>
                                            <teag:text name="remainderInterest" fmt="number"
                      pattern="###,###,###" value="${grat.remainderInterest}" />
                                        </td>
                                    </c:when>
                                    <c:otherwise>
                                        <td align='center' colspan='3'>
                                            Zero Out Grat
                                        </td>
                                    </c:otherwise>
                                </c:choose>
                            </tr>
                            <c:if test="${grat.useInsurance }">
                                <tr>
                                    <td align='right'>
                                        Life Insurance Death Benefit :
                                    </td>
                                    <td>
                                        &nbsp;
                                    </td>
                                    <td>
                                        <teag:text name="lifeDeathBenefit" fmt="number"
                     pattern="###,###,###" value="${grat.lifeDeathBenefit}" />
                                    </td>
                                </tr>
                                <tr>
                                    <td align='right'>
                                        Life Insurance Premium :
                                    </td>
                                    <td>
                                        &nbsp;
                                    </td>
                                    <td>
                                        <teag:text name="lifePremium" fmt="number"
                     pattern="###,###,###" value="${grat.lifePremium}" />
                                    </td>
                                </tr>
                                <tr>
                                    <td align='right'>
                                        Life Insurance Cash Value :
                                    </td>
                                    <td>
                                        &nbsp;
                                    </td>
                                    <td>
                                        <teag:text name="lifeCashValue" fmt="number"
                     pattern="###,###,###" value="${grat.lifeCashValue}" />
                                    </td>
                                </tr>
                            </c:if>
                            <tr>
                                <td colspan='3' align='center'>
                                    <input type="image" src="toolbtns/Update.png" alt="Update Tool"
                                           onclick="document.tForm.action.value='update'"
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
        <%@include file="/inc/aesFooter.jspf"%>
    </body>
</html>
