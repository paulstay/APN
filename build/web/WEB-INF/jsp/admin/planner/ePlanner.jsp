<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
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

		<title>Add and Edit Planner Data</title>

		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<link href="<%=path %>/css/styles.css" rel="stylesheet" >
	</head>
	<jsp:useBean id='planner' scope="session"
		type='com.teag.bean.PlannerBean' />
	<body>
		<%@include file="/inc/aesHeader.jspf"%>
		<teag:form action="servlet/PlannerForm" method="post" name="aForm">
			<teag:hidden name='pageView' value='add' />
			<br>
			<table width='70%' align='center'>
				<tr>
					<td>&nbsp;</td>
					<td align='left' colspan='2'>
						<h3>
							Enter/Edit Planner Data
						</h3>
					</td>
				</tr>
				<tr>
					<td>&nbsp;</td>
					<td colspan='2' align='left'>
						<span class='error-red'>${errorMsg}</span>
					</td>
				</tr>
				<tr>
					<td width='33%' align='right'>
						First Name
					</td>
					<td align='left' width='33%'>
						<teag:text name="firstName" value="${planner.firstName}" />
						<span class='form-error'>${errorFirstName}</span>
					</td>
					<td width='5%'>&nbsp;</td>
				</tr>
				<tr>
					<td align='right'>
						Middle Name
					</td>
					<td align='left'>
						<teag:text name="middleName" value="${planner.middleName}" />
					</td>
					<td>&nbsp;</td>
				</tr>
				<tr>
					<td align='right'>
						Last Name
					</td>
					<td align='left'>
						<teag:text name="lastName" value="${planner.lastName}" />
						<span class='form-error'>${errorLastName}</span>
					</td>
					<td>&nbsp;</td>
				</tr>
				<tr>
					<td align='right'>
						Suffix
					</td>
					<td align='left'>
						<teag:text name="suffix" value="${planner.suffix}" />
					</td>
					<td>&nbsp;</td>
				</tr>
				<tr>
					<td align='right'>
						Address 1
					</td>
					<td align='left'>
						<teag:text name="address1" value="${planner.address1}" />
					</td>
					<td>&nbsp;</td>
				</tr>
				<tr>
					<td align='right'>
						Address 2
					</td>
					<td align='left'>
						<teag:text name="address2" value="${planner.address2}" />
					</td>
					<td>&nbsp;</td>
				</tr>
				<tr>
					<td align='right'>
						City
					</td>
					<td align='left'>
						<teag:text name="city" value="${planner.city}" />
					</td>
					<td>&nbsp;</td>
				</tr>
				<tr>
					<td align='right'>
						State
					</td>
					<td align='left'>
						<select name="state">
							<option value="${planner.state}" selected>
								<c:if test="${view == 'add' }">
									--Select a State--
								</c:if>
								<c:if test="${view == 'edit' }">
								${planner.state}
								</c:if>
							</option>
							<option value="AL">
								Alabama
							</option>
							<option value="AK">
								Alaska
							</option>
							<option value="AZ">
								Arizona
							</option>
							<option value="AR">
								Arkansas
							</option>
							<option value="CA">
								California
							</option>
							<option value="CO">
								Colorado
							</option>
							<option value="CT">
								Connecticut
							</option>
							<option value="DE">
								Delaware
							</option>
							<option value="FL">
								Florida
							</option>
							<option value="GA">
								Georgia
							</option>
							<option value="HI">
								Hawaii
							</option>
							<option value="ID">
								Idaho
							</option>
							<option value="IL">
								Illinois
							</option>
							<option value="IN">
								Indiana
							</option>
							<option value="IA">
								Iowa
							</option>
							<option value="KS">
								Kansas
							</option>
							<option value="KY">
								Kentucky
							</option>
							<option value="LA">
								Louisiana
							</option>
							<option value="ME">
								Maine
							</option>
							<option value="MD">
								Maryland
							</option>
							<option value="MA">
								Massachusetts
							</option>
							<option value="MI">
								Michigan
							</option>
							<option value="MN">
								Minnesota
							</option>
							<option value="MS">
								Mississippi
							</option>
							<option value="MO">
								Missouri
							</option>
							<option value="MT">
								Montana
							</option>
							<option value="NE">
								Nebraska
							</option>
							<option value="NV">
								Nevada
							</option>
							<option value="NH">
								New Hampshire
							</option>
							<option value="NJ">
								New Jersey
							</option>
							<option value="NM">
								New Mexico
							</option>
							<option value="NY">
								New York
							</option>
							<option value="NC">
								North Carolina
							</option>
							<option value="ND">
								North Dakota
							</option>
							<option value="OH">
								Ohio
							</option>
							<option value="OK">
								Oklahoma
							</option>
							<option value="OR">
								Oregon
							</option>
							<option value="PA">
								Pennsylvania
							</option>
							<option value="RI">
								Rhode Island
							</option>
							<option value="SC">
								South Carolina
							</option>
							<option value="SD">
								South Dakota
							</option>
							<option value="TN">
								Tennessee
							</option>
							<option value="TX">
								Texas
							</option>
							<option value="UT">
								Utah
							</option>
							<option value="VT">
								Vermont
							</option>
							<option value="VA">
								Virginia
							</option>
							<option value="WA">
								Washington
							</option>
							<option value="WV">
								West Virginia
							</option>
							<option value="WI">
								Wisconsin
							</option>
							<option value="WY">
								Wyoming
							</option>
						</select>
					</td>
					<td>&nbsp;</td>
				</tr>
				<tr>
					<td align='right'>
						Zipcode
					</td>
					<td align='left'>
						<teag:text name="zipcode" value="${planner.zipcode}" />
					</td>
					<td>&nbsp;</td>
				</tr>
				<tr>
					<td align='right'>
						Phone
					</td>
					<td align='left'>
						<teag:text name="phone" value="${planner.phone}" />
					</td>
					<td>&nbsp;</td>
				</tr>
				<tr>
					<td align='right'>
						Email
					</td>
					<td align='left'>
						<teag:text name="email" value="${planner.email}" />
						<span class='form-error'>${errorEmail}</span>
					</td>
					<td>&nbsp;
					</td>
				</tr>
				<tr>
					<td align='right'>
						Status
					</td>
					<td align='left'>
						<teag:select name='status'>
							<teag:option label='Active' value='A'
								selected='${planner.status }' />
							<teag:option label='Non Active' value='N'
								selected='${planner.status }' />
						</teag:select>
					</td>
					<td>&nbsp;</td>
				</tr>
				<tr>
					<td align='right'>
						Username (hint: use first inital, last name)
					</td>
					<td align='left'>
						<teag:text name="pusername" value="${planner.userName}" />
						<span class='form-error'>${errorUserName}</span>
					</td>
					<td>&nbsp;</td>
				</tr>
				<tr>
					<td align='right'>
						Password
					</td>
					<td align='left'>
						<teag:text name="upassword" value="${planner.password}" />
						<span class='form-error'>${errorPass}</span>
					</td>
					<td>&nbsp;</td>
				</tr>
				<tr>
					<td>&nbsp;</td>
					<td align='left'>
						<c:if test="${view == 'add' }">
							<input type='button' value='Create'
								onclick="document.aForm.pageView.value='add';document.aForm.submit()" />&nbsp;
							<input
								type='submit' value='Quit'
								onclick="document.aForm.pageView.value='quit';document.aForm.submit()" />
						</c:if>
						<c:if test="${view == 'edit' }">
							<input type='button' value='Save'
								onclick="document.aForm.pageView.value='update';document.aForm.submit()" />&nbsp;
							<input
								type='submit' value='Quit'
								onclick="document.aForm.pageView.value='quit';document.aForm.submit()" />
						</c:if>
					</td>
					<td>&nbsp;</td>
				</tr>
			</table>
		</teag:form>
		<%@include file="/inc/aesFooter.jspf"%>
	</body>
</html>
