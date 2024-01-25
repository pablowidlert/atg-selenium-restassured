package pages.components;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class CookiesModal {
    private final WebDriver driver;
    public CookiesModal(WebDriver driver) {
        this.driver = driver;
    }

    // Locators
    private final By cookiesModal = By.id("onetrust-group-container");
    private final By acceptAllCookies = By.id("onetrust-accept-btn-handler");

    public void acceptAllCookies() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        wait.until(ExpectedConditions.visibilityOfElementLocated(cookiesModal));
        driver.findElement(acceptAllCookies).click();
    }
}
