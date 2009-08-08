<%@ tag language='java' import="com.estate.constants.Title" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ attribute name="value" required="true" %>
<%
	int aId = Integer.parseInt(value);
	Title title = Title.getType(aId);
	out.println(title.description());
%>
