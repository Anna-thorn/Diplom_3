import io.qameta.allure.*;
import io.qameta.allure.junit4.*;
import org.junit.*;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.*;

import static org.junit.Assert.*;

import utils.UserGenerator;
import pages.RegisterPage;

/**
 * Проверка Регистрации
 * Требования: минимальный пароль — шесть символов
 * Проверь:
 * успешную регистрацию
 * ошибку для некорректного пароля
 */
public class RegistrationTest extends ParameterizedWebTest {
    private RegisterPage registerPage;
    private UserGenerator registeredUser;

    public RegistrationTest(String browser) {
        super(browser);
    }

    @Before
    public void setup() {
        driver.get(REGISTER_PAGE_URL);
        registerPage = new RegisterPage(driver);
    }

    private void performRegistration(UserGenerator user) {
        registerPage.completeRegistrationFields(user.getName(), user.getEmail(), user.getPassword());
        registerPage.clickRegisterButton();

        if (driver.getCurrentUrl().equals(LOGIN_PAGE_URL)) {
            this.registeredUser = user;
            registeredUser.loginViaApi();
        }
    }

    private void assertSuccessfulRegistration() {
        try {
            new WebDriverWait(driver, 3)
                    .withMessage("Не произошел переход на страницу входа после успешной регистрации")
                    .until(ExpectedConditions.urlToBe(LOGIN_PAGE_URL));
        } catch (TimeoutException e) {
            if (driver.getCurrentUrl().equals(REGISTER_PAGE_URL) &&
                    registerPage.isErrorMessageDisplayed()) {
                fail("Появилось сообщение об ошибке при валидной регистрации");
            }
            throw e;
        }
    }

    private void assertFailedRegistration() {
        assertTrue("Должно отображаться сообщение об ошибке при невалидном пароле",
                new WebDriverWait(driver, DEFAULT_TIMEOUT)
                        .until(d -> registerPage.isErrorMessageDisplayed()));

        assertEquals("URL должен остаться на странице регистрации при ошибке",
                REGISTER_PAGE_URL, driver.getCurrentUrl());
    }

    @Test
    @DisplayName("Проверка успешной регистрации")
    public void testSuccessfulRegistration() {
        UserGenerator user = UserGenerator.getRandomValidUser();
        performRegistration(user);
        assertSuccessfulRegistration();

        if (driver.getCurrentUrl().equals(LOGIN_PAGE_URL)) {
            user.loginViaApi().deleteViaApi();
        }
    }

    @Test
    @DisplayName("Появление ошибки при вводе пароля длиной меньше 6 символов")
    @Description("Ввод пароля длиной 5 символов")
    public void testRegistrationWithShortPassword() {
        performRegistration(UserGenerator.getUserWithShortPassword());
        assertFailedRegistration();
    }

    @Test
    @DisplayName("Появление ошибки при попытке регистрации с отсутствующим паролем")
    public void testRegistrationWithEmptyPassword() {
        performRegistration(UserGenerator.getUserWithEmptyPassword());
        assertFailedRegistration();
    }
}