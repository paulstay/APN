<%@ page language="java" import="java.util.*" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>	
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"
	+request.getServerPort()+path+"/";
%>
  <head>
    <base href="<%=basePath%>">
    
    <title><%=basePath %></title>
    
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="This is my page">
    <link rel="stylesheet" type="text/css" href="<%=path%>/css/styles.css">
  </head>
<%@ include file="/inc/init.jspf" %>
<c:if test="${validUser == null}" >
	<c:redirect url="/login.jsp">
		<c:param name="errorMsg" value="You are not logged in!" />
	</c:redirect>
</c:if> 
<jsp:useBean id="validUser" scope="session" class="com.teag.bean.AdminBean" />
<jsp:useBean id="client" scope="session" class="com.teag.bean.ClientBean" />
<jsp:useBean id="person" scope="session" class="com.teag.bean.PersonBean" />
<jsp:setProperty name="person" property="id" value="${client.primaryId}"/>
<%person.initialize(); %>
<body>
<%@ include file="/inc/aesHeader.jspf" %>
<%@ include file="/inc/aesQuickJump.jspf" %>
<div id='client-index'>
<table align='center'>
	<tr>
		<td colspan='3'>
			<span class='header-bold-blue'>Assets and Liablities for <%= person.getFirstName() + " " + person.getLastName()%></span>
		</td>
	</tr>	<tr> 
		<td align="left" valign="top" width="330">
		<ul>
			<li><a href="servlet/CashAssetServlet?action=list">Cash and Equivalents</a>
			<li><a href="servlet/DebtAssetServlet?action=list">Debts</a>
			<li><a href="servlet/BondAssetServlet?action=list">Municipal Bonds</a>
			<li><a href="servlet/SecuritiesAssetServlet?action=list">Marketable Securities</a>
			<li><a href="servlet/RetirementAssetServlet?action=list">Retirement Plans</a>
			<li><a href="servlet/IlliquidAssetServlet?action=list">Illiquid Investments</a>
			<li><a href="servlet/RealEstateAssetServlet?action=list">Real Estate</a>
			<li><a href="servlet/BusinessAssetServlet?action=list">Business Interests:</a>
			<li><a href="servlet/PersonalAssetServlet?action=list">Personal Properties</a>
			<li><a href="servlet/LifeInsAssetServlet?action=list">Life Insurance</a>
			<li><a href="servlet/NotesRecAssetServlet?action=list">Notes Receivable</a>
			<li><a href="servlet/NotesPayAssetServlet?action=list">Notes Payable</a>
		</ul>
		</td>
	</tr>
</table>
</div>
<div align='center'>
	<a href="client/index.jsp">Back to Client</a>
</div>
<%@ include file="/inc/aesFooter.jspf" %>
</body>
</html>
