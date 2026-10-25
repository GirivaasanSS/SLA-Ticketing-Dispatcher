# SLA-Enforced Email Ticketing & Automated Self-Help Dispatcher

An automated service desk pipeline designed to monitor client emails, triage issues using Regex, instantly dispatch self-help documents, and manage SLAs via MySQL.

## Tech Stack
* **Java 11**
* **JavaMail API / Jakarta Mail** (IMAP/SMTP handling)
* **MySQL & JDBC** (Ticket tracking and escalation)
* **Maven** (Dependency management)

## Setup Instructions
1. **Database:** Execute `src/main/resources/schema.sql` in your local MySQL Workbench to create the `wipro_helpdesk` database and `tickets` table.
2. **Database Credentials:** Update `USER` and `PASS` in `src/main/java/com/ticketing/config/DatabaseManager.java`.
3. **Email Configuration:** Update `EMAIL` and `APP_PASSWORD` in `src/main/java/com/ticketing/service/EmailService.java`. Use a Gmail App Password, not your standard login.
4. **Run:** Execute the `Main.java` class in your IDE.
