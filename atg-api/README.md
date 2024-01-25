# ATG Code Test - API Test

## Table of Contents
- [Project Description](#project-description)
- [Built with](#built-with)
- [Setup The Solution](#setup-the-solution)
- [Reporting of the results](#reporting-of-the-results)
- [Authors](#authors)

### Project Description

---
#### This project executes the following:

- Tests against the "Pet" endpoints on [Swagger Petstore](https://petstore.swagger.io/#/) API

##### Notes:
- Use Java, Maven

### Built with

---
- [OpenJDK 21.0.2](https://openjdk.org/projects/jdk/21/)
- [Maven 3.9.6](https://maven.apache.org/download.cgi)
- [JUnit 5](https://junit.org/junit5/)
- [Rest-Assured](https://rest-assured.io/)
- [Allure](https://allurereport.org/docs/junit5/)
- [Lombok](https://projectlombok.org/)

### Setup the solution

---
Download and install [OpenJDK 21](https://openjdk.org/projects/jdk/21/)

Download and install [Apache Maven 3.9.6](https://maven.apache.org/download.cgi).
-   Controller and data models are kept in `/src/main/java/controllers/` and `/src/main/java/models` and 
-   Tests are in the `/src/test/java/petstore/tests` directory.
-   We are only testing the "Pet" endpoint on the [Swagger Petstore](https://petstore.swagger.io/#/), this is set as the default BASE_URL value in `BaseTest.java` from `application.properties`
-   This project implements one page and four components, with one test specification.
-   Tests are executed locally with:
    ```bash
    mvn test
    ```

### Reporting of the results

---
The project uses [Allure](https://allurereport.org/docs/) for reporting.

You will find all the configuration in the `allure.properties` and `pom.xml` files:

Once the tests are complete the Allure report can be previewed with:

```bash
mvn allure:serve
```

### Authors

---
* **Pablo Widlert** -  Rest-Assured API tests
