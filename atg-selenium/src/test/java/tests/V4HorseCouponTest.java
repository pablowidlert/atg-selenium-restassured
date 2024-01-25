package tests;

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
        //Act
        atgHomePage.getSideMenu().locator().click();
        atgHomePage.getSideMenu().selectV4();
        atgHomePage.couponMenuExpand();
        atgHomePage.selectHorsesOnRaceAtRandom(4, 1);
        atgHomePage.selectHorsesOnRaceAtRandom(1, 2);
        atgHomePage.selectHorsesOnRaceAtRandom(2, 3);
        atgHomePage.selectAllHorsesOnRace(4);
        atgHomePage.placeBet();
        //Assert
        assertThat(atgHomePage.getAuthModal().getTitle()).isEqualToIgnoringCase("SKAPA KONTO");
    }
}
