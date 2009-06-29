<%@ page language="java" import="com.teag.bean.*"
	pageEncoding="ISO-8859-1"%>
<%@ page import="com.estate.constants.*"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="teagtags" prefix="teag" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<! DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<base href="<%=basePath%>">
	<title>Real Estate</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
	<link href="<%=path%>/css/styles.css" rel="stylesheet" media="screen">
	<body>
		<jsp:useBean id="realEstate" scope="request" type="com.teag.bean.RealEstateBean" />
		<%@ include file="/inc/aesHeader.jspf"%>
		<h3 align='center'>
			Real Estate Details
		</h3>
		<br>
		<table align='center' width='70%'>
			<tr>
				<td align="right">
					Description :
				</td>
				<td align="left">
					${realEstate.description}
				</td>
			</tr>
			<tr>
				<td align="right">
					Ownership :
				</td>
				<td align="left">
					<teag:ownership value="${realEstate.ownershipId}" />
				</td>
			</tr>
			<tr>
				<td align="right">
					Title :
				</td>
				<td align="left">
					<teag:title value="${realEstate.titleId }" />
				</td>
			</tr>
			<tr>
				<td align="right">
					City
				</td>
				<td align='left'>
					${realEstate.locationCity}
				</td>
			</tr>
			<tr>
				<td align="right">
					State
				</td>
				<td align="left">
					${realEstate.locationState}
				</td>
			</tr>
			<tr>
				<td align='center' colspan='2' class='text-bold'>
					Details
				</td>
			</tr>
			<fmt:formatNumber var="b" value="${realEstate.basis}"
				pattern="###,###,###" />
			<tr>
				<td align="right">
					Client's Share of Basis :
				</td>
				<td align="left">
					${b}
				</td>
			</tr>
			<fmt:formatNumber var="v" value="${realEstate.value}"
				pattern="###,###,###" />
			<tr>
				<td align="right">
					Client's Share of Value :
				</td>
				<td align="left">
					${v}
				</td>
			</tr>
			<fmt:formatNumber var="g" value="${realEstate.growthRate}"
				pattern="###.####%" />
			<tr>
				<td align="right">
					Growth Rate :
				</td>
				<td align="left">
					${g}
				</td>
			</tr>
			<fmt:formatNumber var='gross' value='${realEstate.grossRents}' />
			<tr>
				<td align='right'>
					Gross Rents (Yearly) :
				</td>
				<td align='left'>
					${gross }
				</td>
			</tr>
			<fmt:formatNumber var='grg' pattern='###.####%'
				value='${realEstate.grossRentsGrowth}' />
			<tr>
				<td align='right'>
					Gross Rents Growth (%) :
				</td>
				<td align='left'>
					${grg}
				</td>
			</tr>
			<fmt:formatNumber var='oe' pattern="###,###,###"
				value='${realEstate.operatingExpenses}' />
			<tr>
				<td align='right'>
					Opearting Expenses ($) :
				</td>
				<td align='left'>
					${oe}
				</td>
			</tr>
			<fmt:formatNumber var='oeg' pattern='###.####%'
				value='${realEstate.growthExpenses}' />
			<tr>
				<td align='right'>
					Operating Expenses Growth (%) :
				</td>
				<td align='left'>
					${oeg}
				</td>
			</tr>
			<c:if test="${realEstate.loanBalance > 0 }">
				<tr>
					<td align='center' colspan='2' class='text-bold'>
						Loan Details
					</td>
				</tr>
				<fmt:formatNumber var="lb" value="${realEstate.loanBalance}"
					pattern="#,###,###" />
				<tr>
					<td align="right">
						Loan Balance :
					</td>
					<td align="left">
						${lb}
					</td>
				</tr>
				<fmt:formatNumber var="li" value="${realEstate.loanInterest}"
					pattern="###.####%" />
				<tr>
					<td align="right">
						Loan Interest Rate (Yearly) :
					</td>
					<td align="left">
						${li}
					</td>
				</tr>
				<fmt:formatNumber var="lp" value="${realEstate.loanPayment}"
					pattern="###,###,###" />
				<tr>
					<td align="right">
						Loan Payment :
					</td>
					<td align="left">
						${lp}
					</td>
				</tr>
				<fmt:formatNumber var='lterm' value="${realEstate.loanTerm}"
					pattern="###" />
				<tr>
					<td align="right">
						Loan Term (Months) :
					</td>
					<td align="left">
						${lterm}
					</td>
				</tr>
				<tr>
					<td align="right">
						Loan Freq. (# of payments per Year)
					</td>
					<td align="left">
						<c:choose>
							<c:when test="${realEstate == '1' }">Annual</c:when>
							<c:when test="${realEstate == '2' }">Semi-Annual</c:when>
							<c:when test="${realEstate == '1' }">Quarterly</c:when>
							<c:otherwise>Monthly</c:otherwise>
						</c:choose>
					</td>
				</tr>
			</c:if>
			<tr>
				<td align='center' class='text-bold' colspan='2'>
					General Notes
				</td>
			</tr>
			<tr>
				<td align="right">
					Notes :
				</td>
				<td align="left">
					${realEstate.notes }
				</td>
			</tr>
			<tr>
				<td align='right'>
					<form action="servlet/RealEstateAssetServlet?action=list"
						method="post">
						<input type='submit' value="Back" />
					</form>
				</td>
				<td align='left'>
					<form
						action="servlet/RealEstateAssetServlet?id=${realEstate.id}&action=edit"
						method="post">
						<input type='submit' value="Edit" />
					</form>
				</td>

			</tr>
		</table>
		<%@ include file="/inc/aesFooter.jspf"%>
	</body>
</html>
