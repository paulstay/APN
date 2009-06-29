package com.teag.taglib;

import java.io.IOException;
import java.io.StringWriter;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import com.estate.constants.DebtTypes;

public class HtmlDebt extends SimpleTagSupport {
	String value;

	@Override
	public void doTag() throws JspException, IOException {
		StringWriter stringWriter = new StringWriter();
		StringBuffer buffer = stringWriter.getBuffer();
		JspWriter out = getJspContext().getOut();

		try {
			int aId = Integer.parseInt(value);
			DebtTypes debt = DebtTypes.getType(aId);
			buffer.append(debt.description());

		} catch (Exception e) {
			buffer.append(DebtTypes.P.description());
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
