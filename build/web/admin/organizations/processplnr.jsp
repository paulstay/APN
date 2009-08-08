<%@ page language="java" import="com.teag.bean.*" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="/inc/init.jspf" %>
<c:if test="${validUser == null}" >
	<c:redirect url="/login.jsp">
		<c:param name="errorMsg" value="You are not logged in!" />
	</c:redirect>
</c:if>
<jsp:useBean id="validUser" scope="session" class="com.teag.bean.AdminBean"/>
<%
	String action = request.getParameter("action");
	String plnr_firstname = request.getParameter("plnr_firstname");
	String plnr_middlename = request.getParameter("plnr_middlename");
	String plnr_lastname = request.getParameter("plnr_lastname");
	String plnr_suffix = request.getParameter("plnr_suffix");
	String plnr_addr1 = request.getParameter("plnr_addr1");
	String plnr_addr2 = request.getParameter("plnr_addr2");
	String plnr_city = request.getParameter("plnr_city");
	String plnr_state = request.getParameter("plnr_state");
	String plnr_zip = request.getParameter("plnr_zip");
	String plnr_phone = request.getParameter("plnr_phone");
	String plnr_fax = request.getParameter("plnr_fax");
	String plnr_email = request.getParameter("plnr_email");
	String plnr_username = request.getParameter("plnr_username");
	String plnr_password = request.getParameter("plnr_password");
	
	long orgId = Long.valueOf(request.getParameter("orgid")).longValue();

	if(action.equals("add"))
	{	
			PlannerBean planner = new PlannerBean();
	
			planner.setFirstName(plnr_firstname);
			planner.setMiddleName(plnr_middlename);
			planner.setLastName(plnr_lastname);
			planner.setSuffix(plnr_suffix);
			planner.setAddress1(plnr_addr1);
			planner.setAddress2(plnr_addr2);
			planner.setCity(plnr_city);
			planner.setState(plnr_state);
			planner.setZipcode(plnr_zip);
			planner.setPhone(plnr_phone);
			planner.setFax(plnr_fax);
			planner.setEmail(plnr_email);
			planner.setPassword(plnr_password);
			planner.setUserName(plnr_username);
			planner.setOrgId(orgId);
			planner.insert();
	}	
	else if(action.equals("update"))
	{
		PlannerBean planner = new PlannerBean();
		long plnrId = Long.valueOf(request.getParameter("plannerid")).longValue();
		planner.setId(plnrId);
		planner.initialize();	// load the info from the Database
		planner.setFirstName(plnr_firstname);
		planner.setMiddleName(plnr_middlename);
		planner.setLastName(plnr_lastname);
		planner.setSuffix(plnr_suffix);
		planner.setAddress1(plnr_addr1);
		planner.setAddress2(plnr_addr2);
		planner.setCity(plnr_city);
		planner.setState(plnr_state);
		planner.setZipcode(plnr_zip);
		planner.setPhone(plnr_phone);
		planner.setFax(plnr_fax);
		planner.setEmail(plnr_email);
		
		if(plnr_password.length() > 0)
		{	
			planner.setPassword(plnr_password);
		}
		
		if(plnr_username.length() > 0)
		{
			planner.setUserName(plnr_username);
		}
		
		planner.update();
	} else if( action.equals("delete")) {
		long plnrId = Long.valueOf(request.getParameter("plannerid")).longValue();
		sys.dbobj.start();
		sys.dbobj.delete("PLANNER", "PLANNER_ID='" + plnrId + "'");
		sys.dbobj.stop();
	}
%>
	<c:redirect url="manage.jsp" >
		<c:param name="orgID" value="${param.orgid}" />
		<c:param name="level" value="${param.orgLevel}" />
	</c:redirect>
