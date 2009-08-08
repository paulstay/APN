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

		<title>RPM Summary</title>

		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<link rel="stylesheet" type="text/css" href="<%=path%>/css/styles.css">
	</head>
	<body>
		<%@include file="/inc/aesHeader.jspf" %>
		<h3 align='center'>
			Retirement Plan Maxamizer
		</h3>
		<h4 align='center'>
			Summary
		</h4>
		<jsp:useBean id='userInfo' scope='session'
			type='com.teag.bean.PdfBean' />
		<jsp:useBean id='rpm' scope='session' type='com.estate.toolbox.Rpm' />
		<table>
			<tr>
				<td valign='top'><%@include file='menu.jspf'%></td>
				<td align='center' width='100%'>
					<teag:form name='tmain' action='servlet/StdRpmServlet' method='post'>
					<input type="hidden" name="pageView" value="summary"/>
					<input type="hidden" name="action" value="update"/>
						<table>
							<tr>
								<td>
									Show
								</td>
								<td>
									<teag:select name='rpmView'
										onChange="javascript: document.tmain.submit()">
										<teag:option label='current' value='current' selected='${param.rpmView}'/>
										<teag:option label='rpm' value='rpm' selected='${param.rpmView }'/>
										<teag:option label='comparison' value='comparison' selected='${param.rpmView}' />
									</teag:select>
								</td>
							</tr>
						</table>
					</teag:form>
					<br>
					<c:choose>
						<c:when test="${showPage == 'rpm' }">
							<%@include file='rpm.jsp'%>
						</c:when>
						<c:when test="${showPage == 'current' }">
							<%@include file='current.jsp'%>
						</c:when>
						<c:otherwise>
							<%@include file='comparison.jsp'%>
						</c:otherwise>
					</c:choose>
				</td>
			</tr>
		</table>
		<%@include file="/inc/aesFooter.jspf" %>
	</body>
</html>
