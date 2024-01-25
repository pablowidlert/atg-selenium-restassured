# ATG Code Test - Selenium Web Test

## Table of Contents
- [Project Description](#project-description)
- [Setup The Solution](#setup-the-solution)
- [Built with](#built-with)
- [Project setup](#project-setup)

### Project Description

---
#### This project executes the following:

1. Go to [ATG - Spel på Sport, Häst och Casino](https://www.atg.se/)
2. Select "Häst"
3. Select "V4"
4. Make a new coupon
5. Select horses on coupon:
    1. Mark 4 horses on v4:1
    2. Mark 1 horse on v4:2
    3. Mark 2 horses on v4:3
    4. Mark all horses on v4:4
6. Click “Lägg Spel”

##### Notes:
- Make sure that this test can run everyday
- Use Java, Maven

### Built with

---
- [OpenJDK 21.0.2](https://openjdk.org/projects/jdk/21/)
- [Maven 3.9.6](https://maven.apache.org/download.cgi)
- [Selenium WebDriver](https://www.selenium.dev/documentation/webdriver/getting_started/install_library/)
- [JUnit 5](https://junit.org/junit5/)
- [Allure](https://allurereport.org/docs/junit5/)
- [Lombok](https://projectlombok.org/)

## Setup the solution

---
### Project setup
Download and install [OpenJDK 21](https://openjdk.org/projects/jdk/21/).
Download and install [Apache Maven 3.9.6](https://maven.apache.org/download.cgi).
-   Page objects and components are kept in `/src/main/java/pages/` and `/src/main/java/pages/components` and tests in the `/src/test/java/tests` directory, respectively.
-   We are only testing the "Horse" web page: `https://www.atg.se/`, this is set as the default BASE_URL value in `ATGHomePage.java` from `application.properties`
-   This project implements one page and four components, with one test specification.
-   Tests are executed locally (HEADED) with:
    ```bash
    mvn clean test
    ```
-   Tests are executed locally (HEADLESS) with:
    ```bash
    mvn clean test -Dheadless=true
    ```

## Reporting of the results

The project uses [Allure](https://allurereport.org/docs/) for reporting.

You will find all the configuration in the `allure.properties` and `pom.xml` files:

Once the tests are complete the Allure report can be previewed with:

```bash
mvn allure:serve
```

## Authors

* **Pablo Widlert** - Selenium web tests
