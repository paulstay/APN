package com.teag.taglib;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;


/**
 * @author Paul Stay
 * Description - HTML option element, must be nested inside a select tag....
 * Date Jan. 2008
 * copyright @ teag software llc.
 *
 */
public class HtmlOption extends SimpleTagSupport {


	private String label;
	private String value;
	private String selected;
	
	public HtmlOption() {
	}
	
	@Override
	public void doTag() throws JspException, IOException {
		JspWriter out = getJspContext().getOut();
		StringBuffer buf = new StringBuffer("");
		
		// Make sure we are inside a select tag...
		HtmlSelect select = null;

		try {
			select = (HtmlSelect)getParent();
			select.getName();
		} catch(ClassCastException cce) {
			String msg = "error: option must be inside a select";
			throw new JspTagException(msg);
		}

		buf.append("<option value='" + getValue() + "' ");
		if(selected != null && selected.length()> 0){
			if(selected.equalsIgnoreCase(value)){
				buf.append("selected='selected' ");
			}
		}
		buf.append(">");
		buf.append(label);
		buf.append("</option>");
		out.println(buf.toString());
	}

	public String getLabel() {
		return label;
	}

	public String getSelected() {
		return selected;
	}

	public String getValue() {
		return value;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public void setSelected(String selected) {
		this.selected = selected;
	}

	public void setValue(String value) {
		this.value = value;
	}

	
}
