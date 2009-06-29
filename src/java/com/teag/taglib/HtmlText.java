package com.teag.taglib;

import java.io.IOException;
import java.io.StringWriter;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;

/**
 * @author Paul Stay Description, Unlink the general Input tag, this tag
 *         creates a input element and provides formatting for the body or value
 *         of the tag.
 * January 2008
 * copyright @2008 TEAG Software LLC.
 * 
 */
public class HtmlText extends SimpleTagSupport {

	private String align;
	private String maxLength;
	private String name;
	private String value;
	private String type;
	private String fmt;				// Can be currency, percent, number, or text (default)
	private String pattern;

	public HtmlText() {
		this.align = "right";
		this.value = "";
		this.type = "text";
		this.maxLength = "";
		this.fmt = "text";
	}

	/**
	 * doTag() - output the form element....
	 * 
	 * @return
	 */
	@Override
	public void doTag() throws JspException, IOException {
		JspWriter out = getJspContext().getOut();
		String retValue = "";
		
		if(fmt!=null && fmt.equalsIgnoreCase("date")){
			HtmlUtil hu = new HtmlUtil();
			retValue = hu.fmtDate(fmt, pattern, value);
		}
		
		if(fmt!=null && !fmt.equalsIgnoreCase("text")) {
			HtmlUtil hu = new HtmlUtil();
			retValue = hu.fmtValue(fmt, pattern, value);
		} else {
			retValue = value;
		}
		
		StringWriter stringWriter = new StringWriter();
		StringBuffer buff = stringWriter.getBuffer();
		buff.append("<input type='" + getType() + "' ");
		buff.append("name='" + getName() + "' ");
		buff.append("align='" + getAlign() + "' ");
		buff.append("value='" + retValue + "' ");
		buff.append("size='" + getMaxLength() + "' ");
		buff.append("></input>");
		out.println(stringWriter);
	}
	
	public String getAlign() {
		return align;
	}

	public String getFmt() {
		return fmt;
	}

	public String getMaxLength() {
		return maxLength;
	}

	public String getName() {
		return name;
	}

	public String getPattern() {
		return pattern;
	}

	public String getType() {
		return type;
	}

	public String getValue() {
		return value;
	}

	public void setAlign(String align) {
		this.align = align;
	}

	public void setFmt(String fmt) {
		this.fmt = fmt;
	}

	public void setMaxLength(String maxLength) {
		this.maxLength = maxLength;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setPattern(String pattern) {
		this.pattern = pattern;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setValue(String value) {
		this.value = value;
	}

}
