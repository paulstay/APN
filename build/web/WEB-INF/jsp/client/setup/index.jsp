<%@ page language="java" import="java.util.*"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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

		<title><%=basePath%></title>

		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<link rel="stylesheet" type="text/css" href="<%=path%>/css/styles.css">
	</head>
	<body>
		<c:if test="${validUser == null}">
			<c:redirect url="/login.jsp">
				<c:param name="errorMsg" value="You are not logged in!" />
			</c:redirect>
		</c:if>
		<jsp:useBean id="validUser" scope="session"
			class="com.teag.bean.AdminBean" />
		<jsp:useBean id="client" scope="session"
			class="com.teag.bean.ClientBean" />
		<%@ include file="/inc/aesHeader.jspf"%>
		<%@ include file="/inc/aesQuickJump.jspf"%>
		<div id='client-index'>
			<table align='center'>
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
								<a href='servlet/CashFlowInitServlet'>Variables for
									Balance Sheet</a>
							<li>
								<a href='servlet/VCFItemServlet'>Additional Balance
									Sheet Line Items</a>
							<li>
								<a href='servlet/ABTrustServlet'>AB Trust</a>
						</ul>
					</td>
				</tr>
			</table>
		</div>
		<div align='center'>
			<a href="client/index.jsp">Back to Client</a>
		</div>
		<%@ include file="/inc/aesFooter.jspf"%>
	</body>
</html>
