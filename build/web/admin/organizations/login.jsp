<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="/inc/init.jspf" %>
<c:remove var="validUser"/>
<jsp:useBean id="validUser" scope="session" class="com.teag.bean.AdminBean" >
	<c:set target="${validUser}" property="userName" value="${param.username}" />
	<c:set target="${validUser}" property="password" value="${param.password}" />
</jsp:useBean>
<%
	validUser.initialize(); 
	if( validUser.isAuthorized()) {
		sys.dbobj.setUserName(validUser.getUserName());
		System.out.println("User: " + validUser.getUserName() + " has logged in");
	}
%>
<c:if test="${ !validUser.authorized}">
		<c:redirect url='login.jsp' >
		   <c:param name='errorMsg' value="Invalid Username and Password, Please try again" />
		 </c:redirect>
</c:if>
<jsp:forward page='/admin/index.jsp'/>