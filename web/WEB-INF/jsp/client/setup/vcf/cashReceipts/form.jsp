<%@ page language="java" pageEncoding="ISO-8859-1"%>
<%@ page import="com.estate.constants.*" %>
<%@ page import="java.util.*" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="teagtags" prefix="teag" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<! DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<base href="<%=basePath%>">
    <title>Cash Flow Receipt</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<link href="css/style.css" rel="stylesheet" media="screen">
<body>
<!-- insert bean here using page scope, if its an add, than the id should be passed as zero -->
<jsp:useBean id="vcf" scope='request' type="com.teag.bean.VCFBean" />
<jsp:setProperty name="vcf" property="id" param="id" />
<c:if test="${param.action == 'Add' }">
	<c:set var="btn1" value='Add'/>
</c:if>
<c:if test="${param.action == 'Edit' }">
	<c:set var="btn1" value='Save'/>
</c:if>
<%@ include file="/inc/aesHeader.jspf" %>
<h3 align='center'>Cash Flow Variable Line Item</h3>
<br>
		<teag:form action="servlet/VcfCashReceiptsServlet" method="post"
			name="iform">
			<input type='hidden' name='action' value='Cancel' />
			<input type='hidden' name='id' value='${vcf.id}' />
			<input type='hidden' name='cfFlag' value='C' />
			<table align='center'>
				<tr>
					<td align='right'>
						Description :
					</td>
					<td align='left'>
						<teag:text name="description" value="${vcf.description }" />
					</td>
				</tr>
				<tr>
					<td align='right'>
						Starting Year :
					</td>
					<td align='left'>
						<teag:text name='startYear' fmt='number' pattern='###' value='${vcf.startYear}' />
					</td>
				</tr>
				<tr>
					<td align='right'>
						Ending Year :
					</td>
					<td align='left'>
						<teag:text name='endYear' fmt='number' pattern='####' value='${vcf.endYear}' />
					</td>
				</tr>
				<fmt:formatNumber var="v" value="${vcf.value}" pattern="###,###,###" />
				<tr>
					<td align="right">
						Amount :
					</td>
					<td align="left">
						<teag:text name="value" fmt='number' pattern='###,###,###' value="${vcf.value}" />
					</td>
				</tr>
				<tr>
					<td align="right">
						Growth :
					</td>
					<td align="left">
						<teag:text name="growth" fmt='percent' value="${vcf.growth}" />
					</td>
				</tr>
				<tr>
					<td align="right">
						Taxable amount of Income (%) :
					</td>
					<td align="left">
						<teag:text name="prctTax" fmt='percent' value="${vcf.prctTax}" />
					</td>
				</tr>
				<tr>
					<td align='right'>
						Use Flag
					</td>
					<td align='left'>
						<teag:select name='useFlag'>
							<teag:option label='Both' value='B' selected='${vcf.useFlag}'/>
							<teag:option label='Scneario 1' value='1' selected='${vcf.useFlag}'/>
							<teag:option label='Scenario 2' value='2' selected='${vcf.useFlag}'/>
						</teag:select>
					</td>
				</tr>
				<tr>
					<td align='center' colspan='2'>
						<c:choose>
							<c:when test="${btn=='Add' }">
								<input type='submit'
									onclick="document.iform.action.value='insert'" value='${btn1 }' />
							</c:when>
							<c:otherwise>
								<input type='submit'
									onclick="document.iform.action.value='update'" value='${btn1 }' />
							</c:otherwise>
						</c:choose>
						<input type='submit'
							onclick='document.iform.action.value="Cancel"' value='Cancel' />
						<input type='submit'
							onclick='document.iform.action.value="Delete"' value='Delete' />
					</td>
				</tr>
			</table>
		</teag:form>
		<%@ include file="/inc/aesFooter.jspf" %>
</body>
</html>
