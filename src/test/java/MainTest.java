import io.qameta.allure.junit4.*;
import org.junit.*;
import org.openqa.selenium.support.ui.*;
import static org.junit.Assert.*;

import pages.LoginPage;
import pages.MainPage;
import utils.UserGenerator;
import pages.PersonalAccountPage;

/**
 * Проверка главной страницы и конструктора
 * Проверка:
 * переход по клику на «Личный кабинет»
 * переход по клику на «Конструктор» и на логотип Stellar Burgers
 * работу переходов к разделам: «Булки», «Соусы», «Начинки».
 */
public class MainTest extends ParameterizedWebTest {
    private MainPage mainPage;
    private LoginPage loginPage;
    private PersonalAccountPage personalAccountPage;

    public MainTest(String browser) {
        super(browser);
    }

    @Before
    public void setUp() {
        mainPage = new MainPage(driver);
        loginPage = new LoginPage(driver);
        personalAccountPage = new PersonalAccountPage(driver);
    }

    @Test
    @DisplayName("Переход по клику на ссылку 'Личный кабинет' (неавторизованным пользователем)")
    public void testClickPersonalAccountUnregisteredUser() {
        openUrl(BaseTest.BASE_URL);
        mainPage.clickPersonalAccount();
        assertTrue("Не произошел переход на страницу входа",
                loginPage.isPageOpened());
    }

    @Test
    @DisplayName("Переход по клику на ссылку 'Личный кабинет' (авторизованным пользователем)")
    public void testClickPersonalAccountForLoggedUser() {
        UserGenerator user = UserGenerator.getRandomValidUser().registerViaApi();
        try {
            openUrl(BaseTest.LOGIN_PAGE_URL);
            loginPage.fillLoginForm(user.getEmail(), user.getPassword());
            loginPage.clickLoginButton();
            new WebDriverWait(driver, 5)
                    .until(ExpectedConditions.urlToBe(BaseTest.BASE_URL + "/"));
            assertTrue("Главная страница не открылась", mainPage.isConstructorHeaderDisplayed());

            mainPage.clickPersonalAccount();
            new WebDriverWait(driver, 5)
                    .until(ExpectedConditions.urlToBe(BaseTest.PERSONAL_ACCOUNT_URL));
            assertTrue("Страница профиля не открылась",
                    personalAccountPage.isProfileHeaderDisplayed());
        } finally {
            try {
                user.deleteViaApi();
            } catch (Exception e) {
                System.err.println("Ошибка при удалении пользователя: " + e.getMessage());
            }
        }
    }

    @Test
    @DisplayName("Переход по клику на ссылку 'Конструктор'")
    public void testClickConstructorLink() {
        openUrl(BaseTest.LOGIN_PAGE_URL);
        assertTrue("Страница входа не открылась",
                loginPage.isPageOpened());

        mainPage.clickConstructorLink();
        new WebDriverWait(driver, 5)
                .until(ExpectedConditions.urlToBe(BaseTest.BASE_URL + "/"));
        assertTrue("Главная страница не открылась",
                mainPage.isConstructorHeaderDisplayed());
    }

    @Test
    @DisplayName("Переход по клику на логотип 'Stellar Burgers'")
    public void testClickLogoLink() {
        openUrl(BaseTest.LOGIN_PAGE_URL);
        assertTrue("Страница входа не открылась",
                loginPage.isPageOpened());

        mainPage.clickLogo();
        new WebDriverWait(driver, 5)
                .until(ExpectedConditions.urlToBe(BaseTest.BASE_URL + "/"));
        assertTrue("Главная страница не открылась",
                mainPage.isConstructorHeaderDisplayed());
    }

    @Test
    @DisplayName("Переход в раздел 'Булки'") // вариант 1 - проверка текста активной вкладки
    public void testTransitionSectionBuns() {
        openUrl(BaseTest.BASE_URL);
        assertTrue("Главная страница не открылась",
                mainPage.isConstructorHeaderDisplayed());
        assertEquals("По умолчанию должна быть активна вкладка 'Булки'",
                "Булки", mainPage.getActiveTabText());

        mainPage.clickSaucesTab();
        new WebDriverWait(driver, 3)
                .until((ExpectedCondition<Boolean>) driver -> "Соусы".equals(mainPage.getActiveTabText()));
        assertTrue("Не удалось перейти в раздел 'Соусы'",
                mainPage.isSaucesHeaderDisplayed());

        mainPage.clickBunsTab();
        new WebDriverWait(driver, 3)
                .until((ExpectedCondition<Boolean>) driver -> "Булки".equals(mainPage.getActiveTabText()));
        assertTrue("Раздел 'Булки' не отобразился после перехода",
                mainPage.isBunsHeaderDisplayed());
    }

    @Test
    @DisplayName("Переход в раздел 'Соусы'") // вариант 2 - проверка заголовка раздела
    public void testTransitionSectionSauces() {
        openUrl(BaseTest.BASE_URL);
        assertTrue("Главная страница не открылась",
                mainPage.isConstructorHeaderDisplayed());
        assertTrue("Раздел 'Булки' не активен по умолчанию",
                mainPage.isBunsHeaderDisplayed());

        mainPage.clickSaucesTab();
        new WebDriverWait(driver, 3)
                .until(ExpectedConditions.visibilityOfElementLocated(
                        mainPage.getSaucesHeaderLocator()));
        assertTrue("Раздел 'Соусы' не отобразился после перехода",
                mainPage.isSaucesHeaderDisplayed());
    }

    @Test
    @DisplayName("Переход в раздел 'Начинки'") // вариант 3 - проверка класса активной вкладки
    public void testTransitionSectionFillings() {
        openUrl(BaseTest.BASE_URL);
        assertTrue("Главная страница не открылась",
                mainPage.isConstructorHeaderDisplayed());
        assertTrue("Раздел 'Булки' не активен по умолчанию",
                mainPage.isTabActive(mainPage.getTabLocator("Булки")));

        mainPage.clickFillingsTab();
        new WebDriverWait(driver, 3)
                .until(ExpectedConditions.attributeContains(
                        mainPage.getTabLocator("Начинки"), "class", "current"));
        assertTrue("Раздел 'Начинки' не отобразился после перехода",
                mainPage.isTabActive(mainPage.getTabLocator("Начинки")));
    }
}