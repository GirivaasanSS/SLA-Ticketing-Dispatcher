package com.ticketing.service;

import com.ticketing.config.DatabaseManager;
import javax.mail.Message;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class TicketingEngine {
    private EmailService emailService = new EmailService();

    public void processIncomingEmails() {
        Message[] messages = emailService.fetchUnreadEmails();
        
        for (Message msg : messages) {
            try {
                String subject = msg.getSubject();
                String sender = msg.getFrom()[0].toString();
                String content = msg.getContent().toString().toLowerCase(); // Simplified extraction
                
                // Keyword Triage Logic (Regex)
                if (Pattern.compile(".*\\b(didn't work|did not work|still failing)\\b.*").matcher(content).find()) {
                    System.out.println("Triage: Client escalation detected.");
                    DatabaseManager.escalateTicket(sender);
                    // Mark as read in production
                    
                } else if (Pattern.compile(".*\\b(vpn|network|connect)\\b.*").matcher(content).find()) {
                    System.out.println("Triage: VPN issue detected.");
                    DatabaseManager.logNewTicket(sender, "VPN Support", subject);
                    
                    String reply = "Hello,\n\nWe received your request regarding VPN connectivity. " +
                                   "Please follow the attached self-help steps (Check internet, verify Cisco AnyConnect, restart). \n\n" +
                                   "If this didn't work, please reply to this email.\n\nRegards,\nWipro Service Desk";
                    emailService.sendReply(sender, subject, reply);
                    
                } else if (Pattern.compile(".*\\b(password|locked|reset)\\b.*").matcher(content).find()) {
                    System.out.println("Triage: Password reset detected.");
                    DatabaseManager.logNewTicket(sender, "Account Management", subject);
                    
                    String reply = "Hello,\n\nTo reset your password, please visit https://wipro.mock-portal.com/reset. " +
                                   "If this didn't work, reply directly to this thread.\n\nRegards,\nService Desk";
                    emailService.sendReply(sender, subject, reply);
                }
                
            } catch (Exception e) {
                System.out.println("Error processing message: " + e.getMessage());
            }
        }
    }
}
