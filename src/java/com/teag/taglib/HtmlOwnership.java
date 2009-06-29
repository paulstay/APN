package com.teag.taglib;

import java.io.IOException;
import java.io.StringWriter;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import com.estate.constants.Ownership;

public class HtmlOwnership extends SimpleTagSupport {

	String value;
	
	@Override
	public void doTag() throws JspException, IOException {
		StringWriter stringWriter = new StringWriter();
		StringBuffer buffer = stringWriter.getBuffer();
		JspWriter out = getJspContext().getOut();

		try {
			int aId = Integer.parseInt(value);

			Ownership own = Ownership.getType(aId);
			buffer.append(own.description());
			
		} catch (Exception e){
			buffer.append(Ownership.CP.description());
		}
		out.println(stringWriter);
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}
