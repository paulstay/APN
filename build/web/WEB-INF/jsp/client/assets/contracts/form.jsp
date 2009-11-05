<%@ page language="java" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="teagtags" prefix="teag"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<! DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<base href="<%=basePath%>">
	<title>Business Contract Asset Edit/Add</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
	<link href="<%=path%>/css/styles.css" rel="stylesheet" media="screen">
	<body>
		<!-- insert bean here using page scope, if its an add, than the id should be passed as zero -->
		<jsp:useBean id="biz" scope='request' type="com.teag.bean.BizContractBean" />
		<c:if test="${btn == 'Add' }">
			<c:set var="btn1" value='Add' />
		</c:if>
		<c:if test="${btn == 'Edit' }">
			<c:set var="btn1" value='Edit' />
		</c:if>
		<%@ include file="/inc/aesHeader.jspf"%>
		<h3 align='center'>
			Business COntract Asset Input
		</h3>
		<br>
		<teag:form action="servlet/BizContractServlet" method="post" name="iform">
			<input type="hidden" name="action" value="cancel" />
			<input type="hidden" name="id" value="${biz.id}" />
			<input type="hidden" name="ownerId" value="${person.id}" />
			<table align='center'>
				<tr>
					<td align='right'>
						Description :
					</td>
					<td align='left'>
						<teag:text name="description" value="${biz.description}" />
					</td>
				</tr>
				<tr>
					<td align="right">
						Value :
					</td>
					<td align="left">
						<teag:text name="value" fmt="number" pattern="###,###,###"
							value="${biz.value}" />
					</td>
				</tr>
				<tr>
					<td align="right">
						Salary :
					</td>
					<td align="left">
						<teag:text name="salary" fmt="number" pattern="###,###,###"
							value="${biz.salary}" />
					</td>
				</tr>
				<tr>
					<td align="right">
						Starting Year :
					</td>
					<td align="left">
						<teag:text name="start_year" fmt="number" pattern="####" value="${biz.startYear }" />
					</td>
				</tr>
                                <tr>
					<td align="right">
						Ending Year :
					</td>
					<td align="left">
						<teag:text name="end_year" fmt="number" pattern="####" value="${biz.endYear }" />
					</td>
				</tr>
				<tr>
					<td align='center' colspan='2'>
						<c:choose>
							<c:when test="${btn=='Add' }">
								<input type='submit'
									onclick="document.iform.action.value='insert'" value='Add' />
							</c:when>
							<c:otherwise>
								<input type='submit'
									onclick="document.iform.action.value='update'" value='Edit' />
							</c:otherwise>
						</c:choose>
						<input type='submit'
							onclick="document.iform.action.value='Cancel'" value='Cancel' />
						<input type='submit'
							onclick="document.iform.action.value='delete'" value='Delete' />
					</td>
				</tr>
			</table>
		</teag:form>
		<%@ include file="/inc/aesFooter.jspf"%>
	</body>
</html>
