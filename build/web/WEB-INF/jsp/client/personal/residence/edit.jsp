<%@ page language="java" pageEncoding="ISO-8859-1"%>
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
	<link href="<%=path %>/css/styles.css" rel="stylesheet" media="screen">
	<body>
		<jsp:useBean id="res" scope='request'
			type="com.teag.bean.LocationBean" />
		<jsp:useBean id="client" scope="session"
			class="com.teag.bean.ClientBean" />
		<jsp:useBean id="person" scope="session"
			class="com.teag.bean.PersonBean" />
		<%@ include file="/inc/aesHeader.jspf"%>
		<h3 align='center'>
			Edit Residence or Business Address
		</h3>
		<teag:form action='servlet/ResidenceServlet'
			name='iform' method='post'>
			<input type='hidden' name='action' value='${btn}' />
			<input type='hidden' name='ownerId' value="${person.id}" />
			<input type='hidden' name='id' value="${res.id}" />
			<table align='center' width='60%'>
				<tr>
					<td align='right'>
						Type :
					</td>
					<td>
						<teag:select name='rType' options='<%=InputMaps.residenceTypes%>'
							optionValue='${res.type}' />
					</td>
				</tr>
				<tr>
					<td align='right'>
						Description :
					</td>
					<td>
						<teag:text name='name' value="${res.name}" />
					</td>
				</tr>
				<tr>
					<td align='right'>
						Address1 :
					</td>
					<td>
						<teag:text name='address1' value="${res.address1}" />
					</td>
				</tr>
				<tr>
					<td align='right'>
						Address2 :
					</td>
					<td>
						<teag:text name='address2' value="${res.address2}" />
					</td>
				</tr>
				<tr>
					<td align='right'>
						City :
					</td>
					<td>
						<teag:text name='city' value="${res.city}" />
					</td>
				</tr>
				<tr>
					<td align='right'>
						State :
					</td>
					<td>
						<teag:select name='state' options="<%=InputMaps.states%>"
							optionValue="${res.state}" />
					</td>
				</tr>
				<tr>
					<td align='right'>
						Zip :
					</td>
					<td>
						<teag:text name='zip' value="${res.zip}" />
					</td>
				</tr>
				<tr>
					<td align='right'>
						Phone :
					</td>
					<td>
						<teag:text name='phone' value="${res.phone}" />
					</td>
				</tr>
				<tr>
					<td align='right'>
						Fax :
					</td>
					<td>
						<teag:text name='fax' value="${res.fax}" />
					</td>
				</tr>
				<tr>
					<td colspan='2'>
						&nbsp;
					</td>
				</tr>
				<tr>
					<td align='center' colspan='2'>
						<c:if test="${btn == 'add'}">
							<input type='submit' value='Save'
								onclick="document.iform.action.value='insert'" />
						</c:if>
						<c:if test="${btn == 'edit'}">
							<input type='submit' value='Save'
								onclick="document.iform.action.value='update'" />
						</c:if>
						<input type='submit' value='Cancel'
							onClick='document.iform.action.value="Cancel"' />
						<input type='submit' value='Delete'
							onClick='document.iform.action.value="Delete"' />
					</td>
			</table>
		</teag:form>
		<%@ include file="/inc/aesFooter.jspf"%>
	</body>
</html>
