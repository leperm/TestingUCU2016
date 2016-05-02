
import java.util.concurrent.TimeUnit;

import junitparams.FileParameters;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import junitparams.FileParameters;
import junitparams.JUnitParamsRunner;
import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

@RunWith(JUnitParamsRunner.class)
public class DesafioCuatro{
	
	ChromeDriver wd;
    
    @Before
    public void setUp() throws Exception {
    	System.setProperty("webdriver.chrome.driver", "C://chromedriver//chromedriver.exe");
        wd = new ChromeDriver();
        wd.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }
    
    /**
     * M�todo que busca productos en la p�gina seg�n 'productos.csv'. El m�todo
     * lanzar� una excepci�n en caso que el producto no exista, haciendo fallar el test case.
     * @param nombreBusqueda texto a ingresar en el buscador de productos de la pagina
     * @param nombreProducto nombre del item en la p�gina
     */
    
    @Test
    @FileParameters("src/resources/listaProductos.txt")
    public void desafioCuatro(String nombreProducto) {
        wd.get("http://demo.opencart.com/");
        wd.findElement(By.name("search")).click();
        wd.findElement(By.name("search")).clear();
        wd.findElement(By.name("search")).sendKeys(nombreProducto);
        wd.findElement(By.xpath("//*[@id='search']/span/button/i")).click();
        
        //busca primer resultado
        WebElement element = wd.findElement(By.xpath("//*[@id='content']/div[4]/div/div/div[2]/h4/a"));
        //comprueba que coincida con el producto buscado
        Assert.assertEquals(element.getText(), nombreProducto);
        //accede al producto
        element.click();
        //busca el titulo
        element = wd.findElement(By.xpath("//*[@id='content']/div[1]/div[2]/h1"));
        //verifica el titulo
        Assert.assertEquals(element.getText(), nombreProducto);
    }
    
    /**
     * M�todo que busca un elemento por xpath.
     * 
     * @param xpath el xpath del elemento a buscar
     * @return true si el elemento fue encontrado, false en el caso contrario
     */
    public boolean elementoPresente(String xpath){
    	try{
    		wd.findElement(By.xpath(xpath));
    		return true;
    	}catch (org.openqa.selenium.NoSuchElementException e){
    		return false;
    	}
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
