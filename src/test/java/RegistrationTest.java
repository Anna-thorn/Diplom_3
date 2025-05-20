import io.qameta.allure.*;
import io.qameta.allure.junit4.*;
import io.restassured.response.Response;
import org.junit.*;
import static org.junit.Assert.*;

import utils.UserGenerator;
import model.User;

/**
 * Проверка Регистрации
 * Требования: минимальный пароль — шесть символов
 * Проверь:
 * успешную регистрацию
 * ошибку для некорректного пароля
 */
public class RegistrationTest extends BaseTest {

    private void performRegistration(User user) {
        navigation.openRegisterPage();
        registerPage.completeRegistrationFields(
                user.getName(),
                user.getEmail(),
                user.getPassword());
        registerPage.clickRegisterButton();
    }

    private void assertRegistrationSuccess() {
        assertTrue("Не открылась страница входа после регистрации",
                loginPage.isPageOpened());
    }

    private void assertRegistrationError() {
        assertTrue("Сообщение об ошибке не отобразилось",
                registerPage.isErrorMessageDisplayed());
    }

    @Test
    @DisplayName("Проверка успешной регистрации")
    @Description("Проверка что после заполнения валидных данных и нажатия кнопки регистрации происходит переход на страницу входа")
    public void testSuccessfulRegistration() {
        User user = UserGenerator.createValidUser();
        try {
            performRegistration(user);
            assertRegistrationSuccess();
        } finally {
            try {
                Response loginResponse = userApi.loginUser(user);
                if (loginResponse.statusCode() == 200) {
                    user.setAccessToken(userApi.extractAccessToken(loginResponse));
                    userApi.deleteUser(user);
                }
            } catch (Exception e) {
                // Логирование ошибки при очистке
                System.out.println("Ошибка при очистке тестовых данных: " + e.getMessage());
            }
        }
    }

    @Test
    @DisplayName("Появление ошибки при вводе пароля длиной меньше 6 символов")
    @Description("Проверка отображения сообщения об ошибке при попытке регистрации с паролем длиной 5 символов")
    public void testRegistrationWithShortPassword() {
        User user = UserGenerator.createUserWithShortPassword();
        performRegistration(user);
        assertRegistrationError();
    }

    @Test
    @DisplayName("Появление ошибки при попытке регистрации с отсутствующим паролем")
    @Description("Проверка отображения ошибки при попытке регистрации с пустым паролем")
    public void testRegistrationWithEmptyPassword() {
        User user = UserGenerator.createUserWithEmptyPassword();
        performRegistration(user);
        assertRegistrationError();
    }
}