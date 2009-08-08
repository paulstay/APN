<%@ page language="java" import="java.util.*" %>
<%@ page import="com.teag.util.*" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib tagdir="/WEB-INF/tags/teag" prefix="teag" %>
<%@ include file="/inc/init.jspf" %>
<c:if test="${validUser == null}" >
	<c:redirect url="/login.jsp">
		<c:param name="errorMsg" value="You are not logged in!" />
	</c:redirect>
</c:if> 
<jsp:useBean id="validUser" scope="session" class="com.teag.bean.AdminBean" />
<jsp:useBean id='delClient' scope='page' class='com.teag.util.DeleteClient'/>
<c:if test="${param.confirm =='Y' }">
	<jsp:setProperty name='delClient' property='clientId' param='clientId'/>
	<jsp:setProperty name='delClient' property='ownerId' param='personId'/>
<%
	delClient.deleteRecords();
%>
</c:if>
<c:redirect url="./index.jsp">
	<c:param name="plannerId" value='${validUser.id}' />
</c:redirect>
