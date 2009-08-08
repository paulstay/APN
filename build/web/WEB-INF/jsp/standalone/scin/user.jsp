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

		<title>Client Information</title>

		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<link rel="stylesheet" type="text/css" href="<%=path%>/css/styles.css">
	</head>
	<jsp:useBean id='userInfo' scope='session'
		class='com.teag.bean.PdfBean' />
	<body>
		<%@include file="/inc/aesHeader.jspf"%>
		<h3 align='center'>
			Self Canceling Installment Note (SCIN)
		</h3>
		<h4 align='center'>
			Client Information
		</h4>
		<table>
			<tr>
				<td><%@include file='menu.jspf'%></td>
				<td>
					<teag:form name="uForm" action="servlet/StdScinServlet"
						method="post">
						<input type='hidden' name='action' value='none' />
						<input type='hidden' name='pageView' value='user'/>
						<table width='60%' align='center'>
							<tr>
								<td colspan='3'>
									Client information can be entered here. This information will
									allow a cover page to be generated.
								</td>
							</tr>
							<tr>
								<td colspan='3'>
									&nbsp;
								</td>
							</tr>
							<tr>
								<td align='right'>
									First Name(s) :
								</td>
								<td>
									&nbsp;
								</td>
								<td>
									<teag:text name='firstName' value='${userInfo.firstName }' />
								</td>
							</tr>
							<tr>
								<td align='right'>
									Middle Initial :
								</td>
								<td>
									&nbsp;
								</td>
								<td>
									<teag:text name='middleName' value='${userInfo.middleName }' />
								</td>
							</tr>
							<tr>
								<td align='right'>
									Last Name :
								</td>
								<td>
									&nbsp;
								</td>
								<td>
									<teag:text name='lastName' value='${userInfo.lastName }' />
								</td>
							</tr>
							<tr>
								<td colspan='3' align='center'>
									<input type="image" src="toolbtns/Update.png" alt="Update Tool"
										onclick="document.uForm.action.value='update'"
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
		<%@include file="/inc/aesFooter.jspf"%>
	</body>
</html>
