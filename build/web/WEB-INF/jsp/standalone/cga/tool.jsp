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
		type='com.teag.bean.PdfBean' />
	<jsp:useBean id='cga' scope='session'
		type='com.estate.toolbox.CGA' />
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
				<td><%@include file='menu.jspf'%></td>
				<td>
					<teag:form name='tForm' action='servlet/StdCgaServlet' method='post'>
						<teag:hidden name="pageView" value="tool"/>
						<teag:hidden name="action" value="none"/>
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
								<td align='right'>IRS 7520 rate <span class='text-red'>*</span></td>
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
									<teag:select name='calcType' onChange="javascript: document.tForm.action.value='update'; document.tForm.submit()">
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
										onclick="document.tForm.action.value='update'"
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
