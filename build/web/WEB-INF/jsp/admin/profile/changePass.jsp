<%-- 
    Document   : changePass
    Created on : Aug 8, 2009, 8:08:34 AM
    Author     : Paul
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="teagtags" prefix="teag"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<%
            String path = request.getContextPath();
            String basePath = request.getScheme() + "://" +
                    request.getServerName() + ":" + request.getServerPort()
                    + path + "/";
%>
<html>
    <head>
        <base href="<%=basePath%>">

        <title><%=basePath%></title>

        <meta http-equiv="pragma" content="no-cache">
        <meta http-equiv="cache-control" content="no-cache">
        <meta http-equiv="expires" content="0">
        <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
        <meta http-equiv="description" content="This is my page">
        <link rel="stylesheet" type="text/css" href="<%=path%>/css/styles.css">
    </head>
    <%@ include file="/inc/aesHeader.jspf" %>
    <%@ include file="/inc/aesQuickJump.jspf" %>
    <body>
        <jsp:useBean id="validUser" scope="session" class="com.teag.bean.AdminBean" />
        <jsp:useBean id="client" scope="session" class="com.teag.bean.ClientBean"/>
        <teag:form name='iform' action='servlet/PlannerPassword' method='post'>
            <teag:hidden name='action' value='cancel'/>
            <table width='90%' align='center'>
                <tr>
                    <td colspan="2" align="center"><b>Change Password</b></td>
                </tr>
                <tr>
                    <td colspan="2" align="center" style='color: red;'>${errMsg}</td>
                </tr>
                <tr>
                    <td align='right' width='50%'>Enter Old Password :
                    </td>
                    <td align='left'>
                        <input type="password" name="oldPass" size="14"/></td>
                </tr>
                <tr>
                    <td align='right'>Enter New Password : </td>
                    <td align='left'>
                        <input type="password" name="newPass1" size="14"/></td>
                </tr>
                <tr>
                    <td align='right'>ReEnter New Password : </td>
                    <td align='left'>
                        <input type="password" name="newPass2" size="14"/></td>
                </tr>
                <tr>
                    <td>&nbsp;</td>
                    <td>&nbsp;</td>
                </tr>
                <tr>
                    <td colspan='3' align='center'>
                        <button type='submit'
                                onclick="document.iform.action.value='modify'">
                            Change</button>
                        <button type='submit'
                                onclick="document.iform.action.value='cancel'">
                            Back</button>
                    </td>
                </tr>
            </table>
        </teag:form>
    </body>
</html>
