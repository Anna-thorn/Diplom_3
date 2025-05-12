package pages;

import io.qameta.allure.*;
import org.openqa.selenium.*;

/**
 * Страница восстановления пароля
 */
public class PasswordRecoveryPage extends AbstractPage {
    public PasswordRecoveryPage(WebDriver driver){
        super(driver);
    }

    // локаторы
    private final By passwordRecoveryHeader = By.xpath("//h2[text()='Восстановление пароля']");
    private final By loginLink = By.cssSelector("a[href='/login']");

    @Step("Проверка открытия страницы 'Восстановление пароля'")
    public boolean isPasswordRecoveryHeaderDisplayed() {
        return isElementDisplayed(passwordRecoveryHeader);
    }

    @Step("Клик по ссылке 'Войти'")
    public void clickLoginLink() {
        clickElement(loginLink);
    }
}