# Patient Management System (PMS)

This project is a microservices-based Patient Management System (PMS). It provides a modular and scalable architecture for managing patient information, billing, authentication, and analytics.

## Services

The project is composed of the following microservices:

| Service             | Description                                                                                                                              |
| ------------------- | ---------------------------------------------------------------------------------------------------------------------------------------- |
| `api-gateway`       | A Spring Cloud Gateway that acts as a single entry point for all client requests, routing them to the appropriate backend service.         |
| `auth-service`      | Handles user authentication and authorization using JWT. It manages user credentials and roles.                                          |
| `patient-service`   | Manages patient data, including personal information and medical records. It communicates with other services using gRPC and Kafka.        |
| `billing-service`   | Manages billing and payments for patient services. It uses gRPC for communication.                                                       |
| `analytics-service` | Consumes events from other services via Kafka to provide data for analytics and reporting.                                               |
| `infrastructure`    | Contains AWS CDK code for deploying the application's infrastructure.                                                                    |
| `integration-tests` | Contains integration tests that verify the interactions between the different microservices.                                             |

## Technologies

- **Backend:** Java 21, Spring Boot 3
- **API Gateway:** Spring Cloud Gateway
- **Authentication:** Spring Security, JWT
- **Database:** PostgreSQL, H2 (for testing)
- **Communication:** REST, gRPC, Kafka
- **Infrastructure as Code:** AWS CDK
- **Build Tool:** Maven
- **Testing:** JUnit, REST Assured

## Prerequisites

- Java 21
- Maven 3.x
- Docker

## Build

To build all the services, run the following command from the root directory:

```bash
mvn clean install
```

## Running the Application

Each service can be run individually using the Spring Boot Maven plugin. For example, to run the `patient-service`:

```bash
cd patient-service
mvn spring-boot:run
```

## Testing

To run the integration tests, navigate to the `integration-tests` directory and run:

```bash
cd integration-tests
mvn test
```
