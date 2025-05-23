package utils;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

/**
 * Класс для настройки окружения (браузеров)
 */
public class BrowserUtils {
    private static final String YANDEX_DRIVER_VERSION = "133.0.6943.126"; // версии хрома лежат C:\Users\Practicum\.cache\selenium\chromedriver\win64
    private static final String YANDEX_BROWSER_PATH = "C:\\Users\\Practicum\\AppData\\Local\\Yandex\\YandexBrowser\\Application\\browser.exe";

    public static WebDriver createWebDriver() {
        String browser = System.getProperty("browser");
        if (browser == null) {
            return createChromeDriver();
        }

        switch (browser) {
            case "yandex":
                return createYandexDriver();
            case "chrome":
            default:
                return createChromeDriver();
        }
    }


    private static WebDriver createChromeDriver() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        return new ChromeDriver(options);
    }

    private static WebDriver createYandexDriver() {
        // путь для Яндекс.Браузера через chromedriver (версия Яндекса)
        WebDriverManager.chromedriver().driverVersion(YANDEX_DRIVER_VERSION).setup();
        ChromeOptions options = new ChromeOptions();
        options.setBinary(YANDEX_BROWSER_PATH); // полный путь к исполняемому файлу Яндекс.Браузера
        return new ChromeDriver(options);
    }
}