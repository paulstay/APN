<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ attribute name="value" required="true" %>
<%
	int aId = Integer.parseInt(value);
	com.estate.constants.AdvisorTypes at = com.estate.constants.AdvisorTypes.getType(aId);
	out.println(at.description());
%>
