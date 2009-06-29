<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:if test="${param.pageView == 'User' }" >
	<jsp:forward page='user.jsp' />
</c:if>
<c:if test="${param.pageView == 'Tool' }">
	<jsp:forward page='tool.jsp' />
</c:if>
<c:if test="${param.pageView == 'Summary' }">
	<jsp:forward page='summary.jsp' />
</c:if>
<c:if test="${param.pageView == 'Report' }">
	<jsp:forward page='report.jsp' />
</c:if>
<c:if test="${param.pageView == 'Cancel' }">
	<jsp:forward page='../index.jsp' />
</c:if>
