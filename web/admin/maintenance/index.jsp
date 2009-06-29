<%@ page language="java" import="java.util.*" %>
<%@ page import="com.teag.bean.*" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="input" uri="http://jakarta.apache.org/taglibs/input-1.0" %>
<%@ taglib prefix="teag" tagdir="/WEB-INF/tags/teag" %>
<%@ include file="/inc/init.jspf" %>
<c:if test="${validUser == null}" >
	<c:redirect url="/login.jsp">
		<c:param name="errorMsg" value="You are not logged in!" />
	</c:redirect>
</c:if> 
<jsp:useBean id="validUser" scope="session" class="com.teag.bean.AdminBean" />
<html>
	<%
		String path = request.getContextPath();
		String basePath = request.getScheme() + "://"
				+ request.getServerName() + ":" + request.getServerPort()
				+ path + "/";
	%>
	<head>
		<base href="<%=basePath%>">
		<title>Manage Organizations</title>

		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<link rel="stylesheet" type="text/css" href="<%=path%>/css/styles.css">
	</head>
	<body>
<%@ include file="/inc/aesHeader.jspf" %>
<%@ include file="/inc/aesQuickJump.jspf" %>
<%
	PlannerBean planner = new PlannerBean();
	String wClause = PlannerBean.ID + ">'0'";
	ArrayList pList = planner.getBeans(wClause);
	
	Iterator itr = pList.iterator();
	Map<String,String> pMap = new LinkedHashMap<String,String>();
	
	while(itr.hasNext()){
		PlannerBean p = (PlannerBean) itr.next();
		pMap.put(p.getFirstName() + " " + p.getLastName(), Long.toString(p.getId()) );
	}
%>
<hr size='3' width='60%' />
<input:form action="admin/maintenance/clients.jsp" method="post" name="iform">
<table class='normal' align='center'>
	<tr>
	<td colspan='2'><h3>Please Select the Planner</h3></td>
	</tr>
	<tr>
		<td><input:select name="plannerId" options="<%=pMap%>" /></td>
		<td><input type="submit" value="GO!"/></td>
	</tr>
</table>
</input:form>
<%@ include file="/inc/aesFooter.jspf" %>
</body>
</html>