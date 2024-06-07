import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.List;

public class TestConcertUA {

    private WebDriver driver;
    private WebDriverWait wait;

    @BeforeMethod
    public void setUp() {
        // Встановіть шлях до вашого драйвера Chrome
        System.setProperty("webdriver.chrome.driver", "src/test/resources/drivers/chromedriver.exe");
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
    }

    @AfterMethod
    public void tearDown() {
//        if (driver != null) {
//            driver.quit();
//        }
    }

    @Test
    public void testSuccessfulEventFiltering() {
        // Відкрити сайт
        driver.get("https://concert.ua/");

        // Обрати місто в хедері (Київ)
//        driver.findElement(By.xpath("//div[@class=\"city-chooser-wrapper\"]")).click();
//        driver.findElement(By.xpath("//a[@href=\"/en/kyiv\"]")).click();

        // Перейти на сторінку “Концерти”
        driver.findElement(By.xpath("//nav/a[@href=\"/en/catalog/all-cities/concerts\"]")).click();

        // Фільтрувати подію за майданчиком та стилем


        // Перевірити, чи знайшло події за вказаним фільтром

    }

    @Test
    public void testUnsuccessfulEventFiltering() {
        // Відкрити сайт
        driver.get("https://concert.ua/");

        // Обрати місто в хедері (Київ)
        WebElement cityButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[contains(@class, 'city-selector')]")));
        cityButton.click();
        WebElement kyivOption = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[text()='Київ']")));
        kyivOption.click();

        // Перейти на сторінку “Концерти”
        WebElement concertsLink = wait.until(ExpectedConditions.elementToBeClickable(By.linkText("Концерти")));
        concertsLink.click();

        // Фільтрувати подію за майданчиком та стилем
        WebElement venueFilter = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//select[@id='venue-filter']")));
        venueFilter.click();
        WebElement venueOption = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//option[@value='Nonexistent Venue']")));
        venueOption.click();

        WebElement styleFilter = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//select[@id='style-filter']")));
        styleFilter.click();
        WebElement styleOption = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//option[@value='Nonexistent Style']")));
        styleOption.click();

        WebElement applyFiltersButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[text()='Застосувати фільтри']")));
        applyFiltersButton.click();

        // Перевірити, чи знайшло події за вказаним фільтром
        List<WebElement> eventsList = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.className("event-item")));
        Assert.assertTrue(eventsList.isEmpty(), "Знайдено події, хоча не повинно бути");
    }
}