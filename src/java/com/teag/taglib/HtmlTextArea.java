package com.teag.taglib;

import java.io.IOException;
import java.io.StringWriter;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.JspFragment;
import javax.servlet.jsp.tagext.SimpleTagSupport;

/**
 * @author Paul Stay
 * Description TextArea element should be inside a form 
 * copyright @ TEAG Software LLC.
 *
 */
public class HtmlTextArea extends SimpleTagSupport {
	private String name;
	private String value;
	private int rows;
	private int cols;
	
	public HtmlTextArea() {
		rows = -1;
		cols = -1;
		value = null;
	}
	
	@Override
	public void doTag() throws JspException, IOException {
		JspWriter out = getJspContext().getOut();
		JspFragment body = getJspBody();
		
		StringWriter stringWriter = new StringWriter();
		StringBuffer buffer = stringWriter.getBuffer();
		
		buffer.append("<textarea name='" + getName() + "' ");
		if( rows > 0)
			buffer.append("rows='" + Integer.toString(rows) + "' ");
		if( cols > 0)
			buffer.append("cols='" + Integer.toString(cols) + "'");
		buffer.append(">");
		if(value != null && value.length()> 0) {
			buffer.append(value);
		} else {
			body.invoke(stringWriter);
		}
		buffer.append("</textarea>");
		out.println(stringWriter);
	}

	public int getCols() {
		return cols;
	}

	public String getName() {
		return name;
	}

	public int getRows() {
		return rows;
	}

	public String getValue() {
		return value;
	}

	public void setCols(int cols) {
		this.cols = cols;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

	public void setValue(String value) {
		this.value = value;
	}
}
