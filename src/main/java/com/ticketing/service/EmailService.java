package com.ticketing.service;

import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;

public class EmailService {
   
    private static final String EMAIL = "yourmail@gmail.com";
    private static final String APP_PASSWORD = "your password";

    public Message[] fetchUnreadEmails() {
        try {
            Properties props = new Properties();
            props.put("mail.store.protocol", "imaps");
            Session session = Session.getInstance(props, null);
            Store store = session.getStore();
            store.connect("imap.gmail.com", EMAIL, APP_PASSWORD);
            
            Folder inbox = store.getFolder("INBOX");
            inbox.open(Folder.READ_WRITE);
            
            // In a real app, use search terms for UNREAD. Simulating with getMessages.
            Message[] messages = inbox.getMessages();
            return messages;
        } catch (Exception e) {
            System.out.println("Error connecting to IMAP: " + e.getMessage());
            return new Message[0];
        }
    }

    public void sendReply(String toEmail, String subject, String body) {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(EMAIL, APP_PASSWORD);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(EMAIL));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
            message.setSubject("RE: " + subject);
            message.setText(body);
            Transport.send(message);
            System.out.println("Email Auto-Reply sent to: " + toEmail);
        } catch (Exception e) {
            System.out.println("Failed to send email: " + e.getMessage());
        }
    }
}
