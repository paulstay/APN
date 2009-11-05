<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<jsp:useBean id='grat' scope='session' type='com.teag.estate.GratTool' />
<jsp:forward page="/servlet/PDFTest?tool=grat"/>
<p>Reports Page
