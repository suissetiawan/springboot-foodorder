# Food Order API - Spring Boot

A robust and feature-rich Food Ordering System API built with Spring Boot 3.x. This project features a complete flow from authentication, menu management, cart system, to advanced sales reporting with multi-format export.

## 🚀 Key Features

*   **Secure Authentication**: JWT-based stateless authentication with Role-Based Access Control (Admin & Customer).
*   **Menu Management**: Full CRUD for food/drink items (Admin only).
*   **Cart & Checkout**: 
    *   Stock validation before order processing.
    *   Real-time stock deduction upon successful payment.
*   **Global Soft Delete**: Entire system uses soft deletion (Hibernate `@SQLRestriction`) for data integrity.
*   **Advanced Reporting**: 
    *   Daily & Monthly sales reports with date filtering.
    *   Top 5 selling items analysis.
    *   **Export formats**: Professional PDF (.pdf) with styled tables and grand revenue analysis.
*   **Data Seeding**: Built-in seeder to generate 15+ realistic orders for testing.
*   **Containerized Environment**: Ready-to-use Docker and Docker Compose configuration.

## 🛠️ Technology Stack

*   **Backend**: Java 21, Spring Boot 3.x
*   **Database**: MySQL
*   **Security**: Spring Security 6, JWT (jjwt)
*   **Data Access**: Spring Data JPA, Hibernate
*   **Utility**: Lombok, Jackson (Auto-hiding null fields), OpenPDF (Reporting)
*   **DevOps**: Docker, Docker Compose

## 📦 Database Schema

The database follows a relational structure optimized for reporting. You can find the detailed ERD in DBML format at: `databases/erd_dbdiagram.text`.

*   **Users**: Stores credentials and roles.
*   **Menus**: Product catalog with stock management.
*   **Carts**: Temporary storage for customer items.
*   **Orders**: Transaction headers with status (PAID, COMPLETED, etc.).
*   **Order Items**: Snapshots of items purchased (Quantity & Price at Buy).

## ⚙️ Installation & Running

### Prerequisites
*   Java 21 or higher
*   Maven 3.8+
*   MySQL (or Docker)

### Running Locally
1. Configure database in `src/main/resources/application.properties`.
2. Run the application:
   ```bash
   ./mvnw spring-boot:run
   ```

### Running with Docker
```bash
docker-compose up --build
```

## 📡 API Endpoints

### Authentication
*   `POST /api/auth/register`: Create new account
*   `POST /api/auth/login`: Get JWT token

### Reporting (Admin Only)
*   `GET /api/report/daily`: Daily sales JSON
*   `GET /api/report/monthly`: Monthly sales JSON
*   `GET /api/report/download`: Unified PDF export (`?type=daily|monthly&date=...&month=...&year=...`) 
*   `GET /api/report/seed-data`: Generate 15 sample orders

## 📂 Project Structure
```text
src/main/java/com/dibimbing/foodorder/
├── config/       # Security & Jackson Config
├── controller/   # API Endpoints
├── dto/          # Data Transfer Objects
├── entity/       # JPA Entities (Base & Domain)
├── enums/        # Role & Status Enums
├── exception/    # Global Exception Handlers
├── repository/   # JPA Repositories
├── service/      # Business Logic
└── util/         # Report Generation & JWT Logic
```
