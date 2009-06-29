package com.teag.taglib;

import java.io.IOException;
import java.io.StringWriter;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.JspFragment;
import javax.servlet.jsp.tagext.SimpleTagSupport;


/**
 * Generate HTML Form element
 * @author Paul
 *
 */

public class HtmlForm extends SimpleTagSupport {
	/**
	 * Generated serialVersionUID
	 */
	private static final long serialVersionUID = 8253812466886319894L;
	private String name;
	private String method;
	
	private String action;

	/**
	 * doTag() - output the form element....
	 * @return
	 */
	@Override
	public void doTag() throws JspException, IOException {
		JspWriter out = getJspContext().getOut();
		JspFragment body = getJspBody();
		
		StringWriter stringWriter = new StringWriter();
		StringBuffer buff = stringWriter.getBuffer();
		buff.append("<form name='" + getName() + "' action='" + getAction() + "' method='"+getMethod()+"'>");
		body.invoke(stringWriter);
		buff.append("</form>");
		out.println(stringWriter);
		
	}
	
	public String getAction() {
		return action;
	}

	public String getMethod() {
		return method;
	}

	public String getName() {
		return name;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public void setName(String name) {
		this.name = name;
	}

	
	
}
