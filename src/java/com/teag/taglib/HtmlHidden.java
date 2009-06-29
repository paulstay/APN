package com.teag.taglib;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;
/**
 * @author Paul Stay
 * Description Add a hidden filed inside a form
 * copyright @2008 Teag Software LLC.
 *
 */
public class HtmlHidden extends SimpleTagSupport {
	private String name;
	private String value;
	
	@Override
	public void doTag() throws JspException, IOException {
		JspWriter out = getJspContext().getOut();
		out.println("<input type='hidden' name='" + getName() + "' value='" + getValue() + "'/>");
	}

	public String getName() {
		return name;
	}

	public String getValue() {
		return value;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
	
}
