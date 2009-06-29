package com.teag.taglib;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.BodyTagSupport;

/**
 * @author Paul Stay
 * Copyright 2008 TEAG Software LLC
 * Date January 2008
 *
 * Support for parsing Percent values, even if the '%' sign is not appended to the input string. Append it if 
 * it is not. Similar to the JST fmt:formatNumber but for parsing percent values only.
 *
 */
public class ParsePercent extends BodyTagSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8893827469835574960L;
	
	/**
	 * Scope types
	 */
	
	private static final String REQUEST = "request";
	private static final String SESSION = "SESSION";
	private static final String APPLICATION = "APPLICATION";
	
	protected String value;
	protected boolean valueSpecified;
	protected String pattern;

	private String var;
	private int scope;
	
	public ParsePercent() {
		super();
		init();
	}
	
	@Override
	public int doEndTag() throws JspException {
		String input = null;
		
		if(valueSpecified) {
			input = value;
		} else {
			if(bodyContent != null && bodyContent.getString() != null) 
				input = bodyContent.getString().trim();
		}
		
		if((input == null) || (input.equals(""))) {
			if(var != null){
				pageContext.removeAttribute(var,scope);
			}
			return EVAL_PAGE;
		}
		
		// Make sure we have a % at the end of the string, if not add one.
		if(input.indexOf('%') < 0) {
			input = input + "%";
		}
		
		NumberFormat parser = null;
		if((pattern != null) && !pattern.equals("")) {
			parser = new DecimalFormat(pattern);
		} else {
			parser = NumberFormat.getPercentInstance();
		}
		
		Number parsed = null;
		try {
			parsed = parser.parse(input);
		} catch(ParseException pe) {
			throw new JspException("PARSE_PERCENT_ERROR", pe);
		}
	
		if(var != null) {
			pageContext.setAttribute(var,parsed,scope);
		} else {
			try {
				pageContext.getOut().print(parsed);
			} catch (java.io.IOException ie) {
				throw new JspTagException(ie.toString(), ie);
			}
		}
		
		return EVAL_PAGE;
	}

	public String getPattern() {
		return pattern;
	}
	
	public int getScope() {
		return scope;
	}
	
	public String getValue() {
		return value;
	}

	public String getVar() {
		return var;
	}

	private void init() {
		value = pattern = var = null;
		valueSpecified = false;
		scope = PageContext.PAGE_SCOPE;
	}

	@Override
	public void release() {
		init();
	}

	public void setPattern(String pattern) {
		this.pattern = pattern;
	}

	public void setScope(String scope) {
		int ret = PageContext.PAGE_SCOPE;
		if(REQUEST.equalsIgnoreCase(scope))
			ret = PageContext.REQUEST_SCOPE;
		if(SESSION.equalsIgnoreCase(scope))
			ret = PageContext.SESSION_SCOPE;
		if(APPLICATION.equalsIgnoreCase(scope))
			ret = PageContext.APPLICATION_SCOPE;
		this.scope = ret;
	}

	public void setValue(String value) throws JspException {
		valueSpecified = true;
		this.value = value;
	}

	public void setVar(String var) {
		this.var = var;
	}
}
