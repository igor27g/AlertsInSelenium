import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

public class Alert {

    WebDriver driver;
    WebDriverWait wait;
    JavascriptExecutor js;

    @BeforeEach
    public void driverSetup()
    {
        String w3schools = "https://www.w3schools.com/code/tryit.asp?filename=FZ9IOGP56P0O";

        System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().timeouts().pageLoadTimeout(15, TimeUnit.SECONDS);
        driver.manage().window().maximize();
        driver.navigate().to(w3schools);

        wait = new WebDriverWait(driver,10);
        driver.manage().timeouts().setScriptTimeout(1000,TimeUnit.MILLISECONDS);
        js = (JavascriptExecutor)driver;

    }

    @AfterEach
    public void driverClose()
    {
        driver.close();
        driver.quit();
    }


    @Test
    public void promptBoxTet() {
        String javascript = "prompt('Możesz tutaj coś wpisać:')";
        js.executeScript(javascript);
        wait.until(ExpectedConditions.alertIsPresent());
        String text = driver.switchTo().alert().getText();
        driver.switchTo().alert().sendKeys("Test");
        driver.switchTo().alert().accept();
        js.executeScript(javascript);
        driver.switchTo().alert().dismiss();
    }

    @Test
    public void acceptConfirmWindowTest() {
        driver.findElement(By.xpath("//*[contains(text(), 'Run')]")).click();
        driver.switchTo().frame(0);
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("button[onclick='confirmFunction()']"))).click();
        driver.switchTo().alert().accept();
        WebElement alertChooseOk = driver.findElement(By.id("demo"));
        Assertions.assertEquals("Wybrana opcja to OK!", alertChooseOk.getText(), "Wrong alert after clicking ok");
    }

    @Test
    public void dismissConfirmWindowTest() {
        driver.findElement(By.xpath("//*[contains(text(), 'Run')]")).click();
        driver.switchTo().frame(0);
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[contains(text(), 'Otwórz alert')]"))).click();
        driver.switchTo().alert().dismiss();
        String alertChooseOk = driver.findElement(By.id("demo")).getText();
        Assertions.assertEquals("Wybrana opcja to Cancel!", alertChooseOk, "Wrong alert after clicking ok");
    }

    @Test
    public void acceptPromptWindowTest() {
        driver.findElement(By.xpath("//*[contains(text(), 'Run')]")).click();
        driver.switchTo().frame(0);
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("button[onclick='promptFunction()']"))).click();
        String name = "Imie";
        driver.switchTo().alert().sendKeys(name);
        driver.switchTo().alert().accept();
        WebElement alertChooseOk = driver.findElement(By.id("prompt-demo"));
        Assertions.assertEquals("Cześć " + name + "! Jak leci?", alertChooseOk.getText(), "Wrong alert after clicking ok");
    }

    @Test
    public void dismissPromptWindowTest() {
        driver.findElement(By.xpath("//*[contains(text(), 'Run')]")).click();
        driver.switchTo().frame(0);
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("button[onclick='promptFunction()']"))).click();
        driver.switchTo().alert().dismiss();
        String alertDismissPrompt = driver.findElement(By.id("prompt-demo")).getText();
        Assertions.assertEquals("Użytkownik anulował akcję.", alertDismissPrompt, "Wrong alert after clicking ok");
    }



}
