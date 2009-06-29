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

		<title>Qualified Personal Residence Trust</title>

		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<link rel="stylesheet" type="text/css" href="<%=path %>/css/styles.css" media="screen" />
	</head>
	<jsp:useBean id='userInfo' scope='session'
		class='com.teag.bean.PdfBean' />
	<jsp:useBean id='qprt' scope='session'
		class='com.estate.controller.QprtController' />
	<body>
		<%@include file="/inc/aesHeader.jspf" %>
		<h3 align='center'>Qualified Personal Residence Trust</h3>
		<h4 align='center'>
			Tool Information for ${userInfo.firstName} ${userInfo.lastName}
		</h4>
		<table>
			<tr>
				<td><%@include file='menu.jspf'%></td>
				<td>
					<teag:form name='tForm' action='servlet/StdQprtServlet' method='post'>
						<teag:hidden name="pageView" value="tool"/>
						<teag:hidden name="action" value="none"/>
						<table>
							<tr>
								<td colspan='3'>
									Enter the following information for calculating the 
									Qualified Personal Residence Trust
								</td>
							</tr>
							<tr>
								<td colspan='3'>&nbsp;</td>
							</tr>
							<tr>
								<td align='right'>Number of Trusts</td>
								<td>&nbsp;</td>
								<td>
									<select name='trusts'  onchange="javascript: document.tForm.action.value='update';document.tForm.submit()">
										<option value="1" <c:if test="${qprt.trusts==1}">selected='selected'</c:if>>One</option>
										<option value="2" <c:if test="${qprt.trusts==2}">selected='selected'</c:if>>Two</option>
									</select>
								</td>
							</tr>
							<tr>
								<td align='right'>Fair Market Value of Residence</td>
								<td>&nbsp;</td>
								<td>
									<teag:text name='fmv' fmt='number' pattern='###,###,###' value='${qprt.fmv }'/>
								</td>
							</tr>
							<tr>
								<td align='right'>Cost Basis</td>
								<td>&nbsp;</td>
								<td>
									<teag:text name='basis' fmt='number' pattern='###,###,###' value='${qprt.basis }'/>
								</td>
							</tr>
							<tr>
								<td align='right'>Asset Growth (year)</td>
								<td>&nbsp;</td>
								<td>
									<teag:text name='growth' fmt='percent' pattern='##.##%' value='${qprt.growth }'/>
								</td>
							</tr>
							<tr>
								<td align='right'>Is 7520 rate</td>
								<td>&nbsp;</td>
								<td>
									<teag:text name='irsRate' fmt='percent' pattern='##.##%' value='${qprt.irsRate }'/>
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
									<fmt:formatDate var='d1' pattern='M/d/yyyy' value='${qprt.irsDate }'/>
									<teag:text name='irsDate' value='${d1}' />
								</td>
							</tr>
							<tr>
								<td align='right'>Fractional Discount</td>
								<td>&nbsp;</td>
								<td>
									<fmt:formatNumber var='f1' pattern='##.##%' value='${qprt.fractionalDiscount}'/>
									<teag:text name='fractionalDiscount' value='${f1}'/>
								</td>
							</tr>
							<tr>
								<td align='right'>Client&rsquo;s Age</td>
								<td>&nbsp;</td>
								<td>
									<teag:text name="clientAge" fmt='number' pattern='###' value="${qprt.clientAge}"  />
								</td>
							</tr>
							<tr>
								<td align='right'>Client Term</td>
								<td>&nbsp;</td>
								<td>
									<teag:text name='clientTerm' fmt='number' pattern='###' value='${qprt.clientTerm }'/>
								</td>
							</tr>
							<tr>
								<td align='right'>Client Prior Gifts</td>
								<td>&nbsp;</td>
								<td>
									<teag:text name='clientPriorGifts' fmt='number' pattern='###,###,###' value='${qprt.clientPriorGifts}'/>
								</td>
							</tr>
							<c:if test="${qprt.trusts>1 }">
								<tr>
									<td align='right'>Spouse&rsquo;s Age</td>
									<td>&nbsp;</td>
									<td>
										<teag:text name="spouseAge" fmt='number' pattern='###' value="${qprt.spouseAge}"></teag:text>
									</td>
								</tr>
								<tr>
								<td align='right'>Spouse Term</td>
								<td>&nbsp;</td>
								<td>
									<teag:text name='spouseTerm' fmt='number' pattern='###' value='${qprt.spouseTerm }'/>
								</td>
								</tr>
								<tr>
								<td align='right'>Spouse Prior Gifts</td>
								<td>&nbsp;</td>
								<td>
									<teag:text name='spousePriorGifts' fmt='number' pattern='###,###,###' value='${qprt.spousePriorGifts}'/>
								</td>
							</tr>
							</c:if>
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
