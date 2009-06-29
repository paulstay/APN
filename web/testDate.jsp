<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <script type="text/javascript" src="js/calendarDateInput.js">

        /***********************************************
         * Jason's Date Input Calendar- By Jason Moon http://calendar.moonscript.com/dateinput.cfm
         * Script featured on and available at http://www.dynamicdrive.com
         * Keep this notice intact for use.
         ***********************************************/

    </script>
    <body>
        <c:set var="d" value="12/25/1956"></c:set>
        <h1>Hello World!</h1>
        <form name="tForm"  action="validTest.jsp" method="post">
            <script>DateInput('irsDate', true, 'MM/DD/YYYY','${d}')</script>
            <input type="button" onClick="alert(this.form.irsDate.value)" value="Show date value passed">
            <input type="submit" value="Submit"/>

        </form>
    </body>
</html>
