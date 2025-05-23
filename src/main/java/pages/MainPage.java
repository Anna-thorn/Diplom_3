package pages;

import io.qameta.allure.*;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;

/**
 * Главная страница и раздел Конструктор
 */
public class MainPage extends AbstractPage {
    public MainPage(WebDriver driver) {
        super(driver);
    }

    // локаторы для конструктора
    private final By constructorHeader = By.cssSelector("h1.text_type_main-large");
    private final By bunsTab = By.xpath("//span[contains(@class, 'text_type_main-default') and text()='Булки']/..");
    private final By saucesTab = By.xpath("//span[contains(@class, 'text_type_main-default') and text()='Соусы']/..");
    private final By fillingsTab = By.xpath("//span[contains(@class, 'text_type_main-default') and text()='Начинки']/..");
    private final By activeTab = By.cssSelector("div.tab_tab_type_current__2BEPc");
    private final By loginButton = By.xpath("//button[text()='Войти в аккаунт']");
    // локаторы заголовков разделов
    private final By bunsHeader = By.xpath("//h2[text()='Булки']");
    private final By saucesHeader = By.xpath("//h2[text()='Соусы']");
    private final By fillingsHeader = By.xpath("//h2[text()='Начинки']");

    @Step("Проверка открытия страницы конструктора")
    public boolean isConstructorHeaderDisplayed() {
        return isElementDisplayed(constructorHeader);
    }

    @Step("Клик по вкладке 'Булки'")
    public void clickBunsTab() {
        clickElement(bunsTab);
    }

    @Step("Проверка отображения заголовка раздела 'Булки'")
    public boolean isBunsHeaderDisplayed() {
        return isElementDisplayed(bunsHeader);
    }

    @Step("Клик по вкладке 'Соусы'")
    public void clickSaucesTab() {
        clickElement(saucesTab);
    }

    @Step("Проверка отображения заголовка раздела 'Соусы'")
    public boolean isSaucesHeaderDisplayed() {
        return isElementDisplayed(saucesHeader);
    }

    @Step("Клик по вкладке 'Начинки'")
    public void clickFillingsTab() {
        clickElement(fillingsTab);
    }

    @Step("Проверка отображения заголовка раздела 'Начинки'")
    public boolean isFillingsHeaderDisplayed() {
        return isElementDisplayed(fillingsHeader);
    }

    @Step("Ожидание завершения скролла к разделу '{sectionName}'")
    public void waitForScrollToSection(By sectionLocator) {
        WebElement section = wait.until(ExpectedConditions.presenceOfElementLocated(sectionLocator));
        wait.until(d -> {
            int yPosition = section.getLocation().getY();
            int windowHeight = driver.manage().window().getSize().getHeight();
            return yPosition > 0 && yPosition < windowHeight;
        });
    }

    @Step("Клик по кнопке 'Войти в аккаунт'")
    public void clickLoginButton() {
        clickElement(loginButton);
    }

    public By getBunsHeaderLocator() {return bunsHeader;}
    public By getSaucesHeaderLocator() {return saucesHeader;}
    public By getFillingsHeaderLocator() {return fillingsHeader;}

    /* Проверим активность вкладки разными способами (небольшой эксперимент) */

    // 1 вариант: общий метод проверки активной вкладки (ищет вкладку и возвращает её название)
    @Step("Проверка что вкладка стала активной")
    public String getActiveTabText() {
        WebElement activeTabElement = wait.until(ExpectedConditions.visibilityOfElementLocated(activeTab));
        return activeTabElement.getText().trim();
    }

    // 2 вариант: проверка появления соответствующего заголовка раздела
    @Step("Проверка что вкладка '{headerName}' активна")
    public boolean isSectionHeaderDisplayed(By headerLocator) {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(headerLocator));
            return true;
        } catch (TimeoutException e) {
            return false;
        }
    }

    // 3 вариант: проверяет, что конкретная вкладка сейчас активна
    @Step("Проверка что вкладка стала активной")
    public boolean isTabActive(By tabLocator) {
        try {
            WebElement tab = wait.until(ExpectedConditions.visibilityOfElementLocated(tabLocator));
            return tab.getAttribute("class").contains("tab_tab_type_current__2BEPc");
        } catch (TimeoutException e) {
            return false;
        }
    }
    public By getTabLocator(String tabName) { // дополнительный метод получения локатора по имени вкладки
        switch (tabName.trim().toLowerCase()) {
            case "булки": return bunsTab;
            case "соусы": return saucesTab;
            case "начинки": return fillingsTab;
            default: throw new IllegalArgumentException("Неизвестная вкладка: " + tabName);
        }
    }
}