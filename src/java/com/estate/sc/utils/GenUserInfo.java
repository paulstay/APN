package com.estate.sc.utils;

import com.teag.bean.*;

public class GenUserInfo {
	PdfBean userInfo = new PdfBean();
	PersonBean client;
	PersonBean spouse;
	PlannerBean admin;
	
	public void initClient(long id) {
		client = new PersonBean();
		client.setId(id);
		client.initialize();
	}

}
