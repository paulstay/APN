<%@ tag language='java' import="com.estate.constants.*" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ attribute name="value" required="true" %>
<%
	RetirementLife at = RetirementLife.getCodeType(value);
	out.println(at.description());
%>
