package pageObject;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class PaginaArticulo {
	private final WebDriver driver;
	
	public PaginaArticulo(WebDriver driver) {
		this.driver = driver;
	}
	
	public String obtenerTituloArticulo(){
		return (driver.findElement(By.xpath("//*[@id='content']/div[1]/div[2]/h1")).getText());
	}

}
