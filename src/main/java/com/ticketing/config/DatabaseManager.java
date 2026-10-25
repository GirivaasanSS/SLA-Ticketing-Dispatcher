package com.ticketing.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DatabaseManager {
   
    private static final String URL = "jdbc:mysql://localhost:3306/wipro_helpdesk";
    private static final String USER = "root";
    private static final String PASS = "password"; 

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASS);
    }

    public static void logNewTicket(String email, String category, String description) {
        String sql = "INSERT INTO tickets (client_email, issue_category, description, sla_deadline) VALUES (?, ?, ?, DATE_ADD(NOW(), INTERVAL 4 HOUR))";
        try (Connection conn = getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, email);
            pstmt.setString(2, category);
            pstmt.setString(3, description);
            pstmt.executeUpdate();
            System.out.println("DB LOG: Logged new ticket for " + email);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void escalateTicket(String email) {
        String sql = "UPDATE tickets SET status = 'Red Flag - Escalate to Team Leader', is_escalated = TRUE WHERE client_email = ? AND status != 'Resolved'";
        try (Connection conn = getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, email);
            int updated = pstmt.executeUpdate();
            if (updated > 0) {
                System.out.println("DB ALERT: Ticket escalated for " + email);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
