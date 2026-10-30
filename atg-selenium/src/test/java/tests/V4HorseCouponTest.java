package tests;

import io.qameta.allure.Allure;
import io.qameta.allure.Feature;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@Tag("fast")
@Tag("V4")
@DisplayName("e2e Web Tests")
@Feature("Betting on V4 Races")
public class V4HorseCouponTest extends BaseTest {
    @Test
    void placeBetOnV4Race() {
        Allure.step("Open the V4 betting coupon", () -> {
            atgHomePage.getSideMenu().locator().click();
            atgHomePage.getSideMenu().selectV4();
            atgHomePage.couponMenuExpand();
        });
        Allure.step("Select horses for races 1 to 3", () -> {
            atgHomePage.selectHorsesOnRaceAtRandom(4, 1);
            atgHomePage.selectHorsesOnRaceAtRandom(1, 2);
            atgHomePage.selectHorsesOnRaceAtRandom(2, 3);
        });
        Allure.step("Select all horses in race 4", () -> atgHomePage.selectAllHorsesOnRace(4));
        Allure.step("Place the bet", () -> atgHomePage.placeBet());
        Allure.step("Verify sign-in is required", () ->
                assertThat(atgHomePage.getPrivatePurchaseOverview().getSignInButton().getText())
                        .isEqualToIgnoringCase("Logga in"));
    }
}
