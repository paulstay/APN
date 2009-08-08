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
	String org_name = request.getParameter("org_name");
	String org_addr1 = request.getParameter("org_addr1");
	String org_addr2 = request.getParameter("org_addr2");
	String org_City = request.getParameter("org_city");
	String org_state = request.getParameter("org_state");
	String org_zip = request.getParameter("org_zip");
	String org_phone = request.getParameter("org_phone");
	String org_fax = request.getParameter("org_fax");

	long parentOrg = Long.valueOf(request.getParameter("parentOrg")).longValue();
	long orgLevel = Long.valueOf(request.getParameter("orgLevel")).longValue();	

	if(action.equals("add"))
	{	
		OrgBean parent = new OrgBean();
		parent.setId(parentOrg);
		parent.initialize();
		OrgBean org = new OrgBean();
		org.setAddress1(org_addr1);
		org.setAddress2(org_addr2);
		org.setCity(org_City);
		org.setFax(org_fax);
		org.setOrgName(org_name);
		org.setParentId(parentOrg);
		org.setPhone(org_phone);
		org.setState(org_state);
		org.setZip(org_zip);
		org.setOrgLevel(parent.getOrgLevel()+1);
		org.insert();
		


	}	
	else if(action.equals("update"))
	{
		long orgID = Long.valueOf(request.getParameter("orgId")).longValue();
		OrgBean org = new OrgBean();
		org.setId(orgID);
		org.initialize();
		org.setAddress1(org_addr1);
		org.setAddress2(org_addr2);
		org.setCity(org_City);
		org.setFax(org_fax);
		org.setOrgName(org_name);
		org.setPhone(org_phone);
		org.setState(org_state);
		org.setZip(org_zip);
		org.update();
	}
%>
	<c:redirect url="manage.jsp" >
		<c:param name="orgID" value="${param.parentOrg}" />
		<c:param name="level" value="${param.orgLevel}" />
	</c:redirect>
