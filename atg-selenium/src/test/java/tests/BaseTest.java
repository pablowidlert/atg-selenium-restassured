package tests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import io.qameta.allure.Allure;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import pages.ATGHomePage;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ExtendWith(FailureScreenshotExtension.class)
public class BaseTest {
    ATGHomePage atgHomePage;
    ChromeOptions options = new ChromeOptions();
    WebDriver driver;

    private boolean failureScreenshotCaptured;

    @BeforeEach
    void setup() {
        //Arrange
        boolean isHeadless = Boolean.valueOf(System.getProperty("headless"));
        if (isHeadless) {
            System.out.println("Using headless Chrome");
            options.addArguments("--headless=new");
            options.addArguments("--no-sandbox");
            options.addArguments("--disable-dev-shm-usage");
            options.addArguments("--disable-gpu");
        }

        driver = new ChromeDriver(options);
        atgHomePage = new ATGHomePage(driver);
        atgHomePage.visit();
        atgHomePage.getCookiesModal().acceptAllCookies();
    }

    @AfterEach
    void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    void captureFailureScreenshot(String testId, Throwable failure) {
        if (driver == null || failureScreenshotCaptured || !(driver instanceof TakesScreenshot screenshotDriver)) {
            return;
        }

        try {
            byte[] screenshot = screenshotDriver.getScreenshotAs(OutputType.BYTES);
            Path screenshotPath = Path.of(
                    System.getProperty("failureScreenshotDirectory", "target/failure-screenshots"),
                    testId.replaceAll("[^A-Za-z0-9._-]", "_") + ".png");
            Files.createDirectories(screenshotPath.getParent());
            Files.write(screenshotPath, screenshot);
            Allure.addAttachment("Failure screenshot", "image/png",
                    new ByteArrayInputStream(screenshot), ".png");
            failureScreenshotCaptured = true;
            System.out.println("Saved failure screenshot to " + screenshotPath);
        } catch (IOException | RuntimeException screenshotFailure) {
            failure.addSuppressed(screenshotFailure);
            System.err.println("Unable to capture failure screenshot: " + screenshotFailure.getMessage());
        }
    }
}
