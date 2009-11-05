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
		<title>Irrevocable Life Insurance Trust</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<link rel="stylesheet" type="text/css" href="<%=path%>/css/styles.css">
	</head>
	<jsp:useBean id='life' scope='session' type='com.teag.estate.LifeTool' />
	<body>
		<%@include file="/inc/aesHeader.jspf"%>
		<h3 align='center'>
			Irrevocable Life Insurance Trust
		</h3>
		<table align='center' width='100%'>
			<tr>
				<td valign='top'>
					<%@ include file="menu.jspf"%>
				</td>
				<td valign='top'>
					<teag:form name='iform' method='post'
						action='servlet/LifeToolServlet'>
						<teag:hidden name='pageView' value='noUpdate' />
						<table align='left' >
							<tr>
								<td colspan='2' align='center'><h3>ILIT Tool Values</h3></td>
							</tr>
							<tr>
								<td>Description</td>
								<td>
									<teag:text name="description" value="${life.description}" />
								</td>
							</tr>
							<tr>
								<td colspan='3' align='center'>
									<input type="image" src="toolbtns/Update.png" alt="Update Tool"
										onclick="document.iform.pageView.value='PROCESS'"
										onmouseover="this.src='toolbtns/Update_over.png';"
										onmouseout="this.src='toolbtns/Update.png';"
										onmousedown="this.src='toolbtns/Update_down.png';" />
								</td>
							</tr>
						</table>
					</teag:form>
				</td>
			</tr>
		</table>
	</body>
</html>
