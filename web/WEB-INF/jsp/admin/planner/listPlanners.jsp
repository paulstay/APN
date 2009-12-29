<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="teagtags" prefix="teag"%>
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
		<title>List Planners</title>
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
		<table align='center' width='50%'>
			<tr>
				<td width='35%'>
					Name
				</td>
				<td width='20%'>
					User Name
				</td>
				<td width='15%'>
					Status
				</td>
				<td width='15%'>
					&nbsp;
				</td>
				<td width='15%'>
					&nbsp;
				</td>
			</tr>
			<c:set var='crl' value='0' />
			<c:forEach var="planner" items='${planners}'>
                            <c:if test="${planner.status == 'A'}">
				<tr class='l-color${crl%2}'>
					<td>
						${planner.firstName }&nbsp;${planner.lastName }
					</td>
					<td>
						${planner.userName }
					</td>
					<td>
						<c:if test="${planner.status == 'A' }">Active</c:if>
						<c:if test="${planner.status == 'N' }">Not Active</c:if>
					</td>
					<td>
					<form name="eForm" action="servlet/PlannerDispatch" method="post">
						<input type='hidden' name="pageView" value="edit"></input>
						<input type='hidden' name="id" value="${planner.id}"></input>
						<input type="image" src="btns/BtnEdit.png" alt="edit" height='30'
							width='60' onmouseover="this.src='btns/BtnEdit_over.png';"
							onmouseout="this.src='btns/BtnEdit.png';"
							onmousedown="this.src='btns/BtnEdit_down.png';"></input>
					</form>
					</td>
						<td>
					<form name="pForm" action="PlannerLogin"
							method="post">
							<input type='hidden' name="j_username" value="${planner.userName}"></input>
							<input type='hidden' name="j_password" value="${planner.encryptPass }"></input>
                            <input type="hidden" name="hash" value="1"/>
							<input type="image" src="btns/BtnLogin.png" alt="login" height='30' width='60'
								onmouseover="this.src='btns/BtnLogin_over.png';"
								onmouseout="this.src='btns/BtnLogin.png';"
								onmousedown="this.src='btns/BtnLogin_down.png';"></input>
					</form>
						</td>
				</tr>
				<c:set var='crl' value='${crl + 1}' />
                            </c:if>
			</c:forEach>
			<teag:form name="nForm" action="servlet/PlannerDispatch"
				method="post">
				<input type='hidden' name="pageNum" value="${page}" />
				<input type='hidden' name="pageView" value="list" />
				<tr>
					<td>
						<input type="button" value="Prev"
							onclick="document.nForm.pageNum.value='${page-1}';document.nForm.submit()">
					<td>
					<td>
						&nbsp;
					</td>
					<td>
						&nbsp;
					</td>
					<td>
						<input type="submit" value="Next"
							onclick="document.nForm.pageNum.value='${page+1}';document.nForm.submit()">
					<td>
				</tr>
				<tr>
					<td colspan='5' align='center'>
						<input type="button" value="Return to Planner Menu"
							onclick="document.nForm.pageView.value='menu';document.nForm.submit()">
					</td>
				</tr>
			</teag:form>
		</table>
		<%@include file="/inc/aesFooter.jspf"%>
	</body>
</html>
