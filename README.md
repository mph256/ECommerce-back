# ECommerce-back

A simple C2C e-commerce site.

## Description

This site allows you to buy and sell products.  
You can also post product reviews and track your orders on it.

## Build With
* Angular
* SASS
* Spring Boot/Data JPA/Validation/Security
* Swagger
* Log4j2
* Lombok
* JPL
* JPQL
* PostgreSQL
* Maven
* Tomcat

## Getting Started

### Dependencies

* Angular 16.2
* PostgreSQL 16
* JDK 17.0.6
* Maven 3.9.1

### Installing

1. Clone the repositories on your machine:
   * ```git clone https://github.com/mph256/ECommerce-back.git```
   * ```git clone https://github.com/mph256/ECommerce-front.git```
2. From the backend directory, connect to PostgreSQL (with ```psql```) and run the following command: ```\i ./scripts/schema.sql```
3. Update the database information in the application module resources application.properties file if necessary (your username and password).

You can also import test data with the command: ```\i ./scripts/data.sql```

### Executing

1. Start the backend using the following command: ```mvn spring-boot:run```
1. Start the frontend with: ```ng serve```
2. The application is accessible at the following url: http://localhost:4200/

## Authors

[mph256](https://github.com/mph256)

## Version History

* 0.1
    * Initial Release

## License

This project is licensed under the MIT License - see the LICENSE.md file for details