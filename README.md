# 🏢 HRS — Human Resources System

A full-stack **Human Resources Management** web application built with **Java 21** and **Spring Boot 3**. The system supports three distinct roles — Admin, Manager, and Employee — each with their own dashboard, access controls, and workflows including leave request management, employee reporting, and automatic department assignment.

---

## 🚀 Tech Stack

![Java](https://img.shields.io/badge/Java-21-ED8B00?style=flat&logo=openjdk&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.3.4-6DB33F?style=flat&logo=springboot&logoColor=white)
![Spring Security](https://img.shields.io/badge/Spring_Security-6-6DB33F?style=flat&logo=springsecurity&logoColor=white)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-16-336791?style=flat&logo=postgresql&logoColor=white)
![Thymeleaf](https://img.shields.io/badge/Thymeleaf-3-005F0F?style=flat&logo=thymeleaf&logoColor=white)
![Lombok](https://img.shields.io/badge/Lombok-latest-red?style=flat)
![Bootstrap](https://img.shields.io/badge/Bootstrap-5-7952B3?style=flat&logo=bootstrap&logoColor=white)

| Layer | Technology |
|---|---|
| Backend | Spring Boot 3, Spring MVC, Spring Data JPA |
| Security | Spring Security 6, BCrypt password hashing |
| Frontend | Thymeleaf, Bootstrap 5 |
| Database | PostgreSQL |
| Build | Maven |
| Utilities | Lombok, Bean Validation |

---

## 📐 Architecture

The application follows a layered MVC architecture:

```
Controller  →  Service  →  Repository  →  Database
                 ↕
             DTOs / MapperUtil
```

### Domain Model

```
Department ──── Manager ──┬── Employee ──── LeaveRequest
                          └── LeaveRequest (to manage)
                               
User (login) ──── Manager / Employee (1:1)
```

- **Department** — represents a business unit, managed by one Manager
- **Employee** — belongs to a Department; has a Manager as superior
- **Manager** — oversees multiple Employees; can approve leave requests
- **LeaveRequest** — created by Employees or Managers; approved by Managers
- **User** — handles authentication; linked to either an Employee or a Manager with role `[ADMIN, EMPLOYEE, MANAGER]`

---

## ✨ Features

### 🔐 Authentication & Authorisation
- Form-based login with email + national ID as initial password
- Role-based access control via Spring Security 6 — routes protected per role
- Password hashing with BCryptPasswordEncoder

### 👤 Employee
- View and edit own personal details
- Submit and track leave requests
- View remaining annual leave days

### 🗂️ Manager
- View and approve pending leave requests from their team
- Submit and track their own leave requests
- View and edit own personal details

### 🛡️ HR Admin
- Create and delete employees (deletion of Admin/Manager roles is prevented)
- View a full employee report across the company
- Update any employee or manager's details
- **Automatic department assignment** — employees are assigned to a manager automatically based on their job title

---

## 🗂️ Project Structure

```
src/main/java/ro/siit/HRS/
├── config/          # Spring Security configuration
├── controller/      # MVC controllers (Admin, Employee, Manager)
├── dto/
│   ├── create/      # DTOs for entity creation
│   ├── update/      # DTOs for entity updates
│   └── response/       # DTOs for responses
├── exceptions/      # Custom exception classes
├── model/           # JPA entities
├── repository/      # Spring Data JPA repositories
├── service/
│   ├── EmployeeService.java
│   ├── ManagerService.java
│   └── impl/        # Service implementations
└── util/            # MapperUtil, Role enum
```

---

## ⚙️ Getting Started

### Prerequisites
- Java 21+
- Maven 3.8+
- PostgreSQL 14+

### Setup

1. **Clone the repository**
   ```bash
   git clone https://github.com/Raaaluca/HRS.git
   cd HRS
   git checkout develop
   ```

2. **Create the database**
   ```sql
   CREATE DATABASE hrs_db;
   ```

3. **Configure environment**  
   Copy `application.properties.example` to `application.properties` and fill in your DB credentials:
   ```properties
   spring.datasource.url=jdbc:postgresql://localhost:5432/hrs_db
   spring.datasource.username=YOUR_DB_USER
   spring.datasource.password=YOUR_DB_PASSWORD
   ```

4. **Run the app**
   ```bash
   mvn spring-boot:run
   ```

5. **Access the app**  
   Open `http://localhost:8080` in your browser.

---

## 🔑 Default Credentials

| Role | Username | Password |
|---|---|---|
| Admin | `admin@hrs.com` | *(national ID set at creation)* |
| Manager | `manager@hrs.com` | *(national ID set at creation)* |
| Employee | `employee@hrs.com` | *(national ID set at creation)* |

---

## 📚 References

- [Spring Initializr](https://start.spring.io/)
- [Bootstrap Template — BootstrapMade Quickstart](https://bootstrapmade.com/quickstart-bootstrap-startup-website-template/)
- [Spring Security + Thymeleaf — Baeldung](https://www.baeldung.com/spring-security-thymeleaf)
- [Bean Validation — Medium](https://medium.com/@tanersahin/java-bean-validation-with-javax-validation-5c11d9ebc409)
- [Thymeleaf Select Options — Baeldung](https://www.baeldung.com/thymeleaf-select-option)
- Project structure inspired by: [adrianbucur83/class22project](https://github.com/adrianbucur83/class22project)

---

## 👩‍💻 Author

**Raluca** — built as part of the [Școala Informală de IT](https://www.scoalainformala.ro/) Java course.
