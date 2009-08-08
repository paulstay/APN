<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="teagtags" prefix="teag"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    <title>User and Advisor Page</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link rel="stylesheet" type="text/css" href="<%=path %>/css/styles.css">
  </head>
  <jsp:useBean id='userInfo' scope='session' class='com.teag.bean.PdfBean'/>
  <body>
  	<c:if test="${param.pageView =='Update' }" >
		<jsp:setProperty name='userInfo' property='firstName' param='firstName'/>
  		<jsp:setProperty name='userInfo' property='lastName' param='lastName'/>
  		<jsp:setProperty name='userInfo' property='advisor1' param='advisor1'/>
  		<jsp:setProperty name='userInfo' property='advisor2' param='advisor2'/>
  		<jsp:setProperty name='userInfo' property='advisor3' param='advisor3'/>
  		<jsp:setProperty name='userInfo' property='advisor4' param='advisor4'/>
  	</c:if>
  	<!-- Insert the new Advanced Estate Strategies Logo here -->
	<%@include file="/inc/aesHeader.jspf" %>
	<h2 align='center'>
  	Charitable Lead Annuity Trust with Life Insurance
  	</h2>
	<table width='100%'>
		<tr>
			<!-- menu options -->
			<td width='25%' align='center'><%@include file='menu.jsp' %></td>
			<!-- Form -->
			<td  align='left'>
				<teag:form action="toolbox/clatWithLife/user.jsp" method="post" name="uForm" >
				<teag:hidden name="pageView" value="noUpdate"></teag:hidden>
				<table>
					<tr>
						<td colspan='3' align='center' ><h3>userInfo Information</h3></td>
					</tr>
					<tr>
						<td align='right'>First Name(s)</td>
						<td>&nbsp;</td>
						<td>
							<teag:text name='firstName' value='${userInfo.firstName}'/>
						</td>
					</tr>
					<tr>
						<td align='right'>Last Name</td>
						<td>&nbsp;</td>
						<td>
							<teag:text name='lastName' value='${userInfo.lastName}'/>
						</td>
					</tr>
					<tr>
						<td colspan='3'>&nbsp;</td>
					</tr>
					<tr>
						<td align='right'>Advisor Information </td>
						<td>&nbsp;</td>
						<td>
							<teag:text name='advisor1' value='${userInfo.advisor1}'/>
						</td>
					</tr>
					<tr>
						<td align='right'>&nbsp;</td>
						<td>&nbsp;</td>
						<td>
							<teag:text name='advisor2' value='${userInfo.advisor2}'/>
						</td>
					</tr>
					<tr>
						<td align='right'>&nbsp;</td>
						<td>&nbsp;</td>
						<td>
							<teag:text name='advisor3' value='${userInfo.advisor3}'/>
						</td>
					</tr>
					<tr>
						<td align='right'>&nbsp;</td>
						<td>&nbsp;</td>
						<td>
							<teag:text name='advisor4' value='${userInfo.advisor4}'/>
						</td>
					</tr>
					<tr>
							<td colspan='3' align='center'>
								<teag:input type="image" src="toolbtns/Update.png" alt="Update Tool"
									onclick="document.uForm.pageView.value='Update'"
									onmouseout="this.src='toolbtns/Update.png';"
									onmousedown="this.src='toolbtns/Update_down.png';" />
							</td>
					</tr>
				</table>
				</teag:form>
			</td>
		</tr>
	</table>
	<%@include file="/inc/aesFooter.jspf" %>
  </body>
</html>
