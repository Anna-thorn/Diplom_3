import io.qameta.allure.*;
import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.BrowserUtils;
import java.util.*;

/**
 * Настройка кроссбраузерного тестирования
 */
@RunWith(Parameterized.class)
public abstract class ParameterizedWebTest extends BaseTest {
    private final String browser;

    public ParameterizedWebTest(String browser) {
        this.browser = browser;
    }

    @Parameterized.Parameters(name = "Браузер: {0}")
    public static Collection<Object[]> browsers() {
        return Arrays.asList(new Object[][]{
                {"chrome"},
                {"yandex"}
        });
    }

    @Before
    public void initDriver() {
        System.setProperty("browser", browser);
        driver = BrowserUtils.createWebDriver();
        driver.manage().window().maximize();
    }

    @Step("Переход на сайт")
    protected void openUrl(String url) {
        driver.get(url);
        new WebDriverWait(driver, DEFAULT_TIMEOUT)
                .until(webDriver -> ((JavascriptExecutor) webDriver)
                        .executeScript("return document.readyState").equals("complete"));
    }

    @After
    public void tearDown() {
        try {
            if (driver != null) {
                driver.quit();
            }
        } finally {
            driver = null;
        }
    }
}