<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="teagtags" prefix="teag"%>
<teag:form name='menu' action='AdvanceToolbox/CLAT/processMenu.jsp' method='post'>
<input type='hidden' name='pageView' value='Tool'/>
<table>
	<tr>
		<td>
			<input type="image" src='${context}/toolbtns/UserInfo.png' alt='User'
				onclick="document.menu.pageView.value='User'"
				onmousedown="this.src='${context}/toolbtns/UserInfo_down.png'"
				onmouseover="this.src='${context}/toolbtns/UserInfo.png'"
				onmouseout="this.src='${context}/toolbtns/UserInfo.png'"/>
		</td>
	</tr>
	<tr>
		<td>
			<input type="image" src='${context}/toolbtns/ToolSetup.png' alt='ToolSetup'
				onclick="document.menu.pageView.value='Tool'"
				onmousedown="this.src='${context}/toolbtns/ToolSetup_down.png'"
				onmouseover="this.src='${context}/toolbtns/ToolSetup.png'"
				onmouseout="this.src='${context}/toolbtns/ToolSetup.png'"/>
		</td>
	</tr>
	<tr>
		<td>
			<input type="image" src='${context}/toolbtns/Summary.png' alt='Summary'
				onclick="document.menu.pageView.value='Summary'"
				onmousedown="this.src='${context}/toolbtns/Summary_down.png'"
				onmouseover="this.src='${context}/toolbtns/Summary.png'"
				onmouseout="this.src='${context}/toolbtns/Summary.png'"/>
		</td>
	</tr>
	<tr>
		<td>
			<input type="image" src='${context}/toolbtns/Report.png' alt='Report'
				onclick="document.menu.pageView.value='Report'"
				onmousedown="this.src='${context}/toolbtns/Report_down.png'"
				onmouseover="this.src='${context}/toolbtns/Report.png'"
				onmouseout="this.src='${context}/toolbtns/Report.png'"/>
		</td>
	</tr>
	<tr>
		<td>
			<input type="image" src='${context}/toolbtns/Quit.png' alt='Quit'
				onclick="document.menu.pageView.value='Quit'"
				onmousedown="this.src='${context}/toolbtns/Quit_down.png'"
				onmouseover="this.src='${context}/toolbtns/Quit.png'"
				onmouseout="this.src='${context}/toolbtns/Quit.png'"/>
		</td>
	</tr>
</table>
</teag:form>
