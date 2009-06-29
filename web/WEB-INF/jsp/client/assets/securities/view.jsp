<%@ page language="java" import="com.teag.bean.*"
	pageEncoding="ISO-8859-1"%>
<%@ page import="com.estate.constants.*"%>
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
	<title>Securities</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
	<link href="<%=path%>/css/style.css" rel="stylesheet" media="screen">
	<body>
		<jsp:useBean id="sec" scope="request"
			type="com.teag.bean.SecuritiesBean" />
		<%@ include file="/inc/aesHeader.jspf"%>
		<h3 align='center'>
			Securities Details
		</h3>
		<br>
		<table align='center' width='70%'>
			<tr>
				<td align="right">
					Description :
				</td>
				<td align="left">
					${sec.description}
				</td>
			</tr>
			<tr>
				<td align="right">
					Ownership :
				</td>
				<td align="left">
					<teag:ownership value="${sec.ownershipId}" />
				</td>
			</tr>
			<tr>
				<td align="right">
					Title :
				</td>
				<td align="left">
					<teag:title value="${sec.titleId }" />
				</td>
			</tr>
			<tr>
				<td align="right">
					Amount :
				</td>
				<td align="left">
					<fmt:formatNumber value="${sec.value }" pattern="###,###,###" />
				</td>
			</tr>
			<tr>
				<td align="right">
					Basis :
				</td>
				<td align="left">
					<fmt:formatNumber value="${sec.basis}" pattern="###,###,###" />
				</td>
			</tr>
			<tr>
				<td align="right">
					Interest Rate/Dividend :
				</td>
				<td align="left">
					<fmt:formatNumber value="${sec.divInt}" pattern="##.####%" />
				</td>
			</tr>
			<tr>
				<td align="right">
					Growth :
				</td>
				<td align="left">
					<fmt:formatNumber value="${sec.growthRate}" type='percent' />
				</td>
			</tr>
			<tr>
				<td align='right'>
					Capital Gains Turnover Rate
				</td>
				<td align='left'>
					<fmt:formatNumber type='percent' value='${sec.cgTurnover}' />
				</td>
			</tr>
			<tr>
				<td align="right">
					Bank :
				</td>
				<td align="left">
					${sec.bank}
				</td>
			</tr>
			<tr>
				<td align="right">
					Notes :
				</td>
				<td align="left">
					${sec.notes}
				</td>
			</tr>
			<tr>
				<td align='right'>
					<teag:form name="bform" action="servlet/SecuritiesAssetServlet"
						method="post">
						<input type='submit' value="Back" />
					</teag:form>
				</td>
				<td align='left'>
					<teag:form name='eform' action="servlet/SecuritiesAssetServlet"
						method="post">
						<input type="hidden" name="action" value="edit" />
						<input type="hidden" name="id" value="${sec.id}" />
						<input type="hidden" name="ownerId" value="${person.id}" />
						<input type='submit' value="Edit" />
					</teag:form>
				</td>
			</tr>
		</table>
		<%@ include file="/inc/aesFooter.jspf"%>
	</body>
</html>
