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

		<title>RPM Information</title>

		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<link rel="stylesheet" type="text/css" href="<%=path %>/css/styles.css">
	</head>
	<jsp:useBean id='userInfo' scope='session'
		type='com.teag.bean.PdfBean' />
	<jsp:useBean id='rpm' scope='session'
		type='com.estate.toolbox.Rpm' />
	<body>
	<%@include file="/inc/aesHeader.jspf" %>
		<h3 align='center'>
			Retirement Plan Maximizer 2
		</h3>
		<h4 align='center'>
			Tool Information for ${userInfo.firstName} ${userInfo.lastName}
		</h4>
		<table  align='center' width='80%'>
			<tr>
				<td><%@include file='menu.jspf'%></td>
				<td align='center'>
					<teag:form name='tForm' action='servlet/StdRpmServlet' method='post'>
						<teag:hidden name="pageView" value="tool"/>
						<teag:hidden name="action" value="none"/>
						<table align='left'>
							<tr>
								<td colspan='3' align='center'>
									Enter the following information for calculating the 
									Retirement Plan Maximizer
								</td>
							</tr>
							<tr>
								<td colspan='3'>&nbsp;</td>
							</tr>
							<tr>
								<td align='right'>Plan Current Value</td>
								<td>&nbsp;</td>
								<td>
									<teag:text name='planValue' fmt='number' pattern='###,###,###' value='${rpm.planValue }'/>
								</td>
							</tr>
							<tr>
								<td align='right'>Plan Growth</td>
								<td>&nbsp;</td>
								<td>
									<teag:text name='planGrowth' fmt='percent' pattern='##.###%' value='${rpm.planGrowth }'/>
								</td>
							</tr>
							<tr>
								<td align='right'>Client Age</td>
								<td>&nbsp;</td>
								<td>
									<teag:text name='clientAge' fmt='number' pattern='###' value='${rpm.clientAge }'/>
								</td>
							</tr>
							<tr>
								<td align='right'>Beneficiary Age</td>
								<td>&nbsp;</td>
								<td>
									<teag:text name='spouseAge' fmt='number' pattern='###' value='${rpm.spouseAge }'/>
								</td>
							</tr>
							<tr>
								<td align='right'>Payment Term</td>
								<td>&nbsp;</td>
								<td>
									<teag:text name='term' fmt='number' pattern='###' value='${rpm.term }'/>
								</td>
							</tr>
							<tr>
								<td align='right'>State Income Tax Rate</td>
								<td>&nbsp;</td>
								<td>
									<teag:text name='stateIncomeTaxRate' fmt='percent' pattern='##.###%' value='${rpm.stateIncomeTaxRate}'/>
								</td>
							</tr>
							<tr>
								<td align='right'>Securities Growth</td>
								<td>&nbsp;</td>
								<td>
									<teag:text name='secGrowthRate' fmt='percent' value='${rpm.secGrowthRate }'/>
								</td>
							</tr>
							<tr>
								<td align='right'>Securities Dividend</td>
								<td>&nbsp;</td>
								<td>
									<teag:text name='secDivRate' fmt='percent' pattern='##.####%' value='${rpm.secDivRate }'/>
								</td>
							</tr>
							<tr>
								<td align='right'>Securities Turnover Rate</td>
								<td>&nbsp;</td>
								<td>
									<teag:text name='secTurnoverRate' fmt='percent' value='${rpm.secTurnoverRate }'/>
								</td>
							</tr>
							<tr>
								<td align='right'>Life Insurance Premium (Year)</td>
								<td>&nbsp;</td>
								<td>
									<teag:text name='lifeInsPremium' fmt='number' pattern='###,###,###' value='${rpm.lifeInsPremium }'/>
								</td>
							</tr>
							<tr>
								<td align='right'>Life Insurance Death Benefit</td>
								<td>&nbsp;</td>
								<td>
									<teag:text name='lifeInsDeathBenefit' fmt='number' pattern='###,###,###' value='${rpm.lifeInsDeathBenefit }'/>
								</td>
							</tr>
							<tr>
								<td colspan='3'>
									&nbsp;
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
