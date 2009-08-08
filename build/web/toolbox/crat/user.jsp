<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%@ page import="com.estate.constants.*" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://jakarta.apache.org/taglibs/input-1.0"
	prefix="input"%>
<%@ taglib tagdir="/WEB-INF/tags/teag" prefix="teag"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>User Information</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link rel="stylesheet" type="text/css" href="<%=path%>/css/styles.css">
  </head>
  <jsp:useBean id='userInfo' scope='session' class='com.teag.bean.PdfBean'/>
  <body>
  <%@include file="/inc/aesHeader.jspf" %>
  <h3 align='center'>Charitable Lead Annuity Trust</h3>
  <h4 align='center'>User Information</h4>
  <c:if test="${param.pageView == 'Update'}">
  		<jsp:setProperty name='userInfo' property='firstName' param='firstName'/>
  		<jsp:setProperty name='userInfo' property='middleName' param='middleName'/>
  		<jsp:setProperty name='userInfo' property='lastName' param='lastName'/>
  		<jsp:setProperty name='userInfo' property='address' param='address'/>
  		<jsp:setProperty name='userInfo' property='city' param='city'/>
  		<jsp:setProperty name='userInfo' property='state' param='state'/>
  		<jsp:setProperty name='userInfo' property='zipCode' param='zipCode'/>
  		<jsp:setProperty name='userInfo' property='phone' param='phone'/>
  		<jsp:setProperty name='userInfo' property='age' param='age'/>
  		<jsp:setProperty name='userInfo' property='finalDeath' param='finalDeath'/>
  		<jsp:setProperty name='userInfo' property='gender' param='gender'/>
  </c:if>
  <table>
  	<tr>
  		<td><%@include file='menu.jsp' %></td>
  		<td>
  			<input:form name='uForm' action='toolbox/crt/user.jsp' method='post'>
  			<input type='hidden' name='pageView' value='noUpdate'/>
  			<table>
  				<tr>
  					<td colspan='3'>
  						User information can be entered here. This information will allow a cover page to be generated, as well as provide some details
  						that are necessary for many of the tools, such as the user's age.
  					</td>
  				</tr>
  				<tr>
  					<td colspan='3'>&nbsp;</td>
  				</tr>
  				<tr>
  					<td align='right'>First Name(s) :</td><td>&nbsp;</td>
  					<td>
  						<input:text name='firstName' default='${userInfo.firstName }'/>
  					</td>
  				</tr>
  				<tr>
  					<td align='right'>Middle Initial :</td><td>&nbsp;</td>
  					<td>
  						<input:text name='middleName' default='${userInfo.middleName }'/>
  					</td>
  				</tr>
  				<tr>
  					<td align='right'>Last Name :</td><td>&nbsp;</td>
  					<td>
  						<input:text name='lastName' default='${userInfo.lastName }'/>
  					</td>
  				</tr>
  				<tr>
  					<td align='right'>Gender :</td><td>&nbsp;</td>
  					<td>
  						<input:select name='gender' options='<%=InputMaps.gender %>'/>
  					</td>
  				</tr>
  				<tr>
  					<td align='right'>Address :</td><td>&nbsp;</td>
  					<td>
  						<input:text name='address' default='${userInfo.address }'/>
  					</td>
  				</tr>
  				<tr>
  					<td align='right'>City :</td><td>&nbsp;</td>
  					<td>
  						<input:text name='city' default='${userInfo.city }'/>
  					</td>
  				</tr>
  				<tr>
  					<td align='right'>State :</td><td>&nbsp;</td>
  					<td>
	  					<input:select name="state" options="<%=InputMaps.states %>" default="${userInfo.state}" />
  					</td>
  				</tr>
				<tr>
					<td align='right'>Zipcode :</td><td>&nbsp;</td>
					<td>
					<input:text name='zipCode' default='${userInfo.zipCode }'/>
					</td>
				</tr>  				
				<tr>
					<td align='right'>Phone :</td><td>&nbsp;</td>
					<td>
					<input:text name='phone' default='${userInfo.phone }'/>
					</td>
				</tr>  				
				<tr>
					<td align='right'>Age :</td><td>&nbsp;</td>
					<td>
					<fmt:formatNumber var='a1' pattern='###' value='${userInfo.age }'/>
					<input:text name='age' default='${a1}'/>
					</td>
				</tr>
				<tr>
					<td align='right'>Final Death Calculation :</td><td>&nbsp;</td>
					<td>
						<fmt:formatNumber var='fd' pattern='###' value='${userInfo.finalDeath }'/>
						<input:text name='finalDeath' default='${fd }'/>
					</td>
				</tr>
				<tr>
					<td colspan='3' align='center'>
						<input type="image" src="toolbtns/Update.png" alt="Update Tool"
									onclick="document.uForm.pageView.value='Update'"
									onmouseover="this.src='toolbtns/Update_over.png'"
									onmouseout="this.src='toolbtns/Update.png'"
									onmousedown="this.src='toolbtns/Update_down.png'"/>
					</td>
				</tr>  				
  			</table>
  			</input:form>
  		</td>
  	</tr>
  </table>
  <%@include file="/inc/aesFooter.jspf" %>
  </body>
</html>
