package com.ticketing.app;

import com.ticketing.service.TicketingEngine;

public class Main {
    public static void main(String[] args) {
        System.out.println("Starting SLA-Enforced Email Ticketing Dispatcher...");
        TicketingEngine engine = new TicketingEngine();
        
       
        while (true) {
            try {
                System.out.println("Checking inbox for new client requests...");
                engine.processIncomingEmails();
                // Pause for 1 minute before checking again
                Thread.sleep(60000); 
            } catch (InterruptedException e) {
                System.out.println("Service interrupted.");
                break;
            }
        }
    }
}
