<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="teagtags" prefix="teag"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    <title>Life Insurance</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link rel="stylesheet" type="text/css" href="<%=path%>/css/styles.css">
  </head>
  <body>
  	<!-- Insert the new Advanced Estate Strategies Logo here -->
	<%@include file="/inc/aesHeader.jspf" %>
	<h2 align='center'>
  	Charitable Lead Annuity Trust with Life Insurance
  	</h2>
	<jsp:useBean id='life' scope='session' class='com.estate.toolbox.LifeTool'/>
  	<c:if test="${param.pageView == 'Add' }">
  		<jsp:setProperty name='life' property='inputString' param='life'/>
  	</c:if>
	<table width='100%'>
		<tr>
			<!-- menu options -->
			<td width='25%' align='center' valign='top'><%@include file='menu.jsp' %></td>
			<!-- Form -->
			<td  align='left'>
				<teag:form action="toolbox/clatWithLife/life.jsp" method="post" name="uForm" >
				<teag:hidden name='pageView' value='Answer42'/>
				<table cellspacing='5'>
					<tr>
						<td colspan='10' align='center' ><h3>Life Insurance Information</h3></td>
					</tr>
					<tr>
						<td align='right'>Year</td>
						<td align='right'>Age</td>
						<td align='right'>Div</td>
						<td align='right'>Net Prem.</td>
						<td align='right'>Net After Tax</td>
						<td align='right'>Cumulative Net</td>
						<td align='right'>Cash Value Adds</td>
						<td align='right'>Net Cash Value</td>
						<td align='right'>Net Death Benefit</td>
					</tr>
					<c:forEach items="${life.lifeElements }" var="element">
					<tr>
						<td align='right'>${element.year }</td>
						<td align='right'>${element.age }</td>
						<td align='right'><fmt:formatNumber pattern="###,###,###">${element.dividends}</fmt:formatNumber></td>
						<td align='right'><fmt:formatNumber pattern="###,###,###">${element.netPremium}</fmt:formatNumber></td>
						<td align='right'><fmt:formatNumber pattern="###,###,###">${element.cumNet}</fmt:formatNumber></td>
						<td align='right'><fmt:formatNumber pattern="###,###,###">${element.baseCashValue}</fmt:formatNumber></td>
						<td align='right'><fmt:formatNumber pattern="###,###,###">${element.cashValueAdds}</fmt:formatNumber></td>
						<td align='right'><fmt:formatNumber pattern="###,###,###">${element.netCashValue}</fmt:formatNumber></td>
						<td align='right'><fmt:formatNumber pattern="###,###,###">${element.netDeathBenefit}</fmt:formatNumber></td>
					</tr>
					</c:forEach>
					<tr>
						<td colspan='10' align='center'>Enter the Life Insurance info into the textbox.</td>
					</tr>
					<tr>
						<td colspan='10'>&nbsp;</td>
					</tr>
					<tr>
						<td colspan='10'>
							<textarea name='life' cols='80' rows='5'>
							</textarea>
						</td>
					</tr>
					<tr>
						<td colspan='3' align='center'>
							<teag:input type="image" src="toolbtns/Add.png" alt="Add"
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
