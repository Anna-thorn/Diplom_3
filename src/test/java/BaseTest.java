import org.junit.*;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.WebDriverWait;

import pages.*;
import utils.BrowserUtils;
import utils.NavigationHelper;
import api.UserApi;

/**
 * Общие настройки для тестовых классов
 */
public class BaseTest {
    protected WebDriver driver;
    protected WebDriverWait wait;
    protected NavigationHelper navigation;

    protected MainPage mainPage;
    protected LoginPage loginPage;
    protected RegisterPage registerPage;
    protected PasswordRecoveryPage passwordRecoveryPage;
    protected PersonalAccountPage personalAccountPage;
    protected UserApi userApi;

    @Before
    public void setUp() {
        driver = BrowserUtils.createWebDriver();
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, 10);

        navigation = new NavigationHelper(driver);

        mainPage = new MainPage(driver);
        loginPage = new LoginPage(driver);
        registerPage = new RegisterPage(driver);
        passwordRecoveryPage = new PasswordRecoveryPage(driver);
        personalAccountPage = new PersonalAccountPage(driver);
        userApi = new UserApi();
    }

    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}