package com.teag.taglib;

import java.io.IOException;
import java.io.StringWriter;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.JspFragment;
import javax.servlet.jsp.tagext.SimpleTagSupport;

/**
 * @author Paul Stay
 * Description  Input Element : Accepts user input inside a form
 * Date jan 2008
 * Copyright @ Teag Software LLC.
 *
 */
public class HtmlInput extends SimpleTagSupport {
	private String name;
	private String type;
	private String value;
	private String size;
	private String maxlength;
	private String src;
	private String alt;
	private String onclick;
	private String onmousedown;
	private String onmouseup;
	private String onmouseout;
	private String onmouseover;
	
	public HtmlInput() {
		type="text";
	}

	@Override
	public void doTag() throws JspException, IOException {
		StringWriter stringWriter = new StringWriter();
		StringBuffer buffer = stringWriter.getBuffer();
		JspWriter out = getJspContext().getOut();
		JspFragment body = getJspBody();
		
		buffer.append("<input type=\"" + getType() + "\" ");
		// Handle the above options.....
		if(value!= null && value.length()> 0)
			buffer.append("value=\"" + getValue() + "\" ");
		if(size!= null && size.length()> 0)
			buffer.append("size=\"" + getSize() + "\" ");
		if(maxlength!= null && maxlength.length()> 0)
			buffer.append("maxlength=\"" + getMaxlength() + "\" ");
		if(src!= null && src.length()> 0)
			buffer.append("src=\"" + getSrc() + "\" ");
		if(alt!= null && alt.length()> 0)
			buffer.append("alt=\"" + getAlt() + "\" ");
		if(onclick!= null && onclick.length()> 0)
			buffer.append("onclick=\"" + getOnclick() + "\" ");
		if(onmousedown!= null && onmousedown.length()> 0)
			buffer.append("onmousedown=\"" + getOnmousedown() + "\" ");
		if(onmouseup!= null && onmouseup.length()> 0)
			buffer.append("onmouseup=\"" + getOnmouseup() + "\" ");
		if(onmouseout!= null && onmouseout.length()> 0)
			buffer.append("onmouseout=\"" + getOnmouseout() + "\" ");
		if(onmouseover!=null && onmouseover.length()>0)
			buffer.append("onmouseover='" + getOnmouseover() + "' ");
		
		buffer.append(">");
		if(body != null)
			body.invoke(stringWriter);
		buffer.append("</input>");
		out.println(stringWriter);
	}
	
	
	
	public String getAlt() {
		return alt;
	}
	public String getMaxlength() {
		return maxlength;
	}
	public String getName() {
		return name;
	}
	public String getOnclick() {
		return onclick;
	}
	public String getOnmousedown() {
		return onmousedown;
	}
	public String getOnmouseout() {
		return onmouseout;
	}
	public String getOnmouseover() {
		return onmouseover;
	}
	public String getOnmouseup() {
		return onmouseup;
	}
	public String getSize() {
		return size;
	}
	public String getSrc() {
		return src;
	}
	public String getType() {
		return type;
	}
	public String getValue() {
		return value;
	}
	public void setAlt(String alt) {
		this.alt = alt;
	}
	public void setMaxlength(String maxlength) {
		this.maxlength = maxlength;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setOnclick(String onclick) {
		this.onclick = onclick;
	}
	public void setOnmousedown(String onmousedown) {
		this.onmousedown = onmousedown;
	}
	public void setOnmouseout(String onmouseout) {
		this.onmouseout = onmouseout;
	}
	public void setOnmouseover(String onmouseover) {
		this.onmouseover = onmouseover;
	}
	public void setOnmouseup(String onmouseup) {
		this.onmouseup = onmouseup;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public void setSrc(String src) {
		this.src = src;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setValue(String value) {
		this.value = value;
	}
}
