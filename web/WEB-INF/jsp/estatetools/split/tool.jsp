<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%@ page import="com.estate.constants.*"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="teagtags" prefix="teag"%>
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
		<title>Clat Setup Page</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<link rel="stylesheet" type="text/css" href="<%=path%>/css/styles.css">
	</head>
	<jsp:useBean id='split' scope='session' type='com.teag.estate.SplitDollarTool' />
	<jsp:useBean id='epg' scope='session'
		type='com.teag.webapp.EstatePlanningGlobals' />
	<body>
		<%@include file="/inc/aesHeader.jspf"%>
		<h3 align='center'>
			Private Split Dollar Tool
		</h3>
		<table align='center' width='100%'>
			<tr>
				<td colspan='2' align='center'>
					<h3>
						Tool Assumptions
					</h3>
				</td>
			</tr>
                        <tr>
                            <td>
                                <p>
                                The client will pay $4M to an ILIT in the form of a Gift and Premiums for
                                a $40M Life Insurance policy to be placed inside the Multi-Generation Trust. The loan of $3.6M will accrue interest
                                at the current long term APR rate of 3.4% until death. At death the loan balance will be paid from the death benefit
                                with the remainder going to the family through the MGT.
                                </p>
                                <p>
                                The premiums will be paid in three years, with $1.4M the first year, 1.8M the second and third and final
                                payment of $800K will be paid in the third and final year.
                                </p>
                                <p>A total of $4M in muni bonds will be liquidated during the first three years to facilitate this process</p>
                            </td>
                        </tr>
			<tr>
				<td>
					<%@ include file="menu.jspf"%>
				</td>
				<td>
					<teag:form name='iform' method='post'
						action='servlet/SplitToolServlet'>
						<teag:hidden name='pageView' value='noUpdate' />
					</teag:form>
				</td>
			</tr>
		</table>
	</body>
</html>
