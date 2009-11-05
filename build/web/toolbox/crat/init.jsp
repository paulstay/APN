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
    
    <title>Initialization for CRT</title>
    
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
	userInfo.setAge(68);
	
	session.setAttribute("userInfo", userInfo);
	
	CRT crt = new CRT();
	crt.setIrsRate(.044);
	crt.setIrsDate(new Date());
	crt.setPayoutRate(.075);
	crt.setInvestmentReturn(.08);
	crt.setFmv(9500000);
	crt.setLiability(0);
	crt.setBasis(1155000);
	crt.setMarginalTaxRate(.4313);
	crt.setEstateTaxRate(.55);
	crt.setCapitalGainsTax(.2);
	crt.setTaxGrowth(.04);
	crt.setTaxIncome(.035);
	crt.setAgi(2000000);
	crt.setInsuranceBenefit(9350000);
	crt.setInsurancePremium(115200);
	crt.setFinalDeath(25);
	crt.setCrtType("CRUT");
	crt.setPayoutOption("P");
	crt.setSpouseAge(0);
	crt.calculate();

	session.setAttribute("crt",crt);
	
	
 %>
<jsp:forward page="user.jsp"></jsp:forward>
</html>
