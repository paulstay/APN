<%@ tag language='java' import="com.estate.constants.Ownership" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ attribute name="value" required="true" %>
<%
	int aId = Integer.parseInt(value);
	Ownership own = Ownership.getType(aId);
	out.println(own.description());
%>
