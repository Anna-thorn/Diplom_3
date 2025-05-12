package pages;

import io.qameta.allure.*;
import org.openqa.selenium.*;

/**
 * Страница входа
 */
public class LoginPage extends AbstractPage {
    public LoginPage(WebDriver driver) {
        super(driver);
    }

    private final By loginHeader = By.xpath("//h2[text()='Вход']");
    private final By emailField = By.xpath("//label[text()='Email']/following-sibling::input");;
    private final By passwordField = By.xpath("//label[text()='Пароль']/following-sibling::input");
    private final By loginButton = By.xpath("//button[text()='Войти']");

    @Step("Проверка открытия страницы входа")
    public boolean isPageOpened() {
        return isElementDisplayed(loginHeader);
    }

    @Step("Заполнение поля 'Email'")
    public void setEmail(String email) {
        typeText(emailField, email);
    }
    @Step("Заполнение поля 'Пароль'")
    public void setPassword(String password) {
        typeText(passwordField, password);
    }
    @Step("Заполнение всех полей входа")
    public void fillLoginForm(String email, String password) {
        setEmail(email);
        setPassword(password);
    }
    @Step("Выполнение входа (email: {email})")
    public void login(String email, String password) {
        fillLoginForm(email, password);
        clickLoginButton();
    }

    @Step("Клик по кнопке 'Войти'")
    public void clickLoginButton() {
        clickElement(loginButton);
    }
}