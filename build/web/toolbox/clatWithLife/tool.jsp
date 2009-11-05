<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%@ page import="com.estate.constants.*" %>
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
    <title>Tool Setup</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link rel="stylesheet" type="text/css" href="<%=path %>/css/styles.css">
  </head>
  <body>
  	<!-- Insert the new Advanced Estate Strategies Logo here -->
	<%@include file="/inc/aesHeader.jspf" %>
	<h2 align='center'>
  	Charitable Lead Annuity Trust with Life Insurance<br>
  	</h2>
  	<jsp:useBean id='clat' scope='session' class='com.estate.toolbox.Clat'/>
  	<c:if test="${param.pageView == 'calcMethod' }">
			<jsp:setProperty name='clat' property='calculationType' param='calculationMethod' />
		</c:if>
		<c:if test="${param.pageView == 'Update'}">
			<c:if test="${param.grantor == 'T' }">
				<jsp:setProperty name="clat" property="grantor" value="true" />
			</c:if>
				<c:if test="${param.grantor == 'F' }">
				<jsp:setProperty name="clat" property="grantor" value="false" />
			</c:if>
			<teag:parsePercent var='a2' value='${param.discount }' />
			<jsp:setProperty name='clat' property='discount' value='${a2 }' />
			<teag:parsePercent var='a5' value='${param.irsRate }' />
			<jsp:setProperty name='clat' property='irsRate' value='${a5 }' />
			<fmt:parseDate var='a6' pattern='M/d/yyyy' value='${param.irsDate }' />
			<jsp:setProperty name='clat' property='irsDate' value='${a6 }' />
			<teag:parsePercent var='etax' value='${param.estateTaxRate }' />
			<jsp:setProperty name='clat' property='estateTaxRate'
				value='${etax }' />
			<fmt:parseNumber var='a7' pattern='###' value='${param.term }' />
			<jsp:setProperty name='clat' property='term' value='${a7 }' />
			<fmt:parseNumber var='a8' pattern='###' value='${param.endBegin }' />
			<jsp:setProperty name='clat' property='endBegin' value='${a8 }' />
			<fmt:parseNumber var='a9' pattern='###' value='${param.freq }' />
			<jsp:setProperty name='clat' property='freq' value='${a9 }' />
			<teag:parsePercent var='tax' value="${param.taxRate }"/>
			<jsp:setProperty name='clat' property='taxRate' value='${tax}'/>
			<teag:parsePercent var='annI' value='${param.annuityIncrease }'/>
			<jsp:setProperty name='clat' property='annuityIncrease' value='${annI}'/>
			<c:if test="${param.llc =='F'}">
				<% clat.setUseLLC(false); %>
			</c:if>
			<c:if test="${param.llc =='L'}">
				<% clat.setUseLLC(true); %>
			</c:if>
			<jsp:setProperty name='clat' property='calculationType' param='calculationMethod' />
			<c:choose>
				<c:when test="${clat.calculationType == 'A' }">
					<fmt:parseNumber var="ap" value="${param.annuityPayment }" />
					<jsp:setProperty name="clat" property="annuityPayment"
						value="${ap }" />
					<jsp:setProperty name="clat" property="remainderInterest" value="0" />
				</c:when>
				<c:when test="${clat.calculationType == 'R' }">
					<fmt:parseNumber var="ri" value="${param.remainderInterest }" />
					<jsp:setProperty name="clat" property="remainderInterest"
						value="${ri }" />
					<jsp:setProperty name="clat" property="annuityPayment" value="0" />
				</c:when>
				<c:otherwise>
					<jsp:setProperty name="clat" property="remainderInterest" value="0" />
					<jsp:setProperty name="clat" property="annuityPayment" value="0" />
				</c:otherwise>
			</c:choose>
		</c:if>
	<table width='100%'>
		<tr>
			<!-- menu options -->
			<td width='25%' align='center' valign='top'><%@include file='menu.jsp' %></td>
			<!-- Form -->
			<td  align='left'>
				<teag:form action="toolbox/clatWithLife/tool.jsp" method="post" name="uForm" >
				<teag:hidden name="pageView" value="noUpdate"/>
				<table>
					<tr>
						<td colspan='3' align='center' ><h3>CLAT Information</h3></td>
					</tr>
					<tr>
								<td align='right'>Type of Trust</td>
								<td>&nbsp;</td>
								<td>
									<select name='grantor'>
									<c:choose>
										<c:when test="${clat.grantor}">
											<option value="T" selected='selected'>Grantor</option>
											<option value="F">Non Grantor</option>
										</c:when>
										<c:otherwise>
											<option value="T">Grantor</option>
											<option value="F" selected='selected'>Non Grantor</option>
										</c:otherwise>	
									</c:choose>
									</select>
								</td>
							</tr>
							<tr>
								<td align='right'>Discount Entity :</td><td>&nbsp;</td>
								<td>
									<c:set var='lc1' value='F'/>
									<c:if test="${clat.useLLC }">
										<c:set var='lc1' value='L'/>
									</c:if>
									<teag:select name="llc" options='<%=InputMaps.discountEntity%>' optionValue='${lc1}'></teag:select>
								</td>
							</tr>
							<tr>
								<td align='right'>
									Discount (From LLC or FLP) :
								</td>
								<td>
									&nbsp;
								</td>
								<td>
									<teag:text name="discount" value="${clat.discount}" fmt='percent' pattern='##.###%'/>
								</td>
							</tr>
							<tr>
								<td align='right'>
									Irs 7520 Rate :
								</td>
								<td>
									&nbsp;
								</td>
								<td>
									<teag:text name="irsRate" value="${clat.irsRate}" fmt='percent' pattern='##.###%'/>
								</td>
							</tr>
							<tr>
								<td align='right'>
									Date of Transfer :
								</td>
								<td>
									&nbsp;
								</td>
								<td>
									<fmt:formatDate var='a4' pattern='M/d/yyyy'
										value='${clat.irsDate}' />
									<teag:text name='irsDate' value='${a4}' />
								</td>
							</tr>
							<tr>
								<td align='right'>Federal and State Tax Rate</td><td>&nbsp;</td>
								<td>
									<teag:text name="taxRate" value="${clat.taxRate}" fmt='percent' pattern='##.###%'/>
								</td>
							</tr>
							<tr>
								<td align='right'>
									Estate Tax Rate :
								</td>
								<td>
									&nbsp;
								</td>
								<td>
									<teag:text name="estateTaxRate" value="${clat.estateTaxRate}" fmt='percent' pattern='##.####%'/>
								</td>
							</tr>
							<tr>
								<td align='right'>
									Term :
								</td>
								<td>
									&nbsp;
								</td>
								<td>
									<teag:text name="term" value="${clat.term}"></teag:text>
								</td>
							</tr>
							<tr>
								<td align='right'>
									Pay at begin or end :
								</td>
								<td>
									&nbsp;
								</td>
								<td>
									<teag:select name="endBegin" options='<%=InputMaps.endBegin%>' optionValue='${clat.endBegin}'></teag:select>
								</td>
							</tr>
							<tr>
								<td align='right'>
									Payment Frequency :
								</td>
								<td>
									&nbsp;
								</td>
								<td>
									<teag:select name="freq">
										<teag:option value="1" label="Yearly" selected='${clat.freq }'/>
										<teag:option value="2" label="Semi Annual" selected='${clat.freq }'/>
										<teag:option value="4" label="Quartery" selected='${clat.freq }'/>
										<teag:option value="12" label="Monthly" selected='${clat.freq }'/>
									</teag:select>
								</td>
							</tr>
							<tr>
								<td align='right'>
									Calculation Method
								</td>
								<td>
									&nbsp;
								</td>
								<td>
								<teag:select name='calculationMethod' onChange="javascript: document.uForm.pageView.value='calcMethod'; document.uForm.submit();" >
									<teag:option value='A' label='Annuity Payment' selected='${clat.calculationType}'/>
									<teag:option value='R' label='Remainder Interest' selected='${clat.calculationType}' />
									<teag:option value='Z' label='Zero Out Grat' selected='${clat.calculationType}'/>
								</teag:select>
								</td>
							</tr>
							<tr>
								<c:choose>
									<c:when test="${clat.calculationType == 'A' }">
										<td align='right'>
											Annuity Payment
										</td>
										<td>
											&nbsp;
										</td>
										<td>
											<teag:text name="annuityPayment" value="${clat.annuityPayment}" fmt="number"/>
										</td>
									</c:when>
									<c:when test="${clat.calculationType == 'R' }">
										<td align='right'>
											Remainder Interest
										</td>
										<td>
											&nbsp;
										</td>
										<td>
											<teag:text name="remainderInterest" value="${clat.remainderInterest}" fmt='number'/>
										</td>
									</c:when>
									<c:otherwise>
										<td align='center' colspan='3'>
											Zero Out CLAT
										</td>
									</c:otherwise>
								</c:choose>
							</tr>
							<tr>
								<td align='right'>Annuity Payment Increase (Yearly)</td>
								<td>&nbsp;</td>
								<td>
									<teag:text name="annuityIncrease" value="${clat.annuityIncrease}" fmt='percent' pattern='##.####%'/>
								</td>
							</tr>
							<tr>
								<td colspan='3' align='center'>
									<teag:input type="image" src="toolbtns/Update.png" alt="Update Tool"
										onclick="document.uForm.pageView.value='Update'"
										onmouseout="this.src='toolbtns/Update.png';"
										onmousedown="this.src='toolbtns/Update_down.png';" />
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
