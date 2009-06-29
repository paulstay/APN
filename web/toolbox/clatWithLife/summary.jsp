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
    <title>Summary</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link rel="stylesheet" type="text/css" href="<%=path%>/css/styles.css">
  </head>
  <jsp:useBean id='userInfo' scope='session' class='com.teag.bean.PdfBean'/>
  <jsp:useBean id='clat' scope='session' class='com.estate.toolbox.Clat'/>
  <jsp:useBean id='aList' scope='session' class='com.estate.beans.AssetList'/>
  <jsp:useBean id='life' scope='session' class='com.estate.toolbox.LifeTool'/>
  <%
  		clat.setFmv(aList.getTotal());
		clat.setAssetIncome(aList.getIncome());
		clat.setAssetGrowth(aList.getGrowth());
		clat.getCalculate();
   %>
  <body>
  	<c:set var="premiums" value="${life.premiums }"/>
  	<!-- Insert the new Advanced Estate Strategies Logo here -->
	<%@include file="/inc/aesHeader.jspf" %>
	<h2 align='center'>
  	Charitable Lead Annuity Trust with Life Insurance<br>Summary
  	</h2>
	<table width='100%'>
		<tr>
			<!-- menu options -->
			<td width='25%' align='center'><%@include file='menu.jsp' %></td>
			<!-- Form -->
			<td  align='left'>
				<teag:form action="toolbox/clatWithLife/summary.jsp" method="post" name="uForm" >
				<teag:hidden name="pageView" value="noUpdate"></teag:hidden>
				<table width='80%' align='left'>
					<tr>
						<td>Beginning Principal</td>
						<td align='right'><fmt:formatNumber pattern="###,###,###" value="${clat.fmv}"/></td>
						<td width='5%'>&nbsp;</td>
						<td>Section 7520 Rate</td>
						<td align='right'><fmt:formatNumber pattern="##.##%" value="${clat.irsRate }"/></td>
					</tr>
					<tr>
						<td>Discounted Principal</td>
						<td align='right'><fmt:formatNumber pattern="###,###,###" value="${clat.discountValue}"/></td>
						<td width='5%'>&nbsp;</td>
						<td>Transfer Date</td>
						<td align='right'><fmt:formatDate pattern="M/d/yyyy" value="${clat.irsDate}"/></td>
					</tr>
					<tr>
						<td>Discount</td>
						<td align='right'><fmt:formatNumber pattern="##.#%" value="${clat.discount}"/></td>
						<td width='5%'>&nbsp;</td>
						<td>Annuity Factor</td>
						<td align='right'><fmt:formatNumber pattern="###.####" value="${clat.annuityFactor }"/></td>
					</tr>
					<tr>
						<td>Remainder Interest</td>
						<td align='right'><fmt:formatNumber pattern="###,###,###" value="${clat.remainderInterest}"/></td>
						<td width='5%'>&nbsp;</td>
						<td>Annuity Payment</td>
						<td align='right'><fmt:formatNumber pattern="###,###" value="${clat.annuityPayment}"/></td>
					</tr>
					<tr>
						<td>CLAT Term</td>
						<td align='right'><fmt:formatNumber pattern="###" value="${clat.term}"/></td>
						<td width='5%'>&nbsp;</td>
						<td>Annuity Increase</td>
						<td align='right'><fmt:formatNumber pattern="##.###%" value="${clat.annuityIncrease}" /></td>
					</tr>
					<tr>
						<td>Charitable Deduction</td>
						<td align='right'><fmt:formatNumber pattern="###,###,###" value="${clat.charitableDeduction}" /></td>
						<td width='5%'>&nbsp;</td>
						<td>Optimal Payment Rate</td>
						<td align='right'><fmt:formatNumber pattern="##.###%" value="${clat.optimalRate}"/></td>
					</tr>
					<tr>
						<td>Type of Trust</td>
						<td align='right'>
							<c:choose>
								<c:when test="${clat.grantor }">Grantor</c:when>
								<c:otherwise>Non Grantor</c:otherwise>
							</c:choose>
						</td>
						<td width='5%'>&nbsp;</td>
						<td>Payment Frequency</td>
						<td align='right'>
						<c:choose>
							<c:when test="${clat.freq == 1 }">Annual</c:when>
							<c:when test="${clat.freq == 2 }">Semi Annual</c:when>
							<c:when test="${clat.freq == 4 }">Quarterly</c:when>
							<c:otherwise>Monthly</c:otherwise>
						</c:choose>
						</td>
					</tr>
					<tr>
						<td>Life Insurance Premium</td>
						<td align='right'><fmt:formatNumber pattern="###,###,###" value="${premiums[0] }"/></td>
						<td width='5%'>&nbsp;</td>
						<td>Life Death Benefit</td>
						<td align='right'></td>
					</tr>
				</table>
				</teag:form>
			</td>
		</tr>
	</table>
	<%@include file="/inc/aesFooter.jspf" %>
  </body>
</html>
