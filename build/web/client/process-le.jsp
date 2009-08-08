<%@ page import="com.teag.bean.*" %>
<%@ page language="java" import="java.util.*" %>
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
	PersonBean person = new PersonBean();
	long id = Long.valueOf(request.getParameter("id")).longValue();
	String strLe = request.getParameter("lifeExpectancy");
	int le=0;
	if(strLe.length() > 0)
	{
		le = Integer.valueOf(strLe).intValue();
	}
	
	// = Integer.valueOf().intValue();
	person.setId(id);
	person.initialize();
		
	
	if( action == null) 
		action = "cancel";

	if( action.equals("add") ) {
		if(le != 0)
		{
			person.setLifeExpectancy(le);
			person.update();
			System.out.println("Added LE");
		}
	} else if( action.equals("update")) {
			person.setLifeExpectancy(le);
			person.update();
	}
%>
	<c:redirect url="personal-index.jsp" />
