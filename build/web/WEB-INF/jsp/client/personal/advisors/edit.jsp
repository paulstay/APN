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
	<link href="css/style.css" rel="stylesheet" media="screen">
	<body>
		<%@ include file="/inc/aesHeader.jspf"%>
		<jsp:useBean id="client" scope="session"
			class="com.teag.bean.ClientBean" />
		<jsp:useBean id='person' scope='session'
			class='com.teag.bean.PersonBean' />
		<jsp:useBean id='advisor' scope='request'
			type='com.teag.bean.AdvisorBean' />
		<teag:form method='post' name='iform' action='servlet/AdvisorServlet'>
			<c:if test="${btn =='add' }">
				<input type='hidden' name='action' value='add' />
			</c:if>
			<c:if test="${btn =='edit' }">
				<input type='hidden' name='action' value='Edit' />
			</c:if>
			<input type='hidden' name='ownerId' value='${client.primaryId}' />
			<input type='hidden' name='id' value='${advisor.id}' />
			<h3 align='center'>
				${btn} Advisor for ${person.firstName} ${person.lastName}
			</h3>
			<table align='center' width='60%'>
				<tr>
					<td align='right'>
						Firm :
					</td>
					<td align='left'>
						<teag:text name='firmName' value="${advisor.firmName}" />
					</td>
				</tr>
				<tr>
					<td align='right'>
						Advisor Type :
					</td>
					<td align='left'>
						<teag:select name='advisorType'
							options='<%= InputMaps.advisorTypes %>'
							optionValue="${advisor.type}" />
					</td>
				</tr>
				<tr>
					<td align='right'>
						Title :
					</td>
					<td align='left'>
						<teag:text name='title' value="${advisor.title }" />
					</td>
				</tr>
				<tr>
					<td align='right'>
						First Name :
					</td>
					<td align='left'>
						<teag:text name='firstName' value="${advisor.firstName}" />
					</td>
				</tr>
				<tr>
					<td align='right'>
						Middle Name :
					</td>
					<td align='left'>
						<teag:text name='middleName' value="${advisor.middleName}" />
					</td>
				</tr>
				<tr>
					<td align='right'>
						Last Name :
					</td>
					<td align='left'>
						<teag:text name='lastName' value="${advisor.lastName}" />
					</td>
				</tr>
				<tr>
					<td align='right'>
						Suffix :
					</td>
					<td align='left'>
						<teag:text name='suffix' value="${advisor.suffix}" />
					</td>
				</tr>
				<!-- 
		<tr>
			<td align='right'>Assistant :</td>
			<td align='left'><teag:text name='assistant' value="${}" /></td>
		</tr>
		 -->
				<tr>
					<td align='right'>
						Address1 :
					</td>
					<td>
						<teag:text name='address1' value="${advisor.address1 }" />
					</td>
				</tr>
				<tr>
					<td align='right'>
						Address2 :
					</td>
					<td>
						<teag:text name='address2' value="${advisor.address2 }" />
					</td>
				</tr>
				<tr>
					<td align='right'>
						City :
					</td>
					<td>
						<teag:text name='city' value="${advisor.city}" />
					</td>
				</tr>
				<tr>
					<td align='right'>
						State :
					</td>
					<td>
						<teag:select name='state' options="<%=InputMaps.states%>"
							optionValue="${advisor.state}" />
					</td>
				</tr>
				<tr>
					<td align='right'>
						Zip :
					</td>
					<td>
						<teag:text name='zip' value="${advisor.zip}" />
					</td>
				</tr>
				<tr>
					<td align='right'>
						Phone :
					</td>
					<td>
						<teag:text name='phone' value="${advisor.phone}" />
					</td>
				</tr>
				<tr>
					<td align='right'>
						Fax :
					</td>
					<td>
						<teag:text name='fax' value="${advisor.fax }" />
					</td>
				</tr>
				<tr>
					<td align='right'>
						Email :
					</td>
					<td>
						<teag:text name='email' value="${advisor.email}" />
					</td>
				</tr>
				<tr>
					<td>
						&nbsp;
					</td>
				</tr>
				<tr>
					<td align='center' colspan='2'>
						<c:if test="${btn == 'add'}">
						<input type='submit' value='Save' onclick="document.iform.action.value='insert'" />
						</c:if>
						<c:if test="${btn == 'edit'}">
						<input type='submit' value='Save' onclick="document.iform.action.value='update'" />
						</c:if>
						<input type='submit' value='Cancel'
							onclick='document.iform.action.value="Cancel"' />
						<input type='submit' value='Delete'
							onclick='document.iform.action.value="Delete"' />
			</table>
		</teag:form>
		<%@ include file="/inc/aesFooter.jspf"%>
	</body>
</html>
