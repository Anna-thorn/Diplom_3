package pages;

import io.qameta.allure.*;
import org.openqa.selenium.*;

/**
 * Страница личного кабинета
 */
public class PersonalAccountPage extends AbstractPage {
    public PersonalAccountPage(WebDriver driver){
        super(driver);
    }

    // локаторы
    private final By profileLink = By.cssSelector("li.Account_listItem__35dAP a[href='/account/profile']");
    private final By exitButton = By.cssSelector("button.Account_button__14Yp3");

    @Step("Проверка открытия профиля пользователя")
    public boolean isProfileHeaderDisplayed() {
        return isElementDisplayed(profileLink);
    }

    @Step("Клик по кнопке 'Выход'")
    public void clickExitButton() {
        clickElement(exitButton);
    }
}