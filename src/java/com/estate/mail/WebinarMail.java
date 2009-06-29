package com.estate.mail;


import javax.mail.*;
import javax.mail.internet.*;

import com.sun.mail.smtp.*;

import java.util.*;

public class WebinarMail {
	
	String smtpServer;
	String toMail;
	String fromMail;
	String subject;
	String body;
	
	String name;
	String company;
	String phone;
	String comments;
	String email;
	boolean debug = false;
	
	String defaultText = "Please send webinar info";
	
	public void send() {
		SMTPTransport t;
		try {
			Properties props = System.getProperties();
			props.put("mail.smtp.host", smtpServer);
			props.put("mail.smtp.auth", "true");
			Session session = Session.getDefaultInstance(props,null);
			session.setDebug(debug);
			Message msg = new MimeMessage(session);
			msg.setFrom(new InternetAddress(fromMail));
			msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toMail, false));
			msg.setSubject(subject);
			String bText = genBody();
			if( bText != null) {
				msg.setText(bText);
			} else {
				msg.setText(defaultText);
			}
			msg.setHeader("X-Mailer", "TeagMail");
			msg.setSentDate(new Date());
			msg.saveChanges();
			
			t = (SMTPTransport) session.getTransport("smtp");
			t.connect(smtpServer, "stay", "alms4poor");
			t.sendMessage(msg, msg.getAllRecipients());
			t.close();
			System.out.println("Message sent");
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public String genBody() {
		StringBuffer sb = new StringBuffer("");
		sb.append("The following information was submitted for information regarding the TEAG Webinar\n");
		sb.append("Name: " + name + "\n");
		sb.append("Company: " + company + "\n");
		sb.append("Phone: " + phone + "\n");
		sb.append("Email: " + email + "\n");
		sb.append("Comments: " + comments + "\n");
		return sb.toString();
	}
	
	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public String getFromMail() {
		return fromMail;
	}

	public void setFromMail(String fromMail) {
		this.fromMail = fromMail;
	}

	public String getSmtpServer() {
		return smtpServer;
	}

	public void setSmtpServer(String smtpServer) {
		this.smtpServer = smtpServer;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getToMail() {
		return toMail;
	}

	public void setToMail(String toMail) {
		this.toMail = toMail;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getDefaultText() {
		return defaultText;
	}

	public void setDefaultText(String defaultText) {
		this.defaultText = defaultText;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public boolean isDebug() {
		return debug;
	}

	public void setDebug(boolean debug) {
		this.debug = debug;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	
	
}
