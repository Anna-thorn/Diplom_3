import io.qameta.allure.junit4.*;
import org.junit.*;
import org.openqa.selenium.support.ui.*;
import static org.junit.Assert.*;

import java.util.function.*;

import pages.*;
import utils.UserGenerator;


/**
 * Проверка страницы Входа
 * Проверка входа:
 * вход по кнопке «Войти в аккаунт» на главной,
 * вход через кнопку «Личный кабинет»,
 * вход через кнопку в форме регистрации,
 * вход через кнопку в форме восстановления пароля.
 * Выход из аккаунта
 */
public class LoginTest extends ParameterizedWebTest {
    private WebDriverWait wait;
    private LoginPage loginPage;
    private MainPage mainPage;
    private RegisterPage registerPage;
    private PasswordRecoveryPage passwordRecoveryPage;
    private PersonalAccountPage personalAccountPage;

    public LoginTest(String browser) {
        super(browser);
    }

    @Before
    public void setUp() {
        wait = new WebDriverWait(driver, DEFAULT_TIMEOUT);
        loginPage = new LoginPage(driver);
        mainPage = new MainPage(driver);
        registerPage = new RegisterPage(driver);
        passwordRecoveryPage = new PasswordRecoveryPage(driver);
        personalAccountPage = new PersonalAccountPage(driver);
    }

    private void waitForUrl(String expectedUrl) {
        wait.until(ExpectedConditions.urlToBe(expectedUrl));
    }

    private void verifyPageDisplayed(Supplier<Boolean> pageCheck, String errorMessage) {
        assertTrue(errorMessage, pageCheck.get());
    }

    private void navigateAndVerifyLoginPage(Runnable navigationAction) {
        navigationAction.run();
        waitForUrl(LOGIN_PAGE_URL);
        assertTrue("Страница входа не открылась", loginPage.isPageOpened());
    }

    @Test
    @DisplayName("Вход по кнопке 'Войти в аккаунт' с главной страницы")
    public void loginViaMainPageButton() {
        openUrl(BASE_URL);
        verifyPageDisplayed(mainPage::isConstructorHeaderDisplayed,
                "Главная страница не открылась");
        navigateAndVerifyLoginPage(mainPage::clickLoginButton);
    }

    @Test
    @DisplayName("Вход по ссылке 'Личный кабинет' с главной страницы (неавторизованным пользователем)")
    public void loginViaPersonalAccountLink() {
        openUrl(BASE_URL);
        verifyPageDisplayed(mainPage::isConstructorHeaderDisplayed,
                "Главная страница не открылась");
        navigateAndVerifyLoginPage(() -> mainPage.clickPersonalAccount());
    }

    @Test
    @DisplayName("Вход по ссылке 'Войти' со страницы регистрации")
    public void loginViaRegistrationPageLink() {
        openUrl(REGISTER_PAGE_URL);
        verifyPageDisplayed(registerPage::isRegisterHeaderDisplayed,
                "Страница регистрации не открылась");
        navigateAndVerifyLoginPage(registerPage::clickLoginLink);
    }

    @Test
    @DisplayName("Вход по ссылке 'Войти' со страницы восстановления пароля")
    public void loginViaPasswordRecoveryPageLink() {
        openUrl(PASSWORD_RECOVERY_URL);
        verifyPageDisplayed(passwordRecoveryPage::isPasswordRecoveryHeaderDisplayed,
                "Страница восстановления пароля не открылась");
        navigateAndVerifyLoginPage(passwordRecoveryPage::clickLoginLink);
    }

    @Test
    @DisplayName("Проверка выхода из 'Личного кабинета'")
    public void logoutFromAccount() {
        UserGenerator user = UserGenerator.getRandomValidUser().registerViaApi();
        try {
            openUrl(LOGIN_PAGE_URL);
            loginPage.login(user.getEmail(), user.getPassword());

            wait.until(d -> mainPage.isConstructorHeaderDisplayed());
            mainPage.clickPersonalAccount();

            waitForUrl(PERSONAL_ACCOUNT_URL);
            verifyPageDisplayed(personalAccountPage::isProfileHeaderDisplayed,
                    "Страница профиля не открылась");

            personalAccountPage.clickExitButton();
            waitForUrl(LOGIN_PAGE_URL);
            verifyPageDisplayed(loginPage::isPageOpened,
                    "Не вернулись на страницу входа после выхода");
        } finally {
            try {
                user.deleteViaApi();
            } catch (Exception e) {
                System.err.println("Ошибка при удалении пользователя: " + e.getMessage());
            }
        }
    }
}