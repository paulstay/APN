<%@ page language="java" import="java.util.*" %>
<%@ page import="com.teag.util.*" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="/inc/init.jspf" %>
<c:if test="${validUser == null}" >
	<c:redirect url="/login.jsp">
		<c:param name="errorMsg" value="You are not logged in!" />
	</c:redirect>
</c:if> 
<jsp:useBean id="validUser" scope="session" class="com.teag.bean.AdminBean" />
<%
	String personId = request.getParameter("personId");
	String clientId = request.getParameter("clientId");
	String plannerId = request.getParameter("plannerId");
	DeleteClient dc = new DeleteClient();
	dc.setClientId(Long.parseLong(clientId));
	dc.setOwnerId(Long.parseLong(personId));
	dc.deleteRecords();
%>
<c:redirect url="clients.jsp">
	<c:param name="plannerId" value='<%=plannerId%>' />
</c:redirect>

