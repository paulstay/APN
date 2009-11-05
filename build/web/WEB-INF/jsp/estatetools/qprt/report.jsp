<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<jsp:useBean id='qprt' scope='session' type='com.teag.estate.QprtTool' />
<jsp:forward page="/servlet/PDFTest?tool=qprt"/>
<p>Reports Page
