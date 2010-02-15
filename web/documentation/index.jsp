<%@ page language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
            String path = request.getContextPath();
            String basePath = request.getScheme() + "://"
                    + request.getServerName() + ":" + request.getServerPort()
                    + path + "/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
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
    <body>
        <%@include file="/inc/aesHeader.jspf"%>
        <%@include file="/inc/aesQuickJump.jspf"%>
        <div id='client-index'>
            <table width='60%' align='center'>
                <tr>
                    <td>
                        &nbsp;
                    </td>
                </tr>
                <tr>
                    <td></td>
                </tr>
                <tr>
                    <td align="left" valign="top" width="330">
                        <ul>
                            <li>
                                <a href='documentation/user.doc' target='_blank'>User
									Documentation (3MB Word File)</a>
                            <li>
								Fact Finder Forms - The Discovery Process
                                <ul>
                                    <li>
                                        <a href='documentation/TEAG_Fact_Finder_1.pdf'>Factual
											Information</a>
                                    </li>
                                    <li>
                                        <a href='documentation/TEAG_Fact_Finder_2.pdf'>Objectives
											and Philosophy</a>
                                    </li>
                                </ul>
                            </li>
                            <li>
                                <a
                                    href='http://www.irs.gov/businesses/small/article/0,,id=112482,00.html'
                                    target='_blank'>IRS AFR 7520 Rates</a>
                            </li>
                            <!--
							<li>
								<a href='documentation/CB.pdf' target='_blank'>Sample Report
									(Clark and Barbara)</a>
							</li>
							<li>
								<a href='documentation/BN.pdf' target='_blank'>Sample Report
									(Bruce and Nadine)</a>
							</li>
							<li>
								<a href='documentation/Monika.pdf' target='_blank'>Sample
									Report (Monika)</a>
							</li>
                            -->
                        </ul>
                    </td>
                </tr>
            </table>
        </div>
        <div align='center'>
            <c:choose>
                <c:when test="${param.page == 'main' }">
                    <a href="admin/index.jsp">Back to Main Menu</a>
                </c:when>
                <c:otherwise>
                    <a href="client/index.jsp">Back to Client Menu</a>
                </c:otherwise>
            </c:choose>
        </div>
        <%@include file="/inc/aesFooter.jspf"%>
    </body>
</html>
