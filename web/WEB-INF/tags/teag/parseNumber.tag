<%@ tag import="java.text.*" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ attribute name='value' required='true' %>
<%@ attribute name='var' required="true" rtexprvalue="false" %>
<%@ variable name-from-attribute='var' alias='v' scope='AT_BEGIN' %>
<%
	DecimalFormat df = new DecimalFormat("$###,###,###");
	double x = 0;
	if( !value.startsWith("$",0))	
		value = "$" + value;
	try{
		x = (df.parse(value)).doubleValue();
	} catch (Exception e) {
		System.out.println("error parsing number");
	}
%> 
<c:set var='v' value='<%=Double.toString(x)%>'/>
