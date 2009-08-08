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
		String basePath = request.getScheme() + "://"
				+ request.getServerName() + ":" + request.getServerPort()
				+ path + "/";
	%>
	<head>
		<base href="<%=basePath%>">
		<title>Charitable Lead Annuity Trust</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<link rel="stylesheet" type="text/css" href="/css/styles.css"
			media="screen" />
	</head>
	<c:if test="${param.pageView =='Update' }">
		<jsp:setProperty name='userBean' property='firstName'
			param='firstName' />
		<jsp:setProperty name='userBean' property='middleName'
			param='middleName' />
		<jsp:setProperty name='userBean' property='lastName' param='lastName' />
		<jsp:setProperty name='userBean' property='spouse' param='spouse' />
	</c:if>
	<body>
		<jsp:include page='/inc/aesHeader.jspf' />
		<table width='100%'>
			<tr>
				<td width='30%'>
					<jsp:include page='menu.jsp' />
				</td>
				<td width='70%'>
					<teag:form name='tForm' action='user.jsp' method='post'>
						<teag:hidden name="pageView" value="noUpdate" />
						<table>
							<tr>
								<td colspan='3' align='center'>
									<h3>
										Client Information
									</h3>
								</td>
							</tr>
							<tr>
								<td colspan='3'>
									&nbsp;
								</td>
							</tr>
							<tr>
								<td>
									First Name
								</td>
								<td>
									&nbsp;
								</td>
								<td>
									<teag:text name='firstName' value='${userBean.firstName }' />
								</td>
							</tr>
							<tr>
								<td>
									Middle Name
								</td>
								<td>
									&nbsp;
								</td>
								<td>
									<teag:text name='middleName' value='${userBean.middleName }' />
								</td>
							</tr>
							<tr>
								<td>
									Last Name
								</td>
								<td>
									&nbsp;
								</td>
								<td>
									<teag:text name='lastName' value='${userBean.lastName }' />
								</td>
							</tr>
							<tr>
								<td>
									Spouse Name
								</td>
								<td>
									&nbsp;
								</td>
								<td>
									<teag:text name='spouse' value='${userBean.spouse }' />
								</td>
							</tr>
							<tr>
								<td>
									&nbsp;
								</td>
							</tr>
							<tr>
								<td colspan='3' align='center'>
									&nbsp;
									<teag:input type="image" src="${context}/toolbtns/Update.png"
										alt="Update Tool"
										onclick="document.tForm.pageView.value='Update';" />
								</td>
							</tr>
						</table>
					</teag:form>
				</td>
			</tr>
		</table>
		<jsp:include page='/inc/aesFooter.jspf' />
	</body>
</html>