package pageObject;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class PaginaResultadosBusqueda {
	private final WebDriver driver;
	String newLine = System.getProperty("line.separator");
	File archivo;
    FileWriter fw;
    BufferedWriter bw;
	
	public PaginaResultadosBusqueda(WebDriver driver) {
		this.driver = driver;
	}
	
	public String obtenerTituloPrimerResultado(){
		return (driver.findElement(By.xpath("//*[@id='content']/div[4]/div/div/div[2]/h4/a")).getText());
	}
	
	public PaginaArticulo irAPrimerArticulo(){
		driver.findElement(By.xpath("//*[@id='content']/div[4]/div/div/div[2]/h4/a")).click();
		PaginaArticulo paginaArticulo = new PaginaArticulo(driver);
		return paginaArticulo;
	}
	
	public void bajarResultadosAArchivoTxt(String nombreArchivo){
    	archivo = new File("src/resources/"+nombreArchivo+".txt");
    	try {
	    	if (!archivo.exists()){
	        	archivo.createNewFile();
	        }    	
	        fw = new FileWriter(archivo.getAbsoluteFile());
	        bw = new BufferedWriter(fw);
	        if (!driver.findElement(By.xpath("//select[@id='input-limit']//option[5]")).isSelected()) {
	        	driver.findElement(By.xpath("//select[@id='input-limit']//option[5]")).click();
	        }
			
	        for (Integer i=1; i<=20; i++){
				if (elementoPresente("//*[@id='content']/div[4]/div["+(i).toString()+"]/div/div[2]/h4/a")){
					bw.write(driver.findElement(By.xpath("//*[@id='content']/div[4]/div["+(i).toString()+"]/div/div[2]/h4/a")).getText()
								+newLine);					
				}
			}
	        
			bw.close();
	        
	    } catch (Exception e) {
			e.printStackTrace();
		}  
	}
	
	public boolean elementoPresente(String xpath){
    	try{
    		driver.findElement(By.xpath(xpath));
    		return true;
    	}catch (org.openqa.selenium.NoSuchElementException e){
    		return false;
    	}
    }
}
