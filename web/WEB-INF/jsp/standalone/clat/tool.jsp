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

		<title>Clat Tool Information</title>

		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<link rel="stylesheet" type="text/css" href="<%=path%>/css/styles.css">
		<style type="text/css"></style>
		<script type="text/javascript" src="js/calendarDateInput.js">

        /***********************************************
         * Jason's Date Input Calendar- By Jason Moon http://calendar.moonscript.com/dateinput.cfm
         * Script featured on and available at http://www.dynamicdrive.com
         * Keep this notice intact for use.
         ***********************************************/

        </script>
	</head>
	<jsp:useBean id='userInfo' scope='session' type='com.teag.bean.PdfBean' />
	<jsp:useBean id='clat' scope='session' type='com.estate.toolbox.Clat' />
	<body>
		<%@include file="/inc/aesHeader.jspf"%>
		<h3 align='center'>
			Charitable Lead Annuity Trust
		</h3>
		<h4 align='center'>
			Tool Information for ${userInfo.firstName} ${userInfo.lastName}
		</h4>
		<table>
			<tr>
				<td><%@include file='menu.jspf'%></td>
				<td>
					<teag:form name='tForm' action='servlet/StdClatToolServlet'
						method='post'>
						<input type='hidden' name='pageView' value="tool" />
						<input type='hidden' name='action' value="none" />
						<table>
							<tr>
								<td colspan='3'>
									Enter the following information for calculating the Charitable
									Lead Annuity Trust
								</td>
							</tr>
							<tr>
								<td colspan='3'>
									&nbsp;
								</td>
							</tr>
							<tr>
								<td align='right'>
									Type of Trust
								</td>
								<td>
									&nbsp;
								</td>
								<td>
									<teag:select name='trustType'
										onChange="javascript: document.tForm.action.value='update';document.tForm.submit();">
										<teag:option label='Term' value='T'
											selected='${clat.trustType}' />
										<teag:option label='Life' value='L'
											selected='${clat.trustType}' />
										<teag:option label='Shorter of' value='S'
											selected='${clat.trustType}' />
										<teag:option label='Greater of' value='G'
											selected='${clat.trustType}' />
									</teag:select>
								</td>
							</tr>
							<c:if test="${dispTerm}">
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
							</c:if>
							<c:if test="${dispLife}">
								<tr>
									<td align='right'>
										Nearest Age Life 1
									</td>
									<td>
										&nbsp;
									</td>
									<td>
										<teag:text name='age1' value='${clat.age1}' />
									</td>
								</tr>
								<tr>
									<td align='right'>
										Nearest Age Life 2
									</td>
									<td>
										&nbsp;
									</td>
									<td>
										<teag:text name='age2' value='${clat.age2}' />
									</td>
								</tr>
								<tr>
									<td align='right'>Life Type</td>
									<td>&nbsp;</td>
									<td>
										<teag:select name='lifeType'>
											<teag:option label='Last To Die' value='0' selected='${clat.lifeType}'></teag:option>
											<teag:option label='First To Die' value='1' selected='${clat.lifeType}'></teag:option>
										</teag:select>
									</td>
								</tr>
							</c:if>
							<tr>
								<td align='right'>
									Grantor/Non Grantor
								</td>
								<td>
									&nbsp;
								</td>
								<td>
									<select name='grantor'>
										<c:choose>
											<c:when test="${clat.grantor}">
												<option value="T" selected='selected'>
													Grantor
												</option>
												<option value="F">
													Non Grantor
												</option>
											</c:when>
											<c:otherwise>
												<option value="T">
													Grantor
												</option>
												<option value="F" selected='selected'>
													Non Grantor
												</option>
											</c:otherwise>
										</c:choose>
									</select>
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
									<teag:text name='fmv' fmt='number' pattern='###,###,###'
										value='${clat.fmv}' />
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
									<c:set var='lc1' value='false' />
									<c:if test="${clat.useLLC }">
										<c:set var='lc1' value='true' />
									</c:if>
									<teag:select name="llc">
										<teag:option label="FLP" value="false" selected="${lc1}" />
										<teag:option label="LLC" value="true" selected="${lc1}" />
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
									<teag:text name="discount" value="${clat.discount}"
										fmt='percent' pattern='##.###%' />
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
									<teag:text name="assetGrowth" value="${clat.assetGrowth}"
										fmt='percent' pattern='##.###%' />
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
									<teag:text name="assetIncome" value="${clat.assetIncome}"
										fmt='percent' pattern='##.###%' />
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
									<teag:text name="irsRate" value="${clat.irsRate}" fmt='percent'
										pattern='##.###%' />
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
									<fmt:formatDate var='a13' pattern='M/d/yyyy'
										value='${clat.irsDate }' />
									<p>
                                        <script>DateInput('irsDate', true, 'MM/DD/YYYY','${a13}')</script>
									</p>
								</td>
							</tr>
							<!-- 
							<tr>
								<td align='right'>Discount Assumption Rate : </td><td>&nbsp;</td>
								<td>
									<teag:text name="discountAssumptionRate" value="${clat.discountAssumptionRate}" fmt='percent' pattern='##.####%'/>
								</td>
							</tr>
							 -->
							<tr>
								<td align='right'>
									Federal and State Tax Rate
								</td>
								<td>
									&nbsp;
								</td>
								<td>
									<teag:text name="taxRate" value="${clat.taxRate}" fmt='percent'
										pattern='##.###%' />
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
									<teag:text name="estateTaxRate" value="${clat.estateTaxRate}"
										fmt='percent' pattern='##.####%' />
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
									<teag:select name="endBegin" options='<%=InputMaps.endBegin%>'
										optionValue='${clat.endBegin}'></teag:select>
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
										<teag:option value="1" label="Yearly" selected='${clat.freq }' />
										<teag:option value="2" label="Semi Annual"
											selected='${clat.freq }' />
										<teag:option value="4" label="Quartery"
											selected='${clat.freq }' />
										<teag:option value="12" label="Monthly"
											selected='${clat.freq }' />
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
									<teag:select name='calculationMethod'
										onChange="javascript: document.tForm.action.value='calc'; document.tForm.submit();">
										<teag:option value='A' label='Annuity Payment'
											selected='${clat.calculationType}' />
										<teag:option value='R' label='Remainder Interest'
											selected='${clat.calculationType}' />
										<teag:option value='Z' label='Zero Out Grat'
											selected='${clat.calculationType}' />
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
											<teag:text name="annuityPayment"
												value="${clat.annuityPayment}" fmt="number" />
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
											<teag:text name="remainderInterest"
												value="${clat.remainderInterest}" fmt='number' />
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
								<td align='right'>
									Annuity Payment Increase (Yearly)
								</td>
								<td>
									&nbsp;
								</td>
								<td>
									<teag:text name="annuityIncrease"
										value="${clat.annuityIncrease}" fmt='percent'
										pattern='##.####%' />
								</td>
							</tr>
							<tr>
								<td colspan='3' align='center'>
									<teag:input type="image" src="toolbtns/Update.png"
										alt="Update Tool"
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
		<%@include file="/inc/aesFooter.jspf"%>
	</body>
</html>
