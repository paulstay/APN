<%-- 
    Document   : validTest
    Created on : May 19, 2009, 4:43:10 PM
    Author     : Paul
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <%
java.util.Enumeration e = request.getParameterNames();
	while(e.hasMoreElements()) {
		String name = (String) e.nextElement();
		String value = request.getParameter(name);
		System.out.println("parameter : " + name + " : " + value);
	}
%>
        <h1>Hello World!</h1>
    </body>
</html>
