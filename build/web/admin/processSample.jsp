<%@ page language="java" import="java.util.*" %>
<%@ page import="com.teag.sample.*" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="/inc/init.jspf" %>
<c:if test="${validUser == null}" >
	<c:redirect url="/login.jsp">
		<c:param name="errorMsg" value="You are not logged in!" />
	</c:redirect>
</c:if> 
<jsp:useBean id="validUser" scope="session" class="com.teag.bean.AdminBean" />
<%
	String action = request.getParameter("action");
	LoadSampleCase lsc = new LoadSampleCase();
	if( action != null && action.equals("C")) {
		lsc.copyData(validUser.getId(), 87, 98, false);
	} else if( action.equals("B")) {
		lsc.copyData(validUser.getId(), 1594, 1595, false);
	} else if( action.equals("N") ) {
		lsc.copyData(validUser.getId(), 887, 0, true);
	} else if( action.equals("K")) {
		lsc.copyData(validUser.getId(), 3359,0, true);
	}
%>
<c:redirect url="index.jsp" />