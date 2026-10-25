-- MySQL Database Schema for SLA Ticketing System
CREATE DATABASE IF NOT EXISTS wipro_helpdesk;
USE wipro_helpdesk;

CREATE TABLE IF NOT EXISTS tickets (
    ticket_id INT AUTO_INCREMENT PRIMARY KEY,
    client_email VARCHAR(255) NOT NULL,
    issue_category VARCHAR(100),
    description TEXT,
    status VARCHAR(50) DEFAULT 'Pending Client',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    sla_deadline TIMESTAMP,
    is_escalated BOOLEAN DEFAULT FALSE
);
