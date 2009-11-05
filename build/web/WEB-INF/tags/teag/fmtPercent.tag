<%@ tag import="java.text.*" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ attribute name='value' required='true' %>
<%@ attribute name='variable' required="true" rtexprvalue="false" %>
<%@ variable name-from-attribute='variable' alias='v' scope='AT_BEGIN' %>
<%
	DecimalFormat df = new DecimalFormat("##.####%");
	DecimalFormat pf = new DecimalFormat("##.#########");
	double x;
	try{
		x = (df.parse(value)).doubleValue();
	} catch (Exception e) {
		x = (pf.parse(value)).doubleValue()/100.0;
	}
%> 
<c:set var='v' value='<%=Double.toString(x)%>'/>
