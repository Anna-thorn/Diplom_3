package pages;

import io.qameta.allure.*;
import org.openqa.selenium.*;

/**
 * Страница регистрации нового пользователя
 */
public class RegisterPage extends AbstractPage {
    public RegisterPage(WebDriver driver){
        super(driver);
    }

    // локаторы
    private final By registrationHeader = By.xpath("//h2[text()='Регистрация']");
    private final By nameField = By.xpath("//label[contains(text(), 'Имя')]/following-sibling::input");
    private final By emailField = By.xpath("//label[contains(text(), 'Email')]/following-sibling::input");
    private final By passwordField = By.xpath("//label[contains(text(), 'Пароль')]/following-sibling::input");
    private final By registerButton = By.xpath("//button[text()='Зарегистрироваться']");
    private final By loginLink = By.xpath("//a[text()='Войти']");
    private final By errorMessage = By.cssSelector("p.input__error.text_type_main-default");

    @Step("Проверка открытия страницы 'Регистрация'")
    public boolean isRegisterHeaderDisplayed() {
        return isElementDisplayed(registrationHeader);
    }

    @Step("Заполнение поля 'Имя'")
    public void setName(String name) {
        typeText(nameField, name);

    }
    @Step("Заполнение поля 'Email'")
    public void setEmail(String email) {
        typeText(emailField, email);
    }
    @Step("Заполнение поля 'Пароль'")
    public void setPassword(String password) {
        typeText(passwordField, password);
    }
    @Step("Заполнение всех полей регистрации")
    public void completeRegistrationFields(String name, String email, String password) {
        setName(name);
        setEmail(email);
        setPassword(password);
    }

    @Step("Клик по кнопке 'Зарегистрироваться'")
    public void clickRegisterButton() {
        clickElement(registerButton);
    }

    @Step("Клик по ссылке 'Войти'")
    public void clickLoginLink() {
        clickElement(loginLink);
    }

    @Step("Проверка отображения сообщения 'Некорректный пароль'")
    public boolean isErrorMessageDisplayed() {
        return isElementDisplayed(errorMessage);
    }
}