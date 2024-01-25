package pages.components;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class SideMenu {

    private final WebDriver driver;
    public SideMenu(WebDriver driver) {
        this.driver = driver;
    }

    private final By sideMenuLocator = By.cssSelector("[data-test-id='overlay-side-menu']");
    private final By horseMenuLocator = By.cssSelector("[data-test-id='horse-verticals-menu']");
    private final By allGamesMenuLocator = By.cssSelector("[data-test-id='horse-left-menu-sub-menu-toggle-allaspel-new']");
    private final By v4MenuLocator = By.cssSelector("[data-test-id='horse-left-menu-sub-menu-item-v4']");

    public WebElement locator() {
        return driver.findElement(sideMenuLocator);
    }

    public void allGames() {
        driver.findElement(allGamesMenuLocator).click();
    }

    public void selectV4() {
        allGames();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        wait.until(ExpectedConditions.visibilityOfElementLocated(v4MenuLocator));
        new Actions(driver)
                .scrollToElement(driver.findElement(v4MenuLocator))
                .perform();
        driver.findElement(v4MenuLocator).click();
    }
}
