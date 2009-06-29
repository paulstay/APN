package com.teag.taglib;


import java.io.IOException;
import java.io.StringWriter;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import com.estate.constants.BusinessTypes;

public class HtmlBusiness extends SimpleTagSupport {
	String value;
	
	@Override
	public void doTag() throws JspException, IOException {
		StringWriter stringWriter = new StringWriter();
		StringBuffer buffer = stringWriter.getBuffer();
		JspWriter out = getJspContext().getOut();

		try {
			int aId = Integer.parseInt(value);


			BusinessTypes biz = BusinessTypes.getType(aId);
			buffer.append(biz.description());
			
		} catch (Exception e){
			buffer.append(BusinessTypes.O.description());
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
