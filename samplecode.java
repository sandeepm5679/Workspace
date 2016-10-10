package com.custom.test.automation;
import static org.junit.Assert.assertEquals;

import junit.framework.*;
import org.junit.*;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

@RunWith(JUnit4.class)
public class OpenTextTestAutomationPOC extends TestCase {

    private static ChromeDriverService service;
    private WebDriver driver;
    private static String mainWindow;
    private String expected = "Hi padmara... !";

    @BeforeClass
    public static void createAndStartService() {
        service = new ChromeDriverService.Builder()
                .usingDriverExecutable(new File("C:/DEV/TestAutomationPOC/lib/chromedriver.exe"))
                .usingAnyFreePort()
                .build();
        try {
            service.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @AfterClass
    public static void createAndStopService() {
        service.stop();
    }

    @Before
    public void createDriver() {
        driver = new RemoteWebDriver(service.getUrl(),
                DesiredCapabilities.chrome());
    }

    @After
    public void quitDriver() {
        driver.quit();
    }

    @Test
    public void testGoogleSearch() {
        driver.get("http://www.google.com");
        WebElement searchBox = driver.findElement(By.name("q"));
        searchBox.sendKeys("webdriver");
        System.out.println("Actual result : "+ driver.getTitle());
        assertEquals("Google", driver.getTitle());
        driver.quit();
    }

    @Test
    public void testFlipkartLogin() throws InterruptedException {
        driver.manage().timeouts().implicitlyWait(6000, TimeUnit.MILLISECONDS);
        driver.get("http://www.flipkart.com/");
        driver.manage().window().maximize();
        mainWindow = driver.getWindowHandle();

        //*[@id="fk-mainhead-id"]/div[1]/div/div[2]/div[1]/ul/li[8]/a - Login Link
        driver.findElement(By.xpath("//*[@id='fk-mainhead-id']/div[1]/div/div[2]/div[1]/ul/li[8]/a")).click();
        //*[@id="login_email_id"]
        //#login_email_id
        driver.findElement(By.id("login_email_id1")).sendKeys("login_id");
        //*[@id="login_email_id1"]
        //driver.findElement(By.cssSelector("#login_email_id1")).sendKeys("padmaraj.moorthy@gmail.com");

        //*[@id="login_password"]
        //*[@id="login_password1"]
        driver.findElement(By.id("login_password1")).sendKeys("password");
        //*[@id="login-form"]/div[3]/div[2]/input[2]
        //*[@id="login-cta"]
        //driver.findElement(By.xpath("//*[@id='login-form']/div[3]/div[2]/input[2]")).click();
        driver.findElement(By.xpath("//*[@id=\"login-cta\"]")).click();
        //If there is an alert use this
        //driver.switchTo().window(mainWindow);
        String welcomeText = driver.findElement(By.cssSelector("#fk-mainhead-id > div.header-topbar > div > div.unitExt.mainUnit > div.header-links.unitExt > ul > li.no-border.greeting-link > a")).getText();
        System.out.println(welcomeText);
        junit.framework.Assert.assertEquals(expected, welcomeText);
    }
}
