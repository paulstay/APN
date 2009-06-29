<%@ page language="java" import="java.util.*"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://jakarta.apache.org/taglibs/request-1.0" prefix="req" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<base href="<%=basePath%>">

		<title><%=basePath%></title>

		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<link rel="stylesheet" type="text/css" href="<%=path%>/css/styles.css">
	</head>
	<%@ include file="/inc/init.jspf"%>
	<c:if test="${validUser == null}">
		<c:redirect url="/login.jsp">
			<c:param name="errorMsg" value="You are not logged in!" />
		</c:redirect>
	</c:if>
	<jsp:useBean id="validUser" scope="session"
		class="com.teag.bean.AdminBean" />
	<body>
		<%@ include file="/inc/aesHeader.jspf"%>
		<%@ include file="/inc/aesQuickJump.jspf"%>
		<div id='client-index'>
			<table class='normal' align='center'>
				<%
					if (validUser.canCreateClient()) {
				%>
				<tr>
					<td align='right' width='50%' valign='top'>
						<a href='servlet/AdminClientServlet?action=new'><b>Create
								Client</b>
						</a>:
					</td>
					<td valign="top">
						Create New Client
					</td>
				</tr>
				<%
					}
				%>
				<tr>
					<td align='right' valign='top'>
						<a href='servlet/AdminSelectClientServlet'><b>Select
								Client</b>
						</a>:
					</td>
					<td valign="top">
						Select Client from Existing Client list
					</td>
				</tr>
				<tr>
					<td align='right' valign='top'>
						<a href='admin/profile/index.jsp'><b>User Settings</b>
						</a>:
					</td>
					<td valign="top">
						Change User Settings (password, contact information).
					</td>
				</tr>
				<tr>
					<td align='right' valign='top'>
						<a href='admin/support/index.jsp'>Client Maintenance</a>:
					</td>
					<td valign='top'>
						Delete and Copy Clients
					</td>
				</tr>
				<tr>
					<td align='right' valign='top'>
						<a href='servlet/IrsRatesServlet'><b>Section 7520 rates</b>
						</a>
					</td>
					<td>
						Display the current 7520 rates
					</td>
				</tr>
				<tr>
					<td align='right' valign='top'>
						<a href='documentation/index.jsp?page=main'><b>Supporting
								Documents</b>
						</a>:
					</td>
					<td valign="top">
						User Manual, Fact Finder Forms, AFR 7520 Rates
					</td>
				</tr>
				<tr>
					<td align='right' valign='top'>
						<a href='admin/sample.jsp'><b>Load Sample Case</b>
						</a>
					</td>
					<td>
						Load The Clark and Barbara Sample Case
					</td>
				</tr>
				<tr>
					<td align='right' valign='top'>
						<a href='toolbox/index.jsp'><b>Toolbox Wizards</b>
						</a>:
					</td>
					<td valign="top">
						Wealth Preservation Toolbox Standalone Tools
					</td>
				</tr>
				<tr>
					<td align='right' valign='top'>
						<a href='admin/Webinar/index.jsp'><b>Webinars</b>
						</a>:
					</td>
					<td>
						Recorded Webinars
					</td>
				</tr>
				<tr>
					<td align='right' valign='top'>
						<a href='logout.jsp'><b>Logout</b>
						</a>:
					</td>
					<td valign="top">
						Logout of this Session
					</td>
				</tr>
			</table>
		</div>
        <req:isUserInRole role="admin">
            <hr size='3' width='60%' />
            <table class='normal' align='center'>
                <tr>
                    <td align='right' width='50%' valign='top'>
                        <a href='admin/admintools.jsp'><span class='link-color1'><u>Admin
                                Tools</u>
                            </span>
                        </a>:
                    </td>
                    <td valign="top">
                        Administrative Tools
                    </td>
                </tr>
                <tr>
                    <td align='right' width='50%' valign='top'>
                        <a href='servlet/PlannerDispatch'><span class='link-color1'><u>Planner
                                Accounts</u>
                            </span>
                        </a>:
                    </td>
                    <td valign="top">
                        Tools for Creating Planners
                    </td>
                </tr>
            </table>
        </req:isUserInRole>
		<%@ include file="/inc/aesFooter.jspf"%>
	</body>
</html>
