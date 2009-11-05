<%@ page language="java" import="com.teag.bean.*" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="teagtags" prefix="teag" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<! DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
    <base href="<%=basePath%>">
    <title>Select Client</title>
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <link href="css/style.css" rel="stylesheet" media="screen">
    <body>
        <jsp:useBean id="illiquid" scope="request" type="com.teag.bean.IlliquidBean" />
        <%@ include file="/inc/aesHeader.jspf" %>
        <h3 align='center'> Illiquid Asset Details</h3>
        <br>
        <table align='center' width='70%'>
            <tr>
                <td align="right">Description : </td>
                <td align="left">${illiquid.description}</td>
            </tr>
            <tr>
                <td align="right">Ownership : </td>
                <td align="left"><teag:ownership value="${illiquid.ownershipId}"/></td>
            </tr>
            <tr>
                <td align="right">Title : </td>
                <td align="left"><teag:title value="${illiquid.titleId }" /></td>
            </tr>
            <tr>
                <td align="right">Amount : </td>
                <td align="left"><fmt:formatNumber value="${illiquid.value }" type="currency"/></td>
            </tr>
            <tr>
                <td align="right">Basis : </td>
                <td align="left"><fmt:formatNumber value="${illiquid.basis}" pattern="###,###,###" /></td>
            </tr>
            <tr>
                <td align="right">Income Rate : </td>
                <td align="left"><fmt:formatNumber value="${illiquid.divInt}" pattern="##.####%"  /></td>
            </tr>
            <tr>
                <td align="right">Growth : </td>
                <td align="left"><fmt:formatNumber value="${illiquid.growthRate}" type='percent'  /></td>
            </tr>
            <tr>
                <td align="right">Anticipated Liquidation Date</td>
                <c:choose>
                    <c:when test="${illiquid.ald != null }">
                        <td align="left">${illiquid.ald}</td>
                    </c:when>
                    <c:otherwise>
                        <td align="left"></td>
                    </c:otherwise>
                </c:choose>

            </tr>
            <tr>
                <td align="right">Notes : </td>
                <td align="left">${illiquid.notes }"</td>
            </tr>
            <tr>
                <td align='right'>
                    <teag:form name="bform" action="servlet/IlliquidAssetServlet" method="post">
                        <input type="hidden" name="action" value="list"/>
                        <input type='submit' value="Back"/>
                    </teag:form>
                </td>
                <td align='left'>
                    <teag:form name="eform" action="servlet/IlliquidAssetServlet" method="post">
                        <input type="hidden" name="action" value="edit"/>
                        <input type="hidden" name="id" value="${illiquid.id}"/>
                        <input type='submit' value="Edit"/>
                    </teag:form>
                </td>
            </tr>
        </table>
        <%@ include file="/inc/aesFooter.jspf" %>
    </body>
</html>
