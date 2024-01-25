package pages.components;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class AuthModal {
    private final WebDriver driver;
    public AuthModal(WebDriver driver) {
        this.driver = driver;
    }

    // Locators
    private final By authModalTitleLocator = By.cssSelector("[data-test-id='auth-modal-title']");
    private final By authModalMobileBankIDLocator = By.id("use-other-device-button");
    private final By authModalBankIDLocator = By.id("use-this-device-button");
    private final By authModalFrejaIDLocator = By.id("use-freja-eid");

    public String getTitle() {
        return driver.findElement(authModalTitleLocator).getText();
    }

    public void selectMobileBankId() {
        driver.switchTo().frame("curity-iframe");
        driver.findElement(authModalMobileBankIDLocator).click();
    }
}
