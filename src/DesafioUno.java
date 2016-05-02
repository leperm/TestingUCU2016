import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class DesafioUno {
    ChromeDriver wd;
    
    @Before
    public void setUp() throws Exception {
    	System.setProperty("webdriver.chrome.driver", "C://chromedriver//chromedriver.exe");
        wd = new ChromeDriver();
        wd.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
    }
    
    @Test
    public void desafioUno() {
        wd.get("http://demo.opencart.com/");
        wd.findElement(By.name("search")).click();
        wd.findElement(By.name("search")).clear();
        wd.findElement(By.name("search")).sendKeys("iPhone");
        wd.findElement(By.xpath("//*[@id='search']/span/button/i")).click();
        WebElement element = wd.findElement(By.xpath("//*[@id='content']/div[4]/div/div/div[2]/h4/a"));
        Assert.assertEquals("iPhone", element.getText());
    }
        
    @After
    public void tearDown() {
        wd.close();
    }
    
    public static boolean isAlertPresent(ChromeDriver wd) {
        try {
            wd.switchTo().alert();
            return true;
        } catch (NoAlertPresentException e) {
            return false;
        }
    }
}
