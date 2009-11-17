<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://jakarta.apache.org/taglibs/input-1.0"
	prefix="input"%>
<%@ taglib tagdir="/WEB-INF/tags/teag" prefix="teag"%>
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

		<title>Tool Wizard</title>

		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<link rel="stylesheet" type="text/css" href="<%=path%>/css/styles.css">
	</head>
	<script>
		function toolVars(toolId, tableId, planToolId, aValue) {
			document.tForm.action.value = aValue;
			document.tForm.id.value=toolId;
			document.tForm.toolType.value=tableId;
			document.tForm.planTableId.value=planToolId;
		}
	</script>
	<body>
		<%@ include file="/inc/aesHeader.jspf"%>
		<%@ include file="/inc/aesQuickJump.jspf"%>
		<table width='45%' align='center'>
			<tr>
				<td align='left'>
					<p>
						Please select the Estate Planning Tool that you wish to add to the
						Estate, you will be redirected to the Estate Tool page that will
						allow you to place assets, and work with the tool to mimimize the
						Estate Tax.
					<p>
						The existing tools already used in the Estate Plan are listed
						below. You can delete, edit, or view any of these tools.
					</p>
				</td>
			</tr>
		</table>
		<br>
		<br>
		<form name='tForm' action='servlet/ToolSelectServlet' method='post'>
			<input type='hidden' name='id' value='' />
			<input type='hidden' name='toolType' value='' />
			<input type='hidden' name='planTableId' value='' />
			<input type='hidden' name='action' value='' />
			<table align='center'>
				<caption align='top'>
					Existing Tools
				</caption>
				<c:forEach var='tool' items="${toolArray}">
					<tr>
						<td>
							${tool.description}
						</td>
						<td>
							<button type='submit'
								onclick="javascript: toolVars(${tool.toolId},${tool.toolTableId}, ${tool.id},'edit');">
								Edit
							</button>
						</td>
						<td>
							<button type='submit'
								onclick="javascript: toolVars(${tool.toolId},${tool.toolTableId}, ${tool.id},'delete');">
								Delete
							</button>
						</td>
					</tr>
				</c:forEach>
			</table>
		</form>
		<br></br>
		<table align='center' width='30%'>
			<tr align='center'>
				<td>
					<p>
						Clicking on the following button will generate the full report for
						the Estate using the existing tools and the current assets already
						entered into the database. Please note that this may take some
						time to generate and download to your computer. Please only click
						this once.
					</p>
				</td>
			<tr>
				<td align='center'>
					<form name='fullreport' id='fullreport' method='post'
						action='servlet/FullReport'>
						<button type='submit'>
							Report
						</button>
					</form>
				</td>
			</tr>
		</table>
		<br>
		<form name='aForm' action='servlet/ToolSelectServlet' method='post'>
			<input type='hidden' name='action' value='add' />
			<table width='40%' align='center' frame='box'>
				<tr>
					<td>
						<p>
							Clicking on the following button will generate the full report
							for the Estate using the existing tools and the current assets
							already entered into the database. Please note that this may take
							some time to generate and download to your computer. Please only
							click this once.
						</p>
					</td>
				</tr>
				<tr>
					<td align='center'>
						<select name='toolName'>
							<option value='FLP'>
								Family Limited Partnership
							</option>
							<option value="LLC">
								Limited Liability Company
							</option>
							<option value="CLAT">
								Charitable Lead Annuity Trust
							</option>
							<option value="CRT">
								Charitable Remainder Trusts
							</option>
							<option value="FCF">
								Family Charitable Foundation
							</option>
							<option value="GRAT">
								Grantor Retained Annuity Trust
							</option>
							<option value="SIDIT">
								Intentionally Defective Irrevocable Trust
							</option>
							<option value="IDIT">
								Intentionally Defective Irrevocable Trust and GRAT combination
							</option>
							<option value="CRUM">
								Irrevocable (Crummey) Trust
							</option>
							<option value="LIFE">
								Life Insurance Tool
							</option>
							<option value="LAP">
								Liquid Asset Protection Plan
							</option>
							<option value="MULTI">
								MutiGenerational Trust
							</option>
							<option value="PAT">
								Private Annuity
							</option>
							<option value="QPRT">
								Qualified Personal Residence Trust
							</option>
							<option value="RETI">
								Retirement Plan Maximizer (TM)
							</option>
							<option value="SCIN">
								Self-Canceling Installment Note
							</option>
							<option value="TCLAT">
								Testimentary Charitable Lead Annuity Trust
							</option>
                                                        <option value="SPLIT">
                                                                Private Split Dollar (Marc S. only)
                                                        </option>
						</select>
					</td>
				</tr>
				<tr>
					<td align='center'>
						<button type='submit'>
							Add Tool
						</button>
					</td>
				</tr>
			</table>
		</form>
		<%@ include file="/inc/aesFooter.jspf"%>
	</body>
</html>
