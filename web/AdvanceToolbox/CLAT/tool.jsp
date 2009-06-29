<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="teagtags" prefix="teag"%>
<jsp:useBean id='clat' scope='session'
	class='com.growcharity.toolbox.ClatTool' />
<jsp:useBean id='userBean' scope='session'
	class='com.growcharity.bean.UserBean' />
<c:set var='context' scope='session' value='/aes' />
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"
	+request.getServerPort()+path+"/";
%>
	<head>
		<base href="<%=basePath%>">
		<title>Charitable Lead Annuity Trust Tool Setup</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<link rel="stylesheet" type="text/css" href="/css/styles.css"
			media="screen" />
	</head>
	<c:if test="${param.pageView =='Update' }">
		
	</c:if>
	<body>
		<jsp:include page='/inc/aesHeader.jspf' />
		<table width='100%'>
			<tr>
				<td width='30%'>
					<jsp:include page='menu.jsp'/>
				</td>
				<td width='70%'>
					
				</td>
			</tr>
		</table>
		<jsp:include page='/inc/aesFooter.jspf' />
	</body>
	</html>