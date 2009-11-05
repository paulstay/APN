<%@ tag language='java' import="com.estate.constants.NotePayableTypes" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ attribute name="value" required="true" %>
<%
	String v = (String) value;
	com.estate.constants.NotePayableTypes at = com.estate.constants.NotePayableTypes.getCodeType(v);
	out.println(at.description());
%>
