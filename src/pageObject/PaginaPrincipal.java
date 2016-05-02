package pageObject;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class PaginaPrincipal {
	private final WebDriver driver;
	
	public PaginaPrincipal(WebDriver driver){
		this.driver = driver;
		driver.get("http://demo.opencart.com/");
	}
	
	public PaginaResultadosBusqueda buscarArticulo(String nombreArticulo){
		driver.findElement(By.name("search")).click();
		driver.findElement(By.name("search")).clear();
		driver.findElement(By.name("search")).sendKeys(nombreArticulo);
		driver.findElement(By.xpath("//*[@id='search']/span/button/i")).click();
		PaginaResultadosBusqueda paginaResultadosBusqueda = new PaginaResultadosBusqueda(driver);
		return paginaResultadosBusqueda;
        
	}
}
