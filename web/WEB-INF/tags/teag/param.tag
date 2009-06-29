<%
	java.util.Enumeration e = request.getParameterNames();
	while(e.hasMoreElements()) {
		String name = (String) e.nextElement();
		String value = request.getParameter(name);
		System.out.println("parameter : " + name + " : " + value);
	}
%>
