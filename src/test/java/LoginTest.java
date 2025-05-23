import io.qameta.allure.*;
import io.qameta.allure.junit4.*;
import org.junit.*;
import static org.junit.Assert.*;

import utils.UserGenerator;
import model.User;

/**
 * Проверка страницы Входа
 * Проверка входа:
 * вход по кнопке «Войти в аккаунт» на главной,
 * вход через кнопку «Личный кабинет»,
 * вход через кнопку в форме регистрации,
 * вход через кнопку в форме восстановления пароля.
 * Выход из аккаунта
 */
public class LoginTest extends BaseTest {

    private void verifyLoginPageOpened() {
        assertTrue("Страница входа не открылась", loginPage.isPageOpened());
    }

    @Test
    @DisplayName("Вход по кнопке 'Войти в аккаунт' с главной страницы")
    @Description("Проверка перехода на страницу входа при клике по кнопке 'Войти в аккаунт' на главной странице")
    public void loginViaMainPageButton() {
        navigation.openMainPage();
        mainPage.clickLoginButton();
        verifyLoginPageOpened();
    }

    @Test
    @DisplayName("Вход по ссылке 'Личный кабинет' с главной страницы (неавторизованным пользователем)")
    @Description("Проверка перехода на страницу входа при клике по ссылке 'Личный кабинет' без авторизации")
    public void loginViaPersonalAccountLink() {
        navigation.openMainPage();
        mainPage.clickPersonalAccount();
        verifyLoginPageOpened();
    }

    @Test
    @DisplayName("Вход по ссылке 'Войти' со страницы регистрации")
    @Description("Проверка перехода на страницу входа при клике по ссылке 'Войти' со страницы регистрации")
    public void loginViaRegistrationPageLink() {
        navigation.openRegisterPage();
        registerPage.clickLoginLink();
        verifyLoginPageOpened();
    }

    @Test
    @DisplayName("Вход по ссылке 'Войти' со страницы восстановления пароля")
    @Description("Проверка перехода на страницу входа при клике по ссылке 'Войти' со страницы восстановления пароля")
    public void loginViaPasswordRecoveryPageLink() {
        navigation.openPasswordRecoveryPage();
        passwordRecoveryPage.clickLoginLink();
        verifyLoginPageOpened();
    }

    @Test
    @DisplayName("Проверка выхода из 'Личного кабинета'")
    @Description("Проверка перехода на страницу входа при клике кнопки 'Выход' в личном кабинете")
    public void logoutFromAccount() {
        User registeredUser = userApi.registerAndGetUser(UserGenerator.createValidUser());
        try {
            navigation.openLoginPage();
            loginPage.login(registeredUser.getEmail(), registeredUser.getPassword());
            mainPage.clickPersonalAccount();
            personalAccountPage.clickExitButton();
            verifyLoginPageOpened();
        } finally {
            userApi.deleteUser(registeredUser);
        }
    }
}