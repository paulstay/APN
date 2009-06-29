<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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

		<title>AB Trust Setup</title>

		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<link rel="stylesheet" type="text/css" href="<%=path%>/css/styles.css">
	</head>
	<body>
		<jsp:useBean id="ab" scope="request"
			type="com.teag.bean.ABTrust" />
		<jsp:useBean id="client" scope="session"
			class="com.teag.bean.ClientBean" />
		<%@ include file="/inc/aesHeader.jspf"%>
		<%@ include file="/inc/aesQuickJump.jspf"%>
		<h3 align='center'>
			AB Trust Setup
		</h3>
		<teag:form name='iform' method='post'
			action='servlet/ABTrustServlet'>
			<input type="hidden" name="pageView" value="return" />
			<table align='center' width='65%'>
				<tr>
					<td align='right'>Use with Cash Flow Balance Sheet</td>
					<td>
						<teag:select name="useBoth">
							<teag:option label="Include" value="B" selected="${ab.used}"/>
							<teag:option label="Do Not Include" value="0" selected="${ab.used}"/>
						</teag:select>
					</td>
				</tr>
				<tr>
					<td align='right'>Number of AB Trusts (Before Scenario)</td>
					<td>
						<teag:select name="beforeTrusts">
							<teag:option label="1" value="1" selected='${ab.beforeTrusts}'/>
							<teag:option label="2" value="2" selected='${ab.beforeTrusts}'/>
						</teag:select>
					</td>
				</tr>
				<tr>
					<td align='right'>Number of AB Trusts (After Scenario)</td>
					<td>
						<teag:select name="afterTrusts">
							<teag:option label="1" value="1" selected='${ab.afterTrusts}'/>
							<teag:option label="2" value="2" selected='${ab.afterTrusts}'/>
						</teag:select>
					</td>
				</tr>
				<tr>
					<td colspan='3' align='center'>
						<input type="image" src="toolbtns/Update.png" alt="Update Tool"
							onclick="document.iform.pageView.value='update'"
							onmouseover="this.src='toolbtns/Update_over.png';"
							onmouseout="this.src='toolbtns/Update.png';"
							onmousedown="this.src='toolbtns/Update_down.png';" />
							&nbsp;
						<input type="image" src="toolbtns/Return.png" alt="Return"
							onclick="document.iform.pageView.value='return'"
							onmouseover="this.src='toolbtns/Return_over.png';"
							onmouseout="this.src='toolbtns/Return.png';"
							onmousedown="this.src='toolbtns/Return_down.png';" />
					</td>
				</tr>
			</table>
		</teag:form>
		<%@ include file="/inc/aesFooter.jspf"%>
	</body>
</html>
