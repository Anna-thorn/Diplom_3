package pages;

import io.qameta.allure.*;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.*;

/**
 * Базовый абстрактный класс для страниц.
 * Содержит:
 * шаблоны методов
 * локаторы шапки сайта
 */
public class AbstractPage {
    protected final WebDriver driver;
    protected final WebDriverWait wait;

    public AbstractPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, 10);
    }

    //=== Локаторы шапки сайта ===//
    protected final By constructorLink = By.xpath("//p[text()='Конструктор']");
    protected final By logoLink = By.cssSelector("div[class*='AppHeader_header__logo']");
    protected final By personalAccountLink = By.xpath("//*[text()='Личный Кабинет']/ancestor::a");

    //=== Общие методы шапки сайта ===//
    @Step("Клик по ссылке 'Конструктор'")
    public void clickConstructorLink() {
        clickElement(constructorLink);
    }
    @Step("Клик по логотипу")
    public void clickLogo() {
        clickElement(logoLink);
    }
    @Step("Клик по 'Личному кабинету'")
    public void clickPersonalAccount() {
        clickElement(personalAccountLink);
    }

    //=== Общие методы для работы с элементами ===//
    // ввод текста в поле
    protected void typeText(By locator, String text) {
        WebElement field = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        field.clear();
        field.sendKeys(text);
    }
    // клик по элементу
    protected void clickElement(By locator) {
        wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
    }
    // проверка отображения элемента
    protected boolean isElementDisplayed(By locator) {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
            return true;
        } catch (TimeoutException e) {
            return false;
        }
    }
}