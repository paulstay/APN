<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%
        String path = request.getContextPath();
        String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
    <head>
        <base href="<%=basePath%>">

        <title>CRUT</title>
        <meta http-equiv="pragma" content="no-cache">
        <meta http-equiv="cache-control" content="no-cache">
        <meta http-equiv="expires" content="0">
        <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
        <meta http-equiv="description" content="This is my page">
        <link rel="stylesheet" type="text/css" href="<%=path%>/css/styles.css">
    </head>

    <body>
        <%@include file="/inc/aesHeader.jspf" %>
        <h4 align='center'>
            Estate Tax Calculator
        </h4>
        <table align='center' width='100%'>
            <tr>
                <!-- Header -->
                <td colspan='2' align='center'>
                </td>
            </tr>
            <tr>
                <!--  menu  -->
                <td>
                    <%@ include file="menu.jspf"%>
                </td>
                <!-- Form/Web Page -->
                <td>
                    <h3 align='center'>
                        Description
                    </h3>
                    <table align='center' border='1'>
                        <tr>
                            This tool determines the gift and or estate tax, with the appropriate
                            unified credit, and show the net tax payable. It shows the net estate
                            remaining and the percent of the Estate lost in taxes. It also shows a
                            projection for several years based on a standard growth rate for each year.
                        </tr>
                    </table>
                </td>
            </tr>
        </table>
        <%@include file="/inc/aesFooter.jspf" %>
    </body>
</html>
