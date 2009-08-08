<%@ page language="java" pageEncoding="ISO-8859-1"%>
<%@ page import="com.estate.constants.*"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="teagtags" prefix="teag" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<! DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<base href="<%=basePath%>">
	<title>Select Client</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
	<link href="css/style.css" rel="stylesheet" media="screen">
	<body>
		<jsp:useBean id="sp" scope="request" type="com.teag.bean.PersonBean" />
		<%@ include file="/inc/aesHeader.jspf"%>
		<teag:form action="servlet/ChildSpouseServlet"
			name="iform" method="post">
			<input type='hidden' name='action' value='${btn}' />
			<input type='hidden' name='cid' value='${cid}' />
			<input type='hidden' name='city' value='Belcamp' />
			<input type='hidden' name='state' value='MD' />
			<input type='hidden' name='ssn' value='xxx-xx-xxxx' />
			<input type='hidden' name='healthId' value='1' />
			<input type='hidden' name='citizenship' value='us' />
			<input type='hidden' name="id" value='${sp.id }' />
			<table align='center' width='60%'>
				<tr>
					<td align='right'>
						First Name :
					</td>
					<td align='left'>
						<teag:text name='firstName' value='${sp.firstName }' />
					</td>
				</tr>
				<tr>
					<td align='right'>
						Middle Name :
					</td>
					<td align='left'>
						<teag:text name='middleName' value='${sp.middleName }' />
					</td>
				</tr>
				<tr>
					<td align='right'>
						Last Name :
					</td>
					<td align='left'>
						<teag:text name='lastName' value='${sp.lastName }' />
					</td>
				</tr>
				<tr>
					<td align='right'>
						Occupation :
					</td>
					<td align='left'>
						<teag:text name='occupation' value='${sp.occupation }' />
					</td>
				</tr>
				<tr>
					<td align='right'>
						Gender :
					</td>
					<td align='left'>
						<teag:select name='gender' options="<%=InputMaps.gender%>"
							optionValue='${sp.gender}' />
					</td>
				</tr>
				<tr>
					<td align='right'>
						Birth Date :
					</td>
					<td align='left'>
						<teag:text name='birthDate' value='${sp.birthDate}' />
					</td>
				</tr>
				<tr>
					<td colspan='2' align='center'>
						<c:if test="${btn == 'Add' }">
							<input type='submit' value='Add'
								onclick="document.iform.action.value='insert'" />
							<input type='submit' value='Cancel'
								onclick="document.iform.action.value='Cancel'" />
						</c:if>
						<c:if test="${btn == 'Edit' }">
							<input type='submit' value='Save'
								onclick="document.iform.action.value='update'" />
							<input type='submit' value='Cancel'
								onclick="document.iform.action.value='Cancel'" />
							<input type='submit' value='Delete'
								onclick="document.iform.action.value='Delete'" />
						</c:if>
					</td>
			</table>
		</teag:form>
		<%@ include file="/inc/aesFooter.jspf"%>
	</body>
</html>
