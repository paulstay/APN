<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%@ page import="com.teag.bean.*"%>
<%@ page import="com.teag.bean.*"%>
<%@ page import="com.teag.client.*"%>
<%@ page import="com.estate.constants.*"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
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

		<title>View Client</title>

		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<link rel="stylesheet" type="text/css" href="css/styles.css">
	</head>
	<body>
		<jsp:useBean id="validUser" scope="session"
			class="com.teag.bean.AdminBean" />
		<jsp:useBean id="client" scope="session"
			class="com.teag.bean.ClientBean" />
		<jsp:useBean id="person" scope="page" class="com.teag.bean.PersonBean" />
		<%@ include file="/inc/aesHeader.jspf"%>
		<%@ include file="/inc/aesQuickJump.jspf"%>
		<br>
		<teag:form name='uform' action="servlet/NewClientFormServlet" method="post">
			<teag:hidden name="action" value="cancel"/>
			<table width='70%' align='center'>
				<tr class='header-bold-blue'>
					<td colspan='3' align='center'>
						Client Personal Data
					</td>
				</tr>
				<tr>
					<td colspan='3'>
						&nbsp;
					</td>
				</tr>
				<tr class='text-bold'>
					<td align='right' width='40%'>
						First Name :
					</td>
					<td width='10%'>
						&nbsp;
					</td>
					<td width='40%'><teag:text name="firstName" value="" type="text"/>
					</td>
				</tr>
				<tr class='text-bold'>
					<td align='right' width='40%'>
						Middle Name :
					</td>
					<td width='10%'>
						&nbsp;
					</td>
					<td width='40%'><teag:text name="middleName" value="" type="text"/></td>
				</tr>
				<tr class='text-bold'>
					<td align='right' width='40%'>
						Last Name :
					</td>
					<td width='10%'>
						&nbsp;
					</td>
					<td width='40%'><teag:text name="lastName" value="" type="text"/></td>
				</tr>
				<tr class='text-bold'>
					<td align='right' width='40%'>
						Occupation :
					</td>
					<td width='10%'>
						&nbsp;
					</td>
					<td width='40%'><teag:text name="occupation" value="" />
					</td>
				</tr>
				<tr class='text-bold'>
					<td align='right' width='40%'>
						Gender :
					</td>
					<td width='10%'>
						&nbsp;
					</td>
					<td>
						<teag:select name="gender" options="<%=InputMaps.gender%>" optionValue="" />
					</td>
				</tr>
				<tr class='text-bold'>
					<td align='right' width='40%'>
						Health Insurance :
					</td>
					<td width='10%'>
						&nbsp;
					</td>
					<td width='40%'>
						<teag:select name="healthId" options="<%=InputMaps.health %>" optionValue="" />
					</td>
				</tr>
				<tr class='text-bold'>
					<td align='right' width='40%'>
						Birth Date (mm/dd/yyyy):
					</td>
					<td width='10%'>
						&nbsp;
					</td>
					<td width='40%'><teag:text name="birthDate" value="" type="text" />
					</td>
				</tr>
				<tr>
					<td colspan='3'>
						&nbsp;
					</td>
				</tr>
				<tr class='header-bold-blue'>
					<td colspan='3' align='center'>
						Place of Birth
					</td>
				</tr>
				<tr class='text-bold'>
					<td align='right' width='40%'>
						City :
					</td>
					<td width='10%'>
						&nbsp;
					</td>
					<td width='40%'><teag:text type="text" name="city" value="" />
					</td>
				</tr>
				<tr class='text-bold'>
					<td align='right' width='40%'>
						State :
					</td>
					<td width='10%'>
						&nbsp;
					</td>
					<td width='40%'>
						<teag:select name="state" options="<%=InputMaps.states%>" optionValue="" />
					</td>
				</tr>
				<tr>
					<td colspan='3'>
						&nbsp;
					</td>
				</tr>
				<tr>
					<td colspan='3' align='center'>
						<button type="submit" onclick="document.uform.action.value='add'">Add</button>
						<button type="submit" onclick="document.uform.action.value='cancel'">Cancel</button>
					</td>
				</tr>
			</table>
		</teag:form>
		<br>
		<div align='center'>
			<a href="admin/index.jsp">Back to Main Menu</a>
		</div>
		<%@ include file="/inc/aesFooter.jspf"%>
	</body>
</html>
