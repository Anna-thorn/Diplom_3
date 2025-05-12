package pages;

import io.qameta.allure.*;
import org.openqa.selenium.*;
import java.util.*;

/**
 * Базовый абстрактный класс для страниц.
 * Содержит:
 * шаблоны методов
 * локаторы шапки сайта
 */
public class AbstractPage {
    protected WebDriver driver;

    public AbstractPage(WebDriver driver) {
        this.driver = driver;
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
        WebElement field = driver.findElement(locator);
        field.clear();
        field.sendKeys(text);
    }
    // клик по элементу
    protected void clickElement(By locator) {
        driver.findElement(locator).click();
    }
    // проверка отображения элемента
    protected boolean isElementDisplayed(By locator) {
        List<WebElement> elements = driver.findElements(locator);
        return elements.size() == 1 && elements.get(0).isDisplayed();
    }
}