package Test;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;

import java.time.Duration;
import java.util.List;

public class BaseTest {
    public static ThreadLocal<WebDriver> driver = new ThreadLocal<WebDriver>();
    public static ThreadLocal<WebDriverWait> explicitWait = new ThreadLocal<WebDriverWait>();
    public static String searchEngineUrl ="https://www.google.com";
    String validateUrl ="gumtree.com";
    String keyword = "Cars in London";
    String searchElement = "//a[contains(@href, '"+validateUrl+"')]";

    @BeforeMethod
    public void openBrowser()throws InterruptedException{
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-notifications");
        WebDriver driver = new ChromeDriver(options);
        JavascriptExecutor js = (JavascriptExecutor) driver;
        driver.get(searchEngineUrl);
        driver.manage().window().maximize();
        explicitWait.set(new WebDriverWait(driver, Duration.ofSeconds(60)));
        try {
            WebElement searchBox = driver.findElement(By.name("q"));;
            searchBox.sendKeys(keyword);
            Thread.sleep(1000);
            searchBox.sendKeys(Keys.RETURN);
            Thread.sleep(1000);
            js.executeScript("window.scrollBy(0, 1500)");
            List<WebElement> searchResults = driver.findElements(By.xpath(searchElement));
            int gumtreeCount = 0;

            for (WebElement result : searchResults) {
                String link = result.getAttribute("href");
                if (link != null && link.contains(validateUrl)) {
                    gumtreeCount++;
                    System.out.println("Found Gumtree link: " + link);
                    driver.get(link);
                    Thread.sleep(2000);
                    String pageTitle = driver.getTitle();
                    System.out.println("Found Title :"+pageTitle);
                }
                System.out.println("Number of Gumtree links found: " + gumtreeCount);
                Assert.assertTrue(gumtreeCount > 0);
            }

        }catch (Exception e) {
            e.printStackTrace();
        } finally {
            Thread.sleep(500);
            driver.quit();
        }
    }
}
