<%@ page language="java" import="java.util.*" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%@ include file="/inc/init.jspf" %>
<%@ include file="/inc/aesHeader.jspf" %>

<%@ include file="/inc/aesFooter.jspf" %>