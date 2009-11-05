<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<html>
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
		<%@ include file="/inc/aesHeader.jspf"%>
		<%@ include file="/inc/aesQuickJump.jspf"%>
		<br><%@ include file="/inc/init.jspf"%>
		<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
		<c:if test="${validUser == null}">
			<c:redirect url="/login.jsp">
				<c:param name="errorMsg" value="You are not logged in!" />
			</c:redirect>
		</c:if>
		<jsp:useBean id="validUser" scope="session"
			class="com.teag.bean.AdminBean" />
		<h3 align='center'>
			TEAG TOOLBOX
		</h3>
		<table width='60%' align='center'>
			<tr>
				<td>
					<ul>
						<li>
							<a href='servlet/StdClatInitServlet'>Charitable Lead Annuity
								Trust</a>
						</li>
						<li>
							<a href='servlet/StdCrtServlet'>Charitable Remainder
								Trust</a>
						</li>
						<li>
							<a href='servlet/StdCgaServlet'>Charitable Gift Annuity</a>
						</li>
						<li>
							<a href='servlet/StdGratServlet'>Grantor Retained
								Annuity Trust</a>
						</li>
						<li>
							<a href='servlet/StdIditServlet'>Installment Sale to an
								IDIT</a>
						</li>
						<li>
							<a href='servlet/StdScinServlet'>Self Canceling
								Installment Note</a>
						</li>
						<li>
							<a href='servlet/StdRpmServlet'>Retirement Plan
								Maximizer</a>
						</li>
						<li>
							<a href='servlet/StdQprtServlet'>Qualified Personal
								Residence Trust</a>
						</li>
						<li>
							<a href='servlet/FactorsServlet'>Term and Life Factors</a>
						</li>
					</ul>
				</td>
			</tr>
		</table>
		<br>
		<div align='center'>
			<a href="admin/index.jsp">Back to Main Menu</a>
		</div>
		<%@ include file="/inc/aesFooter.jspf"%>
		