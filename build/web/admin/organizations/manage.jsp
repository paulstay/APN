<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ page language="java" import="java.util.*"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.teag.bean.*"%>
<jsp:useBean id="validUser" scope="session"
	class="com.teag.bean.AdminBean" />
<%
	try {
%>
<c:if test="${validUser == null}">
	<c:redirect url="/login.jsp">
		<c:param name="errorMsg" value="You are not logged in!" />
	</c:redirect>
</c:if>
<html>
	<%
		String path = request.getContextPath();
			String basePath = request.getScheme() + "://"
					+ request.getServerName() + ":"
					+ request.getServerPort() + path + "/";
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
		<%@include file="/inc/aesHeader.jspf"%>
		<%@include file="/inc/aesQuickJump.jspf"%>
		<table cellspacing="0" cellpadding="0" align="center" width='100%'>
			<tr>
				<td colspan="3" align="center">
					<h2>
						<span class='title-cap'>&nbsp;</span>
					</h2>
				</td>
			</tr>
			<tr valign='top'>
				<td colspan="3">
					<%
						} catch (NullPointerException npe) {
							System.out.println(npe.getCause());
						}

						if (validUser.isAdmin() == false) {
							System.out.println("not admin");
							return;
						}

						long orgID = Long.valueOf(request.getParameter("orgID"))
								.longValue();

						OrgBean org1 = new OrgBean();
						OrgBean subOrg = new OrgBean();
						org1.setId(orgID);
						org1.initialize();
						PlannerBean planner = new PlannerBean();
						String orgName = org1.getOrgName();
						ArrayList<OrgBean> subOrgs = org1.getSubOrgs();
						request.setAttribute("cList", subOrgs);
					%>
					<form name='iform' id='iform' action='admin/organizations/add.jsp'
						method='post'>
						<input type='hidden' name='action' value='' />
						<input type='hidden' name='orgid' value='' />
						<input type='hidden' name='orglevel' value='' />
						<input type='hidden' name='plannerid' value='' />
						<table width="90%" align="center">
							<tr>
								<td colspan="2" align="Center">
									<span class='header-bold'>Manage organizations under <%=orgName%></span>
								</td>
							</tr>
						</table>
						<hr size='3' width='700px' />
						<c:set var="idx" value="0"/>
						<table width="700px" align="center">
							<c:forEach var="s" items="${cList}" >
							<tr>
								<td class='bg-color${idx%2 +2}'>
									<span class='text-bold'>${s.orgName}</span>
								</td>
								<td width='10%' align='center'>
									<a class='button_normal'
										href="admin/organizations/manage.jsp?level=${s.orgLevel}&orgID=${s.id}">Select</a>
								</td>
								<td width='10%' align='center'>
									<a onclick='document.iform.addorg.focus();'
										class='button_normal'
										href="admin/organizations/editorg.jsp?id=<%=subOrg.getId()%>&orglevel=<%=subOrg.getOrgLevel()%>">Edit</a>
								</td>

							</tr>
							<c:set var="idx" value="${idx+1}"/>
							</c:forEach>
							<tr>
								<td colspan='10' align='center'>
									<button name='addorg' type='submit'
										onclick='document.iform.orglevel.value="<%=org1.getOrgLevel()%>"; document.iform.action.value="org"; document.iform.orgid.value="<%=org1.getId()%>"'>
										Add
									</button>
								</td>
							</tr>

						</table>
						<span class='header-bold'><br> </span>
						<table width="90%" align="center">
							<tr>
								<td colspan="2" align="Center">
									<span class='header-bold'>Manage planners under <%=orgName%></span>
								</td>
							</tr>
						</table>
						<hr size='3' width='700px' />

						<table width="700px" align="center">
							<%
								int n = 0;
								String cssClass = "bg-color2";
								String lnkColor = "link-color1";
								int count = (int) org1.getPlannerCount();

								for (int i = 0; i < count; i++) {
									planner.setId(org1.getPlannerIDs(i));
									planner.initialize();

									if (n % 2 == 0) {
										cssClass = "bg-color2";
										lnkColor = "link-color1";
									} else {
										cssClass = "bg-color3";
										lnkColor = "link-color2";
									}
									n++;
									String userName = planner.getUserName();
									String password = planner.getPassword();
							%>
							<tr>
								<td class='<%=cssClass%>'>
									<span class='text-bold'><%=planner.getFirstName() + " "
						+ planner.getLastName()%></span>
								</td>
								<td width='10%' align='center'>
									<a onclick='document.iform.addplanner.focus();'
										class='button_normal'
										href="admin/organizations/editplanner.jsp?orglevel=<%=org1.getOrgLevel()%>&orgid=<%=org1.getId()%>&plannerid=<%=planner.getId()%>">Edit</a>
								</td>
								<td width='10%' align='center'>
									<a
										href="admin/organizations/login.jsp?password=<%=password%>&username=<%=userName%>">Login</a>
								</td>
							</tr>
							<%
								}
							%>
							<tr>
								<td colspan='10' align='center'>
									<button name='addplanner' type='submit'
										onclick='document.iform.orglevel.value="<%=org1.getOrgLevel()%>"; document.iform.action.value="planner"; document.iform.orgid.value="<%=org1.getId()%>"'>
										Add
									</button>
								</td>
							</tr>
						</table>
					</form>
			</tr>
		</table>
		<%@include file="/inc/aesFooter.jspf"%>
	</body>
</html>
