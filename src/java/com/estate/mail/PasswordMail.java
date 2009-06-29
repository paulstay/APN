package com.estate.mail;

/**
 * Generate Email to reset the user password.....
 * @author Paul Stay
 * Date May 2009
 * copyright 2009 @ Advanced Practice Network
 */

import javax.mail.*;
import javax.mail.internet.*;
import com.sun.mail.smtp.*;
import java.util.*;
import com.estate.security.*;

public class PasswordMail {

    String name;
    String phone;
    String Email;
    String comments;
    String company;
    
    String toMail;
    String email;
    String fromMail;
    String smtpServer;
    String subject;
    boolean debug = true;
    String defaultText = "Password reset request. Please contact the APN offices";

    public void send(){
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

            msg.setContent(bText, "text/html");

            msg.setHeader("X-Mailer", "APNMail");
			msg.setSentDate(new Date());
			msg.saveChanges();

			t = (SMTPTransport) session.getTransport("smtp");
            t.setStartTLS(true);
			t.connect(smtpServer, "edgeadmin@advpractice.com", ".almelo.");
			t.sendMessage(msg, msg.getAllRecipients());
			t.close();
			System.out.println("Message sent");

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
    }

    public String genBody() {
        PasswordGenerator pg = new PasswordGenerator();
        pg.setLength(8);
        String nPassword = pg.getPass();
        String msg = "Your new password has been reset to the following: <B> " + nPassword + "</b>";
        return msg;
	}

    public static void main(String args[]){
        PasswordMail pm = new PasswordMail();
        pm.setSmtpServer("imap.gmail.com");
        pm.setSubject("test email");
        pm.setFromMail("edgeadmin@advpractice.com");
        pm.setToMail("paul.stay@gmail.com");
        pm.setComments("this is a test");
        pm.send();
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String Email) {
        this.Email = Email;
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

    public boolean isDebug() {
        return debug;
    }

    public void setDebug(boolean debug) {
        this.debug = debug;
    }

    public String getDefaultText() {
        return defaultText;
    }

    public void setDefaultText(String defaultText) {
        this.defaultText = defaultText;
    }

    public String getFromMail() {
        return fromMail;
    }

    public void setFromMail(String fromMail) {
        this.fromMail = fromMail;
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

}
