package pages;

import lombok.Getter;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import pages.components.AuthModal;
import pages.components.CookiesModal;
import pages.components.Header;
import pages.components.SideMenu;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

import java.time.Duration;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ATGHomePage {

    private final WebDriver driver;

    @Getter
    private final AuthModal authModal;
    @Getter
    private final CookiesModal cookiesModal;
    @Getter
    private final Header header;
    @Getter
    private final SideMenu sideMenu;

    public ATGHomePage(WebDriver driver){
        this.driver = driver;
        this.driver.manage().timeouts().implicitlyWait(Duration.ofMillis(30000));
        authModal = new AuthModal(this.driver);
        cookiesModal = new CookiesModal(this.driver);
        header = new Header(this.driver);
        sideMenu = new SideMenu(this.driver);
    }

    Config config = ConfigFactory.load();
    // Locators
    private final String couponRaceTemplate = "[data-test-id='coupon-race-RACEID'] [data-test-selected='false'][data-test-scratched='false']:enabled";
    private final String couponRaceTemplateAllButton = "[data-test-id='leg-RACEID-toggle-all']";
    private final By couponViewExpand = By.cssSelector("[data-test-id='expand-coupon']");
    private final By placeBetButtonLocator = By.cssSelector(".MuiButtonBase-root.MuiButton-root.MuiButton-contained.MuiButton-containedSuccess.MuiButton-sizeLarge");

    public void visit() {
        driver.get(config.getString("base.uri"));
    }

    public void couponMenuExpand(){
        driver.findElement(couponViewExpand).click();
    }

    public void selectHorsesOnRaceAtRandom(int numberOfHorses, int raceNumber){
        // Query how many valid horses are available
        List<WebElement> horses = driver.findElements(By.cssSelector(couponRaceTemplate.replace("RACEID",String.valueOf(raceNumber))));
        int count = horses.size();
        // Initialize array with the total amount and shuffle them to randomize picking order
        Integer[] arr = new Integer[count];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = i;
        }
        Collections.shuffle(Arrays.asList(arr));
        // For the specified number of horses select them from the coupon
        for (int i = 0; i < numberOfHorses; i++) {
            horses.get(arr[i]).click();
        }
    }

    public void selectAllHorsesOnRace(int raceNumber){
        WebElement allHorses = driver.findElement(By.cssSelector(couponRaceTemplateAllButton.replace("RACEID", String.valueOf(raceNumber))));
        allHorses.click();
    }

    public void placeBet() {
        driver.findElement(placeBetButtonLocator).click();
    }
}
