<%@ tag language='java' import="com.estate.constants.DebtTypes" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ attribute name="value" required="true" %>
<%
	int aId = Integer.parseInt(value);
	DebtTypes at = DebtTypes.getType(aId);
	out.println(at.description());
%>
