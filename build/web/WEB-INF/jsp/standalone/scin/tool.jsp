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

		<title>Self Canceling Installment Note</title>

		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<link rel="stylesheet" type="text/css" href="<%=path%>/css/styles.css">
	</head>
	<jsp:useBean id='userInfo' scope='session'
		type='com.teag.bean.PdfBean' />
	<jsp:useBean id='scin' scope='session'
		type='com.estate.toolbox.SCIN' />
	<body>
		<%@include file="/inc/aesHeader.jspf" %>
		<h3 align='center'>
			Self Canceling Installment Note
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
					<teag:form name='tForm' action='servlet/StdScinServlet' method='post'>
						<teag:hidden name="pageView" value="tool"/>
						<teag:hidden name="action" value="none"/>
						<table>
							<tr>
								<td colspan='3'>
									Enter the following information for calculating the 
									Self Canceling Installment Note
								</td>
							</tr>
							<tr>
								<td align='right'>Fair Market Value of Assets</td>
								<td>&nbsp;</td>
								<td>
									<teag:text name='fmv' fmt='number' pattern='###,###,###' value='${scin.fmv }'/>
								</td>
							</tr>
							<tr>
								<td align='right'>Basis</td>
								<td>&nbsp;</td>
								<td>
									<teag:text name='basis' fmt='number' pattern='###,###,###' value='${scin.basis }'/>
								</td>
							</tr>
							<tr>
								<td align='right'>Asset Growth (year)</td>
								<td>&nbsp;</td>
								<td>
									<teag:text name='growth' fmt='percent' pattern='##.##%' value='${scin.growth }'/>
								</td>
							</tr>
							<tr>
								<td align='right'>Is 7520 rate</td>
								<td>&nbsp;</td>
								<td>
									<teag:text name='irsRate' fmt='percent' pattern='##.##%' value='${scin.irsRate }'/> 
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
									<fmt:formatDate var='d1' pattern='M/d/yyyy' value='${scin.irsDate }'/>
									<teag:text name='irsDate' value='${d1}' />
								</td>
							</tr>
							<tr>
								<td align='right'>Client's  Age</td>
								<td>&nbsp;</td>
								<td>
									<teag:text name="clientAge" fmt='number' pattern='###' value="${scin.clientAge}"></teag:text>
								</td>
								
							</tr>
							<tr>
								<td align='right'>Spoouse's Age (0 for none)</td>
								<td>&nbsp;</td>
								<td>
									<teag:text name="spouseAge" fmt='number' pattern='###' value="${scin.spouseAge}"></teag:text>
								</td>
								
							</tr>
							<tr>
								<td align='right'>Note Type</td>
								<td>&nbsp;</td>
								<td>
									<teag:select name="noteType" >
										<teag:option label='Level Principal + Interest' value='0' selected='${scin.noteType}' />
										<teag:option label='Amoritized' value='1' selected='${scin.noteType}' />
										<teag:option label='Interest Only' value='2' selected='${scin.noteType}' />
									</teag:select>
								</td>
							</tr>
							<tr>
								<td align='right'>Payment Type</td>
								<td>&nbsp;</td>
								<td>
									<teag:select name='paymentType'>
										<teag:option label='Principal Risk Premium' value='0' selected='${scin.paymentType }'/>
										<teag:option label='Interest Risk Premium' value='1' selected='${scin.paymentType }'/>
									</teag:select>
								</td>
							</tr>
							<tr>
								<td align='right'>Interest Rate on Note</td>
								<td>&nbsp;</td>
								<td>
									<teag:text name="noteRate" fmt='percent' pattern='##.###%' value="${scin.noteRate}"></teag:text>
								</td>
							</tr>
							<tr>
								<td align='right'>Term of Note</td>
								<td>&nbsp;</td>
								<td>
									<teag:text name='term' fmt='number' pattern='###' value='${scin.term }'/>
								</td>
							</tr>
							<tr>
								<td align='right'>Years Deffered</td>
								<td>&nbsp;</td>
								<td>
									<teag:text name='yrsDeffered' fmt='number' pattern='###' value='${scin.yrsDeffered }'/>
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
						</table>
					</teag:form>
				</td>
			</tr>
		</table>
		<%@include file="/inc/aesFooter.jspf" %>
	</body>
</html>
