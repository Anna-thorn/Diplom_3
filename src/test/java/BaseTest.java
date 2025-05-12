import org.openqa.selenium.*;

/**
 * Общие настройки для тестовых классов + URL
 */
public class BaseTest {
    protected WebDriver driver;

    /** Базовый URL приложения */
    protected static final String BASE_URL = "https://stellarburgers.nomoreparties.site";
    protected static final String LOGIN_PAGE_URL = BASE_URL + "/login";
    protected static final String PASSWORD_RECOVERY_URL = BASE_URL + "/forgot-password";
    protected static final String PERSONAL_ACCOUNT_URL = BASE_URL + "/account/profile";
    protected static final String REGISTER_PAGE_URL = BASE_URL + "/register";
    protected static final int DEFAULT_TIMEOUT = 5;
}