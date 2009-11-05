<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%@ page import="com.estate.constants.*"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="teagtags" prefix="teag"%>
 <table border='1'>
 	<tr>
 		<td colspan='5' align='center'>Net (after all taxes) Amount to Family</td>
 	</tr>
 	<tr>
 		<th>&nbsp;</th>
		<th>Year</th>
		<th>Amount to Family Before</th>
		<th>Amount to Family RPM</th>
		<th>RPM vs Before Advantage</th>
 	</tr>
 	<c:forEach var='i' begin='0' end='35'>
 		<tr>
 			<td>${i}</td>
 			<td align='right'><fmt:formatNumber pattern='####' value='${rpm.rpmTable[i][1] }'/></td>
 			<td align='right'><fmt:formatNumber pattern='###,###,###' value='${rpm.rpTable[i][11] }'/></td>
 			<td align='right'><fmt:formatNumber pattern='###,###,###' value='${rpm.rpmTable[i][11] }'/></td>
 			<td align='right'><fmt:formatNumber pattern='###,###,###' value='${rpm.rpmTable[i][11] - rpm.rpTable[i][11] }'/></td>
 		</tr>
 	</c:forEach>
 	
 </table>

