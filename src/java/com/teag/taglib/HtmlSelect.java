package com.teag.taglib;

import java.io.IOException;
import java.util.Map;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;

/**
 * @author Paul Stay
 * Description Select Tag 
 *
 */
public class HtmlSelect extends SimpleTagSupport {
	
	private String name;
	private String multiple;
	private Map<String,String> options;
	private String optionValue;
	private String onChange;
	
	public HtmlSelect() {
	}

	@Override
	public void doTag() throws JspException, IOException {
		JspWriter out = getJspContext().getOut();
		StringBuffer buf = new StringBuffer("");
		
		buf.append("<select name='" + name + "' ");
		if(multiple != null && !multiple.isEmpty()) {
			buf.append("multiple='multiple' ");
		}
		if(onChange!=null && !onChange.isEmpty()){
			buf.append("onchange=\"" + onChange + "\" ");
		}
		
		buf.append(">");
		out.println(buf.toString());
		
		if(options != null && !options.isEmpty()) {				// handle options if we have an option map!
			for(Map.Entry<String,String> entry : options.entrySet()){
				String key = entry.getKey();
				String value = entry.getValue();
				if(!optionValue.isEmpty() && optionValue.equalsIgnoreCase(key)) {
					out.println("<option value='" + value + "' selected='selected'>" + key + "</option>");  
				} else if(!optionValue.isEmpty() && optionValue.equalsIgnoreCase(value)) {
					out.println("<option value='" + value + "' selected='selected'>" + key + "</option>");  
				} else {
					out.println("<option value='" + value + "'>" + key + "</option>");    //need to add selected here!
				}
			}
		} else {
			getJspBody().invoke(null);
		}
		out.println("</select>");
	}
	

	public String getMultiple() {
		return multiple;
	}



	public String getName() {
		return name;
	}



	public String getOnChange() {
		return onChange;
	}

	public Map<String, String> getOptions() {
		return options;
	}

	public String getOptionValue() {
		return optionValue;
	}

	public void setMultiple(String multiple) {
		this.multiple = multiple;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setOnChange(String onChange) {
		this.onChange = onChange;
	}

	public void setOptions(Map<String, String> options) {
		this.options = options;
	}

	public void setOptionValue(String optionValue) {
		this.optionValue = optionValue;
	}
}
