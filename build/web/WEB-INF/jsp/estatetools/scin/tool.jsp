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
		<title>SCIN Setup Page</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<link rel="stylesheet" type="text/css" href="<%=path%>/css/styles.css">
	</head>
	<jsp:useBean id='scin' scope='session' type='com.teag.estate.SCINTool' />
	<body>
		<%@include file="/inc/aesHeader.jspf"%>
		<h3 align='center'>
			Self Canceling Installment Note
		</h3>
		<table align='center' width='100%'>
			<tr>
				<td colspan='2' align='center'>
					<h3>
						Tool Setup
					</h3>
				</td>
			</tr>
			<tr>
				<td>
					<%@ include file="menu.jspf"%>
				</td>
				<td>
					<teag:form name='iform' method='post'
						action='servlet/ScinToolServlet'>
						<teag:hidden name='pageView' value='noUpdate' />
						<table align='left'>
							<tr>
								<td>
									Irs 7520 rate :
								</td>
								<td>
									<teag:text fmt='percent' pattern='##.###%' name='irsRate'
										value='${scin.irsRate }' />
								</td>
							</tr>
							<tr>
								<td>
									IRS 750 Date of Transfer :
								</td>
								<fmt:formatDate var='idate' pattern='M/dd/yyyy' value='${scin.irsDate}'/>
								<td>
									<teag:text name='irsDate' value='${idate}' />
								</td>
							</tr>
							<tr>
								<td>Note Type</td>
								<td>
									<teag:select name='noteType'>
										<teag:option label="Level Prin + Interest" value="0" selected="${scin.noteType}"/>
										<teag:option label="Self Amoritized" value="1" selected="${scin.noteType}"/>
										<teag:option label="Interest Only" value="2" selected="${scin.noteType}"/>
									</teag:select>
								</td>
							</tr>
							<tr>
								<td>Note Rate</td>
								<td>
									<teag:text name="noteRate" fmt="percent" pattern="##.###%" value="${scin.noteRate}"/>
								</td>
							</tr>
							<tr>
								<td>Term</td>								
								<td>
									<teag:text name='term' pattern="###" value="${scin.term}"/>
								</td>
							</tr>
							<tr>
								<td>Pay at Begin or End</td>
								<td>
									<teag:select name="endBegin">
										<teag:option label="Begin" value="1" selected="${scin.endBegin}"/>
										<teag:option label="End" value="0" selected="${scin.endBegin}"/>
									</teag:select>
								</td>
							</tr>
							<tr>
								<td>Payment Type</td>
								<td>
									<teag:select name="paymentType">
										<teag:option label="Principal Risk Premium" value="0" selected="${scin.paymentType}"/>
										<teag:option label="Interest Risk Premium" value="1" selected="${scin.paymentType}"/>
									</teag:select>
								</td>
							</tr>
							<tr>
								<td>Years Deferred</td>
								<td>
									<teag:text name='yrsDeferred' value='${scin.yrsDeferred }'/>
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
