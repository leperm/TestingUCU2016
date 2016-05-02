import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
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
public class DesafioTres {
	
	String newLine = System.getProperty("line.separator");
	ChromeDriver wd;
	File archivo;
    FileWriter fw;
    BufferedWriter bw;
	
    @Before
    public void setUp() throws Exception {
    	System.setProperty("webdriver.chrome.driver", "C://chromedriver//chromedriver.exe");
        wd = new ChromeDriver();
        wd.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        archivo = new File("src/resources/listaProductos.txt");
        if (!archivo.exists()){
        	archivo.createNewFile();
        }
        fw = new FileWriter(archivo.getAbsoluteFile());
        bw = new BufferedWriter(fw);
    }
    
    /**
     * Obtiene todos los nombres de los productos del sitio y los baja a un archivo de texto llamado
     * productos.txt.
     */
    @Test
    public void desafioTres() {
        wd.get("http://demo.opencart.com/");        
        wd.findElement(By.xpath("//*[@id='search']/input")).click();
        wd.findElement(By.xpath("//*[@id='search']/input")).clear();
        //Busco el caracter espacio (" ") para mostrar todos los elementos.
        wd.findElement(By.xpath("//*[@id='search']/input")).sendKeys(" ");
        wd.findElement(By.xpath("//*[@id='search']/span/button/i")).click();
		if (!wd.findElement(By.xpath("//select[@id='input-limit']//option[5]")).isSelected()) {
            wd.findElement(By.xpath("//select[@id='input-limit']//option[5]")).click();
        }
		
		//A pesar de ser 19 elementos en total, el ultimo elemento de la busqueda es de valor div[20].
		//No existe elemento div[5], por lo cual se chequea que el elemento esté presente antes de escribir su nombre.
		for (Integer i=1; i<=20; i++){
			if (elementoPresente("//*[@id='content']/div[4]/div["+(i).toString()+"]/div/div[2]/h4/a")){
				try {
					bw.write(wd.findElement(By.xpath("//*[@id='content']/div[4]/div["+(i).toString()+"]/div/div[2]/h4/a")).getText()
							+newLine);
				} catch (IOException e) {
					e.printStackTrace();
				}	
			}
		}
		
		//Se comprueba que el archivo exista
		Assert.assertEquals(true, archivo.exists());
		
    }
    
    @Test
    @FileParameters("src/resources/listaProductos.txt")
    public void desafioDos(String nombreProducto) {
        wd.get("http://demo.opencart.com/");
        wd.findElement(By.name("search")).click();
        wd.findElement(By.name("search")).clear();
        wd.findElement(By.name("search")).sendKeys(nombreProducto);
        wd.findElement(By.xpath("//*[@id='search']/span/button/i")).click();
        WebElement element = wd.findElement(By.xpath("//*[@id='content']/div[4]/div/div/div[2]/h4/a"));
        Assert.assertEquals(element.getText(), nombreProducto);
        
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
        try {
        	wd.close();
			bw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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