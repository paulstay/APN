<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<jsp:useBean id='fcf' scope='session' type='com.teag.estate.FCFTool' />
<jsp:forward page="/servlet/PDFTest?tool=fcf"/>
<p>Reports Page
