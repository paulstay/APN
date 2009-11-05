<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%@ page import="com.estate.constants.*"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="teagtags" prefix="teag"%>
 <table border='1' align='center' >
 	<tr>
 		<td colspan='12' align='center'><h4>Retirement Plan Maximizer (TM) Summary</h4></td>
 	</tr>
 	<tr>
 		<th colspan='2' align='center' >Year</th>
		<th>Plan Balance Beg of Year</th>
		<th>Earning @ <fmt:formatNumber pattern='##.#%' value='${rpm.planGrowth}'/></th>
		<th>Plan Dist.</th>
		<th>End of Year Bal.</th>
		<th>Total Taxes</th>
		<th>Net Distribution to Trust</th>
		<th>Trust Life Premium</th>
		<th>End of Year Trust</th>
		<th>Life Death Benefit</th>
		<th>Net to Family</th>
 	</tr>
 	<c:forEach var='i' begin='0' end='35'>
 		<tr>
 			<td>${i}</td>
 			<td align='right' ><fmt:formatNumber pattern='####' value='${rpm.rpmTable[i][1] }'/></td>
 			<td align='right' ><fmt:formatNumber pattern='###,###,###' value='${rpm.rpmTable[i][2] }'/></td>
 			<td align='right' ><fmt:formatNumber pattern='###,###,###' value='${rpm.rpmTable[i][3] }'/></td>
 			<td align='right' ><fmt:formatNumber pattern='###,###,###' value='${rpm.rpmTable[i][4] }'/></td>
 			<td align='right' ><fmt:formatNumber pattern='###,###,###' value='${rpm.rpmTable[i][5] }'/></td>
 			<td align='right' ><fmt:formatNumber pattern='###,###,###' value='${rpm.rpmTable[i][6] }'/></td>
 			<td align='right' ><fmt:formatNumber pattern='###,###,###' value='${rpm.rpmTable[i][7] }'/></td>
 			<td align='right' ><fmt:formatNumber pattern='###,###,###' value='${rpm.rpmTable[i][8] }'/></td>
 			<td align='right' ><fmt:formatNumber pattern='###,###,###' value='${rpm.rpmTable[i][9] }'/></td>
 			<td align='right' ><fmt:formatNumber pattern='###,###,###' value='${rpm.rpmTable[i][10] }'/></td>
 			<td align='right' ><fmt:formatNumber pattern='###,###,###' value='${rpm.rpmTable[i][11] }'/></td>
 		</tr>
 	</c:forEach>
 	
 </table>

