package tests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import pages.ATGHomePage;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BaseTest {
    ATGHomePage atgHomePage;
    ChromeOptions options = new ChromeOptions();
    WebDriver driver;

    @BeforeEach
    void setup() {
        //Arrange
        boolean isHeadless = Boolean.valueOf(System.getProperty("headless"));
        if(isHeadless) {
            System.out.println("Using headless Chrome");
            options.addArguments("--headless=new");
        }
        driver = new ChromeDriver(options);
        atgHomePage = new ATGHomePage(driver);
        atgHomePage.visit();
        atgHomePage.getCookiesModal().acceptAllCookies();
    }

    @AfterEach
    void tearDown() {
        driver.quit();
    }
}
