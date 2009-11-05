<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="/inc/init.jspf" %>
<c:if test="${validUser == null}" >
	<c:redirect url="/login.jsp">
		<c:param name="errorMsg" value="You are not logged in!" />
	</c:redirect>
</c:if>
<jsp:useBean id="validUser" scope="session" class="com.teag.bean.AdminBean"/>
<%
	String id = request.getParameter("orgid");
	String action = request.getParameter("action");
	
	if(action.equals("org"))
	{

//<jsp:forward page="creatorg.jsp"/>
%>
<c:redirect url="creatorg.jsp">
	<c:param name="id" value="${param.orgid}" />
	<c:param name="orglevel" value="${param.orglevel}" />
</c:redirect>

<%
	}else if( action.equals("planner"))
	{
	
%>
<c:redirect url="creatplanner.jsp">
	<c:param name="orgid" value="${param.orgid}" />
	<c:param name="orglevel" value="${param.orglevel}" />
	
</c:redirect>

<%
	}	
	
%>