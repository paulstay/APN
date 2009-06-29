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

		<title>Tool Information</title>

		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<link rel="stylesheet" type="text/css" href="<%=path%>/css/styles.css">
	</head>
	<jsp:useBean id='userInfo' scope='session'
		type='com.teag.bean.PdfBean' />
	<jsp:useBean id='idit' scope='session' type='com.estate.toolbox.Idit' />
	<body>
		<%@include file="/inc/aesHeader.jspf" %>
		<h3 align='center'>
			Intentionaly Defective Irrevocable Trust (IDIT)
		</h3>
		<h4 align='center'>
			Tool Information for ${userInfo.firstName} ${userInfo.lastName}
		</h4>
		<table>
			<tr>
				<td valign='top'><%@include file='menu.jspf'%></td>
				<td>
					<teag:form name='tForm' action='servlet/StdIditServlet'
						method='post'>
						<input type='hidden' name='pageView' value='tool' />
						<input type='hidden' name='action' value='none' />
						<input type='hidden' name="giftType" value="C" />
						<table>
							<tr>
								<td colspan='3'>
									Enter the following information for calculating an Installment
									Sale to an Intentionaly Defective Irrevocable Trust
								</td>
							</tr>
							<tr>
								<td colspan='3'>
									&nbsp;
								</td>
							</tr>
							<tr>
								<td align='right'>
									Fair market Value of Assets :
								</td>
								<td>
									&nbsp;
								</td>
								<td>
									<teag:text name="fmv" fmt="number" pattern="###,###,###" value="${idit.fmv }"/>
								</td>
							</tr>
							<tr>
								<td align='right'>
									Discount Entity :
								</td>
								<td>
									&nbsp;
								</td>
								<td>
									<c:set var='lc1' value='F' />
									<c:if test="${idit.useLLC }">
										<c:set var='lc1' value='L' />
									</c:if>
									<teag:select name="llc">
										<teag:option label="LLC" value="L" selected="${lc1}"/>
										<teag:option label="FLP" value="F" selected="${lc1}"/>
									</teag:select>
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
									<teag:text name="discount" fmt="number" pattern="##.##%" value="${idit.discount}"/>
								</td>
							</tr>
							<tr>
								<td align='right'>
									Asset Growth :
								</td>
								<td>
									&nbsp;
								</td>
								<td>
									<teag:text name="assetGrowth" fmt="number" pattern="##.###%" value="${idit.assetGrowth }"/>
								</td>
							</tr>
							<tr>
								<td align='right'>
									Asset Income :
								</td>
								<td>
									&nbsp;
								</td>
								<td>
									<teag:text name="assetIncome" fmt="number" pattern="##.###%" value="${idit.assetIncome}"/>
								</td>
							</tr>
							<tr>
								<td align='right'>
									Federal and State Tax Rate
								</td>
								<td>
									&nbsp;
								</td>
								<td>
									<teag:text name="taxRate" fmt="number" pattern="##.###%" value="${idit.taxRate}"/>
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
									<teag:text name="estateTaxRate" fmt="number" pattern="##.###%" value="${idit.estateTaxRate}"/>
								</td>
							</tr>
							<tr>
								<td align='right'>Gift to IDIT</td>
								<td>&nbsp;</td>
							</tr>
							<tr>
								<td align='right'>Amount of Gift</td>
								<td>&nbsp;</td>
								<td>
									<teag:text name='giftAmount' fmt='number' pattern='###,###,###' value="${idit.giftAmount}"/>
								</td>
							</tr>
							<tr>
								<td align='right'>Note Type</td>
								<td>&nbsp;</td>
								<td>
									<teag:select name="noteType">
										<teag:option label="Interest + Balloon" value="2" selected='${idit.noteType}'/>
										<teag:option label="Level Prin + Interest" value="0" selected='${idit.noteType}'/>
										<teag:option label="Amoritized" value="1" selected='${idit.noteType}'/>
									</teag:select>
								</td>
							</tr>
							<tr>
								<td align='right'>
									Note Term :
								</td>
								<td>
									&nbsp;
								</td>
								<td>
									<teag:text name="noteTerm" fmt="number" pattern="###" value="${idit.noteTerm}"/>
								</td>
							</tr>
							<tr>
								<td align='right'>
									Note Rate
								</td>
								<td>
									&nbsp;
								</td>
								<td>
									<teag:text name="noteRate" fmt="number" pattern="##.###%" value="${idit.noteRate}"/>
								</td>
							</tr>
							<tr>
								<td align='right'>Final Death</td>
								<td>&nbsp;</td>
								<td>
									<teag:text name="finalDeath" fmt="number" pattern="###" value="${idit.finalDeath}"/>
								</td>
							</tr>
							<tr>
								<td align='right'>
									Life Insurance Benefit
								</td>
								<td>
									&nbsp;
								</td>
								<td>
									<teag:text name="lifeDeathBenefit" fmt="number" pattern="###" value="${idit.lifeDeathBenefit}"/>
								</td>
							</tr>
							<tr>
								<td align='right'>
									Life Insurance Premium (Yearly)
								</td>
								<td>
									&nbsp;
								</td>
								<td>
									<teag:text name="lifePremium" fmt="number" pattern="###" value="${idit.lifePremium}"/>
								</td>
							</tr>
							<tr>
								<td align='right'>
									Years to Pay Premium
								</td>
								<td>
									&nbsp;
								</td>
								<td>
									<teag:text name="lifePremiumYears" fmt="number" pattern="###" value="${idit.lifePremiumYears}"/>
								</td>
							</tr>
							<tr>
								<td colspan='3' align='center'>
									<input type="image" src="toolbtns/Update.png" alt="Update Tool"
										onclick="document.tForm.action.value='update'"
										onmouseover="this.src='toolbtns/Update_over.png';"
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
