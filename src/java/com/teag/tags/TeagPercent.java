/**
 * 
 */
package com.teag.tags;

import java.io.IOException;
import java.text.DecimalFormat;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.SimpleTagSupport;

/**
 * @author Paul Stay
 * Date Jan 16, 2008
 * Copyright 2006 Teag Software LLC.
 *
 */
public class TeagPercent extends SimpleTagSupport {
	private String var;

	@Override
	public void doTag() throws JspException, IOException {
		DecimalFormat df = new DecimalFormat("##.####%");
		DecimalFormat pf = new DecimalFormat("##.#########");
		double x;
		try{
			if( var.indexOf('%') > 0)
				x = (df.parse(var)).doubleValue();
			else
				x = (pf.parse(var)).doubleValue()/100.0;
		} catch (Exception e) {
			x = 0.0;
		}
		getJspContext().getOut().print(x);
	}

	public String getVar() {
		return var;
	}

	public void setVar(String var) {
		this.var = var;
	}
	
	
}
