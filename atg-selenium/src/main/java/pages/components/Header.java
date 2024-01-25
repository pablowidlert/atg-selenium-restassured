package pages.components;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class Header {

    private final WebDriver driver;

    public Header(WebDriver driver) {
        this.driver = driver;
    }
    private final By horseTab = By.cssSelector("data-test-id='header-verticallink-horse'");

    public void getHorseTab() {
        driver.findElement(horseTab).click();
    }
}
