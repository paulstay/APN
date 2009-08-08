<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<form name='mform' id='mform' action="toolbox/clatWithLife/processMenu.jsp" method="get">
<input type='hidden' name='pageView' value='Tool'/><br>
<table>
<tr><td>
	<input type="image" src="toolbtns/UserInfo.png" alt="UserInfo"
		onclick="document.mform.pageView.value='User'"
		onmouseover="this.src='toolbtns/UserInfo_over.png';"
		onmouseout="this.src='toolbtns/UserInfo.png';"
		onmousedown="this.src='toolbtns/UserInfo_down.png';"/>
</td></tr>
<tr><td align='left'>
	<input type="image" src="toolbtns/assets.png" alt="Asset"
		onclick="document.mform.pageView.value='Asset'"
		onmouseover="this.src='toolbtns/assets_over.png';"
		onmouseout="this.src='toolbtns/assets.png';"
		onmousedown="this.src='toolbtns/assets_down.png';"/>
</td></tr>
<tr><td align='left'>
	<input type="image" src="toolbtns/life.png" alt="Life Insurance"
		onclick="document.mform.pageView.value='Life';"
		onmouseover="this.src='toolbtns/life_over.png';"
		onmouseout="this.src='toolbtns/life.png';"
		onmousedown="this.src='toolbtns/life_down.png';"/>
</td></tr>
<tr><td align='left'>
	<input type="image" src="toolbtns/ToolSetup.png" alt="Tool Setup"
		onclick="document.mform.pageView.value='Tool'"
		onmouseover="this.src='toolbtns/ToolSetup_over.png';"
		onmouseout="this.src='toolbtns/ToolSetup.png';"
		onmousedown="this.src='toolbtns/ToolSetup_down.png';"/>
</td></tr>
<tr><td align='left'>
	<input type="image" src="toolbtns/Summary.png" alt="Summary"
		onclick="document.mform.pageView.value='Summary'"
		onmouseover="this.src='toolbtns/Summary_over.png';"
		onmouseout="this.src='toolbtns/Summary.png';"
		onmousedown="this.src='toolbtns/Summary_down.png';"/>
</td></tr>
<tr><td align='left'>
	<input type="image" src="toolbtns/Report.png" alt="Report"
		onclick="document.mform.pageView.value='Report'"
		onmouseover="this.src='toolbtns/Report.png';"
		onmouseout="this.src='toolbtns/Report.png';"
		onmousedown="this.src='toolbtns/Report_down.png';"/>
</td></tr>
<tr><td align='left'>
	<input type="image" src="toolbtns/Cancel.png" alt="Cancel"
		onclick="document.mform.pageView.value='Cancel'"
		onmouseover="this.src='toolbtns/Cancel_over.png';"
		onmouseout="this.src='toolbtns/Cancel.png';"
		onmousedown="this.src='toolbtns/Cancel_down.png';"/>
</td></tr>
</table>
</form>
