# Utopia Backend API Documentation

Welcome to the documentation for the backend API application of Utopia, designed to power the corresponding frontend web app.

## Overview

This backend API application is built using the Spring Boot Java Framework, created via "Spring Initializr" available at [start.spring.io](https://start.spring.io). It leverages Gradle for dependency management and utilizes Java as the primary programming language.

## Getting Started

To set up the application locally, follow these steps:

1. **Clone the Repository**: Begin by cloning the repository to your local machine.

2. **Database Setup**:
    - Navigate to `./src/main/java/com/utopia/api/config/`.
    - Utilize the provided MySQL database files:
        - `creatable.sql`: Imports the schema of the database.
        - `importable.sql`: Imports both the schema and sample data.
    - Import the desired SQL file based on your requirements into your MySQL instance.

3. **Configure Database Credentials**:
    - Locate the `application.properties` file in the root directory.
    - Update the file with your MySQL username and password. Ensure not to modify other configurations.

4. **Run the Application**:
    - Execute the application. By default, it runs on port 8080.

## Application Structure

### Main Class
The application starts within the main method of the `Application` class located in the `com.utopia.api` package. This class initializes the Spring Boot Application, which subsequently manages the application lifecycle, including class instantiation, configuration, and request handling.

### Configuration
Important configuration classes reside in the `com.utopia.api.config` package. Current configurations include CORS mappings and database settings.

### Controllers
Controllers, responsible for handling incoming HTTP requests and generating responses, are located in the `com.utopia.api.controllers` package. Annotations from the Spring Boot framework control these requests.

### DAO (Data Access Object)
The DAO pattern abstracts and encapsulates data access operations into separate classes. DAO classes, situated in the `com.utopia.api.dao` package, provide a clean interface for CRUD operations, shielding the application from underlying data source intricacies.

### Entities and DTOs
- **Entities**: Data types within the `com.utopia.api.entities` package represent data retrieved from the database.
- **DTOs (Data Transfer Objects)**: DTO classes, housed in their package, facilitate the transfer of multiple objects in a single request, addressing limitations imposed by the Spring Boot `@RequestBody` annotation.

### Public Folder
The `public` folder serves static assets, including HTML, images, and videos. Requests to the root endpoint return the `index.html` by default, while other endpoints are handled by controller classes.

## API ENDPOINTS
   - "/categories": returns list of all categories (possible for a product)
   - "/roles": returns list of all roles (user roles)
   - "/statuses": returns list of all statuses (order statuses)
   - "/shippers": returns list of all shippers
   - "/payment-methods": returns list of all payment methods
   - "/categorized-products": returns list of all categorized products;
## Contribution

Contributions to this project are welcome. Feel free to fork the repository, make improvements, and submit pull requests to collaborate with the development efforts.

Thank you for your interest in Utopia!