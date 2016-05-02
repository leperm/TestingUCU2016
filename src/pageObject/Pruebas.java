package pageObject;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import junitparams.FileParameters;
import junitparams.JUnitParamsRunner;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

@RunWith(JUnitParamsRunner.class)
public class Pruebas {

	ChromeDriver wd;	
    
    @Before
    public void setUp() throws Exception {
    	System.setProperty("webdriver.chrome.driver", "C://chromedriver//chromedriver.exe");
        wd = new ChromeDriver();
        wd.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    }
    
    @Test
    public void desafioUno(){
    	PaginaPrincipal homePage = new PaginaPrincipal(wd);
    	PaginaResultadosBusqueda results = homePage.buscarArticulo("iPhone");
    	Assert.assertEquals("iPhone", results.obtenerTituloPrimerResultado());
    }
    
    @Test
    @FileParameters("src/resources/productos.csv")
    public void desafioDos(String nombreBusqueda, String nombreProducto){
    	PaginaPrincipal homePage = new PaginaPrincipal(wd);
    	PaginaResultadosBusqueda results = homePage.buscarArticulo(nombreBusqueda);
    	Assert.assertEquals(nombreProducto, results.obtenerTituloPrimerResultado());
    }    
    
    @Test
    public void desafioTres(){
    	PaginaPrincipal homePage = new PaginaPrincipal(wd);
    	PaginaResultadosBusqueda results = homePage.buscarArticulo(" ");
    	results.bajarResultadosAArchivoTxt("listaProductos");
    	File archivo = new File("src/resources/listaProductos.txt");
    	Assert.assertEquals(true, archivo.exists());
    }
    
    @Test
    @FileParameters("src/resources/listaProductos.txt")
    public void desafioCuatro(String nombreProducto) {
    	PaginaPrincipal homePage = new PaginaPrincipal(wd);
    	PaginaResultadosBusqueda results = homePage.buscarArticulo(nombreProducto);
    	Assert.assertEquals(nombreProducto, results.obtenerTituloPrimerResultado());
    	PaginaArticulo articulo = results.irAPrimerArticulo();
    	Assert.assertEquals(nombreProducto,articulo.obtenerTituloArticulo());
    }
    
    @After
    public void tearDown() {
        wd.close();
    }

}
