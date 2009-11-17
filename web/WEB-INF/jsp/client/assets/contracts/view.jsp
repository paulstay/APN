<%@ page language="java" import="com.teag.bean.*" pageEncoding="ISO-8859-1"%>
<%@ page import="com.estate.constants.*" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="teagtags" prefix="teag"%>
<%
            String path = request.getContextPath();
            String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<! DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
    <base href="<%=basePath%>">
    <title>Select Client</title>
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <link href="<%=path%>/css/styles.css" rel="stylesheet" media="screen">
    <body>
        <jsp:useBean id="biz" scope="request" type="com.teag.bean.BizContractBean" />
        <%@ include file="/inc/aesHeader.jspf" %>
        <h3 align='center'> Business Contract Details</h3>
        <br>
        <table align='center' width='70%'>
            <tr>
                <td align="right">Description : </td>
                <td align="left">${biz.description}</td>
            </tr>
            <tr>
                <td align="right">Value : </td>
                <td align="left"><fmt:formatNumber value="${biz.value }" type="currency"/></td>
            </tr>
            <tr>
                <td align="right">Salary : </td>
                <td align="left"><fmt:formatNumber value="${biz.salary }" pattern="###,###,###.##" type="currency"/></td>
            </tr>
            <tr>
                <td align="right">Starting Year : </td>
                <td align="left">${biz.startYear }</td>
            </tr>
            <tr>
                <td align="right">Ending Year : </td>
                <td align="left">${biz.endYear }</td>
            </tr>
            <tr>
                <td align="right">Liquidate at end of Contract</td>
                <c:if test="${biz.cashFlow}">
                    <td align="left">YES</td>
                </c:if>
                <c:if test="${!biz.cashFlow}">
                    <td align="left">NO</td>
                </c:if>
            </tr>

            <tr>
                <td align='right'>
                    <teag:form name='bform' action="servlet/BizContractServlet" method="post">
                        <input type="hidden" name="action" value="edit"/>
                        <input type='submit' value="Back"/>
                    </teag:form>
                </td>
                <td align='left'>
                    <teag:form name='eform' action="servlet/BizContractServlet" method="post">
                        <input type="hidden" name="id" value="${biz.id}"/>
                        <input type="hidden" name="action" value="edit"/>
                        <input type='submit' value="Edit"/>
                    </teag:form>
                </td>
            </tr>
        </table>
        <%@ include file="/inc/aesFooter.jspf" %>
    </body>
</html>
