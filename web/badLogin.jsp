<%-- 
    Document   : badLogin
    Created on : Jul 9, 2009, 11:18:13 AM
    Author     : Paul Stay
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <%@ include file="/inc/aesHeader.jspf"%>
        <h1 align="center">Bad Login Attempt</h1>
        <table align="center" width="20%">
            <tr>
                <td>
                    <p>We are currently unable to log you into the system, you
                        have exceeded the number of times for login attempts. Please contact
                        the APN staff to reset your password.</p>
                </td>
            </tr>

        </table>
        <%@ include file="/inc/aesFooter.jspf"%>
    </body>
</html>
