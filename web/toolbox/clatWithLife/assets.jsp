<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="teagtags" prefix="teag"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<base href="<%=basePath%>">
		<title>Assets</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<link rel="stylesheet" type="text/css" href="<%=path %>/css/styles.css">
	</head>
	<body>
	<jsp:useBean id='aList' scope='session' class='com.estate.beans.AssetList'/>
	<c:if test="${param.pageView == 'Add'}">
		<jsp:useBean id='asset' scope='page' class='com.estate.beans.UserAssets'/>
		<jsp:setProperty name='asset' property='description' param='description'/>
		<fmt:parseNumber var='fmv' value='${param.fmv}' /> 
		<jsp:setProperty name='asset' property='fmv' value='${fmv}'/>
		<fmt:parseNumber var='basis' value='${param.basis}' /> 
		<jsp:setProperty name='asset' property='basis' value='${basis}'/>
		<teag:parsePercent var='grw' value='${param.growth }' />
		<jsp:setProperty name='asset' property='growth' value='${grw }'/>
		<teag:parsePercent var='income' value='${param.income }' />
		<jsp:setProperty name='asset' property='income' value='${income }'/>
		<c:set target="${aList}" property="asset" value='${asset }'/>
	</c:if>
	<c:if test="${param.pageView == 'Delete' }">
		<c:set target='${aList }' property='delete' value='${param.id }'/>
	</c:if>
		<%@include file="/inc/aesHeader.jspf" %>
		<h2 align='center'>
			Charitable Lead Annuity Trust with Life Insurance
		</h2>
		<table width='100%'>
			<tr>
				<!-- menu options -->
				<td width='25%' align='center'><%@include file='menu.jsp'%></td>
				<!-- Form -->
				<td align='left' valign='top'>
					<teag:form action="toolbox/clatWithLife/assets.jsp" method="post" name="uForm">
					<teag:hidden name='pageView' value='noAdd'/>
					<teag:hidden name='id' value='0'/>
						<table width='100%'>
							<tr>
								<td colspan='5' align='center'>
									<h1>
										Assets
									</h1>
								</td>
							</tr>
							<tr class='bg-color6'>
								<td width='20%'>
									Description
								</td>
								<td width='20%' align='right'>
									Value
								</td>
								<td width='20%' align='right'>
									Basis
								</td>
								<td width='5%' align='right'>
									Growth %
								</td>
								<td width='5%' align='right'>
									Income %
								</td>
								<td width='5%'>
									&nbsp;
								</td>
							
							</tr>
							<!-- Add existing assets here, they are probably in an array list with the asset bean -->
							<c:forEach items='${aList.assetList}' var='a'>
								<tr>
									<td>${a.description}</td>
									<td align='right'><fmt:formatNumber pattern="###,###,###" value='${a.fmv }'/></td>
									<td align='right'><fmt:formatNumber pattern="###,###,###" value='${a.basis }'/></td>
									<td align='right'><fmt:formatNumber pattern="##.##%" value='${a.growth }'/></td>
									<td align='right'><fmt:formatNumber pattern="##.##%" value='${a.income }'/></td>
									<td align='right'>
										<teag:input type='image' src='toolbtns/Delete.png' 
											onclick="document.uForm.pageView.value='Delete';document.uForm.id.value='${a.id}'"
											onmouseout="this.src='toolbtns/Delete.png'"
											onmousedown="this.src='toolbtns/DeleteDown.png'" />
									</td>
								</tr>
								<tr>
									<td colspan='5' align='center'><hr size='1' color='#0a0a0a' width='80%'/></td>
								</tr>
							</c:forEach>
							<tr>
								<td colspan='5'>&nbsp;</td>
							</tr>
							<tr>
								<td colspan='5'><hr size='2' color='#0f0fff' width='100%'/></td>
							</tr>
							<tr>
								<td><teag:text name='description' fmt='text' value='Enter Description here'/></td>
								<td><teag:text name='fmv' fmt='number' value='10000000'/></td>
								<td><teag:text name='basis' fmt='number' value='1500000'/></td>
								<td><teag:text name='growth' fmt='percent' value='.06'/></td>
								<td><teag:text name='income' fmt='percent' value='.05'/></td>
								<td>&nbsp;</td>
							</tr>
							<tr>
								<td colspan='5'><hr size='2' width='100%' color='#0f0fff' /></td>
							</tr>
							<tr>
								<td colspan='5' align='center'>
									<teag:input type="image" src="toolbtns/Add.png" alt="Add Tool"
										onclick="document.uForm.pageView.value='Add'"
										onmouseout="this.src='toolbtns/Add.png';"
										onmousedown="this.src='toolbtns/AddDown.png';" />
								</td>
							</tr>
						</table>
					</teag:form>
				</td>
			</tr>
		</table>
		<%@include file="/inc/aesFooter.jspf" %>
	</body>
</html>
