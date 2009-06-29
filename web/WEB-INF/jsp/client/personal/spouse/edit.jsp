<%@ page language="java" pageEncoding="ISO-8859-1"%>
<%@ page import='com.estate.constants.InputMaps'%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="teagtags" prefix="teag"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<! DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<c:if test="${param.action == 'Back' }" >
	<jsp:forward page="index.jsp" />
</c:if>
<c:set var='btn1' value='Add'/>
<c:set var='btn2' value='Cancel'/>
<c:if test="${empty param.action || param.action == 'edit' || param.action == 'Edit' }" >
	<c:set var='btn1' value='Update' />
	<c:set var='btn2' value='Cancel'/>
</c:if>
<html>
	<base href="<%=basePath%>">
	<title>Select Client</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
	<link href="<%=path%>/css/style.css" rel="stylesheet" media="screen">
	<body>
		<jsp:useBean id='spouse' type='com.teag.bean.PersonBean'
			scope='request' />
		<jsp:useBean id='marriage' type='com.teag.bean.MarriageBean'
			scope='request' />
		<%@ include file="/inc/aesHeader.jspf"%>
		<%@ include file="/inc/aesQuickJump.jspf"%>
		<fmt:parseDate var='bDate' pattern="MM/dd/yyyy" value="${spouse.birthDate}"/>
		<fmt:parseDate var='mDate' pattern="MM/dd/yyyy" value="${marriage.date}" />	
		<form name='iform' action='servlet/SpouseServlet' method='post'>
			<input type='hidden' name='action' value='${btn}' />
			<input type='hidden' name='id' value='${param.id}' />
			<input type='hidden' name='mid' value='${param.mid}' />
			<table width='60%' align='center'>
				<tr>
					<td colspan='2' align='center' class='text-bold'>
						<b>Spouse Data</b>
					</td>
				</tr>
				<tr>	
					<td colspan='2' align='center' class='text-red'>
					<c:out value="${param.dateErrMsg}"/>
					</td>
				</tr>
				<tr>
					<td align='right'>
						First Name :
					</td>
					<td align='left'>
						<teag:text name='firstName' value='${spouse.firstName}' />
					</td>
				</tr>
				<tr>
					<td align='right'>
						Middle Name :
					</td>
					<td align='left'>
						<teag:text name='middleName' value='${spouse.middleName}' />
					</td>
				</tr>
				<tr>
					<td align='right'>
						Last Name :
					</td>
					<td align='left'>
						<teag:text name='lastName' value='${spouse.lastName}'/>
					</td>
				</tr>
				<tr>
					<td align='right'>
						Occupation :
					</td>
					<td align='left'>
						<teag:text name='occupation' value='${spouse.occupation}' />
					</td>
				</tr>
				<tr>
					<td align="right">
						Gender :
					</td>
					<td align="left">
						<teag:select name="gender" options="<%=InputMaps.gender%>" optionValue="${spouse.gender}"/>
					</td>
				</tr>
				<tr>
					<td align='right'>
						Birthdate (mon/day/year) :
					</td>
					<td align='left'>
						<fmt:formatDate var='a1' pattern="M/d/yyyy" value="${bDate}"/>
						<teag:text name='birthDate' value='${a1}' />
					</td>
				</tr>
				<tr>
					<td colspan='2' align='center'>
						<b>Place of Birth</b>
					</td>
				</tr>
				<tr>
					<td align='right'>
						&nbsp;&nbsp;&nbsp;&nbsp;City :
					</td>
					<td align='left'>
						<teag:text name='city' value="${spouse.city}"/>
					</td>
				</tr>
				<tr>
					<td align='right'>
						&nbsp;&nbsp;&nbsp;&nbsp;State :
					</td>
					<td align='left'>
						<teag:select name='state' options="<%=InputMaps.states%>" optionValue="${spouse.state}"/>
					</td>
				</tr>
				<tr>
					<td align='right'>
						Health History :
					</td>
					<td align='left'>
						<teag:select name="healthId" options="<%=InputMaps.health%>" optionValue="${spouse.healthId}" />
					</td>
				</tr>
				<tr>
					<td colspan='2'>&nbsp;</td>
				</tr>
				<tr>
					<td align='center' colspan='2'><b>Marriage Information</b></td>
				</tr>
				<tr>
					<td align='right'>Marriage Date ; </td>
					<fmt:formatDate var='a2' pattern="M/d/yyyy" value="${mDate}"/>
					<td align='left'><teag:text name='mDate' value="${a2 }"/></td>
				</tr>
				<tr>
					<td align='right'>City : </td>
					<td align='left'><teag:text name='mCity' value="${marriage.city }" /></td>
				</tr>
				<tr>
					<td align='right'>State : </td>
					<td align='left'><teag:select name='mState' options="<%=InputMaps.states%>" optionValue="${marriage.state}"/></td>
				</tr>
				<tr>
					<td align='right'>Status : </td>
					<td align='left'><teag:select name='mStatus' options="<%=InputMaps.marriage %>" optionValue="${marriage.status}" /></td>
				</tr>
				<tr><td colspan='2'>&nbsp;</td></tr>
				<tr>
					<td colspan='2' align='center'>
						<c:if test="${btn == 'add' }">
							<input type='submit' value='Add' onclick='document.iform.action.value="insert"' />
						</c:if>
						<c:if test="${btn == 'edit' }">
							<input type='submit' value='Save' onclick='document.iform.action.value="update"' />
						</c:if>
						<input type='submit' value='${btn2}' onclick='document.iform.action.value="${btn2}"' />
						<input type='submit' value='Delete'  onclick="document.iform.action.value='delete'" />
					</td>
				</tr>
			</table>
		</form>
		<%@ include file="/inc/aesFooter.jspf"%>
	</body>
</html>
