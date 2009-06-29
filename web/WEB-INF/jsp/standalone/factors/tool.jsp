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

		<title>Term and Life Factors</title>

		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<link rel="stylesheet" type="text/css" href="<%=path%>/css/styles.css">
		<script type="text/javascript" src="<%=path%>/cal/calendar.js"></script>
		<script type="text/javascript" src="<%=path%>/cal/lang/calendar-en.js"></script>
		<script type="text/javascript" src="<%=path%>/cal/calendar-setup.js"></script>
	</head>
	<body>
		<jsp:useBean id="factors" scope="session" type="com.estate.toolbox.TermLifeFactors" />
		<jsp:useBean id="termCertain" scope="request" type="com.estate.toolbox.TermLifeFactors" />
		<jsp:useBean id="lifeStatus1" scope="request" type="com.estate.toolbox.TermLifeFactors" />
		<jsp:useBean id="lifeStatus2" scope="request" type="com.estate.toolbox.TermLifeFactors" />
		<%@include file="/inc/aesHeader.jspf"%>
		<teag:form name='tForm' action='servlet/FactorsServlet' method='post'>
			<input type='hidden' name='action' value='noupdate'/>
			<table align='center' width='80%'>
				<tr>
					<td colspan='5' align='center'>
						<h3>Assumptions</h3>
					</td>
				</tr>
				<tr>
					<td>
						7520 Rate
					</td>
					<td align='left'>
						<teag:text name='irsRate' fmt="percent" pattern="##.##%" value='${factors.irsRate}'/>
					</td>
					<td>
						&nbsp;
					</td>
					<td>
						Term
					</td>
					<td align='right'>
						<teag:text name="term" fmt="number" pattern="###,###,###"
										value="${factors.term}" />
					</td>
				</tr>
				<tr>
					<td>
						Annuity Increase
					</td>
					<td align='left'>
						<teag:text name='annuityIncrease' fmt="percent" pattern="##.###%" value='${factors.annuityIncrease}'/>
					</td>
					<td>
						&nbsp;
					</td>
					<td>
						Age 1
					</td>
					<td align='right'>
						<teag:text name='age1' fmt='number' value='${factors.age1}'/>
					</td>
				</tr>
				<tr>
					<td>
						Annuity Frequency
					</td>
					<td align='left'>
						<teag:select name='freq'>
							<teag:option label='Annual' value='1' selected='${factors.freq}'/>
							<teag:option label='Semi-Annual' value='2'  selected='${factors.freq}'/>
							<teag:option label='Quarterly' value='4' selected='${factors.freq}'/>
							<teag:option label='Montly' value='12' selected='${factors.freq}'/>
						</teag:select>
					</td>
					<td>
						&nbsp;
					</td>
					<td>
						Age 2
					</td>
					<td align='right'>
						<teag:text name='age2' fmt='number' value='${factors.age2}'/>
					</td>
				</tr>
				<tr>
					<td align='left'>
						Annuity Type
					</td>
					<td align='left'>
						<teag:select name='aType'>
							<teag:option label='End' value='0' selected='${factors.annuityType}'/>
							<teag:option label='Begin' value='1' selected='${factors.annuityType}'/>
						</teag:select>
					</td>
					<td>
						&nbsp;
					</td>
					<td>
						&nbsp;
					</td>
					<td>
						&nbsp;
					</td>
				</tr>
				<tr>
					<td align='left'>
						Years Deferred
					</td>
					<td align='left'>
						<teag:text name='yrsDeferred' pattern='###' value='${factors.yrsDef}'/>
					</td>
					<td>
						&nbsp;
					</td>
					<td>
						&nbsp;
					</td>
					<td>
						&nbsp;
					</td>
				</tr>
				<tr>
					<td colspan='5' align='center'>
							<input type="image" src="toolbtns/Update.png" alt="Update Tool"
										onclick="document.tForm.action.value='update'"
											onmouseover="this.src='toolbtns/Update_over.png';"
												onmouseout="this.src='toolbtns/Update.png';"
												onmousedown="this.src='toolbtns/Update_down.png';" />
							<input type="image" src="toolbtns/Cancel.png" alt="Cancel"
							            onclick="document.tForm.action.value='cancel'"
										onmouseover="this.src='toolbtns/Cancel_over.png';"
										onmouseout="this.src='toolbtns/Cancel.png';"
										onmousedown="this.src='toolbtns/Cancel_down.png';" />
					</td>
				</tr>
			</table>
			<br>
			<table width='80%' align='center' frame='box'>
				<tr>
					<th colspan='5' align='center'><h3>Term and Life Factors</h3></th>
				</tr>
				<tr class="l-color1">
					<th align='left'>IRS Rounded Results</th>
					<th align='right'>Income</th>
					<th align='right'>Remainder</th>
					<th align='right'>Reversion</th>
					<th align='right'>Annuity</th>
				</tr>
				<tr class='l-color0'>
					<td colspan='5'><b>Term Status</b></td>
				</tr>
				<tr>
					<td>Term Certain</td>
					<td align='right'><fmt:formatNumber pattern="##.#####" value="${termCertain.incomeFactor}"/></td>
					<td align='right'><fmt:formatNumber pattern="##.#####" value="${termCertain.remainderFactor}"/></td>
					<td align='right'><fmt:formatNumber pattern="##.#####" value="${termCertain.reversionFactor}"/></td>
					<td align='right'><fmt:formatNumber pattern="##.#####" value="${termCertain.annuityFactor}"/></td>
				</tr>
				<tr>
					<td colspan='5'>&nbsp;</td>
				</tr>
				<tr class='l-color0'>
					<td colspan='5'><b>Life Status</b> - Joint Life Expectancy (<fmt:formatNumber pattern="###.#" value="${factors.lifeExpectancy}"/>)</td>
				</tr>
				<tr>
					<td>Last to Die</td>
					<td align='right'><fmt:formatNumber pattern="##.#####" value="${lifeStatus1.incomeFactor}"/></td>
					<td align='right'><fmt:formatNumber pattern="##.#####" value="${lifeStatus1.remainderFactor}"/></td>
					<td align='right'><fmt:formatNumber pattern="##.#####" value="${lifeStatus1.reversionFactor}"/></td>
					<td align='right'><fmt:formatNumber pattern="##.#####" value="${lifeStatus1.annuityFactor}"/></td>
				</tr>
				<tr>
					<td>First to Die</td>
					<td align='right'><fmt:formatNumber pattern="##.#####" value="${lifeStatus2.incomeFactor}"/></td>
					<td align='right'><fmt:formatNumber pattern="##.#####" value="${lifeStatus2.remainderFactor}"/></td>
					<td align='right'><fmt:formatNumber pattern="##.#####" value="${lifeStatus2.reversionFactor}"/></td>
					<td align='right'><fmt:formatNumber pattern="##.#####" value="${lifeStatus2.annuityFactor}"/></td>
				</tr>
				<tr>
					<td colspan='5'>&nbsp;</td>
				</tr>
				<tr class='l-color0'>
					<td colspan='5'><b>Term Life Statuses</b></td>
				</tr>
				<tr>
					<td>Shorter of Term and Last To Die</td>
					<td align='right'><fmt:formatNumber pattern="##.#####" value="${termLifeStatus1.incomeFactor}"/></td>
					<td align='right'><fmt:formatNumber pattern="##.#####" value="${termLifeStatus1.remainderFactor}"/></td>
					<td align='right'><fmt:formatNumber pattern="##.#####" value="${termLifeStatus1.reversionFactor}"/></td>
					<td align='right'><fmt:formatNumber pattern="##.#####" value="${termLifeStatus1.annuityFactor}"/></td>
				</tr>
				<tr>
					<td>Shorter of Term and First To Die</td>
					<td align='right'><fmt:formatNumber pattern="##.#####" value="${termLifeStatus2.incomeFactor}"/></td>
					<td align='right'><fmt:formatNumber pattern="##.#####" value="${termLifeStatus2.remainderFactor}"/></td>
					<td align='right'><fmt:formatNumber pattern="##.#####" value="${termLifeStatus2.reversionFactor}"/></td>
					<td align='right'><fmt:formatNumber pattern="##.#####" value="${termLifeStatus2.annuityFactor}"/></td>
				</tr>
				<tr>
					<td>Greater of Term and Last To Die</td>
					<td align='right'><fmt:formatNumber pattern="##.#####" value="${termLifeStatus3.incomeFactor}"/></td>
					<td align='right'><fmt:formatNumber pattern="##.#####" value="${termLifeStatus3.remainderFactor}"/></td>
					<td align='right'><fmt:formatNumber pattern="##.#####" value="${termLifeStatus3.reversionFactor}"/></td>
					<td align='right'><fmt:formatNumber pattern="##.#####" value="${termLifeStatus3.annuityFactor}"/></td>
				</tr>
				<tr>
					<td>Greater of Term and First To Die</td>
					<td align='right'><fmt:formatNumber pattern="##.#####" value="${termLifeStatus4.incomeFactor}"/></td>
					<td align='right'><fmt:formatNumber pattern="##.#####" value="${termLifeStatus4.remainderFactor}"/></td>
					<td align='right'><fmt:formatNumber pattern="##.#####" value="${termLifeStatus4.reversionFactor}"/></td>
					<td align='right'><fmt:formatNumber pattern="##.#####" value="${termLifeStatus4.annuityFactor}"/></td>
				</tr>
			</table>
		</teag:form>
		<%@include file="/inc/aesFooter.jspf"%>
	</body>
</html>
