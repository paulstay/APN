package com.teag.bean.utils;


public class HeadingText {
	String text;
	String header;
	String body;
	
	
	
	public String getBody() {
		return body;
	}
	public String getHeader() {
		return header;
	}
	public String getText() {
		this.text = header.toUpperCase() + "\r\n\r\n" + body; 
		return text;
	}
	public void setBody(String body) {
		this.body = body;
	}
	public void setHeader(String header) {
		this.header = header;
	}
	
	public void setText(String text) {
		this.text = text;
		int idx = text.indexOf("\r\n\r\n");
		header = text.substring(0,idx);
		body = text.substring(idx +4);
	}
	
	
}
