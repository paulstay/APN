<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%@ page import="com.estate.constants.*"%>
<%@ page import="com.teag.estate.*"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ page import="com.estate.constants.*"%>
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

		<title>Irrevocable Life Insurance Trust</title>

		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<link rel="stylesheet" type="text/css" href="<%=path%>/css/styles.css"
			media="screen" />
	</head>
	<jsp:useBean id='crum' scope='session'
		type='com.teag.estate.CrummeyTool' />
	<body>
		<%@include file="/inc/aesHeader.jspf"%>
		<h3 align='center'>
			Irrrevocable Life Insurance Trust
		</h3>
		<h3 align='center'>
			Summary Page
		</h3>
		<table width='100%'>
			<tr>
				<td valign='top' width='20%'>
					<%@ include file="menu.jspf"%>
				</td>
				<td valign='top' align='left' width='60%'>
					<table align='center' width='70%'>
						<tr>
							<td align='center' valign='top'>
								<img src="<%=path%>/images/Crummey.png" width='677'
									height='329' alt="" />
							</td>
						</tr>
						<tr>
							<td>
								<ol>
									<li>${epg.clientFirstName} and ${epg.spouseFirstName} 
										make total annual gifts of <fmt:formatNumber pattern="$###,###,###" value="${crum.annualGift}"/>
										to an Irrevocable (Crummey) Trust utilizing annual exlusion
										gifts.
									</li>
									<li>
										The trustee uses the gifts to loan money to the
										Multigererational Trust, which then purchases a
										<fmt:formatNumber pattern="$###,###,###" value="${crum.lifeDeathBenefit}"/>
										life insurance policy on both ${epg.clientFirstName} and ${epg.spouseFirstName}
										lives with an annual premium of <fmt:formatNumber pattern="$###,###,###" value="${crum.lifePremium}"/>
										The multigenerational trust is both the owner and the beneficiary
										of the life insurance policy.
									</li>
									<li>
										Upon the second death, the life insurance proceeds are paid to
										the Multigenerational Trust free of both income and estate
										taxes.
									</li>
									<li>
										The multigenerational trust then repays the load to this trust
										and distributes the balance of the life insurance proceeds
										according to the Multigenerational Trust instructions.
								</ol>
							</td>
						</tr>
						<tr>
							<td>
								Other Issues
							</td>
						</tr>
						<tr>
							<td>
								<ul>
									<li>
										Trustee must notify the beneficiaries in writing of the gift
										made to this trust. (Crummey power withdrawal right letters
										drafted by the attorney).
									<li>
										Beneficiaries normally have a certain amount of time (e.g. 30
										days) to 'demand' their share of the gift. This withdrawal
										right creates a "present interest (vs a "future interest")
										gift".
									<li>
										Beneficiaries can refuse the gift in writing, or waive the
										fgift by allowing their option to lapse.
								</ul>
							</td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
		<%@include file="/inc/aesFooter.jspf"%>
	</body>
</html>
