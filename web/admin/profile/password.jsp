<%@ page language="java" import="java.util.*" %>
<%@ page import="com.teag.util.*" %>
<%@page import="com.teag.util.StringUtil"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="/inc/init.jspf" %>
<c:if test="${validUser == null}" >
	<c:redirect url="/login.jsp">
		<c:param name="errorMsg" value="You are not logged in!" />
	</c:redirect>
</c:if> 
<jsp:useBean id="validUser" scope="session" class="com.teag.bean.AdminBean" />
<jsp:useBean id="client" scope="session" class="com.teag.bean.ClientBean"/>
<%
	String action = request.getParameter("action");
	
	if( action == null || action.equals("cancel")) 
	{
%>
	<c:redirect url="./index.jsp"/>	
<%
	}
	else if( action.equals("modify") ) 
	{
		String oldPass = request.getParameter("oldPass");
		String newPass1 = StringUtil.removeChar(request.getParameter("newPass1"),"'".charAt(0));
		String newPass2 = StringUtil.removeChar(request.getParameter("newPass2"),"'".charAt(0));

		long plannerId = validUser.getId();
		sys.dbobj.start();
		sys.dbobj.setTable("PLANNER");
		String sql = "Select * from PLANNER where PLANNER_ID=" + plannerId;
		HashMap rec = sys.dbobj.execute(sql);
		sys.dbobj.stop();


		if( rec != null ) 
		{
			if( newPass1.equals(newPass2)) 
			{
				sys.dbobj.start();
				sys.dbobj.setTable("PLANNER");
				sys.dbobj.addField("PASSWORD", DBObject.dbString(newPass1));
				sys.dbobj.setWhere("PLANNER_ID =" + plannerId + "");
				int err = sys.dbobj.update();
				sys.dbobj.stop();
%>
<c:redirect url="index.jsp"/>	
<%
			}
			else
			{	// The passwords didn't match
%>
<c:redirect url="password.jsp">	
	<c:param name="action" value="retry" />
</c:redirect>
<%			
			}
		}
	} 
	else  if (action.equals("add") || action.equals("retry"))
	{
%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"
	+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title><%=basePath %></title>
    
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="This is my page">
    <link rel="stylesheet" type="text/css" href="<%=path%>/css/styles.css">
  </head>
<%@ include file="/inc/aesHeader.jspf" %>
<%@ include file="/inc/aesQuickJump.jspf" %>
	<form name='iform' action='admin/profile/password.jsp' method='post'>
	<input type='hidden' name='action' value='cancel'/>
	<table width='90%' align='center'>
<%		if(action.equals("retry"))
		{
%>
		<tr>
			<td align='center' colspan='2'><b>ERROR in changing the password.</b></td>
			<td>&nbsp;</td>
		</tr>
<%		}	%>			
		<tr>
			<td align='right' width='50%'>Enter Old Password : </td>
			<td align='left'><input type="password" name="oldPass" size="14"/></td>
		</tr>
		<tr>
			<td align='right'>Enter New Password : </td>
			<td align='left'><input type="password" name="newPass1" size="14"/></td>
		</tr>
		<tr>
			<td align='right'>ReEnter New Password : </td>
			<td align='left'><input type="password" name="newPass2" size="14"/></td>
		</tr>
		<tr>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
		</tr>
		<tr>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
		</tr>
		<tr>
		<td colspan='3' align='center'>
			<button type='submit' 
				onclick="document.iform.action.value='modify'" >Change</button>
			<button type='submit' 
				onclick="document.iform.action.value='cancel'" >Cancel</button>
		</td>
		</tr>
	</table>
	</form>
<%@ include file="/inc/aesFooter.jspf" %>
<%
	} else {
%>
	<c:redirect url="index.jsp"/>	
<%
	}
%>
