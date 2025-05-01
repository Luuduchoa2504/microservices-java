# Order Management System (Microservices)

This project is a microservices-based application for managing orders, built with Spring Boot. It consists of the following independent services, organized in a monorepo:

- **[Identity Service](services/identity-service)**: Handles user authentication and authorization using JWT.
- **[Product Service](services/product-service)**: Manages product catalog and inventory.
- **[Order Service](services/order-service)**: Processes customer orders and payments.

## Architecture

This project follows a microservices architecture, where each service is:
- Independently deployable (though currently in a monorepo for simplicity)
- Communicates via REST APIs
- Uses its own database (if applicable)

## Getting Started

1. Clone the repository:
   ```bash
   git clone https://github.com/your-username/order-management-system.git