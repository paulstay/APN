<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%@ page import="com.estate.constants.*"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="teagtags" prefix="teag"%>
 <table border='1' align='center' >
 	<tr>
 		<td colspan='11'><h4>Current Retirement Plan, and Distribution after Both Deaths</h4></td>
 	</tr>
 	<tr>
 		<th>&nbsp;</th>
		<th>Year</th>
		<th>Beg. Year</th>
		<th>Earning @<fmt:formatNumber pattern='##.#%' value='${rpm.planGrowth}'/></th>
		<th>Minimum Distribution</th>
		<th>End of Year</th>
		<th>Retirement Plan Taxes @ Death</th>
		<th>% to Taxes</th>
		<th>Accum'd in Trust</th>
		<th>Plan and Trust Money</th>
		<th>Total Taxes</th>
		<th>Net to Family</th>
 	</tr>
 	<c:forEach var='i' begin='0' end='35'>
 		<tr>
 			<td>${i}</td>
 			<td><fmt:formatNumber pattern='####' value='${rpm.rpTable[i][1] }'/></td>
 			<td align='center' ><fmt:formatNumber pattern='###,###,###' value='${rpm.rpTable[i][2] }'/></td>
 			<td align='center' ><fmt:formatNumber pattern='###,###,###' value='${rpm.rpTable[i][3] }'/></td>
 			<td align='center' ><fmt:formatNumber pattern='###,###,###' value='${rpm.rpTable[i][4] }'/></td>
 			<td align='center' ><fmt:formatNumber pattern='###,###,###' value='${rpm.rpTable[i][5] }'/></td>
 			<td align='center' ><fmt:formatNumber pattern='###,###,###' value='${rpm.rpTable[i][6] }'/></td>
 			<td align='center' ><fmt:formatNumber pattern='##.##%' value='${rpm.rpTable[i][7] }'/></td>
 			<td align='center' ><fmt:formatNumber pattern='###,###,###' value='${rpm.rpTable[i][8] }'/></td>
 			<td align='center' ><fmt:formatNumber pattern='###,###,###' value='${rpm.rpTable[i][9] }'/></td>
 			<td align='center' ><fmt:formatNumber pattern='###,###,###' value='${rpm.rpTable[i][10] }'/></td>
 			<td align='center' ><fmt:formatNumber pattern='###,###,###' value='${rpm.rpTable[i][11] }'/></td>
 		</tr>
 	</c:forEach>
 	
 </table>


