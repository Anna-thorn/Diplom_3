import io.qameta.allure.*;
import io.qameta.allure.junit4.*;
import org.junit.*;
import static org.junit.Assert.*;

import utils.UserGenerator;
import model.User;

/**
 * Проверка главной страницы и конструктора
 * Проверка:
 * переход по клику на «Личный кабинет»
 * переход по клику на «Конструктор» и на логотип Stellar Burgers
 * работу переходов к разделам: «Булки», «Соусы», «Начинки».
 */
public class MainTest extends BaseTest {

    @Test
    @DisplayName("Переход по клику на ссылку 'Личный кабинет' (неавторизованным пользователем)")
    @Description("Проверка что неавторизованный пользователь при клике на 'Личный кабинет' перенаправляется на страницу входа")
    public void testClickPersonalAccountUnregisteredUser() {
        navigation.openMainPage();
        mainPage.clickPersonalAccount();
        assertTrue("Не произошел переход на страницу входа", loginPage.isPageOpened());
    }

    @Test
    @DisplayName("Переход по клику на ссылку 'Личный кабинет' (авторизованным пользователем)")
    @Description("Проверка что авторизованный пользователь при клике на 'Личный кабинет' попадает в свой профиль")
    public void testClickPersonalAccountForLoggedUser() {
        User registeredUser = userApi.registerAndGetUser(UserGenerator.createValidUser());
        try {
            navigation.openLoginPage()
                    .login(registeredUser.getEmail(), registeredUser.getPassword());
            mainPage.isConstructorHeaderDisplayed();
            mainPage.clickPersonalAccount();
            assertTrue("Страница профиля не открылась",
                    personalAccountPage.isProfileHeaderDisplayed());
        } finally {
            userApi.deleteUser(registeredUser);
        }
    }

    @Test
    @DisplayName("Переход по клику на ссылку 'Конструктор'")
    @Description("Проверка что при клике на 'Конструктор' происходит переход на главную страницу")
    public void testClickConstructorLink() {
        navigation.openLoginPage();
        mainPage.clickConstructorLink();
        assertTrue("Главная страница не открылась",
                mainPage.isConstructorHeaderDisplayed());
    }

    @Test
    @DisplayName("Переход по клику на логотип 'Stellar Burgers'")
    @Description("Проверка что при клике на логотип происходит переход на главную страницу")
    public void testClickLogoLink() {
        navigation.openLoginPage();
        mainPage.clickLogo();
        assertTrue("Главная страница не открылась",
                mainPage.isConstructorHeaderDisplayed());
    }

    @Test
    @DisplayName("Проверка активности вкладки 'Булки' по умолчанию") // вариант 1 - проверка текста активной вкладки
    @Description("Проверка что при открытии главной страницы по умолчанию активна вкладка 'Булки'")
    public void testBunsTabIsActiveByDefault() {
        navigation.openMainPage();
        assertEquals("По умолчанию должна быть активна вкладка 'Булки'",
                "Булки", mainPage.getActiveTabText());
    }

    @Test
    @DisplayName("Переход в раздел 'Булки'") // вариант 1 - проверка текста активной вкладки
    @Description("Проверка перехода в раздел 'Булки' после клика на соответствующую вкладку")
    public void testTransitionSectionBuns() {
        navigation.openMainPage();
        mainPage.clickSaucesTab();
        mainPage.waitForScrollToSection(mainPage.getSaucesHeaderLocator());
        mainPage.clickBunsTab();
        mainPage.waitForScrollToSection(mainPage.getBunsHeaderLocator());
        assertEquals("После клика должна быть активна вкладка 'Булки'",
                "Булки", mainPage.getActiveTabText());
    }

    @Test
    @DisplayName("Переход в раздел 'Соусы'") // вариант 2 - проверка заголовка раздела
    @Description("Проверка перехода в раздел 'Соусы' после клика на соответствующую вкладку")
    public void testTransitionSectionSauces() {
        navigation.openMainPage();
        mainPage.clickSaucesTab();
        assertTrue("Раздел 'Соусы' не отобразился после перехода",
                mainPage.isSectionHeaderDisplayed(mainPage.getSaucesHeaderLocator()));
    }

    @Test
    @DisplayName("Переход в раздел 'Начинки'") // вариант 3 - проверка класса активной вкладки
    @Description("Проверка перехода в раздел 'Начинки' после клика на соответствующую вкладку")
    public void testTransitionSectionFillings() {
        navigation.openMainPage();
        mainPage.clickFillingsTab();
        assertTrue("Раздел 'Начинки' не отобразился после перехода",
                mainPage.isTabActive(mainPage.getTabLocator("Начинки")));
    }
}