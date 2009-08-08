<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
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

		<title>My JSP 'confirm.jsp' starting page</title>

		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<link rel="stylesheet" type="text/css" href="<%=path%>/css/styles.css">
	</head>
	<body>
	<jsp:useBean id='c' scope='page' class='com.teag.bean.PersonBean'/>
	<jsp:setProperty name='c' property='id' value='${param.personId}'/>
	<% c.initialize(); %> 
		<%@include file="/inc/aesHeader.jspf" %>
		<form action="admin/support/delete.jsp" method="post">
			<input type="hidden" name="plannerId" value="${param.plannerId }"/>
			<input type="hidden" name="clientId" value="${param.clientId }"/>
			<input type="hidden" name="personId" value="${param.personId }"/>
			<table align='center' width='25%'>
				<tr>
					<td align='center'><span class='text-bold-red'>WARNING : </span></td>
				</tr>
				<tr>
					<td align='left'>
						<p>
							<span class='text-red'>You have choosen to delete the
								following client:
								${c.firstName} ${c.lastName}, and all of his/her data.
								This proceedure cannot be undone. Once
								deleted, you will have to renter the data again.</span>
						</p>
					</td>
				</tr>
				<tr>
					<td>&nbsp;</td>
				</tr>
				<tr>
					<td>
						<p>
						<input type='radio' name='confirm' value='Y' />Yes I want to delete this client
						</p>
						<p>
						<input type='radio' name='confirm' value='N' checked='checked'>No, I do not want to delete.
						</p>
					</td>
				</tr>
				<tr>
					<td align='center'>
						<input type='submit' value='Submit'/>
					</td>
				</tr>
			</table>
		</form>
		<%@include file="/inc/aesFooter.jspf"%>
	</body>
</html>
