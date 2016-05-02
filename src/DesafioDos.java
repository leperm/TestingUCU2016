import java.util.concurrent.TimeUnit;
import junitparams.FileParameters;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import junitparams.FileParameters;
import junitparams.JUnitParamsRunner;
import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

@RunWith(JUnitParamsRunner.class)
public class DesafioDos {
	
	ChromeDriver wd;
    
    @Before
    public void setUp() throws Exception {
    	System.setProperty("webdriver.chrome.driver", "C://chromedriver//chromedriver.exe");
        wd = new ChromeDriver();
        wd.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }
    
    /**
     * Método que busca productos en la página según 'productos.csv'. El método
     * lanzará una excepción en caso que el producto no exista, haciendo fallar el test case.
     * @param nombreBusqueda texto a ingresar en el buscador de productos de la pagina
     * @param nombreProducto nombre del item en la página
     */
    @Test
    @FileParameters("src/resources/productos.csv")
    public void desafioDos(String nombreBusqueda, String nombreProducto) {
        wd.get("http://demo.opencart.com/");
        wd.findElement(By.name("search")).click();
        wd.findElement(By.name("search")).clear();
        wd.findElement(By.name("search")).sendKeys(nombreBusqueda);
        wd.findElement(By.xpath("//*[@id='search']/span/button/i")).click();
        WebElement element = wd.findElement(By.xpath("//*[@id='content']/div[4]/div/div/div[2]/h4/a"));
        Assert.assertEquals(element.getText(), nombreProducto);
        
    }
    
    /**
     * Método que comprueba productos que no deberían existir en la página. Los productos
     * son tomados del archivo 'noProductos.csv'.
     * @param nombreBusqueda texto a ingresar en el buscador de productos de la pagina
     */
    @Test
    @FileParameters("src/resources/noProductos.csv")
    public void productosInexistentes(String nombreBusqueda) {
        wd.get("http://demo.opencart.com/");
        wd.findElement(By.name("search")).click();
        wd.findElement(By.name("search")).clear();
        wd.findElement(By.name("search")).sendKeys(nombreBusqueda);
        wd.findElement(By.xpath("//*[@id='search']/span/button/i")).click();
        Assert.assertEquals(elementoPresente("//*[@id='content']/div[4]/div/div/div[2]/h4/a"), false);        
    }
    
    /**
     * Método que busca un elemento por xpath.
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
