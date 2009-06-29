<%@ page language="java" pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.*"%>
<%@ page import="com.teag.bean.*"%>
<%@ page import="com.teag.view.*"%>
<%@ page import="com.estate.constants.*"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="teagtags" prefix="teag"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<! DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<base href="<%=basePath%>">
	<title>Select Client</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
	<link href="<%=path%>/css/style.css" rel="stylesheet" media="screen">
	<body>
		<jsp:useBean id="person" class="com.teag.bean.PersonBean"
			scope="session" />
		<jsp:useBean id="child" scope="request"
			type="com.teag.bean.PersonBean" />
		<jsp:useBean id="cb" scope="request" type="com.teag.bean.ChildrenBean" />
		<%@ include file="/inc/aesHeader.jspf"%>
		<h3 align='center'>
			${btn} Child
		</h3>
		<teag:form name='iform' action='servlet/ChildProcessServlet'
			method='post'>
			<input type='hidden' name='action' value='${btn}' />
			<input type='hidden' name='id' value='${child.id}' />
			<input type='hidden' name='city' value='Belcamp' />
			<input type='hidden' name='state' value='MD' />
			<input type='hidden' name='ssn' value='xxx-xx-xxxx' />
			<input type='hidden' name='healthId' value='1' />
			<input type='hidden' name='citizenship' value='us' />
			<table align='center'>
				<tr>
					<td align='right'>
						First Name :
					</td>
					<td align='left'>
						<teag:text name='firstName' value="${child.firstName}" />
					</td>
				</tr>
				<tr>
					<td align='right'>
						Middle Name :
					</td>
					<td align='left'>
						<teag:text name='middleName' value="${child.middleName}" />
					</td>
				</tr>
				<tr>
					<td align='right'>
						Last Name :
					</td>
					<td align='left'>
						<teag:text name='lastName' value="${child.lastName}" />
					</td>
				</tr>
				<tr>
					<td align='right'>
						Occupation :
					</td>
					<td align='left'>
						<teag:text name='occupation' value="${child.occupation}" />
					</td>
				</tr>
				<tr>
					<td align='right'>
						Gender
					</td>
					<td align='left'>
						<teag:select name='gender' options="<%=InputMaps.gender%>"
							optionValue="${child.gender}" />
					</td>
				</tr>
				<tr>
					<td align='right'>
						Birth Date
					</td>
					<td align='left'>
						<teag:text name='birthDate' value="${child.birthDate}" />
					</td>
				</tr>
				<tr>
					<td align='right'>
						Other Parent
					</td>
					<td align='left'>
						<teag:select name="parent" options="${sp}" optionValue=""/>
					</td>
				</tr>
				<tr>
					<td align='right'>
						# of children
					</td>
					<td align='left'>
						<teag:select name="numChildren" options="${cm}" optionValue="${cb.numChildren}" />
					</td>
				</tr>
				<tr>
					<td align='right'>
						Enter Children names and ages
					</td>
					<td align='left'>
						<input name='family' value="${cb.notes }" size='75' />
					</td>
				</tr>
				<tr>
					<td>
						&nbsp;
					</td>
				</tr>
				<tr>
					<td colspan='2' align='center'>
						<c:if test="${btn == 'Add' }">
							<input type='submit' value='Save'
								onclick="document.iform.action.value='insert'" />
						</c:if>
						<c:if test="${btn == 'Edit' }">
							<input type='submit' value='Save'
								onclick="document.iform.action.value='update'" />
						</c:if>
						<input type='submit' value='Cancel'
							onclick="document.iform.action.value='Cancel'" />
						<input type='submit' value='Delete'
							onclick="document.iform.action.value='Delete'" />
					</td>
				</tr>
			</table>
		</teag:form>
		<%@ include file="/inc/aesFooter.jspf"%>
	</body>
</html>
