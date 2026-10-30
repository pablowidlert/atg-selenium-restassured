package pages.components;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class PrivatePurchaseOverview {
    private final WebDriver driver;
    public PrivatePurchaseOverview(WebDriver driver) {
        this.driver = driver;
    }

    // Locators
    private final By signInButtonLocator = By.cssSelector("[data-test-id='login-button']");

    public WebElement getSignInButton() {
        return driver.findElement(signInButtonLocator);
    }
}
