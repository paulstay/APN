<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
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

		<title>Webinar Page</title>

		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

	</head>

	<body>
		<%@ include file="/inc/aesHeader.jspf"%>
		<p>
			The following is a recording of the Webinar held on December 19,
			2008. We recorded it so that all could have a chance to hear about
			the merger between TEAG and the Advance Practice Network. If the video does not show up in
			your browser, you can right click on the link and "Save Target As" or "Save Object As" to copy it to your
			computer.
		</p>
		<object id="MediaPlayer" width=640 height=480
			classid="CLSID:22D6f312-B0F6-11D0-94AB-0080C74C7E95"
			standby="Loading Windows Media Player components..."
			type="application/x-oleobject"
			codebase="http://activex.microsoft.com/activex/controls/mplayer/en/nsmp2inf.cab#Version=6,4,7,1112">
			<param name="filename" value="admin/Webinar/Webinar.wmv">
			<param name="Showcontrols" value="True">
			<param name="autoStart" value="True">
		</object>
		<ul>
			<li>
				<a href="admin/Webinar/Webinar.wmv">December 19, 2008</a>
			</li>
		</ul>
		<%@ include file="/inc/aesFooter.jspf"%>
	</body>
</html>
