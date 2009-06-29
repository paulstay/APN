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

		<title>Cash Flow Setup</title>

		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<link rel="stylesheet" type="text/css" href="<%=path%>/css/styles.css">
	</head>
	<body>
		<jsp:useBean id="cashFlow" scope="request"
			type="com.teag.bean.CashFlowBean" />
		<jsp:useBean id="client" scope="session"
			class="com.teag.bean.ClientBean" />
		<%@ include file="/inc/aesHeader.jspf"%>
		<%@ include file="/inc/aesQuickJump.jspf"%>
		<h3 align='center'>
			Variables For Balance Sheet Projections of Cash Flow
		</h3>
		<teag:form name='iform' method='post'
			action='servlet/CashFlowInitServlet'>
			<input type="hidden" name="pageView" value="return" />
			<table align='center' width='65%'>
				<tr>
					<td align='right'>
						Assumed Second Death (years)
					</td>
					<td>
						<teag:text fmt="number" pattern="###" name="finalDeath"
							value="${cashFlow.finalDeath}" />
					</td>
				</tr>
				<tr>
					<td align='right'>
						Social Security Payments (monthly)
					</td>
					<td>
						<teag:text fmt="number" pattern="###,###,###"
							name="socialSecurity" value="${cashFlow.socialSecurity}" />
					</td>
				</tr>
				<tr>
					<td align='right'>
						Social Security Annual Growth (%)
					</td>
					<td>
						<teag:text fmt="number" pattern="##.###%"
							name="socialSecurityGrowth"
							value="${cashFlow.socialSecurityGrowth}" />
					</td>
				</tr>
				<tr>
					<td align='right'>
						Inflation
					</td>
					<td>
						<teag:text fmt="number" pattern="##.###%" name="inflation"
							value="${cashFlow.inflation}" />
					</td>
				</tr>
				<tr>
					<td align='right'>
						State Income Tax Rate
					</td>
					<td>
						<teag:text fmt="number" pattern="##.###%" name="stateTaxRate"
							value="${cashFlow.stateTaxRate}" />
					</td>
				</tr>
				<tr>
					<td align='right'>
						Charitable Contribution (% of Cash Receipts)
					</td>
					<td>
						<teag:text fmt="percent" pattern="##.###%" name="charity" value="${cashFlow.charity}"/>
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
