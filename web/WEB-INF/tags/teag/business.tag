<%@ tag language='java' import="com.estate.constants.*" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ attribute name="value" required="true" %>
<%
	int aId = Integer.parseInt(value);
	BusinessTypes at = BusinessTypes.getType(aId);
	out.println(at.description());
%>
