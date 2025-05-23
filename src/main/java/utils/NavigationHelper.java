package utils;

import io.qameta.allure.*;
import org.openqa.selenium.WebDriver;
import pages.*;

public class NavigationHelper {
    private final WebDriver driver;

    public NavigationHelper(WebDriver driver) {
        this.driver = driver;
    }

    @Step("Открыть главную страницу")
    public MainPage openMainPage() {
        driver.get(Urls.BASE_URL);
        return new MainPage(driver);
    }

    @Step("Открытие страницы 'Регистрация'")
    public RegisterPage openRegisterPage() {
        driver.get(Urls.REGISTER_PAGE_URL);
        return new RegisterPage(driver);
    }

    @Step("Открыть страницу 'Вход'")
    public LoginPage openLoginPage() {
        driver.get(Urls.LOGIN_PAGE_URL);
        return new LoginPage(driver);
    }

    @Step("Открытие страницы 'Восстановление пароля'")
    public PasswordRecoveryPage openPasswordRecoveryPage() {
        driver.get(Urls.PASSWORD_RECOVERY_URL);
        return new PasswordRecoveryPage(driver);
    }
}