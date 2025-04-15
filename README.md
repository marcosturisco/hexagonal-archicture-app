This project demonstrates the implementation of a system using various technologies to efficiently deliver features to the user while following best software development practices.
The application was developed over the following technologies and features:

🚀 Technologies & Features:

# Spring Boot for building the backend application.
# Spring Security for user authentication and authorization.
# Spring MVC for developing RESTful APIs.
# Spring HATEOAS implemented to achieve Level 3 of the REST Maturity Model, enabling hypermedia-driven navigation between resources.

# New API Endpoints:
# DELETE endpoint for removing animals.
# Paginated listing with support for dynamic sorting.
# Custom exception handling, such as a specific error response for failed animal creation.

# BDD testing using the Cucumber Framework, with a refined suite of behavioral test scenarios.
# Swagger (Springdoc OpenAPI) for API documentation and testing.
# Spring Boot Actuator for application monitoring and health checks.
# Nginx as a reverse proxy, providing load balancing and added security.
# MySQL as a relational database for persistent data storage.
# Hexagonal Architecture (Ports and Adapters) combined with DDD principles to ensure modularity, flexibility, and scalability.
# iReports API for exporting database data into customized reports.
# Integration with OpenAI API to retrieve animal species names based on descriptive input.
# Maven for project build and dependency management.
# Docker for containerizing the solution, ensuring portability and ease of deployment.
# .env file support, allowing secure and flexible environment variable configuration for Docker environments.

This project serves as a practical guide for integrating these technologies in a real-world development environment.

$ docker build -t dockermactur/spring:zoo . ; docker build -t dockermactur/nginx:zoo nginx/.
$ docker compose --env-file .env up --build
# To generate de keystore file (Execute replacing the need parameters and execute on bash terminal)
$ keytool -genkeypair -alias your-key-alias \
$ -keyalg RSA -keysize 2048 \
$ -storetype PKCS12 \
$ -keystore keystore.p12 \
$ -validity 3650 \
$ -storepass your-password

# To generate the .crt and .key file for access HTTP2 SSL Protocol Test
$ openssl pkcs12 -in keystore.p12 -clcerts -nokeys -out certificate.crt
$ openssl pkcs12 -in keystore.p12 -nocerts -nodes -out private.key