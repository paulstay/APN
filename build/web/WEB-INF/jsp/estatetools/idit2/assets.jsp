<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%@ page import="com.teag.estate.*"%>
<%@ page import="com.teag.bean.*"%>
<%@ page import="com.estate.constants.*"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://jakarta.apache.org/taglibs/input-1.0"
	prefix="input"%>
<jsp:useBean id='idit' scope='session' type='com.teag.estate.IditTool' />
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
		<title>Sale to Intentionally Deffective Irrevocable Trust</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<link href="<%=path%>/css/styles.css" rel="stylesheet" media="screen">
	</head>
	<body>
		<%@include file="/inc/aesHeader.jspf"%>
		<h3 align='center'>
			Sale to an Intentionally Deffective Irrevocable Trust
		</h3>
		<h3 align='center'>
			Assets
		</h3>
		<table border="0" width='100%'>
			<tr>
				<td width='15%'>
					<%@ include file="menu.jspf"%>
				</td>
				<td valign='top' align='right' width='65%' >
					<table align='center' frame='box' width='50%'>
						<tr>
							<td>Total Value of Assets</td>
							<td align='right'>
								<fmt:formatNumber pattern="###,###,###" value="${totalValue}"/>
							</td>
						</tr>
						<tr>
							<td>Liquid Assets Needed</td>
							<td align='right'>
								<fmt:formatNumber pattern="###,###,###" value="${liquidValue}"/>
							</td>
						</tr>
						<tr>
							<td>Current Amount of Liquid Asset In Grat</td>
							<td align='right'>
								<fmt:formatNumber pattern="###,###,###" value="${liqHave}"/>
							</td>
						</tr>
					</table>
					<br>
					<table align='center' border='0'>
						<tr class='bg-color3'>
							<td align='left' width='30%' id='main-title'>
								Name
							</td>
							<td align='left' width='10%' id='main-title'>
								Value
							</td>
							<td align='left' width='10%' id='main-title'>
								Growth
							</td>
							<td align='left' width='10%' id='main-title'>
								Income
							</td>
							<td width='20%'>&nbsp;</td>
						</tr>
						<input:form name="assets" action='servlet/IditGratAssetServlet'>
							<input type='hidden' name='action' value='delAsset' />
							<input type='hidden' name='delId' value='-1' />
						<c:set var='style' value='2'/>
						<c:forEach var='sv' items='${aList}'>							
							<tr class='bg-color${style}'>
								<td align='left' width='30%'>${sv.name}</td>
								<td align='right' width='10%'>
									<fmt:formatNumber pattern="###,###,###" value='${sv.value}' />
								</td>
								<td align='right' width='10%'>
									<fmt:formatNumber type='percent'
										value='${sv.growth}' />
								</td>
								<td align='right' width='10%'>
									<fmt:formatNumber type='percent'
										value='${sv.income}' />
								</td>
								<td>
									<button type='submit'
										onclick="document.assets.delId.value='${sv.id}'">
										Delete
									</button>
								</td>
							</tr>
							<c:if test="${style ==2 }">
								<c:set var="style" value="6"/>
							</c:if>
							<c:if test="${style ==6 }">
								<c:set var="style" value="2"/>
							</c:if>
						</c:forEach>
						</input:form>
						<tr>
							<td colspan='5'>
								&nbsp;
							</td>
						</tr>
						<tr>
							<td colspan='5' align='center'>
								<form name='detail'
									action='servlet/IditGratAssetServlet' method='post'>
									<input type='hidden' name='action' value='noAction' />
									Asset to IDIT: <select name='assetId'>
									<c:set var='idx' value='0'/>
										<c:forEach var='a' items='${bList}'>
											<option value='${a.id}:${a.assetType}'>${a.description} : <fmt:formatNumber type='currency' value='${a.remainingValue}'/></option>
											<c:set var='idx' value='${idx+1}'/>
										</c:forEach>
									</select>									
									<br>
									Ammount to Distribute : <input name='v_dist'
										value='100%' />
									<br>
									<button type='submit'
										onclick="document.detail.action.value='addAsset'">
										Add
									</button>
								</form>
							</td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
		<%@include file="/inc/aesFooter.jspf"%>
	</body>
</html>
