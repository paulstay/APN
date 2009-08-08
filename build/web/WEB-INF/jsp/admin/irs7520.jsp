<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
		<title>IRS 7520 Rates</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<link rel="stylesheet" type="text/css" href="<%=path%>/css/styles.css">
	</head>
	<body>
		<%@ include file="/inc/aesHeader.jspf"%>
		<%@ include file="/inc/aesQuickJump.jspf"%>
		<h3 align='center'>
			IRIS Section 7520 Rates
		</h3>
		<div align='center'>
		<table width='65%'>
		<tr><td>
		<p>
			To be used to value certain charitable interests in trusts (CLAT,
			CRUT) and some other Trusts (IDIT, GRAT). Pursuant to Internal
			Revenue Code 7520, the interest rate for a particular month is the
			rate that is 120 percent of the applicable federal midterm rate
			(compounded annually) for the month in which the valuation date
			falls. That rate is then rounded to the nearest two-tenths of one
			percent.
		</p>
		</td></tr>
		</table>
		</div>
		<br>
		<div align='center'>
			<table width='80%' border='1'>
				<tr>
					<th>
						Year
					</th>
					<th>
						Jan
					</th>
					<th>
						Feb
					</th>
					<th>
						Mar
					</th>
					<th>
						Apr
					</th>
					<th>
						May
					</th>
					<th>
						Jun
					</th>
					<th>
						Jul
					</th>
					<th>
						Aug
					</th>
					<th>
						Sep
					</th>
					<th>
						Oct
					</th>
					<th>
						Nov
					</th>
					<th>
						Dec
					</th>
				</tr>
				<c:forEach var='i' begin='0' end='${(maxYear - startYear)}'>
					<tr>
						<td>
							${startYear + i}
						</td>
						<td align='center'>
							${rates[i][0]}
						</td>
						<td align='center'>
							${rates[i][1]}
						</td>
						<td align='center'>
							${rates[i][2]}
						</td>
						<td align='center'>
							${rates[i][3]}
						</td>
						<td align='center'>
							${rates[i][4]}
						</td>
						<td align='center'>
							${rates[i][5]}
						</td>
						<td align='center'>
							${rates[i][6]}
						</td>
						<td align='center'>
							${rates[i][7]}
						</td>
						<td align='center'>
							${rates[i][8]}
						</td>
						<td align='center'>
							${rates[i][9]}
						</td>
						<td align='center'>
							${rates[i][10]}
						</td>
						<td align='center'>
							${rates[i][11]}
						</td>
					</tr>
				</c:forEach>
			</table>
		</div>
		<br>
		<div align='center'>
		<a href='admin/index.jsp'>Return to Main Menu</a>
		</div>
		<%@ include file="/inc/aesFooter.jspf"%>
	</body>
</html>
