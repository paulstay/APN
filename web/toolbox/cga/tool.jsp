<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
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

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<base href="<%=basePath%>">

		<title>Charitable Gift Annuity Contract</title>

		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<link rel="stylesheet" type="text/css" href="/teag/css/styles.css" media="screen" />
	</head>
	<jsp:useBean id='userInfo' scope='session'
		class='com.teag.bean.PdfBean' />
	<jsp:useBean id='cga' scope='session'
		class='com.estate.toolbox.CGA' />
	<c:if test="${param.pageView == 'Update' }">
		<fmt:parseNumber var='a1' value='${param.fmv }' />
		<jsp:setProperty name='cga' property='fmv' value='${a1 }' />
		<fmt:parseNumber var='a2' value='${param.basis }' />
		<jsp:setProperty name='cga' property='basis' value='${a2 }' />
		<teag:parsePercent var='a3' value='${param.growth }'/>
		<jsp:setProperty name='cga' property='growth' value='${a3 }' />
		<teag:parsePercent var='a4' value='${param.income }' />
		<jsp:setProperty name='cga' property='income' value='${a4 }' />
		<teag:parsePercent var='a5' value='${param.irsRate }' />
		<jsp:setProperty name='cga' property='irsRate' value='${a5 }' />
		<fmt:parseDate var='a6' pattern='M/d/yyyy' value='${param.irsDate }' />
		<jsp:setProperty name='cga' property='irsDate' value='${a6 }' />
		<fmt:parseNumber var='a7' value='${param.cAge }'/>
		<jsp:setProperty name='cga' property='clientAge' value='${a7 }'/>
		<fmt:parseNumber var='a8' value='${param.sAge }'/>
		<jsp:setProperty name='cga' property='spouseAge' value='${a8 }'/>
		<jsp:setProperty name='cga' property='calcType' param='calcType'/>
		<c:if test="${cga.calcType == 'M' }" >
			<teag:parsePercent var='a9' value='${param.annuityRate}'/>
			<jsp:setProperty name='cga' property='annuityRate' value='${a9 }'/>
		</c:if>
		<jsp:setProperty name='cga' property='frequency' param='freq'/>
		<jsp:setProperty name='cga' property='endBegin' param='endBegin'/>
	</c:if>
	<body>
		<%@include file="/inc/aesHeader.jspf" %>
		<h3 align='center'>
			Charitable Gift Annuity Contract
		</h3>
		<h4 align='center'>
			Tool Information for ${userInfo.firstName} ${userInfo.lastName}
		</h4>
		<c:if test="${param.pageView == 'Update'}">
		</c:if>
		<table>
			<tr>
				<td><%@include file='menu.jsp'%></td>
				<td>
					<teag:form name='tForm' action='toolbox/cga/tool.jsp' method='post'>
						<teag:hidden name="pageView" value="noUpdate"/>
						<table>
							<tr>
								<td colspan='3'>
									Enter the following information for calculating the 
									Charitable Gift Annuity Contract
								</td>
							</tr>
							<tr>
								<td align='right'>Principal Donated</td>
								<td>&nbsp;</td>
								<td>
									<teag:text name='fmv' fmt='number' pattern='###,###,###' value='${cga.fmv }'/>
								</td>
							</tr>
							<tr>
								<td align='right'>Cost Basis</td>
								<td>&nbsp;</td>
								<td>
									<teag:text name='basis' fmt='number' pattern='###,###,###' value='${cga.basis }'/>
								</td>
							</tr>
							<tr>
								<td align='right'>Asset Growth (year)</td>
								<td>&nbsp;</td>
								<td>
									<teag:text name='growth' fmt='percent' pattern='##.##%' value='${cga.growth }'/>
								</td>
							</tr>
							<tr>
								<td align='right'>Asset Income (year)</td>
								<td>&nbsp;</td>
								<td>
									<teag:text name='income' fmt='percent' pattern='##.##%' value='${cga.income }'/>
								</td>
							</tr>
							<tr>
								<td align='right'>Is 7520 rate <span class='text-red'>*</span></td>
								<td>&nbsp;</td>
								<td>
									<teag:text name='irsRate' fmt='percent' pattern='##.##%' value='${cga.irsRate }'/>
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
									<fmt:formatDate var='d1' pattern='M/d/yyyy' value='${cga.irsDate }'/>
									<teag:text name='irsDate' value='${d1}' />
								</td>
							</tr>
							<tr>
								<td align='right'>Client's Age</td>
								<td>&nbsp;</td>
								<td>
									<teag:text name="cAge" fmt='number' pattern='###' value="${cga.clientAge}"  />
								</td>
								
							</tr>
							<tr>
								<td align='right'>Spoouse's Age (0 for none)</td>
								<td>&nbsp;</td>
								<td>
									<teag:text name="sAge" fmt='number' pattern='###' value="${cga.spouseAge}"></teag:text>
								</td>
							</tr>
							<tr>
								<td align='right'>Annuity Rate</td>
								<td>&nbsp;</td>
								<td>
									<teag:select name='calcType' onChange="javascript: document.tForm.pageView.value='Update'; document.tForm.submit()">
										<teag:option value='X'  selected='${cga.calcType}' label='Use Max Annuity Rate'/>
										<teag:option value='A'  selected='${cga.calcType}' label='Standard ACGA Rates'/>
										<teag:option value='M'  selected='${cga.calcType}' label='Specific Annuity Rate'/>
									</teag:select>
								</td>
							</tr>
							<c:if test="${cga.calcType == 'M' }">
								<tr>
									<td align='right'>Annuity Rate</td>
									<td>&nbsp;</td>
									<td>
										<teag:text name='annuityRate' fmt='percent' pattern='##.##%' value='${cga.annuityRate }'/>
									</td>
								</tr>
							</c:if>
							<tr>
								<td align='right'>Payment Schedule</td>
								<td>
									&nbsp;
								</td>
								<td>
									<select name='freq'>
										<option value="1" <c:if test="${cga.frequency==1}">selected='selected'</c:if>>Annual</option>
										<option value="2" <c:if test="${cga.frequency==2}">selected='selected'</c:if> >Semi-Annual</option>
										<option value="4" <c:if test="${cga.frequency==4}">selected='selected'</c:if> >Quarterly</option>
										<option value="12" <c:if test="${cga.frequency==12}">selected='selected'</c:if> >Monthly</option>
									</select>
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
									<select name='endBegin'>
										<option value="0" <c:if test="${cga.endBegin==0}">selected='selected'</c:if> >End of Period</option>
										<option value="1" <c:if test="${cga.endBegin==1}">selected='selected'</c:if> >Begin of Period</option>
									</select>
								</td>
							</tr>
							<tr>
								<td colspan='3' align='center'>&nbsp; 
									<teag:input type="image" src="toolbtns/Update.png" alt="Update Tool"
										onclick="document.tForm.pageView.value='Update'"
										onmouseout="this.src='toolbtns/Update.png';"
										onmousedown="this.src='toolbtns/Update_down.png';" />
								</td>
							</tr>
							<tr class='text-red' >
								<td colspan='3' align='center'>Use the largest 7520 Rate from the past three months!</td>
							</tr>
						</table>
					</teag:form>
				</td>
			</tr>
		</table>
		<%@include file="/inc/aesFooter.jspf" %>
	</body>
</html>
