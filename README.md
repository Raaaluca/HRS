# HRS
 HRS is a Human Resources web application project created for Scoala Informala de IT course.
 It is built with the following technologies:

# Spring Boot
    - created with spring initialzr - https://start.spring.io/
    - dependencies used: Lombok, Spring Data JPA, Spring Security, Spring Web
# Thymeleaf
    - used for html templates to connect the Controllers to the UI interface
# Boostrap
    - for styling and design of the html pages
    - free Boostrap template used - https://bootstrapmade.com/quickstart-bootstrap-startup-website-template/

# APP STRUCTURE
    Entities:
    -> Department (represented by a Manager)
    -> Employee (has the Manager as superior)
    -> LeaveRequest (Employee and Manager can create them, Managers can approve them)
    -> Manager (has multiple Employees)
    -> User (used for Login, linked to an Employee or a Manager, has [ADMIN, EMPLOYEE, MANAGER] role)

# APP FUNCTIONALITIES
    - can login based on email and password (national id)
    - can create/delete employees
    - can view and create leave requests (as employee and manager)
    - can view and approve leave requests (as manager)
    - can view and edit own personal details for manager and employee
    - can view a report of all employees in a company (as HR Admin)
    - can update employee/manager details from report (as HR Admin)
    - can assign an employee to a manager automatically based on job title
    - can prevent deletion of a HR Admin or a Manager

# Project references
    - Boostrap template: https://bootstrapmade.com/quickstart-bootstrap-startup-website-template/
    - Project structure, login and entity relations: https://github.com/adrianbucur83/class22project/tree/master/src/main/java/com/siit/class22project
    - Validations: https://medium.com/@tanersahin/java-bean-validation-with-javax-validation-5c11d9ebc409
    - Page visibility and authorizations: https://www.baeldung.com/spring-security-thymeleaf
    - Thymeleaf dropdown selection and others: https://www.baeldung.com/thymeleaf-select-option