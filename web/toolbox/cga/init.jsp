<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%@ page import="com.teag.bean.*" %>
<%@ page import="com.estate.controller.*" %>
<%@ page import="com.estate.constants.*" %>
<%@ page import="com.estate.toolbox.*" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>Initialization for SCIN</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

  </head>
<%

	PdfBean userInfo = new PdfBean();
	userInfo.setFirstName("Clark");
	userInfo.setMiddleName("");
	userInfo.setLastName("Jones");
	userInfo.setGender("M");
	userInfo.setFinalDeath(25);
	
	session.setAttribute("userInfo", userInfo);

	CGA cga = new CGA();
	
	cga.setFmv(1000000);
	cga.setBasis(250000);
	cga.setIrsRate(.05);
	cga.setIrsDate(new Date());
	cga.setClientAge(65);
	cga.setSpouseAge(65);
	cga.setGrowth(.07);
	cga.setEndBegin(1);
	cga.setFrequency(1);
	cga.setSingle(true);
	cga.setCalcType("M");
			
	session.setAttribute("cga", cga);
 %>
<jsp:forward page="user.jsp"></jsp:forward>
</html>
